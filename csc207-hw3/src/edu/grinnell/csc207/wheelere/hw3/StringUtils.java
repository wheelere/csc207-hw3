package edu.grinnell.csc207.wheelere.hw3;

/**
 * 
 * @author Earnest Wheeler
 *
 * CSC 207 (Rebelsky) Assignment 3
 *
 * 	Citations:
 * 	Unit Testing Reading and Lab found online at:
 *      http://www.cs.grinnell.edu/~rebelsky/Courses/CSC207/2013F
 *        	/readings/unit-testing.html
 * 	The Java reference library for ArrayLists found online at:
 * 		http://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html
 *  	A stack overflow question on ArrayLists found online at:
 *  		http://stackoverflow.com/questions/1921181
 *  			/java-arraylist-of-string-arrays
 *  	I was pointed to the charArray functionality by Tiffany Nguyen.
 *  	I discussed the nameGame and how to solve it with Daniel Goldstein
 */

import java.util.ArrayList;

public class StringUtils {

       /**
	 *  splitAt takes a string and a character and splits the string into
         *   an array of strings, excluding all instances of the character c,
 	 *   instead separating the original string at those locations.
 	 */
	public static String[] splitAt(String str, char c) {		
		char[] charArray = str.toCharArray();
		String curr = "";
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i < str.length(); i++) {
			if(charArray[i] == c) {
				list.add(curr);
				curr = "";
			} else {
				curr = curr + charArray[i];
			}
		}
		list.add(curr); //adds whatever is remaining
		String[] strArray = (String[]) list.toArray(new String[list.size()]);
		
		return strArray;
	}
	
	/**
	 *  splitCSV takes a string and separates it at commas, unless the comma is 
	 *   contained within quotes. Two quotation marks in a row is also read as 
	 *   just a quotation mark, and invokes no special conditions.
	 *  splitCSV returns an array of the resulting strings separated this way.
	 */
	public static String[] splitCSV(String str) {
		char[] charArray = str.toCharArray();
		String curr = "";
		ArrayList<String> list = new ArrayList<String>();
		
		for(int i = 0; i < str.length(); i++) {
			// When we reach a quotation mark:
			// treat 2 quotation marks as one
			// or add everything in quotes to the procedure
			if(charArray[i] == '"') {
				int num = 1;
				i++;
				//check if the next character is " too
				if(charArray[i] == '"') {
					curr = curr + charArray[i];
					num = 0;
				}
				while(num == 1 && i < str.length()) {
					//check if the quotes end
					if(charArray[i] == '"') {
						i++;
						if(charArray[i] == '"') {
							curr = curr + charArray[i];
							i++;
						} else {
							num = 0;
							
						}
					} else {
						curr = curr + charArray[i];
						i++;
					}
				}
			}
			if(charArray[i] == ',') {
				list.add(curr);
				curr = "";
			} else {
				curr = curr + charArray[i];
			}
		}
		list.add(curr); //adds whatever is remaining
		String[] strArray = (String[]) list.toArray(new String[list.size()]);

		return strArray;
		
	}
	
	/**
	 * deLeet takes a string and translates any instances of "leetspeak" within it
	 *  to the english equivalent, returning the translation. All instances of "leet"
	 *  character equivalents of o, t, b, n, l, e, and a are modified.
	 */
	public static String deLeet(String str) {
		char[] charArray = str.toCharArray();
		String curr = "";
		int i = 0;
		while(i < str.length()) {
			if(charArray[i] == '0') {
				curr = curr + "o";
				i++;
			} else if(charArray[i] == '+') {
				curr = curr + "t";
				i++;
			} else if(charArray[i] == '1') {
				curr = curr + "l";
				i++;
			} else if(charArray[i] == '3') {
				curr = curr + "e";
				i++;
			} else if(charArray[i] == '@') {
				curr = curr + "a";
				i++;
			} else if(charArray[i] == '|') {
				if(charArray[i + 1] == '3') {
					curr = curr + "b";
					i+=2;
				} else if(charArray[i + 1] == '\\' && charArray[i + 2] == '|') {
					curr = curr + "n";
					i+=3;
				} else {
					curr = curr + "|";
					i++;
				}
			} else {
				curr = curr + charArray[i];
				i++;
			}
		}

		return curr;
	}
	
	/**
	 * nameGame takes a name in the form of a string and returns string that is 
	 *  a verse in the style of Shirley Ellis' "The Name Game." 
	 */
	public static String nameGame(String name) {
		String nameLower = name.toLowerCase();
		char[] nameArray = nameLower.toCharArray();
		int count = 0;
		char check;
		boolean consonant = true;
		while(consonant) {
			check = nameArray[count];
			if(check == 'a' || check == 'e' || check == 'i' || check == 'o' ||
					check == 'u' || check == 'y') {
				consonant = false;
			} else {
				count++;
			}
		}
		String base = nameLower.substring(count);
		String verse = name + "!\n" + name + ", " + name + " bo B" + base +
				" Bonana fanna fo F" + base + "\nFee fy mo M" + base + ", " +
				name + "!";
		
		return verse;
	}
	//The following are test cases for the procedure nameGame, which demonstrate its correctness
	public static void main(String[] args) {
		java.io.PrintWriter pen;
		pen = new java.io.PrintWriter(System.out, true);
		String verse1 = nameGame("Shirley");
		pen.println(verse1);
		String verse2 = nameGame("Otto");
		pen.println(verse2);
		String verse3 = nameGame("Charlie");
		pen.println(verse3);
		String verse4 = nameGame("Tyler");
		pen.println(verse4);
		pen.close();

	}
}
