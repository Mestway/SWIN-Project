/*************************************************************************
	> File Name: CodeRefactoring.java
	> Author: Stanley Wong
	> Mail: stangley.chenglongwang@gmail.com 
	> Created Time: Sun 20 Apr 2014 01:56:21 AM PDT
 ************************************************************************/

package polyglot.ext.update.visit;

import java.io.*;
import java.util.ArrayList;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ext.update.ast.*;
import polyglot.ext.update.match.Matching;
import polyglot.visit.NodeVisitor;

public class CodeRefactoring extends NodeVisitor
{
	NodeFactory nf;
	String fileName = "MatchInfo.in";
	ArrayList<Matching> rawMatching = new ArrayList<Matching>();

	public CodeRefactoring(NodeFactory nf) {	
		this.nf = nf;
		System.out.println("Code Refactoring Begin");
		
		File file = new File(fileName);
		BufferedReader reader = null;
		System.out.println(file.getAbsolutePath());

		try {
			reader = new BufferedReader(new FileReader(file));
			
			String tempString = null;
			
			while ((tempString = reader.readLine()) != null) {
				//System.out.println(":" + tempString);
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
			String outputName = null;
			((UpdateJL5CanonicalTypeNode_c)n).setOutputName(outputName);
		} else if (n instanceof UpdateJL5Call_c) {
			String outputName = "Haha";
			((UpdateJL5Call_c)n).setOutputName(outputName);
		}
		return this;
	}
}
