package polyglot.ext.update.match;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*************************************************************************
	> File Name: match/Matching.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Wed 23 Apr 2014 08:01:35 AM PDT
 ************************************************************************/

public class Matching {
	
	Pair typePair =  new Pair();	
	Pair defPair = new Pair();
	Pair blockPair = new Pair();

	public Matching(String rawMatching) {
		rawMatching = rawMatching.substring(rawMatching.indexOf("[") + 1, rawMatching.indexOf("]"));
		
		// Match the old and new one in a MatchingCommand
		String pattern = "\\w+\\s+\\([^\\(\\)]*\\)\\s+\\{[^\\{\\}]*\\}";
		Pattern r = Pattern.compile(pattern);
		Matcher matcher = r.matcher(rawMatching);

		while(matcher.find()) {
			String partString = matcher.group();
			String tempRegex = "\\{[^\\{\\}]*\\}";
			Pattern tempPattern = Pattern.compile(tempRegex);
			Matcher tempMatcher = tempPattern.matcher(partString);
			tempMatcher.find();
			String block = tempMatcher.group();
			blockPair.add(block.substring(1,block.length()-1));
			partString = partString.substring(0, tempMatcher.start());
			
			tempRegex = "\\([^\\(\\)]*\\)";
			tempPattern = Pattern.compile(tempRegex);
			tempMatcher = tempPattern.matcher(partString);
			tempMatcher.find();
			String def = tempMatcher.group();
			defPair.add(def.substring(1,def.length()-1));
			partString = partString.substring(0, tempMatcher.start());
			
			tempRegex = "\\S+";
			tempPattern = Pattern.compile(tempRegex);
			tempMatcher = tempPattern.matcher(partString);
			tempMatcher.find();
			typePair.add(tempMatcher.group());
		}
		
		//typePair.print();
		//defPair.print();
		//blockPair.print();
	}
}
