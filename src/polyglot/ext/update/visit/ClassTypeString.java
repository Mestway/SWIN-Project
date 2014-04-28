package polyglot.ext.update.visit;

import java.util.ArrayList;

/*************************************************************************
	> File Name: visit/ClassTypeString.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Sun 27 Apr 2014 01:48:40 AM PDT
 ************************************************************************/

public class ClassTypeString {
	protected String typeName;
	protected ArrayList<String> typeArgs;

	public ClassTypeString() {
		typeName = new String();
		typeArgs = new ArrayList<String>();
	}

	public void typeName(String typeName) {
		this.typeName = typeName;
	}

	public String typeName() {
		return this.typeName;
	}

	public ArrayList<String> typeArgs() {
		return typeArgs;
	}
	
	public void print() {
		System.out.println("Class: " + typeName);
		for (String part : typeArgs) {
			System.out.println("Args: " + part);
		}
	}

}
