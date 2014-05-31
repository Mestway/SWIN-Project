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
			printAsInvoke(w,tr);
		}
	}

	protected void printAsNew(CodeWriter w, PrettyPrinter tr) {
		printQualifier(w, tr);

		w.write("new ");
		
		if (qualifier != null) {
			String[] outputSpt = outputName.split("\\.");
			outputName = outputSpt[outputSpt.length - 1];
			
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
			// This will not be a sequence, so we just pick the first one.
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
		
		ArrayList<ArrayList<String>> dstArgs = match.getBlockPair().second().getArgs();
		ArrayList<ArrayList<String>> srcArgs = match.getBlockPair().first().getArgs();
		ArrayList<String> dstMethods = match.getBlockPair().second().getMethodName();
		String dstTargetName = match.getBlockPair().second().getTarget();

		// which one invoke the function.
		int n = match.lookUpDstVirtualNo(dstTargetName);
		if (n == -1) {
			w.write(dstTargetName);
		} else {
			Expr e = (Expr) arguments.get(n);
			print(e,w,tr);
		}
		
		int loop = dstMethods.size();
		for (int i = 0; i < loop; i ++) {
			w.write(".");
			w.write(dstMethods.get(i) + "(");
			w.begin(0);
			
			boolean isFirst = true;
			for (String tempStr : dstArgs.get(i)) {
				if (!isFirst) {
					w.write(",");
				}
				isFirst = false;
				int n2 = match.lookUpDstVirtualNo(tempStr);
				if (n2 == -1) {
					w.write(tempStr);
				} else {
					Expr e = (Expr) arguments.get(n2);
					print(e,w,tr);
				}
			}
			w.end();
			w.write(")");
		}
	}

	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

	public void setMatch(Matching match) {
		this.match = match;
	}
}
