package polyglot.ext.jl5.types;

import java.util.List;

import polyglot.types.ClassType;
import polyglot.types.ReferenceType;

public interface IntersectionType extends ClassType {
    List<ReferenceType> bounds();
    
    void boundOf(TypeVariable tv);
    TypeVariable boundOf();
    
    public static final Kind INTERSECTION = new Kind("intersection");
    
}
