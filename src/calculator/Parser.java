package calculator;

import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;


public class Parser {
	
	private Stack <String> output; // Выходная очередь
	private Stack <String> operator_stack; // Очередь операторов
	
	public Parser() {
		output = new Stack <String>();
		operator_stack = new Stack <String>();
	}
	
	/** Разобрать выражение и записать его в выходную очередь в обратной польской нотации
	 * @param expression математическое выражение
	 * @return output выходная очередь в обратной польской нотации */
	@SuppressWarnings("unchecked")
	public Stack<String> parse(String expression) throws Exception {
		expression = Helper.prepareExpressionString(expression);
		
		// Удаляем содержимое очередей
		operator_stack.clear();
		output.clear();
		
		// Разбиваем строку выражения на отдельные элементы 
		StringTokenizer stringTokenizer = new StringTokenizer(expression, Helper.OPERATORS + Helper.BRACKETS, true);
		
		while (stringTokenizer.hasMoreTokens()) {
			
			String token = stringTokenizer.nextToken();
			
			if (Helper.isLeftBracket(token)) { // Если левая скобка, поместить в стек операторов
				operator_stack.push(token);
				
				// Если правая скобка, все операторы переносятся в выходную очередь,
				// пока не появится левая скобка
			} else if (Helper.isRightBracket(token)) {
				while (!operator_stack.empty()
					&& !Helper.isLeftBracket(operator_stack.lastElement())) {
					output.push(operator_stack.pop());
				}
				if (!operator_stack.empty()) { // Проверяем, что очередь операторов не пуста, иначе пропущена левая скобка 
					operator_stack.pop();
				}
				else {
					throw new Exception("Ошибка: Отсутствует открываеющая скобка");
				}
			} else if(Helper.isNumeric(token)) { // Если число, добавить в выходную очередь
				output.push(token);
				
			} else if (Helper.isOperator(token)) { // Пока приоритет token меньше операторов из очереди, добавляем их в output
				while (!operator_stack.empty()
						&& Helper.isOperator(operator_stack.lastElement())
						&& Helper.getPriority(token) <= Helper.getPriority(operator_stack.lastElement())) {
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
		
		return (Stack<String>) output.clone();
	}
}