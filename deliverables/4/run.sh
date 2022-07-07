mkdir bin

javac -d bin -cp "CommandLineJunit/*" src/*.java

java -Xverify:none -cp bin GameOfLife $1
