package calculator;

import java.util.function.*;


public class IO<A> {
	private final Effect<A> effect;
	
	private IO(Effect<A> effect) {
		this.effect = effect;
	}
	
	public A unsafeRun() {
		return effect.run();
	}
	
	public <B> IO<B> flatMap(Function<A, IO<B>> function) {
		return IO.apply(()->function.apply(effect.run()), unsafeRun());
	}
	
	public static <T> IO<T> apply(Effect<T> effect) {
		return new IO<>(effect);
	}
	
	public <B> IO<B> map(Function<A, B> function){
		return this.flatMap(result->IO.apply(()->{
			function.accept(result);
			return null;
		}));
	}
	
	public IO<Void> mapToVoid(Consumer<A> function){
		return this.flatMap(result->IO.apply(()->{
			function.accept(result);
			return null;
		}));
	}
	
	public IO<A> peek(Consumer<A> function){
		return this.flatMap(result->IO.apply(()->{
			function.accept(result);
			return null;			
		}));
	}
}
