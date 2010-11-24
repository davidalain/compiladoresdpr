package encoder;

public class Instruction {
	
	public static final byte LOADop 	= 0;
	public static final byte LOADAop 	= 1;
	public static final byte LOADIop 	= 2;
	public static final byte LOADLop 	= 3;
	public static final byte STOREop 	= 4;
	public static final byte STOREIop 	= 5;
	public static final byte CALLop 	= 6;
	public static final byte CALLIop 	= 7;
	public static final byte RETURNop 	= 8;
	public static final byte PUSHop 	= 10;
	public static final byte POPop 		= 11;
	public static final byte JUMPop 	= 12;
	public static final byte JUMPIop 	= 13;
	public static final byte JUMPIFop 	= 14;
	public static final byte HALTop 	= 15;
	
	
	public static final byte CBr = 0;
	public static final byte CTr = 1;
	public static final byte PBr = 2;
	public static final byte PTr = 3;
	public static final byte SBr = 4;
	public static final byte STr = 5;
	public static final byte HBr = 6;
	public static final byte HTr = 7;
	public static final byte LBr = 8;
	public static final byte L1r = 9;
	public static final byte L2r = 10;
	public static final byte L3r = 11;
	public static final byte L4r = 12;
	public static final byte L5r = 13;
	public static final byte L6r = 14;
	public static final byte CPr = 15;
	
	
	public byte op; // op-code (0 .. 15)
	public byte r; // register field (0 .. 15)
	public byte n; // length field (0 .. 255)
	public short d; // operand field (-32767 .. +32767)
	
	public Instruction(byte op , byte r , byte n , short d){
		this.op = op;
		this.r = r;
		this.n = n;
		this.d = d;
	}
	
}
