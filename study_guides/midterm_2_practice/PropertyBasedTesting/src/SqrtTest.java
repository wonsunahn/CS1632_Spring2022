import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.*;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(JUnitQuickcheck.class)
public class SqrtTest {
	@Property
	public void testSqrt(@InRange(minDouble = 0, maxDouble = 25) double d) {
		double ret = Math.sqrt(d);
		// Invariant: the square root of a value is always positive or zero.
		assertTrue(ret >= 0);
		// Invariant: given 0 <= d <= 25, ret is at maximum 5. 
		assertTrue(ret <= 5);
		// Invariant: the square root of a value is greater than the given value if it is larger than 1.  Otherwise, it is less than. 
		assertTrue(d > 1 ? d > ret : d <= ret);
		// Invariant: the squared of a square root is equal to the original value (+- some epsilon)
	    assertTrue(Math.abs(ret * ret - d) < 0.0001);
	}
}