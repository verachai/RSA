package miniRSA;

import static org.junit.Assert.*;

import org.junit.Test;

public class RSAtest {
	
	@Test
	public void testGCD() {
		assertEquals(RSA.GCD(1, -11), 1);
        assertEquals(RSA.GCD(100,10),10);
        assertEquals(RSA.GCD(10,100),10);
        assertEquals(RSA.GCD(100,13),1);
        assertEquals(RSA.GCD(100,100),100);
        assertEquals(RSA.GCD(13,39),13);
        assertEquals(RSA.GCD(120,35),5);
        assertEquals(RSA.GCD(1001,77),77);
        assertEquals(RSA.GCD(1001,33),11);
	}
	
	@Test
	public void testComplicatedGCD() {
        assertTrue(arrayEquals(RSA.complicatedGCD(77,33), new long[]{11,1,-2}));
        assertTrue(arrayEquals(RSA.complicatedGCD(33,77), new long[]{11,-2,1}));
        assertTrue(arrayEquals(RSA.complicatedGCD(13,37), new long[]{1,-17,6}));
        assertTrue(arrayEquals(RSA.complicatedGCD(142312,2385), new long[]{1,-227,13545}));
        assertTrue(arrayEquals(RSA.complicatedGCD(442,2278), new long[]{34,31,-6}));
	}
	
	@Test
	public void testMod_inverse() {
        assertEquals(RSA.mod_inverse(3,17),6);
        assertEquals(RSA.mod_inverse(55,123),85);
        assertEquals(RSA.mod_inverse(45,223),114);
        assertEquals(RSA.mod_inverse(2342,92830457),75588250);
        assertEquals(RSA.mod_inverse(3,30),0);
	}
	
	@Test
	public void testModulo() {
        assertEquals(RSA.modulo(2,0,55),1);
        assertEquals(RSA.modulo(2,4,1000),16);
        assertEquals(RSA.modulo(2,6,5),4);
        assertEquals(RSA.modulo(78,45,37),36);
        assertEquals(RSA.modulo(89,1232,4623),1);
        assertEquals(RSA.modulo(254,234,123),4);
        assertEquals(RSA.modulo(2349723,423424,12345),696);
//        long n = 313135419829591;
//        long d = 53390193787969;
//        long e = 19913737;
//        long s = 237692323384979;
//        assertEquals(RSA.modulo(104,e,n),s);
//        assertEquals(RSA.modulo(s,d,n),104);
	}
	
	@Test
	public void testTotient() {
		assertEquals(RSA.totient(1), 0);
		assertEquals(RSA.totient(3), 2);
		assertEquals(RSA.totient(4), 2);
		assertEquals(RSA.totient(9), 6);
		assertEquals(RSA.totient(115), 88);
		assertEquals(RSA.totient(10000), 4000);
		assertEquals(RSA.totient(99999), 64800);
		assertEquals(RSA.totient(81931731), 46818120);
		assertEquals(RSA.totient(218127142), 109063570);
	}
	
	private boolean arrayEquals(long[] x, long[] y) {
		if (x.length != y.length) return false;
		for (int i = 0; i < x.length; i++) 
			if (x[i] != y[i]) return false;
		return true;
	}
}
