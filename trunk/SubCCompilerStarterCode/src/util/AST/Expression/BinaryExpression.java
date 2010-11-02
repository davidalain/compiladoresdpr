package util.AST.Expression;

import util.AST.Terminal.Operator;
import checker.SemanticException;
import checker.Visitor;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class BinaryExpression extends Expression {

	private Expression leftExpression;
	private Operator operator;
	private Expression rightExpression;
	
	public BinaryExpression(Expression leftExpression, Operator operator, Expression rightExpression) {
		this.leftExpression = leftExpression;
		this.operator = operator;
		this.rightExpression = rightExpression;
	}

	public Expression getLeftExpression() {
		return leftExpression;
	}

	public Operator getOperator() {
		return operator;
	}

	public Expression getRightExpression() {
		return rightExpression;
	}

	@Override
	public String toString(int level) {
		int nextLevel = level+5;
		StringBuffer str = new StringBuffer();
		str.append("BINE\n");
		if ( this.leftExpression != null ) {
			str.append(super.getSpaces(nextLevel) + "|-" + this.leftExpression.toString(nextLevel));
		}
		if ( this.operator != null ) {
			str.append(super.getSpaces(nextLevel) + "|-" + this.operator.toString(nextLevel));
		}
		if ( this.rightExpression != null ) {
			str.append(super.getSpaces(nextLevel) + "|-" + this.rightExpression.toString(nextLevel));
		}
		return str.toString();
	}

	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		return v.visitBinaryExpression(this, arg);
	}
	
}
