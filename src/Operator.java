public interface Operator {
	public Complex getVal(int a, int b);
	public Operator dagger();
	public Operator plus(Operator that);
	public Operator minus(Operator that);
	public Operator times(Complex that);
	public Operator times(Operator that);
	public Operator tensor(Operator that);
	public boolean isIdentity();
	public boolean isUnitary();
	public int size();
	public boolean isReal();
}