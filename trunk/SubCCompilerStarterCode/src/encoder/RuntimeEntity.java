package encoder;

public abstract class RuntimeEntity {

	private int size;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public RuntimeEntity(int size) {
		super();
		this.size = size;
	}

	public RuntimeEntity() {
		super();
	}
	
	

}
