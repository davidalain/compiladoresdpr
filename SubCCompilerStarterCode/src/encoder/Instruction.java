package encoder;


public class Instruction {

	private int tipo; // op-code (0 .. 15)
	private String op1; // register field (0 .. 15)
	private String op2; // length field (0 .. 255)
	private String op3; // operand field (-32767 .. +32767)
	private StringBuffer retorno;
	private String sinal;
	
	public Instruction(int tipo, String op1, String op2, String op3) {
		this.tipo = tipo;
		this.op1 = op1;
		this.op2 = op2;
		this.op3 = op3;
		this.retorno = new StringBuffer();
	}

	public Instruction(int tipo, String op1, String op2, String op3,
			String sinal) {
		this.tipo = tipo;
		this.op1 = op1;
		this.op2 = op2;
		this.op3 = op3;
		this.sinal = sinal;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getOp1() {
		return op1;
	}

	public void setOp1(String op1) {
		this.op1 = op1;
	}

	public String getOp2() {
		return op2;
	}

	public void setOp2(String op2) {
		this.op2 = op2;
	}
	public String getOp3() {
		return op3;
	}

	public void setOp3(String op3) {
		this.op3 = op3;
	}

	private String criarInstrucao(){
		String retorno = null;
		switch (this.getTipo()) {
		
		case InstructionType.EXTERN:{
			retorno =  this.criarInstrucaoExtern();
			break;
		}
		case InstructionType.SECTION:{
			this.retorno.append(InstructionType.NAME_SECTION);
			this.retorno.append(" ");
			this.retorno.append(this.getOp1());
			if (getOp1().equals(InstructionType.TEXT)){
				retorno =  this.criarInstrucaoSectionText();
			}
			else if (getOp1().equals(InstructionType.DATA)){
				retorno =  this.criarInstrucaoSectionData();

			}
			break;
		}
		case InstructionType.VARIAVEL_GLOBAL:{
			retorno = this.criarInstrucaoVariavelGlobal();
			break;
		}
		case InstructionType.FUNCAO_LABEL:{
			retorno = this.criarInstrucaoFuncao();
			break;
		}
		case InstructionType.PUSH:{
			retorno = this.criarInstrucaoPush();
			break;
		}
		case InstructionType.PUSH_FLOAT:{
			retorno = this.criarInstrucaoPushFloat();
			break;
		}
		case InstructionType.MOV:{
			retorno = "mov " + this.getOp1() + ", " + this.getOp2();
			break;
		}
		case InstructionType.SUB:{
			retorno = "sub " + this.getOp1() + ", " + this.getOp2();
			break;
		}
		case InstructionType.ADD:{
			retorno = "add " + this.getOp1() + ", " + this.getOp2();
			break;
		}
		case InstructionType.MULT:{
			retorno = "imul " + this.getOp1()+ ", " + this.getOp2();
			break;
		}
		case InstructionType.DIV:{
			retorno = "idiv " + this.getOp1()+ ", " + this.getOp2();
			break;
		}
		case InstructionType.ADD_FLOAT:{
			retorno =  "faddp " + this.getOp1();
			break;
		}
		case InstructionType.SUB_FLOAT:{
			retorno =  "fsubp " + this.getOp1();
			break;
		}
		case InstructionType.MULT_FLOAT:{
			retorno =  "fimul " + this.getOp1();
			break;
		}
		case InstructionType.DIV_FLOAT:{
			retorno =  "fidiv " + this.getOp1();
			break;
		}
		case InstructionType.CMP:{
			retorno = "cmp " + this.getOp1() + ", " + this.getOp2();
			break;
		}
		case InstructionType.POP:{
			retorno = this.criarInstrucaoPOP();
			break;
		}
		case InstructionType.CONSTANTE_DOUBLE:{
			retorno = this.getOp1() + ": dq " + this.getOp2()  ;
			break;
		}
		case InstructionType.POP_FLOAT:{
			retorno = this.criarInstrucaoPopFloat();
			break;
				//"fstp " + this.getOp1() + " [" + this.getOp2() + "]";
		}
		case InstructionType.JNE:{
			retorno = "jne " + this.getOp1();
			break;
		}
		case InstructionType.JE:{
			retorno = "je " + this.getOp1();
			break;
		}
		case InstructionType.JGE:{
			retorno = "jge " + this.getOp1();
			break;
		}
		case InstructionType.JLE:{
			retorno = "jle " + this.getOp1();
			break;
		}
		case InstructionType.JG:{
			retorno = "jg " + this.getOp1();
			break;
		}
		case InstructionType.JL:{
			retorno = "jl " + this.getOp1();
			break;
		}
		case InstructionType.LABEL:{
//			retorno = "\t";
			retorno = this.getOp1() + ":";
		
			break;
		}
		case InstructionType.JUMP:{
			retorno = "jmp " + this.getOp1();
			break;
		}
		case InstructionType.RET:{
			retorno = "ret";
			break;
		}
		case InstructionType.CALL:{
			retorno = "call " + this.getOp1();
			break;
		}
		
	
	



		default:
			/*
				System.out.println("------------------------------------------------------------");
				System.out.println("Erro ao criar a string da instrução: TIPO PASSADO INCORRETO");
				System.out.println("Olhar as constantes em InstructionType");
				System.out.println("tipo:"+this.getTipo()+" , op1:"+this.getOp1()+" , op2:"+this.getOp2()+" , op3:"+this.getOp3());
				System.out.println("------------------------------------------------------------");
				break;
			 */
		}
		return retorno;
	}

	
	private String criarInstrucaoPopFloat() {
		String retorno = null;
		
		if(this.getOp2() == null && this.getOp3() == null){
			//pop ebp ;
			retorno = "fstp " + this.getOp1() ;
			
		}else if (this.getOp2() != null && this.getOp3() == null){
		    //pop dword [a] ;				
			retorno = "fstp " + this.getOp1() + " [" + this.getOp2() + "]";
			
		}
		else{
			//pop dword [ebp+8] ;				
			retorno = "fstp " + this.getOp1() + " [" + this.getOp2() + this.getSinal() + this.getOp3() + "]";
		}
		return retorno;

	}

	private String criarInstrucaoPOP() {
		String retorno = null;
		
		if(this.getOp2() == null && this.getOp3() == null){
			//pop ebp ;
			retorno = "pop " + this.getOp1() ;
			
		}else if (this.getOp2() != null && this.getOp3() == null){
		    //pop dword [a] ;				
			retorno = "pop " + this.getOp1() + " [" + this.getOp2() + "]";
			
		}
		else{
			//pop dword [ebp+8] ;				
			retorno = "pop " + this.getOp1() + " [" + this.getOp2() + this.getSinal() + this.getOp3() + "]";
		}
		return retorno;

	}

	private String criarInstrucaoPushFloat() {
		String retorno = null;
		
		if(this.getOp2() == null && this.getOp3() == null){
			//pop ebp ;
			retorno = "fld " + this.getOp1() ;
			
		}else if (this.getOp2() != null && this.getOp3() == null){
		    //pop dword [a] ;				
			retorno = "fld " + this.getOp1() + " [" + this.getOp2() + "]";
			
		}
		else{
			//pop dword [ebp+8] ;				
			retorno = "fld " + this.getOp1() + " [" + this.getOp2() + this.getSinal() + this.getOp3() + "]";
		}
		return retorno;

	}

	private String criarInstrucaoPush() {
		String retorno = null;
		
		if(this.getOp2() == null && this.getOp3() == null){
			//push ebp ;
			retorno = "push " + this.getOp1() ;
			
		}else if (this.getOp2() != null && this.getOp3() == null){
		    //push dword 1 ;				
			retorno = "push " + this.getOp1() + " " + this.getOp2();
			
		}else{
			//push dword [ebp+8] ;				
			retorno = "push " + this.getOp1() + "[" + this.getOp2() + this.getSinal() + this.getOp3() + "]";
		}
		return retorno;
	}

	private String criarInstrucaoFuncao() {
		this.retorno.append("\t");
		this.retorno.append(this.getOp1() + ":");
		return this.retorno.toString();
	
	}

	private String criarInstrucaoVariavelGlobal() {
		//		  a: dd 0 ; Declara variável global int a com 32 bits e inicializa com 0
		//		  b: dq 0.0 ; Declara variável global double b com 64 bits e inicializa com 0.0
		//		  igual: dd 0 ; Declara variável global boolean c com 32 bits (0 - false, 1 - true)
		this.retorno.append("\t");
		this.retorno.append(this.getOp1() + ":");
		this.retorno.append(" ");
		String tipo = InstructionType.TIPO_INT;
		String valor = "0";
		if (this.getOp2().equals("double")){
			tipo = InstructionType.TIPO_DOUBLE;
			valor = "0.0";
		}
		this.retorno.append(tipo);
		this.retorno.append(" ");
		this.retorno.append(valor);
		return retorno.toString();
	}

	private String criarInstrucaoSectionData() {
		this.retorno.append("\n");
		this.retorno.append("\t");
		this.retorno.append(InstructionType.INT_FORMAT + ": db \"%d\", 10, 0");
		this.retorno.append("\n");
		this.retorno.append("\t");
		this.retorno.append(InstructionType.DOUBLE_FORMAT + ": db \"%.2f\", 10, 0");
		return this.retorno.toString();
	}

	private String criarInstrucaoSectionText() {
		this.retorno.append("\n");
		this.retorno.append("\t");
		this.retorno.append(InstructionType.GLOBAL + " " + InstructionType.WINMAIN);
		return this.retorno.toString();
	}
	private String criarInstrucaoExtern() {
		this.retorno.append(InstructionType.NAME_EXTERN);
		this.retorno.append(" " + getOp1()) ;
		return this.retorno.toString();
	}

	@Override
	public String toString() {
		return this.criarInstrucao();
	}

	public String getSinal() {
		return sinal;
	}

	public void setSinal(String sinal) {
		this.sinal = sinal;
	}
}
