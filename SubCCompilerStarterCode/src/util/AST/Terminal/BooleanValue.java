package util.AST.Terminal;

import checker.Visitor;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class BooleanValue extends Terminal {

	public BooleanValue(String spelling) {
		super(spelling);
	}

	@Override
	public String toString(int level) {
		return "Boolean(" + this.spelling + ")\n";
	}

	@Override
	public Object visit(Visitor v, Object arg) {
		return v.visitBooleanValue(this, arg);
	}

}
