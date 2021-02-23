package demo;

import calculator.*;

public class ExampleSingle {

	public static void main(String[] args) {
	  
		String expression = args[0];
		expression = expression.replace(" ", "").replace("(-", "(0-").replace("(+", "(0+");
		if (expression.charAt(0) == '-' || expression.charAt(0) == '+') {
			expression = "0" + expression;
		}

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