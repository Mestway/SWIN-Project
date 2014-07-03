/*************************************************************************
	> File Name: CallArgs.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Tue 10 Jun 2014 07:17:05 PM PDT
 ************************************************************************/

package polyglot.ext.update.util;

import java.util.ArrayList;
import java.util.List;
import polyglot.ext.update.match.Matching;

public class CallArgs {

	// A new X() or x.m()
	Object caller;
	String methodName;
	ArrayList<Object> args;

	private CallArgs(Object caller, String methodName, ArrayList<Object> args) {
		this.caller = caller;
		this.methodName = methodName;
		this.args = args;


		//System.out.println("[Caller]" + caller);
		//System.out.println("[mName]" + methodName);
		//System.out.println("[args]" + args.get(0));
		
	}

	public static Object getCallArgs(String input) {
		
		String callerString = new String();
		String bodyString = new String();
	
		boolean findCall = false;
		int layer = 0;
		for (int i = input.length() - 1; i >= 0 ; i --) {
			if (input.charAt(i) == ')')
				layer ++;
			else if (input.charAt(i) == '(')
				layer --;
			else if (input.charAt(i) == '.' && layer == 0) {
				callerString = input.substring(0, i);
				bodyString = input.substring(i + 1, input.length());
				findCall = true;
				break;
			}
		}

		int l = 0, r = 0;
		if (findCall == false) {
			return new String(input);
		} else {
			int ly = 0;
			for (int i = 0; i < bodyString.length(); i ++) {
				if (bodyString.charAt(i) == '(') {
					if (ly == 0)
						l = i;
					ly ++;
				} else if (bodyString.charAt(i) == ')') {
					ly --;
					if (ly == 0) r = i;
				}
			}
		}

		ArrayList<String> ar = Common.splitByCommaB(bodyString.substring(l + 1, r));
		ArrayList<Object> ag = new ArrayList<Object>();
		for (String s : ar) {
			ag.add(getCallArgs(s));
		}


		String mName = bodyString.substring(0, l).trim();

		Object clr = getCallArgs(callerString);

		return new CallArgs(clr, mName, ag);
	}

	@Override
	public String toString() {
		String str = "";
		

		str += "" + caller.toString() + "";

		str += "." + methodName;

		str += "(";
		boolean first = true;
		for (Object i : args) {
			if (first) {
				first = false;
			} else {
				str += ',';
			}
			str += i.toString();
		}
		str += ")";
	
		return str;	
	}

	public void substitution(Matching match, List arrayL) {
		if (caller instanceof String) {
			int n = match.lookUpDstVirtualNo(caller.toString());
			if (n != -1) {
				caller = arrayL.get(n);
			}
		} else if (caller instanceof CallArgs) {
			((CallArgs)caller).substitution(match, arrayL);
		}

		for (int i = 0; i < args.size(); i ++) {
			Object o = args.get(i);
			if (o instanceof String) {
				int n = match.lookUpDstVirtualNo(o.toString());
				System.out.println("[???]" + n + " : " + o.toString() + " :: " + arrayL.size());
				if (n != -1 && arrayL.size() > n) {
					args.set(i, arrayL.get(n));
				}
			} else if (o instanceof CallArgs) {
				((CallArgs)o).substitution(match, arrayL);
			}
		}
	}

}
