package testes.AST;

import junit.framework.TestCase;
import parser.Parser;
import parser.SyntacticException;
import scanner.LexicalException;
import testes.sintaticos.CaminhosArquivosTeste;
import util.AST.AST;
import util.AST.Program;
import util.AST.Command.FunctionBody;
import util.AST.Command.FunctionDeclaration;

public class R002 extends TestCase{
	
	public void testC001()
	{
		Parser p;
		AST ast;
		
		p = new Parser(CaminhosArquivosTeste.R002C001);
		
		try {
			ast = p.parse();
			
			Program program = (Program) ast;
		
			assertTrue(program.getCommands().size() == 1);
			
			FunctionDeclaration f = (FunctionDeclaration) program.getCommands().get(0);
			
			assertTrue(f.getParameters().size() == 0);
			
			assertTrue(f.getReturnType().getSpelling().equals("void"));
			
			FunctionBody body = f.getFunctionBody();
			
			assertTrue(body.getStatements().size() == 0);
			
		} catch (SyntacticException e) {
			fail();
		}
	}

}
