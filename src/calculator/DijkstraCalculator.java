package calculator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

/** В качестве значений операндов можно использовать многоразрядные числа. */
public class DijkstraCalculator {
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
		String expression = reader.readLine(); //Подготовка исходного выражения
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
