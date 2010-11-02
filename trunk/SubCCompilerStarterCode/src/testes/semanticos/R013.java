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
 * Em A = B, o tipo de B precisa ser igual ao tipo de A e A é uma variável. 
 */
public class R013 extends TestCase {
	
	private String caminho = "src/testes/semanticos/arquivos/R013/";
	
	/**
	 * ERRO: Funcao DOUBLE atribuída a uma variavel INT
	 */
	public void testC001()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R0013C001.subC");
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
	 * ERRO: atribuicao de uma variavel int a uma funcao int
	 */
	public void testC002()
	{
		Parser p;
		Program ast;
		Checker check; 
		
		p = new Parser(caminho+"R0013C002.subC");
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
