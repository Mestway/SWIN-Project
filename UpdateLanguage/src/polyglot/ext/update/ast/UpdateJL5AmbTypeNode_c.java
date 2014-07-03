package polyglot.ext.update.ast;

import java.util.List;

import polyglot.ast.QualifierNode;
import polyglot.ext.jl5.ast.JL5AmbTypeNode_c;
import polyglot.util.Position;

/*************************************************************************
	> File Name: AmbTypeNode_up.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Mon 21 Apr 2014 04:06:56 AM PDT
 ************************************************************************/

public class UpdateJL5AmbTypeNode_c extends JL5AmbTypeNode_c {

	public UpdateJL5AmbTypeNode_c(Position pos, QualifierNode qual, String name, List typeArguments) {
		super(pos, qual, name, typeArguments);
	}


}
