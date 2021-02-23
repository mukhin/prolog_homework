package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({DijkstraCalculatorTests.class, DijkstraAlgorithmCalculatorSingleTests.class})
public class AllTests {

}
