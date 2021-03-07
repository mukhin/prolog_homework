package calculator;

import java.util.Stack;

public class Calculator {

	Calculator() {}
	
	@SuppressWarnings("unchecked")
	double calculate(Stack<String> output) throws Exception {
		double result = 0.0;
		Stack <String> calc_output = (Stack <String>) output.clone();
		Stack <Double> result_stack = new Stack <Double>();
		
		while(!calc_output.empty()) {
			String token = calc_output.pop(); // Получить очередной токен
			if(Helper.isNumeric(token)) { // Если число -- положить в стек результат
				result_stack.push(Double.parseDouble(token));
			}
			else if(Helper.isOperator(token)){ // Если оператор -- вычислить результат и положить в стек
				if(result_stack.size() >= 2) {
					double op1 = result_stack.pop();
					double op2 = result_stack.pop();
					result = opCalculate(op1, op2, token);
					result_stack.push(result);		
				}
				else {
					throw new Exception("Ошибка: Количество операндов выражения \""
						+ token + "\" равно" + result_stack.size());
				}
				
			} else { // Если token не подходит ни под один из типов, -- ошибка
				throw new Exception("Ошибка: Неизвестный элемент \"" + token + "\"");
			}
		}
		
		if(result_stack.size() == 1) {
			result = result_stack.pop();
		}
		else {
			throw new Exception("Ошибка: Количество операндов в стеке равно" + result_stack.size()
				+ " Ожидалось: 1");
		}
		
		return result;
	}
	
	/** Вычисление выражения для оператора
	 * 	@param op1 левый операнд
	 * 	@param op2 правый операнд
	 *	@param operator оператор
	 *	@return результат вычисления */
	private double opCalculate(final double op1, final double op2, final String operator ) {
		double result = 0.0;
		switch (operator) {
			case ("+"): result = op1 + op2;
				break;
			case ("-"): result = op1 - op2;
				break;
			case ("*"): result = op1 * op2;
				break;
			case ("/"): result = op1 / op2;
				break;
			case ("%"): result = op1 % op2;
				break;
		}
		return result;
	}
}