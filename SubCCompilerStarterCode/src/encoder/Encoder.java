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
import util.AST.Statement.Statement;
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
		Frame frameGlobal = new Frame(0, 0);
		prog.visit(this, frameGlobal);
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

		this.emit(InstructionType.SECTION,InstructionType.DATA);
		this.emit(InstructionType.SECTION,InstructionType.TEXT);

		ArrayList<Command> comandos = prog.getCommands();
		for (Command com : comandos){
			//se for declaracao de variavel global, passa o prog pra ele saber
			com.visit(this, arg);
		}
		return null;
	}


	public Object visitVariableDeclaration(VariableDeclaration decl, Object arg)
	throws SemanticException {

		Frame frame = (Frame) arg;
		Identifier id = decl.getIdentifier();

		//variaveis globais
		if (frame.getLevel() == 0){
			Instruction variavelGlobal = new Instruction(InstructionType.VARIAVEL_GLOBAL, //variavel global 
					decl.getIdentifier().getSpelling(), //nomeVariavel
					decl.getType().getSpelling(), //TipoVariavel
					null); 

			this.codigo.add(this.posicaoInstrucaoSessaoData, variavelGlobal);
			this.posicaoInstrucaoSessaoData++;
			int tamanho = 4;
			String valor = "0";
			if (decl.getType().getSpelling().equals("double")){
				tamanho = 8;
				valor = "0.0";
			}
			//FIXME: caso voces tenham necessidade de aumentar o frame, faço-o aqui
			decl.entity = new KnownValue(tamanho,valor,decl.getIdentifier().getSpelling());

		}
		//variavel local
		else{
			int tamanho = 4;
			if (decl.getType().equals("double")){
				tamanho = 8;
			}
			this.emit(InstructionType.SUB, InstructionType.ESP, "" + tamanho, null);
			decl.entity = new KnownAddress(tamanho,frame.getSize());
			frame.setSize(frame.getSize() + tamanho);
		}

		return null;
	}

	public Object visitFunctionDeclaration(FunctionDeclaration decl, Object arg)
	throws SemanticException {

		Frame frame = new Frame(1, 0);
		this.emit(InstructionType.FUNCAO_LABEL, decl.getFunctionName().getSpelling());
		this.emit(InstructionType.PUSH, InstructionType.EBP);
		this.emit(InstructionType.MOV, InstructionType.EBP, InstructionType.ESP, null);
		decl.entity = new KnownRoutine(decl.getFunctionName().getSpelling());
		//TODO: Criar instruções dos parametros
		//decl.getParameters();
		for (VariableDeclaration vd : decl.getParameters()){
			//	vd.entity = 
		}
		FunctionBody fb = decl.getFunctionBody();
		if (fb != null){
			//visitar o function body
			fb.visit(this, frame);
		}
		return null;
	}

	public Object visitFunctionBody(FunctionBody fbody, Object arg)
	throws SemanticException {

		ArrayList<VariableDeclaration> variaveis = fbody.getVariables();
		for (VariableDeclaration vd : variaveis){
			vd.visit(this, arg);
		}
		ArrayList<Statement> statements = fbody.getStatements();
		for (Statement stat : statements){
			stat.visit(this, arg);
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

	private void encodeFetch(Identifier id, Frame frame) throws SemanticException{
		RuntimeEntity entity = (RuntimeEntity) id.visit(this, null)	;


	}

	public Object visitIfElseStatement(IfElseStatement stat, Object arg)
	throws SemanticException {
		// TODO Auto-generated method stub
		stat.getCondition().visit(this, arg);
	
		ArrayList<Statement> stats = stat.getIfStatements();
	
		
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
		this.emit(InstructionType.POP, null);
		this.emit(InstructionType.POP, null);

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
		byExp.getLeftExpression().visit(this, arg);
		byExp.getRightExpression().visit(this, arg);
		byExp.getOperator().visit(this, byExp.getType());

		return null;
	}

	public Object visitIdentifierUnaryExpression(
			IdentifierUnaryExpression idUnExp, Object arg)
	throws SemanticException {

		idUnExp.getVariableName().visit(this, arg);
		
		return null;
	}

	public Object visitNumberUnaryExpression(NumberUnaryExpression numUnExp,
			Object arg) {
		numUnExp.visit(this, numUnExp.getType());
		return null;
	}

	public Object visitBooleanUnaryExpression(BooleanUnaryExpression booUnExp,
			Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitIdentifier(Identifier id, Object arg)
	throws SemanticException {

		return id.getNoDeclaracao().entity;
	}

	public Object visitOperator(Operator op, Object arg)
	throws SemanticException {

		Type tipo = (Type) arg;
		Operator operador = op;
		if(operador.equals("+")){
			if (tipo.getSpelling().equals("double")){
				this.emit(InstructionType.ADD_FLOAT, InstructionType.ST1);
			}
			else {
				this.emit(InstructionType.POP, InstructionType.EBX);
				this.emit(InstructionType.POP, InstructionType.EAX);
				this.emit(InstructionType.ADD,InstructionType.EAX,InstructionType.EBX,null);
			}
		}
		if(operador.equals("-")){
			if (tipo.getSpelling().equals("double")){
				this.emit(InstructionType.SUB_FLOAT, InstructionType.ST1);
			}
			else {
				this.emit(InstructionType.POP, InstructionType.EBX);
				this.emit(InstructionType.POP, InstructionType.EAX);
				this.emit(InstructionType.SUB,InstructionType.EAX,InstructionType.EBX,null);
			}
		}
		if(operador.equals("*")){
			if (tipo.getSpelling().equals("double")){
				this.emit(InstructionType.MULT_FLOAT, InstructionType.ST1);
			}
			else {
				this.emit(InstructionType.POP, InstructionType.EBX);
				this.emit(InstructionType.POP, InstructionType.EAX);
				this.emit(InstructionType.MULT,InstructionType.EAX,InstructionType.EBX,null);
			}

		}
		if(operador.equals("/")){
			if (tipo.getSpelling().equals("double")){
				this.emit(InstructionType.DIV_FLOAT, InstructionType.ST1);
			}
			else {
				this.emit(InstructionType.POP, InstructionType.EBX);
				this.emit(InstructionType.POP, InstructionType.EAX);
				this.emit(InstructionType.DIV,InstructionType.EAX,InstructionType.EBX,null);
			}
		}

		if(operador.equals("==")){
			this.emit(InstructionType.POP, InstructionType.EBX);
			this.emit(InstructionType.POP, InstructionType.EAX);
			this.emit(InstructionType.CMP, InstructionType.EAX, InstructionType.EBX, null);
		}

		if(operador.equals("!=")){
			this.emit(InstructionType.POP, InstructionType.EBX);
			this.emit(InstructionType.POP, InstructionType.EAX);
			this.emit(InstructionType.CMP, InstructionType.EAX, InstructionType.EBX, null);
		}
		if(operador.equals("<")){
			this.emit(InstructionType.POP, InstructionType.EBX);
			this.emit(InstructionType.POP, InstructionType.EAX);
			this.emit(InstructionType.CMP, InstructionType.EAX, InstructionType.EBX, null);
		}

		if(operador.equals(">")){
			this.emit(InstructionType.POP, InstructionType.EBX);
			this.emit(InstructionType.POP, InstructionType.EAX);
			this.emit(InstructionType.CMP, InstructionType.EAX, InstructionType.EBX, null);
		}
		if(operador.equals("<=")){
			this.emit(InstructionType.POP, InstructionType.EBX);
			this.emit(InstructionType.POP, InstructionType.EAX);
			this.emit(InstructionType.CMP, InstructionType.EAX, InstructionType.EBX, null);
		}

		if(operador.equals(">=")){
			this.emit(InstructionType.POP, InstructionType.EBX);
			this.emit(InstructionType.POP, InstructionType.EAX);
			this.emit(InstructionType.CMP, InstructionType.EAX, InstructionType.EBX, null);
		}
		return null;


	}

	public Object visitNumberValue(NumberValue number, Object arg) {

		Type tipo = (Type) arg;

		if (tipo.getSpelling().equals("int")){
			this.emit(InstructionType.PUSH, InstructionType.DWORD, number.getSpelling(), null);
		}
		else {
			//colocando a constante na section DATA
			String constante = "const" + this.indiceConstante;
			Instruction instrucao = new Instruction(InstructionType.CONSTANTE_DOUBLE, constante, number.getSpelling(), null);

			this.codigo.add(this.posicaoInstrucaoSessaoData, instrucao);
			this.emit(InstructionType.FLD, InstructionType.QWORD, constante, null);

			this.posicaoInstrucaoSessaoData++;
			this.indiceConstante++;

		}

		return null;
	}

	public Object visitBooleanValue(BooleanValue boo, Object arg) {
		if (boo.getSpelling().equals("true")){
			this.emit(InstructionType.PUSH, InstructionType.DWORD, "1",null);
		}
		else {
			this.emit(InstructionType.PUSH, InstructionType.DWORD, "0",null);
		}

		return null;
	}

	public Object visitType(Type type, Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

}
