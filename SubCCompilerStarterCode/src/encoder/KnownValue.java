package encoder;

public class KnownValue extends RuntimeEntity {

	private String valor;
	private String label;
	
	public KnownValue(int size,String valor,String label) {
		super(size);
		this.valor = valor;
		this.label = label;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}



	
}
