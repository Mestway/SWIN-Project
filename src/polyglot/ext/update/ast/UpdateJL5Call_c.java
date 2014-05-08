package polyglot.ext.update.ast;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import polyglot.ast.Expr;
import polyglot.ast.Receiver;
import polyglot.ast.TypeNode;
import polyglot.ext.jl5.ast.JL5Call_c;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.PrettyPrinter;
import polyglot.ext.update.match.*;

public class UpdateJL5Call_c extends JL5Call_c
{
	protected ArrayList<String> argsOutput = new ArrayList<String>();
	protected String wholeOutputString = null;
	protected Matching match = null;

	public void setMatch(Matching match) {
		this.match = match;
	}

	public ArrayList<String> getArgsOutput() {
		return argsOutput;
	}

	public UpdateJL5Call_c(Position pos, Receiver target, String name, List arguments, List typeArguments) {
		super(pos, target, name, arguments, typeArguments);
	}

	@Override
	public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        if (!targetImplicit) {
            if (target instanceof Expr) {
                printSubExpr((Expr) target, w, tr);
                w.write(".");
            } else if (target != null) {
                print(target, w, tr);
                w.write(".");
            }
        }

        if (typeArguments.size() != 0) {
            w.write("<");
            for (Iterator it = typeArguments.iterator(); it.hasNext();) {
                print((TypeNode) it.next(), w, tr);
                if (it.hasNext()) {
                    w.write(", ");
                }
            }
            w.write(">");
        }

		if (this.match == null) {
			w.write(name + "(");
			w.begin(0);

			for (Iterator i = arguments.iterator(); i.hasNext();) {
				Expr e = (Expr) i.next();
				print(e, w, tr);
				if (i.hasNext()) {
					w.write(",");
					w.allowBreak(0, " ");
				}
			}
			w.end();
			w.write(")");
		} else {
			int loop = match.getBlockPair().second().getMethodName().size();
			ArrayList<String> methodName = match.getBlockPair().second().getMethodName();
			ArrayList<ArrayList<String>> dstArgs = match.getBlockPair().second().getArgs();
			ArrayList<ArrayList<String>> srcArgs = match.getBlockPair().first().getArgs();

			for (int i = 0; i < loop; i ++) {
				if (i > 0) 
					w.write(".");
				
				w.write(methodName.get(i) + "(");
				w.begin(0);

				boolean isFirst = true;
				for (String tempStr : dstArgs.get(i)) {
					if (!isFirst) {
						w.write(",");
					}
					int n = match.lookUpDstVirtualNo(tempStr);
					if (n == -1) {
						w.write(tempStr);
					} else {
						Expr e = (Expr) arguments.get(n);
						print(e,w,tr);
					}
					isFirst = false;
				}
				w.end();
				w.write(")");
			}
		}
    }

	void printAsUpdateJL5New_c(CodeWriter w, PrettyPrinter tr) {
		
	}
}
