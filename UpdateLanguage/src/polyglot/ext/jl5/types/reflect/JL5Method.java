package polyglot.ext.jl5.types.reflect;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import polyglot.ext.jl5.types.JL5ArrayType;
import polyglot.ext.jl5.types.JL5ConstructorInstance;
import polyglot.ext.jl5.types.JL5MethodInstance;
import polyglot.ext.jl5.types.JL5ParsedClassType;
import polyglot.ext.jl5.types.JL5TypeSystem;
import polyglot.ext.jl5.types.ParameterizedType;
import polyglot.types.ArrayType;
import polyglot.types.ClassType;
import polyglot.types.ConstructorInstance;
import polyglot.types.MethodInstance;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.types.reflect.Attribute;
import polyglot.types.reflect.ClassFile;
import polyglot.types.reflect.Constant;
import polyglot.types.reflect.Exceptions;
import polyglot.types.reflect.Field;
import polyglot.types.reflect.Method;
public class JL5Method extends Method{

    protected boolean defaultVal;
    protected Signature signature;
    
    public JL5Method(DataInputStream in, ClassFile clazz) {
        super(in, clazz);
    }


    public void initialize() throws IOException {
        modifiers = in.readUnsignedShort();

        name = in.readUnsignedShort();
        type = in.readUnsignedShort();
                
        int numAttributes = in.readUnsignedShort();
                     
        attrs = new Attribute[numAttributes];
        for (int i = 0; i < numAttributes; i++) {
            int nameIndex = in.readUnsignedShort();
            int length = in.readInt();
            Constant name = clazz.constants()[nameIndex];

            if (name != null){
                if ("Exceptions".equals(name.value())) {
                    exceptions = new Exceptions(clazz, in, nameIndex, length);
                    attrs[i] = exceptions;
                }
                if ("Synthetic".equals(name.value())) {
                    synthetic = true;
                }
                if ("AnnotationDefault".equals(name.value())){
                    defaultVal = true;
                }
                
                if("Signature".equals(name.value())){
                    signature = new Signature(in, nameIndex, length, clazz);
                    attrs[i] = signature;
                }
            }

            if (attrs[i] == null){
                long n = in.skip(length);
                if (n != length){
                    throw new EOFException();
                }
            }
        }
                                                  
    }
    
    public boolean hasDefaultVal(){
        return defaultVal;
    }

	// turn bare occurrences of generic types into raw types
    private static JL5MethodInstance rawifyBareGenerics(JL5MethodInstance mi, JL5TypeSystem ts) {
	mi = (JL5MethodInstance) mi.returnType(ts.rawifyBareGenericType(mi.returnType()));
	mi = mi.formalTypes(ts.rawifyBareGenericTypeList(mi.formalTypes()));
	mi = mi.throwTypes(ts.rawifyBareGenericTypeList(mi.throwTypes()));
	    // FIXME:  Should do this as well, but it's causing null pointer exceptions.
//	mi = (JL5MethodInstance) mi.typeArguments(ts.rawifyBareGenericTypeList(mi.typeArguments()));
	return mi;
    }

    private static JL5ConstructorInstance rawifyBareGenerics(JL5ConstructorInstance ci, JL5TypeSystem ts) {
	ci = ci.formalTypes(ts.rawifyBareGenericTypeList(ci.formalTypes()));
	ci = ci.throwTypes(ts.rawifyBareGenericTypeList(ci.throwTypes()));
	return ci;
    }

    public MethodInstance methodInstance(TypeSystem ts, ClassType ct){
        JL5MethodInstance mi = (JL5MethodInstance)super.methodInstance(ts, ct);
        if (signature != null){
            try {
                signature.parseMethodSignature(ts, mi.position(), ct);
            }
            catch(IOException e){
            }
            catch(SemanticException e){
            }

            mi.typeVariables(signature.methodSignature.typeVars());
            mi = (JL5MethodInstance)mi.returnType(signature.methodSignature.returnType());
            mi = (JL5MethodInstance)mi.formalTypes(signature.methodSignature.formalTypes());
		// It seems that the signature doesn't contain the thrown exceptions, so we should
		// rely on the super call above to handle that.
//            mi = (JL5MethodInstance)mi.throwTypes(signature.methodSignature.throwTypes());
        }
        if (mi.flags().isTransient()){
            ArrayList newFormals = new ArrayList();
            for (Iterator it = mi.formalTypes().iterator(); it.hasNext(); ){
                Type t = (Type)it.next();
                if (!it.hasNext()){
                    ArrayType at = ((JL5TypeSystem)ts).arrayType(t.position(), ((ArrayType)t).base());
                    ((JL5ArrayType)at).setVariable();
                    newFormals.add(at);
                } 
                else{
                    newFormals.add(t);
                }
            }
            mi = (JL5MethodInstance)mi.formalTypes(newFormals);
        }
        return rawifyBareGenerics(mi, (JL5TypeSystem) ts);
    }

    public ConstructorInstance constructorInstance(TypeSystem ts, ClassType ct, Field[] fields){
        JL5ConstructorInstance ci = (JL5ConstructorInstance)super.constructorInstance(ts, ct, fields);
        if (signature != null){
            try {
                signature.parseMethodSignature(ts, ci.position(), ct);
            }
            catch(IOException e){
            }
            catch(SemanticException e){
            }

            ci.typeVariables(signature.methodSignature.typeVars());
            ci = (JL5ConstructorInstance)ci.formalTypes(signature.methodSignature.formalTypes());
		// It seems that the signature doesn't contain the thrown exceptions, so we should
		// rely on the super call above to handle that.	    
		// ci = (JL5ConstructorInstance)ci.throwTypes(signature.methodSignature.throwTypes());
	    
        }
        if (ci.flags().isTransient()){
            ArrayList newFormals = new ArrayList();
            for (Iterator it = ci.formalTypes().iterator(); it.hasNext(); ){
                Type t = (Type)it.next();
                if (!it.hasNext()){
                    ArrayType at = ((JL5TypeSystem)ts).arrayType(t.position(), ((ArrayType)t).base());
                    ((JL5ArrayType)at).setVariable();
                    newFormals.add(at);
                } 
                else{
                    newFormals.add(t);
                }
            }
            ci = (JL5ConstructorInstance)ci.formalTypes(newFormals);
        }
        return rawifyBareGenerics(ci, (JL5TypeSystem) ts);
    }
}

