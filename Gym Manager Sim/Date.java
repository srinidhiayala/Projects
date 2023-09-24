package com.example.project3;

import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Performs operations on a given or created calendar date in mm/dd/yyyy format.
 * Can assess if a date is valid, can compare 2 dates with each other and determine which one is smaller/bigger.
 * It uses the Calendar class to generate the current date.
 * And it uses the StringTokenizer class to parse the date, month and year from a string input.
 * has instance variables holding the month, date and year of the date as well as constants that are used in
 * computations to figure out if a given year is a leap year
 *
 * @author Srinidhi Ayalasomayajula, Palak Mehta
 */


public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUARTERCENTENNIAL = 400;
    public static final int OLDER_THAN = -1;
    public static final int MAX_VALID_MONTH = 12;
    public static final int JAN_MONTH = 1;
    public static final int FEB_MONTH = 2;
    public static final int MARCH_MONTH = 3;
    public static final int APRIL_MONTH = 4;
    public static final int MAY_MONTH = 5;
    public static final int JUNE_MONTH = 6;
    public static final int JULY_MONTH = 7;
    public static final int AUGUST_MONTH = 8;
    public static final int SEPTEMBER_MONTH = 9;
    public static final int OCTOBER_MONTH = 10;
    public static final int NOVEMBER_MONTH = 11;
    public static final int DECEMBER_MONTH = 12;
    public static final int LEAP_DAYS = 29;
    public static final int NOT_LEAP_DAYS = 28;

    /**
     * Generates the current date.
     * The default month range is from 0-11 so had to increment by 1 to account for how we count months (1-12).
     * It uses the calendar class's getInstance method to create the current date.
     */
    public Date() {
        Calendar today = Calendar.getInstance();
        this.year = today.get(Calendar.YEAR);
        this.month = today.get(Calendar.MONTH) + 1;
        this.day = today.get(Calendar.DATE);
    }

    /**
     * Initializes a date object given a string input in the form mm/dd/yyyy.
     * Uses StringTokenizer object with a delimiter of "/" or "-" to extract the month, day and year.
     *
     * @param date string input of the date in mm/dd/yyyy format
     */
    public Date(String date) {
        if (date.contains("/")) {
            StringTokenizer parsedDate = new StringTokenizer(date, "/");
            this.month = Integer.parseInt(parsedDate.nextToken());
            this.day = Integer.parseInt(parsedDate.nextToken());
            this.year = Integer.parseInt(parsedDate.nextToken());
        } else {
            StringTokenizer parsedDate = new StringTokenizer(date, "-");
            this.year = Integer.parseInt(parsedDate.nextToken());
            this.month = Integer.parseInt(parsedDate.nextToken());
            this.day = Integer.parseInt(parsedDate.nextToken());
        }

    }

    /**
     * Given the year, month and day, initializes a new gym.Date with the given parameters.
     *
     * @param newYear  year of date
     * @param newMonth month of date
     * @param newDay   day of date
     */
    public Date(int newYear, int newMonth, int newDay) {
        this.month = newMonth;
        this.day = newDay;
        this.year = newYear;
    }

    /**
     * Returns the instance variable year for the date object.
     *
     * @return date's year
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the instance variable day for the date object.
     *
     * @return date's day
     */
    public int getDay() {
        return day;
    }

    /**
     * Returns the instance variable month for the date object.
     *
     * @return date's month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Compares if this date is smaller than, greater than, or equal to the parameter date.
     *
     * @param date the object to be compared.
     * @return 0 if both dates are equal, -1 if this date is older, 1 if this date is younger
     */
    @Override
    public int compareTo(Date date) {
        if (date.year == this.year && date.month == this.month && date.day == this.day) {
            return 0;
        } else if (date.year > this.year) {
            return 1;
        } else if (date.year < this.year) {
            return OLDER_THAN;
        } else {
            if (date.month > this.month) {
                return 1;
            } else if (date.month < this.month) {
                return OLDER_THAN;
            } else {
                if (date.day > this.day) {
                    return 1;
                }
                return OLDER_THAN;
            }
        }
    }


    /**
     * Returns string detailing the month, date and year of the date.
     *
     * @return String of date in mm/dd/yyyy format
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    /**
     * Determines if a given date falls in the valid range for months, days and accounts for number of days in a leap year.
     * It evaluates if the month is between 1-12 and days are greater than zero.
     * Checks that if the date is in January, March, May, July, August, October or December it has a max of 31 days.
     * It checks if the date is in April, June, September, November it has a max of 30 days.
     * Also evaluates if the date is a leap year by using the constant instance variables QUADRENNIAL, CENTENNIAL,
     * QUARTERCENTENNIAL.
     * If it is a leap year and is in February, method checks that it has a max of 29 days and
     * if it is not a leap year and is in February, method checks that is has a max of 28 days.
     *
     * @return true if the date is valid, false otherwise
     */
    public boolean isValid() {
        boolean leap = false;
        boolean validDay = false;

        if (month < 1 || month > MAX_VALID_MONTH || year <= 0) {
            return false;
        }
        if (((year % QUADRENNIAL == 0) && (year % CENTENNIAL != 0)) || (year % QUARTERCENTENNIAL == 0)) {
            leap = true;
        }
        if ((month == JAN_MONTH || month == MARCH_MONTH || month == MAY_MONTH || month == JULY_MONTH || month == AUGUST_MONTH
                || month == OCTOBER_MONTH || month == DECEMBER_MONTH)
                && day <= 31 && day > 0) {
            validDay = true;
        } else if ((month == APRIL_MONTH || month == JUNE_MONTH || month == SEPTEMBER_MONTH || month == NOVEMBER_MONTH)
                && day <= 30 && day > 0) {
            validDay = true;
        } else if (month == FEB_MONTH) {
            if (leap && day <= LEAP_DAYS && day > 0) {
                validDay = true;
            } else if (day <= NOT_LEAP_DAYS && day > 0) {
                validDay = true;
            }
        }
        return validDay;
    }


}