package polyglot.ext.update.ast;

import polyglot.ast.QualifierNode;
import polyglot.ext.jl.ast.AmbTypeNode_c;
import polyglot.util.Position;

/*************************************************************************
	> File Name: AmbTypeNode_up.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Mon 21 Apr 2014 04:06:56 AM PDT
 ************************************************************************/

public class UpdateAmbTypeNode_c extends AmbTypeNode_c {

	public UpdateAmbTypeNode_c(Position pos, QualifierNode qual, String name) {
		super(pos, qual, name);
		System.out.println("AmbTypeNode: " + name);
	}


}
