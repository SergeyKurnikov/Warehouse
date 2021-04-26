package com.example.warehouse;

/**
 * Класс валидации ввода в числовые поля, содержит определение метода isNumberInputCorrect
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
