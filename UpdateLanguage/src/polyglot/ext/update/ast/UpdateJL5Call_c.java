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
import polyglot.ext.update.util.*;

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
		
		if (match != null) {
			System.out.println("InPrintCall: 1: " + match.getBlockPair().getFirst().getTarget());
			System.out.println("InPrintCall: 2: " + match.getBlockPair().getSecond().getTarget());
		}
		
		if (match == null || match.getBlockPair().getSecond().isInvoke()) {
			printAsInvoke(w,tr);
		} else {
			System.out.println("Haha, I get here");
			printAsNew(w,tr);
		}
	}

	public void printAsInvoke(CodeWriter w, PrettyPrinter tr) {
		boolean daKaiYanJie = false;

        if (!targetImplicit) {
            if (target instanceof Expr) {
                printSubExpr((Expr) target, w, tr);
				w.write(".");
            } else if (target != null) {
                print(target, w, tr);
                w.write(".");
				System.out.println("Dakaiyanjie: " + target.getClass());
				daKaiYanJie = true;
			}
        }

		if (daKaiYanJie == true) {
			System.out.println("HouXu: " + name);
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
			int loop = match.getBlockPair().getSecond().getMethodName().size();
			ArrayList<String> methodName = match.getBlockPair().getSecond().getMethodName();
			ArrayList<ArrayList<String>> dstArgs = match.getBlockPair().getSecond().getArgs();
			ArrayList<ArrayList<String>> srcArgs = match.getBlockPair().getFirst().getArgs();

			for (int i = 0; i < loop; i ++) {
				if (i > 0) 
					w.write(".");
				
				w.write(methodName.get(i) + "(");
				w.begin(0);

				boolean isFirst = true;
				for (String tempStr : dstArgs.get(i)) {
					/*System.out.println("[BattleShip] " + tempStr);
					if (!isFirst) {
						w.write(",");
					}
					int n = match.lookUpDstVirtualNo(tempStr);
					if (n == -1) {
						System.out.println("[Cruiser] " + tempStr);
						w.write(tempStr);
					} else {
						Expr e = (Expr) arguments.get(n);
						print(e,w,tr);
					}
					isFirst = false;*/
					
					Object ca = CallArgs.getCallArgs(tempStr);
					if (ca instanceof CallArgs)
						((CallArgs)ca).substitution(match, arguments);
					if (!isFirst) {
						w.write(",");
					}
						
					System.out.println("[BG]" + ca.toString());
					w.write(ca.toString());
					isFirst = false;
				}
				w.end();
				w.write(")");
			}
		}
    }

	void printAsNew(CodeWriter w, PrettyPrinter tr) {
		w.write("new ");
		w.write(match.getBlockPair().getSecond().getTarget());

		ArrayList<String> dstArgs = match.getBlockPair().getSecond().getArgs().get(0);
		ArrayList<String> srcArgs = match.getBlockPair().getFirst().getArgs().get(0);

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
				print(e,w,tr);
			}
		}

		w.end();
		w.write(")");
	}
}
