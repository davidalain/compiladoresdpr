package util.AST.Terminal;

import checker.SemanticException;
import checker.Visitor;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Operator extends Terminal {

	public Operator(String spelling) {
		super(spelling);
	}
	
	@Override
	public String toString(int level) {
		return "Operator(" + this.spelling + ")\n";
	}

	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		return v.visitOperator(this, arg);
	}	

}
