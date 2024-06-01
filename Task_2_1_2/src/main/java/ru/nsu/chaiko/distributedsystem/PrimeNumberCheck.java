package ru.nsu.chaiko.distributedsystem;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * class for static methods/variables.
 */
public class PrimeNumberCheck {
    public static AtomicBoolean flag = new AtomicBoolean(false);

    public static Boolean hasNotPrimeInList(List<Integer> list) {
        for (Integer num : list) {
            if (isNumberNotPrime(num)) {
                return true;
            }
        }
        return false;
    }

    /**
     * for separate numbers.
     *
     * @param number input number.
     *
     * @return Does specific number is prime.
     */
    public static boolean isNumberNotPrime(int number) {
        if (number == 0 || number == 1) {
            return false;
        }

        int lim = (int) Math.ceil(Math.sqrt(number));

        for (int i = 2; i < lim; i++) {
            if (number % i == 0) {
                return true;
            }
        }

        return false;
    }
}
