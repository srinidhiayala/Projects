/**
 * Houses constant time objects of either afternoon or morning timing based on a certain time
 *
 * @author Srinidhi Ayalasomayajula, Palak Mehta
 */
public enum Time {
    MORNING(9, 30),
    AFTERNOON(14, 0);

    private final int hour;
    private final int minute;

    /**
     * Constructor- assigns a new enum time with the given hour and minute parameters
     *
     * @param hour   hour of the time
     * @param minute minute of the time
     */
    Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }
}