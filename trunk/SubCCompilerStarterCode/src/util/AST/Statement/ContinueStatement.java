package util.AST.Statement;

import checker.Visitor;


public class ContinueStatement extends Statement {

	@Override
	public String toString(int level) {
		return "CONT\n";
	}

	@Override
	public Object visit(Visitor v, Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

}
