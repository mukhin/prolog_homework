package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;
import java.util.Stack;

import calculator.*;

public class DijkstraCalculatorTests {
	
	private static String className = DijkstraCalculator.class.getSimpleName();
	
	private static void positiveTestConstructor (String testKey) {
		System.out.println("Тест " + testKey);
		
		Parser p = new Parser();
		Calculator c = new Calculator();
		Map <String, Double> map = TestHelper.testCases.get(testKey);
		for (Map.Entry<String, Double> entry : map.entrySet()) {
			System.out.print("\""+entry.getKey()+"\"");
			try {
				String expression = entry.getKey();
				String testStr = Double.toString(entry.getValue());
				String calc_expression = Helper.prepareExpressionString(expression);
				
			    Stack<String> output = p.parse(calc_expression);
			    double result = c.calculate(output);
			    
				System.out.println("= " + result
						+ "; Польская нотация: " + output
						+ "; Эталонное значение: " + testStr);

		    	assertEquals(entry.getValue(), result, 0);
		    	
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("\nТестирование класса " + className);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Тестирование класса " + className + " окончено\n");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("Начало теста");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("Окончание теста\n");		
	}

	@Test
	public void testA() {
		positiveTestConstructor("A");
	}
	
	@Test
	public void testB() {
		positiveTestConstructor("B");
	}
	
}
