mkdir output
mkdir documentaion

javac -d output src/main/java/ru/nsu/chaiko/SolutionsForPrimeChecking.java
javac -d output src/main/java/ru/nsu/chaiko/PrimeNumberCheck.java
javac -d output src/main/java/ru/nsu/chaiko/ThreadForPrimes.java
javac -d output src/main/java/ru/nsu/chaiko/SolutionsTest.java

javadoc -d output src/main/java/ru/nsu/chaiko/SolutionsForPrimeChecking.java
javadoc -d output src/main/java/ru/nsu/chaiko/PrimeNumberCheck.java
javadoc -d output src/main/java/ru/nsu/chaiko/ThreadForPrimes.java
javadoc -d output src/main/java/ru/nsu/chaiko/SolutionsTest.java