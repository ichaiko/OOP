package ru.nsu.chaiko;

import java.io.FileNotFoundException;

/**
 * Главный класс приложения, запускающий пиццерию.
 */
public class Main {

    /**
     * Основной метод, запускающий приложение.
     *
     * @param args аргументы командной строки (не используются)
     *
     * @throws FileNotFoundException если файл параметров пиццерии не найден
     *
     * @throws InterruptedException если поток главного метода прерван во время ожидания завершения других потоков
     */
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        PizzeriaParameters params = new PizzeriaParameters(args[0]);
        params.extractPizzeriaParams();

        params.startWorkDayTimer();

        for (Baker baker : PizzeriaParameters.bakers) {
            baker.start();
        }

        for (Courier courier : PizzeriaParameters.couriers) {
            courier.start();
        }

        for (Baker baker : PizzeriaParameters.bakers) {
            baker.join();
        }

        for (Courier courier : PizzeriaParameters.couriers) {
            courier.join();
        }

        if (!PizzeriaParameters.ordersList.isEmpty()) {
            PizzeriaParameters.saveRemained(args[1]);
        }
    }
}
