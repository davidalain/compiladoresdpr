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
 * Operadores +, -, * e / devem ser aplicados a operandos int ou double.
 */
public class R008 extends TestCase {
	
	private String caminho = "src/testes/semanticos/arquivos/R008/";
	
	/**
	 * ERRO: contante INT + constante FALSE
	 */
	public void testC001()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R008C001.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.LADOS_DIFERENTES);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (LexicalException e) {
			fail("Erro léxico");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.LADOS_DIFERENTES, e.getMessage());
		}
	}
	
	/**
	 * ERRO: contante DOUBLE + constante FALSE
	 */
	public void testC002()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R008C002.subC");
		check = new Checker();
		try {
			ast =(Program) p.parse();
			
			check.check(ast);
			fail(Mensagens.LADOS_DIFERENTES);
			
		} catch (SyntacticException e) {
			e.printStackTrace();
			fail("Erro sintático");
		} catch (LexicalException e) {
			fail("Erro léxico");
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			assertEquals(Mensagens.LADOS_DIFERENTES, e.getMessage());
		}
	}
	
	
	
	

	
}
