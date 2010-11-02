package util.AST.RHS;

import util.AST.Statement.CallStatement;
import checker.SemanticException;
import checker.Visitor;

/**
 * AST class
 * @version 2010-september-04
 * @discipline Compiladores
 * @author Gustavo H P Carvalho
 * @email gustavohpcarvalho@ecomp.poli.br
 */
public class CallStatementRHS extends RHS {

	private CallStatement functionCall;
	
	public CallStatementRHS(CallStatement functionCall) {
		this.functionCall = functionCall;
	}

	public CallStatement getFunctionCall() {
		return functionCall;
	}

	@Override
	public String toString(int level) {
		if ( this.functionCall != null ) {
			return this.functionCall.toString(level);
		}
		return null;
	}

	@Override
	public Object visit(Visitor v, Object arg) throws SemanticException {
			
		return v.visitCallStatementRHS(this, arg);
	}
	
}
