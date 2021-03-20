package calculator;

/**
 * Монада State
 *
 * @param <S>
 * @param <V>
 */
public class StateTuple<S, V> {
    final S state;
    final V value;

    public StateTuple(S state, V value) {
        this.state = state;
        this.value = value;
    }
}
