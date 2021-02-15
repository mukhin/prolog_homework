package demo;

import calculator.ExpressionCalculatorFunc;

public class ExampleCalculatorFunc1 {

	public static void main(String[] args){

		try {
			String v = ExpressionCalculatorFunc.calculate(args[0]);
			System.out.println(v);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}