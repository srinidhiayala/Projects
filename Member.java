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

    /**
     * Constructor- initializes a new Member object with the given parameters
     * assigns an enum location object to the location variable based on which city it matches
     * if none of the 5 gym locations match the location given, set this.Location to null
     *
     * @param fname    first name of member
     * @param lname    last name of member
     * @param dob      date of birth of member as a Date object
     * @param expire   date of member's expiration as Date object
     * @param location which gym location the member joins as Location object
     */
    public Member(String fname, String lname, String dob, String expire, String location) {
        this.fname = fname;
        this.lname = lname;
        this.dob = new Date(dob);
        this.expire = new Date(expire);
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
     * Constructor- creates a Member object with a first and last name
     *
     * @param fname first name
     * @param lname last name
     */
    public Member(String fname, String lname) {
        this.fname = fname;
        this.lname = lname;
    }

    /**
     * Returns location of which gym the member is a part
     *
     * @return gym's location as Location
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Returns first name of the member
     *
     * @return member's first name as String
     */
    public String getFname() {
        return this.fname;
    }

    /**
     * Returns last name of member
     *
     * @return member's last name as String
     */
    public String getLname() {
        return this.lname;
    }

    /**
     * Returns date of birth of member
     *
     * @return member's date of birth as Date
     */
    public Date getDob() {
        return this.dob;
    }

    /**
     * Returns gym expiration date of member
     *
     * @return member's gym expiration date as Date
     */
    public Date getExpire() {
        return this.expire;
    }

    /**
     * Returns string detailing all instance variables of member object
     * if the expiration date has already expired, the string includes "Membership expired", otherwise if
     * the membership has not yet expired and will, the string includes "Membership expires"
     *
     * @return String of firstName lastName, DOB: mm/dd/yyyy, Membership expired mm/dd/yyyy, Location: CITY, zipcode, COUNTY
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
     * uses .equalsIgnoreCase() to account for uppercase and lowercase differences in names
     * two members are equal if their first name and last name are lexicographically the same without case differences
     * and their date of birth is the same (same month, day and year)
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
     * Compares two members based on last name and then first name
     * this method is used when sorting members based on their full names
     * if the last names are the same, then the method compares the first names
     *
     * @param member the object to be compared
     * @return 1 if this.Member's last name comes first or if last names are same and first name comes first and -1 if
     * this.Member's last name comes second or if last names are same and first name comes second, 0 if the first and last
     * names are the same
     */
    @Override
    public int compareTo(Member member) {
        final int COMES_FIRST = -1;
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
     * Determines if the member's date of birth is today or in the future
     * generates a current date and uses .equals from the Date class to evaluate if the member was born today
     * tests if date of birth's year is in the future
     * if the current year and date of birth year are the same, then method checks whether the birthdate month is larger,
     * if those are also the same then it checks if the birthday day is larger to figure if it is in the future
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
     * Determines if the member is 18 years or older based on birthdate
     * generates a current date and uses .equals from the Date class to evaluate if the member was born today by subtracting 18
     * if the difference is exactly 18, then method checks if the difference of the current month and birthdate month is
     * negative, implying less than 18. If the difference is zero, then it checks that the difference of the days
     * is not negative
     *
     * @return true if not eighteen or older, false otherwise
     */
    public boolean eighteenOrOlder() {
        Date current = new Date();
        final int EIGHTEEN_YEARS = 18;
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
     * Helps format the results of the test cases and evaluates if the expected and
     * actual outputs match.
     *
     * @param member1        first member to be compared to the second member
     * @param member2        second member to be compared to the first member
     * @param expectedOutput the output that is expected
     * @param actualOutput   the output that is recieved
     */
    private static void testResult(Member member1, Member member2, int expectedOutput, int actualOutput) {
        System.out.println(member1.fname + " " + member1.lname);
        System.out.println(member2.fname + " " + member2.lname);
        System.out.print(".compareTo() returns " + actualOutput);
        if (actualOutput == expectedOutput) {
            System.out.println(", PASS.\n");
        } else {
            System.out.println(", FAIL.\n");
        }
    }

    /**
     * uses system prints to call the compareTo() method and evaluate if the actual
     * result is same as the expected outlined in the design test cases
     *
     * @param args input from command line (not used)
     */
    public static void main(String[] args) {
// 1
        Member member1 = new Member("John", "Doe");
        Member member2 = new Member("John", "Doe");
        int expectedOutput = 0;
        int actualOutput = member1.compareTo(member2);
        System.out.println("**Test case #1: compare instances of Member that have the same first and last name");
        testResult(member1, member2, expectedOutput, actualOutput);

// 2
        member1 = new Member("John", "Doe");
        member2 = new Member("Jane", "Doe");
        expectedOutput = -1;
        actualOutput = member1.compareTo(member2);
        System.out.println(
                "**Test case #2: Compare instances of Member that have the same last name and the argument member has a lexicographically smaller first name");
        testResult(member1, member2, expectedOutput, actualOutput);

// 3
        member1 = new Member("Jane", "Doe");
        member2 = new Member("John", "Doe");
        expectedOutput = 1;
        actualOutput = member1.compareTo(member2);
        System.out.println(
                "**Test case #3: Compare instances of Member that have the same last name and the instance member has a lexicographically smaller first name");
        testResult(member1, member2, expectedOutput, actualOutput);

// 4
        member1 = new Member("April", "March");
        member2 = new Member("John", "Doe");
        expectedOutput = -1;
        actualOutput = member1.compareTo(member2);
        System.out.println(
                "**Test case #4: Compare instances of Member that has a lexicographically smaller first name but not last name");
        testResult(member1, member2, expectedOutput, actualOutput);

// 5
        member1 = new Member("Mary", "Lindsey");
        member2 = new Member("April", "March");
        expectedOutput = 1;
        actualOutput = member1.compareTo(member2);
        System.out.println(
                "**Test case #5: Compare instances of Member that has a lexicographically larger first name but lexicographically smaller last name");
        testResult(member1, member2, expectedOutput, actualOutput);

// 6
        member1 = new Member("Carl", "Brown");
        member2 = new Member("Mary", "Lindsey");
        expectedOutput = 1;
        actualOutput = member1.compareTo(member2);
        System.out.println(
                "**Test case #6: Compare instances of Member that has a lexicographically smaller last name and first name");
        testResult(member1, member2, expectedOutput, actualOutput);

// 7
        member1 = new Member("Paul", "Siegel");
        member2 = new Member("Duke", "Ellington");
        expectedOutput = -1;
        actualOutput = member1.compareTo(member2);
        System.out.println(
                "**Test case #7: Compare instances of Member that has a lexicographically bigger last name and first name");
        testResult(member1, member2, expectedOutput, actualOutput);

// 8
        member1 = new Member("Mary", "Lindsey");
        member2 = new Member("Bill", "Scanlan");
        expectedOutput = 1;
        actualOutput = member1.compareTo(member2);
        System.out.println(
                "**Test case #8: Compare instances of Member that has a lexicographically smaller first name but not last name");
        testResult(member1, member2, expectedOutput, actualOutput);

// 9
        member1 = new Member("John", "Doe");
        member2 = new Member("Roy", "Brooks");
        expectedOutput = -1;
        actualOutput = member1.compareTo(member2);
        System.out.println(
                "**Test case #9: Compare instances of Member that has a lexicographically larger first name but lexicographically smaller last name");
        testResult(member1, member2, expectedOutput, actualOutput);

// 10
        member1 = new Member("Mary", "Lindsey");
        member2 = new Member("Duke", "Ellington");
        expectedOutput = -1;
        actualOutput = member1.compareTo(member2);
        System.out.println(
                "**Test case #10: Compare instances of Member that has a lexicographically smaller last name and first name");
        testResult(member1, member2, expectedOutput, actualOutput);

// 11
        member1 = new Member("Jane", "Doe");
        member2 = new Member("Paul", "Siegel");
        expectedOutput = 1;
        actualOutput = member1.compareTo(member2);
        System.out.println(
                "**Test case #11: Compare instances of Member that has a lexicographically bigger last name and first name");
        testResult(member1, member2, expectedOutput, actualOutput);

// 12
        member1 = new Member("mary", "Lindsey");
        member2 = new Member("April", "march");
        expectedOutput = 1;
        actualOutput = member1.compareTo(member2);
        System.out.println("**Test case #12: Compare instances of Member that are a mix of upper case and lower case");
        testResult(member1, member2, expectedOutput, actualOutput);

// 13
        member1 = new Member("carl", "brown");
        member2 = new Member("mary", "lindsey");
        expectedOutput = 1;
        actualOutput = member1.compareTo(member2);
        System.out.println("**Test case #13: Compare instances of Member that are all lowercase");
        testResult(member1, member2, expectedOutput, actualOutput);

// 14
        member1 = new Member("PAUL", "SIEGEL");
        member2 = new Member("DUKE", "ELLINGTON");
        expectedOutput = -1;
        actualOutput = member1.compareTo(member2);
        System.out.println("**Test case #14: Compare instances of Member that are all uppercase");
        testResult(member1, member2, expectedOutput, actualOutput);
    }
}