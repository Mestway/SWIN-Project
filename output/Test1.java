import java.util.ArrayList;

public class Test1 {
    public static void main(String[] args) {
        ArrayList<Integer> array = new ArrayList<Integer>();
        for (int i = 0;i < 10;i++) { array.add(new Integer(i)); }
        System.out.println(array.get(5));
    }
    
    public Test1() { super(); }
}
