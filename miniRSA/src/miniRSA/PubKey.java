package miniRSA;

import java.math.BigInteger;

public class PubKey {
	public BigInteger c;
	public BigInteger e;
	
	public PubKey(BigInteger e, BigInteger c) {
		this.c = c;
		this.e = e;
	}
}
