package demo;

import calculator.*;

public class ExampleSingle {//В качестве математического выражения используется первый из входных аргументов 

	public static void main(String[] args) {
	  
		String expression = DijkstraAlgorithmCalculatorSingle.prepareExpressionString(args[0]);

		final char[] input = expression.toCharArray();
		char[] output = new char[input.length];
		
		System.out.println("Исходное выражение: " + String.valueOf(input));
	  
		if(DijkstraAlgorithmCalculatorSingle.expressionParser(input, output)){
			if (DijkstraAlgorithmCalculatorSingle.expressionCalc(output)) {
				System.out.println("Результат: "
						+ String.valueOf(output)
						+ "="
						+ DijkstraAlgorithmCalculatorSingle.getResult());
			}
			else {
				System.out.println("Ошибка вычисления: " + String.valueOf(output));
			}
		}
		else {
			System.out.println("Ошибка разбора: " + String.valueOf(input));
		}
	}
}