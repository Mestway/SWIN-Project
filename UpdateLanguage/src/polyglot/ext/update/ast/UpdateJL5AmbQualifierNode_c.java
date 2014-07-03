package polyglot.ext.update.ast;

import java.util.List;

import polyglot.ast.QualifierNode;
import polyglot.ext.jl5.ast.JL5AmbQualifierNode_c;
import polyglot.util.Position;

/*************************************************************************
	> File Name: ast/UpdateJL5AmbQualifierNode_c.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Tue 22 Apr 2014 04:30:03 AM PDT
 ************************************************************************/

public class UpdateJL5AmbQualifierNode_c extends JL5AmbQualifierNode_c {

	public UpdateJL5AmbQualifierNode_c(Position pos, QualifierNode qual, String name, List typeArguments) {
		super(pos, qual, name, typeArguments);
		System.out.println("QualifierNode: " + name);
	}

}
