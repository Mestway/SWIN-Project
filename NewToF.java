class P {
    private P() { super(); }
    
    static P newInstance() { return new P(); }
    
    P.Q1 newQ1() { return this.new Q1(); }
    
    class Q1 {
        void f() {  }
        
        public Q1() { super(); }
    }
    
}

class Q {
    void f() {  }
    
    public Q() { super(); }
}

public class NewToF {
    static void print(java.lang.Object ... x) { for (java.lang.Object e : x) java.lang.System.out.println(e + " "); }
    
    public static void main(java.lang.String[] args) { Q x = new Q(); }
    
    public NewToF() { super(); }
}
