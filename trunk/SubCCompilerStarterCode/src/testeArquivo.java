import java.util.ArrayList;

import parser.Parser;
import parser.SyntacticException;
import util.AST.Program;
import checker.Checker;
import encoder.Encoder;


public class testeArquivo {

	/**
	 * @param args
	 * @throws SyntacticException 
	 */
	public static void main(String[] args) throws SyntacticException {
		// TODO Auto-generated method stub

		/**
		 * Scanner scanner = new Scanner();
		 * Token token = scanner.getNextToken();
		 * while (token.getKind() != GrammarSymbols.EOT){
		 *  System.out.print(token.getSpelling() + "\n");
		 *  token = scanner.getNextToken();
		 * }
		 */
		try{
			Parser parser = new Parser();
			Program prog = (Program) parser.parse();
			Checker checker = new Checker();
			Encoder encoder = new Encoder();
			checker.check(prog);
			encoder.encode(prog);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

}
