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
import polyglot.frontend.Job;
import polyglot.frontend.OutputPass;
import polyglot.frontend.TargetFactory;
import polyglot.types.PrimitiveType;
import polyglot.types.TypeSystem;
import polyglot.visit.NodeVisitor;
import polyglot.visit.Translator;

public class CodeRefactoring extends Translator
{
	Job job;
	TypeSystem ts;
	NodeFactory nf;
	TargetFactory tf;
	
	public CodeRefactoring(Job job, TypeSystem ts, NodeFactory nf, TargetFactory tf) {
		super(job, ts, nf, tf);
	
		System.out.println("Code Generation");
	}
}
