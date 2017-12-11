"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var ts = require("typescript");
var WalkerContext = (function () {
    function WalkerContext() {
    }
    return WalkerContext;
}());
exports.WalkerContext = WalkerContext;
var BaseTransformWalker = (function () {
    function BaseTransformWalker(sourceFile, context, walkerContext) {
        this.sourceFile = sourceFile;
        this.context = context;
        this.walkerContext = walkerContext || new WalkerContext();
    }
    BaseTransformWalker.prototype.walk = function () {
        if (this.sourceFile.isDeclarationFile) {
            return this.sourceFile;
        }
        else {
            var visited = this.visitNode(this.sourceFile);
            return visited;
        }
    };
    BaseTransformWalker.prototype.visitClassDeclaration = function (node) {
        return this.walkChildren(node);
    };
    BaseTransformWalker.prototype.visitConstructorDeclaration = function (node) {
        return this.walkChildren(node);
    };
    BaseTransformWalker.prototype.visitParameterDeclaration = function (node) {
        return this.walkChildren(node);
    };
    BaseTransformWalker.prototype.visitPropertyDeclaration = function (node) {
        return this.walkChildren(node);
    };
    BaseTransformWalker.prototype.visitMethodDeclaration = function (node) {
        return this.walkChildren(node);
    };
    BaseTransformWalker.prototype.visitSyntaxList = function (node) {
        return this.walkChildren(node);
    };
    BaseTransformWalker.prototype.visitDecorator = function (node) {
        return this.walkChildren(node);
    };
    BaseTransformWalker.prototype.visitNode = function (node) {
        this.onBeforeVisitNode(node);
        var visited = this._visitNode(node);
        this.onAfterVisitNode(node);
        return visited;
    };
    BaseTransformWalker.prototype.filterNodes = function (nodes, test) {
        return nodes
            ? ts.createNodeArray(nodes.filter(function (n) { return test(n); }))
            : ts.createNodeArray();
    };
    BaseTransformWalker.prototype.visitNodes = function (nodes, test) {
        var _this = this;
        if (!nodes || nodes.length === 0) {
            return undefined;
        }
        else {
            var resultNodes = ts.visitNodes(nodes, function (n) { return _this.visitNode(n); }, test);
            if (!resultNodes || resultNodes.length === 0) {
                return undefined;
            }
            else {
                return nodes;
            }
        }
    };
    BaseTransformWalker.prototype.walkChildren = function (node) {
        var _this = this;
        return ts.visitEachChild(node, function (child) { return _this.visitNode(child); }, this.context);
    };
    BaseTransformWalker.prototype.onBeforeVisitNode = function (node) { };
    BaseTransformWalker.prototype.onAfterVisitNode = function (node) { };
    BaseTransformWalker.prototype.findAstNodes = function (node, kind, recursive, max) {
        if (recursive === void 0) { recursive = false; }
        if (max === void 0) { max = Infinity; }
        if (max == 0) {
            return [];
        }
        if (!node) {
            node = this.sourceFile;
        }
        var arr = [];
        if (node.kind === kind) {
            // If we're not recursively looking for children, stop here.
            if (!recursive) {
                return [node];
            }
            arr.push(node);
            max--;
        }
        if (max > 0) {
            for (var _i = 0, _a = node.getChildren(this.sourceFile); _i < _a.length; _i++) {
                var child = _a[_i];
                this.findAstNodes(child, kind, recursive, max)
                    .forEach(function (node) {
                    if (max > 0) {
                        arr.push(node);
                    }
                    max--;
                });
                if (max <= 0) {
                    break;
                }
            }
        }
        return arr;
    };
    BaseTransformWalker.prototype.findFirstAstNode = function (node, kind) {
        return this.findAstNodes(node, kind, false, 1)[0] || null;
    };
    BaseTransformWalker.prototype._visitNode = function (node) {
        switch (node.kind) {
            case ts.SyntaxKind.ClassDeclaration:
                return this.visitClassDeclaration(node);
            case ts.SyntaxKind.Constructor:
                return this.visitConstructorDeclaration(node);
            case ts.SyntaxKind.Parameter:
                return this.visitParameterDeclaration(node);
            case ts.SyntaxKind.PropertyDeclaration:
                return this.visitPropertyDeclaration(node);
            case ts.SyntaxKind.MethodDeclaration:
                return this.visitMethodDeclaration(node);
            case ts.SyntaxKind.SyntaxList:
                return this.visitSyntaxList(node);
            case ts.SyntaxKind.Decorator:
                return this.visitDecorator(node);
            default:
                return this.walkChildren(node);
        }
    };
    return BaseTransformWalker;
}());
exports.BaseTransformWalker = BaseTransformWalker;
//# sourceMappingURL=base-transform-walker.js.map