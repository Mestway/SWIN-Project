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
		
		String regex = "\\([^\\(\\)]*\\)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(javaBody);
		
		String argString = null;
		String lastBody = new String();
		int lastEnd = 0;
		// Match the arguments e.g.(a,b,c)
		while (matcher.find()) {
			argString = matcher.group();
			lastBody += javaBody.substring(lastEnd, matcher.start());
			lastEnd = matcher.end();

			String[] argsParts = argString.substring(1, argString.length()-1).split(",");
			
			ArrayList<String> ag = new ArrayList<String>();
			for (String i : argsParts) {
				if (!i.equals("")) {
					int first = 0;
					int last = i.length() - 1;
					while (first <= last) {
						if (i.charAt(first )== ' ')
							first ++;
						else break;
					}

					while (last >= first) {
						if (i.charAt(last) == ' ') {
							last --;
						} else {
							break;
						}
					}

					ag.add(i.substring(first, last + 1));
				}
			}
			args.add(ag);
		}

		String[] nameParts = lastBody.split(" ");
		if (nameParts.length > 1 && nameParts[0].equals("new")) {
			isInvoke = false;
			target = nameParts[1];
			methodName.add(target);
		} else {
			String[] invokeSeq = nameParts[0].split("\\.");
			target = invokeSeq[0];
			System.out.println("tg -- " + target);
			for (int i = 1; i <= invokeSeq.length - 1; i ++) {
				methodName.add(invokeSeq[i]);
			}
		}
	}

	public String allString() {
		return allString;
	}

}
