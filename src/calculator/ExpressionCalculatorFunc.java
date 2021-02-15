package calculator;

/** Калькулятор математических выражений в парадигме функционального программирования.
 * 	Для хранения результатов разбора частей выражения используется класс StructNode.
 *  Вместо enum MathConstant используются пары значений из массивов CONSTANT_NAME и CONSTANT_VALUE */

import java.text.DecimalFormat;

/** Калькулятор математических выражений в парадигме функционального программирования
 *  В отличие от классов ExpressionCalculator и Node здесь используются статические функции.
 *  Дерево выражений строится из объектов класса StructNode. */
public class ExpressionCalculatorFunc {
	
	/** Формат строки резудьтатов вычисления*/
	public static final String decimalFormat = "#.###############";
	
	public static byte[] expression; 		// Выражение
	public static double tokenValue; 		// Значение токена для числа или константного выражения
	public static Helper.OPERATOR operator; // Оператор/скобки/разделитель/функция в позиции курсора                   
	public static int position; 			// Позиция курсора в выражении
	
	/** Инициализация  структуры математического выражения 
	 * @param node -- выражение, которое собираемся инициализировать
	 * @param operator -- оператор
	 * @param value -- значение выражения */
	public static void init(StructNode node) throws Exception {
		node.operator = ExpressionCalculatorFunc.operator;
		node.value = ExpressionCalculatorFunc.tokenValue; 
		node.left = null;
		node.right = null;
		return;
	}
	
	/** Инициализация  структуры математического выражения 
	 * @param node -- выражение, которое собираемся инициализировать
	 * @param operator -- оператор
	 * @param left -- левая часть выражения */
	public static void init(StructNode node, Helper.OPERATOR operator, StructNode left)
		throws Exception {
		node.operator = operator;
		node.value = 0; 
		node.left = left;
		node.right = null;
		return;
	}
	
	/** Инициализация  структуры математического выражения 
	 * @param node -- выражение, которое собираемся инициализировать
	 * @param operator -- оператор */
	public static void init(StructNode node, Helper.OPERATOR operator) throws Exception {
		node.operator = operator;
		node.value = 0; 
		node.left = null;
		node.right = null;
		return;
	}
	
	// Проверка символов буква/цифра/точка/название функции
	public static boolean isLetter() throws Exception {
		return Character.isLetter(expression[position]);
	}
	  
	public static boolean isDigit() throws Exception {
		return Character.isDigit(expression[position]); 
	}
	  
	public static boolean isPoint() throws Exception {
		return expression[position] == '.'; 
	}
	 
	public static boolean isFunctionSymbol() throws Exception {
		byte c = expression[position];
	 	return Character.isLetterOrDigit(c) || c == '_'; 
	}
	
	/** Получить значения оператора/функции, константное выражение (токен), переместить позицию курсора 
	 *  @return OPERATOR -- результат вычисления оператора */
	public static Helper.OPERATOR getToken() throws Exception {
		int i;
		
		if(position == expression.length-1){ // Дошли до конца строки выражения
			operator = Helper.OPERATOR.END;
		}
		else if ((i = Helper.serviceSymbolString.indexOf(expression[position])) != -1 ){ // Если встретился один из символов "+-*/()[]{},", то устанавливаем оператор или скобки
			position++;
			operator = Helper.OPERATOR.values()[i];
		}
		else if(isLetter()){ // Иначе ищем по наименованию функций
			for(i = position++; isFunctionSymbol(); position++); // Смещаем курсор позиции, пока символы удовлетворяют правилам именования функций
			String token = new String(expression, i, position-i);// Выбираем подстроку названия функции
     
			try {
			  	operator = Helper.OPERATOR.valueOf(token); // Пробуем получить ID функции по её названию
			  	i = operator.ordinal();
			  	
			  	if(i < Helper.OPERATOR.SIN.ordinal() || i > Helper.OPERATOR.MAX.ordinal()){ 
			  		throw new IllegalArgumentException(); // Если ID функции вне диапазона SIN..MAX, генерируем ошибку
			  	}		  	
			}
			catch (IllegalArgumentException _ex){ // Возможно, это одно из константных математических выражений
				try{
					tokenValue = Helper.CONSTANT_VALUE[Helper.CONSTANT_NAME.valueOf(token).ordinal()];
					operator = Helper.OPERATOR.NUMBER;
				}
				catch (IllegalArgumentException ex){
					throw new Exception("Ошибка: Неизвестное ключевое слово");
				}
			}
		}
		else if(isDigit() || isPoint()){ // Пробуем получить числовое значение
			for(i = position++ ;
				isDigit() || isPoint()
				|| expression[position] == 'E' 
				|| expression[position-1] == 'E' && "+-".indexOf(expression[position])!= -1;
				position++);
			
			tokenValue = Double.parseDouble(new String(expression, i, position-i));
			operator = Helper.OPERATOR.NUMBER;
		}
		else{
			throw new Exception("Ошибка: Неизвестный символ в " + expression);
		}
		return operator;
	}      

	/** Разбор операций сложения/вычитания
	 * @return StructNode */
	public static StructNode parse() throws Exception {
		StructNode node = parse1();
		while(operator == Helper.OPERATOR.PLUS || operator == Helper.OPERATOR.MINUS){
			StructNode node1 = node;
			node = new StructNode();
			init(node, operator, node1);
			getToken();
			if(operator == Helper.OPERATOR.PLUS || operator == Helper.OPERATOR.MINUS){
				throw new Exception("Ошибка: Указывать последовательно два оператора недопустимо, попробуйте использовать скобки");
			}
			node.right = parse1();
		}
		return node;
	}
   
	/** Разбор математических операторов 
	 * @return StructNode */
	public static StructNode parse1() throws Exception {
		StructNode node = parse2();
		while(operator == Helper.OPERATOR.MULTIPLY
				|| operator == Helper.OPERATOR.DIVIDE
				|| operator == Helper.OPERATOR.MOD
				|| operator == Helper.OPERATOR.POW1
				|| operator == Helper.OPERATOR.OR
				|| operator == Helper.OPERATOR.AND){
			StructNode node1 = node;
			node = new StructNode();
			init (node, operator, node1);
			
			getToken();
			if(operator == Helper.OPERATOR.PLUS || operator == Helper.OPERATOR.MINUS){
				throw new Exception("Ошибка: Указывать последовательно два оператора недопустимо, попробуйте использовать скобки");
			}
			node.right=parse2();		
		}
		return node;
	}	

	/** Разбор унарных операторов
	 * @return StructNode */
	public static StructNode parse2() throws Exception {
		StructNode node;
		if(operator == Helper.OPERATOR.MINUS){
			getToken();
			node = new StructNode();
			init(node, Helper.OPERATOR.UNARY_MINUS, parse3());
		}
		else{
			if(operator == Helper.OPERATOR.PLUS){
				getToken();
			}
			node = parse3();
		}
		return node;      
	}

	/** Разбор функций и заключённых в скобки выражений + определение аргументов при операторах
	 * @return StructNode */
	public static StructNode parse3() throws Exception {
		StructNode node;
		Helper.OPERATOR openBracket;
		
		if(operator.ordinal() >= Helper.OPERATOR.SIN.ordinal()
		&& operator.ordinal() <= Helper.OPERATOR.MAX.ordinal()){
			
			int arguments = 0;
			if( operator.ordinal() >= Helper.OPERATOR.POW.ordinal()
			&& operator.ordinal() <= Helper.OPERATOR.MAX.ordinal() ){
				arguments = 2;
			}
			else{
				arguments = operator == Helper.OPERATOR.RANDOM ? 0:1;
			}
  			
			node = new StructNode();
			init(node, operator);
			openBracket = getToken();
			
			if(operator != Helper.OPERATOR.LEFT_BRACKET
				&& operator != Helper.OPERATOR.LEFT_SQUARE_BRACKET
				&& operator != Helper.OPERATOR.LEFT_CURLY_BRACKET ){
				throw new Exception("Ошибка: Нет открывающей скобки");
			}
			getToken();
      
			if(arguments > 0){
				node.left = parse();
	      
				if(arguments > 1){
					if(operator != Helper.OPERATOR.COMMA){
						throw new Exception("Ошибка: Пропущена запятая");
					}
					getToken();
					node.right = parse();
				}
			}
			checkBrackets(openBracket); // проверка скобок
		}
		else{
			switch(operator){
				case NUMBER:
					node = new StructNode();
					init(node);
				break;
				case LEFT_BRACKET:
				case LEFT_SQUARE_BRACKET:
				case LEFT_CURLY_BRACKET:
					openBracket = operator;
					getToken();
					node = parse();
					checkBrackets(openBracket); // проверка скобок
				break;
				default:
					throw new Exception("Ошибка: Аргумент неизвестен или отсутствует");
			}
		}
		getToken();
		return node;
	}

	/** Проверка скобок 
 	@param openBracket -- оператор открывающей скобки */
	public static void checkBrackets(Helper.OPERATOR openBracket) throws Exception {
		if(openBracket == Helper.OPERATOR.LEFT_BRACKET
			&& operator != Helper.OPERATOR.RIGHT_BRACKET
		|| openBracket == Helper.OPERATOR.LEFT_SQUARE_BRACKET
			&& operator != Helper.OPERATOR.RIGHT_SQUARE_BRACKET
		|| openBracket == Helper.OPERATOR.LEFT_CURLY_BRACKET
			&& operator != Helper.OPERATOR.RIGHT_CURLY_BRACKET){
			throw new Exception("Ошибка: Нет закрывающей скобки");
		}
	}
	
	/** Вычиселние математического выражения
	 * @param StructNode node математическое выражение
	 * @return результат вычисления */
	public static double nodeCalculate(StructNode node) throws Exception {
		
		double left = (node.left == null) ? 0:nodeCalculate(node.left);
		double right = (node.right == null) ? 0:nodeCalculate(node.right);
		
		switch(node.operator) {
			case NUMBER: return node.value;

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
  	
			case MIN: return Math.min(left, right);

			case MAX: return Math.max(left, right);
      	
			default: throw new Exception("Ошибка вычисления выражения");
		}
	}	  
  
	/** Компиляция:
	 *  - форматирование исходного выражения -- приведение к заглавным буквам, исключение пробелов и табуляций;
	 *  - построение дерева подвыражений, с корневым элементом root. 
	 *  @param expression -- исходное математическое выражение.*/
	public static StructNode compile(String expression) throws Exception {
		position = 0; // Позиция курсора
		StructNode node;
			// Убрать пробелы
		String s = expression.toUpperCase(); 
		String from[] = {"\s", "\t", "\n", "\r", "\f", "\b"};
		for(int i = 0; i < from.length; i++){
			s = s.replace(from[i], "");
		}
		ExpressionCalculatorFunc.expression=(s+'\0').getBytes();
		
		getToken();
		if(operator == Helper.OPERATOR.END){
			throw new Exception("Ошибка: Отсутствует окончание выражения");
		}
		node = parse();
		if(operator!= Helper.OPERATOR.END){
			throw new Exception("Ошибка: Отсутствует окончание выражения");
		}
		return node;
	}
	
	/** Вычисление выражения node
	 * @param node -- исходное математическое выражение
	 * @return результат вычисления */
	public static double calculate(StructNode node) throws Exception {
		if(node == null){
			throw new Exception("Ошибка: Перед вызовом функции calculate() необходим вызов функции compile()");
		}
		return nodeCalculate(node);
	}

	/**	Вычисление выражения
	 *	@param s -- математическое выражение
	 *	@return результат вычисления выражения */
	public static double calculateDouble(String s) throws Exception {
		ExpressionCalculator calculator = new ExpressionCalculator(); 
		calculator.compile(s);
		return calculator.calculate();
	}

	/**	Вычисление выражения
	 *	@param s -- математическое выражение
	 *	@return результат вычисления выражения */
	public static String calculate(String s) throws Exception {
		return new DecimalFormat(Helper.decimalFormat).format(calculateDouble(s));
	}	
}

