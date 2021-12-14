package ru.netology;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    private static Faker faker = new Faker(new Locale("ru"));
    private static LocalDate today = LocalDate.now();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    DataGenerator() {
    }

    public static String forwardDate(int plusDays) {
        LocalDate newDate = today.plusDays(plusDays);
        return formatter.format(newDate);
    }

    public static String randomCity() {
        String[] myCityList = new String[]{"Абакан", "Анадырь", "Архангельск", "Астрахань", "Барнаул", "Владикавказ",
                "Горно-Алтайск", "Йошкар-Ола", "Казань", "Калининград", "Калуга", "Краснодар", "Магас", "Махачкала",
                "Нарьян-Мар", "Салехард", "Самара", "Саранск", "Саратов", "Хабаровск", "Ханты-Мансийск",
                "Южно-Сахалинск", "Великий Новгород", "Владивосток", "Владимир", "Вологда", "Рязань", "Биробиджан",
                "Чебоксары", "Москва", "Санкт-Петербург", "Ульяновск", "Симферополь", "Ростов-на-Дону"};
        int city = (int) Math.floor(Math.random() * myCityList.length);
        return myCityList[city];
    }

    public static String randomName() {
        return faker.name().fullName().replace("ё", "е");
    }

    public static String randomPhone() {
        return faker.numerify("+7##########");
    }
}
