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
	 * Parse no padrão [Program]
	 * Program -> (Type identifier (; | ( (Parameters | empty) ) { FunctionBody })* eot
	 * @throws SyntacticException 
	 */
	private Program parseProgram() throws SyntacticException{
		
		ArrayList<Command> commands = new ArrayList<Command>();
		
		//Enquanto ainda não chegou no fim do código fonte
		while(this.currentToken.getKind() != GrammarSymbols.EOT){
			
			Type type = this.parseType();
			Identifier identifier = new Identifier(this.accept(GrammarSymbols.ID));
			
			Command atualCommand = null;
			
			//declaração de variável
			if(this.currentToken.getKind() == GrammarSymbols.SEMICOLON){
				this.acceptIt();
				
				//comando lido a ser adicionado na lista
				atualCommand = new VariableDeclaration(type,identifier); 
	
			//declaração de função
			}else {
				
				ArrayList<VariableDeclaration> parameters = null;
				FunctionBody functionBody = null;
				
				// ( parametros )
				this.accept(GrammarSymbols.LPAR);
				if(this.currentToken.getKind() != GrammarSymbols.RPAR){
					parameters = this.parseParameters();
				}
				this.accept(GrammarSymbols.RPAR);
				
				// { functionBody }
				this.accept(GrammarSymbols.LBRACKET);
				if(this.currentToken.getKind() != GrammarSymbols.RBRACKET){
					functionBody = this.parseFunctionBody();
				}
				this.accept(GrammarSymbols.RBRACKET);
				
				//comando lido a ser adicionado na lista	
				atualCommand = new FunctionDeclaration(type,identifier,parameters,functionBody); 
			}
			
			//Adiciona na lista de comandos o último comando lido
			commands.add(atualCommand);
		}

		return new Program(commands);
	}

	/**
	 * Parse do padrão [FunctionBody]
	 * FunctionBody -> (Type identifier ;)* Statements
	 * @throws SyntacticException 
	 */
	private FunctionBody parseFunctionBody() throws SyntacticException {
		
		ArrayList<VariableDeclaration> variables = new ArrayList<VariableDeclaration>();
		ArrayList<Statement> statements = new ArrayList<Statement>();
		
		//declaração de variáveis no inicio do corpo da função
		while( this.currentToken.getKind() == GrammarSymbols.BOOLEAN || 
			this.currentToken.getKind() == GrammarSymbols.INT ||
			this.currentToken.getKind() == GrammarSymbols.DOUBLE ||
			this.currentToken.getKind() == GrammarSymbols.VOID)
		{
			Type type = this.parseType();
			Identifier identifier = new Identifier(this.accept(GrammarSymbols.ID));
			this.accept(GrammarSymbols.SEMICOLON);
			
			variables.add(new VariableDeclaration(type, identifier));
		}
		
		//Statements
		statements = this.parseStatements();
			
		
		return new FunctionBody(variables, statements);
	}

	/**
	 * Parse do padrão [Statements]
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
			
			//chamada de função ou atribuição de variável
			if(this.currentToken.getKind() == GrammarSymbols.ID){
				//aceitar o Token ID
				Identifier identifier = new Identifier(this.acceptIt());

				// chamada de função
				if(this.currentToken.getKind() == GrammarSymbols.LPAR){
					
					ArrayList<Identifier> arguments = null;
					
					// ( Parametros )
					this.acceptIt();
					if(this.currentToken.getKind() != GrammarSymbols.RPAR){
						arguments = this.parseArguments();	
					}
					this.accept(GrammarSymbols.RPAR);
					this.accept(GrammarSymbols.SEMICOLON);
					
					statementsReturn.add(new CallStatement(identifier,arguments));
					
				// atribuição
				}else{
					this.accept(GrammarSymbols.ASSIGN);
					RHS rhs = this.parseRHS();
					this.accept(GrammarSymbols.SEMICOLON);
					
					statementsReturn.add(new AssignStatement(identifier,rhs));
					
				}
				
			//Padrão if
			//if ( Expression ) { Statements } (empty | else { Statements }) |
			}else if(this.currentToken.getKind() == GrammarSymbols.IF){
				this.acceptIt();
				
				Expression condition;
				ArrayList<Statement> ifStatements = null;
				ArrayList<Statement> elseStatements = null;
				
				//( expressão )
				this.accept(GrammarSymbols.LPAR);
				condition = this.parseExpression();
				this.accept(GrammarSymbols.RPAR);
				
				// { Statements }
				this.accept(GrammarSymbols.LBRACKET);
				if(this.currentToken.getKind() != GrammarSymbols.RBRACKET){
					ifStatements = this.parseStatements();
				}
				this.accept(GrammarSymbols.RBRACKET);
				
				//else
				if(this.currentToken.getKind() == GrammarSymbols.ELSE){
					this.acceptIt();
					
					// { Statements }
					this.accept(GrammarSymbols.LBRACKET);
					if(this.currentToken.getKind() != GrammarSymbols.RBRACKET){
						elseStatements = this.parseStatements();	
					}
					this.accept(GrammarSymbols.RBRACKET);
				}
				
				statementsReturn.add(new IfElseStatement(condition, ifStatements, elseStatements));
				
			//Padrão while
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
				if(this.currentToken.getKind() != GrammarSymbols.RBRACKET){
					statementsWhile = this.parseStatements();	
				}
				this.accept(GrammarSymbols.RBRACKET);
			
				statementsReturn.add(new WhileStatement(conditionWhile, statementsWhile));
				
			// Padrão return
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
				
			//Erro, fora do padrão
			}else{
				throw new SyntacticException("[parseStatements Erro] fora do padrão", this.currentToken);
			}
			
		}//fim do while
		
		return statementsReturn;
	}

	/**
	 * Parse do padrão [Expression]
	 * Expression -> ( Expression ( + | - | * | / | == | != | > | >= | < | <= ) Expression ) | Value  
	 * @throws SyntacticException 
	 */
	private Expression parseExpression() throws SyntacticException {
		
		// ( Expression ( + | - | * | / | == | != | > | >= | < | <= ) Expression )
		if(this.currentToken.getKind() == GrammarSymbols.LPAR){
			this.acceptIt();
			
			Expression leftExpression = this.parseExpression();
			Operator operator = this.parseOperation();	
			Expression rightExpression = this.parseExpression();
			
			this.accept(GrammarSymbols.RPAR);
			
			return new BinaryExpression(leftExpression, operator, rightExpression);
			
		// Value
		}else{
			UnaryExpression unaryExpression = this.parseValue();
			return unaryExpression;
			
		}
	}
	
	/**
	 * Parse de uma operação
	 * @throws SyntacticException
	 */
	private Operator parseOperation() throws SyntacticException {
		Operator operator;
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
			operator = new Operator(this.acceptIt());
		}else{
			throw new SyntacticException("[parseOperation erro] Esperada uma operação", this.currentToken);
		}
		
		return operator;
	}

	/**
	 * Parse do padrão [Value]
	 * Value -> identifier | number | false | true
	 * @throws SyntacticException 
	 */
	private UnaryExpression parseValue() throws SyntacticException {
		
		if(	this.currentToken.getKind() == GrammarSymbols.ID){
			return new IdentifierUnaryExpression(new Identifier(this.acceptIt()));
			
		}else if(this.currentToken.getKind() == GrammarSymbols.NUMBER ||
				this.currentToken.getKind() == GrammarSymbols.INT ||
				this.currentToken.getKind() == GrammarSymbols.DOUBLE)
		{
			return new NumberUnaryExpression(new NumberValue(this.acceptIt()));
			
		}else if(this.currentToken.getKind() == GrammarSymbols.FALSE ||
				this.currentToken.getKind() == GrammarSymbols.TRUE )
		{
			return new BooleanUnaryExpression(new BooleanValue(this.acceptIt()));
			
		}else{
			throw new SyntacticException("[parseValue Erro] Era esperado um valor",this.currentToken);
		}
		
	}
	
	
	
	/**
	 * Parse do padrão [RHS]
	 * RHS -> ( Expression ) | identifier ( (Arguments | empty) )
	 * @throws SyntacticException 
	 */
	private RHS parseRHS() throws SyntacticException {
		// ( Expression )
		if(this.currentToken.getKind() == GrammarSymbols.LPAR){
			this.acceptIt();
			ExpressionRHS expRHS = new ExpressionRHS(this.parseExpression());
			this.accept(GrammarSymbols.RPAR);
			return expRHS;
			
		// identifier ( (Arguments | empty) )
		}else{
			
			Identifier identifier = new Identifier(this.accept(GrammarSymbols.ID));
			this.accept(GrammarSymbols.LPAR);
			ArrayList<Identifier> arguments = this.parseArguments();
			this.accept(GrammarSymbols.RPAR);
			return new CallStatementRHS(new CallStatement(identifier, arguments));
		}
	}

	/**
	 * Parse do padrão [Arguments]
	 * Arguments -> identifier (empty | , Arguments)
	 * @throws SyntacticException 
	 */
	private ArrayList<Identifier> parseArguments() throws SyntacticException {
		
		ArrayList<Identifier> arguments = new ArrayList<Identifier>();
		
		//se a função tem argumentos
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
	 * Parse do padrão [Parameters]
	 * Parameters -> Type identifier (empty | , Parameters)
	 * @throws SyntacticException 
	 */
	private ArrayList<VariableDeclaration> parseParameters() throws SyntacticException {
		
		ArrayList<VariableDeclaration> parameters = new ArrayList<VariableDeclaration>();
		
		Type type = this.parseType();
		Identifier identifier = new Identifier(this.accept(GrammarSymbols.ID));
		parameters.add(new VariableDeclaration(type, identifier));
		
		while(this.currentToken.getKind() == GrammarSymbols.COMMA){
			this.acceptIt();
			
			type = this.parseType();
			identifier = new Identifier(this.accept(GrammarSymbols.ID));
			parameters.add(new VariableDeclaration(type, identifier));
		}
		
		return parameters;
	}

	/**
	 * Parser do padrão [Type]
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
	public AST parse() throws SyntacticException {
		
		return this.parseProgram();
		
	}

}
