package scanner;

import java.io.File;

import compiler.Properties;
import compiler.SubCCompiler;

import parser.GrammarSymbols;
import util.Arquivo;

/**
 * Scanner class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Scanner {

	// The file object that will be used to read the source code
	private Arquivo file;
	// The last char read from the source code
	private char currentChar;
	// The kind of the current token
	private int currentKind;
	// Buffer to append characters read from file
	private StringBuffer currentSpelling;
	// Current line and column in the source file
	private int line, column;

	/**
	 * Default constructor
	 */
	public Scanner() {
		this.file = new Arquivo(Properties.sourceCodeLocation);		
		this.line = 0;
		this.column = 0;
		this.currentChar = this.file.readChar();
	}

	/**
	 * Returns the next token
	 * @return
	 */ //TODO
	public Token getNextToken() {
		// Initializes the string buffer
		// Ignores separators
		// Clears the string buffer
		// Scans the next token
		// Creates and returns a token for the lexema identified
		this.currentSpelling = new StringBuffer("");

		while ( this.currentChar == '#' || this.currentChar == ' ' ||
				this.currentChar == '\n' || this.currentChar == '\t' ) {
			try{
				this.scanSeparator();
			}
			catch (LexicalException e){
				e.toString();
			}
		}

		this.currentSpelling = new StringBuffer(""); // ou apagar o conteúdo do buffer
		try{
			this.currentKind = this.scanToken();
		}
		catch (LexicalException e){
			e.toString();
		}

		return new Token(this.currentKind, this.currentSpelling.toString(), this.line, this.column);

	}

	/**
	 * Returns if a character is a separator
	 * @param c
	 * @return
	 */
	private boolean isSeparator(char c) {
		if ( c == '#' || c == ' ' || c == '\n' || c == '\t' ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Reads (and ignores) a separator
	 * @throws LexicalException
	 */ //TODO



	private void scanSeparator() throws LexicalException {
		// If it is a comment line
		if ( this.currentChar == '#' ) {
			// Gets next char
			this.getNextChar();
			// Reads characters while they are graphics or '\t'
			while ( this.isGraphic(this.currentChar) ) {
				this.getNextChar();
			}
			// A command line should finish with a \n
			if ( this.currentChar == '\n'){
				this.getNextChar();
			}
			else {
				throw new LexicalException("ai meu ovo", this.currentChar, this.line, this.column);
			}
			//			verifica se o caractere corrente é \n
			//			se sim, this.getNextChar
			//			caso contrário, erro léxico
		}//fim do if do #

		else {
			this.getNextChar();
		}
	}

	/**
	 * Gets the next char
	 */
	private void getNextChar() {
		// Appends the current char to the string buffer
		this.currentSpelling.append(this.currentChar);
		// Reads the next one
		this.currentChar = this.file.readChar();
		// Increments the line and column
		this.incrementLineColumn();
	}

	/**
	 * Increments line and column
	 */
	private void incrementLineColumn() {
		// If the char read is a '\n', increments the line variable and assigns 0 to the column
		if ( this.currentChar == '\n' ) {
			this.line++;
			this.column = 0;
			// If the char read is not a '\n' 
		} else {
			// If it is a '\t', increments the column by 4
			if ( this.currentChar == '\t' ) {
				this.column = this.column + 4;
				// If it is not a '\t', increments the column by 1
			} else {
				this.column++;
			}
		}
	}

	/**
	 * Returns if a char is a digit (between 0 and 9)
	 * @param c
	 * @return
	 */
	private boolean isDigit(char c) {
		if ( c >= '0' && c <= '9' ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns if a char is a letter (between a and z or between A and Z)
	 * @param c
	 * @return
	 */
	private boolean isLetter(char c) {
		if ( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns if a char is a graphic (any ASCII visible character)
	 * @param c
	 * @return
	 */
	private boolean isGraphic(char c) {
		if ( c >= ' ' && c <= '~' ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Scans the next token
	 * Simulates the DFA that recognizes the language described by the lexical grammar
	 * @return
	 * @throws LexicalException
	 */ //TODO
	private int scanToken() throws LexicalException {
		// The initial automata state is 0
		// While loop to simulate the automata
		
		//DFA do identificador
		if (isLetter(this.currentChar)){
			if(this.verificarIdentificador()){
				return this.verificarPalavrasChaves();
			} else {
				throw new LexicalException("ScanToken de Identificador", this.currentChar, this.line, this.column);
			}
		}
		//DFA dos digitos ou numeros
		else if (isDigit(this.currentChar)){
			int retVerificarNumeros = this.verificarNumero();
			if(retVerificarNumeros == 0){
				return GrammarSymbols.INT;
			}else if(retVerificarNumeros == 1){
				return GrammarSymbols.DOUBLE;
			}else{
				throw new LexicalException("ScanToken de Digitos", this.currentChar, this.line, this.column);
			}
		}
		//DFA de outros tokens (por exemplo :  ;)
		else if (isGraphic(this.currentChar)){
			return this.verificarOutrosTokens();
		}

		//TODO: Verificar esse retorno do -1
		return -1;
	}


	/**
	 * Verifica se o token está no padrão [identifier]
	 * @return true se estiver no padrão, false se não estiver
	 */
	private boolean verificarIdentificador (){
		while (!isSeparator(this.currentChar)){
			if(isLetter(this.currentChar) || isDigit(this.currentChar)){
				this.currentSpelling.append(this.currentChar);
				this.getNextChar();
			}
			else {
				return false;
			}
		}
		return true;
	}

	/**
	 * Verifica se o spelling corrente é uma palavra reservada
	 * se for retorna o tipo correspondente,
	 * se não retorna GrammarSymbols.ID; 
	 * @return
	 */
	private int verificarPalavrasChaves (){
		if(this.currentSpelling.toString().equals("boolean")){
			return GrammarSymbols.BOOLEAN;
		}else if(this.currentSpelling.toString().equals("break")){
			return GrammarSymbols.BREAK;
		}else if(this.currentSpelling.toString().equals("continue")){
			return GrammarSymbols.CONTINUE;
		}else if(this.currentSpelling.toString().equals("double")){
			return GrammarSymbols.DOUBLE;
		}else if(this.currentSpelling.toString().equals("else")){
			return GrammarSymbols.ELSE;
		}else if(this.currentSpelling.toString().equals("if")){
			return GrammarSymbols.IF;
		}else if(this.currentSpelling.toString().equals("int")){
			return GrammarSymbols.INT;
		}else if(this.currentSpelling.toString().equals("return")){
			return GrammarSymbols.RETURN;
		}else if(this.currentSpelling.toString().equals("true")){
			return GrammarSymbols.TRUE;
		}else if(this.currentSpelling.toString().equals("void")){
			return GrammarSymbols.VOID;
		}else if(this.currentSpelling.toString().equals("while")){
			return GrammarSymbols.WHILE;
		}
		return GrammarSymbols.ID;

	}

	/**
	 * Verifica se o token está no padrão [digits] ou [number]
	 *  @return	0 está no padrão [digits], 1 se está no padrão [number], -1 se houve ERRO
	 */
	private int verificarNumero(){
		int estado = 0;
		while (!isSeparator(this.currentChar)){
			if(isDigit(this.currentChar)){
				this.currentSpelling.append(this.currentChar);
				this.getNextChar();
			} else if(this.currentChar == '.' && estado != 1){
				estado = 1;
			} else {
				estado = -1;
				break;
			}
		}
		return estado;
	}

	/**
	 * Verifica se o token é algum simbolo do grupo:
	 *  , ; (  )  {  }  =  *  /  +  -  ==  !=  <  <=  >  >=
	 *  se for retorna o inteiro do tipo correspondente,
	 *  se não for retorna -1
	 * @return
	 */
	private int verificarOutrosTokens(){
		
		int kindRet = -1;
		this.currentSpelling.append(this.currentChar);
		
		switch (this.currentChar){
		// ;  ) | { | } | = | * | / | + | - | == | != | < | <= | > | >= | , |
			case ';' : {
				kindRet = GrammarSymbols.SEMICOLON;
				this.getNextChar();
				break;
			}
			case '(' : {
				kindRet = GrammarSymbols.LPAR; 
				this.getNextChar();
				break;
			}
			case ')' : kindRet = GrammarSymbols.RPAR; break;
			case '{' : kindRet = GrammarSymbols.LBRACKET; break;
			case '}' : kindRet = GrammarSymbols.RBRACKET; break;
			case '*' : kindRet = GrammarSymbols.MULT; break;
			case '/' : kindRet = GrammarSymbols.DIV; break;
			case '+' : kindRet = GrammarSymbols.PLUS; break;
			case '-' : kindRet = GrammarSymbols.MINUS; break;
			case ',' : kindRet = GrammarSymbols.COMMA; break;
			case '=' : {
				kindRet = GrammarSymbols.ASSIGN;
				this.getNextChar();
				if (this.currentChar == '='){
					kindRet = GrammarSymbols.EQUAL;
				}
			}
			
		}
		
		return kindRet;
	}
}
