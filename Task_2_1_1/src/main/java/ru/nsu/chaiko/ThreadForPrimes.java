package ru.nsu.chaiko;

/**
 * class for threads.
 */
class ThreadForPrimes extends Thread {
    final private int[] array;
    public ThreadForPrimes(int[] partOfArray) {
        this.array = partOfArray;
    }

    /**
     * thread entry point.
     */
    @Override
    public void run() {
        boolean res;

        for (int number : this.array) {
            if (PrimeNumberCheck.flag.get()) {
                break;
            }

            res = PrimeNumberCheck.isNumberNotPrime(number);

            if (res) {
                PrimeNumberCheck.flag.set(true);
                break;
            }
        }
    }
}