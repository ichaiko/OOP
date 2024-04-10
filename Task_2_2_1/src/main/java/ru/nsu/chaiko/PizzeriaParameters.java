package ru.nsu.chaiko;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import com.google.gson.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс, представляющий параметры и операции пиццерии.
 */
public class PizzeriaParameters {
    /** Общее рабочее время пиццерии. */
    static int workTime;
    /** Вместимость склада. */
    static int stockCapacity;
    /** Количество пекарей, завершивших работу. */
    static AtomicInteger bakersFinished = new AtomicInteger(0);
    /** Список пекарей в пиццерии. */
    static ArrayList<Baker> bakers = new ArrayList<>();
    /** Список курьеров в пиццерии. */
    static ArrayList<Courier> couriers = new ArrayList<>();
    /** Временное хранилище для заказов. */
    static ArrayList<Order> temporaryStorage = new ArrayList<>();
    /** Список заказов в пиццерии. */
    static MyBlockingQueue<Order> ordersList, stock;
    /** Флаг, указывающий, что рабочий день закончен. */
    static boolean workDayIsOver = false;
    /** Флаг, указывающий, что работа завершена. */
    static boolean workFinished = false;
    /** Путь к JSON-файлу с параметрами пиццерии. */
    private final String path;

    /**
     * Создает новый экземпляр класса PizzeriaParameters с указанным путем к JSON-файлу.
     *
     * @param path Путь к JSON-файлу.
     */
    public PizzeriaParameters(String path) {
        this.path = path;
    }

    /**
     * Извлекает параметры пиццерии из JSON-файла.
     *
     * @throws FileNotFoundException Если файл, указанный по пути, не найден.
     */
    public void extractPizzeriaParams() throws FileNotFoundException {
        Gson json = new Gson();
        JsonElement jsonElement;
        JsonObject jsonObject = null;

        try {
            jsonElement = JsonParser.parseReader(new FileReader(this.path));
            jsonObject = jsonElement.getAsJsonObject();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (jsonObject == null) {
            throw new FileNotFoundException();
        }

        PizzeriaParameters.workTime = jsonObject.get("workTime").getAsInt();
        PizzeriaParameters.stockCapacity = jsonObject.get("stockCapacity").getAsInt();
        PizzeriaParameters.stock = new MyBlockingQueue<>(PizzeriaParameters.stockCapacity);

        var list = jsonObject.getAsJsonArray("bakers");
        for (var elem : list) {
            JsonObject obj = elem.getAsJsonObject();
            Baker baker = new Baker(obj.get("name").getAsString(), obj.get("cookingSpeed").getAsInt());
            PizzeriaParameters.bakers.add(baker);
        }

        list = jsonObject.getAsJsonArray("couriers");
        for (var elem : list) {
            JsonObject obj = elem.getAsJsonObject();
            Courier courier = new Courier(obj.get("name").getAsString(), obj.get("bagCapacity").getAsInt());
            PizzeriaParameters.couriers.add(courier);
        }

        list = jsonObject.getAsJsonArray("ordersList");
        for (var elem : list) {
            JsonObject obj = elem.getAsJsonObject();
            PizzeriaParameters.temporaryStorage.add(new Order(obj.get("id").getAsInt(),
                    obj.get("timeToDelivery").getAsInt()));
        }

        PizzeriaParameters.ordersList = new MyBlockingQueue<>(temporaryStorage.size());
        for (var elem : PizzeriaParameters.temporaryStorage) {
            PizzeriaParameters.ordersList.put(elem);
        }
    }

    /**
     * Запускает таймер рабочего дня.
     */
    public void startWorkDayTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                workDayIsOver = true;
                timer.cancel();
            }
        }, workTime * 1000L);
    }

    /**
     * Сохраняет оставшиеся заказы в JSON-файл.
     *
     * @param filePath Путь к файлу, в который необходимо сохранить оставшиеся заказы.
     */
    public static void saveRemained(String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray ordersJsonArray = new JsonArray();

        while (true) {
            Order order = ordersList.poll();
            if (order == null) {
                break;
            }

            JsonObject orderJsonObject = new JsonObject();
            orderJsonObject.addProperty("id", order.getId());
            ordersJsonArray.add(orderJsonObject);
        }

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            gson.toJson(ordersJsonArray, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
