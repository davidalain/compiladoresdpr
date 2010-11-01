package util.AST.RHS;

import util.AST.AST;
import util.AST.Terminal.Type;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public abstract class RHS extends AST {
	private Type tipo ;

	public Type getTipo() {
		return tipo;
	}

	public void setTipo(Type tipo) {
		this.tipo = tipo;
	}
	
	
}
