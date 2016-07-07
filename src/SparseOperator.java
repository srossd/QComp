import java.util.Hashtable;

public class SparseOperator implements Operator {
	private int n;
	private Hashtable<Pair,Complex> matrix;
	
	public SparseOperator(int n) {
		this.n = n;
		matrix = new Hashtable<Pair,Complex>();
	}
	public SparseOperator(int n, Complex[][] matrix) {
		this.matrix = new Hashtable<Pair,Complex>();
		this.n = n;
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++)
				if(matrix[i][j].abs() > 0)
					this.matrix.put(new Pair(i,j),matrix[i][j]);
	}
	public SparseOperator(int n, Hashtable<Pair,Complex> matrix) {
		this.n = n;
		this.matrix = matrix;
	}
	public Complex getVal(int a, int b) {
		return matrix.get(new Pair(a,b));
	}
	public Hashtable<Pair,Complex> getMatrix() {
		return matrix;
	}
	public Operator dagger() {
		Hashtable<Pair,Complex> dag = new Hashtable<Pair,Complex>();
		for(Pair p : matrix.keySet())
			dag.put(new Pair(p.b,p.a),matrix.get(p).conjugate());
		return new SparseOperator(n,dag);
	}
	public Operator plus(Operator that) {
		if(!(that instanceof SparseOperator))
			throw new RuntimeException("Cannot add sparse and dense operators.");
		Hashtable<Pair,Complex> sum = new Hashtable<Pair,Complex>();
		for(Pair p : matrix.keySet())
			sum.put(p,matrix.get(p));
		for(Pair p : ((SparseOperator)that).getMatrix().keySet()) {
			if(!sum.containsKey(p))
				sum.put(p,that.getVal(p.a,p.b));
			else if(sum.get(p).plus(that.getVal(p.a,p.b)).abs() != 0)
				sum.put(p,sum.get(p).plus(that.getVal(p.a,p.b)));
			else
				sum.remove(p);
		}
		return new SparseOperator(n,sum);
	}
	
	public Operator minus(Operator that) {
		return plus(that.times(new Complex(-1,0)));
	}
	
	public Operator times(Complex that) {
		Hashtable<Pair,Complex> prod = new Hashtable<Pair,Complex>();
		for(Pair p : matrix.keySet())
			prod.put(p,matrix.get(p).times(that));
		return new SparseOperator(n,prod);
	}
	public Operator times(Operator that) {
		if(!(that instanceof SparseOperator))
			throw new RuntimeException("Cannot multiply sparse and dense operators.");
		Hashtable<Pair,Complex> prod = new Hashtable<Pair,Complex>();
		for(Pair p : matrix.keySet()) {
			for(int i = 0; i < n; i++) {
				if(((SparseOperator)that).getMatrix().containsKey(new Pair(p.b,i))) {
					if(prod.containsKey(new Pair(p.a,i)))
						prod.put(new Pair(p.a,i),prod.get(new Pair(p.a,i)).plus(matrix.get(p).times(that.getVal(p.b,i))));
					else
						prod.put(new Pair(p.a,i),matrix.get(p).times(that.getVal(p.b,i)));
				}
			}
		}
				
		return new SparseOperator(n,prod);
	}
	public Operator tensor(Operator that) {
		if(!(that instanceof SparseOperator))
			throw new RuntimeException("Cannot tensor sparse and dense operators.");
		Hashtable<Pair,Complex> newMat = new Hashtable<Pair,Complex>();
		for(Pair p : matrix.keySet())
			for(Pair q : ((SparseOperator)that).getMatrix().keySet()) {
				Complex c = getVal(p.a,p.b);
				newMat.put(new Pair(p.a*that.size()+q.a,p.b*that.size()+q.b),getMatrix().get(p).times(((SparseOperator)that).getMatrix().get(q)));
			}
		return new SparseOperator(n*that.size(),newMat);
	}
	public boolean isIdentity() {
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(i == j && (!matrix.containsKey(new Pair(i,j)) || !matrix.get(new Pair(i,j)).equals(new Complex(1,0))))
					return false;
			}
		}
		return true;
	}
	public boolean isUnitary() {
		return times(dagger()).isIdentity();
	}
	
	public int size() {
		return n;
	}
	public boolean isReal() {
		for(Pair p : matrix.keySet())
				if(Math.abs(matrix.get(p).im()) > Globals.precision)
					return false;
		return true;
	}
}