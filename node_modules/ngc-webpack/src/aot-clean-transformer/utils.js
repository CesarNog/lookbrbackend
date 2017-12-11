"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var ts = require("typescript");
function angularImportsFromNode(node, _sourceFile) {
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
exports.angularImportsFromNode = angularImportsFromNode;
/**
 * Find the matching twin node for a node where both root and node have a twin SourceFile.
 * Twin SourceFiles are 2 instances of a the same source file.
 *
 * @param root
 * @param node
 * @return {any}
 */
function findRemoteMatch(root, node) {
    if (root.kind === node.kind && root.getStart() === node.getStart()) {
        return root;
    }
    else if (node.getStart() >= root.getStart() && node.getEnd() <= root.getEnd()) {
        for (var _i = 0, _a = root.getChildren(root.getSourceFile()); _i < _a.length; _i++) {
            var child = _a[_i];
            var result = findRemoteMatch(child, node);
            if (result) {
                return result;
            }
        }
    }
}
exports.findRemoteMatch = findRemoteMatch;
//# sourceMappingURL=utils.js.map