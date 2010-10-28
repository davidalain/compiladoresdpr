package util.AST.Statement;

import java.util.ArrayList;

import checker.Visitor;

import util.AST.Expression.Expression;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class IfElseStatement extends Statement {

	private Expression condition;
	private ArrayList<Statement> ifStatements;
	private ArrayList<Statement> elseStatements;
	
	public IfElseStatement(Expression condition, ArrayList<Statement> ifStatements, ArrayList<Statement> elseStatements) {
		this.condition = condition;
		this.ifStatements = ifStatements;
		this.elseStatements = elseStatements;
	}

	public Expression getCondition() {
		return condition;
	}

	public ArrayList<Statement> getIfStatements() {
		return ifStatements;
	}

	public ArrayList<Statement> getElseStatements() {
		return elseStatements;
	}

	@Override
	public String toString(int level) {
		int nextLevel = level+5;
		StringBuffer str = new StringBuffer();
		str.append("IEST\n");
		str.append(super.getSpaces(nextLevel) + "|-COND\n");
		str.append(super.getSpaces(nextLevel+5) + "|-");
		if ( this.condition != null ) {
			str.append(this.condition.toString(nextLevel+5));
		}
		str.append(super.getSpaces(nextLevel) + "|-IFST\n");
		if ( this.ifStatements != null ) {
			for (Statement statement : this.ifStatements) {
				str.append(super.getSpaces(nextLevel+5) + "|-");			
				str.append(statement.toString(nextLevel+5));
			}
		}
		str.append(super.getSpaces(nextLevel) + "|-ELST\n");
		if ( this.elseStatements != null ) {
			for (Statement statement : this.elseStatements) {
				str.append(super.getSpaces(nextLevel+5) + "|-");	
				str.append(statement.toString(nextLevel+5));
			}
		}
		return str.toString();
	}

	@Override
	public Object visit(Visitor v, Object arg) {
		return v.visitIfElseStatement(this, arg);
	}

}
