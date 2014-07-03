package polyglot.ext.jl5.ast;

import polyglot.ast.ClassMember;
import polyglot.ast.Expr;
import polyglot.ast.TypeNode;
import polyglot.ext.jl5.types.AnnotationElemInstance;
import polyglot.types.Flags;

public interface AnnotationElemDecl extends ClassMember {

    AnnotationElemDecl type(TypeNode type);
    TypeNode type();
    
    AnnotationElemDecl flags(Flags flags);
    Flags flags();

    AnnotationElemDecl defaultVal(Expr def);
    Expr defaultVal();

    AnnotationElemDecl name(String name);
    String name();

    AnnotationElemDecl annotationElemInstance(AnnotationElemInstance ai);
    AnnotationElemInstance annotationElemInstance();
}
