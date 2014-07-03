/*************************************************************************
	> File Name: match/TypeName.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Tue 06 May 2014 05:36:35 AM PDT
 ************************************************************************/
package polyglot.ext.update.match;

import polyglot.ext.update.util.Common;
import polyglot.ext.update.util.Pair;

public class TypeName {
	protected String type = null;
	protected String name = null;

	TypeName() {
		type = null;
		name = null;
	}

	public void setTypeName(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return type + " " + name; 
	}

	public boolean useful() {
		if (type == null || name == null) {
			return false;
		} else {
			return true;
		}
	}

	public static Pair<TypeName,TypeName> fromSWINDef(String str) {
		TypeName src = new TypeName();
		TypeName dst = new TypeName();
		
		String head = Common.removeHeadTailBlank(str.substring(0, str.indexOf(":")));
		String tail = str.substring(str.indexOf(":") + 1, str.length());

		String oClass = tail.substring(0, tail.indexOf("->>"));
		String nClass = tail.substring(tail.indexOf("->>")+3, tail.length());
		//System.out.println("HD: " + head + " -- " + "TL: " + oClass + "#" + nClass);

		src.setTypeName(Common.removeHeadTailBlank(oClass), head);
		dst.setTypeName(Common.removeHeadTailBlank(nClass), head);

		return new Pair<TypeName,TypeName>(src,dst);
	}
}
