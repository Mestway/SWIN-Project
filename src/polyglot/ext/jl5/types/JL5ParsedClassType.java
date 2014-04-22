package polyglot.ext.jl5.types;

import java.util.List;

import polyglot.types.LazyClassInitializer;
import polyglot.types.ParsedClassType;
import polyglot.types.TypeObject;

/* The type information for a class declaration */

public interface JL5ParsedClassType extends ParsedClassType {
    void addEnumConstant(EnumInstance ei);
    List enumConstants();
    EnumInstance enumConstantNamed(String name);
    
    void addAnnotationElem(AnnotationElemInstance ai);
    List annotationElems();
    AnnotationElemInstance annotationElemNamed(String name);

    void annotations(List annotations);
    List annotations();

    List<TypeVariable> typeVariables();

    void addTypeVariable(TypeVariable type);

    void typeVariables(List<TypeVariable> vars);
    
    boolean hasTypeVariable(String name);
    TypeVariable getTypeVariable(String name);

    boolean isGeneric();

	// find methods with compatible name and formals as the given one
    List methods(JL5MethodInstance mi);

    LazyClassInitializer init();

    boolean equivalentImpl(TypeObject arg2);
    /*List typeArguments();
    void typeArguments(List args);

    boolean isParameterized();*/

}
