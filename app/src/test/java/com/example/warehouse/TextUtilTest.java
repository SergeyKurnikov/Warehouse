package com.example.warehouse;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Класс локального теста ввода в текстовое поле
 * @autor Сергей Курников
 * @version 1.0.0
 */
public class TextUtilTest {
    /**
     *  Тестирование введённого значения на пустоту и спецсимволы
     */
    @Test
    public void testIsTextCorrect() {
        assertFalse(TextUtil.isTextCorrect(""));
        assertFalse(TextUtil.isTextCorrect("tr"));
        assertFalse(TextUtil.isTextCorrect("cpu&"));
        assertFalse(TextUtil.isTextCorrect("/cpu"));
        assertFalse(TextUtil.isTextCorrect("_cpu"));
        assertFalse(TextUtil.isTextCorrect("@cpu"));
        assertFalse(TextUtil.isTextCorrect("$cpu"));
        assertFalse(TextUtil.isTextCorrect("*cpu"));
        assertTrue(TextUtil.isTextCorrect("cpu"));
    }
}