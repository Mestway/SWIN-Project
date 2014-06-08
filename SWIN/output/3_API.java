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
