package checker;

import util.AST.Program;
import util.AST.Command.FunctionBody;
import util.AST.Command.FunctionDeclaration;
import util.AST.Command.VariableDeclaration;
import util.AST.Expression.BinaryExpression;
import util.AST.RHS.CallStatementRHS;
import util.AST.RHS.ExpressionRHS;
import util.AST.Statement.AssignStatement;
import util.AST.Statement.BreakStatement;
import util.AST.Statement.CallStatement;
import util.AST.Statement.ContinueStatement;
import util.AST.Statement.IfElseStatement;
import util.AST.Statement.PrintlnStatement;
import util.AST.Statement.ReturnStatement;
import util.AST.Statement.WhileStatement;

public interface Visitor {
	
	public Object visitProgram (Program prog, Object arg);
	public Object visitVariableDeclaration (VariableDeclaration decl, Object arg);
	public Object visitFunctionDeclaration (FunctionDeclaration decl, Object arg);
	public Object visitFunctionBody (FunctionBody fbody, Object arg);
	public Object visitCallStatement (CallStatement stat, Object arg);
	public Object visitAssignStatement (AssignStatement stat, Object arg);
	public Object visitIfElseStatement (IfElseStatement stat, Object arg);
	public Object visitWhileStatement (WhileStatement stat, Object arg);
	public Object visitReturnStatement (ReturnStatement stat, Object arg);
	public Object visitBreakStatement (BreakStatement stat, Object arg);
	public Object visitContinueStatement (ContinueStatement stat, Object arg);
	public Object visitPrintlnStatement (PrintlnStatement stat, Object arg);
	public Object visitExpressionRHS (ExpressionRHS expRHS, Object arg);
	public Object visitCallStatementRHS (CallStatementRHS callRHS, Object arg);
	public Object visitBinaryExpression (BinaryExpression byExp, Object arg);
	
	/*
	TODO: Visit destes:
	IdentifierUnaryExpression
	NumberUnaryExpression
	BooleanUnaryExpression
	Identifier
	Operator
	Number
	Boolean
	  
	TODO: Verificar se é necessário fazer visitType
	[Type]
	*/
}
