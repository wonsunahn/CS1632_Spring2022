public class TestShoot {
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

        DrunkCarnivalShooterTest test = new DrunkCarnivalShooterTest();
        test.setUp();
        test.testShoot();
	}
}
