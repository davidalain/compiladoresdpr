package encoder;

import java.util.ArrayList;

import compiler.Properties;

import util.Arquivo;
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

public class Encoder implements Visitor {

	private ArrayList<Instruction> codigo;
	private int posicaoInstrucaoSessaoData;
	private int indiceConstante;
	private Arquivo arquivo;

	public Encoder (){
		this.codigo = new ArrayList<Instruction>();
		this.posicaoInstrucaoSessaoData = 1;
		this.indiceConstante = 1;
		this.arquivo = new Arquivo(Properties.sourceCodeLocation,"ArquivoSaida.asm");
	}

	private void emit(int tipo, String op1, String op2,String op3) {
		this.codigo.add(new Instruction(tipo, op1, op2, op3));
	}

	private void emit(int tipo, String op1) {
		this.codigo.add(new Instruction(tipo, op1, null, null));
	}

	public void encode(Program prog) throws SemanticException{
		prog.visit(this, null);
		this.gravarArquivo();
	}

	private void gravarArquivo() {
		for (Instruction ins : codigo){
			String instrucaoAtual = ins.toString();
			this.arquivo.println(instrucaoAtual);
		}
		this.arquivo.close();
	}


	public Object visitProgram(Program prog, Object arg)
	throws SemanticException {

		//this.emit(InstructionType.EXTERN,InstructionType.PRINTF,null,null);
		//Comentado, se não tiver println, não pode adicionar o extern

		this.emit(InstructionType.SECTION,InstructionType.DATA);
		this.emit(InstructionType.SECTION,InstructionType.TEXT);

		ArrayList<Command> comandos = prog.getCommands();
		for (Command com : comandos){
			//se for declaracao de variavel global, passa o prog pra ele saber
			com.visit(this, prog);
		}
		return null;
	}


	public Object visitVariableDeclaration(VariableDeclaration decl, Object arg)
	throws SemanticException {
		//se for variavel global, jogar ela na sessão data;
		if (arg instanceof Program){
			Instruction variavelGlobal = new Instruction(InstructionType.VARIAVEL_GLOBAL, //variavel global 
					decl.getIdentifier().getSpelling(), //nomeVariavel
					decl.getType().getSpelling(), //TipoVariavel
					null); 

			this.codigo.add(this.posicaoInstrucaoSessaoData, variavelGlobal);
			this.posicaoInstrucaoSessaoData++;
		}else{
			//Variável não é global

			this.encodeFetch(decl.getIdentifier());

		}

		return null;
	}

	public Object visitFunctionDeclaration(FunctionDeclaration decl, Object arg)
	throws SemanticException {

		this.emit(InstructionType.FUNCAO_LABEL, decl.getFunctionName().getSpelling());

		//TODO: Criar instruções dos parametros
		FunctionBody fb = decl.getFunctionBody();
		if (fb != null){
			//visitar o function body
			fb.visit(this, decl);
		}
		return null;
	}

	public Object visitFunctionBody(FunctionBody fbody, Object arg)
	throws SemanticException {
		
		ArrayList<VariableDeclaration> variaveis = fbody.getVariables();
		for (VariableDeclaration vd : variaveis){
			vd.visit(this, arg);
		}
		
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

	private void encodeAssign(Identifier variableName) {
	// TODO Auto-generated method stub

	}

	private void encodeFetch(Identifier variableName){

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

		boolean encontrouExternPrintln = false;
		if(this.codigo.size() > 0){
			Instruction inst = this.codigo.get(0); 
			if(	inst.getTipo() == InstructionType.EXTERN &&
					inst.getOp1().equals(InstructionType.PRINTF))
			{
				encontrouExternPrintln = true;
			}
		}

		if(!encontrouExternPrintln){
			Instruction instrucao = new Instruction(InstructionType.EXTERN,InstructionType.PRINTF,null,null);
			this.codigo.add(0, instrucao);

			this.posicaoInstrucaoSessaoData = 2;
		}
		//TODO: Código de chamar o println

		//O visit coloca na pilha o valor a ser mostrado
		stat.getVariableName().visit(this, arg);

		//O tipo é necessário para saber qual instrução chamar,
		//já que as instruções de inteiro são distintas das de ponto flutuante
		String tipo = ((VariableDeclaration)stat.getVariableName().getNoDeclaracao()).getType().getSpelling();
		this.emit(InstructionType.FUNCAO_LABEL, InstructionType.PRINTF, tipo, null);

		//Desempilha os dois ultimos valores da pilha que foram o valor e formato do printf
		this.emit(InstructionType.DESEMPILHAR, null);
		this.emit(InstructionType.DESEMPILHAR, null);

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
