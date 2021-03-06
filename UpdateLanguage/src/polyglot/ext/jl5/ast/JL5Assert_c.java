package polyglot.ext.jl5.ast;

import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ext.jl.ast.Assert_c;
import polyglot.ext.jl5.types.JL5TypeSystem;
import polyglot.main.Options;
import polyglot.types.SemanticException;
import polyglot.util.ErrorInfo;
import polyglot.util.ErrorQueue;
import polyglot.util.Position;
import polyglot.visit.TypeChecker;

public class JL5Assert_c extends Assert_c implements JL5Assert  {

    public JL5Assert_c(Position pos, Expr cond, Expr errorMsg){
        super(pos, cond, errorMsg);
    }

    public Node typeCheck(TypeChecker tc) throws SemanticException {
        JL5TypeSystem ts = (JL5TypeSystem)tc.typeSystem();
        
        if (! Options.global.assertions) {
            ErrorQueue eq = tc.errorQueue();
            eq.enqueue(ErrorInfo.WARNING,
                       "assert statements are disabled. Recompile " +
                       "with -assert and ensure the post compiler supports " +
                       "assert (e.g., -post \"javac -source 1.4\"). " +
                       "Removing the statement and continuing.",
                       cond.position());
        }
        
        if (!ts.equals(cond.type(), ts.Boolean()) && !ts.equals(cond.type(), ts.BooleanWrapper())){
            throw new SemanticException("Condition of assert statement must have boolean type.", cond.position());
        }
        
        if (errorMessage != null && ts.equals(errorMessage.type(), ts.Void())) {
            throw new SemanticException("Error message in assert statement " +
                                        "must have a value.",
                                        errorMessage.position());
        }

        return this;
    }
}
