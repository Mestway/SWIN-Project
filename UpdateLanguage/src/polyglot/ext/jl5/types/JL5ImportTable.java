package polyglot.ext.jl5.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import polyglot.types.ClassType;
import polyglot.types.ImportTable;
import polyglot.types.Named;
import polyglot.types.Resolver;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.StringUtil;

public class JL5ImportTable extends ImportTable {

    protected ArrayList memberImports;
    protected ArrayList staticClassImports;

    public JL5ImportTable(TypeSystem ts, Resolver base, polyglot.types.Package pkg, String src) {
        super(ts, base, pkg, src);
        this.memberImports = new ArrayList();
        this.staticClassImports = new ArrayList();
    }

    public JL5ImportTable(TypeSystem ts, Resolver base, polyglot.types.Package pkg) {
        this(ts, base, pkg, null);
    }

    public void addMemberImport(String member){
        memberImports.add(member);
    }

    public void addStaticClassImport(String className){
        staticClassImports.add(className);
    }

    public List memberImports(){
        return memberImports;
    }

    public List staticClassImports(){
        return staticClassImports;
    }

    public Named find(String name) throws SemanticException {

        Named result = null;
        // may be member in static import
        for (Iterator it = memberImports.iterator(); it.hasNext(); ){
            String next = (String)it.next();
            String id = StringUtil.getShortNameComponent(next);
            if (name.equals(id)){
                String className = StringUtil.getPackageComponent(next);
                Named nt = ts.forName(className);
                if (nt instanceof Type){
                    Type t = (Type)nt;
                    try {
                        result = ts.findMemberClass(t.toClass(), name);
                    }
                    catch (SemanticException e){
                    }
                    if (result != null && ((ClassType)result).flags().isStatic()) return result;
                }                                    
            }
        }

        for (Iterator it = staticClassImports.iterator(); it.hasNext(); ){
            String next = (String)it.next();
            Named nt = ts.forName(next);
            
            if (nt instanceof Type){
                Type t = (Type)nt;
                try {
                    result = ts.findMemberClass(t.toClass(), name);
                }
                catch(SemanticException e){
                }
                if (result != null && ((ClassType)result).flags().isStatic()) return result;
            }
        }

        return super.find(name);
    }
}
