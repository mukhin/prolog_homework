package calculator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

//import calculator.Helper.OPERATOR;

/** Калькулятор выражений */
public class Calculator {

	public Calculator() {}
	
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
	 * @return результат */
	@SuppressWarnings("unchecked")
	public double calculate(Stack<String> output) throws Exception {
		double result = 0.0; // результат
		Stack <String> calc_output = (Stack <String>) output.clone(); // Клонируем выражение
		Stack <Double> result_stack = new Stack <Double>(); // Очередь, куда записываются числа и результаты вычислений
		
		Collections.reverse(calc_output); // Переворачиваем очередь в обратном порядке
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
					throw new Exception("Ошибка: Количество операндов выражения \""
						+ token + "\" равно" + result_stack.size());
				}
				
			} else { // Если token не подходит ни под один из типов, -- ошибка
				throw new Exception("Ошибка: Неизвестный элемент \"" + token + "\"");
			}
		}
		
		if(result_stack.size() == 1) { // В стеке должно остаться единственное число -- результат
			result = result_stack.pop();
		}
		else {
			throw new Exception("Ошибка: Количество операндов в стеке равно" + result_stack.size()
				+ " Ожидалось: 1");
		}
		
		return result;
	}
	
	enum OPERATOR {
	    PLUS {
	        @Override
	        public double calculate(final double op1, final double op2) {
	            return op2+op1;
	        }
	    },
	    MINUS {
	        @Override
	        public double calculate(final double op1, final double op2) {
	            return op2-op1;
	        }
	        
	    },
	    MULTIPLY {
	        @Override
	        public double calculate(final double op1, final double op2) {
	            return op2*op1;
	        }
	    },
	    DIVIDE {
	        @Override
	        public double calculate(final double op1, final double op2) {
	            return op2/op1;
	        }
	    },	
	    MOD {
	        @Override
	        public double calculate(final double op1, final double op2) {
	            return op2%op1;
	        }
	    };

	    public abstract double calculate(final double op1, final double op2);

		/** Соответствие символьного представления оператора и объекту enum OPERATOR*/
		private static Map<String, OPERATOR> operatorMap;
		
		static {
			operatorMap = new HashMap<>();
			operatorMap.put("+", PLUS);
			operatorMap.put("-", MINUS);
			operatorMap.put("*", MULTIPLY);
			operatorMap.put("/", DIVIDE);
			operatorMap.put("%", MOD);
		};
		
		/** Поиск в enum по символу оператора
		 * @param symbol -- символьное представление оператора
		 * @return объект enum OPERATOR или null */
		public static OPERATOR findOPERATOR(String symbol) {
			return operatorMap.get(symbol);
		}
	}
	
	/** Вычисление выражения для оператора
	 * 	@param op1 левый операнд
	 * 	@param op2 правый операнд
	 *	@param operator оператор
	 *	@return результат вычисления */
	private double opCalculate(final double op1, final double op2, final String operator ) {
		return OPERATOR.findOPERATOR(operator).calculate(op1, op2);
	}
}