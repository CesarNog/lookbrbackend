import * as ts from 'typescript';
export declare function angularImportsFromNode(node: ts.ImportDeclaration, _sourceFile: ts.SourceFile): string[];
/**
 * Find the matching twin node for a node where both root and node have a twin SourceFile.
 * Twin SourceFiles are 2 instances of a the same source file.
 *
 * @param root
 * @param node
 * @return {any}
 */
export declare function findRemoteMatch<T extends ts.Node>(root: ts.Node, node: T): T | undefined;
