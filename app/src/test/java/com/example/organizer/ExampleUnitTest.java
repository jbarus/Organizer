package com.example.organizer;

import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void getStringFromDate_isCorrect(){
        String expected = "2023-01-08";
        String actual = DatabasesManager.getStringFromDate(LocalDate.of(2023,1,8));
        assertEquals(expected,actual);
    }

    @Test
    public void getStringFromTime_isCorrect(){
        String expected = "18:38:25";
        String actual = DatabasesManager.getStringFromTime(LocalTime.of(18,38,25));
        assertEquals(expected,actual);
    }

    @Test
    public void previousMonthAction_isCorect(){
        Event event = new Event("Test",LocalDate.now(),LocalTime.now(),LocalTime.of(20,20,20),"Sport","Notatka");
        String expected = "Test";
        String actual = event.getName();
        assertEquals(expected,actual);
    }
}