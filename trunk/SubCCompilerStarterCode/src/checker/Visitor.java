package checker;

import util.AST.Program;
import util.AST.Command.FunctionBody;
import util.AST.Command.FunctionDeclaration;
import util.AST.Command.VariableDeclaration;
import util.AST.Expression.BinaryExpression;
import util.AST.Expression.BooleanUnaryExpression;
import util.AST.Expression.IdentifierUnaryExpression;
import util.AST.Expression.NumberUnaryExpression;
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
import util.AST.Terminal.BooleanValue;
import util.AST.Terminal.Identifier;
import util.AST.Terminal.NumberValue;
import util.AST.Terminal.Operator;
import util.AST.Terminal.Type;

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
	public Object visitIdentifierUnaryExpression (IdentifierUnaryExpression idUnExp, Object arg);
	public Object visitNumberUnaryExpression (NumberUnaryExpression numUnExp, Object arg);
	public Object visitBooleanUnaryExpression (BooleanUnaryExpression booUnExp, Object arg);
	public Object visitIdentifier (Identifier id, Object arg);
	public Object visitOperator (Operator op, Object arg);
	public Object visitNumberValue (NumberValue number, Object arg);
	public Object visitBooleanValue (BooleanValue boo, Object arg);
	public Object visitType (Type type, Object arg);
	
	/*
	TODO: Visit destes:
			command?
			callstatement
			rhs
			expresion
			[Type]
	  
	TODO: Verificar se é necessário fazer visitType
	
	*/
}
