package miniRSA;

public class PubKey {
	public long c;
	public long e;
	
	public PubKey(long e, long c) {
		this.c = c;
		this.e = e;
	}
}
