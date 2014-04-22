package polyglot.ext.update.ast;

import polyglot.ast.*;
import polyglot.ext.jl.ast.*;
import polyglot.types.Flags;
import polyglot.types.Package;
import polyglot.types.Type;
import polyglot.types.Qualifier;
import polyglot.util.*;
import java.util.*;

/**
 * NodeFactory for update extension.
 */
public class UpdateNodeFactory_c extends NodeFactory_c implements UpdateNodeFactory {
    // TODO:  Implement factory methods for new AST nodes.
    // TODO:  Override factory methods for overriden AST nodes.
    // TODO:  Override factory methods for AST nodes with new extension nodes.
	public Binary
	Binary(Position pos, Expr left, Binary.Operator op, Expr right) {
		Binary n = new UpdateBinary_c(pos, left, op, right);
		n = (Binary)n.ext(extFactory.extBinary());
		n = (Binary)n.del(delFactory.delBinary());
		return n;
	}

	public Call
	Call(Position pos, Receiver target, String name, List args) {
		Call n = new UpdateCall_c(pos, target, name, args);
		n = (Call)n.ext(extFactory.extCall());
		n = (Call)n.del(delFactory.delCall());
		return n;
	}

	public AmbTypeNode
	AmbTypeNode(Position pos, QualifierNode qualifier, String name) {
		AmbTypeNode n = new UpdateAmbTypeNode_c(pos, qualifier, name);
		n = (AmbTypeNode)n.ext(extFactory.extAmbTypeNode());
		n = (AmbTypeNode)n.del(delFactory.delAmbTypeNode());
		return n;
	}

}
