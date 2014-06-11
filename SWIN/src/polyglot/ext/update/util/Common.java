/*************************************************************************
	> File Name: Common.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Sat 07 Jun 2014 01:06:36 AM PDT
 ************************************************************************/

package polyglot.ext.update.util;

import java.util.ArrayList;

public class Common {

	// Split String with possible "<" ">".
	public static ArrayList<String> splitByComma(String str) {	
		ArrayList<String> result = new ArrayList<String>();
		int enclosed = 0;
		int last = 0;
		for (int i = 0; i < str.length(); i ++) {
			if (str.charAt(i) == '<') {
				enclosed ++;
			} else if (str.charAt(i) == '>') {
				enclosed --;
			} else if (str.charAt(i) == ',') {
				if (enclosed > 0) 
					continue;
				String tempStr = removeHeadTailBlank(str.substring(last, i));
				result.add(tempStr);
				last = i + 1;
			}
		}
		if (str.length() != last) {
			String tempStr = removeHeadTailBlank(str.substring(last, str.length()));
			result.add(tempStr);
		}

		return result;
	}	
	
	public static ArrayList<String> splitByCommaB(String str) {	
		ArrayList<String> result = new ArrayList<String>();
		int enclosed = 0;
		int last = 0;
		for (int i = 0; i < str.length(); i ++) {
			if (str.charAt(i) == '(') {
				enclosed ++;
			} else if (str.charAt(i) == ')') {
				enclosed --;
			} else if (str.charAt(i) == ',') {
				if (enclosed > 0) 
					continue;
				String tempStr = removeHeadTailBlank(str.substring(last, i));
				result.add(tempStr);
				last = i + 1;
			}
		}
		if (str.length() != last) {
			String tempStr = removeHeadTailBlank(str.substring(last, str.length()));
			result.add(tempStr);
		}

		return result;
	}	


	// Remove blanks
	public static String removeHeadTailBlank(String input) {
		int first = 0;
		int last = input.length()-1;

		if (input.equals(""))
			return "";

		while (first <= last) {
			if (input.charAt(first) == ' ' || input.charAt(first) == '\t')
				first ++;
			else break;
		}
		while (last >= first) {
			if (input.charAt(last) == ' ' || input.charAt(last) == '\t') {
				last --;
			} else {
				break;
			}
		}

		return input.substring(first,last + 1);
	}

	public static void printList(ArrayList list) {
		System.out.println("Start Print List:");
		for (Object i : list) {
			System.out.println("	" + i);
		}
		System.out.println("End Print List");
	}


	public static String removeBracket(String input) {
		while (findMatchedBracket(input)) {
			input = input.substring(1, input.length()-1);
		}
		return input;
	}

	private static boolean findMatchedBracket(String input) {
		
		if (input.charAt(0) != '(')
			return false;

		int level = 0;
		for (int i = 0; i < input.length() ; i ++) {
			if (input.charAt(i) == '(') 
				level ++;
			else if (input.charAt(i) == ')') {
				level --;
				if (level == 0) {
					if (i == input.length() - 1)
						return true;
					else return false;
				}
			}
		}
		return false;
	}

}
