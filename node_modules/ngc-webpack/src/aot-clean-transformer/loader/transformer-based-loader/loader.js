"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var Path = require("path");
var ts = require("typescript");
var loaderUtils = require('loader-utils');
var index_1 = require("../../index");
var webpack_wrapper_1 = require("../../../webpack-wrapper");
var AOTMode;
var compilerOptions;
function init() {
    var plugin = webpack_wrapper_1.findPlugin(this._compilation);
    var options = loaderUtils.getOptions(this) || {};
    if (options.disable === false) {
        AOTMode = true;
    }
    else {
        AOTMode = false;
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
    compilerOptions = parsedConfig.options;
}
function aotCleanLoader(source, sourceMap) {
    var cb = this.async();
    var sourceFileName = this.resourcePath;
    if (AOTMode === false || sourceFileName.endsWith('ngfactory.ts')) {
        return cb(null, source, sourceMap);
    }
    else if (AOTMode !== true) {
        init.call(this);
        if (AOTMode === false) {
            return cb(null, source, sourceMap);
        }
    }
    var sourceFile = ts.createSourceFile(sourceFileName, source, ts.ScriptTarget.Latest, true, ts.ScriptKind.TS);
    var transformResults = ts.transform(sourceFile, [index_1.aotCleanupTransformer], compilerOptions);
    if (transformResults.diagnostics && transformResults.diagnostics.length >= 1) {
        var errors = diagnosticsToErrors(transformResults.diagnostics);
        if (errors.length === 1) {
            cb(errors[0]);
        }
        else {
            for (var _i = 0, errors_1 = errors; _i < errors_1.length; _i++) {
                var e = errors_1[_i];
                this.emitError(e.message);
                cb(new Error('NgcWebpack AotCleanupLoader: Multiple Errors'));
            }
        }
    }
    else {
        try {
            var printer = ts.createPrinter({ newLine: ts.NewLineKind.LineFeed });
            var result = printer.printFile(transformResults.transformed[0]);
            cb(null, result);
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