md bin

javac -d bin -cp "CommandLineJunit\*" src\*.java

java -cp bin GameOfLife %1
