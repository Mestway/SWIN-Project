package polyglot.ext.update.ast;

import polyglot.ext.jl5.ast.JL5CanonicalTypeNode_c;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.PrettyPrinter;
import polyglot.visit.Translator;
import polyglot.visit.TypedTranslator;
import polyglot.main.Options;
import polyglot.types.Type;
/*************************************************************************
	> File Name: UpdateJL5CanonicalTypeNode_c.java
	> Author: Stanley Wang
	> Mail: stanley.chenglongwang@gmail.com 
	> Created Time: Tue 22 Apr 2014 05:47:23 AM PDT
 ************************************************************************/

public class UpdateJL5CanonicalTypeNode_c extends JL5CanonicalTypeNode_c
{
	private String outputName = null;
	
	public UpdateJL5CanonicalTypeNode_c(Position pos, Type type) {
		super(pos, type);
	}

	public void setOutputName(String name) {
		outputName = name;
	}

	@Override
	public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
		String outputType = type.translate(null);
	}

	public String getOutputString() {
		return type.translate(null);
	}

	@Override
	public void translate(CodeWriter w, Translator tr) {
		if(Options.global.fully_qualified_names || ! (tr instanceof TypedTranslator)) {
			String outputType = type.translate(null);
			if(outputName != null)
				outputType = outputName;
			w.write(outputType);
		} else {
			String outputType = type.translate(((TypedTranslator) tr).context());
			if(outputName != null)
				outputType = outputName;
			w.write(outputType);
		}
	}
}
