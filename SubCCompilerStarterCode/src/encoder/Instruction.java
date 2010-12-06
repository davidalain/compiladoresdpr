package encoder;


public class Instruction {

	private int tipo; // op-code (0 .. 15)
	private String op1; // register field (0 .. 15)
	private String op2; // length field (0 .. 255)
	private String op3; // operand field (-32767 .. +32767)
	private StringBuffer retorno;

	public Instruction(int tipo, String op1, String op2, String op3) {
		this.tipo = tipo;
		this.op1 = op1;
		this.op2 = op2;
		this.op3 = op3;
		this.retorno = new StringBuffer();
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
			default:
				System.out.println("------------------------------------------------------------");
				System.out.println("Erro ao criar a string da instru��o: TIPO PASSADO INCORRETO");
				System.out.println("Olhar as constantes em InstructionType");
				System.out.println("tipo:"+this.getTipo()+" , op1:"+this.getOp1()+" , op2:"+this.getOp2()+" , op3:"+this.getOp3());
				System.out.println("------------------------------------------------------------");
				break;
		}
		return retorno;
	}
	
	private String criarInstrucaoVariavelGlobal() {
//		  a: dd 0 ; Declara vari�vel global int a com 32 bits e inicializa com 0
//		  b: dq 0.0 ; Declara vari�vel global double b com 64 bits e inicializa com 0.0
//		  igual: dd 0 ; Declara vari�vel global boolean c com 32 bits (0 - false, 1 - true)
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






}
