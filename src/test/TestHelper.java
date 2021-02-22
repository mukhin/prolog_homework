package test;

import java.util.HashMap;
import java.util.Map;

import calculator.Helper;

/** Вспомогательный класс
 * 	Используется для инициализации тестовых наборов*/
public class TestHelper {
	public static Map<String, Map<String, Double>> testCases;
	
	static {
		testCases = new HashMap<>();
		
		Map<String, Double> map = new HashMap<>();
		map.put("+1*2+3", (double)+1*2+3);
		map.put("+(+2)", (double)+(+2));
		map.put("-(-2)", (double)-(-2));
		map.put("-max(+1,-2)", (double)-Math.max(+1,-2));
		map.put("1+2*3", (double)1+2*3);
		testCases.put("A", map);
		
		map = new HashMap<>();
		map.put("1*2*sin(30)", (double)1*2*Math.sin(Math.toRadians(30)));
		map.put("sin(pow(pi,e))", (double)Math.sin(Math.toRadians(Math.pow(Math.PI,Math.E))));
		map.put("sin(1+3*4)", (double)Math.sin(Math.toRadians(1+3*4)));
		testCases.put("B", map);
		
		map = new HashMap<>();
		map.put("6%4", (double)(6%4));
		map.put("7&5", (double)(7&5));
		map.put("7|5", (double)(7|5));
		map.put("7^5", (double)Math.pow(7,5));
		map.put("XOR(7,5)", (double)(7^5));
		map.put("-10", (double)-10);
		map.put("1E10", (double)Double.parseDouble("1E10"));
		map.put("PI", (double)Helper.MathConstant.PI.getValue());
		map.put("E", (double)Helper.MathConstant.E.getValue());
		map.put("SQRT2", (double)Helper.MathConstant.SQRT2.getValue());
		map.put("SQRT1_2", (double)Helper.MathConstant.SQRT1_2.getValue());
		map.put("LN2", (double)Helper.MathConstant.LN2.getValue());
		map.put("LN10", (double)Helper.MathConstant.LN10.getValue());
		map.put("LOG2E", (double)Helper.MathConstant.LOG2E.getValue());
		map.put("LOG10E", (double)Helper.MathConstant.LOG10E.getValue());
		testCases.put("C", map);
		
		map = new HashMap<>();
		map.put("sin(30)", (double)Math.sin(Math.toRadians(30)));
		map.put("cos(60)", (double)Math.cos(Math.toRadians(60)));
		map.put("tan(45)", (double)Math.tan(Math.toRadians(45)));
		map.put("cot(45)", (double)1/Math.tan(Math.toRadians(45)));
		map.put("round(10.12)", (double)Math.round(10.12));
		map.put("abs(-2)", (double)Math.abs(-2));
		map.put("exp(10)", (double)Math.exp(10));
		map.put("log(2)", (double)Math.log(2));
		map.put("sqrt(9)", (double)Math.sqrt(9));
		map.put("pow(4,9)", (double)Math.pow(4,9));
		map.put("min(-5,4)", (double)Math.min(-5,4));
		map.put("max(12,9)", (double)Math.max(12,9));
		testCases.put("D", map);
		
		map = new HashMap<>();
		map.put("1*2+(", (double)(-1));
		map.put("1*2-(", (double)(-1));
		map.put("sin()", (double)(-1));
		map.put("sin(", (double)(-1));
		map.put("sin)", (double)(-1));
		testCases.put("E", map);
		
		map = new HashMap<>();
		map.put("+-2", (double)(-1));
		map.put("2/+2", (double)(-1));
		map.put("1+-2", (double)(-1));
		testCases.put("F", map);
		
	}
}
