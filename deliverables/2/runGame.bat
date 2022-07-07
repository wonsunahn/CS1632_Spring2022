javac -d bin/ -cp coffeemaker-buggy.jar;CommandLineJunit\junit-4.12.jar;CommandLineJunit\hamcrest-core-1.3.jar;CommandLineJunit\mockito-core-1.10.19.jar;CommandLineJunit\objenesis-2.4.jar src/*.java

java -cp bin/ Game
