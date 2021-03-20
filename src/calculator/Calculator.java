package calculator;

import java.util.Stack;
import java.util.Deque;

//import calculator.Helper.OPERATOR;

/** Калькулятор выражений */
public class Calculator {
	
	/** Посчитать выражение в обратной польской нотации
	 * 	Алгоритм:
	 * 		1. Берём очередной элемент выражения.
	 * 		2. Проверяем 
	 * 			- если это число, помещаем во временный стек result_stack
	 * 			- если это оператор:
	 * 				- выталкиваем из стека последние два числа;
	 * 				- вычисляем результат;
	 * 				- помещаем результат в стек.
	 * 		3.	К тому времени, когда исходные элементы закончатся, в стеке должно остаться единственное число. 
	 * 			Оно и есть результат.	
	 * @param output выражение
	 * @return Массив PRN+результат */
	public static Stack<String> calculate(Deque<String> output) {
		
		double result = 0.0; // результат
		Stack <String> calc_output = new Stack <String>(); calc_output.addAll(output); // Клонируем выражение
		Stack <Double> result_stack = new Stack <Double>(); // Очередь, куда записываются числа и результаты вычислений
		
		while(!calc_output.empty()) {
			String token = calc_output.pop(); // Получить очередной токен
			if(Helper.isNumeric(token)) { // Если число -- положить в стек результат
				result_stack.push(Double.parseDouble(token));
			}
			else if(Helper.isOperator(token)){ // Если оператор -- вычислить результат и положить в стек
				if(result_stack.size() >= 2) { // Выталкиваем из стека последние два числа, результат вычисления token(op1, op2) помещаем в стек
					double op1 = result_stack.pop(); 
					double op2 = result_stack.pop();
					result = opCalculate(op1, op2, token);
					result_stack.push(result);		
				}
				else {					
					returnError("Ошибка: Количество операндов выражения \""
						+ token + "\" равно" + result_stack.size());
				}
				
			} else { // Если token не подходит ни под один из типов, -- ошибка
				returnError("Ошибка: Неизвестный элемент \"" + token + "\"");
			}
		}
		
		if(result_stack.size() == 1) { // В стеке должно остаться единственное число -- результат
			result = result_stack.pop();
		}
		else {
			returnError("Ошибка: Количество операндов в стеке равно " + result_stack.size()
				+ " Ожидалось: 1");
		}
		
		calc_output.add(Double.toString(result));
		calc_output.add("=");
		calc_output.addAll(output);
		return calc_output;
	}

	private static double opCalculate(final double op1, final double op2, final String operator ) {
		return Helper.OPERATOR.findOPERATOR(operator).calculate(op1, op2);
	}
	
	private static Stack<String> returnError(String error){
		Stack <String> error_stack = new Stack <String>();
		error_stack.add(error);
		return error_stack;
	}
	
	
}