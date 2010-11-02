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
 * Todos os identificadores precisam ser declarados antes de serem utilizados.
 */
public class R001 extends TestCase {
	
	private String caminho = "src/testes/semanticos/arquivos/R001/";
	/**
	 * Atribuicao de uma variável não declarada
	 */
	public void testC001()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R001C001.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail("Identificador não declarado");
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.IDENTIFICADOR_NAO_DECLARADO, e.getMessage());
		}
	}
	
	/***
	 * Utilizar uma função não declarada
	 */
	public void testC002()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R001C002.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail("Função não declarada");
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.IDENTIFICADOR_NAO_DECLARADO, e.getMessage());
		}
	}
	
	/***
	 * Declarar uma função já declarada
	 */
	public void testC003()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R001C003.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail("Função já declarada");
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			assertEquals(Mensagens.IDENTIFICADOR_JA_DECLARADO, e.getMessage());
		}
	}
	
	/***
	 * Declarar uma variável já declarada
	 */
	public void testC004()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R001C004.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail("Variável já declarada");
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			assertEquals(Mensagens.IDENTIFICADOR_JA_DECLARADO, e.getMessage());
		}
	}
	
	/***
	 * Declarar uma variável não declarada
	 */
	public void testC005()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R001C005.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (SemanticException e) {
			fail("Variável não declarada");
		}
	}
	
	

}
