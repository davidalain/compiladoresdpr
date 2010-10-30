package checker;

import java.util.ArrayList;

import util.AST.Program;
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

	public Object visitAssignStatement(AssignStatement stat, Object arg) throws SemanticException {
		Type variavel = (Type) stat.getVariableName().visit(this, arg);
		if (!idTable.containsKey(variavel.spelling)){
			throw new SemanticException("Variavel não foi declarada");
		}
		Type atribuicao = (Type) stat.getRightHandStatement().visit(this, arg);
		// TODO checar linha abaixo
		if (!variavel.equals(atribuicao)){
			throw new SemanticException("A expressão não é do mesmo tipo da variavel");
		}
		return null;
	}

	public Object visitBinaryExpression(BinaryExpression byExp, Object arg) throws SemanticException {

		Type expEsq = (Type)byExp.getLeftExpression().visit(this, arg);
		Type expDir = (Type)byExp.getRightExpression().visit(this, arg);
		if (!expDir.equals(expEsq)){
			throw new SemanticException("Operandos com tipos diferentes");
		}

		Type operador = (Type) byExp.getOperator().visit(this, expDir);

		return operador;
	}

	public Object visitBooleanValue(BooleanValue boo, Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitBooleanUnaryExpression(BooleanUnaryExpression booUnExp,
			Object arg) {
		// TODO Auto-generated method stub
		return null;
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

	public Object visitCallStatementRHS(CallStatementRHS callRHS, Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitContinueStatement(ContinueStatement stat, Object arg) throws SemanticException {
		Statement whileStatementPai = (Statement) arg;
		if (!(whileStatementPai instanceof WhileStatement)){
			throw new SemanticException("O continue só pode ser usado dentro de escopo de um while");
		}
		return null;
	}

	public Object visitExpressionRHS(ExpressionRHS expRHS, Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitFunctionBody(FunctionBody fbody, Object arg) throws SemanticException {

		ArrayList<VariableDeclaration> variaveisFuncBody = fbody.getVariables();
		for (VariableDeclaration vd : variaveisFuncBody){
			String nomeVariavel = vd.getIdentifier().getSpelling();
			if(this.idTable.containsKey(nomeVariavel)){
				throw new SemanticException("Variável ["+nomeVariavel+"] já foi declarada");
			}
			this.idTable.enter(nomeVariavel, vd);
		}

		ArrayList<Statement> statsFunBody = fbody.getStatements();
		//	int contadorRetornos = 0;
		Statement ultimo = statsFunBody.get(statsFunBody.size()-1);
		if (!(ultimo instanceof ReturnStatement)){
			throw new SemanticException("");
		}
		for (Statement s : statsFunBody){
			s.visit(this, arg);
		}

		return null;
	}

	public Object visitFunctionDeclaration(FunctionDeclaration decl, Object arg) throws SemanticException {

		String nomeFunc = decl.getFunctionName().getSpelling();
		if(this.idTable.containsKey(nomeFunc)){
			throw new SemanticException("Identificador "+nomeFunc+" já foi declarado");
		}
		
		this.idTable.enter(nomeFunc, decl.getFunctionName());
		this.idTable.openScope();

		ArrayList<VariableDeclaration> parametros = decl.getParameters();
		for (VariableDeclaration vd : parametros){

			String nomeParametro = vd.getIdentifier().getSpelling();
			if(this.idTable.containsKey(nomeParametro)){
				throw new SemanticException("Identificador "+nomeParametro+" já foi declarado");
			}

			this.idTable.enter(nomeParametro, decl);
		}

		decl.getFunctionBody().visit(this, decl);

		this.idTable.closeScope();

		return null;
	}

	public Object visitIdentifier(Identifier id, Object arg) {

		return null;
	}

	public Object visitIdentifierUnaryExpression(
			IdentifierUnaryExpression idUnExp, Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitIfElseStatement(IfElseStatement stat, Object arg) {
		Expression condicao = stat.getCondition();

		this.idTable.openScope();
		this.idTable.closeScope();
		return null;
	}

	public Object visitNumberValue(NumberValue number, Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitNumberUnaryExpression(NumberUnaryExpression numUnExp,
			Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitOperator(Operator op, Object arg) throws SemanticException {

		Type tipo = (Type) arg;
		String operador = op.getSpelling();
		Type retorno = tipo;

		if(operador.equals("+") || operador.equals("-") || operador.equals("*") || operador.equals("/") ){
			if (!(tipo.equals("int") || tipo.equals("double"))){
				throw new SemanticException("Operação permitida apenas com int ou double");
			}

			retorno = tipo;
		}

		if (operador.equals("==") || operador.equals("!=") ) {
			if (!(tipo.equals("int") || tipo.equals("double") || tipo.equals("boolean"))){
				throw new SemanticException("Operação permitida apenas com int,double ou boolean");
			}
		}


		if ( operador.equals(">") || operador.equals(">=") 
				|| operador.equals("<") || operador.equals("<=")) {
			if (!(tipo.equals("int") || tipo.equals("double"))){
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

	public Object visitPrintlnStatement(PrintlnStatement stat, Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitProgram(Program prog, Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object visitReturnStatement(ReturnStatement stat, Object arg) throws SemanticException {

		FunctionDeclaration funcao = (FunctionDeclaration)arg;
		Type tipoRetornoFuncao = funcao.getReturnType();
		Type tipoRetornoStatement = (Type) stat.getReturnExpression().visit(this, arg);
		if (!tipoRetornoFuncao.equals(tipoRetornoStatement)){
			throw new SemanticException("O tipo de retorno é incompatível");
		}
		return null;
	}

	public Object visitVariableDeclaration(VariableDeclaration decl, Object arg) throws SemanticException {
		String nomeVar = decl.getIdentifier().getSpelling();

		if(!idTable.containsKey(nomeVar)){
			idTable.enter(nomeVar ,decl);
		}else{
			throw new SemanticException("Variável ["+nomeVar+"] já foi declarada");
		}
		Type tipoVariavel = decl.getType();
		if (tipoVariavel.getSpelling().equals("void")){
			throw new SemanticException("O tipo void só pode ser usado em declaração de função");
		}
		
		
		return null;
	}

	public Object visitWhileStatement(WhileStatement stat, Object arg) throws SemanticException {
		// TODO Auto-generated method stub
		Type tipoCondicao = (Type) stat.getCondition().visit(this, arg);
		if (!tipoCondicao.getSpelling().equals("boolean")){
			throw new SemanticException("Condicao do while deve ser boolean");
		}
		ArrayList<Statement> statWhile = stat.getStatements();
		for (Statement s : statWhile){
			s.visit(this, stat);
			
		}
		return null;
	}

	public Object visitType(Type type, Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

}
