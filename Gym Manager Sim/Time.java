package com.example.project3;

/**
 * Houses constant time objects of either afternoon or morning timing based on a certain time
 *
 * @author Srinidhi Ayalasomayajula, Palak Mehta
 */
public enum Time {
    MORNING(9, 30),
    AFTERNOON(14, 00),
    EVENING(18, 30);

    private final int hour;
    private final int minute;

    /**
     * Assigns a new enum time with the given hour and minute parameters.
     *
     * @param hour   hour of the time
     * @param minute minute of the time
     */
    Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Returns time's hour.
     *
     * @return hour of time
     */
    public int getHour() {
        return hour;
    }

    /**
     * Returns time's minute.
     *
     * @return minute of time
     */
    public int getMinute() {
        return minute;
    }
}