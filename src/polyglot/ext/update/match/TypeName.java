/*************************************************************************
	> File Name: match/TypeName.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Tue 06 May 2014 05:36:35 AM PDT
 ************************************************************************/
package polyglot.ext.update.match;


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
}
