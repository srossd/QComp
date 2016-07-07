public class MeasureGate extends Gate {
	private int start;
	private String result;
	public MeasureGate(int start, int size) {
		this.size = size;
		this.start = start;
	}
	public void addOperator(Operator U) {
		throw new RuntimeException("Cannot add operator to measurement gate.");
	}
	public Bra apply(Bra b) {
		String bitStr = "";
		for(int i = start; i < size+start; i++) {
			bitStr += (b.measureQubit(i) ? '1' : '0');
		}
		result = bitStr;
		return b;
	}
	public String getResult() {
		return result;
	}
}
		