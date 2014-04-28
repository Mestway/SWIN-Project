/*************************************************************************
	> File Name: CodeRefactoring.java
	> Author: Stanley Wong
	> Mail: stangley.chenglongwang@gmail.com 
	> Created Time: Sun 20 Apr 2014 01:56:21 AM PDT
 ************************************************************************/

package polyglot.ext.update.visit;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import polyglot.types.Type;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ext.jl.types.*;
import polyglot.ext.jl5.types.*;
import polyglot.ext.update.ast.*;
import polyglot.ext.update.match.Matching;
import polyglot.frontend.Job;
import polyglot.types.Context;
import polyglot.types.ReferenceType;
import polyglot.types.TypeSystem;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;

public class CodeRefactoring extends NodeVisitor
{
	NodeFactory nf;
	String fileName = "MatchInfo.in";
	ArrayList<Matching> rawMatching = new ArrayList<Matching>();
	Context context;

	public CodeRefactoring(Job job, NodeFactory nf) {	
		this.context = job.context();
		this.nf = nf;
		
		System.out.println("Code Refactoring Begin");
		
		File file = new File(fileName);
		BufferedReader reader = null;
		System.out.println(file.getAbsolutePath());

		try {
			reader = new BufferedReader(new FileReader(file));
			
			String tempString = null;
			
			while ((tempString = reader.readLine()) != null) {
				rawMatching.add(new Matching(tempString));
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void MatchProcessing(){
	
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
		String outputName = null;
		node.setOutputName(outputName);
		ClassTypeString classType = parseClassType(node.toString());
	}

	private void callNodeHandler(UpdateJL5Call_c node) {
		String outputName = null;
		node.setOutputName(outputName);
		try {
			ReferenceType targetType = node.findTargetType();
			ClassTypeString targetClassType = parseClassType(targetType.toString());
		} catch (SemanticException e) {
			System.err.println("JL5Call_c Node Type cannot find: " + node.toString());	
		}
	}

	private ClassTypeString parseClassType(String type) {	
		String tempType = type;
		ClassTypeString classType = new ClassTypeString();

		String tempRegex = "<[^<>]*>";
		Pattern tempPattern = Pattern.compile(tempRegex);
		Matcher tempMatcher = tempPattern.matcher(tempType);
		if (tempMatcher.find()) {
			String arguments = tempMatcher.group();
			arguments = arguments.substring(1, arguments.length()-1);
			String[] parts = arguments.split(",");
			for (String part : parts) {
				classType.typeArgs().add(part);
			}
			tempType = tempType.substring(0, tempMatcher.start());
		}
		classType.typeName(tempType);
		return classType;
	}

}
