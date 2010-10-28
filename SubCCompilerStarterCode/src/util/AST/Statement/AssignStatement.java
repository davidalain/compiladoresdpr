package util.AST.Statement;

import checker.Visitor;
import util.AST.RHS.RHS;
import util.AST.Terminal.Identifier;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class AssignStatement extends Statement {

	private Identifier variableName;
	private RHS rightHandStatement;
	
	public AssignStatement(Identifier variable, RHS rhs) {
		this.variableName = variable;
		this.rightHandStatement = rhs;
	}

	public Identifier getVariableName() {
		return variableName;
	}

	public RHS getRightHandStatement() {
		return rightHandStatement;
	}

	@Override
	public String toString(int level) {
		int nextLevel = level+5;
		StringBuffer str = new StringBuffer();
		str.append("ASGN\n");
		if ( this.variableName != null ) {
			str.append(super.getSpaces(nextLevel) + "|-" + this.variableName.toString(nextLevel));
		}
		if ( this.rightHandStatement != null ) {
			str.append(super.getSpaces(nextLevel) + "|-" + this.rightHandStatement.toString(nextLevel));
		}
		return str.toString();
	}

	@Override
	public Object visit(Visitor v, Object arg) {
		return v.visitAssignStatement(this, arg);
	}
	
}
