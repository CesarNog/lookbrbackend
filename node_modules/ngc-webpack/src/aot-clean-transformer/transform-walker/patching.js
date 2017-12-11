"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var ts = require("typescript");
var unpatch = undefined;
var patching;
(function (patching_1) {
    function unpathTypeScript() {
        if (unpatch) {
            unpatch();
        }
    }
    patching_1.unpathTypeScript = unpathTypeScript;
    function patchTypeScript() {
        if (unpatch) {
            return;
        }
        var toMonkeyPatch = ['nodeCanBeDecorated', 'nodeIsDecorated', 'childIsDecorated', 'nodeOrChildIsDecorated'];
        var originalPatched = toMonkeyPatch.reduce(function (obj, k) { obj[k] = ts[k]; return obj; }, {});
        var patching = {
            nodeCanBeDecorated: function (node) {
                return originalPatched['nodeCanBeDecorated'](node.parent ? node : node['original']);
            },
            nodeIsDecorated: function (node) {
                return node.decorators !== undefined
                    && patching.nodeCanBeDecorated(node);
            },
            childIsDecorated: function (node) {
                switch (node.kind) {
                    case ts.SyntaxKind.ClassDeclaration:
                        return ts['forEach'](node.members, patching.nodeOrChildIsDecorated);
                    case ts.SyntaxKind.MethodDeclaration:
                    case ts.SyntaxKind.SetAccessor:
                        return ts['forEach'](node.parameters, patching.nodeIsDecorated);
                }
            },
            nodeOrChildIsDecorated: function (node) {
                return patching.nodeIsDecorated(node) || patching.childIsDecorated(node);
            }
        };
        toMonkeyPatch.forEach(function (k) { return ts[k] = patching[k]; });
        unpatch = function () { return toMonkeyPatch.forEach(function (k) { return ts[k] = originalPatched[k]; }); };
    }
    patching_1.patchTypeScript = patchTypeScript;
    function patchTransformer(transformFactory) {
        return function (context) {
            var fn = transformFactory(context);
            return function (file) {
                var sourceFile = fn(file);
                if (sourceFile !== file) {
                    if (!sourceFile['symbol']) {
                        sourceFile['symbol'] = file['symbol'];
                    }
                    if (!sourceFile['locals']) {
                        sourceFile['locals'] = sourceFile['original']['locals'];
                    }
                }
                return sourceFile;
            };
        };
    }
    patching_1.patchTransformer = patchTransformer;
})(patching = exports.patching || (exports.patching = {}));
//# sourceMappingURL=patching.js.map