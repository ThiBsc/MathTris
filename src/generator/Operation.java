package generator;

public enum Operation {
	
	ADDITION("+"),
	SUBSTRACTION("-"),
	MULTIPLICATION("x"),
	DIVISION("÷");
	//EUCLIDIAN_DIVISION("R÷");
	
	private String op;
	
	private Operation(String op) {
		this.op = op;
	}
	
	public String toString() {
		return op;
	}

}
