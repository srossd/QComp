import java.awt.Font;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.Timer;

public class Visualize {
	private Visualize() {} //singleton
	public static void clear() {
		StdDraw.clear();
	}
	public static void drawQubit(Qubit q) {
		StdDraw.setScale(-1.2,1.2);
		double a = q.getA().re();
		double b = q.getB().re();
		double len = Math.hypot(a,b);
		if(Math.abs(len-1) < Globals.precision)
			len = 1;
		if(Math.abs(len-0) < Globals.precision)
			len = 0;
		StdDraw.setPenColor();
		StdDraw.circle(0,0,1);
		StdDraw.setPenColor(new Color((float)(1-len),(float)(1-len),(float)(1-len)));
		StdDraw.line(0,0,a,b);
	}
	public static void drawQubit(Qubit q, String s) {
		drawQubit(q);
		StdDraw.setFont(new Font("Trebuchet MS",Font.PLAIN,24));
		double a = q.getA().re();
		double b = q.getB().re();
		StdDraw.setPenColor();
		StdDraw.text(1.1*a,1.1*b,s);
	}
	public static void drawEvolvingQubit(Qubit q, DenseOperator H) {
		Timer t = new Timer(100,new Listener(q,H));
		t.start();
		StdDraw.show(1000);
	}
	private static class Listener implements ActionListener {
		private Qubit q;
		private DenseOperator H;
		private double t = 0;
		public Listener(Qubit q, DenseOperator H) {
			this.q = q;
			this.H = H;
		}
		
		public void actionPerformed(ActionEvent e) {
			StdDraw.clear();
			drawQubit(q.timeStep(H,t));
			t += 0.1;
		}
	}
}