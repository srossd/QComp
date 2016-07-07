import java.util.ArrayList;

public class Qubit {
	private Bra qubit;
	
	public Qubit() {
		ArrayList<Complex> coeffs = new ArrayList<Complex>();
		coeffs.add(new Complex(1,0));
		coeffs.add(new Complex(0,0));
		qubit = new Bra(1,coeffs);
	}
	public Qubit(Complex a, Complex b) {
		ArrayList<Complex> coeffs = new ArrayList<Complex>();
		coeffs.add(a);
		coeffs.add(b);
		qubit = new Bra(1,coeffs);
	}
	public Qubit(Bra b) {
		this.qubit = b;
	}
	public Bra toBra() {
		return qubit;
	}
	public Complex getA() {
		return qubit.getCoeff(0);
	}
	public Complex getB() {
		return qubit.getCoeff(1);
	}
	public Qubit operate(DenseOperator U) {
		return new Qubit(qubit.operate(U));
	}
	public Qubit operate(SparseOperator U) {
		return new Qubit(qubit.operate(U));
	}
	public Qubit timeStep(DenseOperator U, double t) {
		return new Qubit(qubit.timeStep(U,t));
	}
}