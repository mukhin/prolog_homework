package test;

import org.junit.Assert;
import org.junit.Test;

import calculator.*;

public class DijkstraCalculatorTests {
    @Test
    public void simpleTest() {

        var result = IO.apply(() -> "1+4/2")
                .map(Parser::parse)
                .map(Parser::toRPN)
                .map(Calculator::calculate)
                .map(Helper::calcToString)
                //.mapToVoid(System.out::println)
                .unsafeRun();

        Assert.assertEquals("1 4 + 2 / = 2.5 ", result);
    }	
}
