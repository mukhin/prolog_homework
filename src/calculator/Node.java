package calculator;

import calculator.Helper.OPERATOR;

/** Выражение, является узлом дерева подвыражений, содержит:
 * - левую и правую части выражения,
 * - оператор или функцию, которые выполняются над частями выражения,
 * - результат вычисления выражения.
 */
public class Node {
	OPERATOR operator = null;// Оператор/скобки/разделитель/функция         
	double value = 0; 		 // Вычисляемое значение выражения
	Node left = null;		 // Левая часть выражения
	Node right = null;		 // Правая часть выражения
  
	/** Конструктор
	 *	@param operator -- оператор
	 *  @param value -- значение выражения
	 *	@param left -- левая часть выражения
	 *  @param right -- правая часть выражения */
	Node (Helper.OPERATOR operator,double value, Node left, Node right) {
		this.operator = operator;
		this.value = value; 
		this.left = left;
		this.right = right;
	}
	
	/** Конструктор
	 *	@param operator -- оператор
	 *	@param left -- левая часть выражения */
	Node(Helper.OPERATOR operator, Node left) {
		this(operator, 0, left, null);
	}
	
	/** Конструктор
	 *	@param operator -- оператор */
	Node(Helper.OPERATOR operator) {
		this(operator, 0, null, null);
	}
	
	/** Конструктор
	 *	@param operator -- оператор
	 *	@param value -- значение выражения */
	Node(Helper.OPERATOR operator, double value) {
		this(operator,value, null, null);
	}
	
	/** Вычисление выражения */
	public double calculate() throws Exception {
		
		if (operator == null) {
			throw new Exception("Ошибка: оператор не инициализирован");
		}
		double left = (this.left == null) ? value:this.left.calculate();
		double right = (this.right == null) ? 0:this.right.calculate();

		return operator.calculate(left, right);
	}	  
}
