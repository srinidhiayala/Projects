package com.example.project3;

/**
 * A gym.Premium membership that extends the gym.Family membership.
 * This membership allows for 3 guest passes and has a different fee.
 *
 * @author Srinidhi Ayalasomayajula, Palak Mehta
 */
public class Premium extends Family {
    public static final int NUM_GUEST_PASSES = 3;
    public static final double QUARTERLY_FEE = 59.99;
    public static final int MONTHS_TO_BILL = 11;

    /**
     * Calls the superclass' parametrized constructor as well as initializes the number of guest passes to 3.
     *
     * @param fname    gym.Member first name
     * @param lname    gym.Member last name
     * @param dob      gym.Member date of birth
     * @param expire   gym.Member's membership expiration date
     * @param location location fo gym
     */
    public Premium(String fname, String lname, String dob, Date expire, String location) {
        super(fname, lname, dob, expire, location);
        Premium.super.setGuestPasses(NUM_GUEST_PASSES);
    }

    /**
     * Provides the annual membership fee for a premium membership with no one time fee and a free month waived of
     * family membership cost.
     *
     * @return annual membership fee for a premium membership
     */
    @Override
    public double membershipFee() {
        return QUARTERLY_FEE * MONTHS_TO_BILL;
    }

    /**
     * Returns String information of member and number of guest passes available for the gym.Premium membership
     *
     * @return String of MEMBER, GUEST PASSES left
     */
    @Override
    public String toString() {
        return super.toString() + ", (Premium) Guest-pass remaining: " + getGuestPasses();
    }
}