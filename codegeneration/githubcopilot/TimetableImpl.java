package a01a.codegeneration.githubcopilot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TimetableImpl implements Timetable{
    private final Map<String, Map<String, Integer>> timetable;

    public TimetableImpl() {
        this.timetable = new HashMap<>();
    }

    @Override
    public Timetable addHour(String activity, String day) {
        this.timetable.computeIfAbsent(activity, k -> new HashMap<>()).merge(day, 1, Integer::sum);
        return this;
    }

    @Override
    public Set<String> activities() {
        return this.timetable.keySet();
    }

    @Override
    public Set<String> days() {
        Set<String> days = new HashSet<>();
        for (Map<String, Integer> dayMap : this.timetable.values()) {
            days.addAll(dayMap.keySet());
        }
        return days;
    }

    @Override
    public int getSingleData(String activity, String day) {
        return this.timetable.getOrDefault(activity, new HashMap<>()).getOrDefault(day, 0);
    }

    @Override
    public int sums(Set<String> activities, Set<String> days) {
        int sum = 0;
        for (String activity : activities) {
            Map<String, Integer> dayMap = this.timetable.get(activity);
            if (dayMap != null) {
                for (String day : days) {
                    sum += dayMap.getOrDefault(day, 0);
                }
            }
        }
        return sum;
    }

    @Override
    public Timetable addActivity(String activity, String day) {
        this.timetable.computeIfAbsent(activity, k -> new HashMap<>()).putIfAbsent(day, 0);
        return this;
    }

}