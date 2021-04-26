package com.example.warehouse;

/**
 * Класс валидации ввода в текстовое поле, содержит определение метода isTextCorrect
 * @autor Сергей Курников
 * @version 1.0.0
 */
public class TextUtil {
    /**
     *  Проверка введёного текста
     *  @param value - введенное значение текста
     *  @return - результат проверки
     */
    public static boolean isTextCorrect(String value) {
        if(value.equals("")) return false;
        if(value.length() < 3) return false;
        if(value.matches(".*[/&_@$*].*")) return false;
        return true;
    }
}
