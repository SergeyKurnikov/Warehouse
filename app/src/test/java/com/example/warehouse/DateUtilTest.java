package com.example.warehouse;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Класс локального теста ввода даты.
 * @autor Сергей Курников
 * @version 1.0.0
 */
public class DateUtilTest {

    @Test
    public void isDateCorrect() {
        assertFalse(DateUtil.isDateCorrect("10102020"));
        assertFalse(DateUtil.isDateCorrect("101020"));
        assertTrue(DateUtil.isDateCorrect("10.10.2020"));
        assertTrue(DateUtil.isDateCorrect("10-10-2020"));
        assertTrue(DateUtil.isDateCorrect("10/10/2020"));
    }
}