class NoP {public NoP() { }};
class P {
    private P() { super(); }
    
    static P newInstance() { return new P(); }
    
    Q1 newQ1() { return this.new Q1(); }
    
    class Q1 {
        void f() {  }
        
        public Q1() { super(); }
    }
    
}

class Q {
    void f() {  }
    
    public Q() { super(); }
}

public class testDummy {
    static void print(java.lang.Object ... x) { for (java.lang.Object e : x) java.lang.System.out.println(e + " "); }
    
    public static void main(java.lang.String[] args) {
        NoP x = new NoP();
        Q y = new Q(); }
    
    public testDummy() { super(); }
}
