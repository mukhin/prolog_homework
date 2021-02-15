package test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	public static void main(String[] args) {
		System.out.println("НАЧАЛО ТЕСТИРОВАНИЯ КАЛЬКУЛЯТОРА\n");
		Result result = JUnitCore.runClasses(AllTests.class);
		for(Failure failure: result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(result.wasSuccessful() ? "Тестирование прошло успешно" : "Тестирование завершено с ошибками");
	}
}
