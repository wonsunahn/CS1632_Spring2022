mkdir bin

javac -d bin -cp "CommandLineJunit/*:quickcheck-jars/*" src/*.java

java -cp "CommandLineJunit/*:quickcheck-jars/*:bin" TestRunner
