package util.AST.Expression;

import util.AST.Terminal.Identifier;
import checker.SemanticException;
import checker.Visitor;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class IdentifierUnaryExpression extends UnaryExpression {

	private Identifier variableName;
	
	public IdentifierUnaryExpression(Identifier variableName) {
		this.variableName = variableName;
	}

	public Identifier getVariableName() {
		return variableName;
	}

	@Override
	public String toString(int level) {
		int nextLevel = level+5;
		StringBuffer str = new StringBuffer();
		str.append("IUEX\n");	
		if ( this.variableName != null ) {
			str.append(super.getSpaces(nextLevel)+"|-"+this.variableName.toString(level));
		}
		return str.toString();
	}

	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		return v.visitIdentifierUnaryExpression(this, arg);
	}
	
}
