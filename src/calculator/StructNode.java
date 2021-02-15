package calculator;

/** Структура математического выражения
 * 	Используется для функционального варианта решения задачи.
 * 	cм. ExpressionCalculatorFunc
 */
public class StructNode {
	Helper.OPERATOR operator = null;//Оператор/скобки/разделитель/функция         
	double value = 0; 				//Вычисляемое значение выражения
	StructNode left = null;			//Левая и правая части выражения
	StructNode right = null; 
};
