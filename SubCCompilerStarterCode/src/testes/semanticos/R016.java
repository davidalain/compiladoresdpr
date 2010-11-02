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
 * Não pode haver mais de um identificador (global | local) com o mesmo spelling.
 */
public class R016 extends TestCase {
	
	private String caminho = "src/testes/semanticos/arquivos/R016/";
	
	/**
	 * SUCESSO: 3 funcoes com argumentos de igual spelling
	 */
	public void testC001()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R0016C001.subC");
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
			fail("Código correto");
		}
	}
	
	/**
	 * ERRO: spelling de uma variavel em conflito com o parametro da funcao
	 */
	public void testC002()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R0016C002.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.IDENTIFICADOR_JA_DECLARADO);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (LexicalException e) {
			fail("Erro léxico");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.IDENTIFICADOR_JA_DECLARADO, e.getMessage());
		}
	}
	
	/**
	 * SUCESSO: argumentos e parametros nao causam conflito de spelling
	 */
	public void testC003()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R0016C003.subC");
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
			fail("Código correto");
		}
	}
	
	/**
	 * ERRO: nao pode existir identificador em nenhum escopo igual 
	 * a um identificador global
	 */
	public void testC004()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R0016C004.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.IDENTIFICADOR_JA_DECLARADO);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (LexicalException e) {
			fail("Erro léxico");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.IDENTIFICADOR_JA_DECLARADO, e.getMessage());

		}
	}
	
	/**
	 * SUCESSO: spellings GLOBAL|LOCAL nao devem gerar conflito
	 */
	public void testC005()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R0016C005.subC");
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
			fail("Código correto");

		}
	}
	
}
