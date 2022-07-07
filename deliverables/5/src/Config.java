public class Config {
	// Type of logic to use for BeanCounterLogic
	private static LogicType logicType = LogicType.IMPL;
	// Type of testing to do on BeanCounterLogic
	private static TestType testType = TestType.JUNIT;

	public static void setLogicType(LogicType type) {
		logicType = type;
	}

	public static LogicType getLogicType() {
		return logicType;
	}

	public static void setTestType(TestType type) {
		testType = type;
	}

	public static TestType getTestType() {
		return testType;
	}
}
