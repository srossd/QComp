public class Globals {
	public static final double precision = 0.0001;
	public static final Qubit plus = new Qubit(
		new Bra(1)
		.setCoeff(0,new Complex(Math.sqrt(0.5),0))
		.setCoeff(1,new Complex(Math.sqrt(0.5),0))
	);
	public static final Qubit minus = new Qubit(
		new Bra(1)
		.setCoeff(0,new Complex(Math.sqrt(0.5),0))
		.setCoeff(1,new Complex(-Math.sqrt(0.5),0))
	);
	public static final Qubit zero = new Qubit(
		new Bra(1)
		.setCoeff(0,new Complex(1,0))
		.setCoeff(1,new Complex(0,0))
	);
	public static final Qubit one = new Qubit(
		new Bra(1)
		.setCoeff(0,new Complex(0,0))
		.setCoeff(1,new Complex(1,0))
	);
	public static final SparseOperator I = new SparseOperator(2, new Complex[][]{
		{new Complex(1,0),new Complex(0,0)},
		{new Complex(0,0),new Complex(1,0)}}
	);
	public static final SparseOperator X = new SparseOperator(2, new Complex[][]{
		{new Complex(0,0),new Complex(1,0)},
		{new Complex(1,0),new Complex(0,0)}}
	);
	public static final SparseOperator Y = new SparseOperator(2, new Complex[][]{
		{new Complex(0,0),new Complex(0,-1)},
		{new Complex(0,1),new Complex(0,0)}}
	);
	public static final SparseOperator Z = new SparseOperator(2, new Complex[][]{
		{new Complex(1,0),new Complex(0,0)},
		{new Complex(0,0),new Complex(-1,0)}}
	);
	public static final SparseOperator H = new SparseOperator(2, new Complex[][]{
		{new Complex(Math.sqrt(0.5),0),new Complex(Math.sqrt(0.5),0)},
		{new Complex(Math.sqrt(0.5),0),new Complex(-Math.sqrt(0.5),0)}}
	);
	public static final SparseOperator CNOT = new SparseOperator(4, new Complex[][]{
		{new Complex(1,0),new Complex(0,0),new Complex(0,0),new Complex(0,0)},
		{new Complex(0,0),new Complex(1,0),new Complex(0,0),new Complex(0,0)},
		{new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(1,0)},
		{new Complex(0,0),new Complex(0,0),new Complex(1,0),new Complex(0,0)}}
	);	
	public static final SparseOperator SWAP = new SparseOperator(4, new Complex[][]{
		{new Complex(1,0),new Complex(0,0),new Complex(0,0),new Complex(0,0)},
		{new Complex(0,0),new Complex(0,0),new Complex(1,0),new Complex(0,0)},
		{new Complex(0,0),new Complex(1,0),new Complex(0,0),new Complex(0,0)},
		{new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(1,0)}}
	);
	public static final SparseOperator CSWAP = new SparseOperator(8, new Complex[][]{
		{new Complex(1,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0)},
		{new Complex(0,0),new Complex(1,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0)},
		{new Complex(0,0),new Complex(0,0),new Complex(1,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0)},
		{new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(1,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0)},
		{new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(1,0),new Complex(0,0),new Complex(0,0),new Complex(0,0)},
		{new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(1,0),new Complex(0,0)},
		{new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(1,0),new Complex(0,0),new Complex(0,0)},
		{new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(1,0)}}
	);
	public static final SparseOperator CCNOT = new SparseOperator(8, new Complex[][]{
		{new Complex(1,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0)},
		{new Complex(0,0),new Complex(1,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0)},
		{new Complex(0,0),new Complex(0,0),new Complex(1,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0)},
		{new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(1,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0)},
		{new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(1,0),new Complex(0,0),new Complex(0,0),new Complex(0,0)},
		{new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(1,0),new Complex(0,0),new Complex(0,0)},
		{new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(1,0)},
		{new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(0,0),new Complex(1,0),new Complex(0,0)}}
	);
	public static final SparseOperator I(int n) {
		if(n == 1)
			return I;
		else
			return (SparseOperator)I.tensor(I(n-1));
	}
	public static final SparseOperator H(int n) {
		if(n == 1)
			return H;
		else
			return (SparseOperator)H.tensor(H(n-1));
	}
	public static final SparseOperator X(int n) {
		if(n == 1)
			return X;
		else
			return (SparseOperator)X.tensor(X(n-1));
	}
	public static final SparseOperator Z(int n) {
		if(n == 1)
			return Z;
		else
			return (SparseOperator)Z.tensor(Z(n-1));
	}
	
}