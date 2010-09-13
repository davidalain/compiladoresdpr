package util.AST;

import java.util.ArrayList;

import util.AST.Command.Command;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class Program extends AST {

	private ArrayList<Command> commands = null;
	
	public Program(ArrayList<Command> commands) {
		this.commands = commands;
	}

	public ArrayList<Command> getCommands() {
		return commands;
	}
	
	@Override	
	public String toString(int level) {
		int nextLevel = level+3;
		StringBuffer str = new StringBuffer();
		str.append("PROG\n");
		if ( this.commands != null ) {
			for (Command command : this.commands) {
				str.append(super.getSpaces(nextLevel) + "|-");
				str.append(command.toString(nextLevel));
			}
		}
		return str.toString();
	}
	
}
