package scanner;

import parser.GrammarSymbols;
import util.Arquivo;

import compiler.Properties;

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
	 * @throws LexicalException 
	 */
	public Token getNextToken() throws LexicalException{
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
				e.printStackTrace();
			}
		}

		this.currentSpelling = new StringBuffer(""); // ou apagar o conteúdo do buffer		
		
		this.currentKind = this.scanToken();
		//System.out.println("[getNextToken()] kindName: "+GrammarSymbols.getNameByKind(this.currentKind)+"\t, spelling: "+this.currentSpelling.toString());

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
	 */
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
				throw new LexicalException("ScanSeparator", this.currentChar, this.line, this.column);
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
	 */
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
				
			}else if(retVerificarNumeros == 2){
				return GrammarSymbols.DOUBLE;
				
			}else{
				throw new LexicalException("ScanToken de Digitos", this.currentChar, this.line, this.column);
			}
		}
		//DFA de outros tokens (por exemplo :  ;)
		else if (isGraphic(this.currentChar)){
			int retVerificarOutrosTokens = this.verificarOutrosTokens();
			if (retVerificarOutrosTokens == -1){
				throw new LexicalException("ScanToken outros Tokens", this.currentChar, this.line, this.column);
				
			} else {
				return retVerificarOutrosTokens;
			}
		}

		//Se não tiver mais caractere a ler no arquivo
		//e também aceitar arquivo vazio.
		return GrammarSymbols.EOT;

	}


	/**
	 * Verifica se o token está no padrão [identifier]
	 * @return true se estiver no padrão, false se não estiver
	 */
	private boolean verificarIdentificador (){

		while (!isSeparator(this.currentChar) && !isSpecialCharacters(this.currentChar)){
			if(isLetter(this.currentChar) || isDigit(this.currentChar)){
				this.getNextChar();
			}
			else {
				return false;
			}
		}
		return true;
	}

	private boolean isSpecialCharacters(char pCurrentChar) {
		if(	pCurrentChar == '(' || pCurrentChar == ';' || pCurrentChar == ')'
			|| pCurrentChar == ',' || pCurrentChar == '=' || pCurrentChar == '+'
				|| pCurrentChar == '{' || pCurrentChar == '}' 	
					|| pCurrentChar == '<' || pCurrentChar == '>' 	
						|| pCurrentChar == '/' || pCurrentChar == '*'
							|| pCurrentChar == '-' 	 	


		){
			return true;
		}
		return false;
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
		}
		if(this.currentSpelling.toString().equals("break")){
			return GrammarSymbols.BREAK;
		}
		if(this.currentSpelling.toString().equals("continue")){
			return GrammarSymbols.CONTINUE;
		}
		if(this.currentSpelling.toString().equals("double")){
			return GrammarSymbols.DOUBLE;
		}
		if(this.currentSpelling.toString().equals("else")){
			return GrammarSymbols.ELSE;
		}
		if(this.currentSpelling.toString().equals("if")){
			return GrammarSymbols.IF;
		}
		if(this.currentSpelling.toString().equals("int")){
			return GrammarSymbols.INT;
		}
		if(this.currentSpelling.toString().equals("return")){
			return GrammarSymbols.RETURN;
		}
		if(this.currentSpelling.toString().equals("true")){
			return GrammarSymbols.TRUE;
		}
		if(this.currentSpelling.toString().equals("void")){
			return GrammarSymbols.VOID;
		}
		if(this.currentSpelling.toString().equals("while")){
			return GrammarSymbols.WHILE;
		}

		return GrammarSymbols.ID;
	}

	/**
	 * Verifica se o token está no padrão [digits] ou [number]
	 *  @return	0 está no padrão [digits], 2 se está no padrão [number], 1 ou -1 se houve ERRO
	 */
	private int verificarNumero(){
		int estado = 0;
		while (!isSeparator(this.currentChar) && !isSpecialCharacters(this.currentChar)){
			if(isDigit(this.currentChar)){
				
				if(estado == 0){
					//está lendo numero e ainda não encontrou o caractere ponto [.]
					this.getNextChar();
					
				}else if(estado == 1){
					//começou a ler numeros depois de encontrar o caractere ponto [.]
					estado = 2;
					this.getNextChar();
					
				}else{ //estado == 2
					this.getNextChar();
				}
				
			} else if(this.currentChar == '.' && estado == 0){
				//encontrou o caractere ponto [.]
				estado = 1;
				this.getNextChar();
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

		switch (this.currentChar){
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
		case ')' : {
			kindRet = GrammarSymbols.RPAR; 
			this.getNextChar();
			break;
		}
		case '{' : {
			kindRet = GrammarSymbols.LBRACKET; 
			this.getNextChar();
			break;
		}
		case '}' : {
			kindRet = GrammarSymbols.RBRACKET; 
			this.getNextChar();
			break;
		}
		case '*' : {
			kindRet = GrammarSymbols.MULT; 
			this.getNextChar();
			break;
		}
		case '/' : {
			kindRet = GrammarSymbols.DIV; 
			this.getNextChar();
			break;
		}
		case '+' : {
			kindRet = GrammarSymbols.PLUS; 
			this.getNextChar();
			break;
		}
		case '-' : {
			kindRet = GrammarSymbols.MINUS; 
			this.getNextChar();
			break;
		}
		case ',' : {
			kindRet = GrammarSymbols.COMMA; 
			this.getNextChar();
			break;
		}
		case '=' : {
			kindRet = GrammarSymbols.ASSIGN;
			this.getNextChar();
			if (this.currentChar == '='){
				kindRet = GrammarSymbols.EQUAL;
				this.getNextChar();
			}
			break;
		}
		case '!' : {
			this.getNextChar();
			if (this.currentChar == '='){
				kindRet = GrammarSymbols.NOTEQUAL;
				this.getNextChar();
			}
			break;
		}
		case '<' : {
			kindRet = GrammarSymbols.LESSERTHAN;
			this.getNextChar();
			if (this.currentChar == '='){
				kindRet = GrammarSymbols.LESSEREQUALTHAN;
				this.getNextChar();
			}
			break;
		}
		case '>' : {
			kindRet = GrammarSymbols.GREATERTHAN;
			this.getNextChar();
			if (this.currentChar == '='){
				kindRet = GrammarSymbols.GREATEREQUALTHAN;
				this.getNextChar();
			}
			break;
		}
		}

		return kindRet;
	}
}
