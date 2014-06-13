package polyglot.ext.update.check;

import polyglot.ext.update.util.Common;
import polyglot.ext.update.util.Pair;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class RuleCheck {
	//for need to collect all the data, these vars should be static
	
	static protected HashMap<String, String> typeMappings 
		= new HashMap<String, String>();
	
	//a method is including three parts: class name, method name, and paras
	static protected HashMap<String, HashMap<String, ArrayList<String>>> 
		oldMethods = new HashMap<String, HashMap<String, ArrayList<String>>>();
	static protected HashMap<String, HashMap<String, ArrayList<String>>> 
		newMethods = new HashMap<String, HashMap<String, ArrayList<String>>>();
	static protected HashMap<String, Pair<String, String>> varTypeChanges
		= new HashMap<String, Pair<String, String>>(); 

	@SuppressWarnings("unused")
	private void print(String name, HashMap<String, HashMap<String, ArrayList<String>>> m) {
		System.out.println("methods are:");
		Iterator<Entry<String, HashMap<String, ArrayList<String>>>> 
			it = m.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, HashMap<String, ArrayList<String>>>
				en = it.next();
			Iterator<Entry<String, ArrayList<String>>> itt 
				= en.getValue().entrySet().iterator();
			while (itt.hasNext()) {
				System.out.print(en.getKey() + " ");
				Entry<String, ArrayList<String>> enn = itt.next();
				System.out.print(enn.getKey()+ " ");
				for (String str : enn.getValue())
					System.out.print(str + " ");
				System.out.println();
			}
		}
	}
	
	public RuleCheck(String rawMatching) {
		parse(rawMatching.substring(1, rawMatching.length()-1));
	}
	
	public HashMap<String, String> getTypeMapings() {
		return typeMappings;
	}

	public HashMap<String, HashMap<String, ArrayList<String>>> getOldMethods() {
		return oldMethods;
	}
	
	public HashMap<String, HashMap<String, ArrayList<String>>> getNewMethods() {
		return newMethods;
	}

	protected void parse(String input) {
		String pattern = "\\([^\\(\\)]*\\)";
		Pattern r = Pattern.compile(pattern);
		Matcher matcher = r.matcher(input);
		if (matcher.find()) {
			String def = matcher.group();
			parseDef(def.substring(1, def.length()-1));
		} else {
			parseError("SWIN def not well defined");
		}	
		
		pattern = "\\[[^\\[\\]]*\\]";
		r = Pattern.compile(pattern);
		matcher = r.matcher(input);
		if (matcher.find()) {
			String body = matcher.group();
			parseBody(body.substring(1, body.length()-1));
			input = input.substring(0, matcher.start());
		} else {
			parseError("SWIN body not well defined");
		}
	}

	protected void parseDef(String input) {
		ArrayList<String> segs = Common.splitByComma(input);
		for (String s : segs) {
			int x = s.indexOf(':');
			int y = s.indexOf('-');
			String varName = s.substring(0, x);
			String typeO = s.substring(x+1, y);
			String typeN = s.substring(y+3, s.length());
			varName = varName.trim();
			typeO = typeO.trim();
			typeN = typeN.trim();
			//type mapping
			typeMappingCheck(typeO, typeN);
			//type changes of variables
			varTypeChanges.put(varName, 
				new Pair<String, String>(typeO, typeN));
		}
	}

	protected void parseBody(String input) {
		String left = 
				input.substring(0, input.indexOf("->")).trim();
		String right = 
			input.substring(
					input.indexOf("->") + 2, input.length()).trim();
		String leftBody = left.substring(0, left.indexOf(":"));
		String leftType = left.substring(left.indexOf(":") + 1, left.length());
		String rightBody = right.substring(0,right.indexOf(":"));
		String rightType = right.substring(right.indexOf(":") + 1, right.length());
		rightBody = Common.removeBracket(rightBody);
		leftBody = Common.removeBracket(leftBody);
		typeMappingCheck(leftType, rightType);
		oldMethodCheck(leftBody);
		newMethodCheck(rightBody);
	}

	private void typeMappingCheck(String typeO, String typeN) {
		if (typeMappings.containsKey(typeO) 
				&& !typeMappings.get(typeO).equals(typeN))
			checkError("typemappings is not a function!");
		else {
			typeMappings.put(typeO, typeN);
		}
	}
	
	private void oldMethodCheck(String leftBody) {
		int x = leftBody.indexOf("new");
		String className = "";
		String methName = "";
		int y = 0, z = 0;
		if (x != -1) {
			y = leftBody.indexOf('(');
			className = leftBody.substring(x+3, y).trim();
			methName = className;
		} else {
			y = leftBody.indexOf('.');
			z = leftBody.indexOf('(');
			className = varTypeChanges.get(leftBody.substring(0, y).trim()).getFirst(); 
			methName = leftBody.substring(y+1, z).trim();
			y = z;
		}
		z = leftBody.lastIndexOf(')');
		String[] paraNames = leftBody.substring(y+1, z).split(","); 
		//todo: show more accurate error messages to users
		if (oldMethods.containsKey(className) &&
				oldMethods.get(className).containsKey(methName)) {
				checkError("A method maps to more than one term or" +
					"there are two same rules in your SWIN program");
		}
		ArrayList<String> paraTypes = new ArrayList<String>();
		for (int i = 0; i < paraNames.length; i++) {
			String tmpStr = paraNames[i].trim();
			if (!tmpStr.equals("") && varTypeChanges.containsKey(tmpStr))
				paraTypes.add(
						varTypeChanges.get(tmpStr).getFirst());
		}
		//tmpMap should only be a local var
		HashMap<String, ArrayList<String>> tmpMap 
			= new HashMap<String, ArrayList<String>>();
		tmpMap.put(methName, paraTypes);
		
		if (!oldMethods.containsKey(className))
			oldMethods.put(className, tmpMap);
		else
			oldMethods.get(className).putAll(tmpMap);
//		print(className, oldMethods);
	}
	
	private void newMethodCheck(String rightBody) {
		int firstLeftBracket = rightBody.indexOf('(');
		int x = 
				rightBody.substring(0, firstLeftBracket).indexOf("new");
		String className = "";
		String methName = "";
		int y = 0, z = 0;
		if (x != -1) {
			y = rightBody.indexOf('(');
			className = rightBody.substring(x+3, y).trim();
			methName = className;
		} else {
			y = rightBody.indexOf('.');
			//we only consider t.m(t) and new C(t)
			//todo: for expression (C)t, we should check
			//that there is a inheritance relation between C and type of t
			//for FJ, maybe there is not a problem because the stupid cast
			if (y == -1) {
				return;
			}
			z = rightBody.indexOf('(');
			className = varTypeChanges.get(rightBody.substring(0, y).trim()).getSecond(); 
			methName = rightBody.substring(y+1, z).trim();
			y = z;
		}
		z = rightBody.lastIndexOf(')');
		String[] paraNames = rightBody.substring(y+1, z).split(",");
		
		ArrayList<String> paraTypes = new ArrayList<String>();
		for (int i = 0; i < paraNames.length; i++) {
			String tmpStr = paraNames[i].trim();
			tmpStr = Common.removeBracket(tmpStr);
			//consider the expressions with new
			//todo: we should also consider expression with cast
			int posNew = tmpStr.indexOf("new");
			if (posNew != -1) {
				int pos = tmpStr.indexOf('(');
				paraTypes.add(tmpStr.substring(posNew+3, pos).trim());
			} else if (!tmpStr.equals("") && varTypeChanges.containsKey(tmpStr)) {
				paraTypes.add(
						varTypeChanges.get(tmpStr).getSecond());
			}
		}
		HashMap<String, ArrayList<String>> tmpMap 
			= new HashMap<String, ArrayList<String>>();
		tmpMap.put(methName, paraTypes);
		if (!newMethods.containsKey(className))
			newMethods.put(className, tmpMap);
		else
			newMethods.get(className).putAll(tmpMap);
//		print(className, newMethods);
	}
	
	private void parseError(String str) {
		System.err.println("[Parse Error]" + str);
		System.exit(-1);
	}
	private void checkError(String str) {
		System.err.println("[Check Error]" + str);
		System.exit(-1);
	}
}
