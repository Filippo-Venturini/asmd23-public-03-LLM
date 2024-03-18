package a01a.codegeneration.githubcopilot;

import java.util.function.BiFunction;

public class TimetableFactoryImpl implements TimetableFactory {

    @Override
    public Timetable empty() {
        return new TimetableImpl();
    }

    @Override
    public Timetable single(String activity, String day) {
        return new TimetableImpl().addHour(activity, day);
    }

    @Override
    public Timetable join(Timetable table1, Timetable table2) {
        Timetable joined = new TimetableImpl();
        for (String activity : table1.activities()) {
            for (String day : table1.days()) {
                for (int i = 0; i < table1.getSingleData(activity, day); i++) {
                    joined = joined.addHour(activity, day);
                }
            }
        }
        for (String activity : table2.activities()) {
            for (String day : table2.days()) {
                for (int i = 0; i < table2.getSingleData(activity, day); i++) {
                    joined = joined.addHour(activity, day);
                }
            }
        }
        return joined;
    }

    @Override
    public Timetable cut(Timetable table, BiFunction<String, String, Integer> bounds) {
        Timetable cut = new TimetableImpl();
        for (String activity : table.activities()) {
            for (String day : table.days()) {
                cut = cut.addActivity(activity, day); // Add the activity and day to the cut timetable
                int hours = table.getSingleData(activity, day);
                int bound = bounds.apply(activity, day);
                for (int i = 0; i < Math.min(bound, hours); i++) {
                    cut = cut.addHour(activity, day);
                }
            }
        }
        return cut;
    }
}