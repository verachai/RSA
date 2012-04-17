package miniRSA;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

public class RSAtest {
	
	public BigInteger B(long i) {
		return BigInteger.valueOf(i);
	}
	
	@Test
	public void testGCD() {
		assertEquals(RSA.GCD(B(1), B(-11)).compareTo(B(1)), 0);
        assertEquals(RSA.GCD(B(100), B(10)).compareTo(B(10)), 0);
        assertEquals(RSA.GCD(B(10), B(100)).compareTo(B(10)), 0);
        assertEquals(RSA.GCD(B(100), B(13)).compareTo(B(1)), 0);
        assertEquals(RSA.GCD(B(100), B(100)).compareTo(B(100)), 0);
        assertEquals(RSA.GCD(B(13), B(39)).compareTo(B(13)), 0);
        assertEquals(RSA.GCD(B(120), B(35)).compareTo(B(5)), 0);
        assertEquals(RSA.GCD(B(1001), B(77)).compareTo(B(77)), 0);
        assertEquals(RSA.GCD(B(1001), B(33)).compareTo(B(11)), 0);
	}
	
	@Test
	public void testComplicatedGCD() {
        assertTrue(arrayEquals(RSA.complicatedGCD(B(77), B(33)), new long[]{11,1,-2}));
        assertTrue(arrayEquals(RSA.complicatedGCD(B(33), B(77)), new long[]{11,-2,1}));
        assertTrue(arrayEquals(RSA.complicatedGCD(B(13), B(37)), new long[]{1,-17,6}));
        assertTrue(arrayEquals(RSA.complicatedGCD(B(142312), B(2385)), new long[]{1,-227,13545}));
        assertTrue(arrayEquals(RSA.complicatedGCD(B(442), B(2278)), new long[]{34,31,-6}));
	}
	
	@Test
	public void testMod_inverse() {
        assertEquals(RSA.mod_inverse(B(3), B(17)).compareTo(B(6)), 0);
        assertEquals(RSA.mod_inverse(B(55), B(123)).compareTo(B(85)), 0);
        assertEquals(RSA.mod_inverse(B(45), B(223)).compareTo(B(114)), 0);
        assertEquals(RSA.mod_inverse(B(2342), B(92830457)).compareTo(B(75588250)), 0);
        assertEquals(RSA.mod_inverse(B(3), B(30)).compareTo(B(0)), 0);
	}
	
	@Test
	public void testModulo() {
        assertEquals(RSA.modulo(B(2), B(0), B(55)).compareTo(B(1)), 0);
        assertEquals(RSA.modulo(B(2), B(6), B(5)).compareTo(B(4)), 0);
        assertEquals(RSA.modulo(B(78), B(45), B(37)).compareTo(B(36)), 0);
        assertEquals(RSA.modulo(B(89), B(1232), B(4623)).compareTo(B(1)), 0);
        assertEquals(RSA.modulo(B(254), B(234), B(123)).compareTo(B(4)), 0);
        assertEquals(RSA.modulo(B(2349723), B(423424), B(12345)).compareTo(B(696)), 0);
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
	
	@Test 
	public void testEndecryption() {
		BigInteger msg = B(873123422);
		BigInteger a = new BigInteger("64245974067698974727308576291067189905635129662015537917175582385219809100094736540969262037560193877488366836626125921856719528308413429711"); 
		BigInteger b = new BigInteger("5051274906171681915609732690658365816679515685799846683178436173836692151960702168161668828019540237543601958873479825452017776485422833955447384989249284184721");
		BigInteger c = a.multiply(b);
		BigInteger m = (a.add(BigInteger.ONE.negate())).multiply(b.add(BigInteger.ONE.negate()));
		BigInteger e = RSA.coprime(m);
		BigInteger d = RSA.mod_inverse(e, m);
//		System.out.println("c = " + c + " m = " + m + "\ne = " + e + " d = " + d);
//		System.out.println(RSA.endecrypt(RSA.endecrypt(msg, e, c), d, c));
		assertEquals(RSA.endecrypt(RSA.endecrypt(msg, e, c), d, c).compareTo(msg), 0);
	}
	
	private boolean arrayEquals(BigInteger[] x, long[] y) {
		if (x.length != y.length) return false;
		for (int i = 0; i < x.length; i++) 
			if (x[i].longValue() != y[i]) return false;
		return true;
	}
}
