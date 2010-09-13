package util.AST.Statement;

import util.AST.Expression.Expression;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class ReturnStatement extends Statement {

	private Expression returnExpression;
	
	public ReturnStatement(Expression expression) {
		this.returnExpression = expression;
	}

	public Expression getReturnExpression() {
		return returnExpression;
	}

	@Override
	public String toString(int level) {
		int nextLevel = level+5;
		StringBuffer str = new StringBuffer();
		str.append("RETR\n");
		if ( this.returnExpression != null ) {
			str.append(super.getSpaces(nextLevel) + "|-" + this.returnExpression.toString(nextLevel));
		}
		return str.toString();
	}
	
}
