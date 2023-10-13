package ru.nsu.chaiko;

import java.util.*;

/**
 * main class of the task.
 */
public class Tree<T> implements Iterable<T> {
    private T value;
    private Tree<T> parent;
    private ArrayList<Tree<T>> children;
    private int modificationsCount;

    /**
     * constructor.
     */
    public Tree(T value) {
        this.value = value;
        this.children = new ArrayList<Tree<T>>();
        this.parent = null;
        this.modificationsCount = 0;
    }

    /**
     * modify counter.
     */
    private void changeModificationsCount() {
        for (Tree<T> ancestor = this.parent; ancestor != null; ancestor = ancestor.parent) {
            ancestor.modificationsCount++;
        }
    }

    /**
     * adding value to the tree.
     */
    Tree<T> addChild(T value) {
        Tree<T> newChild = new Tree<>(value);
        newChild.parent = this;

        this.children.add(newChild);
        this.modificationsCount++;
        this.changeModificationsCount();
        return newChild;
    }

    /**
     * adding subtree to the tree.
     */
    void addChild(Tree<T> value) {
        this.modificationsCount++;
        this.changeModificationsCount();
        this.children.add(value);
        value.parent = this;
    }

    /**
     * func to remove tree.
     */
    void remove() {
        this.modificationsCount++;
        this.changeModificationsCount();

        if (this.parent != null) {
            this.parent.children.remove(this);
        }

        this.children = null;
        this.value = null;
    }

    /**
     * equals of trees check.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Tree<?> other = (Tree<?>) object;

        if (!Objects.equals(value, other.value)) {
            return false;
        }

        if (children.size() != other.children.size()) {
            return false;
        }

        for (int i = 0; i < children.size(); i++) {
            if (!children.get(i).equals(other.children.get(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * iterator overriding.
     * use just iterator for bfs iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new BfsIterator<>(this);
    }

    /**
     * subclass for implementation of bfs iterator.
     */
    private class BfsIterator<T> implements Iterator<T> {
        private int expectedModificationsCount;
        private final Queue<Tree<T>> queue = new LinkedList<>();

        /**
         * iterator constructor.
         */
        BfsIterator(Tree<T> tree) {
            this.expectedModificationsCount = tree.modificationsCount;
            if (tree.value != null) {
                queue.add(tree);
            }
        }

        /**
         * checking existing of next elem.
         */
        @Override
        public boolean hasNext() throws ConcurrentModificationException {
            if (expectedModificationsCount != modificationsCount) {
                throw new ConcurrentModificationException();
            }
            return !this.queue.isEmpty();
        }

        /**
         * getting elem if it is existing.
         */
        @Override
        public T next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Tree<T> tree = this.queue.poll();
            for (int i = 0; i < tree.children.size(); i++) {
                if (tree.children.get(i).value != null) {
                    this.queue.add(tree.children.get(i));
                }
            }
            return tree.value;
        }

        @Override
        public void remove() throws ConcurrentModificationException {
            throw new ConcurrentModificationException();
        }
    }

    /**
     * dfsIterator declared.
     */
    public Iterator<T> dfsiterator() {
        return new DfsIterator<>(this);
    }

    /**
     * subclass for implementation of dfs iterator.
     */
    private class DfsIterator<T> implements Iterator<T> {
        private int expectedModificationsCount;
        private Deque<Tree<T>> stack = new ArrayDeque<>();
        private final Deque<Tree<T>> visitedVertex = new ArrayDeque<>();

        /**
         * iterator constructor.
         */
        DfsIterator(Tree<T> t) {
            expectedModificationsCount = t.modificationsCount;
            if (t.value != null) {
                stack.addLast(t);
            }
        }

        /**
         * checking existing of next elem.
         */
        @Override
        public boolean hasNext() throws ConcurrentModificationException {
            if (expectedModificationsCount != modificationsCount) {
                throw new ConcurrentModificationException();
            }

            return !this.stack.isEmpty();
        }

        /**
         * getting elem if it is existing.
         */
        @Override
        public T next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Tree<T> a = this.stack.pop();
            for (int i = a.children.size() - 1; i >= 0; i--) {
                if (a.children.get(i).value != null) {
                    this.stack.addFirst(a.children.get(i));
                }
            }
            this.visitedVertex.addLast(a);
            return a.value;
        }

        @Override
        public void remove() throws ConcurrentModificationException {
            throw new ConcurrentModificationException();
        }
    }
}