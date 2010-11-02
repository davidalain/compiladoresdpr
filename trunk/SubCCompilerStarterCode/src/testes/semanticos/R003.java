package testes.semanticos;

import junit.framework.TestCase;
import parser.Parser;
import parser.SyntacticException;
import scanner.LexicalException;
import testes.sintaticos.CaminhosArquivosTeste;
import util.Mensagens;
import util.AST.AST;
import util.AST.Program;
import checker.Checker;
import checker.SemanticException;


/***
 * 
 * @author diego
 * Ao chamar uma função os tipos dos argumentos devem ser iguais ao dos parâmetros.
 */
public class R003 extends TestCase {
	
	private String caminho = "src/testes/semanticos/arquivos/R003/";
	
	/**
	 * funcao com nenhum argumento e chamada com um argumento
	 */
	public void testC001()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R003C001.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.ARGUMENTOS_INVALIDOS);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
		}
	}
	
	/**
	 * funcao com um argumento e chamada nenhum argumento
	 */
	public void testC002()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R003C002.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.ARGUMENTOS_INVALIDOS);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.ARGUMENTOS_INVALIDOS, e.getMessage());
		}
	}
	
	/**
	 * funcao com um argumento e chamada com dois argumentos
	 */
	public void testC003()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R003C003.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.ARGUMENTOS_INVALIDOS);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.ARGUMENTOS_INVALIDOS, e.getMessage());
		}
	}
	
	/**
	 * funcao com um argumento e chamada com um argumento de tipo diferente
	 */
	public void testC004()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R003C004.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.ARGUMENTOS_INVALIDOS);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.ARGUMENTOS_INVALIDOS, e.getMessage());
		}
	}
	
	/**
	 * funcao com dois argumentos e chamada com um argumento de tipo igual
	 */
	public void testC005()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R003C005.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.ARGUMENTOS_INVALIDOS);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.ARGUMENTOS_INVALIDOS, e.getMessage());
		}
	}
	
	/**
	 * funcao com dois argumentos e chamada com um argumento de tipo diferente
	 */
	public void testC006()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R003C006.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.ARGUMENTOS_INVALIDOS);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.ARGUMENTOS_INVALIDOS, e.getMessage());
		}
	}
	
	/**
	 * funcao com dois argumentos e chamada dois argumentos de tipos invertidos
	 */
	public void testC007()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R003C007.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.ARGUMENTOS_INVALIDOS);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.ARGUMENTOS_INVALIDOS, e.getMessage());
		}
	}
	
	
}
