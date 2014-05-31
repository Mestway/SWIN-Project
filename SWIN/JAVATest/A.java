import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.util.ArrayList;

public class A {
	static void print(Object... x) {
		for (Object e : x)
			System.out.println(e + " ");
	}
	public static void main(String[] args) {
		Hashtable t = new Hashtable();
		for (int i = 0; i < 10; i ++) {
			t.put(i,i);
		}
		Enumeration e = t.elements();
		while (e.hasMoreElements())
			print(e.nextElement());
		Vector v = new Vector();
		Enumeration ve = v.elements();
		while (ve.hasMoreElements())
			print(ve.nextElement());
	
		print((new String("diudiu")).toString());
	}
}

