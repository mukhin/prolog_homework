package demo;

import java.util.List;
import java.util.ArrayList;
import calculator.ExpressionCalculator;
import calculator.ExpressionCalculatorFunc;

public class ExpressionCalculatorsTest {
	private static List<String[]> testCases;
	
	static {
		testCases = new ArrayList<>();
		testCases.add(new String[]{"\nНачало теста\n"});
		testCases.add(new String[]{"\nПозитивный: Арифметические операции", "+1*2+3", "+(+2)", "-(-2)", "-max(+1,-2)", "1+2*3"});
		testCases.add(new String[]{"\nПозитивный: Тригонометрия и степень", "1*2*sin(30)", "sin(pow(pi,e))", "sin(1+3*4)"});
		testCases.add(new String[]{"\nНегативный: Ошибки форматирования", "1*2+(", "1*2-(", "sin()", "sin(", "sin)"});
		testCases.add(new String[]{"\nНегативный: Ошибки последовательности операторов", "+-2", "2/+2", "1+-2"});
		testCases.add(new String[]{"\nОкончание теста"});
	}

	public static void main(String[] args) {
		
		for (String[] entry:testCases){
			int i = 0;
		    for (String s:entry){
		    	if (i > 0) {
		    		try {
		    			System.out.print("ExpressionCalculator:\""+s+"\"= ");
		    			System.out.println(ExpressionCalculator.calculate(s));
		    		}
		    		catch (Exception e) {
						System.err.println(e.getMessage());
		    		}
		    		
		    		try {
		    			System.out.print("ExpressionCalculatorFunc:\""+s+"\"= ");
		    			System.out.println(ExpressionCalculatorFunc.calculate(s)+"\n");
		    		}
		    		catch (Exception e) {
						System.err.println(e.getMessage()+"\n");
		    		}
		    	}
		    	else {
		    		System.out.println(s);
		    	}
	    		++i;
		    }
		}
	}
}
