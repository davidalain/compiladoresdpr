package util.AST.Statement;

import checker.Visitor;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class BreakStatement extends Statement {

	@Override
	public String toString(int level) {
		return "BRKS\n";
	}

	@Override
	public Object visit(Visitor v, Object arg) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
