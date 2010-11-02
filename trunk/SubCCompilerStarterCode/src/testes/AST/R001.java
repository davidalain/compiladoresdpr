package testes.AST;

import junit.framework.TestCase;
import parser.Parser;
import parser.SyntacticException;
import testes.sintaticos.CaminhosArquivosTeste;
import util.AST.AST;
import util.AST.Program;



public class R001 extends TestCase {
	
	/**
	 * Testa um arquivo fonte totalmente em branco 
	 */
	public void testCasoTeste001()
	{
		Parser p;
		Program ast;
		 
		p = new Parser(CaminhosArquivosTeste.R001C001);
		
		try {
			ast =(Program) p.parse();
			
			assertTrue(ast.getCommands().size() == 0);
			
		} catch (SyntacticException e) {
			fail("Um arquivo totalmente em branco não deve gerar erro");
		}
	}
	
	/**
	 * Testa um arquivo
	 */
	public void testCasoTeste002()
	{
		Parser p;
		AST ast;
		
		p = new Parser(CaminhosArquivosTeste.R001C002);
		
		try {
			ast = p.parse();
			fail("Uma entrada com erro deve gerar exceções sintáticas ou léxicas");
		} catch (SyntacticException e) {
		}
	}


}
