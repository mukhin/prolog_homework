package calculator;

import java.util.Scanner;

public class DijkstraCalculator {
	
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        IO.apply(() -> "Введите выражение:")
                .mapToVoid(System.out::println)
                .map(v -> scanner.nextLine())
                .map(Parser::parse)
                .map(Parser::toRPN)
                .map(Calculator::calculate)
                .map(Helper::calcToString)
                .mapToVoid(System.out::println)
                .unsafeRun();
    }
}
