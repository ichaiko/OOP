package ru.nsu.chaiko;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class of the task.
 */
public class SolutionsForPrimeChecking {
    private final int[] inputArray;

    /**
     * class's constructor.
     * @param inputArray array that given as input.
     */
    public SolutionsForPrimeChecking(int[] inputArray) {
        this.inputArray = inputArray;
    }

    /**
     * sequential finding not prime number.
     * @return is there prime number.
     */
    boolean sequentialSolution() {
        boolean res;

        for (int number : this.inputArray) {
            res = PrimeNumberCheck.isNumberNotPrime(number);

            if (res) {
                return true;
            }
        }

        return false;
    }

    /**
     * finding primes using 'numOfThreads' threads.
     * @param numOfThreads parameter.
     * @return is there prime number.
     * @throws InterruptedException for thread stopping.
     */
    boolean multiThreadSolution(int numOfThreads) throws InterruptedException {
        PrimeNumberCheck.flag.set(false);

        int toEach = this.inputArray.length / numOfThreads;
        int counter = 0;
        ArrayList<ThreadForPrimes> list = new ArrayList<>();

        for (int i = 0; i < numOfThreads; i++) {
            int[] arrayPart = new int[toEach];

            for (int j = 0; j < toEach; j++) {
                if (counter >= this.inputArray.length) {
                    break;
                }

                arrayPart[j] = this.inputArray[counter++];
            }

            ThreadForPrimes thread = new ThreadForPrimes(arrayPart);
            thread.start();
            list.add(thread);
        }

        for (ThreadForPrimes i : list) {
            i.join();
        }

        return PrimeNumberCheck.flag.get();
    }

    /**
     * finding prime numbers, using parallel stream.
     * @return is there prime number.
     */
    boolean parallelStreamSolution() {
        List<Integer> list = new ArrayList<>();

        for (int i : this.inputArray) {
            list.add(i);
        }

        long res = list.parallelStream()
                .filter(PrimeNumberCheck::isNumberNotPrime)
                .count();

        return res > 0;
    }

    public static void main(String[] args) throws InterruptedException {
        var obj = new SolutionsForPrimeChecking(new int[] {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053});

        System.out.println(obj.sequentialSolution());
        System.out.println(obj.multiThreadSolution(4));
        System.out.println(obj.parallelStreamSolution());
    }
}
