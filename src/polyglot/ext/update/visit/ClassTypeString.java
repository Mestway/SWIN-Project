package polyglot.ext.update.visit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import polyglot.ext.update.match.*;

/*************************************************************************
	> File Name: visit/ClassTypeString.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Sun 27 Apr 2014 01:48:40 AM PDT
 ************************************************************************/

public class ClassTypeString {
	
	protected static HashMap<String,String> nameMap = new HashMap<String,String>();
	protected static HashSet<String> bannedName = new HashSet<String>();
	protected static Boolean Visited = false;

	/* typeName specifies the name of a type without counting the type arguments */
	protected String typeName;
	/* typeArgs specifies the type arugmetns */
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
	
	public String toTypeString() {
		String type = new String();
		type += typeName;
		if (typeArgs.size() > 0) {
			type += "<";
			for (int i = 0; i < typeArgs.size() - 1; i ++) {
				type += typeArgs.get(i);
				type += ",";
			}
			type += typeArgs.get(typeArgs.size() - 1);
			type += ">";
		}
		return type;
	}

	public ClassTypeString processMatching(Matching match) {
		if (classTypeCompare(typeName, match.getTypePair().first())) {
			this.typeName = match.getTypePair().second();	
		}

		ArrayList<String> tempTypeArgs = new ArrayList<String>();
		for (String str : this.typeArgs) {
			if (classTypeCompare(str, match.getTypePair().first())) {	
				tempTypeArgs.add(match.getTypePair().second());
			} else {
				tempTypeArgs.add(str);
			}
		}

		this.typeArgs = tempTypeArgs;
		return this;
	}

	public void print() {
		System.out.println("Class: " + typeName);
		for (String part : typeArgs) {
			System.out.println("Args: " + part);
		}
		System.out.println("All: " + toTypeString());
	}

	public static String getShortName(String fullName) {
		String[] parts = fullName.split("\\.");
		return parts[parts.length - 1];
	}

	public static boolean classTypeCompare(String first, String second) {
		if (first.equals(second)) 
			return true;
		else if (!bannedName.contains(getShortName(first)) && !bannedName.contains(getShortName(second))
				&& getShortName(first).equals(getShortName(second))){
			return true;
		} else {
			return false;
		}
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

	public static HashSet<String> bannedName() {
		return bannedName;
	}

	public static HashMap<String,String> nameMap() {
		return nameMap;
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
