package polyglot.ext.jl5.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import polyglot.ast.AmbExpr;
import polyglot.ast.Case;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ext.jl.ast.Switch_c;
import polyglot.ext.jl5.types.EnumInstance;
import polyglot.ext.jl5.types.JL5Context;
import polyglot.ext.jl5.types.JL5Flags;
import polyglot.ext.jl5.types.JL5ParsedClassType;
import polyglot.ext.jl5.types.JL5TypeSystem;
import polyglot.frontend.Job;
import polyglot.frontend.Pass;
import polyglot.types.FieldInstance;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;

public class JL5Switch_c extends Switch_c implements JL5Switch  {

    public JL5Switch_c(Position pos, Expr expr, List elements){
        super(pos, expr, elements);
    }

    public NodeVisitor disambiguateEnter(AmbiguityRemover ar) throws SemanticException {
        return ar.bypass(elements);
    }

    public NodeVisitor typeCheckEnter(TypeChecker tc) throws SemanticException {
        return tc.bypass(elements);
    }

    public Node typeCheck(TypeChecker tc) throws SemanticException {
        JL5TypeSystem ts = (JL5TypeSystem)tc.typeSystem();
        JL5Context context = (JL5Context)tc.context();
        JL5NodeFactory nf = (JL5NodeFactory)tc.nodeFactory();
        
        JL5Switch_c sw = this;
        if (sw.expr().type() instanceof JL5ParsedClassType && JL5Flags.isEnumModifier(((JL5ParsedClassType)sw.expr().type()).flags())){
            List elems = new ArrayList(sw.elements.size());
            for (Iterator it = sw.elements().iterator(); it.hasNext(); ){
                Node swElem = (Node)it.next();
                if (swElem instanceof Case && ((Case)swElem).expr() instanceof AmbExpr){
                    AmbExpr amb = (AmbExpr)((Case)swElem).expr();
                    FieldInstance fi = ts.findFieldOrEnum(sw.expr().type().toReference(), amb.name(), context.currentClass());
                    if (fi instanceof EnumInstance){
                    
                        JL5Field caseField = nf.JL5Field(swElem.position(), nf.CanonicalTypeNode(sw.expr().type().position(), sw.expr().type()), amb.name());
                        caseField = (JL5Field)caseField.fieldInstance(fi);
                        caseField = (JL5Field)caseField.targetImplicit(true);
                        swElem = ((Case)swElem).expr(caseField);
                        elems.add(swElem);
                    }
                    else {
                        throw new SemanticException("Case label: "+swElem+" not allowed here, only enum constants are allowed as case labels in a switch statement with an expression of an enum type", swElem.position());
                    }
                }
                else if (swElem instanceof Case && ((Case)swElem).expr() == null){
                    elems.add(swElem);
                }
                else if (swElem instanceof Case){
                    throw new SemanticException("Unexpected case label: "+swElem+", only unqualified enum constanst case labels are allowed in a switch statement with an expression of an enum type", swElem.position());
                }
                else {
                    Job sj = tc.job().spawn(tc.context(), swElem, Pass.CLEAN_SUPER, Pass.DISAM_ALL);
                    if (!sj.status()){
                        if (!sj.reportedErrors()){
                            throw new SemanticException("Could not type check switch element: "+swElem, swElem.position());
                        }
                        throw new SemanticException();
                    }
                    elems.add(sj.ast());
                }
            }        
            sw = (JL5Switch_c)sw.elements(elems);
            
            // now do type checking
            elems = new ArrayList(sw.elements.size());
            for (Iterator it = sw.elements().iterator(); it.hasNext(); ){
                Node swElem = (Node)it.next();
                Job sj = tc.job().spawn(tc.context(), swElem, Pass.TYPE_CHECK, Pass.TYPE_CHECK);
                if (!sj.status()){
                    if (!sj.reportedErrors()){
                        throw new SemanticException("Could not type check switch element: "+swElem, swElem.position());
                    }
                    throw new SemanticException();
                }
                elems.add(sj.ast());
            }
            
            sw = (JL5Switch_c)sw.elements(elems);

            
            return sw;
            
        }
        else {
            List elems = new ArrayList(sw.elements.size());
            for (Iterator it = sw.elements().iterator(); it.hasNext(); ){
                Node swElem = (Node)it.next();
                Job sj = tc.job().spawn(tc.context(), swElem, Pass.CLEAN_SUPER, Pass.DISAM_ALL);
                if (!sj.status()){
                    if (!sj.reportedErrors()){
                        throw new SemanticException("Could not disambigute switch element: "+swElem, swElem.position());
                    }
                    throw new SemanticException();
                }
                elems.add(sj.ast());
            }
            
            sw = (JL5Switch_c)sw.elements(elems);
            
            elems = new ArrayList(sw.elements.size());
            for (Iterator it = sw.elements().iterator(); it.hasNext(); ){
                Node swElem = (Node)it.next();
                Job sj = tc.job().spawn(tc.context(), swElem, Pass.TYPE_CHECK, Pass.TYPE_CHECK);
                if (!sj.status()){
                    if (!sj.reportedErrors()){
                        throw new SemanticException("Could not type check switch element: "+swElem, swElem.position());
                    }
                    throw new SemanticException();
                }
                elems.add(sj.ast());
            }
            
            sw = (JL5Switch_c)sw.elements(elems);

            
            return sw;
        }
    }
    
}
