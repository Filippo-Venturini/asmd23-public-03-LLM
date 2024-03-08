package a01a.withouttests;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class TimetableFactoryImpl implements TimetableFactory {

    @Override
    public Timetable empty() {
        return new TimetableImpl(new HashSet<>(), new HashSet<>(), new int[0][0]);
    }

    @Override
    public Timetable single(String activity, String day) {
        Set<String> activities = new HashSet<>();
        activities.add(activity);
        Set<String> days = new HashSet<>();
        days.add(day);
        int[][] data = new int[1][1];
        return new TimetableImpl(activities, days, data);
    }

    @Override
    public Timetable join(Timetable table1, Timetable table2) {
        Set<String> activities = new HashSet<>(table1.activities());
        activities.addAll(table2.activities());
        Set<String> days = new HashSet<>(table1.days());
        days.addAll(table2.days());
        int[][] newData = new int[activities.size()][days.size()];
        for (int i = 0; i < activities.size(); i++) {
            for (int j = 0; j < days.size(); j++) {
                String activity = (String) activities.toArray()[i];
                String day = (String) days.toArray()[j];
                newData[i][j] = table1.getSingleData(activity, day) + table2.getSingleData(activity, day);
            }
        }
        return new TimetableImpl(activities, days, newData);
    }

    @Override
    public Timetable cut(Timetable table, BiFunction<String, String, Integer> bounds) {
        Set<String> activities = table.activities();
        Set<String> days = table.days();
        int[][] newData = new int[activities.size()][days.size()];
        for (int i = 0; i < activities.size(); i++) {
            for (int j = 0; j < days.size(); j++) {
                String activity = (String) activities.toArray()[i];
                String day = (String) days.toArray()[j];
                int hours = table.getSingleData(activity, day);
                int maxHours = bounds.apply(activity, day);
                newData[i][j] = Math.min(hours, maxHours);
            }
        }
        return new TimetableImpl(activities, days, newData);
    }
}
