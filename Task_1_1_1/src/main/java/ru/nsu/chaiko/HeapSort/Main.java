package ru.nsu.chaiko.HeapSort;

public class Main {
    public static void main(String[] args) {
        System.out.println("hello world");
    }
}

class HeapSort {
    final private int[] heap;
    private int heapSize, len;

    public HeapSort(int[] arr) {
        this.heap = arr;
        this.heapSize = arr.length;
        this.len = arr.length;
    }

    /**
     * swaps elements with specified indexes
     */
    void swap(int ind1, int ind2) {
        int temp = heap[ind1];

        heap[ind1] = heap[ind2];
        heap[ind2] = temp;
    }

    /**
     * siftUp push element up in heap if necessary
     */

    void siftUp(int indOfVertex) {
        int ancestor = (indOfVertex - 1) / 2;

        if (heap[indOfVertex] > heap[ancestor]) {
            swap(ancestor, indOfVertex);
            siftUp(ancestor);
        }
    }

    /**
     * siftDown moves element down to restore heap after swapping
     */
    void siftDown(int indOfVertex) {
        int leftChild = 2 * indOfVertex + 1, rightChild = 2 * indOfVertex + 2, maximumValueChildIndex = indOfVertex;

        if (rightChild < heapSize && heap[rightChild] > heap[leftChild]) {
            maximumValueChildIndex = rightChild;
        } else {
            if (leftChild < heapSize) {
                maximumValueChildIndex = leftChild;
            }
        }

        if (heap[maximumValueChildIndex] > heap[indOfVertex]) {
            swap(maximumValueChildIndex, indOfVertex);
            siftDown(maximumValueChildIndex);
        }
    }

    /**
     * heapify generates max heap
     */
    void heapify() {
        for (int i = 0; i < heapSize; i++) {
            siftUp(i);
        }
    }

    /**
     * sorts array
     */
    int[] heapsort() {
        heapify();

        for (int i = 0; i < len; i++) {
            swap(0, heapSize - 1);
            heapSize--;
            siftDown(0);
        }

        return heap;
    }
}