package calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DijkstraAlgorithmCalculator {
	
	private static double result = 0.0;
	
	public static double getResult() {
		return result;
	}
	
	public static boolean isOperator (final char c) {
		return (c == '+' || c == '-' || c == '/' || c == '*' || c == '!' || c == '%');
	}
	
	public static boolean isNumeric (final char c) {
		return (c >= '0' && c <= '9');
	}
	
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
	
	public static double opCalculate(final double op1, final double op2, final char operation ) {
		double result = 0.0;
		switch (operation) {
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
	
	/***/
	public static boolean expressionParser(final char[] input, char[] output) {//throws Exception 
	    final int input_end = input.length; //Размер входной очереди
	    int input_position = 0; //Позиция во входной очереди
	    int output_position = 0;//Позиция в выходной очереди
	    int stack_position = 0;	//Позиция в стеке операторов
	    char c, sc; //Служебные переменные, значения входной очереди и стека в текущей позиции
	    
	    char[] stack = new char[input_end]; //Стек операторов
	    
	    while(input_position < input_end) {
	        c = input[input_position];
	        if(c != ' ') {
	            if(isNumeric(c)) { // Если значение, добавить в очередь вывода
	                output[output_position] = c; ++output_position;
	            }
	            else if(isOperator(c)) {
	                while(stack_position > 0) {
	                    sc = stack[stack_position - 1];

	                    if(isOperator(sc)
	                    	&& ((opLeftOrRight(c) && (opPriority(c) <= opPriority(sc)))
	                    	|| (!opLeftOrRight(c) && (opPriority(c) < opPriority(sc))))) {
	                        output[output_position] = sc; ++output_position;
	                        stack_position--;
	                    }
	                    else {
	                        break;
	                    }
	                }
	              
	                stack[stack_position] = c; // положить в стек оператор op1
	                ++stack_position;
	            }
	            else if(c == '(') {// Если токен - левая скобка, положить в стек.
	                stack[stack_position] = c;
	                ++stack_position;
	            }
	            else if(c == ')') { // Если токен - правая скобка:
	            	
	                boolean isLeftBracket = false;
	                
	                // До появления на вершине стека токена левой скобки перекладывать операторы из стека в очередь вывода.
	                while(stack_position > 0) {
	                    sc = stack[stack_position - 1];
	                    
	                    if(sc == '(') {
	                    	isLeftBracket = true;
	                        break;
	                    }
	                    else {
	                        output[output_position] = sc; ++output_position;
	                        stack_position--;
	                    }
	                }
	                // Если стек кончится до нахождения левой скобки, то была пропущена скобка.
	                if(!isLeftBracket) {
	                	System.out.println("Пропущена левая скобка");//throw new Exception("Пропущена левая скобка");
	                	return false;
	                }
	                stack_position--; // Пропустить левую скобку.
	            }
	            else {
	            	System.out.println("Неизвестный символ");//throw new Exception("Неизвестный символ");
	            	return false;
	            }
	        }
	        ++input_position;
	    }
	    // Когда не осталось токенов во входной очереди, разобрать содержимое стека:
	    while(stack_position > 0) {
	        sc = stack[stack_position - 1];
	        
	        if(sc == '(' || sc == ')') {
	        	System.out.println("Пропущены значения в скобках");//throw new Exception("Пропущены значения в скобках");
	            return false;
	        }
	        output[output_position] = sc; ++output_position;
	        --stack_position;
	    }

	  //  output[output_position] = 0;//Добавить ноль к строке
	    return true;
	}
	
	public static boolean expressionCalc(final char[] input) {
	    
	    result = 0.0;
		final int input_end = input.length; //Размер входной очереди
	    int input_position = 0; //Позиция во входной очереди
	    int stack_position = 0;	//Позиция в стеке результата
	    char c; //Значение входной очереди
	    
	    double[] stack = new double[input_end]; //Стек операторов
	
	    while(input_position < input_end) {// Пока не достигнут конец очереди
	    	
	        c = input[input_position]; // Прочитать следующее значение
	        
	        if(isNumeric(c)) { // Если цифра, поместить в стек
	            stack[stack_position] = (double) (c - '0');
	            ++stack_position;
	        }
	        else if(isOperator(c)) { // Если оператор  	
	        	
				int nargs = opArgCount(c);
				if(stack_position < nargs) {
					System.out.println("Ошибка: недостаточно аргументов: " + stack_position + " Ожидалось: " + nargs);
					return false;
				}

				if(nargs == 1) {
					result = opCalculate(stack[stack_position - 1], 0, c);
					stack_position--;
				}
				else {
					result = opCalculate(stack[stack_position - 2], stack[stack_position - 1], c);
	                stack_position -= 2;

				}

	            stack[stack_position] = result; //Результат вычислений помещается в стек
	            ++stack_position;
	        }
	        ++input_position;
	    }

		if(stack_position == 1) {//Если в стеке осталось одно значение, оно и есть результат вычисления.
			stack_position--;
			result = stack[stack_position];
			return true;
		}

		return false;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
	    String expression = reader.readLine(); 
		expression = expression.replace(" ", "").replace("(-", "(0-").replace("(+", "(0+");
		if (expression.charAt(0) == '-' || expression.charAt(0) == '+') {
			  expression = "0" + expression;
		}
	  
		final char[] input = expression.toCharArray(); //{'(','1','+','1',')'};
		
		char[] output = new char[input.length];
		result = 0.0;
		
		if(DijkstraAlgorithmCalculator.expressionParser(input, output)) {
			if (DijkstraAlgorithmCalculator.expressionCalc(output)) {
				System.out.println("Результат: " + String.valueOf(output) + "=" + result);
			}
			else {
				System.out.println("Ошибка вычисления: " + String.valueOf(output));
			}
		}
	}
}

