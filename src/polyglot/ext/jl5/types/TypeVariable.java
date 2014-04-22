package polyglot.ext.jl5.types;

import java.util.List;

import polyglot.types.ClassType;
import polyglot.types.ReferenceType;
import polyglot.types.Type;
import polyglot.types.TypeObject;

public interface TypeVariable extends ClassType {

    public static final Kind TYPEVARIABLE = new Kind("type_variable");
    
    public static enum TVarDecl { CLASSTV, PROCEDURETV, SYNTHETICTV }
   
    TVarDecl declaredIn();
    void declaringProcedure(JL5ProcedureInstance pi);
    void declaringClass(ClassType ct);
    ClassType declaringClass();
    JL5ProcedureInstance declaringProcedure();
    
    List <ReferenceType>bounds();
    void bounds(List<ReferenceType> l);

    void name(String name);

    boolean isEquivalent(TypeObject arg2);

    boolean equivalentImpl(TypeObject arg2);

    Type erasureType();

    IntersectionType upperBound();
    void upperBound(IntersectionType b);
    
    
    
    /**
     * lower bound can only occur in the process of capture conversion
     * @return
     */
    Type lowerBound();
    void lowerBound(Type lowerBound); 
}
