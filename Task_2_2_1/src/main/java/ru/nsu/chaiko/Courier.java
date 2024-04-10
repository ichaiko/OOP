package ru.nsu.chaiko;

import java.util.ArrayDeque;

/**
 * Поток, представляющий курьера в пиццерии.
 */
public class Courier extends Thread {
    private final String name;
    private final int bagCapacity;
    private final ArrayDeque<Order> bag = new ArrayDeque<>();

    /**
     * Конструктор курьера.
     * @param name имя курьера
     * @param bagCapacity вместимость сумки курьера (количество заказов)
     */
    public Courier(String name, int bagCapacity) {
        this.name = name;
        this.bagCapacity = bagCapacity;
    }

    /**
     * Метод, который выполняется при запуске потока курьера.
     */
    @Override
    public void run() {
        while (true) {
            int cnt = 0;
            int timeUntilDelivery = 0;

            while (cnt != this.bagCapacity) {
                Order order = PizzeriaParameters.stock.poll();
                if (order == null) {
                    break;
                }

                order.setStatus(OrderStatus.DELIVERING);
                System.out.println("id: " + order.getId() + " - status: " + order.getStatus());
                bag.add(order);
                timeUntilDelivery += order.getTimeToDelivery();
                ++cnt;
            }

            try {
                sleep(timeUntilDelivery * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            for (var order : bag) {
                order.setStatus(OrderStatus.DELIVERED);
                System.out.println("id: " + order.getId() + " - status: " + order.getStatus());
                bag.remove(order);
            }

            int count = PizzeriaParameters.bakersFinished.get();

            if ((PizzeriaParameters.stock.isEmpty() && count == PizzeriaParameters.bakers.size()) ||
                    PizzeriaParameters.workDayIsOver) {
                break;
            }
        }
    }
}
