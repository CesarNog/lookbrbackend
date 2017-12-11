"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var text_based_loader_1 = require("./text-based-loader");
var transformer_based_loader_1 = require("./transformer-based-loader");
var useTransformerBased;
function useTransformerBasedLoader(value) {
    useTransformerBased = value;
}
exports.useTransformerBasedLoader = useTransformerBasedLoader;
function aotCleanLoader(source, sourceMap) {
    if (useTransformerBased === true) {
        return transformer_based_loader_1.aotCleanLoader.call(this, source, sourceMap);
    }
    else {
        return text_based_loader_1.aotCleanLoader.call(this, source, sourceMap);
    }
}
exports.default = aotCleanLoader;
//# sourceMappingURL=index.js.map