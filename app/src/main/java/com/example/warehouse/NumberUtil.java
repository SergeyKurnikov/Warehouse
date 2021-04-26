package com.example.warehouse;

/**
 * Класс тестирования ввода в поля типа number
 * @autor Сергей Курников
 * @version 1.0.0
 */
public class NumberUtil {
    /**
     *  Проверка введёного id
     *  @param value - введенное значение id
     *  @return - результат проверки
     */
    public static boolean isNumberInputCorrect(String value) {
        if(value.equals("")) return false;
        if(value.equals("0")) return false;
        return true;
    }
}
