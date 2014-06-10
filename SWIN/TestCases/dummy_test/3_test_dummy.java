public class testDummy {
	static void print(Object... x) {
		for (Object e : x)
			System.out.println(e + " ");
	}
	public static void main(String[] args) {
		P x = P.newInstance();
		P.Q1 y = x.newQ1();
	}
}

