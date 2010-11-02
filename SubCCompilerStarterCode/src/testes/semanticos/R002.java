package testes.semanticos;

import junit.framework.TestCase;
import parser.Parser;
import parser.SyntacticException;
import util.Mensagens;
import util.AST.Program;
import checker.Checker;
import checker.SemanticException;


/***
 * 
 * @author diego
 * O tipo void só pode ser aplicado no contexto de retorno de função.
 */
public class R002 extends TestCase {
	
	private String caminho = "src/testes/semanticos/arquivos/R002/";
	/**
	 * declaração de uma variável do tipo void
	 */
	public void testC001()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R002C001.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.VOID_NAO_RETORNO);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.VOID_NAO_RETORNO, e.getMessage());
		}
	}
	
	/**
	 * declaração de uma função do tipo void
	 */
	public void testC002()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R002C002.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			fail("Código correto");
		}
	}
	
	/**
	 * declaração de variáveis de tipo int, double e boolean
	 */
	public void testC003()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R002C003.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			fail("Código correto");
		}
	}
	
	/**
	 * declaração de função com um argumento do tipo void
	 */
	public void testC004()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R002C004.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			
			fail(Mensagens.VOID_NAO_RETORNO);
			
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.VOID_NAO_RETORNO, e.getMessage());
		}
	}
	
}
