package util.AST.Statement;

import checker.Visitor;


public class ContinueStatement extends Statement {

	@Override
	public String toString(int level) {
		return "CONT\n";
	}

	@Override
	public Object visit(Visitor v, Object arg) {
		return v.visitContinueStatement(this, arg);
	}

}
