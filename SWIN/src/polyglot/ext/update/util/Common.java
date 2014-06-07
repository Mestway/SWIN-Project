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

	// Remove blanks
	public static String removeHeadTailBlank(String input) {
		int first = 0;
		int last = input.length()-1;

		if (input.equals(""))
			return "";

		while (first <= last) {
			if (input.charAt(first) == ' ')
				first ++;
			else break;
		}
		while (last >= first) {
			if (input.charAt(last) == ' ') {
				last --;
			} else {
				break;
			}
		}

		return input.substring(first,last + 1);
	}

}
