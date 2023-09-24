package com.example.project3;

import java.util.ArrayList;

/**
 * This class uses the gym.Fitness class to show the location associated with each class during the day.
 *
 * @author Srinidhi Ayalasomayajula, Palak Mehta
 */
public class FitnessClass {
    private Fitness course;
    private Location location;
    public static final int NOT_FOUND = -1;
    private ArrayList<Member> classMembers = new ArrayList<Member>();
    private ArrayList<Member> guests = new ArrayList<>();
    private String[] eachPhraseArray = new String[SIZE_OF_INPUT];
    public static final int SIZE_OF_INPUT = 6;
    private Date todayDate = new Date();
    public static final int NOT_EQUAL = -1;


    /**
     * Initializes instance variables by taking in className, instructor, time and location parameters.
     *
     * @param className  name of the class as String
     * @param instructor name of instructor as String
     * @param time       gym.Time object of what time the class starts
     * @param location   gym.Location object of where the class is
     */
    public FitnessClass(String className, String instructor, String time, String location) {
        this.course = new Fitness(className, instructor, time);
        if (location.equalsIgnoreCase("Bridgewater")) {
            this.location = Location.BRIDGEWATER;
        } else if (location.equalsIgnoreCase("Edison")) {
            this.location = Location.EDISON;
        } else if ((location.equalsIgnoreCase("Piscataway"))) {
            this.location = Location.PISCATAWAY;
        } else if ((location.equalsIgnoreCase("Franklin"))) {
            this.location = Location.FRANKLIN;
        } else if ((location.equalsIgnoreCase("Somerville"))) {
            this.location = Location.SOMERVILLE;
        }
    }

    /**
     * Returns the courses of type gym.Fitness that has the className, instructorName, time.
     *
     * @return gym.Fitness the gym.Fitness object's instance
     */
    public Fitness getCourse() {
        return course;
    }

    /**
     * Returns the arraylist that holds all the members that have checked into the class.
     *
     * @return The arraylist that has member objects
     */
    public ArrayList<Member> getClassMembers() {
        return classMembers;
    }

    /**
     * Returns the location of the course of type String.
     *
     * @return gym.Location the gym.Location object's instance
     */
    public Location getLocation() {
        return location;
    }


    /**
     * Returns String information of instance variables.
     *
     * @return String of CLASSNAME - INSTRUCTOR, MM:HH (time), CITY
     */
    @Override
    public String toString() {
        return course.toString() + ", " + location.getCity().toUpperCase();
    }

    /**
     * Returns the arraylist that holds all the guests that have checked into the class.
     *
     * @return The arraylist that has member objects representing the checked in guests
     */
    public ArrayList<Member> getGuests() {
        return this.guests;
    }

    /**
     * Adds member to class array list.
     *
     * @param member Member to be added
     * @return true if member is added, false otherwise
     */
    public boolean addMember(Member member) {
        if (!member.getDob().isValid()) { //invalid calendar date
            return false;
        } else if (new Date().compareTo(member.getExpire()) == NOT_EQUAL) //membership expired
        {
            return false;
        }
        for (int i = 0; i < classMembers.size(); i++) {
            if (classMembers.get(i).equals(member)) { //already checked in
                return false;
            } else {
                classMembers.add(member);
                return true;
            }
        }
        return true;
    }

    /**
     * Adds guest to class array list.
     *
     * @param member Member to be added
     * @return true if guest is added, false otherwise
     */
    public boolean addGuest(Member member) {
        if (!member.getDob().isValid()) { //invalid calendar date
            return false;
        } else if (new Date().compareTo(member.getExpire()) == NOT_EQUAL) //membership expired
        {
            return false;
        }
        for (int i = 0; i < guests.size(); i++) {
            if (guests.get(i).equals(member)) {//already checked in
                return false;
            } else {
                guests.add(member);
                return true;
            }
        }
        return true;
    }

    /**
     * Remove guest from array list
     *
     * @param member Member to be removed
     * @return true if guest is removed, false otherwise
     */
    public boolean removeGuest(Member member) {
        for (int i = 0; i < guests.size(); i++) {
            if (guests.get(i).equals(member)) {
                guests.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes member from class array list.
     *
     * @param member Member to be removed
     * @return true if guest is removed, false otherwise
     */
    public boolean removeMember(Member member) {
        for (int i = 0; i < classMembers.size(); i++) {
            if (classMembers.get(i).equals(member)) {
                classMembers.remove(i);
                return true;
            }
        }
        return false;
    }
}



