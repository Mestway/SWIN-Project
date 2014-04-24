package polyglot.ext.update.match;

/*************************************************************************
	> File Name: match/Pair.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Wed 23 Apr 2014 08:07:34 AM PDT
 ************************************************************************/

public class Pair {
	public String v1 = null, v2 = null;

	public Pair() {
		v1 = null;
		v2 = null;
	}

	public Pair(String v1, String v2) {
		this.setPair(v1, v2);
	}

	public void setPair(String v1, String v2) {
		this.v1 = v1;
		this.v2 = v2;
	}
	
	public boolean add(String v) {
		if (this.v1 == null) {
			this.v1 = v;
			return true;
		} else if (this.v2 == null) {
			this.v2 = v;
			return true;
		} else return false;
	}
	
	public void print() {
		System.out.println(v1 + "---" + v2);
	}

}
