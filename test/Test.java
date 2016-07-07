public class Test {
	public static void main(String[] args) {
		Bra b = new Bra(3);
		b = b.setCoeff("101",new Complex(1,0));
		b.normalize();
		System.out.println(b);
		Bra c = b.operate(Globals.CSWAP);
		System.out.println(c);
		PermuteGate pg = new PermuteGate(3);
		pg.connect(1,2);
		pg.connect(2,1);
		System.out.println(pg.apply(c));
	}
}