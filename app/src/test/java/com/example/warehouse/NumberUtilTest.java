package com.example.warehouse;

import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Класс локального теста ввода в числовые поля
 * @autor Сергей Курников
 * @version 1.0.0
 */
public class NumberUtilTest {

    /**
     *  Тестирование введённого значения ноль, пустоту и цифру
     */
    @Test
    public void testIsNumberCorrect() {
        assertFalse(NumberUtil.isNumberInputCorrect(""));
        assertFalse(NumberUtil.isNumberInputCorrect("0"));
        assertTrue(NumberUtil.isNumberInputCorrect("1"));
    }
}