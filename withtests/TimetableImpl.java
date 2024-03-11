package a01a.withtests;

import a01a.withouttests.Timetable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TimetableImpl implements Timetable {

    public final Map<String, Map<String, Integer>> data; // Map<activity, Map<day, hours>>
    public final Set<String> daysSet; // Set of all days

    public TimetableImpl() {
        this.data = new HashMap<>();
        this.daysSet = new HashSet<>();
    }

    @Override
    public Timetable addHour(String activity, String day) {
        TimetableImpl newTimetable = new TimetableImpl();

        // Copy existing data and daysSet
        for (Map.Entry<String, Map<String, Integer>> entry : data.entrySet()) {
            String existingActivity = entry.getKey();
            Map<String, Integer> existingDayMap = entry.getValue();
            for (Map.Entry<String, Integer> dayEntry : existingDayMap.entrySet()) {
                String existingDay = dayEntry.getKey();
                int hours = dayEntry.getValue();
                newTimetable.data.putIfAbsent(existingActivity, new HashMap<>());
                newTimetable.data.get(existingActivity).put(existingDay, hours);
                newTimetable.daysSet.add(existingDay); // Update daysSet
            }
        }

        // Add new hour
        newTimetable.data.putIfAbsent(activity, new HashMap<>());
        newTimetable.data.get(activity).put(day, newTimetable.getSingleData(activity, day) + 1);
        newTimetable.daysSet.add(day); // Update daysSet

        return newTimetable;
    }

    @Override
    public Set<String> activities() {
        return data.keySet();
    }

    @Override
    public Set<String> days() {
        return daysSet; // Return the pre-maintained set of days
    }

    @Override
    public int getSingleData(String activity, String day) {
        return data.getOrDefault(activity, new HashMap<>()).getOrDefault(day, 0);
    }

    @Override
    public int sums(Set<String> activities, Set<String> days) {
        int sum = 0;
        for (String activity : activities) {
            for (String day : days) {
                sum += getSingleData(activity, day);
            }
        }
        return sum;
    }
}

