package calculator;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser {
	
    public static Deque<String> toRPN(final List<String> tokens) {

        State<Tuple2<Deque<String>, Deque<String>>, String> state = State.of(s -> new StateTuple<>(s, ""));

        Iterator<String> iterator = tokens.iterator();

        StateTuple<Tuple2<Deque<String>, Deque<String>>, String> stateTuple =
                traverse(state, iterator)
                        .flatMap(s -> transferRest())
                        .apply(Tuple2.of(Helper.newDeque(), Helper.newDeque()));

        return stateTuple.state.output;

    }

    public static State<Tuple2<Deque<String>, Deque<String>>, String> traverse(State<Tuple2<Deque<String>, Deque<String>>, String> state, Iterator<String> iterator) {
        if (!iterator.hasNext()) return state;
        String token = iterator.next();
        return traverse(state.flatMap(s -> processToken(token)), iterator);
    }

    private static State<Tuple2<Deque<String>, Deque<String>>, String> transferRest() {
        return State.modify(t -> {
            while (!t.stack.isEmpty()) {
                t.output.push(t.stack.pop());
            }
            return t;
        });
    }

    private static State<Tuple2<Deque<String>, Deque<String>>, String> processToken(String token) {
        return State.modify(t -> {
            if ("(".equals(token)) {
                t.stack.push(token);
            } else if (")".equals(token)) {
                while (!"(".equals(t.stack.peek())) {
                    t.output.push(t.stack.pop());
                }
                t.stack.pop();
            } else if (Helper.isOperator(token)) {
                Predicate<String> predicate = Helper.associativity(token) == Helper.Associativity.LEFT ?
                        op -> Helper.getPriority(op) >= Helper.getPriority(token) : op -> Helper.getPriority(op) > Helper.getPriority(token);
                while (!t.stack.isEmpty() && predicate.test(t.stack.peek())) {
                    t.output.push(t.stack.pop());
                }
                t.stack.push(token);
            } else {
                t.output.push(token);
            }
            return t;
        });
    }
    
    public static List<String> parse(String expression) {
        List<String> tokens = new ArrayList<>();
        Pattern pattern = Pattern.compile("([0-9]*)([+\\-*%/^]*)([()]*)");
        Matcher matcher = pattern.matcher(expression);
        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                String group = matcher.group(i);
                if (group.isEmpty()) continue;
                tokens.add(group.trim());
            }
        }
        return tokens;
    }
}
