package a01a.codegeneration.chatgpt.withouttests;

import java.util.Set;

public class TimetableImpl implements Timetable {

    private final Set<String> activities;
    private final Set<String> days;
    private final int[][] data;

    public TimetableImpl(Set<String> activities, Set<String> days, int[][] data) {
        this.activities = activities;
        this.days = days;
        this.data = data;
    }

    @Override
    public Timetable addHour(String activity, String day) {
        // Copy existing data
        int[][] newData = copyDataArray();

        // Add activity and day if they are not already present
        if (!activities.contains(activity)) {
            activities.add(activity);
            newData = resizeDataArray(newData, activities.size(), days.size());
        }
        if (!days.contains(day)) {
            days.add(day);
            newData = resizeDataArray(newData, activities.size(), days.size());
        }

        // Increment the hour for the specified activity and day
        int activityIndex = getActivityIndex(activity);
        int dayIndex = getDayIndex(day);
        newData[activityIndex][dayIndex]++;

        return new TimetableImpl(activities, days, newData);
    }

    // Helper method to resize the data array when new activities or days are added
    private int[][] resizeDataArray(int[][] oldData, int newActivitySize, int newDaySize) {
        int[][] newData = new int[newActivitySize][newDaySize];
        for (int i = 0; i < Math.min(oldData.length, newActivitySize); i++) {
            System.arraycopy(oldData[i], 0, newData[i], 0, Math.min(oldData[i].length, newDaySize));
        }
        return newData;
    }

    @Override
    public Set<String> activities() {
        return activities;
    }

    @Override
    public Set<String> days() {
        return days;
    }

    @Override
    public int getSingleData(String activity, String day) {
        int activityIndex = getActivityIndex(activity);
        int dayIndex = getDayIndex(day);
        if (activityIndex == -1 || dayIndex == -1)
            return 0;
        return data[activityIndex][dayIndex];
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

    private int getActivityIndex(String activity) {
        int index = 0;
        for (String act : activities) {
            if (act.equals(activity))
                return index;
            index++;
        }
        return -1;
    }

    private int getDayIndex(String day) {
        int index = 0;
        for (String d : days) {
            if (d.equals(day))
                return index;
            index++;
        }
        return -1;
    }

    private int[][] copyDataArray() {
        int[][] newData = new int[activities.size()][days.size()];
        for (int i = 0; i < activities.size(); i++) {
            for (int j = 0; j < days.size(); j++) {
                newData[i][j] = data[i][j];
            }
        }
        return newData;
    }
}

