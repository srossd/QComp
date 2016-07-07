import Jama.Matrix;

public class DenseOperator implements Operator {
	private Complex[][] matrix;
	private int n;
	
	public DenseOperator(int n) {
		this.n = n;
		matrix = new Complex[n][n];
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++)
				matrix[i][j] = new Complex(0,0);
	}
	public DenseOperator(int n, Complex[][] matrix) {
		this.n = n;
		this.matrix = matrix;
	}
	public DenseOperator(Complex[] eigenVals, Bra[] eigenStates) {
		DenseOperator U = new DenseOperator(eigenVals.length);
		for(int i = 0; i < eigenVals.length; i++) 
			U = (DenseOperator)U.plus(eigenStates[i].product(eigenStates[i].toKet()).times(eigenVals[i]));
		matrix = U.getMatrix();
		n = matrix.length;
	}
	public Complex getVal(int a, int b) {
		return matrix[a][b];
	}
	public Operator dagger() {
		Complex[][] dag = new Complex[n][n];
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++)
				dag[j][i] = matrix[i][j].conjugate();
		return new DenseOperator(n,dag);
	}
	public Operator plus(Operator that) {
		Complex[][] sum = new Complex[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				sum[i][j] = getVal(i,j).plus(that.getVal(i,j));
			}
		}
		return new DenseOperator(n,sum);
	}
	public Operator minus(Operator that) {
		return plus(that.times(new Complex(-1,0)));
	}
	public Operator times(Complex that) {
		Complex[][] prod = new Complex[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				prod[i][j] = getVal(i,j).times(that);
			}
		}
		return new DenseOperator(n,prod);
	}
	public Operator times(Operator that) {
		Complex[][] prod = new Complex[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				Complex s = new Complex(0,0);
				for(int k = 0; k < n; k++)
					s = s.plus(getVal(i,k).times(that.getVal(k,j)));
				prod[i][j] = s;
			}
		}
		return new DenseOperator(n,prod);
	}
	public Operator tensor(Operator that) {
		Complex[][] newMat = new Complex[n*that.size()][n*that.size()];
		for(int i = 0; i < n*that.size(); i++)
			for(int j = 0; j < n*that.size(); j++)
				newMat[i][j] = matrix[i / that.size()][j / that.size()].times(that.getVal(i % that.size(),j % that.size()));
		return new DenseOperator(n*that.size(),newMat);
	}
	public boolean isIdentity() {
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(i == j && !getVal(i,j).equals(new Complex(1,0)) || i != j && !getVal(i,j).equals(new Complex(0,0)))
					return false;
			}
		}
		return true;
	}
	public boolean isUnitary() {
		return times(dagger()).isIdentity();
	}
	public Complex[][] getMatrix() {
		return matrix;
	}
	public int size() {
		return n;
	}
	public boolean isReal() {
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {	
				if(Math.abs(matrix[i][j].im()) > Globals.precision)
					return false;
			}
		}
		return true;
	}
	public Matrix getJamaMatrix() {
		if(!isReal())
			throw new RuntimeException("Cannot convert complex matrix to JAMA Matrix");
		double[][] realMatrix = new double[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {	
				realMatrix[i][j] = matrix[i][j].re();
			}
		}
		return new Matrix(realMatrix);
	}
}