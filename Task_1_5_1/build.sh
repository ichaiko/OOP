mkdir output
mkdir documentaion

javac -d output src/main/java/ru/nsu/chaiko/Calculator.java
javac -d output src/main/java/ru/nsu/chaiko/CalculatorTests.java
javac -d output src/main/java/ru/nsu/chaiko/NoInputException.java
javac -d output src/main/java/ru/nsu/chaiko/NoNumberException.java
javac -d output src/main/java/ru/nsu/chaiko/IncorrectInputFormatException.java

javadoc -d documentaion src/main/java/ru/nsu/chaiko/IncorrectInputFormatException.java
javadoc -d documentation src/main/java/ru/nsu/chaiko/Calculator.java
javadoc -d documentation src/main/java/ru/nsu/chaiko/NoInputException.java
javadoc -d documentation src/main/java/ru/nsu/chaiko/NoNumberException.java
javadoc -d documentation src/main/java/ru/nsu/chaiko/CalculatorTests.java