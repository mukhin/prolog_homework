package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.Map;

import calculator.*;

public class ExpressionCalculatorTests {
	
	private static void positiveTestConstructor (String testKey) {
		System.out.println("Тест " + testKey);
		Map <String, Double> map = TestHelper.testCases.get(testKey);
		for (Map.Entry<String, Double> entry : map.entrySet()) {
			System.out.print("\""+entry.getKey()+"\"= ");
			try {
				double result = ExpressionCalculator.calculateDouble(entry.getKey());
				String resultStr = new DecimalFormat(Helper.decimalFormat).format(result);
				String testStr = new DecimalFormat(Helper.decimalFormat).format(entry.getValue());
		    	System.out.println(resultStr + "; ожидалось: " + testStr);
		    	assertEquals(entry.getValue(), result, 0);
		    	
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	private static void negativeTestConstructor (String testKey) {
		int argument = 0;
		System.out.println("Тест " + testKey);
		Map <String, Double> map = TestHelper.testCases.get(testKey);
		for (Map.Entry<String, Double> entry : map.entrySet()) {
			System.out.print("\""+entry.getKey()+"\"= ");
			try {
				double result = ExpressionCalculator.calculateDouble(entry.getKey());
				String resultStr = new DecimalFormat(Helper.decimalFormat).format(result);
				String testStr = new DecimalFormat(Helper.decimalFormat).format(entry.getValue());
		    	System.out.println(resultStr + "; ожидалось: " + testStr);
		    	assertEquals(entry.getValue(), argument, 0);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("\nТестирование класса ExpressionCalculator");
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Тестирование класса ExpressionCalculator окончено\n");
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
	
	@Test
	public void testC() {
		positiveTestConstructor("C");
	}
	
	@Test
	public void testD() {
		positiveTestConstructor("D");
	}
	
	@Test
	public void testE() {
		negativeTestConstructor("E");
	}
	
	@Test
	public void testF() {
		negativeTestConstructor("F");
	}
}
