package util.AST.Terminal;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class NumberValue extends Terminal {

	public NumberValue(String spelling) {
		super(spelling);
	}
	
	@Override
	public String toString(int level) {
		return "NumberValue(" + this.spelling + ")\n";
	}	

}
