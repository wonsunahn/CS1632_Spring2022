import java.util.ArrayList;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class JPFRunner {
	/**
	 * Main method.
	 *
	 * @param args IGNORED, kept for compatibility
	 */
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {

		ArrayList<Class> classesToTest = new ArrayList<Class>();

		// ADD ANY CLASSES YOU WISH TO TEST HERE
		classesToTest.add(JPFTest.class);

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
