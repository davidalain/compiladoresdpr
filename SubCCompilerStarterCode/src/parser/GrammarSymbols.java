package parser;


/**
 * This class contains codes for each grammar terminal
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class GrammarSymbols {

	// Language terminals (starts from 0)
	public static final int ID = 0;
	public static final int NUMBER = 1;
	public static final int SEMICOLON = 2;
	public static final int LPAR = 3;
	public static final int RPAR = 4;
	public static final int LBRACKET = 5;
	public static final int RBRACKET = 6;
	public static final int ASSIGN = 7;
	public static final int MULT = 8;
	public static final int DIV = 9;
	public static final int PLUS = 10;
	public static final int MINUS = 11;
	public static final int EQUAL = 12;
	public static final int NOTEQUAL = 13;
	public static final int LESSERTHAN = 14;
	public static final int LESSEREQUALTHAN = 15;
	public static final int GREATERTHAN = 16;
	public static final int GREATEREQUALTHAN = 17;
	public static final int COMMA = 18;
	public static final int VOID = 19;
	public static final int INT = 20;
	public static final int DOUBLE = 21;
	public static final int BOOLEAN = 22;	
	public static final int IF = 23;
	public static final int ELSE = 24;
	public static final int WHILE = 25;
	public static final int RETURN = 26;
	public static final int BREAK = 27;
	public static final int CONTINUE = 28;
	public static final int PRINTLN = 29;
	public static final int FALSE = 30;
	public static final int TRUE = 31;
	public static final int EOT = 32;


	public static String getNameByKind(int kind){
		switch (kind) {
		case ID: return "ID";
		case NUMBER: return "NUMBER";
		case SEMICOLON: return "SEMICOLON";
		case LPAR: return "LPAR";
		case RPAR: return "RPAR";
		case LBRACKET: return "LBRACKET";
		case RBRACKET: return "RBRACKET";
		case ASSIGN: return "ASSIGN";
		case MULT: return "MULT";
		case DIV: return "DIV";
		case PLUS: return "PLUS";
		case MINUS: return "MINUS";
		case EQUAL: return "EQUAL";
		case NOTEQUAL: return "NOTEQUAL";
		case LESSERTHAN: return "LESSERTHAN";
		case LESSEREQUALTHAN: return "LESSEREQUALTHAN";
		case GREATERTHAN: return "GREATERTHAN";
		case GREATEREQUALTHAN: return "GREATEREQUALTHAN";
		case COMMA: return "COMMA";
		case VOID: return "VOID";
		case INT: return "INT";
		case DOUBLE: return "DOUBLE";
		case BOOLEAN: return "BOOLEAN";
		case IF: return "IF";
		case ELSE: return "ELSE";
		case WHILE: return "WHILE";
		case RETURN: return "RETURN";
		case BREAK: return "BREAK";
		case CONTINUE: return "CONTINUE";
		case PRINTLN: return "PRINTLN";
		case FALSE: return "FALSE";
		case TRUE: return "TRUE";
		case EOT: return "EOT";
		default: return "[GrammarSymbols.getNameByKind Error] Kind not found";
		}		
	}
}
