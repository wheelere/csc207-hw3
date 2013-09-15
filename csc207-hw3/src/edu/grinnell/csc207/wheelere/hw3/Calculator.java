package edu.grinnell.csc207.wheelere.hw3;

/**
 * @author Earnest Wheeler
 *
 * CSC 207 (Rebelsky) Assignment 3
 *
 * 	Citations:
 *  I found how to turn strings into integers at this link:
 * 		http://stackoverflow.com/questions/5585779/how-to-convert-string-
 * 			to-int-in-java
 *  I got a deeper grasp of Part F and possible solutions at:
 *  	http://stackoverflow.com/questions/8031816/dynamic-programming-
 *  		making-change
 */


import java.math.BigInteger;

public class Calculator {

	/**
	 * This procedure takes a string with parens in it,
	 * 	and collapses the equation in the parens to a single
	 * 	integer
	 * It assumes parens are properly placed and paired, and
	 * 	they are not nested.
	 */
	public static String collapseParens(String eqn) {
		char[] charArray = eqn.toCharArray();
		int len = charArray.length;
		BigInteger collapsed;
		String revised = "";
		int u; //tis will be used to index the opening paren
		for(int i = 0; i < len; i++) {
			if(charArray[i] == '(') {
				i++;
				u = i;
				while(charArray[i] != ')') {
					i++;
				} //i should now be the closing paren
				collapsed = eval0(eqn.substring(u, i));
				revised = revised + collapsed.toString();
			} else {
				revised = revised + charArray[i];
			}
		}
		return revised;
	}
	
	/**
	 * The string eqn must be formatted as below,
	 *  and must begin with a number. It returns a calculation
	 *  of all the terms and given operations, from the set 
	 *  {+, -, *, /, ^}.
	 *  Format: [num1] [operation] [num2] etc
	 *  Un-nested Parentheses are supported, but order of
	 *  operations beyond that is not enforced.
	 */
	public static BigInteger eval0(String eqn) {
		eqn = collapseParens(eqn);
		char[] charArray = eqn.toCharArray();
		int len = charArray.length; // we will use this multiple times
		BigInteger result;
		int i = 0;
		int start = i; //the start of each number
		int left; //the left number in the equation
		int right; //the right number in the equation
		int index; //to remember a certain place
		
		
		while(i + 1 < len && charArray[i+1] != ' ') {
			i++;
		} //i is the index of the last element of the number
		left = Integer.parseInt(eqn.substring(0, i + 1));
		i+=2;
		result = BigInteger.valueOf(left);
		while(i < len) {
			if(charArray[i] == ' ') {
				i++;
			}
			index = i; //this is the location of the operation
			i+=2; //set i to be at the beginning of the next number
			start = i;
			while(i + 1 < len && charArray[i+1] != ' ') {
				i++;
			} //i is the index of the last element of the number
			right = Integer.parseInt(eqn.substring(start, i + 1));
			i++;
			switch (charArray[index]) {
			case '+': result = result.add(BigInteger.valueOf(right));
			break;
			case '-': result = result.subtract(BigInteger.valueOf(right));
			break;
			case '*': result = result.multiply(BigInteger.valueOf(right));
			break;
			case '/': result = result.divide(BigInteger.valueOf(right));
			break;
			case '^': result = result.pow(right);
			break;
			default:  System.err.println("The character at " + index +
					" is not a valid operation");
			break;
			}
		}
		return result;
	}
	
	public static int sumChange(int[] coins, int[] nums) {

		int sum = 0;
		;
		for(int i = 0; i < 4; i++) {
			sum = sum + nums[i] * coins[i];
		}
		return sum;
	}


	public static int[] fewestCoins(int[] coins, int change) {


		int n = change + 1; //include zero as a row
		int[][] table = new int[4][n];
		int[] amount = {1, 1, 1, 1}; //Our result
		int[] arrayRef = {0, 0, 0, 0};
		int[] arrayCurr = {0, 0, 0, 0};
		int[] arrayCheck = {0, 0, 0, 0};
		for(int k = 0; k < 4; k++) {
			table[k][0] = 0;
		} // Set the 0 change values as 0
		int jup; //used to fill an array upwards
		int jdown; //used to fill an array downwards
		/**
		 * j is the coin being examined
		 * i is the value being examined
		 */
		for(int i = 1; i < n; i++) {
			for(int j = 0; j < 4 ; j++){
				for(int r = 0; r < 4; r++) {
					arrayCurr[r] = table[r][i];
				} //Build an array for the current row before processing
				if(i - coins[j] >= 0) { //ensure the reference row exists
					for(int l = 0; l < 4; l++) {
						arrayRef[l] = table[l][i - coins[j]];
					} //Build an array for the row coins[j] above
					
					// If 
					if(i - coins[j] == sumChange(coins, arrayRef)) {
						arrayCheck[j] = table[j][i-coins[j]] + 1;
						//Now fill the array for values on either side
						// of j.
						jup = j + 1;
						while(jup < 4) {
							arrayCheck[jup] = table[jup][i-coins[j]];
							jup++;
						}
						jdown = j - 1;
						while(jdown > -1) {
							arrayCheck[jdown] = table[jdown][i-coins[j]];
							jdown--;
						}
						if(sumChange(amount, arrayCurr) == 0) {
							for(int l = 0; l < 4; l++) {
								table[l][i] = arrayCheck[l];
							}
						} else if(sumChange(amount, arrayCurr) >
						sumChange(amount, arrayCheck)) {
							for(int z = 0; z < 4; z++) {
								table[z][i] = arrayCheck[z];
							}
						}
					}
				}

			}
		}
		for(int s = 0; s < 4; s++) {
			amount[s] = table[s][change];
		}
		return amount;
	}
}
