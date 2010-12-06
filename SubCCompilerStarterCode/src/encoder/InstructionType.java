package encoder;

public interface InstructionType {
	
	public static final int EXTERN = 0;
	public static final int SECTION = 1;
	public static final int VARIAVEL_GLOBAL = 2;
	public static final int CONSTANTE_DOUBLE = 3; //quando chegar nos *body;
	public static final int FUNCAO = 4;
	
	
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
	
	
	
}
