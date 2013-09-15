package edu.grinnell.csc207.wheelere.hw3;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void test() {
		assertArrayEquals("colon test", new String[] { "a", "b", "c" },
				StringUtils.splitAt("a:b:c", ':'));
		assertArrayEquals("space test", new String[] { "a", "b", "c" },
				StringUtils.splitAt("a b c", ' '));
		assertArrayEquals("dud test", new String[] { "a:b:c" },
				StringUtils.splitAt("a:b:c", ' '));
		assertArrayEquals("one field", new String[] { "a" },
				StringUtils.splitAt("a", ':'));
		assertArrayEquals("empty inner field", new String[] { "a", "", "c" },
				StringUtils.splitAt("a::c", ':'));
		assertArrayEquals("leading empty field", new String[] { "", "a" },
				StringUtils.splitAt(":a", ':'));
		assertArrayEquals("trailing empty field", new String[] { "a", "" },
				StringUtils.splitAt("a:", ':'));
		assertArrayEquals("many splits-11",
				new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"},
				StringUtils.splitAt("a b c d e f g h i j k", ' '));
	}
	@Test
	public void test2() {
		assertArrayEquals("basic", new String[] { "a", "b", "c" },
				StringUtils.splitCSV("a,b,c"));
		assertArrayEquals("include comma", new String[] { "a,b", "c" },
				StringUtils.splitCSV("\"a,b\",c"));
		assertArrayEquals("include quote", new String[] { "a", "b,b\"", "c" },
				StringUtils.splitCSV("a,\"b,b\"\"\",c"));
		
	}
	
	@Test
	public void test3() {
		assertEquals("e", StringUtils.deLeet("3"));
		assertEquals("leet", StringUtils.deLeet("133+"));
		assertEquals("b", StringUtils.deLeet("|3"));
		assertEquals("eat banana", StringUtils.deLeet("3@+ |3@|\\|@|\\|@"));
		assertEquals("Java is frustrating",
				StringUtils.deLeet("J@v@ is frus+r@+i|\\|g"));
	}
	
}

