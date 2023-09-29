package ru.nsu.chaiko.polynomial;

public class Main {
    /**
     * just main func for no warnings
     */
    public static void main(String[] args) {
        System.out.println("hello world!");
    }
}

class Polynomial {
    private final int[] coefficients;
    private int length;

    /**
     * constructor
     */
    public Polynomial(int[] coefficients) throws Exception {
        if (coefficients.length == 0) {
            throw new Exception("empty polynomial");
        }
        this.coefficients = coefficients;
        this.length = coefficients.length;
    }

    /**
     * my pow for ints
     */
    private int pow(int base, int exponent) {
        int result = 1;

        if (exponent == 0) {return 1;}

        while (exponent > 0) {
            result *= base;
            exponent--;
        }

        return result;
    }
    /**
     * calculate value in point
     */
    int evaluate (int value) {
        int result = 0;

        for (int i = 0; i < length; i++) {
            result += coefficients[i] * pow(value, i);
        }

        return result;
    }

    /**
     * addition of two polynomials
     */

    Polynomial plus (Polynomial inputPolynomial) throws Exception {
        Polynomial answer;
        int[] copy;

        if (this.length > inputPolynomial.length) {
            copy = new int[this.length];
            System.arraycopy(this.coefficients, 0, copy, 0, this.length);
            answer = new Polynomial(copy);

            for (int i = 0; i < inputPolynomial.length; i++) {
                answer.coefficients[i] += inputPolynomial.coefficients[i];
            }
        } else {
            copy = new int[inputPolynomial.length];
            System.arraycopy(inputPolynomial.coefficients, 0, copy, 0, inputPolynomial.length);
            answer = new Polynomial(copy);

            for (int i = 0; i < this.length; i++) {
                answer.coefficients[i] += this.coefficients[i];
            }
        }
        return answer;
    }

    /**
     * difference of two
     */
    Polynomial subtraction (Polynomial inputPolynomial) throws Exception {
        Polynomial answer;
        int[] copy;

        if (this.length > inputPolynomial.length) {
            copy = new int[this.length];
            System.arraycopy(this.coefficients, 0, copy, 0, this.length);
            answer = new Polynomial(copy);

            for (int i = 0; i < inputPolynomial.length; i++) {
                answer.coefficients[i] -= inputPolynomial.coefficients[i];
            }
        } else {
            copy = new int[inputPolynomial.length];
            System.arraycopy(inputPolynomial.coefficients, 0, copy, 0, inputPolynomial.length);
            answer = new Polynomial(copy);

            for (int i = 0; i < inputPolynomial.length; i++) {
                answer.coefficients[i] *= -1;
            }

            for (int i = 0; i < this.length; i++) {
                answer.coefficients[i] += this.coefficients[i];
            }
        }
        return answer;
    }

    /**
     * multiplication of two
     */
    Polynomial multiply (Polynomial inputPolynomial) throws Exception {
        int lengthOfAnswer = this.length + inputPolynomial.length - 1;
        Polynomial answer = new Polynomial(new int[lengthOfAnswer]);

        for (int i = 0; i < this.length; i++) {
            for (int j = 0; j < inputPolynomial.length; j++) {
                answer.coefficients[i+j] += this.coefficients[i] * inputPolynomial.coefficients[j];
            }
        }

        return answer;
    }

    /**
     * derivative of polynomial
     */

    Polynomial differentiate (int order) throws Exception {
        int[] copy = new int[this.length];
        System.arraycopy(this.coefficients, 0, copy, 0, this.length);
        Polynomial result = new Polynomial(copy);

        for (int i = 0; i < order; i++) {
            for (int j = 0; j < result.length - 1; j++) {
                result.coefficients[j] = result.coefficients[j+1] * (j + 1);
            }
            result.coefficients[result.length - 1] = 0;
            result.length--;
        }

        return result;
    }

    /**
     * polynomial equals check
     */
    Boolean equals(Polynomial inputPolynomial) {
        if (this.length != inputPolynomial.length) {return false;}
        else {
            for (int i = 0; i < this.length; i++) {
                if (this.coefficients[i] != inputPolynomial.coefficients[i]) {return false;}
            }

            return true;
        }
    }

    /**
     * toString overriding
     */
    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();

        if (this.length == 0) {return " ";}

        for (int i = this.length - 1; i > 0; i--) {
            if (this.coefficients[i] > 0) {
                if (i != this.length - 1) {build.append(" + ");}

                if (i == 1) {build.append(this.coefficients[i]).append("x");}
                else {build.append(this.coefficients[i]).append("x^").append(i);}
            }
            if (this.coefficients[i] < 0) {
                if (i != this.length - 1) {build.append(" - ");}
                else {
                    build.append(coefficients[i]).append("x^").append(i);
                    continue;
                }
                int value = this.coefficients[i] * -1;

                if (i == 1) {build.append(value).append("x");}
                else {build.append(value).append("x^").append(i);}
            }
        }

        if (this.coefficients[0] < 0) {
            if (this.length != 1) {
                int value = coefficients[0] * -1;
                build.append(" - ").append(value);
            } else {
                build.append(this.coefficients[0]);
            }
        } else {
            if (this.length != 1) {build.append(" + ").append(this.coefficients[0]);}
            else {build.append(this.coefficients[0]);}
        }

        return build.toString();
    }
}
