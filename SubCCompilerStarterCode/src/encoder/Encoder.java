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
import util.AST.Expression.Expression;
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
	private int indiceElse;
	private int indiceEndIf;
	private int indiceWhile;
	private Arquivo arquivo;

	public Encoder (){
		this.codigo = new ArrayList<Instruction>();
		this.posicaoInstrucaoSessaoData = 1;
		this.indiceConstante = 1;
		this.indiceElse = 1;
		this.indiceEndIf = 1;
		this.indiceWhile = 1;
		
		this.arquivo = new Arquivo(Properties.sourceCodeLocation,"ArquivoSaida.asm");
	}

	private void emit(int tipo, String op1, String op2,String op3) {
		this.codigo.add(new Instruction(tipo, op1, op2, op3));
	}
	private void emit(int tipo, String op1, String op2,String op3,String sinal) {
		this.codigo.add(new Instruction(tipo, op1, op2, op3,sinal));
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
			decl.entity = new KnownValue(tamanho,valor,decl.getIdentifier().getSpelling());

		}
		//variavel local
		else{
			int tamanho = 4;
			if (decl.getType().getSpelling().equals("double")){
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
		if(decl.getFunctionName().getSpelling().equals("main")){
			this.emit(InstructionType.FUNCAO_LABEL, InstructionType.WINMAIN);
		}else{
			this.emit(InstructionType.FUNCAO_LABEL, decl.getFunctionName().getSpelling());
		}

		this.emit(InstructionType.PUSH, InstructionType.EBP);
		this.emit(InstructionType.MOV, InstructionType.EBP, InstructionType.ESP, null);
		decl.entity = new KnownRoutine(decl.getFunctionName().getSpelling());
		ArrayList<VariableDeclaration> variaveis = decl.getParameters();
		if (variaveis != null){
			int endereco = 4;
			for (VariableDeclaration vd : variaveis){
				int tamanho = 4;
				if (vd.getType().getSpelling().equals("double")){
					tamanho = 8;
				}
				vd.entity = new KnownAddress(tamanho, endereco, true);
				endereco += tamanho;
				//		frame.setSize(frame.getSize() + tamanho);
			}
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
		
		Frame frame;
//		ArrayList<Object> argTemp = new ArrayList<Object>();
		if (arg instanceof ArrayList){
			frame = (Frame) ((ArrayList<Object>)arg).get(0);
		}
		else {
			frame = (Frame) arg;
		}
		
		ArrayList<Identifier> argumentos = stat.getArguments();
		int qtdParametros = 0;
		RuntimeEntity entity = null;
		if (argumentos != null){
			for (Identifier id : argumentos){
				entity = (RuntimeEntity) id.visit(this, arg);
				this.encodeFetch(id, frame);
				qtdParametros += entity.getSize();
			}
		}
		this.emit(InstructionType.CALL, "_" + stat.getFunctionName().getSpelling());
		if (qtdParametros>0){
			this.emit(InstructionType.ADD, InstructionType.ESP,"" + qtdParametros,null);
		}
		
		
		return null;
	}

	public Object visitAssignStatement(AssignStatement stat, Object arg)
	throws SemanticException {

		Frame frame;

		ArrayList<Object> argTemp = new ArrayList<Object>();
		if (arg instanceof ArrayList){
			frame = (Frame) ((ArrayList<Object>)arg).get(0);
		}
		else {
			frame = (Frame) arg;
		}

		stat.getRightHandStatement().visit(this, arg);

		this.encodeAssign(stat.getVariableName(),frame);

		return null;
	}

	private void encodeAssign(Identifier variableName, Frame frame) throws SemanticException {

		RuntimeEntity entity = (RuntimeEntity) variableName.visit(this, null);

		//variavel global
		if (entity instanceof KnownValue){
			//se a variavel é inteira ou bool
			if (entity.getSize() == 4){
				this.emit(InstructionType.POP,InstructionType.DWORD,((KnownValue)entity).getLabel(),null);
			}
			else {
				this.emit(InstructionType.POP_FLOAT,InstructionType.QWORD,((KnownValue)entity).getLabel(),null);
			}

		}
		//variavel local
		else if (entity instanceof KnownAddress){
			//é variavel local
			if (!((KnownAddress) entity).isParametro()){
				if (entity.getSize() == 4){
					int deslocamento = frame.getSize() - ((KnownAddress)entity).getEndereco();
					this.emit(InstructionType.POP, InstructionType.DWORD, InstructionType.EBP, "" + deslocamento, "-");
				}
				else{
					int deslocamento = frame.getSize() - ((KnownAddress)entity).getEndereco();
					this.emit(InstructionType.POP_FLOAT, InstructionType.QWORD, InstructionType.EBP, "" + deslocamento, "-");
				}
			}
			//ele é parametro
			else {
				if (entity.getSize() == 4){
					int deslocamento = ((KnownAddress)entity).getSize() + ((KnownAddress)entity).getEndereco();
					this.emit(InstructionType.POP, InstructionType.DWORD, InstructionType.EBP, "" + deslocamento, "+");
				}
				else{
					int deslocamento = ((KnownAddress)entity).getSize() + ((KnownAddress)entity).getEndereco();
					this.emit(InstructionType.POP_FLOAT, InstructionType.QWORD, InstructionType.EBP, "" + deslocamento, "+");
				}

			}

		}


	}

	private void encodeFetch(Identifier id, Frame frame) throws SemanticException{
		RuntimeEntity entity = (RuntimeEntity) id.visit(this, null);

		//variavel global
		if (entity instanceof KnownValue){
			//se a variavel é inteira ou bool
			if (entity.getSize() == 4){
				this.emit(InstructionType.PUSH,InstructionType.DWORD,((KnownValue)entity).getLabel(),null);
			}
			else {
				this.emit(InstructionType.PUSH_FLOAT,InstructionType.QWORD,((KnownValue)entity).getLabel(),null);
			}

		}
		//variavel local
		else if (entity instanceof KnownAddress){
			if (!((KnownAddress) entity).isParametro()){
				if (entity.getSize() == 4){
					int deslocamento = frame.getSize() - ((KnownAddress)entity).getEndereco();
					this.emit(InstructionType.PUSH, InstructionType.DWORD, InstructionType.EBP, "" + deslocamento, "-");
				}
				else {
					int deslocamento = frame.getSize() - ((KnownAddress)entity).getEndereco();
					this.emit(InstructionType.PUSH_FLOAT, InstructionType.QWORD, InstructionType.EBP, "" + deslocamento, "-");
				}
			}
			else {
				if (entity.getSize() == 4){
					int deslocamento = ((KnownAddress)entity).getSize() + ((KnownAddress)entity).getEndereco();
					this.emit(InstructionType.PUSH, InstructionType.DWORD, InstructionType.EBP, "" + deslocamento, "+");
				}
				else{
					int deslocamento = ((KnownAddress)entity).getSize() + ((KnownAddress)entity).getEndereco();
					this.emit(InstructionType.PUSH_FLOAT, InstructionType.QWORD, InstructionType.EBP, "" + deslocamento, "+");
				}

			}

		}

	}

	public Object visitIfElseStatement(IfElseStatement stat, Object arg)
	throws SemanticException {
		
		ArrayList<Object> argTemp = new ArrayList<Object>();
		if (!(arg instanceof ArrayList)){
			argTemp.add(arg);
		}
		else {
			argTemp = (ArrayList<Object>) arg;
		}
		
		Expression condicao = stat.getCondition();
		Integer desvio = (Integer) condicao.visit(this, argTemp);
		String labelElse = "else_" + this.indiceElse;
		String labelFimIfElse = "fim_if_else" + this.indiceEndIf;
		this.indiceElse++;
		this.indiceEndIf++;

		if (desvio == InstructionType.BOOLEANO){
			this.emit(InstructionType.PUSH, InstructionType.DWORD, "1", null);
			this.emit(InstructionType.POP, InstructionType.EBX);
			this.emit(InstructionType.POP, InstructionType.EAX);
			this.emit(InstructionType.CMP, InstructionType.EAX, InstructionType.EBX, null);
			this.emit(InstructionType.JNE,  labelElse);
		}
		else {
			this.emit(desvio, labelElse);
		}
		
		ArrayList<Statement> ifStats = stat.getIfStatements();
		if (ifStats != null){
			for (Statement ifStat : ifStats){
				ifStat.visit(this, argTemp);
			}
		}

		this.emit(InstructionType.JUMP, labelFimIfElse);
		this.emit(InstructionType.LABEL, labelElse);

		ArrayList<Statement> elseStats = stat.getElseStatements();
		if (elseStats != null){
			for (Statement elseStat : elseStats){
				elseStat.visit(this, argTemp);
			}
		}
		this.emit(InstructionType.LABEL, labelFimIfElse);
		
		return null;
	}

	public Object visitWhileStatement(WhileStatement stat, Object arg)
	throws SemanticException {
		
		ArrayList<Object> argTemp = new ArrayList<Object>();
		if (!(arg instanceof ArrayList)){
			argTemp.add(arg);
		}
		else {
			argTemp = (ArrayList<Object>) arg;
		}
		String labelWhile = "while_" + this.indiceWhile + "_begin";
		String fimWhile = "while_" + this.indiceWhile + "_end";
		argTemp.add(labelWhile);
		argTemp.add(fimWhile);
		
		this.indiceWhile++;
		this.emit(InstructionType.LABEL, labelWhile);
		
		Integer desvio = (Integer) stat.getCondition().visit(this, argTemp);
		if (desvio == InstructionType.BOOLEANO){
			this.emit(InstructionType.PUSH, InstructionType.DWORD, "1", null);
			this.emit(InstructionType.POP, InstructionType.EBX);
			this.emit(InstructionType.POP, InstructionType.EAX);
			this.emit(InstructionType.CMP, InstructionType.EAX, InstructionType.EBX, null);
			this.emit(InstructionType.JNE,  fimWhile);
		}
		else {
			this.emit(desvio, fimWhile);
		}
		ArrayList<Statement> whileStats = stat.getStatements();
		if (whileStats != null){
			for (Statement whileStat : whileStats){
					whileStat.visit(this, argTemp);
			}
		}
		
		argTemp.remove(labelWhile);
		argTemp.remove(fimWhile);
		
		this.emit(InstructionType.JUMP, labelWhile);
		this.emit(InstructionType.LABEL, fimWhile);
		return null;
	}

	public Object visitReturnStatement(ReturnStatement stat, Object arg)
	throws SemanticException {
		ArrayList<Object> argTemp = new ArrayList<Object>();
		if (!(arg instanceof ArrayList)){
			argTemp.add(arg);
		}
		else {
			argTemp = (ArrayList<Object>) arg;
		}

		
		Expression exp = (Expression) stat.getReturnExpression();
		if (exp != null){
			exp.visit(this, argTemp);
			this.emit(InstructionType.POP, InstructionType.EAX);
		}
		this.emit(InstructionType.MOV, InstructionType.ESP, InstructionType.EBP,null);
		this.emit(InstructionType.POP, InstructionType.EBP);
		this.emit(InstructionType.RET, null);
		return null;
	}

	@SuppressWarnings("unchecked")
	public Object visitBreakStatement(BreakStatement stat, Object arg)
	throws SemanticException {
		String fimWhile;
		int posicao ;
		if (arg instanceof ArrayList){
			posicao = ((ArrayList<Object>)arg).size();
			fimWhile = (String) ( (ArrayList<Object>) arg).get(posicao - 1);
			
		}
		else {
			fimWhile = (String) arg;
		}
		this.emit(InstructionType.JUMP, fimWhile);
		return null;
	}

	public Object visitContinueStatement(ContinueStatement stat, Object arg)
	throws SemanticException {
		String comecoWhile;
		if (arg instanceof ArrayList){
			int posicao = ((ArrayList<Object>)arg).size();
			comecoWhile = (String) ( (ArrayList<Object>) arg).get(posicao - 2);
		}
		else {
			comecoWhile = (String) arg;
		}
		
		
		this.emit(InstructionType.JUMP, comecoWhile);
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
		expRHS.getExpression().visit(this, arg);
		return null;
	}

	public Object visitCallStatementRHS(CallStatementRHS callRHS, Object arg)
	throws SemanticException {
		// TODO Auto-generated method stub
		callRHS.getFunctionCall().visit(this, arg);
		this.emit(InstructionType.PUSH, InstructionType.EAX);
		return null;
	}

	public Object visitBinaryExpression(BinaryExpression byExp, Object arg)
	throws SemanticException {
		byExp.getLeftExpression().visit(this, arg);
		byExp.getRightExpression().visit(this, arg);
		Integer desvio  = (Integer) byExp.getOperator().visit(this, byExp.getType());

		return desvio;
	}

	public Object visitIdentifierUnaryExpression(
			IdentifierUnaryExpression idUnExp, Object arg)
	throws SemanticException {
		Frame frame;
		if (arg instanceof ArrayList){
			frame = (Frame) ((ArrayList<Object>)arg).get(0);
		}
		else {
			frame = (Frame) arg;
		}
		this.encodeFetch(idUnExp.getVariableName(), frame);
		idUnExp.getVariableName().visit(this, arg);

		return null;
	}

	public Object visitNumberUnaryExpression(NumberUnaryExpression numUnExp,
			Object arg) {
		numUnExp.getNumberValue().visit(this, numUnExp.getType());
		return null;
	}

	public Object visitBooleanUnaryExpression(BooleanUnaryExpression booUnExp,
			Object arg) {

		return booUnExp.getBooleanValue().visit(this, arg);
	}

	public Object visitIdentifier(Identifier id, Object arg)
	throws SemanticException {

		return id.getNoDeclaracao().entity;
	}

	public Object visitOperator(Operator op, Object arg)
	throws SemanticException {
		
		Integer retorno = null;
		Type tipo = (Type) arg;
		String operador = op.getSpelling();
		if(operador.equals("+")){
			if (tipo.getSpelling().equals("double")){
				this.emit(InstructionType.ADD_FLOAT, InstructionType.ST1);
			}
			else {
				this.emit(InstructionType.POP, InstructionType.EBX);
				this.emit(InstructionType.POP, InstructionType.EAX);
				this.emit(InstructionType.ADD,InstructionType.EAX,InstructionType.EBX,null);
			}
			this.emit(InstructionType.PUSH, InstructionType.EAX);
			
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
			this.emit(InstructionType.PUSH, InstructionType.EAX);
			
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
			this.emit(InstructionType.PUSH, InstructionType.EAX);
			
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
			this.emit(InstructionType.PUSH, InstructionType.EAX);
			
		}

		if(operador.equals("==")){
			this.emit(InstructionType.POP, InstructionType.EBX);
			this.emit(InstructionType.POP, InstructionType.EAX);
			this.emit(InstructionType.CMP, InstructionType.EAX, InstructionType.EBX, null);
			retorno = new Integer(InstructionType.JNE) ;
		}

		if(operador.equals("!=")){
			this.emit(InstructionType.POP, InstructionType.EBX);
			this.emit(InstructionType.POP, InstructionType.EAX);
			this.emit(InstructionType.CMP, InstructionType.EAX, InstructionType.EBX, null);
			retorno = new Integer(InstructionType.JE);
		}
		if(operador.equals("<")){
			this.emit(InstructionType.POP, InstructionType.EBX);
			this.emit(InstructionType.POP, InstructionType.EAX);
			this.emit(InstructionType.CMP, InstructionType.EAX, InstructionType.EBX, null);
			retorno = new Integer(InstructionType.JGE);
		}

		if(operador.equals(">")){
			this.emit(InstructionType.POP, InstructionType.EBX);
			this.emit(InstructionType.POP, InstructionType.EAX);
			this.emit(InstructionType.CMP, InstructionType.EAX, InstructionType.EBX, null);
			retorno = new Integer(InstructionType.JLE);
		}
		if(operador.equals("<=")){
			this.emit(InstructionType.POP, InstructionType.EBX);
			this.emit(InstructionType.POP, InstructionType.EAX);
			this.emit(InstructionType.CMP, InstructionType.EAX, InstructionType.EBX, null);
			retorno = new Integer(InstructionType.JG);
		}

		if(operador.equals(">=")){
			this.emit(InstructionType.POP, InstructionType.EBX);
			this.emit(InstructionType.POP, InstructionType.EAX);
			this.emit(InstructionType.CMP, InstructionType.EAX, InstructionType.EBX, null);
			retorno = new Integer(InstructionType.JL);
		}
		return retorno;


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
			this.emit(InstructionType.PUSH_FLOAT, InstructionType.QWORD, constante, null);

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
		return new Integer(InstructionType.BOOLEANO);
	}

	public Object visitType(Type type, Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

}
