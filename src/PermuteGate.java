import java.util.Arrays;
import java.util.HashMap;

public class PermuteGate extends Gate {
	private HashMap<Integer,Integer> map;
	public PermuteGate(int numQubits) {
		this.size = numQubits;
		map = new HashMap<Integer,Integer>(numQubits);
	}
	public PermuteGate(int numQubits, HashMap<Integer,Integer> map) {
		this.map = map;
		this.size = numQubits;
	}
	public void addOperator(Operator U) {
		throw new RuntimeException("Cannot add operator to permutation gate.");
	}
	public void connect(Integer a, Integer b) {
		map.put(a,b);
	}
	public Bra apply(Bra b) {
		Bra newBra = new Bra(b.numQubits(),b.getCoeffs());
		for(int i = 0; i < b.getCoeffs().size(); i++) {
			StringBuilder bin = new StringBuilder(Integer.toBinaryString(i));
			while(bin.length() < size)
				bin.insert(0,'0');
			char[] chars = bin.toString().toCharArray();
			char[] chars2 = Arrays.copyOf(chars,chars.length);
			for(Integer key : map.keySet())
				chars2[map.get(key)] = chars[key];
			String bin2 = new String(chars2);
			newBra = newBra.setCoeff(bin2,b.getCoeff(i));
		}
		return newBra;
	}
	public PermuteGate reverse() {
		HashMap<Integer,Integer> map2 = new HashMap<Integer,Integer>(size);
		for(Integer x : map.keySet())
			map2.put(map.get(x),x);
		return new PermuteGate(size,map2);
	}
}