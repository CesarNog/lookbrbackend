import * as ts from 'typescript';
export declare class WalkerContext {
}
export declare class BaseTransformWalker<T extends WalkerContext = WalkerContext> {
    sourceFile: ts.SourceFile;
    context: ts.TransformationContext;
    readonly walkerContext: T;
    constructor(sourceFile: ts.SourceFile, context: ts.TransformationContext, walkerContext?: WalkerContext);
    walk(): ts.Node;
    protected visitClassDeclaration(node: ts.ClassDeclaration): ts.Node;
    protected visitConstructorDeclaration(node: ts.ConstructorDeclaration): ts.Node;
    protected visitParameterDeclaration(node: ts.ParameterDeclaration): ts.Node;
    protected visitPropertyDeclaration(node: ts.PropertyDeclaration): ts.Node;
    protected visitMethodDeclaration(node: ts.MethodDeclaration): ts.Node;
    protected visitSyntaxList(node: ts.SyntaxList): ts.Node;
    protected visitDecorator(node: ts.Decorator): ts.Node;
    protected visitNode(node: ts.Node): ts.Node;
    protected filterNodes<T extends ts.Node>(nodes: ts.NodeArray<T>, test?: (node: T) => boolean): ts.NodeArray<T> | undefined;
    protected visitNodes<T extends ts.Node>(nodes: ts.NodeArray<T>, test?: (node: T) => boolean): ts.NodeArray<T> | undefined;
    protected walkChildren(node: ts.Node): ts.Node;
    protected onBeforeVisitNode(node: ts.Node): void;
    protected onAfterVisitNode(node: ts.Node): void;
    protected findAstNodes(node: ts.Node | null, kind: ts.SyntaxKind, recursive?: boolean, max?: number): ts.Node[];
    findFirstAstNode(node: ts.Node | null, kind: ts.SyntaxKind): ts.Node | null;
    private _visitNode(node);
}
