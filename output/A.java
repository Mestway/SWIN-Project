import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.util.ArrayList;

public class A {
    static void print(java.lang.Object ... x) { for (Object e : x) java.lang.System.out.println(e + " "); }
    
    public static void main(java.lang.String[] args) {
        HashMap t = new HashMap();
        for (int i = 0;i < 10;i++) { t.put(i, i); }
        Iterator e = t.values().iterator();
        while (e.hasNext()) print(e.next());
        ArrayList v = new ArrayList();
        Iterator ve = v.iterator();
        while (ve.hasNext()) print(ve.next());
    }
    
    public A() { super(); }
}
