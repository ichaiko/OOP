package ru.nsu.chaiko;

import java.util.ArrayDeque;

/**
 * Реализация блокирующей очереди.
 * @param <T> тип элементов очереди
 */
public class MyBlockingQueue<T> implements BlockingQueue<T> {
    private final ArrayDeque<T> orders = new ArrayDeque<>();
    private final int capacity;

    /**
     * Конструктор очереди.
     * @param capacity максимальная вместимость очереди
     */
    public MyBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Метод проверяет, пуста ли очередь.
     * @return true, если очередь пуста, иначе false
     */
    public boolean isEmpty() {
        return orders.isEmpty();
    }

    /**
     * Метод добавляет элемент в очередь.
     * @param elem элемент для добавления
     */
    @Override
    public synchronized void put(T elem) {
        while (this.orders.size() >= this.capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        this.orders.addLast(elem);
        notify();
    }

    /**
     * Метод извлекает элемент из очереди.
     * @return извлеченный элемент, либо null, если очередь пуста
     */
    @Override
    public synchronized T poll() {
        while (this.orders.size() == 0) {
            try {
                if (PizzeriaParameters.ordersList.isEmpty()) {
                    return null;
                }
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        var elem = this.orders.poll();
        notifyAll();
        return elem;
    }
}
