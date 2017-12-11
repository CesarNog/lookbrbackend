import * as ts from 'typescript';
export declare class MockTypeChecker {
    options: ts.CompilerOptions;
    services: ts.LanguageService;
    readonly program: ts.Program;
    private files;
    private virtFileSystem;
    private servicesHost;
    constructor(options: ts.CompilerOptions, virtFiles?: {
        [name: string]: string;
    });
    hasFile(fileName: string): boolean;
    addFile(fileName: string): void;
    addVirtFile(fileName: string, content: string): void;
    getDiagnostics(fileName: string): ts.Diagnostic[];
    getSourceFile(fileName: string): ts.SourceFile;
    private init();
}
