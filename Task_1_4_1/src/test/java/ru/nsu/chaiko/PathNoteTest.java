package ru.nsu.chaiko;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * class for tests.
 */
public class PathNoteTest {
    @Test
    void test1() throws UniverseException {
        PathNote pathNote = new PathNote("Igor", "Chaiko");
        pathNote.setMark(1, 4, "declarative programming");
        pathNote.setMark(2, 5, "declarative programming");
        pathNote.setMark(2, 5, "discrete math");
        pathNote.setMark(2, 4, "algebra");

        Assertions.assertEquals(4.5, pathNote.getAverageScore());
    }

    @Test
    void test2() {
        PathNote pathNote = new PathNote("abc", "dcvv");

        Assertions.assertThrows(UniverseException.class, () -> {
            pathNote.setMark(2, -5, "a");
        });

        Assertions.assertThrows(UniverseException.class, () -> {
            pathNote.setMark(0, 4, "a");
        });
    }

    @Test
    void test3() throws UniverseException {
        PathNote pathNote = new PathNote("af", "sdf");
        pathNote.setMark(1, 3, "sadf");
        pathNote.setMark(2, 5, "asdf");
        pathNote.setMark(5, 3, "safds");
        pathNote.setMark(5, 4, "asd");

        Assertions.assertEquals(3.75, pathNote.getAverageScore());
        Assertions.assertFalse(pathNote.redDiploma());
        Assertions.assertFalse(pathNote.increasedScholarships());
    }

    @Test
    void test4() throws UniverseException {
        PathNote pathNote = new PathNote("asf", "safas");
        pathNote.setMark(2, 3, "asdf");

        Assertions.assertEquals(3, pathNote.getAverageScore());
    }

    @Test
    void test5() {
        PathNote pathNote = new PathNote("a", "b");

        Assertions.assertThrows(UniverseException.class, pathNote::getAverageScore);
    }
}