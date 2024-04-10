package ru.nsu.chaiko;

/**
 * Перечисление, представляющее возможные статусы заказа.
 */
public enum OrderStatus {
    /** Заказ в очереди. */
    INQUEUE(0),
    /** Заказ готовится. */
    COOCKING(1),
    /** Заказ в наличии. */
    INSTOCK(2),
    /** Заказ доставляется. */
    DELIVERING(3),
    /** Заказ доставлен. */
    DELIVERED(5);

    /** Числовое представление статуса заказа. */
    private int status;

    /**
     * Создает новый статус заказа с указанным числовым значением.
     * @param status Числовое представление статуса заказа.
     */
    OrderStatus(int status) {
        this.status = status;
    }

    /**
     * Устанавливает числовое представление статуса заказа.
     * @param status Числовое представление статуса заказа.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Возвращает строковое представление статуса заказа.
     * @return Строковое представление статуса заказа.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
