package a01a.chatgpt.withtests;

import a01a.chatgpt.withouttests.Timetable;
import a01a.chatgpt.withouttests.TimetableFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class TimetableFactoryImpl implements TimetableFactory {

    @Override
    public Timetable empty() {
        return new TimetableImpl();
    }

    @Override
    public Timetable single(String activity, String day) {
        Timetable timetable = new TimetableImpl();
        return timetable.addHour(activity, day);
    }

    @Override
    public Timetable join(Timetable table1, Timetable table2) {
        Timetable joinedTimetable = new TimetableImpl();

        // Merge activities
        Set<String> activities = new HashSet<>(table1.activities());
        activities.addAll(table2.activities());

        // Merge days
        Set<String> days = new HashSet<>(table1.days());
        days.addAll(table2.days());

        // Sum hours for each activity and day
        for (String activity : activities) {
            for (String day : days) {
                int hours = table1.getSingleData(activity, day) + table2.getSingleData(activity, day);
                if (hours > 0) {
                    joinedTimetable = joinedTimetable.addHour(activity, day);
                    for (int i = 1; i < hours; i++) {
                        joinedTimetable = joinedTimetable.addHour(activity, day);
                    }
                }
            }
        }

        return joinedTimetable;
    }

    @Override
    public Timetable cut(Timetable table, BiFunction<String, String, Integer> bounds) {
        TimetableImpl cutTimetable = new TimetableImpl();

        // Copy existing activities and days, initializing hours to zero
        for (String activity : table.activities()) {
            for (String day : table.days()) {
                int maxHours = bounds.apply(activity, day);
                int currentHours = table.getSingleData(activity, day);
                int hoursToAdd = Math.min(maxHours, currentHours);

                // Add only the allowed number of hours for this activity on this day
                for (int i = 0; i < hoursToAdd; i++) {
                    cutTimetable = (TimetableImpl) cutTimetable.addHour(activity, day);
                }

                // If no hours to add, add the activity and day with zero hours
                if (hoursToAdd == 0) {
                    cutTimetable.data.putIfAbsent(activity, new HashMap<>());
                    cutTimetable.data.get(activity).put(day, 0);
                    cutTimetable.daysSet.add(day);
                }
            }
        }

        return cutTimetable;
    }





}
