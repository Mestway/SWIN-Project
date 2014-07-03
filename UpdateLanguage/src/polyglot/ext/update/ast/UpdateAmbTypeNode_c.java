package polyglot.ext.update.ast;

import java.util.List;

import polyglot.ast.QualifierNode;
import polyglot.ext.jl5.ast.JL5AmbTypeNode_c;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.PrettyPrinter;

/*************************************************************************
	> File Name: AmbTypeNode_up.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Mon 21 Apr 2014 04:06:56 AM PDT
 ************************************************************************/

public class UpdateAmbTypeNode_c extends JL5AmbTypeNode_c {

	public UpdateAmbTypeNode_c(Position pos, QualifierNode qual, String name, List typeArguments) {
		super(pos, qual, name, typeArguments);
	}

	@Override
	public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
		w.write("End");
	}

}
