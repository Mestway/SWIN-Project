/*************************************************************************
	> File Name: util/Pair.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Sat 07 Jun 2014 01:40:48 AM PDT
 ************************************************************************/

package polyglot.ext.update.util;

public class Pair<A,B> {

	A first;
	B second;
	
	public Pair() {
		first = null;
		second = null;
	}

	public Pair(A f, B s) {
		this.first = f;
		this.second = s;
	}

	public void setFirst(A first) {
		this.first = first;
	}

	public void setSecond(B second) {
		this.second = second;
	}

	public A getFirst() {
		return this.first;
	}

	public B getSecond() {
		return this.second;
	}

	public void print() {
		System.out.println(first + " " + second);
	}
}
