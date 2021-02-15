package demo;

import calculator.ExpressionCalculator;

public class Example1 {

  public static void main(String[] args){

    try {
      String v=ExpressionCalculator.calculate(args[0]);
      System.out.println(v);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}