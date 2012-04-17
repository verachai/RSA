package miniRSA;

import java.math.BigInteger;

public class PriKey {
	public BigInteger c;
	public BigInteger d;
	
	public PriKey(BigInteger d, BigInteger c) {
		this.c = c;
		this.d = d;
	}
}
