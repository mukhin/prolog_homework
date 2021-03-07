package calculator;

import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;


public class Parser {
	
	private final String OPERATORS = "+-*/%"; // Операторы
	private final String BRACKETS = "()"; // Скобки
	
	private Stack <String> output; // Выходная очередь
	private Stack <String> operator_stack; // Очередь операторов
	
	Parser() {
		output = new Stack <String>();
		operator_stack = new Stack <String>();
	}
	
	/** Проверка токена на соответствие числу
	 *	@param токен
	 *	@return результат сравнения */
	private boolean isNumeric(String token) {
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
	private boolean isLeftBracket(String token) {
		return token.equals("(");
	}

	/** Проверка токена на соответствие правой скобке
	 *	@param токен
	 *	@return результат сравнения */
	private boolean isRightBracket(String token) {
		return token.equals(")");
	}

	/** Проверка токена на соответствие оператору
	 *	@param токен
	 *	@return результат сравнения */
	private boolean isOperator(String token) {
		return OPERATORS.contains(token);
	}
	
	/** Проверка приоритета токена-оператора
	 *	@param токен
	 *	@return результат сравнения */
	private int getPriority(String token) {
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
	
	/** Получить содержимое выходной очереди
	 *	@return output */
	public Stack<String> getOutput() {
		return output;
	}
	
	/** Получить содержимое очереди операторов
	 *	@return operator_stack */
	public Stack<String> getOpStack() {
		return operator_stack;
	}
	
	/** Разобрать выражение и записать его в выходную очередь в обратной польской нотации
	 * @param expression математическое выражение
	 * @return output выходная очередь в обратной польской нотации */
	public Stack<String> parse(String expression) throws Exception {
		expression = prepareExpressionString(expression);
		
		// Удаляем содержимое очередей
		operator_stack.clear();
		output.clear();
		
		// Разбиваем строку выражения на отдельные элементы 
		StringTokenizer stringTokenizer = new StringTokenizer(expression, OPERATORS + BRACKETS, true);
		
		while (stringTokenizer.hasMoreTokens()) {
			
			String token = stringTokenizer.nextToken();
			
			if (isLeftBracket(token)) { // Если левая скобка, поместить в стек операторов
				operator_stack.push(token);
				
				// Если правая скобка, все операторы переносятся в выходную очередь,
				// пока не появится левая скобка
			} else if (isRightBracket(token)) {
				while (!operator_stack.empty()
					&& !isLeftBracket(operator_stack.lastElement())) {
					output.push(operator_stack.pop());
				}
				if (!operator_stack.empty()) { // Проверяем, что очередь операторов не пуста, иначе пропущена левая скобка 
					operator_stack.pop();
				}
				else {
					throw new Exception("Ошибка: Отсутствует открываеющая скобка");
				}
			} else if(isNumeric(token)) { // Если число, добавить в выходную очередь
				output.push(token);
				
			} else if (isOperator(token)) { // Пока приоритет token меньше операторов в очереди, добавляем их в output
				while (!operator_stack.empty()
						&& isOperator(operator_stack.lastElement())
						&& getPriority(token) <= getPriority(operator_stack
								.lastElement())) {
					output.push(operator_stack.pop());
				}
				operator_stack.push(token);
				
			} else { // Если token не подходит ни под один из типов, -- ошибка
				throw new Exception("Ошибка: Неизвестный элемент \"" + token + "\"");
			}
		}
		while (!operator_stack.empty()) {
			output.push(operator_stack.pop());
		}

		Collections.reverse(output); // Переворачиваем очередь в обратном порядке	
		
		return output;

	}
}