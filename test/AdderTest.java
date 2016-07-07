import java.util.Hashtable;
import java.util.Scanner;

public class AdderTest {
	public static void main(String[] args) {
		/*Scanner in = new Scanner(System.in);
		String num1 = in.nextLine();
		String num2 = in.nextLine();*/
		Bra input = new Bra(13);
		//input = input.setCoeff(0,new Complex(0,0)).setCoeff(num1.substring(1)+num2.substring(1)+num1.substring(0,1)+num2.substring(0,1)+"000000000",new Complex(1,0));
		input = input.setCoeff(0,new Complex(0,0)).setCoeff("0110000000000",new Complex(Math.sqrt(0.5),0)).setCoeff("1100000000000",new Complex(Math.sqrt(0.5),0));
		Circuit adder = new Circuit(10);
		System.out.println(adder.apply(input));
		
		PermuteGate moveX1 = new PermuteGate(13);
		moveX1.connect(0,2);
		moveX1.connect(1,4);
		moveX1.connect(2,0);
		moveX1.connect(3,1);
		moveX1.connect(4,3);
		adder.addGate(moveX1);
		
		Gate copyX1 = new Gate();
		copyX1.addOperator(Globals.I(2));
		copyX1.addOperator(Globals.CNOT);
		copyX1.addOperator(Globals.CNOT);
		copyX1.addOperator(Globals.I(7));
		adder.addGate(copyX1);
		adder.addGate(moveX1.reverse());
		
		Gate Y1 = new Gate();
		Y1.addOperator(Globals.I(4));
		Y1.addOperator(Globals.CNOT);
		Y1.addOperator(Globals.I(7));
		adder.addGate(Y1);
		
		PermuteGate moveZero = new PermuteGate(13);
		moveZero.connect(6,2);
		moveZero.connect(2,6);
		adder.addGate(moveZero);
		
		Gate C1 = new Gate();
		C1.addOperator(Globals.CSWAP);
		C1.addOperator(Globals.I(10));
		adder.addGate(C1);
		adder.addGate(moveZero.reverse());
		
		PermuteGate moveX2 = new PermuteGate(13);
		moveX2.connect(4,2);
		moveX2.connect(5,3);
		moveX2.connect(2,4);
		moveX2.connect(7,5);
		moveX2.connect(3,6);
		moveX2.connect(8,7);
		moveX2.connect(6,8);
		adder.addGate(moveX2);
		
		Gate copyX2 = new Gate();
		copyX2.addOperator(Globals.I(4));
		copyX2.addOperator(Globals.CNOT);
		copyX2.addOperator(Globals.CNOT);
		copyX2.addOperator(Globals.CNOT);
		copyX2.addOperator(Globals.I(3));
		adder.addGate(copyX2);
		adder.addGate(moveX2.reverse());
		
		Gate XORX2 = new Gate();
		XORX2.addOperator(Globals.I(2));
		XORX2.addOperator(Globals.CNOT);
		XORX2.addOperator(Globals.I(9));
		adder.addGate(XORX2);
		
		PermuteGate moveXORX2 = new PermuteGate(13);
		moveXORX2.connect(3,9);
		moveXORX2.connect(9,3);
		adder.addGate(moveXORX2);
		
		Gate copyXORX2 = new Gate();
		copyXORX2.addOperator(Globals.I(9));
		copyXORX2.addOperator(Globals.CNOT);
		copyXORX2.addOperator(Globals.I(2));
		adder.addGate(copyXORX2);
		adder.addGate(moveXORX2.reverse());
		
		Gate Y2 = new Gate();
		Y2.addOperator(Globals.I(9));
		Y2.addOperator(Globals.CNOT);
		Y2.addOperator(Globals.I(2));
		adder.addGate(Y2);
		
		PermuteGate setupCANDXOR = new PermuteGate(13);
		setupCANDXOR.connect(6,0);
		setupCANDXOR.connect(0,6);
		setupCANDXOR.connect(3,1);
		setupCANDXOR.connect(1,3);
		setupCANDXOR.connect(11,2);
		setupCANDXOR.connect(2,11);
		adder.addGate(setupCANDXOR);
		
		Gate CANDXOR = new Gate();
		CANDXOR.addOperator(Globals.CSWAP);
		CANDXOR.addOperator(Globals.I(10));
		adder.addGate(CANDXOR);
		adder.addGate(setupCANDXOR.reverse());
		
		PermuteGate setupANDX2 = new PermuteGate(13);
		setupANDX2.connect(12,9);
		setupANDX2.connect(9,12);
		adder.addGate(setupANDX2);
		
		Gate ANDX2 = new Gate();
		ANDX2.addOperator(Globals.I(7));
		ANDX2.addOperator(Globals.CSWAP);
		ANDX2.addOperator(Globals.I(3));
		adder.addGate(ANDX2);
		adder.addGate(setupANDX2.reverse());
		
		Gate Y3 = new Gate();
		Y3.addOperator(Globals.I(11));
		Y3.addOperator(Globals.CNOT);
		adder.addGate(Y3);
		
		PermuteGate getY = new PermuteGate(13);
		getY.connect(5,2);
		getY.connect(2,5);
		getY.connect(10,1);
		getY.connect(1,10);
		getY.connect(12,0);
		getY.connect(0,12);
		adder.addGate(getY);
		
		MeasureGate measureY = new MeasureGate(0,3);
		adder.addGate(measureY);
		System.out.println(adder.apply(input));
		System.out.println(measureY.getResult());
		
	}
}