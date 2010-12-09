package encoder;

public interface InstructionType {
	
	public static final int EXTERN = 0;
	public static final int SECTION = 1;
	public static final int VARIAVEL_GLOBAL = 2;
	public static final int CONSTANTE_DOUBLE = 3; //quando chegar nos *body;
	public static final int FUNCAO_LABEL = 4;
	
	public static final int EMPILHAR = 6;
	public static final int DESEMPILHAR = 7;
	public static final int MOVER = 8;
	public static final int COMPARACAO = 9;
	
	public static final int SOMA = 10;
	public static final int SUBTRACAO = 11;
	public static final int MULTIPLICACAO = 12;
	public static final int DIVISAO = 13;
	
	public static final int DESVIA = 5;
	public static final int DESVIA_SE_DIFERENTE = 5;
	public static final int DESVIA_SE_IGUAL = 5;
	public static final int DESVIA_SE_MENOR_IGUAL_ZERO = 5;
	public static final int RETORNA_DA_SUBROTINA = 5;
	
	
	public static final String PRINTF = "_printf";
	public static final String DATA = ".data";
	public static final String TEXT = ".text";
	public static final String NAME_EXTERN = "extern";
	public static final String NAME_SECTION = "SECTION";
	public static final String GLOBAL = "global";
	public static final String WINMAIN = "_WinMain@16";
	public static final String TIPO_INT = "dd";
	public static final String TIPO_DOUBLE = "dq";
	public static final String INT_FORMAT = "intFormat";
	public static final String DOUBLE_FORMAT = "doubleFormat";
	public static final String CONSTANTE = "const";
	
	public static final String EBP = "ebp";
	public static final String ESP = "esp";
	public static final String EAX = "eax";
	public static final String EBX = "ebx";
	
	
	
	
	
}
