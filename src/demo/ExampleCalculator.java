package demo;

import java.util.Stack;

import calculator.*;

public class ExampleCalculator {//В качестве математического выражения используется строка входного аргумента 

	public static void main(String[] args) throws Exception {
	  
		String expression = args[0]; //Исходное выражение
		String calc_expression = Helper.prepareExpressionString(expression); //Преобразование исходного выражения
	    
	    Parser p = new Parser();
	    Stack<String> output = p.parse(calc_expression);
	    
	    Calculator c = new Calculator();
	    double result = c.calculate(output);
	    
	    System.out.println(expression);
	    
	    for(String op : output) {
	    	System.out.print(op);
	    }
	    
	    System.out.println();
	    
	    System.out.println(expression + " =" + result);
		
	}
}