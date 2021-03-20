package calculator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/** Вспомогательный класс для предоставления общедоступных констант и функций */
public class Helper {

	public static boolean isNumeric(String token) {
		try {
			Integer.parseInt(token);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static boolean isLeftBracket(String token) {
		return token.equals("(");
	}

	public static boolean isRightBracket(String token) {
		return token.equals(")");
	}

	public static boolean isOperator(String token) {
		return (OPERATOR.findOPERATOR(token) != null);
	}
	
    public static int getPriority(final String op) {
    	return (OPERATOR.findOPERATOR(op) != null) ?
    			OPERATOR.findOPERATOR(op).getPriority():0;
    }

    public enum Associativity {
        LEFT, RIGHT
    }

    public static Associativity associativity(final String op) {
    	return (OPERATOR.findOPERATOR(op) != null) ?
    			OPERATOR.findOPERATOR(op).getAssociativity():Associativity.LEFT;
    }
	
    public static String calcToString(Stack<String> strings) {
        return strings.stream().reduce("", (s1, s2) -> s2 + " " + s1);
    }
    
    public static Deque<String> newDeque() {
        return new ArrayDeque<>();
    }
    
	public enum OPERATOR {
	    PLUS(2, 2, Associativity.LEFT) {
	        @Override
	        public double calculate(final double op1, final double op2) {
	            return op2+op1;
	        }
	    },
	    MINUS(2, 2, Associativity.LEFT) {
	        @Override
	        public double calculate(final double op1, final double op2) {
	            return op2-op1;
	        }
	        
	    },
	    MULTIPLY(3, 2, Associativity.LEFT) {
	        @Override
	        public double calculate(final double op1, final double op2) {
	            return op2*op1;
	        }
	    },
	    DIVIDE(3, 2, Associativity.LEFT) {
	        @Override
	        public double calculate(final double op1, final double op2) {
	            return op2/op1;
	        }
	    },	
	    MOD(3, 2, Associativity.LEFT) {
	        @Override
	        public double calculate(final double op1, final double op2) {
	            return op2%op1;
	        }
	    };
		
		private final int arguments;
		private final int priority;
		Associativity associativity;

	    public abstract double calculate(final double op1, final double op2);

		/** Соответствие символьного представления оператора и объекту enum OPERATOR*/
		private static Map<String, OPERATOR> operatorMap;
		
		static {
			operatorMap = new HashMap<>();
			operatorMap.put("+", PLUS);
			operatorMap.put("-", MINUS);
			operatorMap.put("*", MULTIPLY);
			operatorMap.put("/", DIVIDE);
			operatorMap.put("%", MOD);
		};
		
		public static OPERATOR findOPERATOR(String symbol) {
			return operatorMap.get(symbol);
		}
		
		public static Set<String> getOperators(){
			return operatorMap.keySet();
		}
		
		OPERATOR(int arguments, int priority, Associativity associativity) {
			this.arguments = arguments;
			this.priority = priority;
			this.associativity = associativity;
		}
		
		public int getArguments() {
			return arguments;
		}
		
		public int getPriority() {
			return priority;
		}
		
		public Associativity getAssociativity() {
			return associativity;
		}
	}
}
