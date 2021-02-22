package calculator;

import java.util.*;

/** Константы и вспомогательные функции */
public class Helper {
		
	/** Перечисление математических операторов и функций.
	 * 	Используется для идентификации математических символов и функций.*/
	public static enum OPERATOR {
		// Операторы + скобки + запятая
		PLUS, MINUS, MULTIPLY, DIVIDE, MOD, POW1, OR, AND
		, LEFT_BRACKET, RIGHT_BRACKET, LEFT_SQUARE_BRACKET, RIGHT_SQUARE_BRACKET
		, LEFT_CURLY_BRACKET, RIGHT_CURLY_BRACKET, COMMA
		// Математические функции
		, SIN(1), COS(1), TAN(1), COT(1)
		, RANDOM(0), ROUND(1), ABS(1), EXP(1), LOG(1), SQRT(1), POW(2), XOR(2), MIN(2), MAX(2)
		// Служебные значения: число, унарный минус, окончание выражения
		, NUMBER, UNARY_MINUS, END;
		
		/** Количество аргументов функции */
		private final int arguments; //
		
		/** Конструктор по умолчанию
		 *  Оператор или разделитель */
		OPERATOR() {
			this(-1);
		}
		
		/** Конструктор
		 * @param arguments -- определяет количество аргументов функции */
		OPERATOR(int arguments) {
			this.arguments = arguments;
		}
		
		/** Получить количество аргументов оператора
		 *  @return arguments */
		public int getArguments() {
			return arguments;
		}
		
		/** Соответствие символьного представления оператора и объекту enum OPERATOR*/
		private static Map<Character, OPERATOR> operatorMap;
		
		static {
			operatorMap = new HashMap<>();
			operatorMap.put('+', PLUS);
			operatorMap.put('-', MINUS);
			operatorMap.put('*', MULTIPLY);
			operatorMap.put('/', DIVIDE);
			operatorMap.put('%', MOD);
			operatorMap.put('^', POW1);
			operatorMap.put('|', OR);
			operatorMap.put('&', AND);
			operatorMap.put('(', LEFT_BRACKET);
			operatorMap.put(')', RIGHT_BRACKET);
			operatorMap.put('[', LEFT_SQUARE_BRACKET);
			operatorMap.put(']', RIGHT_SQUARE_BRACKET);
			operatorMap.put('{', LEFT_CURLY_BRACKET);
			operatorMap.put('}', RIGHT_CURLY_BRACKET);
			operatorMap.put(',', COMMA);
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
				case NUMBER: return left;

				case PLUS: return left + right;

				case MINUS: return left - right;

				case MULTIPLY: return left * right;

				case DIVIDE: return left / right;
				
				case MOD: return left % right;
				
				case POW1: return Math.pow(left, right);
				
				case OR: return (int)left | (int)right;
				
				case AND: return (int)left & (int)right;

				case UNARY_MINUS: return -left;

				case SIN: return Math.sin(Math.toRadians(left));

				case COS: return Math.cos(Math.toRadians(left));

				case TAN: return Math.tan(Math.toRadians(left));

				case COT: return 1/Math.tan(Math.toRadians(left));
  	
				case RANDOM: return Math.random();

				case ROUND: return Math.round(left);

				case ABS: return Math.abs(left);

				case EXP: return Math.exp(left);

				case LOG: return Math.log(left);

				case SQRT: return Math.sqrt(left);
  	
				case POW: return Math.pow(left, right);
				
				case XOR: return (int)left ^ (int)right;
  	
				case MIN: return Math.min(left, right);

				case MAX: return Math.max(left ,right);
      	
				default: throw new Exception("Ошибка вычисления выражения");
			}
		}
	};
	
	/** Математические константные выражения */
	public static enum MathConstant {
		PI(Math.PI),
		E(Math.E),
		SQRT2(Math.sqrt(2)),
		SQRT1_2(Math.sqrt(.5)),
		LN2(Math.log(2)),
		LN10(Math.log(10)),
		LOG2E(1./Math.log(2)),
		LOG10E(1./Math.log(10));
		
		/** Значение выражения элемента enum */
		private final double value;
		
		/** Конструктор
		 *	@param значение элемента enum */
		MathConstant(double value) {
			this.value = value;
		}
		
		/** Получить значение выражения элемента enum
		 * 	@return value -- возвращает результат вычисления */
		public double getValue () {
			return value;
		}
	};
	
	/** Шаблон форматирования строки резудьтатов вычисления*/
	public static final String decimalFormat = "#.###############";
	
	
	/** 
	 * Массивы константных значений для функционального программирования
	 **/
	
	/** Математические символы, содержат:
	 * - операторы арифметических операций,
	 * - скобки группирования или функций.*/
	public static final String serviceSymbolString = "+-*/%^|&()[]{},"; // Порядок следования строго соответствует enum OPERATOR
	
	/** Математические константы, используются для функциональной реализации задачи (см. ExpressionCalculatorFunc)*/
	public static enum CONSTANT_NAME {
		PI, E, SQRT2, SQRT1_2, LN2, LN10, LOG2E, LOG10E
	};
	
	/** Значения математических константных выражений, порядок следования соответствует enum CONSTANT_NAME*/
	public static final double CONSTANT_VALUE[] = {
		Math.PI,Math.E,Math.sqrt(2),Math.sqrt(.5),Math.log(2),Math.log(10),1./Math.log(2),1./Math.log(10)
	};
}
