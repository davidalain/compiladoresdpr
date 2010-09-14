import scanner.Scanner;
import util.Arquivo;


public class testeArquivo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scanner = new Scanner();
		
//		Arquivo arquivo = new Arquivo("program1.subC", "teste.txt");
		
		StringBuffer sb = new StringBuffer("");
		
		sb.append("casa");
		
		System.out.println(sb.toString().equals("casa"));
		
		
		
		
		
		//arquivo.close();
	
	}

}
