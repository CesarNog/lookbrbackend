import * as ts from 'typescript';
export declare namespace patching {
    function unpathTypeScript(): void;
    function patchTypeScript(): void;
    function patchTransformer(transformFactory: ts.TransformerFactory<any>): (context: ts.TransformationContext) => (file: ts.SourceFile) => any;
}
