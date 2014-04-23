/*************************************************************************
	> File Name: CodeRefactoring.java
	> Author: Stanley Wong
	> Mail: stangley.chenglongwang@gmail.com 
	> Created Time: Sun 20 Apr 2014 01:56:21 AM PDT
 ************************************************************************/

package polyglot.ext.update.visit;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ext.jl.ast.TypeNode_c;
import polyglot.ext.jl.types.PrimitiveType_c;
import polyglot.ext.update.ast.*;
import polyglot.frontend.Job;
import polyglot.frontend.OutputPass;
import polyglot.frontend.TargetFactory;
import polyglot.types.PrimitiveType;
import polyglot.types.TypeSystem;
import polyglot.visit.NodeVisitor;
import polyglot.visit.Translator;

public class CodeRefactoring extends NodeVisitor
{
	NodeFactory nf;
	
	public CodeRefactoring(NodeFactory nf) {	
		this.nf = nf;
		System.out.println("Code Refactoring Begin");
	}

	@Override
	public NodeVisitor enter(Node n) {
		if (n instanceof UpdateJL5CanonicalTypeNode_c) {
			String outputName = null;
			((UpdateJL5CanonicalTypeNode_c)n).setOutputName(outputName);
		} else if (n instanceof UpdateJL5Call_c) {
			String outputName = "Haha";
			((UpdateJL5Call_c)n).setOutputName(outputName);
		}
		return this;
	}
}
