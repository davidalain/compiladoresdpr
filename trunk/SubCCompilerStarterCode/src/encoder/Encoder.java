package encoder;

import java.util.ArrayList;

import codeGeneration.Instruction;

import util.AST.Program;
import util.AST.Command.Command;
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
import checker.SemanticException;
import checker.Visitor;

public class Encoder implements Visitor{

	ArrayList<Instruction> instrucoes = new ArrayList<Instruction>();
	
	public void emit(byte op , byte r , byte n , short d) {
		this.instrucoes.add(new Instruction(op, r, n, d));
	}
	
	private static short valuation (String intLit){
		return 0;
	}
	
	private void encodeFetch (Identifier id){
		
	}
	
	private void encodeAssign (Identifier id){
		
	}

	
	public void encode(Program prog) throws SemanticException{
		prog.visit(this, null);
	}
	
	public Object visitProgram(Program prog, Object arg)
			throws SemanticException {

		ArrayList<Command> comandos = prog.getCommands();
		for (Command com : comandos){
			com.visit(this, arg);
		}
		
		this.emit(Instruction.HALTop,(byte)0,(byte)0,(short)0);
		return null;
	}

	public Object visitVariableDeclaration(VariableDeclaration decl, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitFunctionDeclaration(FunctionDeclaration decl, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitFunctionBody(FunctionBody fbody, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitCallStatement(CallStatement stat, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitAssignStatement(AssignStatement stat, Object arg)
			throws SemanticException {
		
		stat.getRightHandStatement().visit(this, arg);
		this.encodeAssign(stat.getVariableName());
		
		return null;
	}

	public Object visitIfElseStatement(IfElseStatement stat, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitWhileStatement(WhileStatement stat, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitReturnStatement(ReturnStatement stat, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitBreakStatement(BreakStatement stat, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitContinueStatement(ContinueStatement stat, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitPrintlnStatement(PrintlnStatement stat, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitExpressionRHS(ExpressionRHS expRHS, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitCallStatementRHS(CallStatementRHS callRHS, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitBinaryExpression(BinaryExpression byExp, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitIdentifierUnaryExpression(
			IdentifierUnaryExpression idUnExp, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitNumberUnaryExpression(NumberUnaryExpression numUnExp,
			Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitBooleanUnaryExpression(BooleanUnaryExpression booUnExp,
			Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitIdentifier(Identifier id, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitOperator(Operator op, Object arg)
			throws SemanticException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitNumberValue(NumberValue number, Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitBooleanValue(BooleanValue boo, Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitType(Type type, Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

}
