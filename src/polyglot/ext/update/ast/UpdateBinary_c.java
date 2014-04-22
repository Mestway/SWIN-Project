package polyglot.ext.update.ast;

import polyglot.ast.Binary;
import polyglot.ast.Expr;
import polyglot.ext.jl.ast.Binary_c;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.PrettyPrinter;

public class UpdateBinary_c extends Binary_c implements Binary
{
	public UpdateBinary_c(Position pos, Expr left, Operator op, Expr right) {
		super(pos, left, op, right);
	}

	@Override
	public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
		printSubExpr(left, true, w, tr);
		w.write(" ");
		w.write(op.toString());
		w.allowBreak(type() == null || type().isPrimitive() ? 2 : 0, " ");
		printSubExpr(right, false, w, tr);
	}

}
