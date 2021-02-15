package demo;

import calculator.ExpressionCalculator;

public class Example2 {

	public static void main(String[] args) throws Exception{
		String expression[]={"sin(pi/4)","1+2"};
		for(String s:expression){
			System.out.print("\""+s+"\"=");
			try {
				System.out.println(ExpressionCalculator.calculate(s));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}