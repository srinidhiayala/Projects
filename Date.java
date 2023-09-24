import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Performs operations on a given or created calendar date in mm/dd/yyyy format
 * can assess if a date is valid, can compare 2 dates with each other and determine which one is smaller/bigger
 * uses the Calendar class to generate the current date
 * uses the StringTokenizer class to parse the date, month and year from a string input
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

    /**
     * Constructor- generates the current date
     * default month range is from 0-11 so had to increment by 1 to account for how we count months (1-12)
     * Uses the calendar class's getInstance method to create the current date
     */
    public Date() {
        Calendar today = Calendar.getInstance();
        this.year = today.get(Calendar.YEAR);
        this.month = today.get(Calendar.MONTH) + 1;
        this.day = today.get(Calendar.DATE);
    }

    /**
     * Constructor- initializes a date object given a string input in the form mm/dd/yyyy
     * uses StringTokenizer object with a delimiter of "/" to extract the month, day and year
     *
     * @param date string input of the date in mm/dd/yyyy format
     */
    public Date(String date) {
        StringTokenizer parsedDate = new StringTokenizer(date, "/");
        this.month = Integer.parseInt(parsedDate.nextToken());
        this.day = Integer.parseInt(parsedDate.nextToken());
        this.year = Integer.parseInt(parsedDate.nextToken());
    }

    /**
     * Returns the instance variable year for the date object
     *
     * @return date's year
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the instance variable day for the date object
     *
     * @return date's day
     */
    public int getDay() {
        return day;
    }

    /**
     * Returns the instance variable month for the date object
     *
     * @return date's month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Compares if this date is smaller than, greater than, or equal to the parameter date
     *
     * @param date the object to be compared.
     * @return 0 if both dates are equal, -1 if this date is older, 1 if this date is younger
     */
    @Override
    public int compareTo(Date date) {
        final int OLDER_THAN = -1;
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
     * Returns string detailing the month, date and year of the date
     *
     * @return String of date in mm/dd/yyyy format
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    /**
     * Determines if a given date falls in the valid range for months, days and accounts for number of days in a leap year
     * evaluates if the month is between 1-12 and days are greater than zero
     * checks that if the date is in January, March, May, July, August, October or December it has a max of 31 days
     * checks if the date is in April, June, September, November it has a max of 30 days
     * evaluates if the date is a leap year by using the constant instance variables QUADRENNIAL, CENTENNIAL, QUARTERCENTENNIAL
     * if it is a leap year and is in February, method checks that it has a max of 29 days
     * if it is not a leap year and is in February, method checks that is has a max of 28 days
     *
     * @return true if the date is valid, false otherwise
     */
    public boolean isValid() {
        boolean leap = false;
        boolean validDay = false;
        final int MAX_VALID_MONTH = 12;
        final int JAN_MONTH = 1;
        final int FEB_MONTH = 2;
        final int MARCH_MONTH = 3;
        final int APRIL_MONTH = 4;
        final int MAY_MONTH = 5;
        final int JUNE_MONTH = 6;
        final int JULY_MONTH = 7;
        final int AUGUST_MONTH = 8;
        final int SEPTEMBER_MONTH = 9;
        final int OCTOBER_MONTH = 10;
        final int NOVEMBER_MONTH = 11;
        final int DECEMBER_MONTH = 12;
        final int LEAP_DAYS = 29;
        final int NOT_LEAP_DAYS = 28;

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

    private static void testResult(Date date, boolean expectedOutput, boolean actualOutput) {
        System.out.println(date.toString());
        System.out.print("isValid() return " + actualOutput);
        if (actualOutput == expectedOutput) {
            System.out.println(", PASS.\n");
        } else {
            System.out.println(", FAIL.\n");
        }
    }

    /**
     * uses system prints to call the isValid() method and evaluate if the actual result is
     * same as the expected outlined in the design test cases
     *
     * @param args input from command line (not used)
     */
    public static void main(String[] args) {
        Date date;
        boolean expectedOutput;
        boolean actualOutput;

        //1
        date = new Date("13/8/1977");
        expectedOutput = false;
        actualOutput = date.isValid();
        System.out.println("Test Case #1 with valid range for month between 1-12: ");
        testResult(date, expectedOutput, actualOutput);
        //2
        date = new Date("0/14/2004");
        expectedOutput = false;
        actualOutput = date.isValid();
        System.out.println("Test Case #2 with valid range for month between 1-12: ");
        testResult(date, expectedOutput, actualOutput);
        //3
        date = new Date("9/23/2002");
        expectedOutput = true;
        actualOutput = date.isValid();
        System.out.println("Test Case #3 with valid range for month between 1-12: ");
        testResult(date, expectedOutput, actualOutput);
        //4
        date = new Date("1/31/1990");
        expectedOutput = true;
        actualOutput = date.isValid();
        System.out.println("Test Case #4 Jan, March, May, July, August, October and December shall have 31 days: ");
        testResult(date, expectedOutput, actualOutput);
        //5
        date = new Date("12/31/2005");
        expectedOutput = true;
        actualOutput = date.isValid();
        System.out.println("Test Case #5 Jan, March, May, July, August, October and December shall have 31 days: ");
        testResult(date, expectedOutput, actualOutput);
        //6
        date = new Date("4/31/2006");
        expectedOutput = false;
        actualOutput = date.isValid();
        System.out.println("Test Case #6 Jan, March, May, July, August, October and December shall have 31 days: ");
        testResult(date, expectedOutput, actualOutput);
        //7
        date = new Date("6/30/1999");
        expectedOutput = true;
        actualOutput = date.isValid();
        System.out.println("Test Case #7 April, June, September and November shall have 30 days: ");
        testResult(date, expectedOutput, actualOutput);
        //8
        date = new Date("4/30/1976");
        expectedOutput = true;
        actualOutput = date.isValid();
        System.out.println("Test Case #8 April, June, September and November shall have 30 days: ");
        testResult(date, expectedOutput, actualOutput);
        //9
        date = new Date("6/31/1965");
        expectedOutput = false;
        actualOutput = date.isValid();
        System.out.println("Test Case #9 April, June, September and November shall have 30 days: ");
        testResult(date, expectedOutput, actualOutput);
        //10
        date = new Date("-1/31/2003");
        expectedOutput = false;
        actualOutput = date.isValid();
        System.out.println("Test Case #10 Month, day and year shall be positive (greater than zero): ");
        testResult(date, expectedOutput, actualOutput);
        //11
        date = new Date("4/-8/2003");
        expectedOutput = false;
        actualOutput = date.isValid();
        System.out.println("Test Case #11 Month, day and year shall be positive (greater than zero): ");
        testResult(date, expectedOutput, actualOutput);
        //12
        date = new Date("6/17/-2005");
        expectedOutput = false;
        actualOutput = date.isValid();
        System.out.println("Test Case #12 Month, day and year shall be positive (greater than zero): ");
        testResult(date, expectedOutput, actualOutput);
        //13
        date = new Date("8/24/2007");
        expectedOutput = true;
        actualOutput = date.isValid();
        System.out.println("Test Case #13 Month, day and year shall be positive (greater than zero): ");
        testResult(date, expectedOutput, actualOutput);
        //14
        date = new Date("2/29/2003");
        expectedOutput = false;
        actualOutput = date.isValid();
        System.out.println("Test Case #14 Number of days in Feb for a non leap year shall be 28: ");
        testResult(date, expectedOutput, actualOutput);
        //15
        date = new Date("2/29/2000");
        expectedOutput = true;
        actualOutput = date.isValid();
        System.out.println("Test Case #15 Number of days in Feb for a non leap year shall be 28: ");
        testResult(date, expectedOutput, actualOutput);
        //16
        date = new Date("2/13/2022");
        expectedOutput = true;
        actualOutput = date.isValid();
        System.out.println("Test Case #16 Number of days in Feb for a non leap year shall be 28: ");
        testResult(date, expectedOutput, actualOutput);
        //17
        date = new Date("2/30/2003");
        expectedOutput = false;
        actualOutput = date.isValid();
        System.out.println("Test Case #17 Number of days in Feb shall never exceed 29: ");
        testResult(date, expectedOutput, actualOutput);
        //18
        date = new Date("3/30/1990");
        expectedOutput = true;
        actualOutput = date.isValid();
        System.out.println("Test Case #18 Jan, March, May, July, August, October and December shall have 31 days: ");
        testResult(date, expectedOutput, actualOutput);
        //19
        date = new Date("5/30/1989");
        expectedOutput = true;
        actualOutput = date.isValid();
        System.out.println("Test Case #19 Jan, March, May, July, August, October and December shall have 31 days: ");
        testResult(date, expectedOutput, actualOutput);
        //20
        date = new Date("7/32/1993");
        expectedOutput = false;
        actualOutput = date.isValid();
        System.out.println("Test Case #20 Jan, March, May, July, August, October and December shall have 31 days: ");
        testResult(date, expectedOutput, actualOutput);
        //21
        date = new Date("8/32/1995");
        expectedOutput = false;
        actualOutput = date.isValid();
        System.out.println("Test Case #21 Jan, March, May, July, August, October and December shall have 31 days: ");
        testResult(date, expectedOutput, actualOutput);
        //22
        date = new Date("10/31/1991");
        expectedOutput = true;
        actualOutput = date.isValid();
        System.out.println("Test Case #22 Jan, March, May, July, August, October and December shall have 31 days: ");
        testResult(date, expectedOutput, actualOutput);
        //23
        date = new Date("12/31/1990");
        expectedOutput = true;
        actualOutput = date.isValid();
        System.out.println("Test Case #23 Jan, March, May, July, August, October and December shall have 31 days: ");
        testResult(date, expectedOutput, actualOutput);
        //24
        date = new Date("12/32/1998");
        expectedOutput = false;
        actualOutput = date.isValid();
        System.out.println("Test Case #24 Jan, March, May, July, August, October and December shall have 31 days: ");
        testResult(date, expectedOutput, actualOutput);
        //25
        date = new Date("12/32/1998");
        expectedOutput = false;
        actualOutput = date.isValid();
        System.out.println("Test Case #25 April, June, September and November shall have 30 days: ");
        testResult(date, expectedOutput, actualOutput);
        //26
        date = new Date("11/30/2004");
        expectedOutput = true;
        actualOutput = date.isValid();
        System.out.println("Test Case #26 April, June, September and November shall have 30 days:");
        testResult(date, expectedOutput, actualOutput);
        //27
        date = new Date("11/31/2008");
        expectedOutput = false;
        actualOutput = date.isValid();
        System.out.println("Test Case #27 April, June, September and November shall have 30 days: ");
        testResult(date, expectedOutput, actualOutput);
        //28
        date = new Date("10/32/1989");
        expectedOutput = false;
        actualOutput = date.isValid();
        System.out.println("Test Case #28 Valid number of days must never exceed 31: ");
        testResult(date, expectedOutput, actualOutput);
        //29
        date = new Date("7/15/1977");
        expectedOutput = true;
        actualOutput = date.isValid();
        System.out.println("Test Case #29 Valid number of days must never exceed 31: ");
        testResult(date, expectedOutput, actualOutput);
        //30
        date = new Date();
        expectedOutput = true;
        actualOutput = date.isValid();
        System.out.println("Test Case #30 Current Date is valid: ");
        testResult(date, expectedOutput, actualOutput);
    }


}


