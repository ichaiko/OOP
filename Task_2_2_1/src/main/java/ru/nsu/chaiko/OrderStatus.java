package ru.nsu.chaiko;

/**
 * Перечисление, представляющее возможные статусы заказа.
 */
public enum OrderStatus {
    /** Заказ в очереди. */
    INQUEUE,
    /** Заказ готовится. */
    COOCKING,
    /** Заказ в наличии. */
    INSTOCK,
    /** Заказ доставляется. */
    DELIVERING,
    /** Заказ доставлен. */
    DELIVERED;

    /**
     * Возвращает строковое представление статуса заказа.
     *
     * @return Строковое представление статуса заказа.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
