package polyglot.ext.update.match;

/*************************************************************************
	> File Name: match/Pair.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Wed 23 Apr 2014 08:07:34 AM PDT
 ************************************************************************/

public class Pair<T> {
	protected T v1 = null;
	protected T v2 = null;

	public Pair() {
		v1 = null;
		v2 = null;
	}

	public Pair(T v1, T v2) {
		this.setPair(v1, v2);
	}

	public void setPair(T v1, T v2) {
		this.v1 = v1;
		this.v2 = v2;
	}
	
	public boolean add(T v) {
		if (this.v1 == null) {
			this.v1 = v;
			return true;
		} else if (this.v2 == null) {
			this.v2 = v;
			return true;
		} else return false;
	}

	public T first() {
		return v1;
	}

	public T second() {
		return v2;
	}
	
	public void print() {
		System.out.println(v1.toString() + "---" + v2.toString());
	}

}
