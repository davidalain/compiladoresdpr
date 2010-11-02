import parser.Parser;
import parser.SyntacticException;
import util.AST.Program;
import checker.Checker;


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
			checker.check(prog);
			
//			System.out.println("\nArvore AST:\n\n"+parser.parse().toString(40));
			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("FIM");
		
		
	}

}
