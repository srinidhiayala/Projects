package com.example.project3;

/**
 * Performs operations on stored information about a person who has a membership at this gym
 * Can determine whether two members are the same and can evaluate if they are the right age to join the gym
 *
 * @author Srinidhi Ayalasomayajula, Palak Mehta
 */
public class Member implements Comparable<Member> {
    private String fname;
    private String lname;
    private Date dob;
    private Date expire;
    private Location location;
    public static final int COMES_FIRST = -1;
    public static final int EIGHTEEN_YEARS = 18;

    public static final double ONE_TIME_FEE = 29.99;
    public static final double QUARTERLY_FEE = 39.99;
    public static final int MONTHS_IN_QUARTER = 3;

    /**
     * Initializes a new gym.Member object with the given parameters.
     * Assigns an enum location object to the location variable based on which city it matches.
     * And if none of the 5 gym locations match the location given, set this.gym.Location to null.
     *
     * @param fname    first name of member
     * @param lname    last name of member
     * @param dob      date of birth of member as a gym.Date object
     * @param location which gym location the member joins as gym.Location object
     */
    public Member(String fname, String lname, String dob, Date expire, String location) {
        this.fname = fname;
        this.lname = lname;
        this.dob = new Date(dob);
        this.expire = expire;
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
        } else {
            this.location = null;
        }
    }


    /**
     * Returns location of which gym the member is a part.
     *
     * @return gym's location as gym.Location
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Returns first name of the member.
     *
     * @return member's first name as String
     */
    public String getFname() {
        return this.fname;
    }

    /**
     * Returns last name of member.
     *
     * @return member's last name as String
     */
    public String getLname() {
        return this.lname;
    }

    /**
     * Returns date of birth of member.
     *
     * @return member's date of birth as gym.Date
     */
    public Date getDob() {
        return this.dob;
    }

    /**
     * Returns gym expiration date of member.
     *
     * @return member's gym expiration date as gym.Date
     */
    public Date getExpire() {
        return this.expire;
    }

    /**
     * Returns string detailing all instance variables of member object.
     * If the expiration date has already expired, the string includes "Membership expired", otherwise if
     * the membership has not yet expired and will, the string includes "Membership expires".
     *
     * @return String of firstName lastName, DOB: mm/dd/yyyy, Membership expired mm/dd/yyyy, gym.Location: CITY, zipcode, COUNTY
     */
    @Override
    public String toString() {
        Date todayDate = new Date();
        if (todayDate.compareTo(getExpire()) == -1) {
            return fname + " " + lname + ", DOB: " + dob.toString() + ", Membership expired " + expire.toString() +
                    ", Location: " + location.toString();
        } else {
            return fname + " " + lname + ", DOB: " + dob.toString() + ", Membership expires " + expire.toString() +
                    ", Location: " + location.toString();
        }
    }

    /**
     * Returns if the two member objects are the same or different based on comparing first name, last name and date of birth
     * uses .equalsIgnoreCase() to account for uppercase and lowercase differences in names.
     * Two members are equal if their first name and last name are lexicographically the same without case differences
     * and their date of birth is the same (same month, day and year).
     *
     * @param obj object to be compared
     * @return true if same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Member) {
            Member member = (Member) obj;
            if (member.fname.equalsIgnoreCase(this.fname) &&
                    (member.lname.equalsIgnoreCase(this.lname)) && dob.compareTo(((Member) obj).dob) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compares two members based on last name and then first name.
     * Used when sorting members based on their full names.
     * If the last names are the same, then the method compares the first names.
     *
     * @param member the object to be compared
     * @return 1 if this.gym.Member's last name comes first or if last names are same and first name comes first and -1 if
     * this.gym.Member's last name comes second or if last names are same and first name comes second, 0 if the first and last
     * names are the same
     */
    @Override
    public int compareTo(Member member) {
        int lastName = this.lname.compareToIgnoreCase(member.getLname());
        int firstName = this.fname.compareToIgnoreCase(member.getFname());
        if (lastName == 0 && firstName == 0) {
            return 0;
        } else if (lastName < 0) {
            return 1;
        } else if (lastName > 0)
            return COMES_FIRST;
        else if (lastName == 0 && firstName < 0) {
            return 1;
        } else {
            return COMES_FIRST;
        }
    }


    /**
     * Determines if the member's date of birth is today or in the future.
     * Generates a current date and uses .equals from the gym.Date class to evaluate if the member was born today.
     * It tests if date of birth's year is in the future.
     * If the current year and date of birth year are the same, then method checks whether the birthdate month is larger,
     * and if those are also the same then it checks if the birthday day is larger to figure if it is in the future.
     *
     * @return true if date of birth is not today or in the future, false otherwise
     */
    public boolean todayOrFutureDate() {
        Date current = new Date();
        if (this.dob.equals(current) || this.dob.getYear() > current.getYear()) {
            return false;
        } else if (this.dob.getYear() == current.getYear()) {
            if (this.dob.getMonth() > current.getMonth()) {
                return false;
            } else if (this.dob.getMonth() == current.getMonth() && this.dob.getDay() > current.getDay()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if the member is 18 years or older based on birthdate.
     * It generates a current date and uses .equals from the gym.Date class to evaluate if the member was born today by subtracting 18.
     * If the difference is exactly 18, then method checks if the difference of the current month and birthdate month is
     * negative, implying less than 18. If the difference is zero, then it checks that the difference of the days
     * is not negative.
     *
     * @return true if not eighteen or older, false otherwise
     */
    public boolean eighteenOrOlder() {
        Date current = new Date();
        if (current.getYear() - this.dob.getYear() < EIGHTEEN_YEARS) {
            return false;
        } else if (current.getYear() - this.dob.getYear() == EIGHTEEN_YEARS) {
            if (current.getMonth() - this.dob.getMonth() < 0) {
                return false;
            } else if (current.getMonth() == this.dob.getMonth() && current.getDay() - this.dob.getDay() < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns String with gym.Member information with indication of membership fees printed.
     *
     * @return String of gym.Member with membership fee printed
     */
    public String memberFeePrint() {
        return this.toString() + ", Membership fee: $" + this.membershipFee();
    }

    /**
     * Returns the quarterly fee for a standard membership including the one time fee
     *
     * @return quarterly fee for membership
     */
    public double membershipFee() {
        return ONE_TIME_FEE + (QUARTERLY_FEE * MONTHS_IN_QUARTER);
    }
}