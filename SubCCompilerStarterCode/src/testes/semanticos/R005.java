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
 * Ao retornar um valor, a função em questão deve ter como retorno o mesmo tipo.
 */
public class R005 extends TestCase {
	
	private String caminho = "src/testes/semanticos/arquivos/R005/";
	/**
	 * função double atribuída a uma variável int
	 */
	public void testC001()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R005C001.subC");
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
	
	/***
	 * Valor boolean atribuido a uma variável double
	 */
	public void testC002()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R005C002.subC");
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
	
	/***
	 * Funcao void retornando int
	 */
	public void testC003()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R005C003.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.RETORNO_INVALIDO);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.RETORNO_INVALIDO, e.getMessage());
		}
	}
	
	/***
	 * Funcao int retornando double
	 */
	public void testC004()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R005C004.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.RETORNO_INVALIDO);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.RETORNO_INVALIDO, e.getMessage());
		}
	}
	

	/***
	 * Funcao int com return ;
	 */
	public void testC005()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R005C005.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.FUNCAO_SEM_RETORNO);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.RETORNO_INVALIDO, e.getMessage());
		}
	}
	
	/***
	 * Funcao double sem retorno
	 */
	public void testC006()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R005C006.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.FUNCAO_SEM_RETORNO);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.RETORNO_INVALIDO, e.getMessage());
		}
	}
	
	/***
	 * SUCESSO: Funcao double com retorno
	 */
	public void testC007()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R005C007.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			fail("C�digo correto");
		}
	}
	
	/***
	 * SUCESSO: Funcao void com return ;
	 */
	public void testC008()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R005C008.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			fail("C�digo correto");
		}
	}
	
	/***
	 * SUCESSO: Funcao void sem retorno
	 */
	public void testC009()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R005C009.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			fail("C�digo correto");
		}
	}
	
	/***
	 * ERRO: Funcao void com retorno int dentro de um if que esta
	 * dentro de um while
	 */
	public void testC010()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R005C010.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.RETORNO_INVALIDO);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.RETORNO_INVALIDO, e.getMessage());
		}
	}
	
	/***
	 * ERRO: Funcao int com retorno "return ;" dentro de um if que esta
	 * dentro de um while
	 */
	public void testC011()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R005C011.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.RETORNO_INVALIDO);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.RETORNO_INVALIDO, e.getMessage());
		}
	}
	
	
	
}
