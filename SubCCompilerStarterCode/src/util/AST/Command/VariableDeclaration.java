package util.AST.Command;

import util.AST.Terminal.Identifier;
import util.AST.Terminal.Type;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class VariableDeclaration extends Command {

	private Type variableType;
	private Identifier variableName;
	
	public VariableDeclaration(Type variableType, Identifier variableName) {
		this.variableType = variableType;
		this.variableName = variableName;
	}

	public Type getType() {
		return variableType;
	}

	public Identifier getIdentifier() {
		return variableName;
	}

	@Override
	public String toString(int level) {
		int nextLevel = level+5;
		StringBuffer str = new StringBuffer();
		str.append("VRLD\n");
		str.append(super.getSpaces(nextLevel)+"|-");
		if ( this.variableType != null ) {
			str.append(this.variableType.toString(nextLevel));
		}
		str.append(super.getSpaces(nextLevel)+"|-");
		if ( this.variableName != null ) {
			str.append(this.variableName.toString(nextLevel));
		}
		return str.toString();
	}
	
}
