mkdir bin

javac -d bin -cp "CommandLineJunit/*:quickcheck-jars/*" src/*.java

java -Xverify:none -cp "bin:CommandLineJunit/*:quickcheck-jars/*" MonkeySim $1
