import java.util.ArrayList;

public class Test3 {
    public static void main(String[] args) {
        TestClass temp = new TestClass();
        temp.reset(8);
        System.out.println(temp.getSum());
    }
    
    public Test3() { super(); }
}

class TestClass {
    public ArrayList<Integer> account = new ArrayList<Integer>();
    
    public Integer sum;
    
    public TestClass() {
        super();
        for (int i = 0;i < 10;i++) { account.add(i); } }
    
    public Integer getSum() {
        sum = 0;
        for (Integer i : account) { sum += i; }
        return sum;
    }
    
    public Integer reset(Integer n) {
        for (int i = n;i < n + 10;i++) { account.add(n); }
        return this.getSum(); }
}
