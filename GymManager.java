import java.util.Scanner;

/**
 * Takes in user input and parses each line to manage the gym's member for different locations and enrollment to
 * different fitness classes.
 * Accepts a batch of user input and spits out statements based on the spliced input.
 * Add a person to the membership database and remove the person from the membership database.
 * Display the fitness class schedule, check in members into a fitness class, and drops members from a fitness class.
 * Evaluates if a member has a time conflict with the fitness classes.
 * Checks if member is found in the database, if member is registered for a class, and if member can be dropped.
 *
 * @author Srinidhi Ayalasomayajula, Palak Mehta
 */

public class GymManager {

    private Fitness pilates = new Fitness("Pilates", "Jennifer", "9:30");
    private Fitness spinning = new Fitness("Spinning", "Denise", "14:00");
    private Fitness cardio = new Fitness("Cardio", "Kim", "14:00");
    private Date todayDate = new Date();
    private MemberDatabase accessMemberList = new MemberDatabase();
    int printSpace = 0;

    /**
     * Runs the entire scanner logic that read, parses, and calls appropriate
     * methods based on the users command entered. Keeps reading the lines until
     * there are no more lines in the input. Checks if the first or first two
     * characters of an input match the expected command statements. If there is a
     * match for a command, then call the method to handle the members for the gym.
     */
    public void run() {
        final int MAX_LENGTH=2;
        System.out.println("Gym Manager running...\n");
        Scanner readInput = new Scanner(System.in);
        while (readInput.hasNext()) {
            if (printSpace == 0) {
                System.out.println();
                printSpace++;
            }
            String input = readInput.nextLine();
            if (input.equals(" ") || input.equals("")) {
                continue;
            } else if (input.substring(0, 1).equals("Q")) {
                System.out.println();
                break;
            } else if (input.substring(0, 1).equals("A") && input.substring(1, MAX_LENGTH).equals(" ")) {
                this.addCommand(input);
            } else if (input.substring(0, 1).equals("P") && input.length() == 1) {
                accessMemberList.print();
            } else if (input.length() > 1 && input.substring(0, MAX_LENGTH).equals("PC")) {
                accessMemberList.printByCounty();
            } else if (input.length() > 1 && input.substring(0, MAX_LENGTH).equals("PN")) {
                accessMemberList.printByName();
            } else if (input.length() > 1 && input.substring(0, MAX_LENGTH).equals("PD")) {
                accessMemberList.printByExpirationDate();
            } else if (input.substring(0, 1).equals("S") && input.length() == 1) {
                this.displayFitnessSchedule();
            } else if (input.substring(0, 1).equals("C") && input.substring(1, MAX_LENGTH).equals(" ")) {
                this.checkInToClass(input);
            } else if (input.substring(0, 1).equals("R") && input.substring(1, MAX_LENGTH).equals(" ")) {
                this.removeMember(input);
            } else if (input.substring(0, 1).equals("D") && input.substring(1, MAX_LENGTH).equals(" ")) {
                this.dropClass(input);
            } else {
                System.out.println(input + " is an invalid command!");
            }
        }
        System.out.println("Gym Manager terminated.");
    }

    /**
     * Adds a member into the large member database if they meet the basic criteria
     * and are valid. Add a space after the user's batch input for better
     * readability. Spilt the user's input by space and create a new member based on
     * that members properties. In order to add a new member, they need to have a
     * valid date of birth, expiration date, gym location, and age. If the member is
     * not already in the database, then add that member into the gym membership
     * database.
     *
     * @param userInput input that the user enters parsed line by line with a members
     *                  relevant information
     */
    public void addCommand(String userInput) {
        if (printSpace == 1) {
            System.out.println();
            printSpace++;
        }
        final int SIZE=6;
        String[] eachPhraseArray = new String[SIZE];
        eachPhraseArray = userInput.split(" ");
        Member person = new Member(eachPhraseArray[1], eachPhraseArray[2], eachPhraseArray[3], eachPhraseArray[4],
                eachPhraseArray[5]);
        if ((person.getLocation() == null || !(person.getLocation().validLocation()))) {
            System.out.println(eachPhraseArray[5] + ": invalid location!");
        } else if (!(person.getDob().isValid())) {
            System.out.println("DOB " + person.getDob() + ": invalid calendar date!");
        } else if (!(person.getExpire().isValid())) {
            System.out.println("Expiration date " + person.getExpire() + ": invalid calendar date!");
        } else if (!(person.todayOrFutureDate())) {
            System.out.println("DOB " + person.getDob() + ": cannot be today or a future date!");
        } else if (accessMemberList.foundMember(person) > -1) {
            System.out.println(person.getFname() + " " + person.getLname() + " is already in the database.");
        } else if (!(person.eighteenOrOlder())) {
            System.out.println("DOB " + person.getDob() + ": must be 18 or older to join!");
        } else {
            accessMemberList.add(person);
            System.out.println(person.getFname() + " " + person.getLname() + " added.");
        }
    }

    /**
     * Removes a member from a database if they are a valid person and found in the
     * existing membership database. Spilt the user's input by space and assign a
     * variable to store the members properties. Compare the first name, last name,
     * and date of birth of the new member and all members in the database. If the
     * member is not found, then inform the user of this finding.
     *
     * @param userInput that the user enters parsed line by line with a members relevant
     *                  information
     */
    public void removeMember(String userInput) {
        final int SIZE=6;
        String[] eachPhraseArray = new String[SIZE];
        eachPhraseArray = userInput.split(" ");
        String firstName = eachPhraseArray[1];
        String lastName = eachPhraseArray[2];
        Date dob = new Date(eachPhraseArray[3]);
        boolean flag = false;
        for (int i = 0; i < accessMemberList.getSize(); i++) {
            if (accessMemberList.getMList()[i].getFname().equals(firstName)
                    && accessMemberList.getMList()[i].getLname().equals(lastName)
                    && accessMemberList.getMList()[i].getDob().compareTo(dob) == 0) {
                accessMemberList.remove(accessMemberList.getMList()[i]);
                System.out.println(firstName + " " + lastName + " removed.");
                flag = true;
                break;
            }
        }
        if (!(flag)) {
            System.out.println(firstName + " " + lastName + " is not in the database.");
        }
    }

    /**
     * Display the schedule of the fitness classes and also display the participants
     * of each fitness class. If there are no participants in the fitness class,
     * then display just the fitness class titles. Print out every member registered
     * for each fitness class.
     */
    public void displayFitnessSchedule() {
        if (pilates.getSize() == 0 && spinning.getSize() == 0 && cardio.getSize() == 0) {
            System.out.println("-Fitness classes-\n" + "Pilates - JENNIFER 9:30\n" + "Spinning - DENISE 14:00\n"
                    + "Cardio - KIM 14:00\n");
        } else {
            System.out.println("\n-Fitness classes-");
            System.out.println("Pilates - JENNIFER 9:30\n\t** participants **");
            for (int i = 0; i < pilates.getSize(); i++) {
                System.out.println("\t\t" + pilates.getMemberList()[i].toString());
            }
            System.out.println("Spinning - DENISE 14:00\n\t** participants **");
            for (int i = 0; i < spinning.getSize(); i++) {
                System.out.println("\t\t" + spinning.getMemberList()[i].toString());
            }
            System.out.println("Cardio - KIM 14:00\n\t** participants **");
            for (int i = 0; i < cardio.getSize(); i++) {
                System.out.println("\t\t" + cardio.getMemberList()[i].toString());
            }
        }
        System.out.println();
    }

    /**
     * Checks in a member into a fitness classes if they have valid information.
     * Spilt the user's input by space and assign a variable to store the members
     * properties. For every member in the membership database, check if they have a
     * valid date of birth and a non-expired membership. Also check if there is any
     * time conflict or if they already exist in the membership database.
     *
     * @param userInput that the user enters parsed line by line with a members relevant
     *                  information
     */
    public void checkInToClass(String userInput) {
        final int SIZE=6;
        String[] eachPhraseArray = new String[SIZE];
        eachPhraseArray = userInput.split(" ");
        String className = eachPhraseArray[1];
        String firstName = eachPhraseArray[2];
        String lastName = eachPhraseArray[3];
        Date dob = new Date(eachPhraseArray[4]);
        Member[] listOfMembers = accessMemberList.getMList();
        for (int i = 0; i < accessMemberList.getSize(); i++) {
            String fullName = listOfMembers[i].getFname() + " " + listOfMembers[i].getLname();
            if (listOfMembers[i].getFname().equals(firstName) && listOfMembers[i].getLname().equals(lastName)
                    && !(dob.isValid())) {
                System.out.println("DOB " + dob + ": invalid calendar date!");
                break;
            } else if (listOfMembers[i].getFname().equals(firstName) && listOfMembers[i].getLname().equals(lastName)
                    && (todayDate.compareTo(listOfMembers[i].getExpire()) == -1)) {
                System.out.println(fullName + " " + dob + " membership expired.");
            } else if (listOfMembers[i].getFname().equals(firstName) && listOfMembers[i].getLname().equals(lastName)
                    && listOfMembers[i].getDob().compareTo(dob) == 0 && listOfMembers[i].getExpire().isValid()) {
                isMemberFound(listOfMembers[i], className, fullName);
            } else if (listOfMembers[i].getFname().equals(firstName) && listOfMembers[i].getLname().equals(lastName)
                    && !listOfMembers[i].getDob().equals(dob)) {
                checkIfMemberInDatabase(listOfMembers, firstName, lastName, dob);
            }
        }
    }

    /**
     * Check if a member is in the membership database by comparing the member to
     * every person in the membership database. Compare members based on first name,
     * last name, and date of birth.
     *
     * @param listOfMembers listOfMembers is the total list of all the members in
     *                      the membership database
     * @param firstName     member's first name
     * @param lastName      member's last name
     * @param dob           member's date of birth
     */
    private void checkIfMemberInDatabase(Member[] listOfMembers, String firstName, String lastName, Date dob) {
        boolean flag = true;
        for (Member m : listOfMembers) {
            if (m.getFname().equals(firstName) && m.getLname().equals(lastName) && m.getDob().compareTo(dob) == 0) {
                flag = false;
            }
        }
        if (flag) {
            System.out.println(firstName + " " + lastName + " " + dob + " is not in the database.");
        }
    }

    /**
     * Check if the member has already checked into a fitness class. Check in the
     * member if they are not checked in. Check if the class exists at the gym.
     *
     * @param listOfMembers listOfMembers is the total list of all the members in
     *                      the membership database
     * @param className     fitness class's name
     * @param fullName      member's full name including first and last name
     */
    private void isMemberFound(Member listOfMembers, String className, String fullName) {
        if (cardio.getClassName().equalsIgnoreCase(className) && cardio.checkIn(listOfMembers)) {
            hasTimeConflictCardio(listOfMembers, fullName);
        } else if (cardio.getClassName().equalsIgnoreCase(className)) {
            System.out.println(fullName + " has already checked in " + cardio.getClassName() + ".");
        } else if (spinning.getClassName().equalsIgnoreCase(className) && spinning.checkIn(listOfMembers)) {
            hasTimeConflictSpinning(listOfMembers, fullName);
        } else if (spinning.getClassName().equalsIgnoreCase(className)) {
            System.out.println(fullName + " has already checked in " + spinning.getClassName() + ".");
        } else if (pilates.getClassName().equalsIgnoreCase(className) && pilates.checkIn(listOfMembers)) {
            System.out.println(fullName + " checked in " + pilates.getClassName() + ".");
            pilates.addMember(listOfMembers);
        } else if (pilates.getClassName().equalsIgnoreCase(className)) {
            System.out.println(fullName + " has already checked in " + pilates.getClassName() + ".");
        } else {
            System.out.println(className + " class does not exist.");
        }
    }

    /**
     * Check if a member has a time conflict for the cardio fitness class, If a
     * member is in the spinning member database, then there is a time conflict with
     * the cardio class. Otherwise they should be checked into the cardio fitness
     * class.
     *
     * @param member   member that needs to be compared to from all the members
     *                 already registered for the spinning class
     * @param fullName member's full name including first and last name
     */
    private void hasTimeConflictCardio(Member member, String fullName) {
        boolean conflict = false;
        for (Member person : spinning.getMemberList()) {
            if (person != null && person.equals(member)) {
                System.out.println("Cardio time conflict -- " + fullName + " has already checked in Spinning.");
                conflict = true;
                break;
            }
        }
        if (!(conflict)) {
            System.out.println(fullName + " checked in " + cardio.getClassName() + ".");
            cardio.addMember(member);
        }
    }

    /**
     * Check if a member has a time conflict for the spinning fitness class. If a
     * member is in the cardio member database, then there is a time conflict with
     * the spinning class. Otherwise they should be checked into the spinning
     * fitness class.
     *
     * @param member   member that needs to be compared to from all the members
     *                 already registered for the cardio class
     * @param fullName member's full name including first and last name
     */
    private void hasTimeConflictSpinning(Member member, String fullName) {
        boolean timeConflict = false;
        for (Member person : cardio.getMemberList()) {
            if (person != null && person.equals(member)) {
                System.out.println("Spinning time conflict -- " + fullName + " has already checked in Cardio.");
                timeConflict = true;
                break;
            }
        }
        if (!(timeConflict)) {
            System.out.println(fullName + " checked in " + spinning.getClassName() + ".");
            spinning.addMember(member);
        }
    }

    /**
     * Check if a member already exists in the fitness class's database and print
     * out the action performed. If the first name, last name, and date of birth of
     * the member are the same as a member in the fitness class's database, then
     * remove that member. Otherwise, print out the fact that the member was not
     * registered for a fitness class.
     *
     * @param activity  activity is string representation of the fitness class where
     *                  the member wants to be removed from
     * @param classType classType is object representation of the fitness class
     *                  where the member wants to be removed from
     * @param firstName member's first name
     * @param lastName  member's last name
     * @param dob       member's date of birth
     */
    private void checkAndDrop(String activity, Fitness classType, String firstName, String lastName, Date dob) {
        boolean checkMemberExist = false;
        for (int i = 0; i < classType.getSize(); i++) {
            if (classType.getMemberList()[i].getFname().equals(firstName)
                    && classType.getMemberList()[i].getLname().equals(lastName)
                    && classType.getMemberList()[i].getDob().compareTo(dob) == 0) {
                classType.cancelMembership(classType.getMemberList()[i]);
                System.out.println(firstName + " " + lastName + " dropped " + activity + ".");
                checkMemberExist = true;
            }
        }
        if (!(checkMemberExist)) {
            System.out.println(firstName + " " + lastName + " is not a participant in " + activity + ".");
        }
    }

    /**
     * Drop the member from a class if they have a valid birthday and the class
     * exists. Spilt the user's input by space and assign a variable to store the
     * members properties. If the class name matches the fitness class name, then
     * call the checkAndDrop() method to remove the member from that class. If the
     * class do not exist, then clearly state so.
     *
     * @param userInput that the user enters parsed line by line with a members relevant
     *                  information
     */
    public void dropClass(String userInput) {
        final int SIZE=6;
        String[] eachPhraseArray = new String[SIZE];
        eachPhraseArray = userInput.split(" ");
        String className = eachPhraseArray[1];
        String firstName = eachPhraseArray[2];
        String lastName = eachPhraseArray[3];
        Date dob = new Date(eachPhraseArray[4]);
        if (!(dob.isValid())) {
            System.out.println("DOB " + dob + ": invalid calendar date!");
        } else if (className.equals("Pilates")) {
            this.checkAndDrop("Pilates", pilates, firstName, lastName, dob);
        } else if (className.equals("Spinning")) {
            this.checkAndDrop("Spinning", spinning, firstName, lastName, dob);
        } else if (className.equals("Cardio")) {
            this.checkAndDrop("Cardio", cardio, firstName, lastName, dob);
        } else {
            System.out.println(className + " class does not exist.");
        }

    }

}