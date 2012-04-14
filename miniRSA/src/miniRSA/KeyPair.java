package miniRSA;

public class KeyPair {
	public PubKey pub;
	public PriKey pri;
	
	public KeyPair(PubKey pub, PriKey pri) {
		this.pub = pub;
		this.pri = pri;
	}
}
