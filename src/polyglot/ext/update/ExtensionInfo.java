package polyglot.ext.update;

import polyglot.lex.Lexer;
import polyglot.ext.update.parse.Lexer_c;
import polyglot.ext.update.parse.Grm;
import polyglot.ext.update.ast.*;
import polyglot.ext.update.types.*;
import polyglot.ext.update.visit.CodeRefactoring;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.visit.*;
import polyglot.frontend.*;
import polyglot.main.*;

import java.util.*;
import java.io.*;

/**
 * Extension information for update extension.
 */
public class ExtensionInfo extends polyglot.ext.jl5.ExtensionInfo {
    static {
        // force Topics to load
        Topics t = new Topics();
    }

    public String defaultFileExtension() {
        return "update";
    }

    public String compilerName() {
        return "updatec";
    }

    public Parser parser(Reader reader, FileSource source, ErrorQueue eq) {
        Lexer lexer = new Lexer_c(reader, source.name(), eq);
        Grm grm = new Grm(lexer, ts, nf, eq);
        return new CupParser(grm, source, eq);
    }

    protected NodeFactory createNodeFactory() {
        return new UpdateNodeFactory_c();
    }

    protected TypeSystem createTypeSystem() {
        return new UpdateTypeSystem_c();
    }

	public static final Pass.ID API_UPDATE = new Pass.ID("api-update");

    public List passes(Job job) {
        List passes = super.passes(job);
		// TODO: add passes as needed by your compiler
		beforePass(passes, Pass.OUTPUT,
					new VisitorPass(Pass.OUTPUT,job, 
					new CodeRefactoring(nf)));
		
		removeCheckings(passes);

		return passes;
    }

	private void removeCheckings(List passes) {
		removePass(passes, Pass.TYPE_CHECK);
		removePass(passes, Pass.REACH_CHECK);
		removePass(passes, Pass.EXC_CHECK);
		removePass(passes, Pass.EXIT_CHECK);
		removePass(passes, Pass.INIT_CHECK);
		removePass(passes, Pass.CONSTRUCTOR_CHECK);
		removePass(passes, GENERIC_TYPE_HANDLER);
		removePass(passes, APPLICATION_CHECK);
	}

}
