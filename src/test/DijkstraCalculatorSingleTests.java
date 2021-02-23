package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import calculator.*;

public class DijkstraCalculatorSingleTests {
	
	private static String className = DijkstraCalculatorSingle.class.getSimpleName();
	
	private static void positiveTestConstructor (String testKey) {
		System.out.println("Тест " + testKey);
		Map <String, Double> map = TestHelper.testCases.get(testKey);
		for (Map.Entry<String, Double> entry : map.entrySet()) {
			System.out.print("\""+entry.getKey()+"\" ");
			try {
				String expression = entry.getKey();
				String testStr = Double.toString(entry.getValue());
				expression = DijkstraCalculatorSingle.prepareExpressionString(expression);
			  
				final char[] input = expression.toCharArray();
				
				char[] output = new char[input.length];
				
				if(DijkstraCalculatorSingle.expressionParser(input, output)) {
					if (DijkstraCalculatorSingle.expressionCalc(output)) {
						System.out.println("Результат: " + String.valueOf(output)
							+ "=" + DijkstraCalculatorSingle.getResult()
							+ "; Эталонное значение: " + testStr);
					}
					else {
						System.out.println("Ошибка вычисления: " + String.valueOf(output));
					}
				}
		    	assertEquals(entry.getValue(), DijkstraCalculatorSingle.getResult(), 0);
		    	
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
