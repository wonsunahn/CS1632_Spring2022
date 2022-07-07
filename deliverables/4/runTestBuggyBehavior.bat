md bin

javac -d bin -cp "CommandLineJunit\*" src\*.java

java -cp "GameOfLifeBuggy.jar;CommandLineJunit\*;bin" TestRunner behavior
