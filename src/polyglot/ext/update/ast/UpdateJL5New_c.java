package polyglot.ext.update.ast;

import java.util.List;
import java.util.ArrayList;

import polyglot.ast.ClassBody;
import polyglot.ast.Expr;
import polyglot.ast.TypeNode;
import polyglot.ext.update.ast.UpdateJL5CanonicalTypeNode_c;
import polyglot.ext.jl5.ast.JL5New_c;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.PrettyPrinter;
import polyglot.ext.update.match.Matching;

/*************************************************************************
	> File Name: ast/UpdateJL5New_c.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Mon 28 Apr 2014 04:41:33 AM PDT
 ************************************************************************/

public class UpdateJL5New_c extends JL5New_c {

	protected String outputName = null;
	protected Matching match = null;

	public UpdateJL5New_c(Position pos, Expr qualifier, TypeNode tn, 
			List arguments, ClassBody body, List typeArguments) {
		super(pos, qualifier, tn, arguments, body, typeArguments);
	}

	@Override
	public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
		if (match == null || match.getBlockPair().second().isNew())	{
			printAsNew(w,tr);
		} else {
			printAsNew(w,tr);
		}
	}

	protected void printAsNew(CodeWriter w, PrettyPrinter tr) {
		printQualifier(w, tr);

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
		
		if (match == null) {
			printArgs(w,tr);
		} else {
			ArrayList<String> dstArgs = match.getBlockPair().second().getArgs().get(0);
			ArrayList<String> srcArgs = match.getBlockPair().first().getArgs().get(0);

			w.write("(");
			w.begin(0);
			
			boolean isFirst = true;
			for (String tempStr : dstArgs) {
				if (!isFirst) {
					w.write(",");
				}
				isFirst = false;
				int n = match.lookUpDstVirtualNo(tempStr);
				if (n == -1) {
					w.write(tempStr);
				} else {
					Expr e = (Expr) arguments.get(n);
					print(e, w, tr);
				}
			}
			w.end();
			w.write(")");
		}
		
		printBody(w,tr);
	}

	public void printAsInvoke(CodeWriter w, PrettyPrinter tr) {
	
	}

	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

	public void setMatch(Matching match) {
		this.match = match;
	}
}
