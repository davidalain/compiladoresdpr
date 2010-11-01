package checker;

import java.util.ArrayList;

import parser.Parser;

import util.AST.AST;
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
import util.symbolsTable.IdentificationTable;

public final class Checker implements Visitor {

	private IdentificationTable idTable;

	public Checker(){
		idTable = new IdentificationTable();

	}
	public void check(Program prog){
		try {
			prog.visit(this, null);
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object visitAssignStatement(AssignStatement stat, Object arg) throws SemanticException {
		
		Type tipoVariavel = (Type) stat.getVariableName().visit(this, arg);
		VariableDeclaration vd = (VariableDeclaration) idTable.retrieve(stat.getVariableName().getSpelling());
		
		if (vd == null){
			throw new SemanticException("Variavel " + vd.getIdentifier().getSpelling()  + " não foi declarada");
		}
		if ( !(vd instanceof VariableDeclaration)){
			throw new SemanticException("Só pode ser atribuido valor a variaveis");
		}
		
		Type tipoAtribuicao = (Type) stat.getRightHandStatement().visit(this, arg);

		if (!tipoVariavel.equals(tipoAtribuicao)){
			throw new SemanticException("A expressão não é do mesmo tipo da variavel");
		}

		return null;

	}

	public Object visitBinaryExpression(BinaryExpression byExp, Object arg) throws SemanticException {

		Type expEsq = (Type)byExp.getLeftExpression().visit(this, arg);
		Type expDir = (Type)byExp.getRightExpression().visit(this, arg);
		if (!expDir.getSpelling().equals(expEsq.getSpelling())){
			throw new SemanticException("Operandos com tipos diferentes");
		}


		Type operador = (Type) byExp.getOperator().visit(this, expDir);

		return operador;
	}

	public Object visitBooleanValue(BooleanValue boo, Object arg) {
		return new Type(boo.getSpelling());
	}

	public Object visitBooleanUnaryExpression(BooleanUnaryExpression booUnExp,
			Object arg) {

		return booUnExp.getBooleanValue().visit(this, arg);
	}

	public Object visitBreakStatement(BreakStatement stat, Object arg) throws SemanticException {
		Statement whileStatementPai = (Statement) arg;
		if (!(whileStatementPai instanceof WhileStatement)){
			throw new SemanticException("O break só pode ser usado dentro de escopo de um while");
		}
		return null;
	}

	public Object visitCallStatement(CallStatement stat, Object arg) throws SemanticException {

		String nomeFuncao = stat.getFunctionName().getSpelling();
		FunctionDeclaration funcao = (FunctionDeclaration)this.idTable.retrieve(nomeFuncao); 
		if(funcao == null){
			throw new SemanticException("Funcão "+nomeFuncao+" não foi declarada");
		}

		if(stat.getArguments().size() != funcao.getParameters().size()){
			throw new SemanticException("Quantidade de argumentos imcompatíevis com a funcão "+nomeFuncao);
		}

		int tamanho = stat.getArguments().size();
		ArrayList<Identifier> argumentos = stat.getArguments();
		ArrayList<VariableDeclaration> parametros = funcao.getParameters();

		for(int i = 0 ; i < tamanho ; i++){
			Type argumentoChamada = (Type) argumentos.get(i).visit(this, arg);
			Type parametroFuncao = (Type) parametros.get(i).visit(this, arg);

			if(!argumentoChamada.equals(parametroFuncao)){
				throw new SemanticException("Tipo do argumento "+argumentoChamada.getSpelling()+" incompatível");
			}

		}

		return funcao.getReturnType();
	}

	public Object visitCallStatementRHS(CallStatementRHS callRHS, Object arg) throws SemanticException {

		
		
		return callRHS.getFunctionCall().visit(this, arg);
		
		
	}

	public Object visitContinueStatement(ContinueStatement stat, Object arg) throws SemanticException {
		Statement whileStatementPai = (Statement) arg;
		if (!(whileStatementPai instanceof WhileStatement)){
			throw new SemanticException("O continue só pode ser usado dentro de escopo de um while");
		}
		return null;
	}

	public Object visitExpressionRHS(ExpressionRHS expRHS, Object arg) throws SemanticException {
		Type tipo = (Type) expRHS.getExpression().visit(this, arg);
		return tipo;
	}

	public Object visitFunctionBody(FunctionBody fbody, Object arg) throws SemanticException {
		
		Type retorno ; 
		boolean existeRetorno = false;
		ArrayList<VariableDeclaration> variaveisFuncBody = fbody.getVariables();
		for (VariableDeclaration vd : variaveisFuncBody){
			vd.visit(this, arg);
			String nomeVariavel = vd.getIdentifier().getSpelling();
		}
		
		ArrayList<Statement> statsFunBody = fbody.getStatements();
		
		//	int contadorRetornos = 0;
		for (Statement s : statsFunBody){
			if (s instanceof ReturnStatement){
				existeRetorno = true;
			}
			s.visit(this, arg);
			
		}
		if ( (existeRetorno == true)){
			
		}
		
		
		return null;
	}

	public Object visitFunctionDeclaration(FunctionDeclaration decl, Object arg) throws SemanticException {

		String nomeFunc = decl.getFunctionName().getSpelling();
		this.idTable.enter(nomeFunc, decl);
		this.idTable.openScope();

		ArrayList<VariableDeclaration> parametros = decl.getParameters();
		if (parametros != null){
			for (VariableDeclaration vd : parametros){

				String nomeParametro = vd.getIdentifier().getSpelling();
				if(this.idTable.containsKey(nomeParametro)){
					throw new SemanticException("Identificador "+nomeParametro+" já foi declarado");
				}

				this.idTable.enter(nomeParametro, decl);
			}
		}
		//problemas para retorno de função
		
		if (decl.getFunctionBody() != null){
			decl.getFunctionBody().visit(this, decl);
		}
		else {
			if (!decl.getReturnType().getSpelling().equals("void")){
				throw new SemanticException("Funcao sem clausula return");
			}
		}
		this.idTable.closeScope();

		return null;
	}

	public Object visitIdentifier(Identifier id, Object arg) throws SemanticException {
		VariableDeclaration retorno = (VariableDeclaration) idTable.retrieve(id.getSpelling());
		if (retorno == null){
			throw new SemanticException("Variavel " + id.getSpelling() + " nao declarada");
		}
		Command decoro = (Command) idTable.retrieve(id.getSpelling());
		id.setNoDeclaracao(decoro);
		return retorno.getType();

	}

	public Object visitIdentifierUnaryExpression(
			IdentifierUnaryExpression idUnExp, Object arg) throws SemanticException {
		return idUnExp.getVariableName().visit(this, arg);
	}

	public Object visitIfElseStatement(IfElseStatement stat, Object arg) throws SemanticException {
		Type condicao = (Type) stat.getCondition().visit(this, arg);
		if (!condicao.getSpelling().equals("boolean")){
			throw new SemanticException("Condicao do if deve ser boolean");
		}
		ArrayList<Statement> ifStats = stat.getIfStatements();
		ArrayList<Statement> elseStats = stat.getElseStatements();
		this.idTable.openScope();
		for (Statement s: ifStats){
			s.visit(this, arg);
		}
		this.idTable.closeScope();

		this.idTable.openScope();
		for (Statement s: elseStats){
			s.visit(this, arg);
		}
		this.idTable.closeScope();

		return null;
	}

	public Object visitNumberValue(NumberValue number, Object arg) {
		if (number.getSpelling().contains(".")){
			return new Type("double");
		}
		else{
			return new Type("int");
		}

	}

	public Object visitNumberUnaryExpression(NumberUnaryExpression numUnExp,
			Object arg) {
		return numUnExp.getNumberValue().visit(this, arg);

	}

	public Object visitOperator(Operator op, Object arg) throws SemanticException {

		Type tipo = (Type) arg;
		String operador = op.getSpelling();
		Type retorno = tipo;

		if(operador.equals("+") || operador.equals("-") || operador.equals("*") || operador.equals("/") ){
			if (!(tipo.equals(new Type("int")) || tipo.equals(new Type("double")))){
				throw new SemanticException("Operação permitida apenas com int ou double");
			}

			retorno = tipo;
		}

		if (operador.equals("==") || operador.equals("!=") ) {
			if (!(tipo.equals(new Type("int")) || tipo.equals(new Type("double")) || tipo.equals(new Type("boolean")))){
				throw new SemanticException("Operação permitida apenas com int,double ou boolean");
			}
		}


		if ( operador.equals(">") || operador.equals(">=") 
				|| operador.equals("<") || operador.equals("<=")) {
			if (!(tipo.equals(new Type("int")) || tipo.equals(new Type("double")))){
				throw new SemanticException("Operação permitida apenas com int,double");
			}
		}

		if ( operador.equals(">") || operador.equals(">=") 
				|| operador.equals("<") || operador.equals("<=")
				|| operador.equals("==") || operador.equals("!=")) {

			retorno = new Type("boolean");
		}

		return retorno;

	}

	public Object visitPrintlnStatement(PrintlnStatement stat, Object arg) throws SemanticException {
		return stat.getVariableName().visit(this, arg);
	}

	public Object visitProgram(Program prog, Object arg) throws SemanticException {

		ArrayList<Command> comandos = prog.getCommands();
		for (Command c: comandos){
			c.visit(this, arg);
		}
		FunctionDeclaration funcaoMain = (FunctionDeclaration) idTable.retrieve("main");
		if ( (funcaoMain == null) || !(funcaoMain.getReturnType().getSpelling().equals("void"))){
			throw new SemanticException("O código deve ter uma função void main()");
		}
		return null;
	}

	public Object visitReturnStatement(ReturnStatement stat, Object arg) throws SemanticException {

		FunctionDeclaration funcao = (FunctionDeclaration)arg;
		Type tipoRetornoFuncao = funcao.getReturnType();
		Expression retorno = stat.getReturnExpression();
	
		if (retorno == null){
			if (!tipoRetornoFuncao.getSpelling().equals("void")){
				throw new SemanticException("O tipo de retorno é incompatível");
			}
			else {
				return new Type("void");
			}
		}

		Type tipoRetornoStatement = (Type) retorno.visit(this, arg);

		if (!tipoRetornoFuncao.getSpelling().equals("void")){
			if (!tipoRetornoFuncao.getSpelling().equals(tipoRetornoStatement.getSpelling())){
				throw new SemanticException("O tipo de retorno é incompatível");
			}	
		}
		else {
			throw new SemanticException("O tipo de retorno é incompatível");

		}

		return tipoRetornoStatement;
	}

	public Object visitVariableDeclaration(VariableDeclaration decl, Object arg) throws SemanticException {
		
		String nomeVar = decl.getIdentifier().getSpelling();
		idTable.enter(nomeVar ,decl);
		Type tipoVariavel = decl.getType();
		if (tipoVariavel.equals(new Type("void"))){
			throw new SemanticException("O tipo void só pode ser usado em declaração de função");
		}
		return null;
	}

	public Object visitWhileStatement(WhileStatement stat, Object arg) throws SemanticException {
		Type tipoCondicao = (Type) stat.getCondition().visit(this, arg);
		if (!tipoCondicao.equals(new Type("boolean"))){
			throw new SemanticException("Condicao do while deve ser boolean");
		}
		ArrayList<Statement> statWhile = stat.getStatements();
		for (Statement s : statWhile){
			s.visit(this, stat);

		}
		return null;
	}

	public Object visitType(Type type, Object arg) {
		return type;
	}

}
