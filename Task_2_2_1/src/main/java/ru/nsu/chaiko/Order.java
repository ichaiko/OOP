package ru.nsu.chaiko;

/**
 * Класс, представляющий заказ.
 */
public class Order {
    /** Уникальный идентификатор заказа. */
    private final int id;
    /** Время до доставки заказа. */
    private final int timeToDelivery;
    /** Статус заказа. */
    private OrderStatus status;

    /**
     * Создает новый заказ с указанным идентификатором и временем до доставки.
     *
     * @param id Уникальный идентификатор заказа.
     *
     * @param timeToDelivery Время до доставки заказа.
     */
    public Order(int id, int timeToDelivery) {
        this.id = id;
        this.timeToDelivery = timeToDelivery;
        this.status = OrderStatus.INQUEUE;
        System.out.println("id: " + this.id + " - status: " + this.status.toString());
    }

    /**
     * Возвращает текущий статус заказа.
     *
     * @return Текущий статус заказа.
     */
    public String getStatus() {
        return this.status.toString();
    }

    /**
     * Возвращает уникальный идентификатор заказа.
     *
     * @return Уникальный идентификатор заказа.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Устанавливает новый статус заказа.
     *
     * @param status Новый статус заказа.
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * Возвращает время до доставки заказа.
     *
     * @return Время до доставки заказа.
     */
    public int getTimeToDelivery() {
        return this.timeToDelivery;
    }
}
