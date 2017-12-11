"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var ts = require("typescript");
var MockTypeChecker = (function () {
    function MockTypeChecker(options, virtFiles) {
        var _this = this;
        this.options = options;
        this.files = {};
        this.virtFileSystem = {};
        if (virtFiles) {
            Object.keys(virtFiles).forEach(function (fileName) { return _this.addVirtFile(fileName, virtFiles[fileName]); });
        }
        this.init();
    }
    Object.defineProperty(MockTypeChecker.prototype, "program", {
        get: function () {
            return this.services.getProgram();
        },
        enumerable: true,
        configurable: true
    });
    MockTypeChecker.prototype.hasFile = function (fileName) {
        return this.files.hasOwnProperty(fileName);
    };
    MockTypeChecker.prototype.addFile = function (fileName) {
        if (this.files.hasOwnProperty(fileName)) {
            this.files[fileName].version++;
        }
        else {
            this.files[fileName] = { version: 0 };
        }
    };
    MockTypeChecker.prototype.addVirtFile = function (fileName, content) {
        this.virtFileSystem[fileName] = content;
        this.addFile(fileName);
    };
    MockTypeChecker.prototype.getDiagnostics = function (fileName) {
        return this.services.getCompilerOptionsDiagnostics()
            .concat(this.services.getSyntacticDiagnostics(fileName))
            .concat(this.services.getSemanticDiagnostics(fileName));
    };
    MockTypeChecker.prototype.getSourceFile = function (fileName) {
        return this.program.getSourceFile(fileName);
    };
    MockTypeChecker.prototype.init = function () {
        var _this = this;
        this.servicesHost = {
            getScriptFileNames: function () { return Object.keys(_this.files); },
            getScriptVersion: function (fileName) { return _this.files[fileName] && _this.files[fileName].version.toString(); },
            getScriptSnapshot: function (fileName) {
                if (!_this.servicesHost.fileExists(fileName)) {
                    return undefined;
                }
                return ts.ScriptSnapshot.fromString(_this.servicesHost.readFile(fileName).toString());
            },
            getCurrentDirectory: function () { return ts.sys.getCurrentDirectory(); },
            getCompilationSettings: function () { return _this.options; },
            getDefaultLibFileName: function (options) { return ts.getDefaultLibFilePath(options); },
            fileExists: function (fileName) { return _this.virtFileSystem.hasOwnProperty(fileName) || ts.sys.fileExists(fileName); },
            readFile: function (fileName) { return _this.virtFileSystem.hasOwnProperty(fileName) ? _this.virtFileSystem[fileName] : ts.sys.readFile(fileName); },
            readDirectory: ts.sys.readDirectory,
        };
        this.services = ts.createLanguageService(this.servicesHost, ts.createDocumentRegistry());
    };
    return MockTypeChecker;
}());
exports.MockTypeChecker = MockTypeChecker;
// export class MockTypeChecker {
//
//   public program: ts.Program;
//
//   private virtFileSystem: {[name: string]: string };
//   private host: ts.CompilerHost;
//
//   constructor(public options: ts.CompilerOptions, virtFiles?: {[name: string]: string }) {
//     this.virtFileSystem = virtFiles
//       ? Object.assign({}, virtFiles)
//       : {}
//     ;
//     this.init();
//   }
//
//   hasFile(fileName: string): boolean {
//     return this.virtFileSystem.hasOwnProperty(fileName);
//   }
//
//   addFile(name: string, content: string) {
//     this.virtFileSystem[name] = content;
//     if (this.program) {
//       this.program = ts.createProgram([name], this.options, this.host, this.program);
//       this.program.emit(this.program.getSourceFile(name));
//     }
//   }
//
//   private init(): void {
//     const _host: ts.CompilerHost = ts.createCompilerHost(this.options);
//     this.host = Object.create(_host, {
//       writeFile: { value: (fileName, content) => {} },
//       getSourceFile: { value: (fileName: string, languageVersion: ts.ScriptTarget, onError?: (message: string) => void) => {
//         if (this.virtFileSystem.hasOwnProperty(fileName)) {
//           const sourceText = this.virtFileSystem[fileName];
//           return sourceText !== undefined
//             ? ts.createSourceFile(fileName, sourceText, languageVersion)
//             : undefined
//             ;
//         } else {
//           return _host.getSourceFile(fileName, languageVersion, onError);
//         }
//       }},
//       getSourceFileByPath: { value: (fileName: string, path: ts.Path, languageVersion: ts.ScriptTarget, onError?: (message: string) => void) => {
//         return _host.getSourceFileByPath(fileName, path, languageVersion, onError);
//       } },
//       fileExists: { value: (fileName: string) => this.virtFileSystem.hasOwnProperty(fileName) || _host.fileExists(fileName) },
//       readFile: { value: (fileName: string) => this.virtFileSystem.hasOwnProperty(fileName) ? this.virtFileSystem[fileName] : _host.readFile(fileName) }
//     });
//
//     this.program = ts.createProgram(Object.keys(this.virtFileSystem), this.options, this.host);
//   }
// }
//# sourceMappingURL=mock-type-checker.js.map