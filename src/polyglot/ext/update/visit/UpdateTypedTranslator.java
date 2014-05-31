/*************************************************************************
	> File Name: visit/UpdateTypedTranslator.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Thu 08 May 2014 06:37:35 PM PDT
 ************************************************************************/

package polyglot.ext.update.visit;

import polyglot.visit.TypedTranslator;
import polyglot.ext.update.match.Matching;
import polyglot.ast.*;
import polyglot.frontend.*;
import polyglot.types.*;
import polyglot.util.CodeWriter;

import java.util.ArrayList;

public class UpdateTypedTranslator extends TypedTranslator {
	
	protected ArrayList<Matching> matchList = null;
	protected ArrayList<String> dummyClasses = new ArrayList<String>();
	protected ArrayList<ArrayList<String>> dummyArgs = new ArrayList<ArrayList<String>>();

	public UpdateTypedTranslator(Job job, TypeSystem ts, NodeFactory nf,
				TargetFactory tf) {
		super(job, ts, nf, tf);
	}
	
	protected void writeHeader(SourceFile sfn, CodeWriter w) {
		super.writeHeader(sfn, w);		
	
		for (int i = 0; i < dummyClasses.size(); i ++) {
			writeDummyClass(w, dummyClasses.get(i), dummyArgs.get(i));
		}
	}

	public ArrayList<String> getDummyClasses() {
		return dummyClasses;
	}

	public ArrayList<ArrayList<String>> getDummyArgs() {
		return dummyArgs;
	}

	public void addDummy(String name, ArrayList<String> args) {
		dummyClasses.add(name);
		dummyArgs.add(args);
	}

	public void printDummy() {
		for (int i = 0; i < dummyClasses.size(); i ++) {
			System.out.println("DummyClass: " + dummyClasses.get(i));
			for (String j : dummyArgs.get(i)) {
				System.out.println("	arg: " + j);
			}
		}
	}

	private void writeDummyClass(CodeWriter w, String className, ArrayList<String> argTypes) {
		w.write("class " + className + " {");
		String args = new String();
		for (int i = 0; i < argTypes.size(); i ++) {
			if (i > 0) args += ",";
			args += argTypes.get(i) + " a" + i;
		}
		w.write("public " + className + "(" + args + ")" + " { }");

		w.write("};");
		w.newline();
	}

}
