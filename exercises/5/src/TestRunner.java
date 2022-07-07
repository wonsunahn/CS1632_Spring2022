import java.util.ArrayList;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	/**
	 * Main method.
	 *
	 * @param args IGNORED, kept for compatibility
	 */
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {

		if (args.length > 0 && args[0].equals("buggy")) {
			Config.setBuggy(true);
			System.out.println("TESTING BUGGY IMPLEMENTATION\n");
		}

		ArrayList<Class> classesToTest = new ArrayList<Class>();

		// ADD ANY CLASSES YOU WISH TO TEST HERE
		classesToTest.add(DrunkCarnivalShooterTest.class);

		// For all test classes added, loop through and use JUnit
		// to run them.

		for (Class c : classesToTest) {
			Result r = JUnitCore.runClasses(c);

			// Print out any failures for this class.

			for (Failure f : r.getFailures()) {
				System.out.println(f.toString());
				// System.out.println(f.getTrace());
			}

		}
	}
}
