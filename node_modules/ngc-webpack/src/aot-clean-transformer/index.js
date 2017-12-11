"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var transform_walker_1 = require("./transform-walker");
var transform_walker_2 = require("./transform-walker");
exports.patching = transform_walker_2.patching;
function aotCleanupTransformer(context) {
    return function (file) {
        var walker = new transform_walker_1.AotTransformWalker(file, context);
        return walker.walk();
    };
}
exports.aotCleanupTransformer = aotCleanupTransformer;
//# sourceMappingURL=index.js.map