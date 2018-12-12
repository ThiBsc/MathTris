package generator;

import java.util.Random;
import java.util.TreeSet;

/**
 * @author thiddev
 */
public class EquationGenerator {
	
	//private int maxlength;
	private int a, b, result;
	private Operation op;
	private TreeSet<Operation> opSet;
	private TreeSet<Integer> tableSet;
	
	public EquationGenerator() {
		//maxlength = 3;
		a = b = result = 0;
		op = Operation.ADDITION;
		opSet = new TreeSet<Operation>();
		opSet.add(Operation.MULTIPLICATION);
		tableSet = new TreeSet<Integer>();
		setTable("1 2 3 4 5 6 7 8 9");
	}
	
	public void setOperation(Operation[] ops) {
		opSet.clear();
		for (Operation op : ops) {
			opSet.add(op);
		}
	}
	
	public void setTable(String tableStr) {
		tableSet.clear();
		String[] tables = tableStr.split("[\\s,;]");
		for (String table : tables) {
			try {
				tableSet.add(Integer.parseInt(table));
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public void generate() {
		Random r = new Random();
		// Generate the max value of a or b
		// 1 <= a <= 9 
		a = r.nextInt((9-1)) + 1;
		// b is a table of X
		b = (int) tableSet.toArray()[r.nextInt(tableSet.size())];
		// [+, -, *, /]
		op = (Operation) opSet.toArray()[r.nextInt(opSet.size())];
		switch (op) {
		case ADDITION:
			result = a + b;
			break;
		case SUBSTRACTION:
			result = a - b;
			break;
		case MULTIPLICATION:
			result = a * b;
			break;
		case DIVISION:
		//case EUCLIDIAN_DIVISION:
			result = a / b;
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
