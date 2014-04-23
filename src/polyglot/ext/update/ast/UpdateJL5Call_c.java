package polyglot.ext.update.ast;

import java.util.Iterator;
import java.util.List;

import polyglot.ast.Expr;
import polyglot.ast.Receiver;
import polyglot.ast.TypeNode;
import polyglot.ext.jl5.ast.JL5Call_c;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.PrettyPrinter;

public class UpdateJL5Call_c extends JL5Call_c
{
	private String outputName = null;
	
	public void setOutputName(String name) {
		outputName = name;
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

		String outputName = name;
		if (this.outputName != null) {
			outputName = this.outputName;
		} 

        w.write(outputName + "(");
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
    }
}
