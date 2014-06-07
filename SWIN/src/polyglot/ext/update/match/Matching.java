package polyglot.ext.update.match;

import polyglot.ext.update.util.Common;
import polyglot.ext.update.util.Pair;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

/*************************************************************************
	> File Name: match/Matching.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Wed 23 Apr 2014 08:01:35 AM PDT
 ************************************************************************/

public class Matching {
	
	protected Pair<String,String> typePair =  new Pair<String,String>();	
	protected ArrayList<Pair<TypeName,TypeName>> defPairs = new ArrayList<Pair<TypeName,TypeName>>();
	protected Pair<JavaBody,JavaBody> blockPair = new Pair<JavaBody,JavaBody>();

	private int passNo = 0;
	private int secondPassCount = 0;

	public Matching(String rawMatching) {
		parse(rawMatching.substring(1, rawMatching.length()-1));
	}
	

	/*public void processOld(String rawMatching) {
		rawMatching = rawMatching.substring(rawMatching.indexOf("[") + 1, rawMatching.indexOf("]"));
		
		// Match the old and new one in a MatchingCommand
		String pattern = "[^\\s]+\\s+\\([^\\(\\)]*\\)\\s+\\{[^\\{\\}]*\\}";
		Pattern r = Pattern.compile(pattern);
		Matcher matcher = r.matcher(rawMatching);

		while(matcher.find()) {
			// Parse the block
			String partString = matcher.group();
			String tempRegex = "\\{[^\\{\\}]*\\}";
			Pattern tempPattern = Pattern.compile(tempRegex);
			Matcher tempMatcher = tempPattern.matcher(partString);
			tempMatcher.find();
			String block = tempMatcher.group();	
			blockPair.add(new JavaBody(block.substring(1,block.length()-1)));
			partString = partString.substring(0, tempMatcher.start());

			// Parse the definition
			tempRegex = "\\([^\\(\\)]*\\)";
			tempPattern = Pattern.compile(tempRegex);
			tempMatcher = tempPattern.matcher(partString);
			tempMatcher.find();
			String def = tempMatcher.group();
			String[] defParts = def.substring(1, def.length()-1).split(",");
			for (String oneDef : defParts) {
				String[] oneDefParts = oneDef.split(" ");
				boolean first = true;
				TypeName tpnm = new TypeName();
				for (String t : oneDefParts) {
					if (!t.equals("")) {
						if (first) {
							tpnm.setType(t);
							first = false;
						} else {
							tpnm.setName(t);
						}
					}
				}
				if (passNo == 0) {
					Pair<TypeName> onlyOneDef = new Pair<TypeName>();
					onlyOneDef.add(tpnm);
					if (tpnm.useful())
						defPairs.add(onlyOneDef);
				} else if (passNo == 1) {
					if (tpnm.useful()) {
						defPairs.get(secondPassCount).add(tpnm);
						secondPassCount ++;
					}
				}
			}

			partString = partString.substring(0, tempMatcher.start());
			tempRegex = "\\S+";
			tempPattern = Pattern.compile(tempRegex);
			tempMatcher = tempPattern.matcher(partString);
			tempMatcher.find();
			String tmgp = tempMatcher.group();
			typePair.add(tmgp);

			passNo ++;
		}
		
		//this.printMatching();
		//print();
	}*/

	public void print() {
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		typePair.print();
		for (Pair<TypeName,TypeName> pair : defPairs) {
			System.out.println("Wula: " + pair.getFirst());
			System.out.println("Heis: " + pair.getSecond());
		}
		//System.out.println("**: " + blockPair.first().getTarget());
		//System.out.println("**: " + blockPair.second().getTarget());
		blockPair.getFirst().print();
		System.out.println("   Wait   ");
		blockPair.getSecond().print();
		System.out.println("::::::::::::::::::;One Done:::::::::::::::::::");
	}

	public Pair<String,String> getTypePair() {
		return typePair;
	}

	public ArrayList<Pair<TypeName,TypeName>> getDefPairs() {
		return defPairs;
	}

	public Pair<JavaBody,JavaBody> getBlockPair() {
		return blockPair;
	}

	// input a name, this will return the pair of name plus type
	public Pair<TypeName,TypeName> defLookUp(String v) {
		for (Pair<TypeName,TypeName> pr : defPairs) {
			if (pr.getFirst().getName().equals(v)) {
				return pr;
			}
		}	
		return null;
	}

	public Pair<TypeName,TypeName> defLookupDst(String name) {
		for (Pair<TypeName,TypeName> pr : defPairs) {
			if (pr.getSecond().getName().equals(name)) {
				return pr;
			}
		}
		return null;
	}

	// given the name of a meta-variable,
	// lookup the index of the variable in the src pattern
	public int lookUpDstVirtualNo(String vname) {
		
		String srcName = null;
		for (Pair<TypeName,TypeName> pr : defPairs) {
			if (pr.getSecond().getName().equals(vname)) {
				srcName = pr.getFirst().getName();
				break;
			}
		}

		int i = 0;
		int ans = -1;
		for (String str : blockPair.getFirst().getArgs().get(0)) {
			if (str.equals(srcName)) {
				ans = i;
				break;
			}
			i ++;
		}

		return ans;
	}

	protected void parse(String input) {
		String pattern = "\\[[^\\[\\]]*\\]";
		Pattern r = Pattern.compile(pattern);
		Matcher matcher = r.matcher(input);

		// parse body
		if (matcher.find()) {
			String body = matcher.group();
			parseBody(body.substring(1, body.length()-1));
			input = input.substring(0, matcher.start());
		} else {
			parseError("SWIN body not well defined");
		}

		// parse def
		pattern = "\\([^\\(\\)]*\\)";
		r = Pattern.compile(pattern);
		matcher = r.matcher(input);
		if (matcher.find()) {
			String def = matcher.group();
			parseDef(def.substring(1, def.length()-1));
		} else {
			parseError("SWIN def not well defined");
		}	
		print();
	}

	protected void parseDef(String input) {
		//System.out.println("THE DEF: " + input);
		ArrayList<String> segs = Common.splitByComma(input);
		for (String s : segs) {
			defPairs.add(TypeName.fromSWINDef(s));
		}
	}

	protected void parseBody(String input) {
		String left = input.substring(0, input.indexOf("->"));
		String right = input.substring(input.indexOf("->") + 2, input.length());
		left = Common.removeHeadTailBlank(left);
		right = Common.removeHeadTailBlank(right);

		String leftBody = left.substring(0, left.indexOf(":"));
		String leftType = left.substring(left.indexOf(":") + 1, left.length());

		String rightBody = right.substring(0,right.indexOf(":"));
		String rightType = right.substring(right.indexOf(":") + 1, right.length());

		typePair.setFirst(leftType);
		typePair.setSecond(rightType);

		blockPair.setFirst(new JavaBody(leftBody));
		
		//TODO:Continue Debugging here!!
		//(new Weibo()):Object --> problem
		blockPair.setSecond(new JavaBody(rightBody));
		
	}


	private void parseError(String str) {
		System.err.println("[Parse Error]" + str);
		System.exit(-1);
	}
}
