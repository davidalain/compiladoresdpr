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
	 * Veririfes if the current token kind is the expected one
	 * @param kind
	 * @throws SyntacticException
	 */ //TODO
	private void accept(int kind) throws SyntacticException {
		// If the current token kind is equal to the expected
			// Gets next token
		// If not
			// Raises an exception
	}
	
	/**
	 * Gets next token
	 */ //TODO
	private void acceptIt() {
	}

	/**
	 * Verifies if the source program is syntactically correct
	 * @throws SyntacticException
	 */ //TODO
	public AST parse() throws SyntacticException {
		return null;
	}
	
}
