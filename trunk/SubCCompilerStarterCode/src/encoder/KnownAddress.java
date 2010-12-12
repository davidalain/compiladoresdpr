package encoder;

public class KnownAddress extends RuntimeEntity{

	public KnownAddress(int size, int endereco) {
		super(size);
		this.endereco = endereco;
		this.eParametro= false;
	}
	public KnownAddress(int size, int endereco, boolean eParametro) {
		super(size);
		this.endereco = endereco;
		this.eParametro= eParametro;
	}
	

	private int endereco;
	private boolean eParametro;
	
	public int getEndereco() {
		return endereco;
	}

	public void setEndereco(int endereco) {
		this.endereco = endereco;
	}

	public boolean isParametro() {
		return eParametro;
	}

	public void seteParametro(boolean eParametro) {
		this.eParametro = eParametro;
	}
	
}
