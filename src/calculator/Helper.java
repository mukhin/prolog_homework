package calculator;

import java.util.*;

/** Константы и вспомогательные функции */
public class Helper {
		
	/** Перечисление математических операторов и функций.
	 * 	Используется для идентификации математических символов и функций.*/
	public static enum OPERATOR {
		
		// Операторы + скобки
		UNARY_MINUS(3, 1, false), PLUS(1, 2, true), MINUS(1, 2, true), MULTIPLY(2, 2, true), DIVIDE(2, 2, true), MOD(2, 2, true)
		,LEFT_BRACKET(), RIGHT_BRACKET(), NUMBER(), END();
		
		/** Приоритет оператора */
		int priority = 0;
		
		/** Количество аргументов */ 
		int arguments = 0;
		
		/** Признак левоассоциативного оператора */
		boolean is_left = false;
		
		/** Конструктор по умолчанию
		 *  Оператор или разделитель */
		OPERATOR() {
			priority = 0;
			arguments = 0;
			is_left = false;
		}
		
		/** Конструктор
		 * @param arguments -- определяет количество аргументов функции */
		OPERATOR(int priority, int arguments, boolean is_left) {
			this.priority = priority;
			this.arguments = arguments;
			this.is_left = is_left;
		}
		
		/** Получить количество аргументов оператора
		 *  @return arguments */
		public int getPriority() {
			return priority;
		}
		
		/** Получить количество аргументов оператора
		 *  @return arguments */
		public int getArguments() {
			return arguments;
		}
		
		/** Определяет левый или правый оператор?
		 *	@param op оператор
		 * 	@return результат: левый == true; правый == false */
		public boolean opLeftOrRight( ) {
			return is_left;
		}
		
		/** Соответствие символьного представления оператора и объекту enum OPERATOR*/
		private static Map<Character, OPERATOR> operatorMap;
		
		static {
			operatorMap = new HashMap<>();
			operatorMap.put('!', UNARY_MINUS);
			operatorMap.put('+', PLUS);
			operatorMap.put('-', MINUS);
			operatorMap.put('*', MULTIPLY);
			operatorMap.put('/', DIVIDE);
			operatorMap.put('%', MOD);
			operatorMap.put('(', LEFT_BRACKET);
			operatorMap.put(')', RIGHT_BRACKET);
		}
		
		/** Поиск в enum по символу оператора
		 * @param symbol -- символьное представление оператора
		 * @return объект enum OPERATOR или null */
		public static OPERATOR findOPERATOR(Character symbol) {
			if (symbol != null) {
				return operatorMap.get(symbol);
			}
			return null;
		}
		
		/** Вычисление результата операции
		 * @param left -- левая часть выражения
		 * @param right -- правая часть выражения
		 * @return результат операции */
		public double calculate(double left, double right) throws Exception {
			
			switch(this) {
				case UNARY_MINUS: return -left;
				
				case NUMBER: return left;

				case PLUS: return left + right;

				case MINUS: return left - right;

				case MULTIPLY: return left * right;

				case DIVIDE: return left / right;
				
				case MOD: return left % right;
      	
				default: throw new Exception("Ошибка вычисления выражения");
			}
		}
	};
}
