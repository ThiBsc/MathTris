package test;

import java.util.Scanner;

import generator.EquationGenerator;

public class Testor {

	public static void main(String[] args) {
		EquationGenerator eg = new EquationGenerator();
		eg.generate();
		System.out.println(eg);
		Scanner sc = new Scanner(System.in);
		int answer = sc.nextInt();
		sc.close();
		boolean res = eg.answer(answer);
		System.out.println(String.format("%b (%d %s %d)", res, answer, (res?"=":"!="), eg.getResult()));
	}

}
