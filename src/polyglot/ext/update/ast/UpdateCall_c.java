package polyglot.ext.update.ast;

import java.util.List;

import polyglot.ast.Receiver;
import polyglot.ext.jl.ast.Call_c;
import polyglot.util.Position;

public class UpdateCall_c extends Call_c
{
	public UpdateCall_c(Position pos, Receiver target, String name, List arguments) {
		super(pos, target, name, arguments);
	}
}
