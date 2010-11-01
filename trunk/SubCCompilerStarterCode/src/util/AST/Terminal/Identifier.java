package util.AST.Terminal;

import util.AST.AST;
import util.AST.Command.Command;
import checker.SemanticException;
import checker.Visitor;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Identifier extends Terminal {
	
	private Command noDeclaracao;
	
	public Identifier(String spelling) {
		super(spelling);
	}
	
	@Override
	public String toString(int level) {
		return "Identifier(" + this.spelling + ")\n";
	}

	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		return v.visitIdentifier(this, arg);
	}

	public Command getNoDeclaracao() {
		return noDeclaracao;
	}

	public void setNoDeclaracao(Command noDeclaracao) {
		this.noDeclaracao = noDeclaracao;
	}



}
