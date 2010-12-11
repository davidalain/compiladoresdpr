package encoder;

public interface InstructionType {
	
	public static final int EXTERN = 0;
	public static final int SECTION = 1;
	public static final int VARIAVEL_GLOBAL = 2;
	public static final int CONSTANTE_DOUBLE = 3; //quando chegar nos *body;
	public static final int FUNCAO_LABEL = 4;
	
	public static final int PUSH = 6;
	public static final int POP = 7;
	public static final int MOV = 8;
	public static final int CMP = 9;
	
	public static final int ADD = 10;
	public static final int SUB = 11;
	public static final int MULT = 12;
	public static final int DIV = 13;
	
	public static final int JUMP = 14;
	public static final int JNE = 15;
	public static final int JE = 16;
	public static final int DESVIA_SE_MENOR_IGUAL_ZERO = 17;
	public static final int RETORNA_DA_SUBROTINA = 18;
	public static final int FLD = 19;
	public static final int ADD_FLOAT = 20;
	public static final int SUB_FLOAT = 21;
	public static final int MULT_FLOAT = 22;
	public static final int DIV_FLOAT = 23;
	
	
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
	public static final String DWORD = "dword";
	public static final String QWORD = "qword";
	public static final String FADDP = "faddp";
	public static final String ST1 = "st1";
	
	
	public static final String FSTP = "fstp";

	
	public static final String EBP = "ebp";
	public static final String ESP = "esp";
	public static final String EAX = "eax";
	public static final String EBX = "ebx";
	
	
	
	
	
}
