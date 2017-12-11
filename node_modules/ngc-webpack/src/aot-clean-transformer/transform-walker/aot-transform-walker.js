"use strict";
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
Object.defineProperty(exports, "__esModule", { value: true });
var ts = require("typescript");
var utils = require("../utils");
var base_transform_walker_1 = require("./base-transform-walker");
var mock_type_checker_1 = require("./mock-type-checker");
var AotWalkerContext = (function (_super) {
    __extends(AotWalkerContext, _super);
    function AotWalkerContext() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this.scope = [];
        return _this;
    }
    AotWalkerContext.prototype.addScope = function (node) {
        this.scope.push(node.kind);
    };
    return AotWalkerContext;
}(base_transform_walker_1.WalkerContext));
exports.AotWalkerContext = AotWalkerContext;
var mockTypeChecker;
var AotTransformWalker = (function (_super) {
    __extends(AotTransformWalker, _super);
    function AotTransformWalker(sourceFile, context, walkerContext) {
        var _this = _super.call(this, sourceFile, context, walkerContext || new AotWalkerContext()) || this;
        _this.sourceFile = sourceFile;
        _this.context = context;
        _this.angularImports = _this.findAstNodes(sourceFile, ts.SyntaxKind.ImportDeclaration)
            .map(function (node) { return utils.angularImportsFromNode(node, _this.sourceFile); })
            .reduce(function (acc, current) { return acc.concat(current); }, []);
        return _this;
    }
    Object.defineProperty(AotTransformWalker.prototype, "mocker", {
        get: function () {
            return mockTypeChecker || (mockTypeChecker = new mock_type_checker_1.MockTypeChecker(this.context.getCompilerOptions()));
        },
        enumerable: true,
        configurable: true
    });
    AotTransformWalker.prototype.walk = function () {
        if (this.sourceFile.fileName.endsWith('ngfactory.ts')) {
            return this.sourceFile;
        }
        else {
            return _super.prototype.walk.call(this);
        }
    };
    AotTransformWalker.prototype.onBeforeVisitNode = function (node) {
        switch (node.kind) {
            case ts.SyntaxKind.ClassDeclaration:
                this.walkerContext.currentClass = node;
                break;
            case ts.SyntaxKind.Parameter:
                if (this.walkerContext.currentClass && node.decorators) {
                    this.walkerContext.currentClassParam = node;
                }
                break;
            case ts.SyntaxKind.PropertyDeclaration:
                if (this.walkerContext.currentClass && node.decorators) {
                    this.walkerContext.currentClassProp = node;
                }
                break;
            case ts.SyntaxKind.MethodDeclaration:
                if (this.walkerContext.currentClass && node.decorators) {
                    this.walkerContext.currentClassMethod = node;
                }
                break;
        }
    };
    AotTransformWalker.prototype.onAfterVisitNode = function (node) {
        switch (node.kind) {
            case ts.SyntaxKind.ClassDeclaration:
                this.walkerContext.currentClass = undefined;
                break;
            case ts.SyntaxKind.Parameter:
                this.walkerContext.currentClassParam = undefined;
                break;
            case ts.SyntaxKind.PropertyDeclaration:
                this.walkerContext.currentClassProp = undefined;
                break;
            case ts.SyntaxKind.MethodDeclaration:
                this.walkerContext.currentClassMethod = undefined;
                break;
        }
    };
    AotTransformWalker.prototype.visitClassDeclaration = function (node) {
        var _this = this;
        if (node.decorators && node.decorators.some(function (n) { return _this.isAngularDecorator(n); })) {
            var members = node.members || [];
            var ctorParams = this.classCtorParamsToLiteralExpressions(node);
            if (ctorParams && ctorParams.length > 0) {
                // Since we are to remove the decorators we need to preserve constructor parameter types and
                // param decorators for angular to be able to use them at runtime.
                //
                // This is done by setting a static method on the class called `ctorParameters`
                // The method returns an array of type information for each ctor param.
                // The type information includes the type value and an array of decorator information.
                // Decorator information holds the decorator function and arguments (metadata) used as params
                // for the decorator factory. Decorator information exists only if the param has decorators.
                // { type: TYPE_VALUE, decorators: [ { type: DECORATOR_VALUE: args: [...] }
                //
                // If the class extends a base class astatic method might fail type check when the signature
                // of constructors does not match.
                // Type script will complain if extending class static's ctorParameters method does not match parent
                // If the class extends another class we do not set the `ctorParameters` function as a
                // static class member method since it might have a different signature then the `ctorParameters`
                // method on the parent class which leads to TS complaining
                // instead we create an assignment to the class object right after class declaration:
                //   export class MyClass { }
                //   (MyClass as any).ctorParameters = function() { return [ ... ]; }
                //
                // `ctorParameters` does not exist on `MyClass` so we cast it to `any`
                if (node.heritageClauses && node.heritageClauses.some(function (hc) { return hc.token === ts.SyntaxKind.ExtendsKeyword; })) {
                    var statements = [_super.prototype.visitClassDeclaration.call(this, this.updateClassDeclaration(node, members))];
                    var leftSideBase = ts.createParen(ts.createAsExpression(node.name, ts.createTypeReferenceNode('any', undefined)));
                    var leftSide = ts.createPropertyAccess(leftSideBase, ts.createIdentifier('ctorParameters'));
                    var rightSide = ts.createFunctionExpression(undefined, undefined, undefined, undefined, undefined, undefined, ts.createBlock([ts.createReturn(ts.createArrayLiteral(ctorParams))]));
                    var statement = ts.createStatement(ts.createAssignment(leftSide, rightSide));
                    statements.push(statement);
                    return statements;
                }
                else {
                    var m = ts.createMethod(undefined, [ts.createToken(ts.SyntaxKind.StaticKeyword)], undefined, ts.createIdentifier('ctorParameters'), undefined, undefined, undefined, undefined, ts.createBlock([ts.createReturn(ts.createArrayLiteral(ctorParams))]));
                    members.push(m);
                    return _super.prototype.visitClassDeclaration.call(this, this.updateClassDeclaration(node, members));
                }
            }
        }
        return _super.prototype.visitClassDeclaration.call(this, node);
    };
    AotTransformWalker.prototype.updateClassDeclaration = function (node, members) {
        var _this = this;
        var decorators = this.filterNodes(node.decorators, function (n) { return !_this.isAngularDecorator(n); });
        return ts.updateClassDeclaration(node, decorators.length > 0 ? node.decorators : undefined, node.modifiers, node.name, node.typeParameters, node.heritageClauses, members && members.length > 0 ? members : undefined);
    };
    AotTransformWalker.prototype.visitParameterDeclaration = function (node) {
        var _this = this;
        if (this.walkerContext.currentClassParam === node) {
            var decorators = this.filterNodes(node.decorators, function (n) { return !_this.isAngularDecorator(n); });
            if (decorators.length === 0) {
                var newParamDec = ts.updateParameter(node, undefined, node.modifiers, node.dotDotDotToken, node.name, node.questionToken, node.type, node.initializer);
                return _super.prototype.visitParameterDeclaration.call(this, newParamDec);
            }
        }
        return _super.prototype.visitParameterDeclaration.call(this, node);
    };
    AotTransformWalker.prototype.visitPropertyDeclaration = function (node) {
        var _this = this;
        if (this.walkerContext.currentClassProp) {
            var decorators = this.filterNodes(node.decorators, function (n) { return !_this.isAngularDecorator(n); });
            if (decorators.length === 0) {
                var newPropDecl = ts.updateProperty(node, undefined, node.modifiers, node.name, node.type, node.initializer);
                return _super.prototype.visitPropertyDeclaration.call(this, newPropDecl);
            }
        }
        return _super.prototype.visitPropertyDeclaration.call(this, node);
    };
    AotTransformWalker.prototype.visitMethodDeclaration = function (node) {
        var _this = this;
        if (this.walkerContext.currentClassMethod) {
            var decorators = this.filterNodes(node.decorators, function (n) { return !_this.isAngularDecorator(n); });
            if (decorators.length === 0) {
                var newMethodDecl = ts.updateMethod(node, undefined, node.modifiers, node.asteriskToken, node.name, node.questionToken, node.typeParameters, node.parameters, node.type, node.body);
                return _super.prototype.visitMethodDeclaration.call(this, newMethodDecl);
            }
        }
        return _super.prototype.visitMethodDeclaration.call(this, node);
    };
    AotTransformWalker.prototype.visitDecorator = function (node) {
        if (this.walkerContext.currentClass) {
            return this.isAngularDecorator(node)
                ? undefined
                : node;
        }
        return node;
    };
    AotTransformWalker.prototype.isAngularDecorator = function (node) {
        var _this = this;
        return this.findAstNodes(node, ts.SyntaxKind.CallExpression)
            .filter(function (node) {
            var fnName = node.expression.getText(_this.sourceFile);
            if (fnName.indexOf('.') != -1) {
                // Since this is `a.b`, see if it's the same namespace as a namespace import.
                return _this.angularImports.indexOf(fnName.replace(/\..*$/, '') + '.') != -1;
            }
            else {
                return _this.angularImports.indexOf(fnName) != -1;
            }
        }).length > 0;
    };
    AotTransformWalker.prototype.classCtorParamsToLiteralExpressions = function (classNode) {
        var _this = this;
        // For every classes with constructors, output the ctorParameters function which contains a list
        // of injectable types.
        var ctor = this.findFirstAstNode(classNode, ts.SyntaxKind.Constructor);
        if (!ctor) {
            // A class can be missing a constructor, and that's _okay_.
            return [];
        }
        return Array.from(ctor.parameters)
            .map(function (paramNode) { return _this.ctorParameterFromTypeReference(paramNode); });
    };
    AotTransformWalker.prototype.ctorParameterFromTypeReference = function (paramNode) {
        if (!this.mocker.hasFile(this.sourceFile.fileName)) {
            // we must use virtual files and not the actual file since previous loaders might have changed
            // the code.
            this.mocker.addVirtFile(this.sourceFile.fileName, this.sourceFile.text);
        }
        var typeName = this.ctorParameName(paramNode);
        var decorators = this.findAstNodes(paramNode, ts.SyntaxKind.Decorator);
        var objLiteralDecorators = this.decoratorsAsObjectLiteral(decorators);
        // TODO: A bug in ngTools shows empty decorators array literal if there are decorators
        // but they are not angular's decorators, i.e. custom or other 3rd party lib decorators.
        // when fixed, uncomment this and remove the if after it.
        // if (objLiteralDecorators.length > 0) {
        if (decorators.length > 0) {
            return ts.createObjectLiteral([
                ts.createPropertyAssignment('type', ts.createIdentifier(typeName)),
                ts.createPropertyAssignment('decorators', ts.createArrayLiteral(objLiteralDecorators))
            ]);
        }
        else {
            return ts.createObjectLiteral([
                ts.createPropertyAssignment('type', ts.createIdentifier(typeName))
            ]);
        }
    };
    AotTransformWalker.prototype.decoratorsAsObjectLiteral = function (decorators) {
        var _this = this;
        return decorators
            .map(function (decorator) {
            var call = _this.findFirstAstNode(decorator, ts.SyntaxKind.CallExpression);
            if (!call) {
                return null;
            }
            var fnName = call.expression.getText(_this.sourceFile);
            if (_this.angularImports.indexOf(fnName) === -1) {
                return null;
            }
            else {
                return [
                    fnName,
                    call.arguments.map(function (x) { return ts.createIdentifier(x.getText(_this.sourceFile)); })
                ];
            }
        })
            .filter(function (x) { return !!x; })
            .map(function (_a) {
            var name = _a[0], args = _a[1];
            var propAssignments = [ts.createPropertyAssignment('type', ts.createIdentifier(name))];
            if (args && args.length > 0) {
                propAssignments.push(ts.createPropertyAssignment('args', ts.createArrayLiteral(args)));
            }
            return ts.createObjectLiteral(propAssignments);
        });
    };
    AotTransformWalker.prototype.ctorParameName = function (paramNode) {
        var typeName = 'undefined';
        if (paramNode.type) {
            switch (paramNode.type.kind) {
                case ts.SyntaxKind.TypeReference:
                    var checker = this.mocker.program.getTypeChecker();
                    paramNode = utils.findRemoteMatch(this.mocker.getSourceFile(this.sourceFile.fileName), paramNode);
                    var type = paramNode.type;
                    var symbolFlags = ts.SymbolFlags.None;
                    if (checker) {
                        var tsType = checker.getTypeFromTypeNode(type);
                        if (tsType) {
                            symbolFlags = tsType.symbol.flags;
                        }
                    }
                    if (symbolFlags && (symbolFlags & ts.SymbolFlags.Interface
                        || symbolFlags & ts.SymbolFlags.TypeAlias
                        || symbolFlags & ts.SymbolFlags.TypeLiteral)) {
                        typeName = 'Object';
                    }
                    else if (type.typeName) {
                        typeName = type.typeName.getText(this.sourceFile);
                    }
                    else {
                        typeName = type.getText(this.sourceFile);
                    }
                    break;
                case ts.SyntaxKind.AnyKeyword:
                    typeName = 'undefined';
                    break;
                default:
                    typeName = 'null';
            }
        }
        return typeName;
    };
    return AotTransformWalker;
}(base_transform_walker_1.BaseTransformWalker));
exports.AotTransformWalker = AotTransformWalker;
//# sourceMappingURL=aot-transform-walker.js.map