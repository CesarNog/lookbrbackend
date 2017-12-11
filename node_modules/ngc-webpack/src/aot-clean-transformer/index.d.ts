import * as ts from 'typescript';
export { patching } from './transform-walker';
export declare function aotCleanupTransformer(context: ts.TransformationContext): (file: ts.SourceFile) => any;
