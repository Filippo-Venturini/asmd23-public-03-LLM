package a01a.githubcopilot;

import java.util.function.BiFunction;

public class TimetableFactoryImpl implements TimetableFactory{
    @Override
    public Timetable empty() {
        return null;
    }

    @Override
    public Timetable single(String activity, String day) {
        return null;
    }

    @Override
    public Timetable join(Timetable table1, Timetable table2) {
        return null;
    }

    @Override
    public Timetable cut(Timetable table, BiFunction<String, String, Integer> bounds) {
        return null;
    }
}
