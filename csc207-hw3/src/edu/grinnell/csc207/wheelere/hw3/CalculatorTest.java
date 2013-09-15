package edu.grinnell.csc207.wheelere.hw3;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;
import java.math.BigInteger;

public class CalculatorTest {

	@Test
	public void test() {
		assertEquals(BigInteger.valueOf(0), Calculator.eval0("0"));
		assertEquals(BigInteger.valueOf(2), Calculator.eval0("1 + 1"));
		assertEquals(BigInteger.valueOf(4), Calculator.eval0("1 + 2 + 1"));
		assertEquals(BigInteger.valueOf(9), Calculator.eval0("1 + 2 * 3"));
		assertEquals(BigInteger.valueOf(36), Calculator.eval0("30 * 2 - 54 ^ 2"));
		assertEquals(BigInteger.valueOf(0), Calculator.eval0("300 - 299 - 1"));
		assertEquals(BigInteger.valueOf(6), Calculator.eval0("5 - -1"));
		assertEquals(BigInteger.valueOf(-1), Calculator.eval0("5 - (7 - 1)"));
		assertEquals(BigInteger.valueOf(115), Calculator.eval0("5 * (18 + 5)"));
	}

	@Test
	public void testChange() {
		assertArrayEquals("dumb test", new int[] {1, 0, 0, 0},
				Calculator.fewestCoins(new int[] {2, 7, 11, 54}, 2));
		assertArrayEquals("real test", new int[] {0, 4, 0, 0},
				Calculator.fewestCoins(new int[] {2, 7, 11, 54}, 28));
		assertArrayEquals("big num test", new int[] {0, 0, 1, 1},
				Calculator.fewestCoins(new int[] {2, 7, 11, 54}, 65));
		assertArrayEquals("general test", new int[] {2, 0, 0, 2},
				Calculator.fewestCoins(new int[] {3, 5, 11, 13}, 32));
	}
}
