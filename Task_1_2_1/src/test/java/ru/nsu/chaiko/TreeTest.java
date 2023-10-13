package ru.nsu.chaiko;

import java.util.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TreeTest {
    @Test
    void testTreeEquality() {
        Tree<Integer> tree1 = new Tree<>(1);
        Tree<Integer> tree2 = new Tree<>(1);
        Tree<Integer> tree3 = new Tree<>(3);

        tree1.addChild(2);
        tree2.addChild(2);

        assertEquals(tree1, tree2);

        tree3.addChild(5);

        tree1.addChild(tree3);
        tree2.addChild(tree3);

        assertEquals(tree1, tree2);
    }

    @Test
    void testAddChild() {
        Tree<String> treeExpected = new Tree<>("A");
        treeExpected.addChild("B");
        var a = treeExpected.addChild("C");
        a.addChild("D");

        Tree<String> treeActual = new Tree<>("A");
        treeActual.addChild("B");
        Tree<String> subtree = new Tree<>("C");
        subtree.addChild("D");
        treeActual.addChild(subtree);

        assertEquals(treeExpected, treeActual);
    }

    @Test
    void testBfsIterator() {
        Tree<String> tree1 = new Tree<>("A");
        var a = tree1.addChild("B");
        a.addChild("C");
        Tree<String> subtree1 = new Tree<>("D");
        subtree1.addChild("E");
        tree1.addChild(subtree1);

        ArrayList<String> bfsResult = new ArrayList<String>();

        for (String value : tree1) {
            bfsResult.add(value);
        }

        List<String> bfsExpected = new ArrayList<>(Arrays.asList("A", "B", "D", "C", "E"));
        assertEquals(bfsResult, bfsExpected);
    }

    @Test
    public void testIteratorExceptionBfs() {
        Tree<String> tree = new Tree<>("R1");
        var a1 = tree.addChild("A");
        var b1 = tree.addChild("B");
        a1.addChild("C");

        Iterator<String> someIterator = b1.iterator();

        assertThrows(ConcurrentModificationException.class, () -> {
            b1.addChild("abc");
            while (someIterator.hasNext()) {
                someIterator.next();
            }
        });
    }

    @Test
    void testDfsIterator() {
        Tree<String> tree = new Tree<>("A");
        Tree<String> child1 = tree.addChild("B");
        Tree<String> child2 = tree.addChild("C");
        child1.addChild("D");
        child1.addChild("E");
        child2.addChild("F");

        ArrayList<String> dfsResult = new ArrayList<String>();
        Iterator<String> iterator = tree.dfsiterator();

        while (iterator.hasNext()) {
            var value = iterator.next();
            dfsResult.add(value);
        }

        List<String> dfsExpected = new ArrayList<>(List.of("A", "B", "D", "E", "C", "F"));

        assertEquals(dfsExpected, dfsResult);
    }

    @Test
    public void testIteratorExceptionDfs() {
        Tree<String> tree = new Tree<>("R1");
        var a1 = tree.addChild("A");
        var b1 = tree.addChild("B");
        a1.addChild("C");

        Iterator<String> someIterator = b1.iterator();

        assertThrows(ConcurrentModificationException.class, () -> {
            b1.addChild("abc");
            while (someIterator.hasNext()) {
                someIterator.next();
            }
        });
    }

    @Test
    void testRemove() {
        Tree<String> treeActual = new Tree<>("R1");
        var a = treeActual.addChild("A");
        var b = a.addChild("B");
        Tree<String> subTree = new Tree<>("R2");
        subTree.addChild("C");
        subTree.addChild("D");
        treeActual.addChild(subTree);
        b.remove();

        Tree<String> treeExpected = new Tree<>("R1");
        treeExpected.addChild("A");
        Tree<String> tree2 = new Tree<>("R2");
        tree2.addChild("C");
        tree2.addChild("D");
        treeExpected.addChild(tree2);

        assertEquals(treeExpected, treeActual);
    }
}