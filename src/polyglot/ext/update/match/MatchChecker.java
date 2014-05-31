/*************************************************************************
	> File Name: match/MatchChecker.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Tue 13 May 2014 07:11:15 PM PDT
 ************************************************************************/

package polyglot.ext.update.match;

import java.util.ArrayList;
import java.util.HashMap;

public class MatchChecker {
	
	protected ArrayList<Matching> matchList = new ArrayList<Matching>();
	protected ArrayList<String> dummyClasses = null;
	protected ArrayList<ArrayList<String>> dummyArgs = null;

	protected HashMap<String, String> classMap = new HashMap<String, String>();

	public MatchChecker(ArrayList<Matching> matchList) {
		this.matchList = matchList;
	}

	public void setDummyClasses(ArrayList<String> dummyC, ArrayList<ArrayList<String>> dummyA) {
		dummyClasses = dummyC;
		dummyArgs = dummyA;
	}

	public boolean check() {

		return true;
	}

	protected void collectClassMapping() {
		for (Matching match : matchList) {
			String src = match.getTypePair().first();
			String dst = match.getTypePair().second();

			if (classMap.get(src) != null && !classMap.get(src).equals(dst)) {
				System.err.println("Match Check Failed: Not one-one corresponding");
				System.exit(-1);
			} else {
				classMap.put(src,dst);
			}
		}
	}

}
