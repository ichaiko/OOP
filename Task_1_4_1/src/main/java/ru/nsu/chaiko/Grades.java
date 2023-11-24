package ru.nsu.chaiko;

/**
 * class for grades.
 */
public enum Grades {
    TWO(2), THREE(3), FOUR(4), FIVE(5);

    private final int numericalGrade;

    Grades(int grade) {
        this.numericalGrade = grade;
    }

    int getValue() {
        return numericalGrade;
    }

    public static Grades setValue(int value) throws PathNoteException {
        for (Grades grade : Grades.values()) {
            if (value == grade.numericalGrade) {
                return grade;
            }
        }

        throw new PathNoteException("No Grade for this value");
    }
}