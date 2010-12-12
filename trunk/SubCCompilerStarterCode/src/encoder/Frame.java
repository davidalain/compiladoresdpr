package encoder;

public class Frame {

	private int level;
	private int size;
	
	public Frame(int level, int size) {
		super();
		this.level = level;
		this.size = size;
	}
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
}
