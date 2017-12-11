"use strict";
function __export(m) {
    for (var p in m) if (!exports.hasOwnProperty(p)) exports[p] = m[p];
}
Object.defineProperty(exports, "__esModule", { value: true });
__export(require("./src/plugin"));
var aot_clean_transformer_1 = require("./src/aot-clean-transformer");
exports.aotCleanupTransformer = aot_clean_transformer_1.aotCleanupTransformer;
exports.patching = aot_clean_transformer_1.patching;
var loader_1 = require("./src/aot-clean-transformer/loader");
exports.useTransformerBasedLoader = loader_1.useTransformerBasedLoader;
var loader_2 = require("./src/aot-clean-transformer/loader");
exports.default = loader_2.default;
//# sourceMappingURL=index.js.map