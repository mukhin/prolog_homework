package calculator;

/**
 * Монада Tuple2
 *
 * @param <V1>
 * @param <V2>
 */
public class Tuple2<V1, V2> {
    final V1 stack;
    final V2 output;

    private Tuple2(V1 value1, V2 value2) {
        this.stack = value1;
        this.output = value2;
    }

    public static <V1, V2>  Tuple2<V1, V2> of(V1 value1, V2 value2) {
        return new Tuple2<>(value1, value2);
    }

}
