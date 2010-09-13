package util.AST.Terminal;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Identifier extends Terminal {

	public Identifier(String spelling) {
		super(spelling);
	}
	
	@Override
	public String toString(int level) {
		return "Identifier(" + this.spelling + ")\n";
	}	

}
