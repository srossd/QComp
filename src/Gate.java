import java.util.ArrayList;

public class Gate {
	protected int size = 1;
	protected ArrayList<SparseOperator> ops;
	
	public Gate() {
		ops = new ArrayList<SparseOperator>();
	}
	
	public void addOperator(SparseOperator U) {
		size *= U.size();
		ops.add(U);
	}
	
	public Bra apply(Bra b) {
		if(Math.pow(2,b.numQubits()) != size)
			throw new RuntimeException("Gate and bra size do not match.");
		SparseOperator U = new SparseOperator(1,new Complex[][]{{new Complex(1,0)}});
		for(SparseOperator x : ops)
			U = (SparseOperator)U.tensor(x);
		return b.operate(U);
	}
}
	