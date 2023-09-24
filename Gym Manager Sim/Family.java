package com.example.project3;

/**
 * A membership class called gym.Family that extends gym.Member as well as additionally has guest passes to be utilized.
 *
 * @author Srinidhi Ayalasomayajula, Palak Mehta
 */
public class Family extends Member {

    private int guestPasses;
    public static final int MAX_GUEST_PASSES = 3;
    public static final double ONE_TIME_FEE = 29.99;
    public static final double QUARTERLY_FEE = 59.99;
    public static final int MONTHS_IN_QUARTER = 3;


    /**
     * Calls the gym.Member's parametrized constructor and initializes the number of guest passes to 1.
     *
     * @param fname    gym.Member's first name
     * @param lname    gym.Member's last name
     * @param dob      gym.Member's date of birth
     * @param expire   gym.Member's expiration date of gym membership
     * @param location gym.Location of gym
     */
    public Family(String fname, String lname, String dob, Date expire, String location) {
        super(fname, lname, dob, expire, location);
        guestPasses = 1;
    }

    /**
     * Provides how many guest passes are left for the family membership type.
     *
     * @return number of guest passes left
     */
    public int getGuestPasses() {
        return guestPasses;
    }

    /**
     * Sets the number of guest passes to a certain amount.
     *
     * @param guestPasses number of guess passes allowed.
     */
    public void setGuestPasses(int guestPasses) {
        this.guestPasses = guestPasses;
    }

    /**
     * Provides the quarterly fee for family membership including the one time fee.
     *
     * @return quarterly fee for a family membership
     */
    @Override
    public double membershipFee() {
        return ONE_TIME_FEE + (QUARTERLY_FEE * MONTHS_IN_QUARTER);
    }

    /**
     * Returns String information of member and number of guest passes available for the gym.Family membership.
     *
     * @return String of MEMBER, GUEST PASSES left
     */
    @Override
    public String toString() {
        if (this instanceof Premium) {
            return super.toString();
        } else {
            return super.toString() + ", (Family) guest-pass remaining: " + getGuestPasses();
        }
    }

    /**
     * Decrements the number of guest passes associated with this membership, accounting for the impossibility of
     * negative passes.
     *
     * @return true if number of guest passes were decreased, false otherwise
     */
    public boolean decreasePasses() {
        if (this.guestPasses > 0) {
            this.guestPasses--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Increases the number of guest passes for a membership, taking into account the maximum passes available are 3.
     *
     * @return true if number of guest passes were incremented, false otherwise
     */
    public boolean increasePasses() {
        if (this.guestPasses == MAX_GUEST_PASSES) {
            return false;
        } else if (this.guestPasses >= 0) {
            this.guestPasses++;
        }
        return true;
    }
}