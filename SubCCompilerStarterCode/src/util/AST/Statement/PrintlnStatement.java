package util.AST.Statement;

import checker.SemanticException;
import checker.Visitor;
import util.AST.Terminal.Identifier;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class PrintlnStatement extends Statement {

	private Identifier variableName;
	
	public PrintlnStatement(Identifier variable) {
		this.variableName = variable;
	}

	public Identifier getVariableName() {
		return variableName;
	}

	@Override
	public String toString(int level) {
		int nextLevel = level+5;
		StringBuffer str = new StringBuffer();
		str.append("PRNT\n");
		if ( this.variableName != null ) {
			str.append(super.getSpaces(nextLevel) + "|-" + this.variableName.toString(nextLevel));
		}
		return str.toString();
	}

	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		return v.visitPrintlnStatement(this, arg);
	}
	
}
