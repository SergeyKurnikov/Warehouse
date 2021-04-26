package com.example.warehouse;

/**
 * Класс тестирования поля типа "дата"
 * @autor Сергей Курников
 * @version 1.0.0
 */
public class DateUtil {
    /**
     *  Проверка введёной даты
     *  @param value - введенное значение имени
     *  @return - результат проверки
     */
    public static boolean isDateCorrect(String value) {
        if(value.length() < 10) return false;
        if(!value.matches(".*[-./].*")) return false;
        if(value.equals("")) return false;
        return true;
    }
}
