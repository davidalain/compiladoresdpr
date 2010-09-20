package parser;

import java.util.ArrayList;

import scanner.Scanner;
import scanner.Token;
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
import util.AST.Expression.UnaryExpression;
import util.AST.RHS.CallStatementRHS;
import util.AST.RHS.ExpressionRHS;
import util.AST.RHS.RHS;
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

/**
 * Parser class
 * @version 2010-august-29
 * @discipline Projeto de Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Parser {

	// The current token
	private Token currentToken = null;
	// The scanner
	private Scanner scanner = null;

	/**
	 * Parser constructor
	 */
	public Parser() {
		// Initializes the scanner object
		this.scanner = new Scanner();

		this.currentToken = this.scanner.getNextToken();
	}

	/**
	 * Verifies if the current token kind is the expected one
	 * @param kind
	 * @throws SyntacticException
	 * @return Token Spelling 
	 */
	private String accept(int kind) throws SyntacticException {
		// If the current token kind is equal to the expected
		// Gets next token
		// If not
		// Raises an exception

		if(this.currentToken.getKind() == kind){
			return this.acceptIt();
		}else{
			throw new SyntacticException("[Erro de sintaxe] Esperado o tipo: "+GrammarSymbols.getNameByKind(kind), this.currentToken);
		}

	}

	/**
	 * Gets next token
	 */
	private String acceptIt() {
		String spelling = this.currentToken.getSpelling();
		this.currentToken = this.scanner.getNextToken();
		return spelling;
	}


	/**
	 * Parse no padr�o [Program]
	 * Program -> (Type identifier (; | ( (Parameters | empty) ) { FunctionBody })* eot
	 * @throws SyntacticException 
	 */
	private Command parseProgram() throws SyntacticException{
		
		Type type = this.parseType();
		Identifier identifier = new Identifier(accept(GrammarSymbols.ID));

		//declara��o de vari�vel
		if(this.currentToken.getKind() == GrammarSymbols.SEMICOLON){
			this.acceptIt();
			return new VariableDeclaration(type,identifier);

		//declara��o de fun��o
		}else {
			
			ArrayList<VariableDeclaration> parameters;
			FunctionBody functionBody;
			
			// ( parametros )
			this.accept(GrammarSymbols.LPAR);
			parameters = this.parseParameters();
			this.accept(GrammarSymbols.RPAR);
			
			// { corpo da fun��o }
			this.accept(GrammarSymbols.LBRACKET);
			functionBody = this.parseFunctionBody();
			this.accept(GrammarSymbols.RBRACKET);

			return new FunctionDeclaration(type,identifier,parameters,functionBody);
		}

	}

	/**
	 * Parse do padr�o [FunctionBody]
	 * FunctionBody -> (Type identifier ;)* Statements
	 * @throws SyntacticException 
	 */
	private FunctionBody parseFunctionBody() throws SyntacticException {
		
		ArrayList<VariableDeclaration> variables = new ArrayList<VariableDeclaration>();
		ArrayList<Statement> statements = new ArrayList<Statement>();
		
		//entra se a func�o possui corpo
		while(this.currentToken.getKind() != GrammarSymbols.RBRACKET){
			
			
			//declara��o de vari�veis no inicio do corpo da fun��o
			while(	this.currentToken.getKind() == GrammarSymbols.BOOLEAN || 
				this.currentToken.getKind() == GrammarSymbols.INT ||
				this.currentToken.getKind() == GrammarSymbols.DOUBLE ||
				this.currentToken.getKind() == GrammarSymbols.VOID)
			{
				Type type = this.parseType();
				Identifier identifier = new Identifier(accept(GrammarSymbols.ID));
				this.accept(GrammarSymbols.SEMICOLON);
				
				variables.add(new VariableDeclaration(type, identifier));
			}
			
			//Statements
			statements = this.parseStatements();
			
		}
		
		return new FunctionBody(variables, statements);
	}

	/**
	 * Parse do padr�o [Statements]
	 * Statements -> Statement*
	 * Statement -> identifier (( (Arguments | empty) ) | = RHS) ; |
	 * 				if ( Expression ) { Statements } (empty | else { Statements }) |
	 * 				while ( Expression ) { Statements } |
	 * 				return Expression ; |
	 * 				return ; |
	 * 				break ; |
	 * 				continue ; |
	 * 				println ( identifier ) ;
	 * @throws SyntacticException 
	 */
	private ArrayList<Statement> parseStatements() throws SyntacticException {
		
		ArrayList<Statement> statementsReturn = new ArrayList<Statement>();
		
		while(this.currentToken.getKind() != GrammarSymbols.RBRACKET){
			//chamada de fun��o ou atribui��o de vari�vel
			if(this.currentToken.getKind() == GrammarSymbols.ID){
				//aceitar o Token ID
				Identifier identifier = new Identifier(this.acceptIt());

				// chamada de fun��o
				if(this.currentToken.getKind() == GrammarSymbols.LPAR){
					
					// ( Parametros )
					this.acceptIt();
					ArrayList<Identifier> arguments = this.parseArguments();
					this.accept(GrammarSymbols.RPAR);
					this.accept(GrammarSymbols.SEMICOLON);
					
					statementsReturn.add(new CallStatement(identifier,arguments));
					
				// atribui��o
				}else{
					this.accept(GrammarSymbols.ASSIGN);
					RHS rhs = this.parseRHS();
					this.accept(GrammarSymbols.SEMICOLON);
					
					statementsReturn.add(new AssignStatement(identifier,rhs));
					
				}
				
			//Padr�o if
			//if ( Expression ) { Statements } (empty | else { Statements }) |
			}else if(this.currentToken.getKind() == GrammarSymbols.IF){
				this.acceptIt();
				
				Expression condition;
				ArrayList<Statement> ifStatements = new ArrayList<Statement>();
				ArrayList<Statement> elseStatements = new ArrayList<Statement>();
				
				//( express�o )
				this.accept(GrammarSymbols.LPAR);
				condition = this.parseExpression();
				this.accept(GrammarSymbols.RPAR);
				
				// { Statements }
				this.accept(GrammarSymbols.LBRACKET);
				ifStatements = this.parseStatements();
				this.accept(GrammarSymbols.RBRACKET);
				
				//else
				if(this.currentToken.getKind() == GrammarSymbols.ELSE){
					this.acceptIt();
					
					// { Statements }
					this.accept(GrammarSymbols.LBRACKET);
					elseStatements = this.parseStatements();
					this.accept(GrammarSymbols.RBRACKET);
				}
				
				statementsReturn.add(new IfElseStatement(condition, ifStatements, elseStatements));
				
			//Padr�o while
			//while ( Expression ) { Statements } |
			}else if(this.currentToken.getKind() == GrammarSymbols.WHILE){
				
				Expression conditionWhile;
				ArrayList<Statement> statementsWhile = new ArrayList<Statement>();
				
				// while ( Expression )			
				this.acceptIt();
				this.accept(GrammarSymbols.LPAR);
				conditionWhile = this.parseExpression();
				this.accept(GrammarSymbols.RPAR);
				
				// { Statements }
				this.accept(GrammarSymbols.LBRACKET);
				statementsWhile = this.parseStatements();
				this.accept(GrammarSymbols.RBRACKET);
			
				statementsReturn.add(new WhileStatement(conditionWhile, statementsWhile));
				
			// Padr�o return
			// return Expression ; |
			}else if(this.currentToken.getKind() == GrammarSymbols.RETURN){
				this.acceptIt();
				
				Expression retExpression = null;
				
				if(this.currentToken.getKind() != GrammarSymbols.SEMICOLON){
					retExpression = this.parseExpression();
				}
				this.accept(GrammarSymbols.SEMICOLON);
			
				statementsReturn.add(new ReturnStatement(retExpression));
				
			//break
			}else if(this.currentToken.getKind() == GrammarSymbols.BREAK){
				this.acceptIt();
				this.accept(GrammarSymbols.SEMICOLON);
				
				statementsReturn.add(new BreakStatement());
				
			//continue
			}else if(this.currentToken.getKind() == GrammarSymbols.CONTINUE){
				this.acceptIt();
				this.accept(GrammarSymbols.SEMICOLON);
			
				statementsReturn.add(new ContinueStatement());
				
			//println ( identifier )
			}else if(this.currentToken.getKind() == GrammarSymbols.PRINTLN){
				this.acceptIt();
				
				// ( identifier )
				this.accept(GrammarSymbols.LPAR);
				Identifier identifierPrintln = new Identifier(this.accept(GrammarSymbols.ID));
				this.accept(GrammarSymbols.RPAR);
				
				statementsReturn.add(new PrintlnStatement(identifierPrintln));
				
			//Erro, fora do padr�o
			}else{
				throw new SyntacticException("[parseStatements Erro] fora do padr�o", this.currentToken);
			}
			
		}//fim do while
		
		return statementsReturn;
	}

	/**
	 * Parse do padr�o [Expression]
	 * Expression -> ( Expression ( + | - | * | / | == | != | > | >= | < | <= ) Expression ) | Value  
	 * @throws SyntacticException 
	 */
	private Expression parseExpression() throws SyntacticException {
		
		// ( Expression ( + | - | * | / | == | != | > | >= | < | <= ) Expression )
		if(this.currentToken.getKind() == GrammarSymbols.LPAR){
			this.acceptIt();
			
			this.parseExpression();
			this.parseOperation();	
			this.parseExpression();
			
			this.accept(GrammarSymbols.RPAR);
			
		// Value
		}else{
			this.parseValue();
		}
		
		//TODO implementar o retorno deste m�todo
		return null;
	}
	
	/**
	 * Parse de uma opera��o
	 * @throws SyntacticException
	 */
	private void parseOperation() throws SyntacticException {
		if(	this.currentToken.getKind() == GrammarSymbols.PLUS ||
			this.currentToken.getKind() == GrammarSymbols.MINUS ||
			this.currentToken.getKind() == GrammarSymbols.MULT ||
			this.currentToken.getKind() == GrammarSymbols.DIV ||
			this.currentToken.getKind() == GrammarSymbols.EQUAL ||
			this.currentToken.getKind() == GrammarSymbols.NOTEQUAL ||
			this.currentToken.getKind() == GrammarSymbols.GREATERTHAN ||
			this.currentToken.getKind() == GrammarSymbols.GREATEREQUALTHAN ||
			this.currentToken.getKind() == GrammarSymbols.LESSERTHAN ||
			this.currentToken.getKind() == GrammarSymbols.LESSEREQUALTHAN )
		{
			this.acceptIt();
		}else{
			throw new SyntacticException("[parseOperation erro] Esperada uma opera��o", this.currentToken);
		}
	}

	/**
	 * Parse do padr�o [Value]
	 * Value -> identifier | number | false | true
	 * @throws SyntacticException 
	 */
	private void parseValue() throws SyntacticException {
		
		if(	this.currentToken.getKind() == GrammarSymbols.ID ||
			this.currentToken.getKind() == GrammarSymbols.NUMBER ||
			this.currentToken.getKind() == GrammarSymbols.INT ||
			this.currentToken.getKind() == GrammarSymbols.DOUBLE ||
			this.currentToken.getKind() == GrammarSymbols.FALSE ||
			this.currentToken.getKind() == GrammarSymbols.TRUE )
		{
			this.acceptIt();
			
		}else{
			
			throw new SyntacticException("[parseValue Erro] Era esperado um valor",this.currentToken);
		}
		
	}
	
	
	
	/**
	 * Parse do padr�o [RHS]
	 * RHS -> ( Expression ) | identifier ( (Arguments | empty) )
	 * @throws SyntacticException 
	 */
	private RHS parseRHS() throws SyntacticException {
		// ( Expression )
		if(this.currentToken.getKind() == GrammarSymbols.LPAR){
			this.acceptIt();
			this.parseExpression();
			this.accept(GrammarSymbols.RPAR);
			
		// identifier ( (Arguments | empty) )
		}else{
			
			this.accept(GrammarSymbols.ID);
			this.accept(GrammarSymbols.LPAR);
			this.parseArguments();
			this.accept(GrammarSymbols.RPAR);
			
		}
		
		return null;
	}

	/**
	 * Parse do padr�o [Arguments]
	 * Arguments -> identifier (empty | , Arguments)
	 * @throws SyntacticException 
	 */
	private ArrayList<Identifier> parseArguments() throws SyntacticException {
		
		ArrayList<Identifier> arguments = new ArrayList<Identifier>();
		
		//se a fun��o tem argumentos
		if(this.currentToken.getKind() != GrammarSymbols.RPAR){
			
			Identifier identifier = new Identifier(this.accept(GrammarSymbols.ID));
			arguments.add(identifier);
			
			while(this.currentToken.getKind() == GrammarSymbols.COMMA){
				this.acceptIt();
				
				identifier = new Identifier(this.accept(GrammarSymbols.ID));
				arguments.add(identifier);
			}
		}
		
		return arguments;
	}

	/**
	 * Parse do padr�o [Parameters]
	 * Parameters -> Type identifier (empty | , Parameters)
	 * @throws SyntacticException 
	 */
	private ArrayList<VariableDeclaration> parseParameters() throws SyntacticException {
		
		ArrayList<VariableDeclaration> parameters = new ArrayList<VariableDeclaration>();
		
		//a fun��o tem par�metros
		while(this.currentToken.getKind() != GrammarSymbols.RPAR){
			
			Type type = this.parseType();
			Identifier identifier = new Identifier(this.accept(GrammarSymbols.ID));
			parameters.add(new VariableDeclaration(type, identifier));
			
			if(this.currentToken.getKind() == GrammarSymbols.COMMA){
				this.acceptIt();
				continue;
			}
		}
		
		return parameters;
	}

	/**
	 * Parser do padr�o [Type]
	 * Type -> void | int | double | boolean
	 * @throws SyntacticException 
	 */
	private Type parseType() throws SyntacticException{
		
		switch (this.currentToken.getKind()) {
		case GrammarSymbols.VOID:
		case GrammarSymbols.INT:
		case GrammarSymbols.DOUBLE:
		case GrammarSymbols.BOOLEAN:{
			return new Type(this.acceptIt());
		}
		default:
			throw new SyntacticException("Erro de sintaxe em [parseType]",this.currentToken);
		}
		

	}

	/**
	 * Verifies if the source program is syntactically correct
	 * @throws SyntacticException
	 */
	public Program parse() throws SyntacticException {
		
		ArrayList<Command> commands = new ArrayList<Command>();
		
		//Enquanto ainda n�o chegou no fim do c�digo fonte
		while(this.currentToken.getKind() != GrammarSymbols.EOT){
			commands.add(this.parseProgram());
		}

		return new Program(commands);
		
		//return null; implementa��o que veio do professor
	}

}