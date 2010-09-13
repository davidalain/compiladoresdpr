package util.AST.Expression;

import util.AST.Terminal.NumberValue;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class NumberUnaryExpression extends UnaryExpression {

	private NumberValue numberValue;
	
	public NumberUnaryExpression(NumberValue numberValue) {
		this.numberValue = numberValue;
	}

	public NumberValue getNumberValue() {
		return numberValue;
	}

	@Override
	public String toString(int level) {
		int nextLevel = level+5;
		StringBuffer str = new StringBuffer();
		str.append("NUEX\n");		
		if ( this.numberValue != null ) {
			str.append(super.getSpaces(nextLevel)+"|-"+this.numberValue.toString(level));
		}
		return str.toString();
	}
	
}
