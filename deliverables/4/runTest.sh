mkdir bin

javac -d bin -cp "CommandLineJunit/*" src/*.java

java -cp "CommandLineJunit/*:bin" TestRunner
