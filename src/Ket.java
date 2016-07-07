import java.util.ArrayList;

public class Ket extends BraKet {
	public Ket(int numQubits) {
		super(numQubits);
	}
	public Ket(int numQubits,ArrayList<Complex> coeffs) {
		super(numQubits,coeffs);
	}
	public Ket setCoeff(int n, Complex c) {
		assert(n < coeffs.size());
		ArrayList coeffs2 = (ArrayList)coeffs.clone();
		coeffs2.set(n,c);
		return new Ket(numQubits,coeffs2);
	}
	public Ket setCoeff(String bin, Complex c) {
		assert(bin.length() == numQubits);
		int n = (int)Math.pow(2,numQubits-1), s = 0;
		for(int i = 0; i < bin.length(); i++) {
			s += (bin.charAt(i) == '1' ? n : 0);
			n /= 2;
		}
		ArrayList coeffs2 = (ArrayList)coeffs.clone();
		coeffs2.set(n,c);
		return new Ket(numQubits,coeffs2);
	}
	public Bra toBra() {
		ArrayList<Complex> coeffs2 = new ArrayList<Complex>();
		for(int i = 0; i < coeffs.size(); i++)
			coeffs2.add(coeffs.get(i).conjugate());
		return new Bra(numQubits,coeffs2);
	}
	public Complex innerProduct(Bra that) {
		if(that.numQubits() != this.numQubits())
			throw new RuntimeException("Cannot take inner product of different sized bras and kets.");
		else {
			Complex s = new Complex(0,0);
			for(int i = 0; i < coeffs.size(); i++)
				s = s.plus(coeffs.get(i).times(that.getCoeff(i)));
			return s;
		}
	}
	public void normalize() {
		Complex Z = innerProduct(toBra()).pow(0.5);
		for(int i = 0; i < coeffs.size(); i++)
			coeffs.set(i,coeffs.get(i).divides(Z));
	}
	public boolean isNormalized() {
		return Math.abs(innerProduct(toBra()).abs()-1) < Globals.precision;
	}

}