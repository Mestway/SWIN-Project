import java.util.Enumeration;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Iterator;

public class Test3 {
    public static void main(java.lang.String[] args) {
        ArrayList<ArrayList<java.lang.Integer>> arrayVector = new ArrayList<ArrayList<java.lang.Integer>>();
        for (int j = 0;j < 3;j++) {
            ArrayList<java.lang.Integer> array = new ArrayList<java.lang.Integer>();
            for (int i = 0;i < 10;i++) { array.add(new java.lang.Integer(i)); }
            arrayVector.add(array);
        }
        Iterator<ArrayList<java.lang.Integer>> it = arrayVector.iterator();
        while (it.hasNext()) {
            Iterator<java.lang.Integer> iit = it.next().iterator();
            while (iit.hasNext()) { java.lang.System.out.println(iit.next().toString()); }
        }
    }
    
    public Test3() { super(); }
}
