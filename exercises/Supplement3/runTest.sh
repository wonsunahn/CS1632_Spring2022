mkdir bin

javac -d bin -cp "quickcheck-jars/*" src/*.java

java -cp "quickcheck-jars/*:bin" TestRunner
