package util.AST.RHS;

import checker.SemanticException;
import checker.Visitor;
import util.AST.Expression.Expression;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class ExpressionRHS extends RHS {

	private Expression expression;
	
	public ExpressionRHS(Expression expression) {
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public String toString(int level) {
		if ( this.expression != null ) {
			return this.expression.toString(level);
		}
		return null;
	}

	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		return v.visitExpressionRHS(this, arg);
	}
	
}
