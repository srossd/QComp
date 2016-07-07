import java.util.ArrayList;

public class Circuit {
	private int numQubits;
	private ArrayList<Gate> gates;
	
	public Circuit(int numQubits) {
		this.numQubits = numQubits;
		gates = new ArrayList<Gate>();
	}
	
	public void addGate(Gate G) {
		gates.add(G);
	}
	
	public Bra apply(Bra b) {
		if(gates.size() == 0)
			return new Bra(b.numQubits(),b.getCoeffs());
		Bra b2 = gates.get(0).apply(b);
		for(int i = 1; i < gates.size(); i++)
			b2 = gates.get(i).apply(b2);
		return b2;
	}
}
	