package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import calculator.*;

public class DijkstraAlgorithmCalculatorSingleTests {
	
	private static String className = DijkstraAlgorithmCalculatorSingle.class.getSimpleName();
	
	private static void positiveTestConstructor (String testKey) {
		System.out.println("Тест " + testKey);
		Map <String, Double> map = TestHelper.testCases.get(testKey);
		for (Map.Entry<String, Double> entry : map.entrySet()) {
			System.out.print("\""+entry.getKey()+"\"= ");
			try {
				String expression = entry.getKey();
				expression = expression.replace(" ", "").replace("(-", "(0-").replace("(+", "(0+");
				if (expression.charAt(0) == '-' || expression.charAt(0) == '+') {
					  expression = "0" + expression;
				}
			  
				final char[] input = expression.toCharArray(); //{'(','1','+','1',')'};
				
				char[] output = new char[input.length];
				
				if(DijkstraAlgorithmCalculatorSingle.expressionParser(input, output)) {
					if (DijkstraAlgorithmCalculatorSingle.expressionCalc(output)) {
						
						System.out.println("Результат: " + String.valueOf(output) + "=" + DijkstraAlgorithmCalculatorSingle.getResult());
					}
					else {
						System.out.println("Ошибка вычисления: " + String.valueOf(output));
					}
				}
		    	assertEquals(entry.getValue(), DijkstraAlgorithmCalculatorSingle.getResult(), 0);
		    	
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
