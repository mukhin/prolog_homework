package calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** В качестве значений операндов можно использовать многоразрядные числа. */
public class DijkstraAlgorithmCalculator {
	
	private static double result = 0.0; // Результат вычисления
	
	/** Получить результат вычисления
	 * 	@return result */
	public static double getResult() {
		return result;
	}
	
	/** Проверка на соответствие математическому оператору
	 *	@param c символ
	 * 	@return результат сравнения */
	public static boolean isOperator (final char c) {
		return (c == '+' || c == '-' || c == '/' || c == '*' || c == '!' || c == '%');
	}
	
	/** Проверка на соответствие цифровому символу
	 * @param c символ
	 * @return результат сравнения */
	public static boolean isNumeric (final char c) {
		return (c >= '0' && c <= '9');
	}
	
	/** Определение приоритета оператора
	 *	@param op оператор
	 *	@return приоритет */
	public static int opPriority(final char op) {//throws Exception 
	    switch(op) {
	        case '!':
	        	return 3;

	        case '*':
	        case '/':
	        case '%':
	        	return 2;

	        case '+':
	        case '-':
	        	return 1;
	        
	        default: {
	        	System.out.println("Неизвестный оператор: \"" + op + "\"");//throw new Exception("Неизвестный оператор");
	        	return 0;
	        }
	    }
	}
	
	/** Определяет левый или правый оператор?
	 *	@param op оператор
	 * 	@return результат: левый == true; правый == false */
	public static boolean opLeftOrRight(final char op) {//throws Exception 
	    switch(op) {
	        	// Левоассоциативные операторы
	        case '*':
	        case '/':
	        case '%':
	        case '+':
	        case '-':
	        	return true;
	        	// Правоассоциативные операторы
	        case '!':
	        	return false;
	        
	        default: {
	        	System.out.println("Неизвестный оператор: \"" + op + "\"");//throw new Exception("Неизвестный оператор");
	        	return false;
	        }
	    }
	}
	
	/** Вычисление количества операндов для оператора
	 *	@param op оператор
	 *	@return количество операндов */
	public static int opArgCount(final char op) {
	    switch(op) {
	        case '*':
	        case '/':
	        case '%':
	        case '+':
	        case '-':
	        	return 2;
	        case '!':
	        	return 1;
	    }
		return 0;
	}
	
	/** Вычисление выражения для оператора
	 * 	@param op1 левый операнд
	 * 	@param op2 правый операнд
	 *	@param operator оператор
	 *	@return результат вычисления */
	public static double opCalculate(final double op1, final double op2, final char operator ) {
		double result = 0.0;
		switch (operator) {
			case ('+'):
				result = op1 + op2;
				break;
			case ('-'):
				result = op1 - op2;
				break;
			case ('*'):
				result = op1 * op2;
				break;
			case ('/'):
				result = op1 / op2;
				break;
			case ('%'):
				result = op1 % op2;
				break;
			case ('!'):
				result = -op1;
				break;
		}
		return result;
	}
	
	/** Конвертация массива символов в число с типом double
	 * 	@param numArray массив символов
	 	@return результат в формате double */
	public static double convertArrayToDouble(final char[] numArray) {
		double result = 0.0;
		int length = numArray.length - 1;
		for(int i = length, j = 1; i >= 0; i--) { //result = 1*numArray[length]+10*numArray[length-1]+...
				//System.out.println("numCalculate, i = " + i + " length = " + length + " numArray[" + (i) + "] = " + numArray[i]);
			result += j * (numArray[i] - '0');
			j *= 10;
		}
		return result;
	}
	
	/** Заполнить массив output значениями массива input, начиная с позиции input_position
	 * @param input массив исходных значений
	 * @param output массив для заполнения
	 * @param input_position смещение в исходном массива
	 * @param length 
	 * @return результат выполнения операции */
	public static boolean fillArray(final char[] input, char[] output, int input_position) {
		
		if (input.length == 0 //Проверка размерности массивов
			|| output.length == 0
			|| input.length <= (input_position + output.length - 2)) {
			return false;
		}
		
		for(int i = 0; i < output.length; i++) {
			output[i] = input[input_position-output.length+i];
		}
		return true;
	}
	
	/** Разбор исходного математического выражения, перевод в обратную польскую нотацию.
	 * 	@param input входная строка выражения
	 * 	@param output выходная очередь
	 * 	@return результат разбора true -- операция выполнена успешно, false -- операция завершилась с ошибкой */
	public static boolean expressionParser(final char[] input, char[][] output) {//throws Exception 
	    final int input_end = input.length; //Размер входной очереди
	    int input_position = 0; //Позиция во входной очереди
	    int output_position = 0;//Позиция в выходной очереди
	    int stack_position = 0;	//Позиция в стеке операторов
	    int num_position = 0;
	    char c, sc; //Служебные переменные, значения входной очереди и стека в текущей позиции
	    
	    char[] stack = new char[input_end]; //Стек операторов
	    
	    boolean num_flag = false;
	    
	    while(input_position < input_end) { //Основной цикл разбора 
	        c = input[input_position];
	        if(c != ' ') {
	            if(isNumeric(c)) { // Если значение, инкрементировать счётчик цифр числового значения
	                num_position++; num_flag = true;
	            }
	            else if(isOperator(c)) {	            	
	            	if (num_flag == true) {
	            		char [] num_stack = new char[num_position];
	            		if (fillArray(input, num_stack, input_position)) {
	            			output[output_position] = num_stack; ++output_position;
	            			num_flag = false; num_position = 0;
	            		}
	            		else {
	            			return false;
	            		}
	            	}
	            	
	                while(stack_position > 0) {
	                    sc = stack[stack_position - 1];

	                    if(isOperator(sc)
	                    	&& ((opLeftOrRight(c) && (opPriority(c) <= opPriority(sc)))
	                    	|| (!opLeftOrRight(c) && (opPriority(c) < opPriority(sc))))) {
	                    	
	                    	char [] num_stack = new char[1]; num_stack[0] = sc;
	                        output[output_position] = num_stack; ++output_position;
	                        stack_position--;
	                    }
	                    else {
	                        break;
	                    }
	                }
	              
	                stack[stack_position] = c; //Положить в стек оператор op1
	                ++stack_position;
	            }
	            else if(c == '(') { //Если левая скобка, положить в стек.
	                stack[stack_position] = c;
	                ++stack_position;
	            }
	            else if(c == ')') { //Если правая скобка:
	            	
	            	if (num_flag == true) {
	            		char [] num_stack = new char[num_position];
	            		if (fillArray(input, num_stack, input_position)) {
	            			output[output_position] = num_stack; ++output_position;
	            			num_flag = false; num_position = 0;
	            		}
	            		else {
	            			return false;
	            		}
	            	}
	            	
	                boolean isLeftBracket = false;
	                
	                //До появления на вершине стека левой скобки перекладывать операторы из стека в очередь вывода.
	                while(stack_position > 0) {
	                    sc = stack[stack_position - 1];
	                    
	                    if(sc == '(') {
	                    	isLeftBracket = true;
	                        break;
	                    }
	                    else {
	                    	char [] num_stack = new char[1]; num_stack[0] = sc;
	                        output[output_position] = num_stack; ++output_position;
	                        stack_position--;
	                    }
	                }
	                
	                if(!isLeftBracket) { //Если стек кончился до появления левой скобки.
	                	System.out.println("Ошибка: Пропущена левая скобка");//throw new Exception("Пропущена левая скобка");
	                	return false;
	                }
	                stack_position--; //Пропустить левую скобку.
	            }
	            else {
	            	System.out.println("Ошибка: Неизвестный символ"); //throw new Exception("Неизвестный символ");
	            	return false;
	            }
	        }
	        ++input_position;
	    }
	    // Когда входная очередь пуста, добавить в выходную очередь содержимое стека операторов:
	    while(stack_position > 0) {
	        sc = stack[stack_position - 1];
	        
	        if(sc == '(' || sc == ')') {
	        	System.out.println("Ошибка: Пустые скобки");//throw new Exception("Пропущены значения в скобках");
	            return false;
	        }
        	char [] num_stack = new char[1];
        	num_stack[0] = sc;
            output[output_position] = num_stack; ++output_position;
	        --stack_position;
	    }

	    return true;
	}
	
	/** Вычисление выражения в обратной польской нотации
	 * 	@param input массив, каждый элемент которого -- это массив цифр числа, либо оператор
	 * 	@return результат выполнения true -- операция выполнена успешно, false -- операция завершилась с ошибкой */
	public static boolean expressionCalc(final char[][] input) {
	    
		final int input_end = input.length; //Длина входной очереди
	    int input_position = 0; //Позиция во входной очереди
	    int stack_position = 0;	//Позиция в стеке результата
	    char[] c; //Служебная переменная, -- значение элемента входной очереди
	    
	    result = 0.0; //Перед очередным вычислением результат обнуляется
	    
	    double[] stack = new double[input_end]; //Стек результирующих значений
	
	    while(input_position < input_end) { //Пока не достигнут конец очереди
	    	
	        c = input[input_position]; //Получить следующее значение
	        
	        if(isNumeric(c[0])) { //Если массив начинается с цифры, преобразовать в число, поместить в стек
	            stack[stack_position] = convertArrayToDouble(c);
	            ++stack_position;
	        }
	        else if(isOperator(c[0])) { //Если оператор, определить операнды, вычислить подвыражение	
	        	
				int nargs = opArgCount(c[0]);
				if(stack_position < nargs) {
					System.out.println("Ошибка: недостаточно аргументов: " + stack_position + " Ожидалось: " + nargs);
					return false;
				}

				if(nargs == 1) {
					result = opCalculate(stack[stack_position - 1], 0, c[0]);
				}
				else {
					result = opCalculate(stack[stack_position - 2], stack[stack_position - 1], c[0]);
				}
				
				stack_position -= nargs;
	            stack[stack_position] = result; //Результат вычисления помещается в стек на место первого операнда подвыражения
	            
	            ++stack_position;
	        }
	        ++input_position;
	    }

		if(stack_position == 1) {//Если в стеке осталось одно значение, оно и есть результат вычисления исходного выражения.
			stack_position--;
			result = stack[stack_position];
			return true;
		}

		return false;
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
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
	    String expression = prepareExpressionString(reader.readLine()); //Исходное выражение
	    
	    //System.out.println(expression);
		
			//Преобразование входного выражения в массив символов {'(','1','+','1',')'};
		final char[] input = expression.toCharArray();
		
		char[][] output = new char[input.length][input.length]; //Выходная очередь 
		result = 0.0;
		
		if(DijkstraAlgorithmCalculator.expressionParser(input, output)) {
			if (DijkstraAlgorithmCalculator.expressionCalc(output)) {
				System.out.print("Результат: ");
				for (int i = 0; i < output.length; i++) {
					System.out.print(String.valueOf(output[i]));
				}
				System.out.println("=" + result);
			}
			else {
				System.out.print("Ошибка вычисления: ");
				for (int i = 0; i < output.length; i++) {
					System.out.print(String.valueOf(output[i]));
				}
				System.out.println("");
			}
		}
		else {
			System.out.println("Ошибка разбора: " + String.valueOf(input));
		}
	}
}
