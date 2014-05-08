class A1 {
    public A1() { super(); }
}

class A2 {
    void f() {  }
    
    public A2() { super(); }
}

class B1 {
    public B1() { super(); }
}

class B2 {
    void g() {  }
    
    public B2() { super(); }
}

class P {
    B1 h(B2 x, B1 x2) {
        x.g();
        return new B1(); }
    
    public P() { super(); }
}

class Q {
    B1 k(B1 x2, B2 x, B2 y) {
        x.g();
        return new B1(); }
    
    public Q() { super(); }
}

public class testPara {
    public static void main(java.lang.String[] args) {
        Q x = new Q();
        B1 a1 = x.k(new B1(),new B2(),new B2());
        B2 a2 = new B2();
        B1 a111 = new B1();
        a2.g();
        B1 a11 = x.k(a111,a2,new B2());
    }
    
    public testPara() { super(); }
}
