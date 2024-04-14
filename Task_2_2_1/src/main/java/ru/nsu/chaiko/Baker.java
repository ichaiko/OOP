package ru.nsu.chaiko;

/**
 * Поток, представляющий пекаря в пиццерии.
 */
public class Baker extends Thread {
    private final String name;
    private final int cookingSpeed;

    /**
     * Конструктор пекаря.
     *
     * @param name имя пекаря
     *
     * @param cookingSpeed скорость приготовления заказов (в секундах)
     */
    public Baker(String name, int cookingSpeed) {
        this.name = name;
        this.cookingSpeed = cookingSpeed;
    }

    /**
     * Метод, который выполняется при запуске потока пекаря.
     */
    @Override
    public void run() {
        while (!PizzeriaParameters.ordersList.isEmpty()) {
            if (PizzeriaParameters.workDayIsOver) {
                return;
            }

            Order order = PizzeriaParameters.ordersList.poll();
            if (order == null) {
                break;
            }

            order.setStatus(OrderStatus.COOCKING);
            System.out.println("id: " + order.getId() + " - status: " + order.getStatus() + " by " + this.name);

            try {
                sleep(this.cookingSpeed * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            PizzeriaParameters.stock.put(order);
            order.setStatus(OrderStatus.INSTOCK);
            System.out.println("id: " + order.getId() + " - status: " + order.getStatus() + " by " + this.name);
        }

        PizzeriaParameters.bakersFinished.incrementAndGet();
    }
}
