import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	/**
	 * Main method.
	 *
	 * @param args IGNORED, kept for compatibility
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {

		if (args.length != 2) {
			System.out.println("Usage: TestRunner <logic type> <test type>\n");
			return;
		}

		if (args[0].equals("impl")) {
			Config.setLogicType(LogicType.IMPL);
			System.out.print("TESTING YOUR IMPLEMENTATION ");
		} else if (args[0].equals("buggy")) {
			Config.setLogicType(LogicType.BUGGY);
			System.out.print("TESTING BUGGY IMPLEMENTATION ");
		} else if (args[0].equals("solution")) {
			Config.setLogicType(LogicType.SOLUTION);
			System.out.print("TESTING SOLUTION IMPLEMENTATION ");
		} else {
			System.out.println("Usage: TestRunner <logic type> <test type>\n");
			return;
		}

		if (args[1].equals("junit")) {
			Config.setTestType(TestType.JUNIT);
			System.out.println("WITH PLAIN JUNIT\n");
		} else if (args[1].equals("jpf")) {
			Config.setTestType(TestType.JPF_ON_JUNIT);
			System.out.println("WITH JPF ON JUNIT\n");

			// Invoke JUnit on BeanCounterLogicTest to get all errors
			Result r = JUnitCore.runClasses(BeanCounterLogicTest.class);
			for (Failure f : r.getFailures()) {
				System.out.println(f.toString());
				// System.out.println(f.getTrace());
			}
			return;
		} else if (args[1].equals("jpftrace")) {
			Config.setTestType(TestType.JPF_ON_JUNIT);
			System.out.println("WITH JPF ON JUNIT WITH TRACING\n");

			// Invoke tests in BeanCounterLogicTest directly to get a trace
			BeanCounterLogicTest test = new BeanCounterLogicTest();
			test.setUp(); // @BeforeClass
			for (Method m : test.getClass().getDeclaredMethods()) {
				if (m.getName().indexOf("test") == 0) {
					m.invoke(test);
				}
			}
			test.tearDown(); // @AfterClass
			return;
		} else {
			System.out.println("\nUsage: TestRunner <logic type> <test type>\n");
			return;
		}

		assert (args[1].equals("junit"));

		ArrayList<Class> classesToTest = new ArrayList<Class>();

		// ADD ANY CLASSES YOU WISH TO TEST HERE
		classesToTest.add(GradeScopeTest.class);
		classesToTest.add(BeanCounterLogicTest.class);

		// For all test classes added, loop through and use JUnit
		// to run them.

		for (Class c : classesToTest) {
			System.out.println("[" + c.getName() + "]\n");
			Result r = JUnitCore.runClasses(c);

			// Print out any failures for this class.

			for (Failure f : r.getFailures()) {
				System.out.println(f.toString());
				// System.out.println(f.getTrace());
			}
			System.out.println("");
		}
	}
}
