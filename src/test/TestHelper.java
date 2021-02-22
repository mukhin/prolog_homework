package test;

import java.util.HashMap;
import java.util.Map;

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
		map.put("-(+1-2)", (double)-(+1-2));
		map.put("1+2*3", (double)1+2*3);
		map.put("!1", (double)-1);
		map.put("4%5", (double)4%5);
		map.put("431%5", (double)431%5);
		testCases.put("A", map);
		
		map = new HashMap<>();
		map.put("+-2", (double)(-1));
		map.put("2/+2", (double)(-1));
		map.put("1+-2", (double)(-1));
		testCases.put("B", map);
		
	}
}
