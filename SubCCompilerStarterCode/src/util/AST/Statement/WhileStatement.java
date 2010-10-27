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
public class WhileStatement extends Statement {

	private Expression condition;
	private ArrayList<Statement> statements;
	
	public WhileStatement(Expression condition, ArrayList<Statement> statements) {
		this.condition = condition;
		this.statements = statements;
	}

	public Expression getCondition() {
		return condition;
	}

	public ArrayList<Statement> getStatements() {
		return statements;
	}

	@Override
	public String toString(int level) {
		int nextLevel = level+5;
		StringBuffer str = new StringBuffer();
		str.append("WHIL\n");
		str.append(super.getSpaces(nextLevel) + "|-COND\n");
		str.append(super.getSpaces(nextLevel+5) + "|-");
		if ( this.condition != null ) {
			str.append(this.condition.toString(nextLevel+5));
		}
		str.append(super.getSpaces(nextLevel) + "|-WLST\n");
		if ( this.statements != null ) {
			for (Statement statement : this.statements) {
				str.append(super.getSpaces(nextLevel+5) + "|-");			
				str.append(statement.toString(nextLevel+5));
			}
		}
		return str.toString();
	}

	@Override
	public Object visit(Visitor v, Object arg) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
