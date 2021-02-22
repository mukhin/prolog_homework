package demo;

import calculator.*;

public class Example1 {

  public static void main(String[] args){
	  
	  String expression = args[0];
	  expression = expression.replace(" ", "").replace("(-", "(0-").replace("(+", "(0+");
	  if (expression.charAt(0) == '-' || expression.charAt(0) == '+') {
		  expression = "0" + expression;
	  }

	  final char[] input = expression.toCharArray();
	  char[] output = new char[input.length];
		
	  System.out.println("Исходное выражение: " + input.toString());
	  
	  if(DijkstraAlgorithmCalculator.expressionParser(input, output)){
		  DijkstraAlgorithmCalculator.expressionCalc(output);
		  
	  }
  }
}