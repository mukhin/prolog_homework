package calculator;

import java.text.DecimalFormat;

/** Калькулятор математических выражений в парадигме ООП
 * 	ExpressionCalculator -- основной класс приложения,
 * 	Node -- класс математического выражения, из нод строится дерево математических выражений для выполнения вычислений,
 *  Helper -- содержит вспомогательные множества, функции и константы.*/
public class ExpressionCalculator {
		
	private Node root = null;		 // Корневой узел дерева исходного выражения
	private String expression; 		 // Исходное выражение
	private Double tokenValue; 		 // Значение токена для числа или константного выражения
	private Helper.OPERATOR operator;// Оператор/скобки/разделитель/функция в позиции курсора         
	private Integer position; 		 // Позиция курсора в исходном выражении
	
	// Проверка символов выражения
	private boolean isLetter() {
		return Character.isLetter(expression.charAt(position));
	}
	  
	private boolean isDigit() {
		return Character.isDigit(expression.charAt(position)); 
	}
	  
	private boolean isPoint() {
		return expression.charAt(position) == '.'; 
	}
	 
	private boolean isFunctionSymbol() {
		char c = expression.charAt(position);
	 	return Character.isLetterOrDigit(c) || c == '_'; 
	}
	
	/** Получить значения оператора/функции,
	 *  константное выражение (токен),
	 *  переместить позицию курсора 
	 *  @return Helper.OPERATOR */
	private Helper.OPERATOR getToken() throws Exception {
		int i = 0;
		
		if(position == expression.length() - 1){ // Проверка конца строки выражения
			operator = Helper.OPERATOR.END;
		}	// Проверка математических операторов и скобок
		else if ((operator = Helper.OPERATOR.findOPERATOR(expression.charAt(position))) != null ) {
			position++;
		}
		else if(isLetter()){ // Ищем название функции
				// Смещаем курсор позиции, пока символы удовлетворяют правилам именования функций
			for(i = position++; isFunctionSymbol(); position++);
			String token = new String(expression.toCharArray(), i, position-i);// Выбираем подстроку названия функции
     
			try{
			  	operator = Helper.OPERATOR.valueOf(token); // Пробуем получить ID функции по её названию
			  	i = operator.ordinal();
			  	
			  	if(i < Helper.OPERATOR.SIN.ordinal() || i > Helper.OPERATOR.MAX.ordinal()){ 
			  		throw new IllegalArgumentException("Ошибка Недопустимое значение вместо функции"); // Если ID функции вне диапазона SIN..MAX, генерируем ошибку
			  	}		  	
			}
			catch (IllegalArgumentException _ex){ // Проверяем, что это одно из константных математических выражений
				try{
					tokenValue = Helper.MathConstant.valueOf(token).getValue();
					operator = Helper.OPERATOR.NUMBER;
				}
				catch (IllegalArgumentException ex){
					throw new Exception("Ошибка: Неизвестное наименование функции " + token);
				}
			}
		}
		else if(isDigit() || isPoint()){ // Пробуем получить числовое значение
			for(i=position++;
				isDigit() || isPoint()
				|| expression.charAt(position)=='E' 
				|| expression.charAt(position-1)=='E' && "+-".indexOf(expression.charAt(position))!= -1;
				position++);
			String strValue = new String(expression.toCharArray(), i, position-i);
			
			try {
				tokenValue = Double.parseDouble(strValue);
				operator = Helper.OPERATOR.NUMBER;
			}
			catch (IllegalArgumentException ex) {
				throw new Exception("Ошибка: Значение " + strValue + " не является числом");
			}
		}
		else{
			throw new Exception("Ошибка: Неизвестный символ в " + expression);
		}
		
		return operator;
	}      

	/** Разбор операций сложения/вычитания
	 * @return node -- результат разбора выражения */
	private Node parse() throws Exception {
		Node node = parse1();
		
		while(operator == Helper.OPERATOR.PLUS || operator == Helper.OPERATOR.MINUS){
			node = new Node(operator, node);
			getToken();
			if(operator == Helper.OPERATOR.PLUS || operator == Helper.OPERATOR.MINUS){
				throw new Exception("Ошибка: Указывать последовательно два оператора недопустимо, попробуйте использовать скобки");
			}
			node.right=parse1();
		}
		return node;
	}
   
	/** Разбор умножения/деления 
	 * @return node -- результат разбора выражения */
	private Node parse1() throws Exception {
		Node node = parse2();
		
		while(operator == Helper.OPERATOR.MULTIPLY
				|| operator == Helper.OPERATOR.DIVIDE
				|| operator == Helper.OPERATOR.MOD
				|| operator == Helper.OPERATOR.POW1
				|| operator == Helper.OPERATOR.OR
				|| operator == Helper.OPERATOR.AND){
			node = new Node(operator, node);
			getToken();
			if(operator == Helper.OPERATOR.PLUS || operator == Helper.OPERATOR.MINUS){
				throw new Exception("Ошибка: Указывать последовательно два оператора недопустимо, попробуйте использовать скобки");
			}
			node.right=parse2();
		}
		return node;
	}	

	/** Определение унарных операторов 
	 * @return node -- результат разбора выражения */
	private Node parse2() throws Exception {
		Node node;
		
		if(operator == Helper.OPERATOR.MINUS){
			getToken();
			node = new Node(Helper.OPERATOR.UNARY_MINUS, parse3());
		}
		else{
			if(operator == Helper.OPERATOR.PLUS){
				getToken();
			}
			node = parse3();
		}
		return node;      
	}

	/** Разбор функций и выражений в скобках
	 * @return Node */
	private Node parse3() throws Exception {
		Node node;
		Helper.OPERATOR openBracket;
		int arguments = operator.getArguments(); // Количество аргументов оператора
		
		if(arguments >=0 ){// Парсинг функций
  			
			node = new Node(operator);
			openBracket = getToken(); // Левый оператор/открывающая скобка
			
			if(operator != Helper.OPERATOR.LEFT_BRACKET
				&& operator != Helper.OPERATOR.LEFT_SQUARE_BRACKET
				&& operator != Helper.OPERATOR.LEFT_CURLY_BRACKET ){
				throw new Exception("Ошибка: Нет открывающей скобки");
			}
			
			getToken();
      
			if(arguments > 0){
				node.left = parse();
	      
				if( arguments == 2 ){
					if(operator != Helper.OPERATOR.COMMA){
						throw new Exception("Ошибка: Пропущена запятая");
					}
					getToken();
					node.right = parse();
				}
			}
			checkBeackets(openBracket); // проверка скобок
		}
		else{ // Парсинг числовых значений и выражений в скобках
			switch(operator){
				case NUMBER:
					node = new Node(operator, tokenValue);
				break;
				case LEFT_BRACKET:
				case LEFT_SQUARE_BRACKET:
				case LEFT_CURLY_BRACKET:
					openBracket = operator; // Левый оператор/открывающая скобка
					getToken(); // Получить правый оператор/закрывающую скобку
					node = parse();
					checkBeackets(openBracket); // проверка скобок
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
	private void checkBeackets(Helper.OPERATOR openBracket) throws Exception {
		if(openBracket == Helper.OPERATOR.LEFT_BRACKET && operator != Helper.OPERATOR.RIGHT_BRACKET
		|| openBracket == Helper.OPERATOR.LEFT_SQUARE_BRACKET && operator != Helper.OPERATOR.RIGHT_SQUARE_BRACKET
		|| openBracket == Helper.OPERATOR.LEFT_CURLY_BRACKET && operator != Helper.OPERATOR.RIGHT_CURLY_BRACKET){
			throw new Exception("Нет закрывающей скобки");
		}
	}
	
	/** Компиляция:
	 *  - форматирование исходного выражения -- приведение к заглавным буквам, исключение пробелов и табуляций;
	 *  - построение дерева подвыражений, с корневым элементом root. 
	 *  @param expression -- исходное математическое выражение. */
	public void compile(String expression) throws Exception {
		this.position=0;// Сброс позиции курсора
						// Приводим к заглавным буквам, убираем пробельные символы, добавляем символ завершения строки
		this.expression = expression.replaceAll("\\s+", "").toUpperCase() + '\0';
		
		getToken();
		if(operator == Helper.OPERATOR.END){
			throw new Exception("Ошибка: Отсутствует выражение");
		}
		root = parse();
		if(operator!= Helper.OPERATOR.END){
			throw new Exception("Ошибка: Отсутствует окончание выражения");
		}
	}
  
	/** Вычисление выражения */
	public double calculate() throws Exception {
		if(root == null){
			throw new Exception("Ошибка: Перед вызовом функции calculate() необходим вызов функции compile()");
		}
		return root.calculate();
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

