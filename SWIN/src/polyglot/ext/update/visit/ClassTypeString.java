package polyglot.ext.update.visit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import polyglot.ext.update.match.*;
import polyglot.ext.update.util.Pair;

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
	protected ArrayList<Object> typeArgs;

	public ClassTypeString() {
		typeName = new String();
		typeArgs = new ArrayList<Object>();
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

	public ArrayList<Object> typeArgs() {
		return this.typeArgs;
	}
	
	public String toTypeString() {
		String type = new String();
		type += typeName;
		if (typeArgs.size() > 0) {
			type += "<";
			for (int i = 0; i < typeArgs.size() - 1; i ++) {
				Object tmpArg = typeArgs.get(i);
				if (tmpArg instanceof String) {
					type += (String)tmpArg;
				} else if (tmpArg instanceof ClassTypeString) {
					type += ((ClassTypeString)tmpArg).toTypeString();
				}
				type += ",";
			}
			Object lastArg = typeArgs.get(typeArgs.size() - 1);
			if (lastArg instanceof String) {
				type += (String)lastArg;
			} else if (lastArg instanceof ClassTypeString) {
				type += ((ClassTypeString)lastArg).toTypeString();
			}
			type += ">";
		}
		return type;
	}

	// we need to handle recursive type arguments
	// This will change the structure of the ClassType with the new matching
	public ClassTypeString processMatching(Matching match) {
		if (classTypeCompare(typeName, match.getTypePair().getFirst())) {
			this.typeName = match.getTypePair().getSecond();	
		}

		ArrayList<Object> tempTypeArgs = new ArrayList<Object>();
		for (Object str : this.typeArgs) {
			if (str instanceof String) {
				if (classTypeCompare((String)str, match.getTypePair().getFirst())) {	
					tempTypeArgs.add(match.getTypePair().getSecond());
				} else {
					tempTypeArgs.add((String)str);
				}
			} else if (str instanceof ClassTypeString) {
				tempTypeArgs.add(((ClassTypeString)str).processMatching(match));
			}
		}

		this.typeArgs = tempTypeArgs;
		return this;
	}

	public boolean meetMatch(Matching match) {
		if (classTypeCompare(typeName, match.getTypePair().getFirst())) {
			if (match.getBlockPair().getFirst().isNew()) {
				if (classTypeCompare(typeName,match.getBlockPair().getFirst().getTarget())) {
					return true;
				}	
			}	
		}
		return false;
	}

	public void print() {
		System.out.println("Class: " + typeName);
		for (Object part : typeArgs) {
			System.out.println("Args: " + part.toString());
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
