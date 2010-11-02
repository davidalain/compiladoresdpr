package util.AST.Command;

import java.util.ArrayList;

import util.AST.AST;
import util.AST.Statement.Statement;
import checker.SemanticException;
import checker.Visitor;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class FunctionBody extends AST {

	private ArrayList<VariableDeclaration> variables;
	private ArrayList<Statement> statements;
	
	public FunctionBody(ArrayList<VariableDeclaration> variables, ArrayList<Statement> statements) {
		this.variables = variables;
		this.statements = statements;
	}

	public ArrayList<VariableDeclaration> getVariables() {
		return variables;
	}

	public ArrayList<Statement> getStatements() {
		return statements;
	}

	@Override
	public String toString(int level) {
		int nextLevel = level+5;
		StringBuffer str = new StringBuffer();
		if ( this.variables != null ) {
			for (VariableDeclaration var : this.variables) {
				str.append(super.getSpaces(nextLevel) + "|-");
				str.append(var.toString(nextLevel));
			}
		}
		if ( this.statements != null ) {
			for (Statement statement : this.statements) {
				str.append(super.getSpaces(nextLevel) + "|-");
				str.append(statement.toString(nextLevel));
			}
		}
		return str.toString();
	}

	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
		return v.visitFunctionBody(this, arg);
	}
	
}
