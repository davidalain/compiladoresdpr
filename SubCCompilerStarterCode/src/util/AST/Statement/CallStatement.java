package util.AST.Statement;

import java.util.ArrayList;

import checker.Visitor;

import util.AST.Terminal.Identifier;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class CallStatement extends Statement {

	private Identifier functionName;
	private ArrayList<Identifier> arguments;
	
	public CallStatement(Identifier function, ArrayList<Identifier> arguments) {
		this.functionName = function;
		this.arguments = arguments;
	}

	public Identifier getFunctionName() {
		return functionName;
	}

	public ArrayList<Identifier> getArguments() {
		return arguments;
	}

	@Override
	public String toString(int level) {
		int nextLevel = level+5;
		StringBuffer str = new StringBuffer();
		str.append("CALS\n");
		if ( this.functionName != null ) {
			str.append(super.getSpaces(nextLevel) + "|-" + this.functionName.toString(nextLevel));
		}
		str.append(super.getSpaces(nextLevel) + "|-ARGS\n");
		if ( this.arguments != null ) {
			for (Identifier argument : this.arguments) {
				str.append(super.getSpaces(nextLevel+5) + "|-");
				str.append(argument.toString(nextLevel+5));
			}
		}
		return str.toString();
	}

	@Override
	public Object visit(Visitor v, Object arg) {
		return v.visitCallStatement(this, arg);
	}
	
}
