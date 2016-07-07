import java.util.ArrayList;

public abstract class BraKet {
	protected ArrayList<Complex> coeffs;
	protected int numQubits;
	
	public BraKet(int numQubits) {
		this.numQubits = numQubits;
		coeffs = new ArrayList<Complex>();
		coeffs.add(new Complex(1,0));
		for(int i = 1; i < Math.pow(2,numQubits); i++)
			coeffs.add(new Complex(0,0));
	}
	public BraKet(int numQubits,ArrayList<Complex> coeffs) {
		assert(Math.pow(2,numQubits) == coeffs.size());
		this.numQubits = numQubits;
		this.coeffs = coeffs;
	}
	public Complex getCoeff(int n) {
		assert(n < coeffs.size());
		return coeffs.get(n);
	}	
	public Complex getCoeff(String bin) {
		assert(bin.length() == numQubits);
		int n = (int)Math.pow(2,numQubits-1), s = 0;
		for(int i = 0; i < bin.length(); i++) {
			s += (bin.charAt(i) == '1' ? n : 0);
			n /= 2;
		}
		return coeffs.get(s);
	}
	public ArrayList<Complex> getCoeffs() {
		return coeffs;
	}
	public int numQubits() {
		return numQubits;
	}
	public abstract BraKet setCoeff(int n, Complex c);
	public abstract void normalize();
	public abstract boolean isNormalized();
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < coeffs.size(); i++) {
			if(coeffs.get(i).abs() > 0) {
				sb.append(coeffs.get(i)+"|");
				for(int j = 0; j < numQubits-Integer.toBinaryString(i).length(); j++)
					sb.append("0");
				sb.append(Integer.toBinaryString(i));
				sb.append(">");
				if(i != coeffs.size()-1) sb.append(" + ");
				if(i % 4 == 3) sb.append("\n");
			}
		}
		return sb.toString();
	}
}
	