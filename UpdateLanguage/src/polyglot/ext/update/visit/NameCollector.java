/*************************************************************************
	> File Name: NameCollector.java
	> Author: Stanley Wong
	> Mail: stangley.chenglongwang@gmail.com 
	> Created Time: Sun 20 Apr 2014 01:56:21 AM PDT
 ************************************************************************/

package polyglot.ext.update.visit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ext.update.ast.*;
import polyglot.frontend.Job;
import polyglot.types.Context;
import polyglot.types.ReferenceType;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;

public class NameCollector extends NodeVisitor
{
	NodeFactory nf;
	Context context;

	public NameCollector(Job job, NodeFactory nf) {	
		this.context = job.context();
		this.nf = nf;
		System.out.println("Name Collector Begin");
	}

	@Override
	public NodeVisitor enter(Node n) {
		if (n instanceof UpdateJL5CanonicalTypeNode_c) {
			typeNodeHandler((UpdateJL5CanonicalTypeNode_c)n);
		} else if (n instanceof UpdateJL5Call_c) {
			callNodeHandler((UpdateJL5Call_c)n);
		}
		return this;
	}

	private void typeNodeHandler(UpdateJL5CanonicalTypeNode_c node) {
		collectClassType(node.toString());
	}

	private void callNodeHandler(UpdateJL5Call_c node) {
		try {
			ReferenceType targetType = node.findTargetType();
			collectClassType(targetType.toString());
		} catch (SemanticException e) {
			System.err.println("JL5Call_c Node Type cannot find: " + node.toString());	
		}
	}

	private void collectClassType(String type) {	
		String tempType = type;
		String tempRegex = "<[^<>]*>";
		
		Pattern tempPattern = Pattern.compile(tempRegex);
		
		Matcher tempMatcher = tempPattern.matcher(tempType);
		if (tempMatcher.find()) {
			String arguments = tempMatcher.group();
			arguments = arguments.substring(1, arguments.length()-1);
			String[] parts = arguments.split(",");
			for (String part : parts) {
				ClassTypeString.collectTypeArgs(part);
			}
			tempType = tempType.substring(0, tempMatcher.start()) + tempType.substring(tempMatcher.end(), tempType.length());
		}

		if (tempType.contains("<") && tempType.contains(">"))
			collectClassType(tempType);
		else {
			//System.out.println("tempType -- " + tempType);
			ClassTypeString.collectTypeName(tempType);
		}
	}

}
