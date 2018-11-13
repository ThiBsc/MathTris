package generator;

import java.util.Random;

public class EquationGenerator {
	
	private int maxlength;
	private int a, b, result;
	private Operation op;
	
	public EquationGenerator() {
		maxlength = 3;
		a = b = result = 0;
		op = Operation.ADDITION;
	}
	
	public void generate() {
		Random r = new Random();
		// Generate the max value of a or b
		int bound = Integer.parseInt(String.format("%0"+(maxlength-1)+"d", 9).replaceAll("0", "9"));
		a = r.nextInt(bound);
		bound  = Integer.parseInt(String.format("%0"+(maxlength - String.valueOf(a).length())+"d", 9).replaceAll("0", "9"));
		b = r.nextInt(bound);
		int rdOp = r.nextInt(5);
		switch (rdOp) {
		case 0:
			op = Operation.ADDITION;
			result = a + b;
			break;
		case 1:
			op = Operation.SUBSTRACTION;
			result = a - b;
			break;
		case 2:
			op = Operation.MULTIPLICATION;
			result = a * b;
			break;
		case 3:
		case 4:
			op = (rdOp==3 ? Operation.DIVISION : Operation.EUCLIDIAN_DIVISION);
			b = (b==0 ? 1 : b);
			result = (op==Operation.DIVISION ? a/b : a%b);
			break;
		default:
			break;
		}
	}
	
	public boolean answer(int ans) {
		return (result == ans);
	}
	
	public int getResult() {
		return result;
	}
	
	public String toString() {
		String str = String.format("%d %s %d = ?", a, op, b);
		return str;
	}

}
