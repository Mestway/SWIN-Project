import java.util.Enumeration;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Iterator;

public class Test2 {
    public static void main(java.lang.String[] args) {
        ArrayList<java.lang.Integer> array = new ArrayList<java.lang.Integer>();
        for (int i = 0;i < 10;i++) { array.add(new java.lang.Integer(i)); }
        Iterator<java.lang.Integer> it = array.elements();
        while (it.hasMoreElements()) { java.lang.System.out.println(it.nextElement().toString()); }
    }
    
    public Test2() { super(); }
}
