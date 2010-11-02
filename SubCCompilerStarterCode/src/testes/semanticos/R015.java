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
 * Todo código deve ter uma função void main() { ... }.
 */
public class R015 extends TestCase {
	
	private String caminho = "src/testes/semanticos/arquivos/R015/";
	
	/**
	 * Código em branco
	 */
	public void testC001()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R0015C001.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.FUNCAO_MAIN);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.FUNCAO_MAIN, e.getMessage());
		}
	}
	
	
	
	
	/**
	 * Codigo com outra função que não é main
	 */
	public void testC002()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R0015C002.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail("Todo código deve ter uma função void main() { ... }.");
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.FUNCAO_MAIN, e.getMessage());
		}
	}
	
	/**
	 * Codigo com duas funções que não são main
	 */
	public void testC003()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R0015C003.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.FUNCAO_MAIN);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.FUNCAO_MAIN, e.getMessage());
		}
	}
	
	/**
	 * main com tipo de retorno int
	 */
	public void testC004()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R0015C004.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail("a função void main() { ... }. deve retornar void");
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.FUNCAO_MAIN, e.getMessage());
		}
	}
	
	/**
	 * dois métodos e o primeiro é main
	 */
	public void testC005()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R0015C005.subC");
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
	 * dois métodos e o segundo é main
	 */
	public void testC006()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R0015C006.subC");
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
	 * dois mains
	 */
	public void testC007()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R0015C007.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.IDENTIFICADOR_JA_DECLARADO);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.IDENTIFICADOR_JA_DECLARADO, e.getMessage());
		}
	}
	
	/**
	 * main com um argumento int
	 */
	public void testC008()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R0015C008.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.FUNCAO_MAIN);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.FUNCAO_MAIN, e.getMessage());
		}
	}
	
	
	/**
	 * main com um argumento int e um argumento boolean
	 * @throws SemanticException 
	 */
	public void testC009() throws SemanticException
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R0015C009.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.FUNCAO_MAIN);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		}
	}
	

}
