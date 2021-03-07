package calculator;

import java.util.*;

/** Константы и вспомогательные функции */
public class Helper {
	
	/** Подготовка строки выражения перед выполнением вычислений 
	 *	@param expression исходное выражение
	 	@return результат преобразования */
	public static String prepareExpressionString(String expression) {
		expression = expression.replace(" ", "").replace("(-", "(0-").replace("(+", "(0+");
		// Если выражение начинается со знака + или -, добавить "0"
		if (expression.charAt(0) == '-' || expression.charAt(0) == '+') {
			  expression = "0" + expression;
		}
			//Всё выражение обрамляется в скобки, поскольку за каждым числом должен следовать оператор 
		expression = "(" + expression + ")";
		return expression;
	}
	
	/** Проверка на соответствие цифровому символу
	 * @param c символ
	 * @return результат сравнения */
	public static boolean isNumeric (final Character c) {
		return (c.charValue() >= '0' && c.charValue() <= '9');
	}	
		
	/** Перечисление математических операторов */
	public static enum OPERATOR {
		
		// Операторы + скобки
		UNARY_MINUS(3, 1, false, true), PLUS(1, 2, true, true), MINUS(1, 2, true, true)
		,MULTIPLY(2, 2, true, true), DIVIDE(2, 2, true, true), MOD(2, 2, true, true)
		,LEFT_BRACKET(), RIGHT_BRACKET(), NUMERIC(), END();
		
		/** Приоритет оператора */
		int priority = 0;
		
		/** Количество аргументов */ 
		int arguments = 0;
		
		/** Признак левоассоциативного оператора */
		boolean is_left = false;
		
		/** Признак оператора */
		boolean is_operator = false;
		
		/** Конструктор по-умолчанию
		 *  Оператор или разделитель */
		OPERATOR() {
			this (0, 0, false, false);
		}
		
		/** Конструктор
		 * @param priority -- приоритет оператора
		 * @param arguments -- определяет количество аргументов функции
		 * @param is_left -- признак левоассоциативного оператора
		 * @param is_operator -- признак оператора */
		OPERATOR(int priority, int arguments, boolean is_left, boolean is_operator) {
			this.priority = priority;
			this.arguments = arguments;
			this.is_left = is_left;
			this.is_operator = is_operator;
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
		
		/** Определяет, левый или правый оператор?
		 * 	@return результат: левый == true; правый == false */
		public boolean opLeftOrRight( ) {
			return is_left;
		}
		
		/** Определяет, оператор или нет?
		 *	@param op оператор
		 * 	@return результат: оператор == true; не оператор == false */
		public boolean isOperator( ) {
			return is_operator;
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
		
		public static boolean isOperator(final Character symbol) throws Exception {
			OPERATOR op = findOPERATOR(symbol);
			if (op != null) {
				return op.isOperator();
			}
			throw new Exception("Ошибка: Это не оператор");
		}
		
		/** Вычисление результата операции
		 * @param left -- левая часть выражения
		 * @param right -- правая часть выражения
		 * @return результат операции */
		public double calculate(double left, double right) throws Exception {
			
			switch(this) {
				case UNARY_MINUS: return -left;
				
				case NUMERIC: return left;

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
