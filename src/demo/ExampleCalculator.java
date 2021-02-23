package demo;

import calculator.*;

public class ExampleCalculator {//В качестве математического выражения используется строка входного аргумента 

	public static void main(String[] args) {
	  
		String expression = DijkstraCalculator.prepareExpressionString(args[0]); //Исходное выражение

		final char[] input = expression.toCharArray();
		char[][] output = new char[input.length][input.length];
		
		System.out.println("Исходное выражение: " + String.valueOf(input));
	  
		if(DijkstraCalculator.expressionParser(input, output)){
			if (DijkstraCalculator.expressionCalc(output)) {
				System.out.print("Результат: ");
				for (int i = 0; i < output.length; i++) {
					System.out.print(String.valueOf(output[i]));
				}
				System.out.println("=" + DijkstraCalculator.getResult());
			}
			else {
				System.out.print("Ошибка вычисления: ");
				for (int i = 0; i < output.length; i++) {
					System.out.print(String.valueOf(output[i]));
				}
				System.out.println("");
			}
		}
		else {
			System.out.println("Ошибка разбора: " + String.valueOf(input));
		}
	}
}