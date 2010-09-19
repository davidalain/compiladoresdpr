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

	}

	/**
	 * Verifies if the current token kind is the expected one
	 * @param kind
	 * @throws SyntacticException
	 */ //TODO
	private void accept(int kind) throws SyntacticException {
		// If the current token kind is equal to the expected
		// Gets next token
		// If not
		// Raises an exception

		if(this.currentToken.getKind() == kind){
			this.acceptIt();
		}else{
			throw new SyntacticException("[Erro de sintaxe] Esperado o tipo: "+GrammarSymbols.getNameByKind(kind), this.currentToken);
		}

	}

	/**
	 * Gets next token
	 */ //TODO
	private void acceptIt() {
		this.currentToken = this.scanner.getNextToken();
	}


	/**
	 * Parse no padr�o [Program]
	 * Program -> (Type identifier (; | ( (Parameters | empty) ) { FunctionBody })* eot
	 * @throws SyntacticException 
	 */
	private void parseProgram() throws SyntacticException{
		
		this.parseType();
		this.accept(GrammarSymbols.ID);

		//declara��o de vari�vel
		if(this.currentToken.getKind() == GrammarSymbols.SEMICOLON){
			this.acceptIt();

		//declara��o de fun��o
		}else {
			// ( parametros )
			this.accept(GrammarSymbols.LPAR);
			this.parseParameters();
			this.accept(GrammarSymbols.RPAR);
			
			// { corpo da fun��o }
			this.accept(GrammarSymbols.LBRACKET);
			this.parseFunctionBody();
			this.accept(GrammarSymbols.RBRACKET);

		}

	}

	/**
	 * Parse do padr�o [FunctionBody]
	 * FunctionBody -> (Type identifier ;)* Statements
	 * @throws SyntacticException 
	 */
	private void parseFunctionBody() throws SyntacticException {
		//a func�o possui corpo
		if(this.currentToken.getKind() != GrammarSymbols.RBRACKET){
			
			//declara��o de vari�veis no inicio do corpo da fun��o
			if(	this.currentToken.getKind() == GrammarSymbols.BOOLEAN || 
				this.currentToken.getKind() == GrammarSymbols.INT ||
				this.currentToken.getKind() == GrammarSymbols.DOUBLE ||
				this.currentToken.getKind() == GrammarSymbols.VOID)
			{
				this.parseType();
				this.accept(GrammarSymbols.ID);
				this.accept(GrammarSymbols.SEMICOLON);
				
				this.parseFunctionBody();
			}
			//j� leu todas as declara��es de vari�veis ou
			//n�o houve declara��o de vari�veis
			else{
				this.parseStatements();
			}
			
		}
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
	private void parseStatements() throws SyntacticException {
		// TODO Auto-generated method stub
		
		if(this.currentToken.getKind() != GrammarSymbols.RBRACKET){
			//chamada de fun��o ou atribui��o de vari�vel
			if(this.currentToken.getKind() == GrammarSymbols.ID){
				//aceitar o Token ID
				this.acceptIt();

				// chamada de fun��o
				if(this.currentToken.getKind() == GrammarSymbols.LPAR){
					
					// ( Parametros )
					this.acceptIt();
					this.parseArguments();
					this.accept(GrammarSymbols.RPAR);
					
				// atribui��o
				}else{
					this.accept(GrammarSymbols.ASSIGN);
					this.parseRHS();
				}
				
				this.accept(GrammarSymbols.SEMICOLON);
			
			//Padr�o if
			//if ( Expression ) { Statements } (empty | else { Statements }) |
			}else if(this.currentToken.getKind() == GrammarSymbols.IF){
				this.acceptIt();
				
				//if ( express�o )
				this.accept(GrammarSymbols.LPAR);
				this.parseExpression();
				this.accept(GrammarSymbols.RPAR);
				
				// { Statements }
				this.accept(GrammarSymbols.LBRACKET);
				this.parseStatements();
				this.accept(GrammarSymbols.RBRACKET);
				
				//else { Statements }
				if(this.currentToken.getKind() == GrammarSymbols.ELSE){
					this.acceptIt();
					
					// { Statements }
					this.accept(GrammarSymbols.LBRACKET);
					this.parseStatements();
					this.accept(GrammarSymbols.RBRACKET);
				}
				
			//Padr�o while
			//while ( Expression ) { Statements } |
			}else if(this.currentToken.getKind() == GrammarSymbols.WHILE){
				
				// while ( Expression )			
				this.acceptIt();
				this.accept(GrammarSymbols.LPAR);
				this.parseExpression();
				this.accept(GrammarSymbols.RPAR);
				
				// { Statements }
				this.accept(GrammarSymbols.LBRACKET);
				this.parseStatements();
				this.accept(GrammarSymbols.RBRACKET);
			
			// Padr�o return
			// return Expression ; |
			}else if(this.currentToken.getKind() == GrammarSymbols.RETURN){
				this.acceptIt();
				
				if(this.currentToken.getKind() != GrammarSymbols.SEMICOLON){
					this.parseExpression();
				}
				this.accept(GrammarSymbols.SEMICOLON);
			
			//break
			}else if(this.currentToken.getKind() == GrammarSymbols.BREAK){
				this.acceptIt();
				this.accept(GrammarSymbols.SEMICOLON);
				
			//continue
			}else if(this.currentToken.getKind() == GrammarSymbols.CONTINUE){
				this.acceptIt();
				this.accept(GrammarSymbols.SEMICOLON);
			
			//println ( identifier )
			}else if(this.currentToken.getKind() == GrammarSymbols.PRINTLN){
				this.acceptIt();
				
				// ( identifier )
				this.accept(GrammarSymbols.LPAR);
				this.accept(GrammarSymbols.ID);
				this.accept(GrammarSymbols.RPAR);
				
			//Erro, fora do padr�o
			}else{
				throw new SyntacticException("[parseStatements Erro] fora do padr�o", this.currentToken);
			}
			
			
			this.parseStatements();
		}
	}

	private void parseExpression() {
		// TODO Auto-generated method stub
		System.out.println("Falta implementar o parseExpression");
	}

	/**
	 * Parse do padr�o [RHS]
	 * RHS -> ( Expression ) | identifier ( (Arguments | empty) )
	 * @throws SyntacticException 
	 */
	private void parseRHS() throws SyntacticException {
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
		
	}

	/**
	 * Parse do padr�o [Arguments]
	 * Arguments -> identifier (empty | , Arguments)
	 * @throws SyntacticException 
	 */
	private void parseArguments() throws SyntacticException {
		//fun��o tem argumentos
		if(this.currentToken.getKind() != GrammarSymbols.RPAR){
			this.accept(GrammarSymbols.ID);
			
			while(this.currentToken.getKind() == GrammarSymbols.COMMA){
				this.acceptIt();
				this.accept(GrammarSymbols.ID);
				
			}
		}
	}

	/**
	 * Parse do padr�o [Parameters]
	 * Parameters -> Type identifier (empty | , Parameters)
	 * @throws SyntacticException 
	 */
	private void parseParameters() throws SyntacticException {
		//a fun��o tem par�metros
		if(this.currentToken.getKind() != GrammarSymbols.RPAR){
			this.parseType();
			this.accept(GrammarSymbols.ID);
			
			if(this.currentToken.getKind() == GrammarSymbols.COMMA){
				this.acceptIt();
				this.parseParameters();
			}
		}
	}

	/**
	 * Parser do padr�o [Type]
	 * Type -> void | int | double | boolean
	 * @throws SyntacticException 
	 */
	private void parseType() throws SyntacticException{
		switch (this.currentToken.getKind()) {
		case GrammarSymbols.VOID:
		case GrammarSymbols.INT:
		case GrammarSymbols.DOUBLE:
		case GrammarSymbols.BOOLEAN:
			this.acceptIt();
			break;

		default:
			throw new SyntacticException("Erro de sintaxe em [parseType]",this.currentToken);
		}
	}

	/**
	 * Verifies if the source program is syntactically correct
	 * @throws SyntacticException
	 */ //TODO
	public AST parse() throws SyntacticException {
		this.currentToken = this.scanner.getNextToken();

		//Enquanto ainda n�o chegou no fim do c�digo fonte
		while(this.currentToken.getKind() != GrammarSymbols.EOT){
			this.parseProgram();
		}

		return null;
	}

}
