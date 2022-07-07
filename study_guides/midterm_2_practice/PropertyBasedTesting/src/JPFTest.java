import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import org.junit.BeforeClass;
import org.junit.Test;
import gov.nasa.jpf.vm.Verify;

public class JPFTest {
	private static int x;
	private static int y;
	
	@BeforeClass
	public static void setUp() {
		// TODO: Fill in
	}
	
	@Test
	public void testAdd() {
		int ret = IntegerOps.add(x, y);
		// TODO: Fill in
	}
	
	@Test
	public void testSquare() {
		int ret = IntegerOps.square(x);
		// TODO: Fill in
	}
	
}