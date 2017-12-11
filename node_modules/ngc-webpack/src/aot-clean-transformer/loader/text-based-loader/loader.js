"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var Path = require("path");
var ts = require("typescript");
var loaderUtils = require('loader-utils');
var refactor_1 = require("./refactor");
var webpack_wrapper_1 = require("../../../webpack-wrapper");
function _angularImportsFromNode(node, _sourceFile) {
    var ms = node.moduleSpecifier;
    var modulePath = null;
    switch (ms.kind) {
        case ts.SyntaxKind.StringLiteral:
            modulePath = ms.text;
            break;
        default:
            return [];
    }
    if (!modulePath.startsWith('@angular/')) {
        return [];
    }
    if (node.importClause) {
        if (node.importClause.name) {
            // This is of the form `import Name from 'path'`. Ignore.
            return [];
        }
        else if (node.importClause.namedBindings) {
            var nb = node.importClause.namedBindings;
            if (nb.kind == ts.SyntaxKind.NamespaceImport) {
                // This is of the form `import * as name from 'path'`. Return `name.`.
                return [nb.name.text + '.'];
            }
            else {
                // This is of the form `import {a,b,c} from 'path'`
                var namedImports = nb;
                return namedImports.elements
                    .map(function (is) { return is.propertyName ? is.propertyName.text : is.name.text; });
            }
        }
    }
    else {
        // This is of the form `import 'path';`. Nothing to do.
        return [];
    }
}
function _ctorParameterFromTypeReference(paramNode, angularImports, refactor) {
    var typeName = 'undefined';
    if (paramNode.type) {
        switch (paramNode.type.kind) {
            case ts.SyntaxKind.TypeReference:
                var type = paramNode.type;
                var tsType = refactor.program.getTypeChecker().getTypeFromTypeNode(type);
                if (tsType.symbol.flags & ts.SymbolFlags.Interface
                    || tsType.symbol.flags & ts.SymbolFlags.TypeAlias
                    || tsType.symbol.flags & ts.SymbolFlags.TypeLiteral) {
                    typeName = 'Object';
                }
                else if (type.typeName) {
                    typeName = type.typeName.getText(refactor.sourceFile);
                }
                else {
                    typeName = type.getText(refactor.sourceFile);
                }
                break;
            case ts.SyntaxKind.AnyKeyword:
                typeName = 'undefined';
                break;
            default:
                typeName = 'null';
        }
    }
    var decorators = refactor.findAstNodes(paramNode, ts.SyntaxKind.Decorator);
    var decoratorStr = decorators
        .map(function (decorator) {
        var call = refactor.findFirstAstNode(decorator, ts.SyntaxKind.CallExpression);
        if (!call) {
            return null;
        }
        var fnName = call.expression.getText(refactor.sourceFile);
        var args = call.arguments.map(function (x) { return x.getText(refactor.sourceFile); }).join(', ');
        if (angularImports.indexOf(fnName) === -1) {
            return null;
        }
        else {
            return [fnName, args];
        }
    })
        .filter(function (x) { return !!x; })
        .map(function (_a) {
        var name = _a[0], args = _a[1];
        if (args) {
            return "{ type: " + name + ", args: [" + args + "] }";
        }
        return "{ type: " + name + " }";
    })
        .join(', ');
    if (decorators.length > 0) {
        return "{ type: " + typeName + ", decorators: [" + decoratorStr + "] }";
    }
    return "{ type: " + typeName + " }";
}
function _addCtorParameters(classNode, angularImports, refactor) {
    // For every classes with constructors, output the ctorParameters function which contains a list
    // of injectable types.
    var ctor = refactor.findFirstAstNode(classNode, ts.SyntaxKind.Constructor);
    if (!ctor) {
        // A class can be missing a constructor, and that's _okay_.
        return;
    }
    var params = Array.from(ctor.parameters).map(function (paramNode) {
        return _ctorParameterFromTypeReference(paramNode, angularImports, refactor);
    });
    // Type script will complain if extending class static's ctorParameters method does not match parent
    if (classNode.heritageClauses && classNode.heritageClauses.some(function (hc) { return hc.token === ts.SyntaxKind.ExtendsKeyword; })) {
        var ctorParametersDecl = "(" + classNode.name.text + " as any).ctorParameters = function () { return [ " + params.join(', ') + " ]; }";
        refactor.appendAfter(classNode.getLastToken(refactor.sourceFile), ctorParametersDecl);
    }
    else {
        var ctorParametersDecl = "static ctorParameters() { return [ " + params.join(', ') + " ]; }";
        refactor.prependBefore(classNode.getLastToken(refactor.sourceFile), ctorParametersDecl);
    }
}
function _removeDecorators(refactor) {
    var angularImports = refactor.findAstNodes(refactor.sourceFile, ts.SyntaxKind.ImportDeclaration)
        .map(function (node) { return _angularImportsFromNode(node, refactor.sourceFile); })
        .reduce(function (acc, current) { return acc.concat(current); }, []);
    var marker = [];
    // Find all decorators.
    refactor.findAstNodes(refactor.sourceFile, ts.SyntaxKind.Decorator)
        .forEach(function (node) {
        // First, add decorators to classes to the classes array.
        if (node.parent) {
            var declarations = refactor.findAstNodes(node.parent, ts.SyntaxKind.ClassDeclaration, false, 1);
            if (declarations.length > 0 && marker.indexOf(declarations[0]) === -1) {
                marker.push(declarations[0]);
                _addCtorParameters(declarations[0], angularImports, refactor);
            }
        }
        refactor.findAstNodes(node, ts.SyntaxKind.CallExpression)
            .filter(function (node) {
            var fnName = node.expression.getText(refactor.sourceFile);
            if (fnName.indexOf('.') != -1) {
                // Since this is `a.b`, see if it's the same namespace as a namespace import.
                return angularImports.indexOf(fnName.replace(/\..*$/, '') + '.') != -1;
            }
            else {
                return angularImports.indexOf(fnName) != -1;
            }
        })
            .forEach(function () { return refactor.removeNode(node); });
    });
}
function getDiagnostics(program, sourceFile, typeCheck) {
    if (typeCheck === void 0) { typeCheck = true; }
    var diagnostics = [];
    // only concat the declaration diagnostics if the tsconfig config sets it to true.
    diagnostics = diagnostics.concat(program.getOptionsDiagnostics());
    diagnostics = diagnostics.concat(program.getGlobalDiagnostics());
    if (program.getCompilerOptions().declaration == true) {
        diagnostics = diagnostics.concat(program.getDeclarationDiagnostics(sourceFile));
    }
    diagnostics = diagnostics.concat(program.getSyntacticDiagnostics(sourceFile), typeCheck ? program.getSemanticDiagnostics(sourceFile) : []);
    return diagnostics;
}
var program;
var compilerHost;
var AOTMode;
/**
 * Reset the loader, allows running a new program on the same session.
 * @internal
 */
function resetLoader() {
    program = compilerHost = AOTMode = undefined;
}
exports.resetLoader = resetLoader;
function aotCleanLoader(source, sourceMap) {
    var cb = this.async();
    var sourceFileName = this.resourcePath;
    if (AOTMode === false || sourceFileName.endsWith('ngfactory.ts')) {
        return cb(null, source, sourceMap);
    }
    if (!program) {
        try {
            var self = this;
            var plugin = webpack_wrapper_1.findPlugin(self._compilation);
            var options = loaderUtils.getOptions(this) || {};
            if (options.disable === false) {
                AOTMode = true;
            }
            else {
                AOTMode = false;
                return cb(null, source, sourceMap);
            }
            var tsConfigPath = options.tsConfigPath;
            if (!tsConfigPath && plugin) {
                tsConfigPath = plugin.options.tsConfig;
            }
            if (tsConfigPath === undefined) {
                throw new Error('aot-transformer is being used as a loader but no `tsConfigPath` option nor '
                    + 'NgcWebpackPlugin was detected. You must provide at least one of these.');
            }
            var tsConfig = ts.readConfigFile(tsConfigPath, ts.sys.readFile);
            if (tsConfig.error) {
                throw tsConfig.error;
            }
            for (var _i = 0, _a = Object.keys(options); _i < _a.length; _i++) {
                var key = _a[_i];
                if (key == 'tsConfigPath') {
                    continue;
                }
                tsConfig.config.compilerOptions[key] = options[key];
            }
            tsConfig.config.compilerOptions.strictNullChecks = false;
            tsConfig.config.compilerOptions.declaration = false;
            tsConfig.config.compilerOptions.diagnostics = false;
            tsConfig.config.compilerOptions.noEmit = true;
            tsConfig.config.compilerOptions.skipLibCheck = true;
            var parsedConfig = ts.parseJsonConfigFileContent(tsConfig.config, ts.sys, Path.dirname(tsConfigPath));
            compilerHost = ts.createCompilerHost(parsedConfig.options);
            program = ts.createProgram(parsedConfig.fileNames, parsedConfig.options);
        }
        catch (err) {
            return cb(err);
        }
    }
    program.emit(program.getSourceFile(sourceFileName), (function () { }));
    var diagnostics = getDiagnostics(program, program.getSourceFile(sourceFileName));
    if (diagnostics.length >= 1) {
        var errors = diagnosticsToErrors(diagnostics);
        if (errors.length === 1) {
            cb(errors[0]);
        }
        else {
            for (var _b = 0, errors_1 = errors; _b < errors_1.length; _b++) {
                var e = errors_1[_b];
                this.emitError(e);
            }
            cb(new Error('NgcWebpack AotCleanupLoader: Multiple Errors'));
        }
    }
    else {
        try {
            var refactor = new refactor_1.TypeScriptFileRefactor(sourceFileName, program.getCompilerOptions(), compilerHost, program, source);
            _removeDecorators(refactor);
            cb(null, refactor.sourceText);
        }
        catch (err) {
            cb(err);
        }
    }
}
exports.aotCleanLoader = aotCleanLoader;
function diagnosticsToErrors(diagnostics) {
    var errors = [];
    diagnostics.forEach(function (d) {
        var msg = d.messageText;
        if (typeof msg === 'string') {
            errors.push(new Error(msg));
        }
        else {
            var chain = d;
            while (chain) {
                if (chain.category = ts.DiagnosticCategory.Error) {
                    errors.push(new Error(chain.messageText));
                }
                chain = chain.next;
            }
        }
    });
    return errors;
}
//# sourceMappingURL=loader.js.map