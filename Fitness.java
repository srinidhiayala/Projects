/**
 * Handles memberships of members at the gym for a Fitness class and checks in
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

    private Member[] membersList = new Member[100];
    private int size = 0;

    /**
     * This is a constructor that creates a Fitness object with a class name,
     * instructor name, and time of class based on timing.
     *
     * @param className      name of fitness class
     * @param instructorName instructor name
     * @param time           time of class
     */
    public Fitness(String className, String instructorName, String time) {
        this.className = className;
        this.instructorName = instructorName;
        if (time.substring(0, 1).equals("9"))
            this.time = Time.MORNING;
        else
            this.time = Time.AFTERNOON;
        size = 0;
    }

    /**
     * Returns the size of the list of all the members in the database that are part
     * of the gym.
     *
     * @return memberlist's size as int
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns all the members that are enrolled in a fitness class.
     *
     * @return members in memberlist as Member array
     */
    public Member[] getMemberList() {
        return membersList;
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
     * @return fitness class time as Time
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
     * Returns if a member trying to register to a fitness class has a membership
     * that has not expired. Creates a new date instance to generate today's date.
     * Compares today's date with the date of the member's expiration date to check
     * if they should be checked in or not.
     *
     * @param member member whose expiration date needs to be checked
     * @return true if member should be checked in, false if membership has expired
     */
    public boolean validExpirationDate(Member member) {
        final int EXPIRED=-1;
        Date today = new Date();
        if (today.compareTo(member.getExpire()) == EXPIRED) {
            return false;
        } else
            return true;
    }

    /**
     * Returns if a member is in found in the fitness class's previously registered
     * list. Compare each member in a fitness class with the new member.
     *
     * @param member member who need to be checked if they already registered for
     *               the fitness class already.
     * @return index of the member in membersList if that member is already in the
     * fitness class, -1 if member is not already enrolled.
     */
    private int memberHunt(Member member) {
        for (int i = 0; i < size; i++) {
            if (membersList[i].equals(member)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks if a member is in found in the fitness class's previously registered
     * list and changes the list of members accordingly. If a member is found, then
     * remove the member from the list of registered members. Cancel membership by
     * starting at the member who needs to be removed and copy each member into the
     * previous member's registered index.
     *
     * @param member member who needs to cancel their registration for the fitness
     *               class
     */
    public void cancelMembership(Member member) {
        final int FOUND=-1;
        int found = memberHunt(member);
        if (found != FOUND) {
            membersList[found] = null;
            for (int i = found; i < size - 1; i++) {
                membersList[i] = membersList[i + 1];
            }
            size--;
        }
    }

    /**
     * Returns if a member should be checked in or not.
     *
     * @param member member wants to be check in
     * @return true if member should be checked in, false if member is already in
     * the database
     */
    public boolean checkIn(Member member) {

        if (memberHunt(member) >= 0) {
            return false;
        }
        return true;
    }

    /**
     * Adds a member to the memberList for a fitness class and increases the size
     * counter for the number of total member.
     *
     * @param member member who needs to be added to the fitness class's list
     */
    public void addMember(Member member) {
        membersList[size] = member;
        size++;
    }

}