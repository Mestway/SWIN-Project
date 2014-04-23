package polyglot.ext.update.ast;

import polyglot.ast.*;
import polyglot.ext.jl5.ast.*;
import polyglot.types.Flags;
import polyglot.types.Package;
import polyglot.types.Type;
import polyglot.types.Qualifier;
import polyglot.util.*;
import java.util.*;

/**
 * NodeFactory for update extension.
 */
public class UpdateNodeFactory_c extends JL5NodeFactory_c implements UpdateNodeFactory {
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

	public JL5Call
	JL5Call(Position pos, Receiver target, String name, List args, List typeArgs) {
		JL5Call n = new UpdateJL5Call_c(pos, target, name, args, typeArgs);
		return n;
	}

	public AmbTypeNode
	AmbTypeNode(Position pos, QualifierNode qualifier, String name) {
		AmbTypeNode n = new UpdateAmbTypeNode_c(pos, qualifier, name, new LinkedList());
		n = (AmbTypeNode)n.ext(extFactory.extAmbTypeNode());
		n = (AmbTypeNode)n.del(delFactory.delAmbTypeNode());
		return n;
	}

	public AmbTypeNode JL5AmbTypeNode(Position pos, QualifierNode qualifier, String name, List args) {
		AmbTypeNode n = new UpdateJL5AmbTypeNode_c(pos, qualifier, name, args);
		return n;
	}

	public AmbQualifierNode JL5AmbQualiferNode(Position pos, QualifierNode qual, String name, List args) {
		AmbQualifierNode n = new UpdateJL5AmbQualifierNode_c(pos, qual, name, args);
		return n;
	}

	public JL5CanonicalTypeNode CanonicalTypeNode(Position pos, Type t) {
		JL5CanonicalTypeNode n = new UpdateJL5CanonicalTypeNode_c(pos, t);
		return n;
	}

}
