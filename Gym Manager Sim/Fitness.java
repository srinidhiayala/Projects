package com.example.project3;

/**
 * Handles memberships of members at the gym for a gym.Fitness class and checks in
 * gym members to the respective fitness class. Checks in a member and adds a
 * member's name to a certain fitness class. Cancels a member's membership to a
 * fitness class.
 *
 * @author Srinidhi Ayalasomayajula, Palak Mehta
 */
public class Fitness {

    private String className;
    private String instructorName;
    private Time time;
    public static final int MEMBERS_LIST_SIZE = 100;
    private Member[] membersList = new Member[MEMBERS_LIST_SIZE];
    private int size = 0;
    public static final int EXPIRED = -1;

    /**
     * Initializes a gym.Fitness object with a class name,
     * instructor name, and time of class based on timing.
     *
     * @param className      name of fitness class
     * @param instructorName instructor name
     * @param time           time of class
     */
    public Fitness(String className, String instructorName, String time) {
        this.className = className;
        this.instructorName = instructorName;
        if (time.equalsIgnoreCase("morning"))
            this.time = Time.MORNING;
        else if (time.equalsIgnoreCase("afternoon"))
            this.time = Time.AFTERNOON;
        else if (time.equalsIgnoreCase("evening"))
            this.time = Time.EVENING;
        size = 0;
    }

    /**
     * Returns the name of the fitness class.
     *
     * @return fitness class name as String
     */
    public String getClassName() {
        return className;
    }

    /**
     * Returns the time of the fitness class.
     *
     * @return fitness class time as gym.Time
     */
    public Time getTime() {
        return time;
    }

    /**
     * Returns the name of the fitness instructor who is teaching a fitness class.
     *
     * @return fitness class instructor name as String
     */
    public String getInstructor() {
        return instructorName;
    }


    /**
     * Returns string of information about a class.
     * Concatenates an extra "0" to the end of the afternoon class to make the minute "00".
     *
     * @return String of CLASSNAME - INSTRUCTOR, MM:HH (time)
     */
    @Override
    public String toString() {
        if (time.getHour() == 14)
            return className.toUpperCase() + " - " + instructorName.toUpperCase() + ", " + time.getHour() + ":" +
                    time.getMinute() + "0";
        else
            return className.toUpperCase() + " - " + instructorName.toUpperCase() + ", " + time.getHour() + ":" +
                    time.getMinute();
    }

}