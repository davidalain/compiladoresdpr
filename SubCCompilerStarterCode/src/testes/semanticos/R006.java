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
public class R006 extends TestCase {
	
	private String caminho = "src/testes/semanticos/arquivos/R006/";
	
	/**
	 * ERRO: primeiro comando � break
	 */
	public void testC001()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R006C001.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.BREAK_CONTINUE_WHILE);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.BREAK_CONTINUE_WHILE, e.getMessage());
		}
	}
	
	/**
	 * ERRO: primeiro comando � continue
	 */
	public void testC002()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R006C002.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.BREAK_CONTINUE_WHILE);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.BREAK_CONTINUE_WHILE, e.getMessage());
		}
	}
	
	/**
	 * ERRO: break dentro de um if
	 */
	public void testC003()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R006C003.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.BREAK_CONTINUE_WHILE);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.BREAK_CONTINUE_WHILE, e.getMessage());
		}
	}
	
	/**
	 * ERRO: continue dentro de um if
	 */
	public void testC004()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R006C004.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.BREAK_CONTINUE_WHILE);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.BREAK_CONTINUE_WHILE, e.getMessage());
		}
	}

	/**
	 * ERRO: break dentro de um else
	 */
	public void testC005()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R006C005.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.BREAK_CONTINUE_WHILE);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.BREAK_CONTINUE_WHILE, e.getMessage());
		}
	}
	
	/**
	 * ERRO: continue dentro de um else
	 */
	public void testC006()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R006C006.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.BREAK_CONTINUE_WHILE);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.BREAK_CONTINUE_WHILE, e.getMessage());
		}
	}

	/**
	 * SUCESSO: break dentro de um else que esta dentro de um while
	 */
	public void testC007()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R006C007.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			fail(Mensagens.BREAK_CONTINUE_WHILE);
		}
	}

	/**
	 * SUCESSO: CONTINUE dentro de um ELSE que esta dentro de um WHILE
	 */
	public void testC008()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R006C008.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			fail(Mensagens.BREAK_CONTINUE_WHILE);
		}
	}
	
	
}
