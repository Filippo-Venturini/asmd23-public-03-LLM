package a01a.testing;

import static org.junit.Assert.*;

import java.util.*;
import java.util.function.BiFunction;

public class Test {
    @org.junit.Test
    public void testEmpty() {
        TimetableFactoryImpl factory = new TimetableFactoryImpl();
        Timetable emptyTimetable = factory.empty();
        assertNotNull(emptyTimetable);
        assertEquals(0, emptyTimetable.sums(Set.of(), Set.of()));
    }

    @org.junit.Test
    public void testSingle() {
        TimetableFactoryImpl factory = new TimetableFactoryImpl();
        Timetable singleTimetable = factory.single("Math", "Monday");
        assertNotNull(singleTimetable);
        assertEquals(1, singleTimetable.sums(Set.of("Math"), Set.of("Monday")));
        assertEquals(0, singleTimetable.sums(Set.of("Math"), Set.of("Tuesday")));
    }

    @org.junit.Test
    public void testJoin() {
        TimetableFactoryImpl factory = new TimetableFactoryImpl();
        Timetable table1 = factory.single("Math", "Monday");
        Timetable table2 = factory.single("Science", "Tuesday");
        Timetable joinedTimetable = factory.join(table1, table2);
        assertNotNull(joinedTimetable);
        assertEquals(1, joinedTimetable.sums(Set.of("Math"), Set.of("Monday")));
        assertEquals(1, joinedTimetable.sums(Set.of("Science"), Set.of("Tuesday")));
        assertEquals(0, joinedTimetable.sums(Set.of("Math"), Set.of("Tuesday")));
    }

    @org.junit.Test
    public void testCut() {
        TimetableFactoryImpl factory = new TimetableFactoryImpl();
        Timetable table = factory.single("Math", "Monday");
        BiFunction<String, String, Integer> bounds = (a, d) -> 0; // Cut to zero hours
        Timetable cutTimetable = factory.cut(table, bounds);
        assertNotNull(cutTimetable);
        assertEquals(0, cutTimetable.sums(Set.of("Math"), Set.of("Monday")));
    }

    @org.junit.Test
    public void testAddHour() {
        TimetableFactoryImpl factory = new TimetableFactoryImpl();
        Timetable timetable = factory.single("Math", "Monday");
        Timetable updatedTimetable = timetable.addHour("Math", "Monday");
        assertEquals(2, updatedTimetable.sums(Set.of("Math"), Set.of("Monday")));
    }

    @org.junit.Test
    public void testJoinMultiple() {
        TimetableFactoryImpl factory = new TimetableFactoryImpl();
        Timetable table1 = factory.single("Math", "Monday");
        Timetable table2 = factory.single("Science", "Monday");
        Timetable table3 = factory.single("Math", "Tuesday");
        Timetable table4 = factory.single("Science", "Tuesday");
        Timetable joinedTimetable = factory.join(factory.join(table1, table2), factory.join(table3, table4));
        assertEquals(1, joinedTimetable.sums(Set.of("Math"), Set.of("Monday")));
        assertEquals(1, joinedTimetable.sums(Set.of("Science"), Set.of("Tuesday")));
    }
}