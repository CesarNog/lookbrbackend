import * as ts from 'typescript';
import { BaseTransformWalker, WalkerContext } from './base-transform-walker';
export declare class AotWalkerContext extends WalkerContext {
    scope: ts.SyntaxKind[];
    addScope(node: ts.Node): void;
    currentClass?: ts.ClassDeclaration;
    currentClassParam?: ts.ParameterDeclaration;
    currentClassProp?: ts.PropertyDeclaration;
    currentClassMethod?: ts.MethodDeclaration;
}
export declare class AotTransformWalker extends BaseTransformWalker<AotWalkerContext> {
    sourceFile: ts.SourceFile;
    context: ts.TransformationContext;
    angularImports: string[];
    private readonly mocker;
    constructor(sourceFile: ts.SourceFile, context: ts.TransformationContext, walkerContext?: WalkerContext);
    walk(): ts.Node;
    protected onBeforeVisitNode(node: ts.Node): void;
    protected onAfterVisitNode(node: ts.Node): void;
    protected visitClassDeclaration(node: ts.ClassDeclaration): any;
    private updateClassDeclaration(node, members);
    protected visitParameterDeclaration(node: ts.ParameterDeclaration): any;
    protected visitPropertyDeclaration(node: ts.PropertyDeclaration): ts.Node;
    protected visitMethodDeclaration(node: ts.MethodDeclaration): ts.Node;
    protected visitDecorator(node: ts.Decorator): ts.Decorator;
    private isAngularDecorator(node);
    private classCtorParamsToLiteralExpressions(classNode);
    private ctorParameterFromTypeReference(paramNode);
    private decoratorsAsObjectLiteral(decorators);
    private ctorParameName(paramNode);
}
