/*************************************************************************
	> File Name: match/SplitString.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Wed 07 May 2014 01:48:21 AM PDT
 ************************************************************************/

package polyglot.ext.update.match;

import java.util.ArrayList;

public class SplitString {
	protected String originalString = new String();
	protected String remainingString = new String();
	protected ArrayList<String> parts = new ArrayList<String>();
	protected ArrayList<Integer> begins = new ArrayList<Integer>();
	protected ArrayList<Integer> ends = new ArrayList<Integer>();

	public SplitString() {
		
	}

	public String getOriginal() {
		return originalString;
	}

	public String getRemaining() {
		return remainingString;
	}

	public void setOriginal(String ori) {
		this.originalString = ori;
	}

	public void setRemaining(String rmi) {
		this.remainingString = rmi;
	}

	public ArrayList<String> getParts() {
		return parts;
	}

	public ArrayList<Integer> getBegins() {
		return begins;
	}

	public ArrayList<Integer> getEnds() {
		return ends;
	}

	public void print() {
		System.out.println("-------------------XXXXX----------------");
		System.out.println("original: " + originalString);
		System.out.println("remaining: " + remainingString);
		for (String i : parts) {
			System.out.println("pt: " + i);
		}
		System.out.println("-----------------------------------------");
	}
}
