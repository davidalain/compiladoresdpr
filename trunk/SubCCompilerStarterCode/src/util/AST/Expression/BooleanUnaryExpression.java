package util.AST.Expression;

import util.AST.Terminal.BooleanValue;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class BooleanUnaryExpression extends UnaryExpression {

	private BooleanValue booleanValue;
	
	public BooleanUnaryExpression(BooleanValue booleanValue) {
		this.booleanValue = booleanValue;
	}

	public BooleanValue getBooleanValue() {
		return booleanValue;
	}

	@Override
	public String toString(int level) {
		int nextLevel = level+5;
		StringBuffer str = new StringBuffer();
		str.append("BUEX\n");
		if ( this.booleanValue != null ) {
			str.append(super.getSpaces(nextLevel)+"|-"+this.booleanValue.toString(level));
		}
		return str.toString();
	}

}
