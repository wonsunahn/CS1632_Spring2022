javac -cp coffeemaker-buggy.jar;CommandLineJunit\junit-4.12.jar;CommandLineJunit\hamcrest-core-1.3.jar;CommandLineJunit\mockito-core-1.10.19.jar;CommandLineJunit\objenesis-2.4.jar -d bin src\*.java

java -cp bin;coffeemaker-buggy.jar;CommandLineJunit\junit-4.12.jar;CommandLineJunit\hamcrest-core-1.3.jar;CommandLineJunit\mockito-core-1.10.19.jar;CommandLineJunit\objenesis-2.4.jar -javaagent:CommandLineJacoco\jacocoagent.jar=destfile=jacoco.exec,includes=* TestRunner

java -jar CommandLineJacoco/jacococli.jar report jacoco.exec --html ./jacocoReport --sourcefiles src --classfiles bin
