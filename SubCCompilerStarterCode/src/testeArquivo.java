import parser.GrammarSymbols;
import scanner.Scanner;
import scanner.Token;
import util.Arquivo;


public class testeArquivo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scanner = new Scanner();
		Token token = scanner.getNextToken();
		while (token.getKind() != GrammarSymbols.EOT){
			System.out.print(token.getSpelling() + " ");
			token = scanner.getNextToken();
		}
		
	}

}
