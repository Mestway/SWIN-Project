package polyglot.ext.update.visit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

/*************************************************************************
	> File Name: visit/ClassTypeString.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Sun 27 Apr 2014 01:48:40 AM PDT
 ************************************************************************/

public class ClassTypeString {
	
	public static HashMap<String,String> nameMap = new HashMap<String,String>();
	public static HashSet<String> bannedName = new HashSet<String>();
	public static Boolean Visited = false;

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

	public static void collectTypeName(String typeName) {
		ClassTypeString.addMapEntry(typeName);
	}

	public static void collectTypeArgs(String typeArg) {
		ClassTypeString.addMapEntry(typeArg);
	}

	public ArrayList<String> typeArgs() {
		return this.typeArgs;
	}
	
	public void print() {
		System.out.println("Class: " + typeName);
		for (String part : typeArgs) {
			System.out.println("Args: " + part);
		}
	}

	public static String getShortName(String fullName) {
		String[] parts = fullName.split("\\.");
		return parts[parts.length - 1];
	}

	public static void addMapEntry(String fullName) {
		String shortName = getShortName(fullName);
		if (bannedName.contains(shortName)) {
			return;
		}
		if (nameMap.containsKey(shortName)) {
			if (!nameMap.get(shortName).equals(fullName)) {
				bannedName.add(shortName);
				nameMap.remove(shortName);
			}
		} else {
			nameMap.put(shortName, fullName);
		}
	}

	public static void printMap() {
		System.out.println("------------- NameMap ----------");
		for (Entry<String, String> entry : nameMap.entrySet()) {
			System.out.println(entry.getKey() + " --- " + entry.getValue());
		}
		System.out.println("------------- Banned ----------");
		for (String entry : bannedName) {
			System.out.println(entry);
		}
	}
}
