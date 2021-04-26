package com.example.warehouse;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
/**
 * Класс инструментального теста Activity "Администрирование" ввщд значениЙ
 * @autor Сергей Курников
 * @version 1.0.0
 */
public class AdminActivityTest {
    /**
     *  Аннотация для запуска главной Activity
     */
    @Rule
    public ActivityScenarioRule<AdminActivity> mActivityRule = new ActivityScenarioRule<>(AdminActivity.class);

    /**
     *  Метод тестирования ввода данных в форму и нажатия кнопки "ПРОВЕРИТЬ"
     */
    @Test
    public void onClick() {
        Espresso.onView(ViewMatchers.withId(R.id.edtId)).perform(ViewActions.typeText("100"), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.edtName)).perform(ViewActions.typeText("Test123"), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.edtType)).perform(ViewActions.typeText("TestCPU"), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.edtNumber)).perform(ViewActions.typeText("10"), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.edtProvider)).perform(ViewActions.typeText("TestCompany"), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.edtDate)).perform(ViewActions.typeText("26.04.2021"), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.btnAdd)).perform(ViewActions.click());
    }
}