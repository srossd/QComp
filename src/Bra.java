import java.util.ArrayList;
import Jama.Matrix;
import Jama.EigenvalueDecomposition;

public class Bra extends BraKet {
	public Bra(int numQubits) {
		super(numQubits);
	}
	public Bra(int numQubits,ArrayList<Complex> coeffs) {
		super(numQubits,coeffs);
	}
	public Bra setCoeff(int n, Complex c) {
		assert(n < coeffs.size());
		ArrayList coeffs2 = (ArrayList)coeffs.clone();
		coeffs2.set(n,c);
		return new Bra(numQubits,coeffs2);
	}	
	public Bra setCoeff(String bin, Complex c) {
		assert(bin.length() == numQubits);
		int n = (int)Math.pow(2,numQubits-1), s = 0;
		for(int i = 0; i < bin.length(); i++) {
			s += (bin.charAt(i) == '1' ? n : 0);
			n /= 2;
		}
		ArrayList coeffs2 = (ArrayList)coeffs.clone();
		coeffs2.set(s,c);
		return new Bra(numQubits,coeffs2);
	}
	public Ket toKet() {
		ArrayList<Complex> coeffs2 = new ArrayList<Complex>();
		for(int i = 0; i < coeffs.size(); i++)
			coeffs2.add(coeffs.get(i).conjugate());
		return new Ket(numQubits,coeffs2);
	}
	public Operator product(Ket that) {
		if(that.numQubits() != this.numQubits())
			throw new RuntimeException("Cannot take inner product of different sized bras and kets.");
		else {
			Complex[][] matrix = new Complex[coeffs.size()][coeffs.size()];
			for(int i = 0; i < coeffs.size(); i++)
				for(int j = 0; j < coeffs.size(); j++)
					matrix[i][j] = coeffs.get(i).times(that.getCoeff(j));
			return new DenseOperator(coeffs.size(),matrix);
		}
	}
	public void normalize() {
		Complex Z = toKet().innerProduct(this).sqrt();
		for(int i = 0; i < coeffs.size(); i++)
			coeffs.set(i,coeffs.get(i).divides(Z));
	}
	public boolean isNormalized() {
		return Math.abs(toKet().innerProduct(this).abs()-1) < Globals.precision;
	}
	public Bra operate(DenseOperator U) {
		if(U.size() != Math.pow(2,numQubits))
			throw new RuntimeException("Operator size does not match vector size.");
		if(!U.isUnitary())
			throw new RuntimeException("Cannot use non-unitary operator.");
		ArrayList<Complex> coeffs2 = new ArrayList<Complex>();
		for(Complex[] row : U.getMatrix()) {
			Complex c = new Complex(0,0);
			for(int i = 0; i < U.size(); i++)
				c = c.plus(row[i].times(coeffs.get(i)));
			coeffs2.add(c);
		}
		return new Bra(numQubits,coeffs2);
	}
	public Bra operate(SparseOperator U) {
		if(U.size() != Math.pow(2,numQubits))
			throw new RuntimeException("Operator size does not match vector size.");
		if(!U.isUnitary())
			throw new RuntimeException("Cannot use non-unitary operator.");
		ArrayList<Complex> coeffs2 = new ArrayList<Complex>();
		for(int i = 0; i < Math.pow(2,numQubits); i++)
			coeffs2.add(new Complex(0,0));
		for(Pair p : U.getMatrix().keySet())
			coeffs2.set(p.b,coeffs2.get(p.b).plus(coeffs.get(p.a)));
		return new Bra(numQubits,coeffs2);
	}
	public Bra plus(Bra that) {
		ArrayList<Complex> coeffs2 = new ArrayList<Complex>();
		for(int i = 0; i < coeffs.size(); i++)
			coeffs2.add(this.getCoeff(i).plus(that.getCoeff(i)));
		return new Bra(numQubits,coeffs2);
	}
	public Bra times(Complex c) {
		ArrayList<Complex> coeffs2 = new ArrayList<Complex>();
		for(int i = 0; i < coeffs.size(); i++)
			coeffs2.add(this.getCoeff(i).times(c));
		return new Bra(numQubits,coeffs2);
	}
	public boolean measureQubit(int i) {
		if(i >= numQubits)
			throw new RuntimeException("Qubit index "+i+" out of bounds.");
		double prob = 0;
		for(int j = 0; j < coeffs.size(); j++)
			if((i-(numQubits-Integer.toBinaryString(j).length()) >= 0 ? Integer.toBinaryString(j).charAt(i-(numQubits-Integer.toBinaryString(j).length())) : '0') == '1')
				prob += coeffs.get(j).abs()*coeffs.get(j).abs();
		boolean one = (Math.random() < prob);
		ArrayList<Complex> coeffs2 = new ArrayList<Complex>();
		for(int j = 0; j < coeffs.size(); j++) {
			if((i-(numQubits-Integer.toBinaryString(j).length()) >= 0 ? Integer.toBinaryString(j).charAt(i-(numQubits-Integer.toBinaryString(j).length()))=='1' : false) != one)
				coeffs2.add(new Complex(0,0));
			else
				coeffs2.add(coeffs.get(j));
		}
		coeffs = coeffs2;
		normalize();
		return one;
	}
	public Bra timeStep(DenseOperator U, double t) {
		Matrix Umat = U.getJamaMatrix();
		EigenvalueDecomposition ed = Umat.eig();
		double[] re = ed.getRealEigenvalues();
		double[] im = ed.getImagEigenvalues();
		Complex[] eigenValues = new Complex[re.length];
		for(int i = 0; i < eigenValues.length; i++)
			eigenValues[i] = new Complex(re[i],im[i]);
		Matrix V = ed.getV();
		double[][] eigenVectors = V.getArrayCopy();
		Bra evolved = new Bra(numQubits);
		for(int i = 0; i < coeffs.size(); i++)
			evolved = evolved.setCoeff(i,new Complex(0,0));
		for(int i = 0; i < eigenVectors.length; i++) {
			Complex scale = new Complex(0,-1).times(
			eigenValues[i].times(
			new Complex(t,0))).exp();
			Bra eigen = new Bra(numQubits);
			for(int j = 0; j < eigenVectors[0].length; j++)
				eigen = eigen.setCoeff(j,new Complex(eigenVectors[j][i],0));
			evolved = evolved.plus(eigen.times(scale).times(getCoeff(i)));			
		}
		return evolved;
	}
}