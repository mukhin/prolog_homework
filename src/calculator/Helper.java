package calculator;

/** Вспомогательный класс для предоставления общедоступных констант и функций */
public class Helper {
	public static final String OPERATORS = "+-*/%"; // Операторы
	public static final String BRACKETS = "()"; // Скобки
	
	/** Проверка токена на соответствие числу
	 *	@param токен
	 *	@return результат сравнения */
	public static boolean isNumeric(String token) {
		try {
			Integer.parseInt(token);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/** Проверка токена на соответствие левой скобке
	 *	@param токен
	 *	@return результат сравнения */
	public static boolean isLeftBracket(String token) {
		return token.equals("(");
	}

	/** Проверка токена на соответствие правой скобке
	 *	@param токен
	 *	@return результат сравнения */
	public static boolean isRightBracket(String token) {
		return token.equals(")");
	}

	/** Проверка токена на соответствие оператору
	 *	@param токен
	 *	@return результат сравнения */
	public static boolean isOperator(String token) {
		return OPERATORS.contains(token);
	}
	
	/** Проверка приоритета токена-оператора
	 *	@param токен
	 *	@return результат сравнения */
	public static int getPriority(String token) {
		if (token.equals("+") || token.equals("-")) {
			return 1;
		}
		return 2;
	}
	
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
}
