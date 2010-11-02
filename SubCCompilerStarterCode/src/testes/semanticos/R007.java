package testes.semanticos;

import junit.framework.TestCase;
import parser.Parser;
import parser.SyntacticException;
import scanner.LexicalException;
import util.Mensagens;
import util.AST.Program;
import checker.Checker;
import checker.SemanticException;


/***
 * 
 * @author diego
 * Break e continue podem ser utilizados somente dentro do escopo de um while
 */
public class R007 extends TestCase {
	
	private String caminho = "src/testes/semanticos/arquivos/R007/";
	
	/**
	 * ERRO: int + double
	 */
	public void testC001()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R007C001.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.LADOS_DIFERENTES);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.LADOS_DIFERENTES, e.getMessage());
		}
	}
	
	
	/**
	 * ERRO: variavel double maior que uma variavel int
	 */
	public void testC003()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R007C003.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.LADOS_DIFERENTES);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.LADOS_DIFERENTES, e.getMessage());
		}
	}
	
	/**
	 * ERRO: operador <= aplicado a operandos de tipos diferentes
	 */
	public void testC004()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R007C004.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.LADOS_DIFERENTES);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.LADOS_DIFERENTES, e.getMessage());
		}
	}
	

	
}
