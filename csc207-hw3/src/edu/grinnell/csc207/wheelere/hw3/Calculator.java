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
	 * 	they are not nested, and that the String is formatted
	 * 	as directed in the eval0 procedure.
	 */
	public static String collapseParens(String eqn) {
		char[] charArray = eqn.toCharArray();
		int len = charArray.length;
		BigInteger collapsed; //solve inside parentheses
		String revised = ""; //String to return
		int u; //tis will be used to index the opening paren
		for(int i = 0; i < len; i++) {
			if(charArray[i] == '(') {
				i++;
				u = i;
				while(charArray[i] != ')') {
					i++;
				} //i should now be the closing paren
				//just calculate this simple eqn
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
			//Switch statement for the type of operation we are performing.
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
	
	/**
	 * sumChange is a helper procedure for fewestCoins which takes two 4 element arrays
	 *  of integers and multiplies corresponding elements and adds them to find a total.
	 * Example:
	 *  Given {a,b,c,d} and {u,v,x,y}, sumChange would return a*u + b*v + c*x + d*y.
	 * 
	 */
	public static int sumChange(int[] coins, int[] nums) {

		int sum = 0;
		int len = coins.length;
		;
		for(int i = 0; i < len; i++) {
			sum = sum + nums[i] * coins[i];
		}
		return sum;
	}

	/**
	 * fewestCoins takes an array of ints as values of coins in ascending order
	 *  and an integer value of change that needs to be distributed, and
	 *  returns an array of the number of each coin that is the least
	 *  total coins that need to be used.
	 * If the integer change cannot be made as a sum of the coins given, 
	 *  fewestCoins returns an array of zeroes.
	 */
	public static int[] fewestCoins(int[] coins, int change) {
		int n = change + 1; //total rows of our table, including zero
		int len = coins.length; //number of coins there are
		int[][] table = new int[len][n];
		//table is a table of the best coin use for a given value between 0 and n
		int[] amount = new int[len]; //Our result
		int[] arrayRef = new int[len]; //an array to be used as a reference
		int[] arrayCurr = new int[len]; //an array to equal the current row
		int[] arrayCheck = new int[len]; //an array to posit a new current row
		int[] arrayOnes = new int[len]; //an array for checking coins in a row
		int jup; //used to fill an array upwards
		int jdown; //used to fill an array downwards
		
		for(int o = 0; o < len; o++) {
			arrayOnes[o] = 1;
		} //arrayOnes is now an array of ones.
		for(int k = 0; k < 4; k++) {
			table[k][0] = 0;
		} // Set the 0 change values as 0

		/**
		 * j is the index of the coin being examined
		 * i is the value being examined
		 * we will build the table of the best coin use
		 * from 0 to n
		 */
		for(int i = 1; i < n; i++) {
			for(int j = 0; j < len ; j++){
				for(int r = 0; r < len; r++) {
					arrayCurr[r] = table[r][i];
				} //Build an array for the current row before processing
				if(i - coins[j] >= 0) { //ensure the reference row exists
					for(int m = 0; m < len; m++) {
						arrayRef[m] = table[m][i - coins[j]];
					} //Build an array for the row coins[j] above
					//check if the reference row is proper
					if(i - coins[j] == sumChange(coins, arrayRef)) {
						arrayCheck[j] = table[j][i-coins[j]] + 1;
						//Now fill the array for values on either side of j
						jup = j + 1;
						while(jup < len) {
							arrayCheck[jup] = table[jup][i-coins[j]];
							jup++;
						}
						jdown = j - 1;
						while(jdown > -1) {
							arrayCheck[jdown] = table[jdown][i-coins[j]];
							jdown--;
						}
						//if this row was empty don't worry about efficiency
						if(sumChange(arrayOnes, arrayCurr) == 0) {
							for(int l = 0; l < len; l++) {
								table[l][i] = arrayCheck[l];
							}
						} 
						//check if the potential row is more efficient than
						// the current one
						else if(sumChange(arrayOnes, arrayCurr) >
						sumChange(arrayOnes, arrayCheck)) {
							for(int z = 0; z < len; z++) {
								table[z][i] = arrayCheck[z];
							}
						}
					}
				}

			}
		}
		//copy the row for n into its own array of ints.
		for(int s = 0; s < len; s++) {
			amount[s] = table[s][change];
		}
		return amount;
	}
}
