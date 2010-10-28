package util.AST.Command;

import java.util.ArrayList;

import checker.Visitor;

import util.AST.Terminal.Identifier;
import util.AST.Terminal.Type;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class FunctionDeclaration extends Command {

	private Type returnType;
	private Identifier functionName;
	private ArrayList<VariableDeclaration> parameters;
	private FunctionBody functionBody;
	
	public FunctionDeclaration(Type type, Identifier function, ArrayList<VariableDeclaration> parameters, FunctionBody functionBody) {
		this.returnType = type;
		this.functionName = function;
		this.parameters = parameters;
		this.functionBody = functionBody;
	}

	public Type getReturnType() {
		return returnType;
	}

	public Identifier getFunctionName() {
		return functionName;
	}

	public ArrayList<VariableDeclaration> getParameters() {
		return parameters;
	}

	public FunctionBody getFunctionBody() {
		return functionBody;
	}

	@Override
	public String toString(int level) {
		int nextLevel = level+5;
		StringBuffer str = new StringBuffer();
		str.append("FUNC\n");
		str.append(super.getSpaces(nextLevel)+"|-");
		if ( this.returnType != null ) {
			str.append(this.returnType.toString(nextLevel));
		}
		str.append(super.getSpaces(nextLevel)+"|-");
		if ( this.functionName != null ) {
			str.append(this.functionName.toString(nextLevel));
		}
		str.append(super.getSpaces(nextLevel) + "|-PARM\n");
		if ( this.parameters != null ) {
			for (VariableDeclaration param : this.parameters) {
				str.append(super.getSpaces(nextLevel+5) + "|-");
				str.append(param.toString(nextLevel+5));
			}
		}
		str.append(super.getSpaces(nextLevel) + "|-BODY\n");
		if ( this.functionBody != null ) {
			str.append(this.functionBody.toString(nextLevel));
		}
		return str.toString();
	}

	@Override
	public Object visit(Visitor v, Object arg) {
		return v.visitFunctionDeclaration(this, arg);
	}
	
}
