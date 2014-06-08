public class Test3 {
	public static void main(String[] args) {
		TestClass temp = new TestClass();
		System.out.println(temp.getSum());
	}
}

class TestClass {
	public int sum = 0;
	
	public TestClass() {
		sum = 0;
	}
	
	public int getSum() {
		return sum;
	}
		
}
