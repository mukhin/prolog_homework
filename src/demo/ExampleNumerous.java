package demo;

import calculator.*;

public class ExampleNumerous {//В качестве математического выражения используется строка входного аргумента 

	public static void main(String[] args) {
	  
		String expression = DijkstraAlgorithmCalculator.prepareExpressionString(args[0]); //Исходное выражение

		final char[] input = expression.toCharArray();
		char[][] output = new char[input.length][input.length];
		
		System.out.println("Исходное выражение: " + String.valueOf(input));
	  
		if(DijkstraAlgorithmCalculator.expressionParser(input, output)){
			if (DijkstraAlgorithmCalculator.expressionCalc(output)) {
				System.out.print("Результат: ");
				for (int i = 0; i < output.length; i++) {
					System.out.print(String.valueOf(output[i]));
				}
				System.out.println("=" + DijkstraAlgorithmCalculator.getResult());
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