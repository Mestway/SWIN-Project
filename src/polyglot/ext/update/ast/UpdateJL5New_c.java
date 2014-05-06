package polyglot.ext.update.ast;

import java.util.List;

import polyglot.ast.ClassBody;
import polyglot.ast.Expr;
import polyglot.ast.TypeNode;
import polyglot.ext.update.ast.UpdateJL5CanonicalTypeNode_c;
import polyglot.ext.jl5.ast.JL5New_c;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.PrettyPrinter;

/*************************************************************************
	> File Name: ast/UpdateJL5New_c.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Mon 28 Apr 2014 04:41:33 AM PDT
 ************************************************************************/

public class UpdateJL5New_c extends JL5New_c {

	protected String outputName = null;
	protected Boolean transformToInvoke = false;

	public UpdateJL5New_c(Position pos, Expr qualifier, TypeNode tn, 
			List arguments, ClassBody body, List typeArguments) {
		super(pos, qualifier, tn, arguments, body, typeArguments);
	}

	@Override
	public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
		printQualifier(w, tr);

		if (!transformToInvoke)
			w.write("new ");
		
		if (qualifier != null) {
			if (outputName == null)
				w.write(tn.name());
			else 
				w.write(outputName);
		} else {
			if (tn instanceof UpdateJL5CanonicalTypeNode_c) {
				((UpdateJL5CanonicalTypeNode_c)tn).setOutputName(outputName);
			}
			print(tn, w, tr);
		}
		
		printArgs(w,tr);
		printBody(w,tr);
	}

	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

	public void setTransformToInvoke() {
		this.transformToInvoke = true;
	}

}
