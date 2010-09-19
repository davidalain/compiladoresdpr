import parser.GrammarSymbols;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;
import scanner.Token;
import util.Arquivo;


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
		
		Parser parser = new Parser();
		parser.parse();
		
		
	}

}
