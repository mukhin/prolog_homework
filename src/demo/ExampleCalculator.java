package demo;

import calculator.*;

public class ExampleCalculator {//В качестве математического выражения используется строка входного аргумента 

	public static void main(String[] args) throws Exception {
        IO.apply(() -> args[0])
        .map(Parser::parse)
        .map(Parser::toRPN)
        .map(Calculator::calculate)
        .map(Helper::calcToString)
        .mapToVoid(System.out::println)
        .unsafeRun();
	}
}