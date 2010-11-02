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
 * A expressão na condição de um if e while precisa ser do tipo boolean
 */
public class R004 extends TestCase {
	
	private String caminho = "src/testes/semanticos/arquivos/R004/";
	
	/**
	 * ERRO: Condição da expressão if é uma variável de tipo int 
	 */
	public void testC001()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R004C001.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.BOOLEAN_IF_WHILE);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (LexicalException e) {
			fail("Erro léxico");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.BOOLEAN_IF_WHILE, e.getMessage());
		}
	}
	
	/**
	 * ERRO: Condição da expressão if é uma variável de tipo double
	 */
	public void testC002()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R004C002.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.BOOLEAN_IF_WHILE);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (LexicalException e) {
			fail("Erro léxico");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.BOOLEAN_IF_WHILE, e.getMessage());
		}
	}
	
	/**
	 * ERRO: Condição da expressão while é uma variável de tipo int
	 */
	public void testC003()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R004C003.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.BOOLEAN_IF_WHILE);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (LexicalException e) {
			fail("Erro léxico");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.BOOLEAN_IF_WHILE, e.getMessage());
		}
	}
	
	/**
	 * ERRO: Condição da expressão while é uma variável de tipo double
	 */
	public void testC004()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R004C004.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.BOOLEAN_IF_WHILE);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (LexicalException e) {
			fail("Erro léxico");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.BOOLEAN_IF_WHILE, e.getMessage());
		}
	}
	

	
	
	/**
	 * ERRO: Condição da expressão if é a constante `1` 
	 */
	public void testC005()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R004C005.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.BOOLEAN_IF_WHILE);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (LexicalException e) {
			fail("Erro léxico");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.BOOLEAN_IF_WHILE, e.getMessage());
		}
	}
	
	/**
	 * ERRO: Condição da expressão if é a constante `1.0` 
	 */
	public void testC006()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R004C006.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.BOOLEAN_IF_WHILE);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (LexicalException e) {
			fail("Erro léxico");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.BOOLEAN_IF_WHILE, e.getMessage());
		}
	}
	
	/**
	 * SUCESSO: Condição da expressão if é uma variável de tipo boolean 
	 */
	public void testC007()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R004C007.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (LexicalException e) {
			fail("Erro léxico");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			fail(Mensagens.BOOLEAN_IF_WHILE);
		}
	}
	
	
	
	/**
	 * SUCESSO: Condição da expressão if é a constante 'false' 
	 */
	public void testC008()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R004C008.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (LexicalException e) {
			fail("Erro léxico");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			fail(Mensagens.BOOLEAN_IF_WHILE);
		}
	}
	
	/**
	 * ERRO: Condição da expressão while é a constante `1` 
	 */
	public void testC009()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R004C009.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.BOOLEAN_IF_WHILE);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (LexicalException e) {
			fail("Erro léxico");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.BOOLEAN_IF_WHILE, e.getMessage());
		}
	}
	
	/**
	 * ERRO: Condição da expressão while é a constante `1.0` 
	 */
	public void testC010()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R004C010.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.BOOLEAN_IF_WHILE);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (LexicalException e) {
			fail("Erro léxico");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.BOOLEAN_IF_WHILE, e.getMessage());
		}
	}
	
	/**
	 * SUCESSO: Condição da expressão while é uma variável de tipo boolean 
	 */
	public void testC011()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R004C011.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (LexicalException e) {
			fail("Erro léxico");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			fail(Mensagens.BOOLEAN_IF_WHILE);
		}
	}
	
	/**
	 * ERRO: Condição da expressão if vazia
	 */
	public void testC012()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R004C012.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.BOOLEAN_IF_WHILE);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (LexicalException e) {
			fail("Erro léxico");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.BOOLEAN_IF_WHILE, e.getMessage());
		}
	}
	
	/**
	 * ERRO: Condição da expressão while vazia
	 */
	public void testC013()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R004C013.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.BOOLEAN_IF_WHILE);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (LexicalException e) {
			fail("Erro léxico");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.BOOLEAN_IF_WHILE, e.getMessage());
		}
	}
	
	
	
	
	
	
}
