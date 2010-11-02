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
 * Break e continue podem ser utilizados somente dentro do escopo de um while
 */
public class R014 extends TestCase {
	
	private String caminho = "src/testes/semanticos/arquivos/R014/";
	
	/**
	 * ERRO: variavel "a" declarada duas vezes
	 */
	public void testC001()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R014C001.subC");
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
	 * SUCESSO: variavel "a" deve ser diferente que variavel "A"
	 */
	public void testC002()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R014C002.subC");
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
	 * SUCESSO: variavel "aBaCaXi" deve ser diferente que variavel "AbAcAxI"
	 */
	public void testC003()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R014C003.subC");
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
