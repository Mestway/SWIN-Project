/*************************************************************************
	> File Name: match/JavaBody.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Tue 06 May 2014 06:48:38 PM PDT
 ************************************************************************/

package polyglot.ext.update.match;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaBody {
	
	// whether this node is a invoke(true) or a new(false) 
	protected boolean isInvoke = true;
	
	// the class symbol --> *NOT THE CLASS TYPE*
	protected String target = null;
	// The method being invoked
	protected ArrayList<String> methodName = new ArrayList<String>();
	protected ArrayList<ArrayList<String>> args = new ArrayList<ArrayList<String>>();

	protected String allString = null;

	public JavaBody(String javaBody) {
		allString = javaBody;
		parseJavaBody(javaBody);		
	}

	public void setNew() { isInvoke = false; }
	public void setInvoke() { isInvoke = true; }
	public boolean isInvoke() { return isInvoke; }
	public boolean isNew() { return !isInvoke; }

	public String getTarget() {
		return target;
	}

	public ArrayList<String> getMethodName() {
		return methodName;
	}

	public ArrayList<ArrayList<String>> getArgs() {
		return args;
	}

	public void parseJavaBody(String javaBody) {

		SplitString splitJavaBody = splitByBracket(javaBody);
		
		String lastBody = splitJavaBody.getRemaining();
		
		for (String argPart : splitJavaBody.getParts()) {
			ArrayList<String> functionArg = new ArrayList<String>();
			
			SplitString eachArg = splitByComma(argPart);	
			for (String oneArg : eachArg.getParts()) {
				if (!oneArg.equals("")) {
					String realArg = removeHeadTailBlank(oneArg);	
					functionArg.add(realArg);
				}	
			}
			args.add(functionArg);
		}
		
		String[] nameParts = lastBody.split(" ");
		
		ArrayList<String> contactStr = new ArrayList<String>();
		for (String ppt : nameParts) {
			if (ppt.equals("") || ppt.equals("\\s*")) 
				continue;
			else 
				contactStr.add(ppt);
		}

		if (nameParts.length > 1 && contactStr.get(0).equals("new")) {
			isInvoke = false;
			target = contactStr.get(1);
			methodName.add(target);
		} else {
			String[] invokeSeq = contactStr.get(0).split("\\.");
			target = invokeSeq[0];
			for (int i = 1; i <= invokeSeq.length - 1; i ++) {
				methodName.add(invokeSeq[i]);
			}
		}
	}

	protected SplitString splitByBracket(String input) {
		SplitString result = new SplitString();
		result.setOriginal(input);
		
		int layers = 0;
		int bracketCount = -1;
		String remaining = new String();

		for (int i = 0; i < input.length(); i ++) {
			if (layers == 0 && input.charAt(i) != '(') {
				remaining += input.charAt(i);
			}
			
			if (input.charAt(i) == '(') {
				layers ++;
				if (layers == 1) {
					result.getBegins().add(i);
					bracketCount ++;
				}
			} else if (input.charAt(i) == ')') {
				layers --;
				if (layers == 0) {
					result.getEnds().add(i);
					result.getParts().add(input.substring(result.getBegins().get(bracketCount) + 1, 
														result.getEnds().get(bracketCount)));
				}
			}
		}
		
		result.setRemaining(remaining);
		return result;
	}

	protected SplitString splitByComma(String input) {
		SplitString result = new SplitString();
		result.setOriginal(input);
		
		int layers = 0;
		int lastIndex = 0;
		int unitCount = 0;

		for (int i = 0; i < input.length(); i ++) {
			if (input.charAt(i) == '(') {
				layers ++;
			} else if (input.charAt(i) == ')') {
				layers --;
			} else if (input.charAt(i) == ',') {
				if (layers == 0){
					result.getBegins().add(lastIndex);
					result.getEnds().add(i-1);
					result.getParts().add(input.substring(lastIndex, i));
					lastIndex = i + 1;
				}
			}
		}

		result.getBegins().add(lastIndex);
		result.getEnds().add(input.length()-1);
		result.getParts().add(input.substring(lastIndex, input.length()));
	
		return result;
	}

	protected String removeHeadTailBlank(String input) {
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

	public String allString() {
		return allString;
	}

	public void print() {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("Is invoke? " + isInvoke());
		System.out.println("Target: " + target);
		for (int i = 0; i < methodName.size(); i ++) {	
			System.out.println("Method:" + methodName.get(i));
			for (String j : args.get(i)) {
				System.out.println("	arg: " + j);
			}
		}
	}
}
