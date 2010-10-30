package util.AST.Terminal;

import util.AST.AST;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public abstract class Terminal extends AST {

	public String spelling;
	
	public Terminal(String spelling) {
		this.spelling = spelling;
	}

	public String getSpelling() {
		return spelling;
	}
	
	
	
	
	
}
