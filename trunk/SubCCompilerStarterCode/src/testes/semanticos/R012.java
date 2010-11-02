package testes.semanticos;

import junit.framework.TestCase;
import parser.Parser;
import parser.SyntacticException;
import scanner.LexicalException;
import util.AST.Program;
import checker.Checker;
import checker.SemanticException;


/***
 * 
 * @author diego
 * Operadores ==, !=, >, <, >=, <= retornam valor de tipo boolean.
 */
public class R012 extends TestCase {
	
	private String caminho = "src/testes/semanticos/arquivos/R012/";
	
	/**
	 * SUCESSO: Operadores ==, !=, >, <, >=, <= retornam valor de tipo boolean.
	 */
	public void testC001()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R0012C001.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
		
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			fail("Codigo correto");
		}
	}
	

	
}
