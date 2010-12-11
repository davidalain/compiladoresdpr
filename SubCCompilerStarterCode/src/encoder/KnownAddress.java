package encoder;

public class KnownAddress extends RuntimeEntity{
	public KnownAddress(int size, int endereco) {
		super(size);
		this.endereco = endereco;
	}

	private int endereco;

	public int getEndereco() {
		return endereco;
	}

	public void setEndereco(int endereco) {
		this.endereco = endereco;
	}
	
}
