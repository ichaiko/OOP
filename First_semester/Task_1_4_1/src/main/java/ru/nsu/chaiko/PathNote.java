package ru.nsu.chaiko;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * main class of the task.
 */
public class PathNote {
    private final String name;
    private final String surname;

    /**
     * at the beginning every value is null, because no mark per specific semester.
     */
    private final HashMap<String, Grade[]> pathNote = new HashMap<>();

    public PathNote(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    /**
     * returning number of current semester.
     */
    private int currentSemester() {
        int res = 0;
        var keys = pathNote.keySet();

        for (String key : keys) {
            var semester = pathNote.get(key);

            for (int i = 0; i < 8; i++) {
                if (semester[i] != null && i > res) {
                    res = i;
                }
            }
        }

        return res;
    }

    /**
     * sets mark per specific semester.
     */
    void setMark(int semester, int mark, String subject) throws UniverseException {
        Grade[] semesters;

        if (semester > 8 || semester < 1) {
            throw new UniverseException("incorrect semester value");
        }

        if (pathNote.containsKey(subject)) {
            semesters = pathNote.get(subject);
        } else {
            semesters = new Grade[8];
        }

        semesters[semester - 1] = Grade.setValue(mark);
        pathNote.put(subject, semesters);
    }

    /**
     * Calculates avg grade.
     */
    double getAverageScore() throws UniverseException {
        var keys = pathNote.keySet();
        double wholeGradesCount = 0;
        double wholeGradesSum = 0;

        if (keys.isEmpty()) {
            throw new UniverseException("you have no marks!");
        }

        for (String subj : keys) {
            var semesters = pathNote.get(subj);

            // здесь потоки притянуты за уши, тем более с ними по ассимтотики даже хуже выходит
            // просто полезная тема, захотелось изучить, потрогать
            // можно было применять стримы сразу к мапе, но для первого знакомство too much
            wholeGradesSum += Arrays.stream(semesters)
                    .filter(Objects::nonNull)
                    .mapToDouble(Grade::getValue)
                    .sum();

            wholeGradesCount += (double) Arrays.stream(semesters)
                    .filter(Objects::nonNull)
                    .count();
        }

        return wholeGradesSum / wholeGradesCount;
    }

    /**
     * returns possibility of getting increased scholarships.
     */
    boolean increasedScholarships() throws UniverseException {
        int currentSemester = currentSemester();
        var values = pathNote.values();

        if (values.isEmpty()) {
            throw new UniverseException("no semester marks");
        }

        for (var value : values) {
            if (value[currentSemester] != Grade.FIVE) {
                if (value[currentSemester] == null) {
                    continue;
                }

                return false;
            }
        }

        return true;
    }

    /**
     * no more than 3 '3' grades & avg score not less than 4.75.
     */
    boolean redDiploma() throws UniverseException {
        var keys = pathNote.keySet();
        int threeCounter = 0;

        for (String key : keys) {
            var semester = pathNote.get(key);

            threeCounter += Arrays.stream(semester)
                    .filter(Objects::nonNull)
                    .mapToInt(Grade::getValue)
                    .filter(mark -> mark == 3)
                    .count();
        }

        return threeCounter < 4 && getAverageScore() >= 4.75;
    }
}

/**
 * class for grades.
 */
enum Grade {
    TWO(2), THREE(3), FOUR(4), FIVE(5);

    private final int numericalGrade;

    Grade(int grade) {
        this.numericalGrade = grade;
    }

    int getValue() {
        return numericalGrade;
    }

    public static Grade setValue(int value) throws UniverseException {
        for (Grade grade : Grade.values()) {
            if (value == grade.numericalGrade) {
                return grade;
            }
        }

        throw new UniverseException("No Grade for this value");
    }
}

