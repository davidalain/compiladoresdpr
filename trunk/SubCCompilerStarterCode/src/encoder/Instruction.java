package encoder;


public class Instruction {
	
	private int tipo; // op-code (0 .. 15)
	private String op1; // register field (0 .. 15)
	private String op2; // length field (0 .. 255)
	private String tamanho; // operand field (-32767 .. +32767)
	private StringBuffer instr;
	

	
	
	public Instruction(int tipo, String op1, String op2, String tamanho) {
		this.tipo = tipo;
		this.op1 = op1;
		this.op2 = op2;
		this.tamanho = tamanho;
		criarInstrucao();
		
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

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}
	
	private void inserirArquivo(){
		//TODO criar classe para inserir as instrucões no arquivo
	}
	public void criarInstrucao(){
		if (getTipo() == InstructionType.EXTERN){
			criarInstrucaoExtern();
		}
		if (getTipo() == InstructionType.SECTION){
			this.instr.append("Section " + getOp1());
			this.instr.append("\n");
			if (getOp1().equals(".text")){
				criarInstrucaoSectionText();
			}
			else if (getOp1().equals(".data")){
				criarInstrucaoSectionData();
			}
		}
	
	
	}
	private void criarInstrucaoSectionData() {
		if (!getOp2().equals("")){
			this.instr.append("\t" + "const1: dq 5.3 ; Declara constante double const1 com valor 5.3");
			this.instr.append("\n");
			this.instr.append("\t" + "intFormat: db \"%d\", 10, 0");
			this.instr.append("\n");
			this.instr.append("\t" + "doubleFormat: db \"%.2f\", 10, 0");
			this.instr.append("\n");
		}
		if (getTamanho().equals("int") || getTamanho().equals("boolean")){
			this.instr.append(getOp2()+ ":" + " dd 0");
		}
		if (getTamanho().equals("double")){
			this.instr.append(getOp2() + ":" + "dq 0.0");
		}
	}
	
	private void criarInstrucaoSectionText() {
		this.instr.append("\t" + "global _WinMain@16");
	}
	private void criarInstrucaoExtern() {
		this.instr.append("extern " + getOp1());
	}
	



	
	
	
}
