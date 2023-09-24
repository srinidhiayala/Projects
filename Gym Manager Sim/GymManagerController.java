package com.example.project3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Takes in user input from GUI and parses each line to manage the gym's member for different locations and enrollment to
 * different fitness classes.
 * Accepts a batch of user input and spits out statements based on the spliced input.
 * Add a person to the membership database and remove the person from the membership database.
 * Display the fitness class schedule, check in members into a fitness class, and drops members from a fitness class.
 * Evaluates if a member has a time conflict with the fitness classes.
 * Checks if member is found in the database, if member is registered for a class, and if member can be dropped.
 *
 * @author Srinidhi Ayalasomayajula, Palak Mehta
 */

public class GymManagerController {

    @FXML
    private DatePicker classDob;

    @FXML
    private TextField classFirstName;

    @FXML
    private TextField classLastName;

    @FXML
    private TextField className;

    @FXML
    private DatePicker date;

    @FXML
    private RadioButton family;

    @FXML
    private TextField firstName;

    @FXML
    private TextField gymLocation;

    @FXML
    private TextField instructorName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField locationName;

    @FXML
    private TextArea output;

    @FXML
    private TextArea outputFitnessClass;

    @FXML
    private TextArea outputInfo;

    @FXML
    private RadioButton premium;

    @FXML
    private RadioButton standard;

    @FXML
    private ToggleGroup type;
    private Date todayDate = new Date();
    private ClassSchedule schedule = new ClassSchedule();
    private MemberDatabase accessMemberList = new MemberDatabase();
    private String[] eachPhraseArray = new String[SIZE_OF_INPUT];
    private int printSpace = 0;
    public static final int MAX_LENGTH = 2;
    public static final int TOTAL_NUMBER_OF_MONTHS = 12;
    public static final int STANDARD_FAMILY_EXPIRATION_MONTH_TIME = 3;
    public static final int SIZE_OF_INPUT = 6;
    public static final int MONTHS_BEFORE_EXPIRATION_CARRYS_OVER = 9;
    public static final int NOT_FOUND = -1;
    public static boolean LOADED_MEMBERS = false;

    /**
     * This method gets the values that the user entered in the GUI and computes the new expiration date based on
     * the selected membership type. Then It calls the addCommand() method to properly add the member if applicable.
     * If no input is entered, then it prompts the user to enter all the data fields correctly.
     *
     * @param event the event of clicking on the button to add a member
     */
    @FXML
    void add(ActionEvent event) {
//        //messageArea.clear(); //clear the TextArea.
        try {
            String fName = firstName.getText().trim();
            String lName = lastName.getText().trim();
            String dob = date.getValue().toString().trim();
            String memberLocation = gymLocation.getText().trim();
            Member person;
            Date expire;
            if (!checkInvalidInputAddRemove()) {
                return;
            }
            if (!containsIntOrDoubleAddRemove()) {
                return;
            }
            if (premium.isSelected()) {
                expire = new Date(todayDate.getYear() + 1, todayDate.getMonth(), todayDate.getDay());
                person = new Premium(fName, lName, dob, expire, memberLocation);
            } else if ((standard.isSelected()) || (family.isSelected())) {

                if (todayDate.getMonth() <= MONTHS_BEFORE_EXPIRATION_CARRYS_OVER) {
                    expire = new Date(todayDate.getYear(), todayDate.getMonth() + 3, todayDate.getDay());
                } else {
                    expire = new Date(todayDate.getYear() + 1, STANDARD_FAMILY_EXPIRATION_MONTH_TIME -
                            (TOTAL_NUMBER_OF_MONTHS - todayDate.getMonth()), todayDate.getDay());
                }
                if (standard.isSelected()) {
                    person = new Member(fName, lName, dob, expire, memberLocation);
                } else {
                    person = new Family(fName, lName, dob, expire, memberLocation);
                }
            } else {
                person = new Member(fName, lName, dob, new Date(dob), memberLocation);
            }
            addCommand(person, false, memberLocation);

        } catch (Exception ex) { //Ask?
            output.appendText("Must enter all inputs required.\n");
        }

    }

    /**
     * Checks if the entered fields are non empty for the add and remove features.
     *
     * @return true if input is all valid, false otherwise
     */
    private boolean checkInvalidInputAddRemove() {
        if (firstName.getText().trim().length() == 0) {
            output.appendText("First name not entered!\n");
            return false;
        }
        if (lastName.getText().trim().length() == 0) {
            output.appendText("Last name not entered!\n");
            return false;
        }
        if (gymLocation.getText().trim().length() == 0) {
            output.appendText("Location not entered!\n");
            return false;
        }
        return true;
    }

    /**
     * Checks if the entered fields are non empty for the fitness class features.
     *
     * @return true if input is all valid, false otherwise.
     */
    private boolean checkInvalidInputFitness() {
        if (classFirstName.getText().trim().length() == 0) {
            outputFitnessClass.appendText("First name not entered!\n");
            return false;
        }
        if (classLastName.getText().trim().length() == 0) {
            outputFitnessClass.appendText("Last name not entered!\n");
            return false;
        }
        if (className.getText().trim().length() == 0) {
            outputFitnessClass.appendText("Class name not entered!\n");
            return false;
        }
        if (instructorName.getText().trim().length() == 0) {
            outputFitnessClass.appendText("Instructor not entered!\n");
            return false;
        }
        if (locationName.getText().trim().length() == 0) {
            outputFitnessClass.appendText("Location not entered!\n");
            return false;
        }
        return true;
    }

    /**
     * Checks if the entered fields contain a digit for the fitness tab.
     *
     * @return true if input does not contain digits, false otherwise.
     */
    private boolean containsIntOrDoubleFitness() {
        for (char c : classFirstName.getText().toCharArray()) {
            if (Character.isDigit(c)) {
                outputFitnessClass.appendText("First name cannot have a digit!\n");
                return false;
            }
        }
        for (char c : classLastName.getText().toCharArray()) {
            if (Character.isDigit(c)) {
                outputFitnessClass.appendText("Last name cannot have a digit!\n");
                return false;
            }
        }
        for (char c : className.getText().toCharArray()) {
            if (Character.isDigit(c)) {
                outputFitnessClass.appendText("Class name cannot have a digit!\n");
                return false;
            }
        }
        for (char c : instructorName.getText().toCharArray()) {
            if (Character.isDigit(c)) {
                outputFitnessClass.appendText("Instructor name cannot have a digit!\n");
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the entered fields contain a digit for the add and remove feature.
     *
     * @return true if input does not contain digits, false otherwise.
     */
    private boolean containsIntOrDoubleAddRemove() {
        for (char c : firstName.getText().toCharArray()) {
            if (Character.isDigit(c)) {
                output.appendText("First name cannot have a digit!\n");
                return false;
            }
        }
        for (char c : lastName.getText().toCharArray()) {
            if (Character.isDigit(c)) {
                output.appendText("Last name cannot have a digit!\n");
                return false;
            }
        }
        return true;
    }


    /**
     * Adds a member into the large member database if they meet the basic criteria
     * and are valid. In order to add a new member, they need to have a
     * valid date of birth, expiration date, gym location, and age. If the member is
     * not already in the database, then add that member into the gym membership
     * database.
     *
     * @param person   person that needs to be added into the membership database
     * @param loading  loading is a boolean that evaluates if the member is added from the history or a new member
     * @param location location is a string that represents the location
     */
    public void addCommand(Member person, boolean loading, String location) {
        if ((person.getLocation() == null || !(person.getLocation().validLocation()))) {
            output.appendText(location + ": invalid location!\n");
        } else if (!(person.getDob().isValid())) {
            output.appendText("DOB " + person.getDob() + ": invalid calendar date!\n");
        } else if (!(person.todayOrFutureDate())) {
            output.appendText("DOB " + person.getDob() + ": cannot be today or a future date!\n");
        } else if (accessMemberList.foundMember(person) > NOT_FOUND) {
            output.appendText(person.getFname() + " " + person.getLname() + " is already in the database.\n");
        } else if (!(person.eighteenOrOlder())) {
            output.appendText("DOB " + person.getDob() + ": must be 18 or older to join!\n");
        } else {
            accessMemberList.add(person);
            if (!loading) {
                output.appendText(person.getFname() + " " + person.getLname() + " added.\n");
            }
        }
    }

    /**
     * Checks in a guest associated with a member into a fitness classes.
     * If the member who is trying to bring a guest has a standard membership, an error message of "Standard membership -
     * guest check-in is not allowed" is printed.
     *
     * @param event the event of clicking on the button to check in a guest
     */
    @FXML
    public void guestCheckIn(ActionEvent event) {
        try {

            String fName = classFirstName.getText().trim();
            String lName = classLastName.getText().trim();
            String dobStr = classDob.getValue().toString().trim();
            Date dob = new Date(dobStr);
            String nameOfClass = className.getText().trim();
            String instructor = instructorName.getText().trim();
            String memberLocation = locationName.getText().trim();
            Member[] listOfMembers = accessMemberList.getMList();
            if (!checkInvalidInputFitness()) {
                return;
            }
            if (!containsIntOrDoubleFitness()) {
                return;
            }
            if (!checkSize()) {
                return;
            }
            for (int i = 0; i < accessMemberList.getSize(); i++) {
                String fullName = listOfMembers[i].getFname() + " " + listOfMembers[i].getLname();
                if (listOfMembers[i].getFname().equalsIgnoreCase(fName) && listOfMembers[i].getLname().equalsIgnoreCase(lName)
                        && dob.compareTo(listOfMembers[i].getDob()) == 0) {
                    if (!(listOfMembers[i] instanceof Family) && !(listOfMembers[i] instanceof Premium)) {
                        outputFitnessClass.appendText("Standard membership - guest check-in is not allowed.\n");
                    } else {
                        guestCheckInProcess(listOfMembers[i], nameOfClass, fullName, instructor, memberLocation);
                    }
                }
            }
        } catch (Exception ex) {
            outputFitnessClass.appendText("Must enter all inputs required.\n");
        }
    }


    /**
     * Checks in a guest if they are of either Family or Premium type membership.
     * Once confirmed that the wanted class exists, the location of the class is checked. If it is different from
     * the location of the Member's gym, an error message is printed. If the location matches, the guest is
     * added to the class list.
     *
     * @param listOfMembers   Member's guest to be added
     * @param className       name of fitness class
     * @param fullName        full name of Member
     * @param instructorName  name of instructor
     * @param locationOfClass location of the class
     */
    private void guestCheckInProcess(Member listOfMembers, String className, String fullName, String instructorName,
                                     String locationOfClass) {
        if (!(checkBasicRequirements(className, instructorName, locationOfClass))) {
            for (int i = 0; i < schedule.getNumClasses(); i++) {
                if (!(locationOfClass.equalsIgnoreCase(listOfMembers.getLocation().getCity()))) {
                    outputFitnessClass.appendText(fullName + " Guest checking in " + listOfMembers.getLocation().
                            findLocation(locationOfClass)
                            + " - guest location restriction.\n");
                    return;
                }
                if (schedule.getClasses()[i].getCourse().getClassName().equalsIgnoreCase(className) &&
                        schedule.getClasses()[i].getCourse().getInstructor().equalsIgnoreCase(instructorName)
                        && schedule.getClasses()[i].getLocation().getCity().equalsIgnoreCase(locationOfClass)) {
                    if (listOfMembers instanceof Family) {
                        listOfMembers = (Family) listOfMembers;
                        boolean decrementPasses = (((Family) listOfMembers).decreasePasses());
                        if (!decrementPasses) {
                            outputFitnessClass.appendText(fullName + " ran out of guest pass.\n");
                            return;
                        }
                    } else {
                        listOfMembers = (Premium) listOfMembers;
                        boolean decrementPasses = (((Family) listOfMembers).decreasePasses());
                        if (!decrementPasses) {
                            outputFitnessClass.appendText(fullName + " ran out of guest pass.\n");
                            return;
                        }
                    }
                    schedule.getClasses()[i].getGuests().add(listOfMembers);
                    outputFitnessClass.appendText(fullName + " (guest) checked in " + classFound(className, instructorName,
                            locationOfClass).toString());
                    displaySpecificFitnessSchedule(className, instructorName, locationOfClass);
                }
            }
        }
    }


    /**
     * Removes a member from a database if they are a valid person and found in the
     * existing membership database. Spilt the user's input by space and assign a
     * variable to store the members properties. Compare the first name, last name,
     * and date of birth of the new member and all members in the database. If the
     * member is not found, then inform the user of this finding.
     *
     * @param event the event of clicking on the button to remove a member
     */
    @FXML
    void remove(ActionEvent event) {
        try {
            String fName = firstName.getText().trim();
            String lName = lastName.getText().trim();
            Date dob = new Date(date.getValue().toString().trim());
            if (!checkInvalidInputAddRemove()) {
                return;
            }
            if (!containsIntOrDoubleAddRemove()) {
                return;
            }
            boolean flag = false;
            for (int i = 0; i < accessMemberList.getSize(); i++) {
                if (accessMemberList.getMList()[i].getFname().equalsIgnoreCase(fName)
                        && accessMemberList.getMList()[i].getLname().equalsIgnoreCase(lName)
                        && accessMemberList.getMList()[i].getDob().compareTo(dob) == 0) {
                    accessMemberList.remove(accessMemberList.getMList()[i]);
                    output.appendText(fName + " " + lName + " removed.\n");
                    output.appendText("\n");
                    flag = true;
                    break;
                }
            }
            if (!(flag)) {
                output.appendText(fName + " " + lName + " is not in the database.\n");
            }
        } catch (Exception ex) { //Ask?
            output.appendText("Must enter all inputs required.\n");
        }
    }

    /**
     * This method reads and loads the fitness class schedule from a chosen file to the Fitness array.
     * It adds the classes to the ClassSchedule list of Fitness classes.
     * Prints "File not found" if the file is not found.
     *
     * @param event the event of clicking on the button to import class schedule
     */
    @FXML
    void importClassSchedule(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Source File for the Import");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File sourceFile = chooser.showOpenDialog(stage); //get the reference of the source file
        if (!(sourceFile.getName().equalsIgnoreCase("classSchedule.txt"))) {
            outputInfo.appendText("Incorrect File inputted.\n\n");
            return;
        }
        int result = schedule.importFile(sourceFile, schedule);
        if (result == 0) {
            outputInfo.appendText("Error: file not found\n");
            outputInfo.appendText("\n");
        } else if (result == 1) {
            displayClasses();
        } else if (result == 2) {
            outputInfo.appendText("Already loaded file\n");
            outputInfo.appendText("\n");
        }


    }

    /**
     * Traverses the array housing FitnessClass objects and prints the classname, instructor, time and location of the class.
     */
    public void displayClasses() {
        outputInfo.appendText("-Fitness classes loaded- \n");
        for (int i = 0; i < schedule.getNumClasses(); i++) {
            outputInfo.appendText(schedule.getClasses()[i].toString() + " \n");
        }
        outputInfo.appendText("-end of class list- \n");
        outputInfo.appendText("\n");


    }

    /**
     * Loads in the members from a chosen file and adds them to the Member database.
     * Prints "File not found" if the file is not found.
     *
     * @param event the event of clicking on the button to import members list
     */
    @FXML
    void importMembers(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Source File for the Import");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File sourceFile = chooser.showOpenDialog(stage); //get the reference of the source file
        if (!(sourceFile.getName().equalsIgnoreCase("memberList.txt"))) {
            outputInfo.appendText("Incorrect File inputted.\n\n");
            return;
        }
        int result = accessMemberList.importFile(sourceFile, accessMemberList);
        if (result == 1) {
            outputInfo.appendText("\n");
            outputInfo.appendText("-list of members loaded-\n");
            for (int i = 0; i < accessMemberList.getSize(); i++) {
                outputInfo.appendText(accessMemberList.getMList()[i].toString() + "\n");
            }
            outputInfo.appendText("-end of list-\n");
            outputInfo.appendText("\n");
        } else if (result == 0) {
            outputInfo.appendText("Error: File not found");
            outputInfo.appendText("\n");
        } else {
            outputInfo.appendText("Already loaded file\n");
            outputInfo.appendText("\n");
        }

    }

    /**
     * Creates a member object by parsing the parameter with space delimiters.
     * Then the member object is added to the member database.
     *
     * @param userInput line of String input with details about member to add
     */
    public void addFileMembers(String userInput) {
        StringTokenizer parsedInput = new StringTokenizer(userInput, " ");
        String fname = parsedInput.nextToken();
        String lname = parsedInput.nextToken();
        String dob = parsedInput.nextToken();
        String expireDate = parsedInput.nextToken();
        String location = parsedInput.nextToken();
        Date expire = new Date(expireDate);
        Member person = new Member(fname, lname, dob, expire, location);
        this.addCommand(person, true, location);
    }

    /**
     * This method prints the billing fee.
     *
     * @param event the event of clicking the button to print by fees
     */
    @FXML
    void printFees(ActionEvent event) {
        printByFees();
    }

    /**
     * Prints the members contained in the member database along with their due fees for the next billing statement.
     * If the database is empty, prints "Member database is empty!".
     */
    public void printByFees() {
        if (accessMemberList.getSize() == 0) {
            outputInfo.appendText("Member database is empty!\n");
            outputInfo.appendText("\n");
        } else {
            outputInfo.appendText("-list of members with membership fees-\n");
            Member[] copy = accessMemberList.printingByName();
            for (int i = 0; i < copy.length; i++) {
                outputInfo.appendText(copy[i].memberFeePrint() + "\n");
            }
            outputInfo.appendText("-end of list-");
            outputInfo.appendText("\n");
        }
    }

    /**
     * Prints the members in the database.
     *
     * @param event event where print button is activated
     */
    @FXML
    void print(ActionEvent event) {
        print();
    }

    /**
     * Prints all the members currently in the membership database in the order
     * they were added. Empty membership databases should print that the database is
     * empty. Adds in the starting and ending format specific phrases "-list of
     * members-" and "-end of list-".
     */
    public void print() {
        if (accessMemberList.getSize() == 0) {
            outputInfo.appendText("Member database is empty!\n");
            return;
        }
        outputInfo.appendText("\n");
        outputInfo.appendText("-list of members-\n");
        for (int i = 0; i < accessMemberList.getSize(); i++) {
            outputInfo.appendText(accessMemberList.getMList()[i].toString() + "\n");
        }
        outputInfo.appendText("-end of list-\n");
        outputInfo.appendText("\n");

    }


    /**
     * This method calls the sorting method to print by county/zip code.
     *
     * @param event the event of clicking the button to print by county/zip.
     */
    @FXML
    void printByCounty(ActionEvent event) {
        printByCounty();
    }

    /**
     * Creates a new membership array and copies the membership database into the
     * new membership array. Initializes the new membership array with the same size
     * as the number of members in the membership database. Deep copies the original membership array.
     *
     * @return the copied membership database as gym.Member array
     */
    private Member[] memberCopyArray() {
        Member[] copy = new Member[accessMemberList.getSize()];
        for (int i = 0; i < accessMemberList.getSize(); i++) {
            copy[i] = accessMemberList.getMList()[i];
        }
        return copy;
    }

    /**
     * Sorts the membership array by county and if the counties are the same, then
     * sort by zip code by using insertion sort. Checks in the membership database
     * is empty. Adds in the starting format specific phrases "-list of members
     * sorted by county and zipcode-".
     */
    public void printByCounty() {
        if (accessMemberList.getSize() == 0) {
            outputInfo.appendText("Member database is empty!\n");
            return;
        }

        Member[] copy = memberCopyArray();
        int length = copy.length;
        for (int j = 1; j < length; j++) {
            Member key = copy[j];
            int i = j - 1;
            while ((i > -1)
                    && copy[i].getLocation().getCounty().compareToIgnoreCase(key.getLocation().getCounty()) > 0) {
                copy[i + 1] = copy[i];
                i--;
            }
            copy[i + 1] = key;
        }
        for (int j = 1; j < length; j++) {
            Member key = copy[j];
            int i = j - 1;
            while ((i > -1) && copy[i].getLocation().getCounty().compareToIgnoreCase(key.getLocation().getCounty()) == 0
                    && copy[i].getLocation().getZipCode() > key.getLocation().getZipCode()) {
                copy[i + 1] = copy[i];
                i--;
            }
            copy[i + 1] = key;
        }
        outputInfo.appendText("\n");
        outputInfo.appendText("-list of members sorted by county and zipcode-\n");
        printArray(copy);
        outputInfo.appendText("\n");
    }

    /**
     * Prints the membership array after being sorted by using insertion sort. Adds
     * in the ending format specific phrases "-end of list-".
     *
     * @param arr member array that needs to be printed
     */
    private void printArray(Member[] arr) {
        for (int i = 0; i < arr.length; i++) {
            outputInfo.appendText(arr[i].toString() + "\n");
        }
        outputInfo.appendText("-end of list-\n");
        outputInfo.appendText("\n");
    }

    /**
     * This method calls the sorting method to print by expiration date.
     *
     * @param event the event of clicking the button to print by expiration date.
     */
    @FXML
    void printByExpiration(ActionEvent event) {
        printByExpirationDate();
    }

    /**
     * Sorts the membership array by expiration date and if the expiration dates are
     * the same, then order of sorting does not matter by using insertion sort.
     * Checks in the membership database is empty. Adds in the starting format
     * specific phrases "-list of members sorted by membership expiration date-".
     */
    public void printByExpirationDate() {
        if (accessMemberList.getSize() == 0) {
            outputInfo.appendText("Member database is empty!\n");
            return;
        }
        Member[] copy = memberCopyArray();
        int length = copy.length;
        for (int j = 1; j < length; j++) {
            Member key = copy[j];
            int i = j - 1;
            while ((i > -1) && copy[i].getExpire().compareTo(key.getExpire()) < 0) {
                copy[i + 1] = copy[i];
                i--;
            }
            copy[i + 1] = key;
        }
        outputInfo.appendText("\n");
        outputInfo.appendText("-list of members sorted by membership expiration date-\n");
        printArray(copy);
        outputInfo.appendText("\n");
    }

    /**
     * Calls method to printed by sorted name.
     *
     * @param event event of clicking on button to print by name.
     */
    @FXML
    void printByName(ActionEvent event) {
        printByName();
    }

    /**
     * Sorts the membership array by name and if the name are the same, then order
     * of sorting does not matter by using insertion sort. Checks in the membership
     * database is empty. Adds in the starting format specific phrases "-list of
     * members sorted by last name, and first name-".
     */
    public void printByName() {
        if (accessMemberList.getSize() == 0) {
            outputInfo.appendText("Member database is empty!\n");
            return;
        }
        Member[] copy = memberCopyArray();
        int length = copy.length;
        for (int j = 1; j < length; j++) {
            Member key = copy[j];
            int i = j - 1;
            while ((i > -1) && copy[i].compareTo(key) < 0) {
                copy[i + 1] = copy[i];
                i--;
            }
            copy[i + 1] = key;
        }
        outputInfo.appendText("\n");
        outputInfo.appendText("-list of members sorted by last name, and first name-\n");
        printArray(copy);
        outputInfo.appendText("\n");
    }

    /**
     * Display the schedule of the fitness classes and also display the participants
     * of each fitness class. If there are no participants in the fitness class,
     * then display just the fitness class titles. Print out every member registered
     * for each fitness class.
     */
    private void displayFitnessSchedule() {
        if (schedule.getNumClasses() == 0) {
            outputInfo.appendText("Fitness class schedule is empty.\n");
            outputInfo.appendText("\n");
        } else {
            outputInfo.appendText("\n-Fitness classes-\n");
            for (int i = 0; i < schedule.getNumClasses(); i++) {
                outputInfo.appendText(schedule.getClasses()[i].toString() + "\n");
                if (schedule.getClasses()[i].getClassMembers().size() > 0) {
                    outputInfo.appendText("- Participants -\n");
                    for (int j = 0; j < schedule.getClasses()[i].getClassMembers().size(); j++) {
                        outputInfo.appendText("\t" + schedule.getClasses()[i].getClassMembers().get(j) + "\n");
                    }
                }
                if (schedule.getClasses()[i].getGuests().size() > 0) {
                    outputInfo.appendText("- Guests -\n");
                    for (int j = 0; j < schedule.getClasses()[i].getGuests().size(); j++) {
                        outputInfo.appendText("\t" + schedule.getClasses()[i].getGuests().get(j) + "\n");
                    }
                }
            }
            outputInfo.appendText("-end of class list.\n");
        }
        outputInfo.appendText("\n");
    }

    /**
     * Calls method to printed by sorted name.
     *
     * @param event event of clicking on button to print by name.
     */
    @FXML
    void displayClasses(ActionEvent event) {
        displayFitnessSchedule();
    }

    /**
     * Drop the member from a class if they have a valid birthday and the class
     * exists. Spilt the user's input by space and assign a variable to store the
     * members properties. If the class name matches the fitness class name, then
     * call the checkAndDrop() method to remove the member from that class. If the
     * class do not exist, then clearly state so.
     *
     * @param event the event of clicking on the button to drop a class
     */
    @FXML
    public void dropClass(ActionEvent event) {
        try {

            String fName = classFirstName.getText().trim();
            String lName = classLastName.getText().trim();
            String memberLocation = locationName.getText().trim();
            String dobStr = classDob.getValue().toString().trim();
            Date dob = new Date(dobStr);
            String nameOfClass = className.getText().trim();
            String instructor = instructorName.getText().trim();
            if (!checkInvalidInputFitness()) {
                return;
            }
            if (!containsIntOrDoubleFitness()) {
                return;
            }
            if (!checkSize()) {
                return;
            }
            if (!(dob.isValid())) {
                outputFitnessClass.appendText("DOB " + dob + ": invalid calendar date!");
            } else if (schedule.getClasses()[0].getLocation().findLocation(memberLocation) == null) {
                outputFitnessClass.appendText(memberLocation + " - invalid location.\n");
            } else if (!(instructorFound(instructor))) {
                outputFitnessClass.appendText(instructor + " - instructor does not exist.\n");
            } else if (memberExists(accessMemberList.getMList(), fName, lName, dob)) {
                outputFitnessClass.appendText(fName + " " + lName + " " + dob + " is not in the database.\n");
            } else if (!(checkBasicRequirements(nameOfClass, instructor, memberLocation))) {
                this.checkAndDrop(nameOfClass, instructor, memberLocation, fName, lName, dob);
            }
        } catch (Exception ex) { //Ask?
            outputFitnessClass.appendText("Must enter all inputs required.\n");
        }
    }

    /**
     * Check if a member already exists in the fitness class's database and print
     * out the action performed. If the first name, last name, and date of birth of
     * the member are the same as a member in the fitness class's database, then
     * remove that member. Otherwise, print out the fact that the member was not
     * registered for a fitness class.
     *
     * @param className       className is the fitness class name
     * @param instructorName  instructorName is the name of the instructor teaching the Fitness class
     * @param locationOfClass locationOfClass is the location of the Fitness class
     * @param firstName       member's first name
     * @param lastName        member's last name
     * @param dob             member's date of birth
     */
    private void checkAndDrop(String className, String instructorName, String locationOfClass, String firstName,
                              String lastName, Date dob) {
        boolean memberDropped = false;
        for (int i = 0; i < schedule.getNumClasses(); i++) {
            Member memberFound = timeToFindMember(accessMemberList.getMList(), firstName, lastName, dob);
            if ((schedule.getClasses()[i].getCourse().getClassName().equalsIgnoreCase(className) &&
                    schedule.getClasses()[i].getCourse().getInstructor().equalsIgnoreCase(instructorName)
                    && schedule.getClasses()[i].getLocation().getCity().equalsIgnoreCase(locationOfClass))
                    && (schedule.getClasses()[i].getClassMembers().contains(memberFound))) {
                schedule.getClasses()[i].getClassMembers().remove(memberFound);
                outputFitnessClass.appendText(firstName + " " + lastName + " done with the class.\n");
                memberDropped = true;
                break;
            }
        }
        if (!(memberDropped)) {
            outputFitnessClass.appendText(firstName + " " + lastName + " did not check in.\n");
        }
    }

    /**
     * Find a member in the membership database by comparing the member to
     * every person in the membership database. Compare members based on first name,
     * last name, and date of birth.
     *
     * @param listOfMembers listOfMembers is the total list of all the members in the membership database
     * @param firstName     member's first name
     * @param lastName      member's last name
     * @param dob           member's date of birth
     * @return Member the member that needs to be found
     */
    private Member timeToFindMember(Member[] listOfMembers, String firstName, String lastName, Date dob) {
        for (Member m : listOfMembers) {
            if (m == null) {
                break;
            } else if (m.getFname().equalsIgnoreCase(firstName) && m.getLname().equalsIgnoreCase(lastName)
                    && m.getDob().compareTo(dob) == 0) {
                return m;
            }
        }
        return null;
    }

    /**
     * Checks the size of the lists.
     *
     * @return true if size is not zero of lists, false otherwise.
     */
    private boolean checkSize() {
        if (schedule.getClasses().length == 0 || accessMemberList.getSize() == 0) {
            outputFitnessClass.appendText("No files loaded\n");
            return false;
        }
        return true;
    }

    /**
     * Checks in a member into a fitness classes if they have valid information.
     * Spilt the user's input by space and assign a variable to store the members
     * properties. For every member in the membership database, check if they have a
     * valid date of birth and a non-expired membership. Also check if there is any
     * time conflict or if they already exist in the membership database.
     *
     * @param event the event of clicking on the button to check in to a class
     */
    @FXML
    void checkInToClass(ActionEvent event) {
        try {

            String fName = classFirstName.getText().trim();
            String lName = classLastName.getText().trim();
            String dobStr = classDob.getValue().toString().trim();
            Date dob = new Date(dobStr);
            String nameOfClass = className.getText().trim();
            String instructor = instructorName.getText().trim();
            String memberLocation = locationName.getText().trim();
            boolean flag = false;
            Member[] listOfMembers = accessMemberList.getMList();

            if (!checkInvalidInputFitness()) {
                return;
            }
            if (!containsIntOrDoubleFitness()) {
                return;
            }
            if (!checkSize()) {
                return;
            }

            for (int i = 0; i < accessMemberList.getSize(); i++) {
                String fullName = listOfMembers[i].getFname() + " " + listOfMembers[i].getLname();
                if (listOfMembers[i].getFname().equalsIgnoreCase(fName) && listOfMembers[i].getLname().equalsIgnoreCase(lName)
                        && !(dob.isValid())) {
                    flag = true;
                    outputFitnessClass.appendText("DOB " + dob + ": invalid calendar date!\n");
                    break;
                } else if (schedule.getClasses()[0].getLocation().findLocation(memberLocation) == null) {
                    outputFitnessClass.appendText(memberLocation + " - invalid location.\n");
                    flag = true;
                    break;
                } else if (!(instructorFound(instructor))) {
                    flag = true;
                    outputFitnessClass.appendText(instructor + " - instructor does not exist.\n");
                    break;
                } else if (listOfMembers[i].getFname().equalsIgnoreCase(fName) &&
                        listOfMembers[i].getLname().equalsIgnoreCase(lName)
                        && listOfMembers[i].getDob().compareTo(dob) == 0 &&
                        (todayDate.compareTo(listOfMembers[i].getExpire()) == NOT_FOUND)) {
                    flag = true;
                    outputFitnessClass.appendText(fullName + " " + dob + " membership expired.\n");
                    break;
                } else if (listOfMembers[i].getFname().equalsIgnoreCase(fName) &&
                        listOfMembers[i].getLname().equalsIgnoreCase(lName)
                        && listOfMembers[i].getDob().compareTo(dob) == 0 && listOfMembers[i].getExpire().isValid()) {
                    flag = true;
                    checkInProcess(listOfMembers[i], nameOfClass, fullName, instructor, memberLocation);
                    break;
                }
            }

            if (!(flag)) {
                checkIfMemberInDatabase(listOfMembers, fName, lName, dob);
            }
        } catch (Exception ex) { //Ask?
            outputFitnessClass.appendText("Must enter all inputs required.\n");
        }
    }

    /**
     * Check if a member is in the membership database by comparing the member to
     * every person in the membership database. Compare members based on first name,
     * last name, and date of birth.
     *
     * @param listOfMembers listOfMembers is the total list of all the members in the membership database
     * @param firstName     member's first name
     * @param lastName      member's last name
     * @param dob           member's date of birth
     */
    private void checkIfMemberInDatabase(Member[] listOfMembers, String firstName, String lastName, Date dob) {
        boolean flag = true;
        for (Member m : listOfMembers) {
            if (m == null) {
                break;
            } else if (m.getFname().equalsIgnoreCase(firstName) && m.getLname().equalsIgnoreCase(lastName)
                    && m.getDob().compareTo(dob) == 0) {
                flag = false;
            }
        }
        if (flag) {
            outputFitnessClass.appendText(firstName + " " + lastName + " " + dob + " is not in the database.\n");
        }
    }

    /**
     * Checks if the instructor that a member is trying to check into actually has a fitness class or not
     *
     * @param instructorName instructorName is a string of the name of the instructor that the member is trying to check into
     * @return true if the instructor is found in the list of classes offered, false if not found
     */
    public boolean instructorFound(String instructorName) {
        for (int i = 0; i < schedule.getNumClasses(); i++) {
            if (schedule.getClasses()[i].getCourse().getInstructor().equalsIgnoreCase(instructorName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the Fitness class exists, if the location is valid, or if the Fitness class by that instructor at
     * that location exists. If the fitness class, instructor, and location are all correct, then check if the member
     * has already checked, or if the member has a standard location and is attempting to check in at a location that
     * is not allowed with the standard membership. Lastly, check in the member if everything else is correct.
     *
     * @param listOfMembers   listOfMembers that is checking in to a class
     * @param className       className is the fitness class name
     * @param fullName        fullName is the first and last name of the member
     * @param instructorName  instructorName is the name of the instructor teaching the Fitness class
     * @param locationOfClass locationOfClass is the location of the Fitness class
     */
    private void checkInProcess(Member listOfMembers, String className, String fullName, String instructorName,
                                String locationOfClass) {
        if (!(checkBasicRequirements(className, instructorName, locationOfClass))) {
            for (int i = 0; i < schedule.getNumClasses(); i++) {
                if (schedule.getClasses()[i].getCourse().getClassName().equalsIgnoreCase(className) &&
                        schedule.getClasses()[i].getCourse().getInstructor().equalsIgnoreCase(instructorName)
                        && schedule.getClasses()[i].getLocation().getCity().equalsIgnoreCase(locationOfClass)) {
                    if (((!(listOfMembers instanceof Premium)) && (!(listOfMembers instanceof Family)))
                            && (!(locationOfClass.equalsIgnoreCase(listOfMembers.getLocation().getCity())))) {
                        outputFitnessClass.appendText(fullName + " checking in " +
                                listOfMembers.getLocation().findLocation(locationOfClass)
                                + " - standard membership location restriction.\n");
                        break;
                    } else if (schedule.getClasses()[i].getClassMembers().contains(listOfMembers)) {
                        outputFitnessClass.appendText(fullName + " already checked in.\n");
                        break;
                    } else if (hasTimeConflict(listOfMembers, className, instructorName, locationOfClass)) {
                        outputFitnessClass.appendText("Time conflict - " +
                                schedule.getClasses()[i].getCourse().toString() + ", " +
                                listOfMembers.getLocation().findLocation(locationOfClass) + "\n");
                        break;
                    } else {
                        schedule.getClasses()[i].getClassMembers().add(listOfMembers);
                        outputFitnessClass.appendText(fullName + " checked in " + classFound(className, instructorName,
                                locationOfClass).toString() + "\n");
                        displaySpecificFitnessSchedule(className, instructorName, locationOfClass);
                    }
                }
            }
        }
    }

    /**
     * Performs basic checks on the inputs like the fitness class name, instructor name, and location of the class.
     *
     * @param className       className is the fitness class name
     * @param instructorName  instructorName is the name of the instructor teaching the Fitness class
     * @param locationOfClass locationOfClass is the location of the Fitness class
     * @return true if there was an issue in the input, false if the inputs passed all the checks
     */
    public boolean checkBasicRequirements(String className, String instructorName, String locationOfClass) {
        boolean flag = false;
        if (schedule.getClasses()[0].getLocation().findLocation(locationOfClass) == null) {
            outputFitnessClass.appendText(locationOfClass + " - invalid location.\n");
            flag = true;
        } else if ((!(className.equalsIgnoreCase("Pilates"))) &&
                (!(className.equalsIgnoreCase("Cardio")))
                && (!(className.equalsIgnoreCase("Spinning")))) {
            outputFitnessClass.appendText(className + " - class does not exist.\n");
            flag = true;
        } else if (!(instructorClassExists(className, instructorName, locationOfClass))) {
            outputFitnessClass.appendText(className + " by " + instructorName + " " +
                    "does not exist at " + locationOfClass + "\n");
            flag = true;
        }
        return flag;
    }

    /**
     * Iterates through all the available Fitness classes offered and identifies if the instructor teachs a fitness
     * class at the correct location that the member is trying to check into.
     *
     * @param className       className is the name of the class that the member is trying to check into
     * @param instructorName  instructorName is the name of the instructor that teaches the Fitness class that the
     *                        member is trying to check into
     * @param locationOfClass locationOfClass is the location of the fitness class that the member is trying to check into
     * @return true if the instructor is found to teach a Fitness class at the inputted location, false if the
     * instructor does not teach a Fitness class at the specified location
     */
    public boolean instructorClassExists(String className, String instructorName, String locationOfClass) {
        for (int i = 0; i < schedule.getNumClasses(); i++) {
            if (schedule.getClasses()[i].getCourse().getClassName().equalsIgnoreCase(className) &&
                    schedule.getClasses()[i].getCourse().getInstructor().equalsIgnoreCase(instructorName)
                    && schedule.getClasses()[i].getLocation().getCity().equalsIgnoreCase(locationOfClass)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a member has a time conflict for the fitness class they are trying to check into. If the time of any
     * two classes are the same and the instructors of the fitness classes are not the same, and if the conflict fitness
     * class already has that member checked in (present in its arraylist), then there is a conflict.
     *
     * @param listOfMember    listOfMember is the name of the member that is trying to check into the Fitness class
     * @param className       className is the name of the Fitness class that the member is trying to check into
     * @param instructorName  instructorName is the name of the instructor that teaches the Fitness class that the member
     *                        is trying to check into
     * @param locationOfClass locationOfClass is the location of the Fitness class that the person is trying to check into
     * @return true if there is a conflict, false if there is no conflict
     */
    private boolean hasTimeConflict(Member listOfMember, String className, String instructorName, String locationOfClass) {
        for (int i = 0; i < schedule.getNumClasses(); i++) {
            if (schedule.getClasses()[i].getCourse().getTime().equals(classFound(className, instructorName,
                    locationOfClass).getCourse().getTime()) &&
                    (!(schedule.getClasses()[i].getCourse().getInstructor().equalsIgnoreCase(instructorName)))
                    && (schedule.getClasses()[i].getClassMembers().contains(listOfMember))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Iterates through all the available Fitness classes offered and locates the class that the member is
     * trying to check into.
     *
     * @param className       className is the name of the class that the member is trying to check into
     * @param instructorName  instructorName is the name of the instructor that teaches the Fitness class that
     *                        the member is trying to check into
     * @param locationOfClass locationOfClass is the location of the fitness class that the member is trying to check into
     * @return the details of the fitness class that the member is trying to check into
     */
    public FitnessClass classFound(String className, String instructorName, String locationOfClass) {
        for (int i = 0; i < schedule.getNumClasses(); i++) {
            if (schedule.getClasses()[i].getCourse().getClassName().equalsIgnoreCase(className) &&
                    schedule.getClasses()[i].getCourse().getInstructor().equalsIgnoreCase(instructorName)
                    && schedule.getClasses()[i].getLocation().getCity().equalsIgnoreCase(locationOfClass)) {
                return schedule.getClasses()[i];
            }
        }
        return null;
    }

    /**
     * Display the schedule of the fitness class that is required and also display the participants of that fitness class.
     *
     * @param className       className is the name of the Fitness class that the member is checked into
     * @param instructorName  instructorName is the name of the instructor that teaches the Fitness class that the member
     *                        is checked into
     * @param locationOfClass locationOfClass is the location of the Fitness class that the person is checked into
     */
    private void displaySpecificFitnessSchedule(String className, String instructorName, String locationOfClass) {
        for (int i = 0; i < schedule.getNumClasses(); i++) {
            if (schedule.getClasses()[i].getCourse().getClassName().equalsIgnoreCase(className)
                    && schedule.getClasses()[i].getCourse().getInstructor().equalsIgnoreCase(instructorName)
                    && schedule.getClasses()[i].getLocation().getCity().equalsIgnoreCase(locationOfClass)) {
                if (schedule.getClasses()[i].getClassMembers().size() > 0) {
                    outputFitnessClass.appendText("\n- Participants -\n");
                    for (int j = 0; j < schedule.getClasses()[i].getClassMembers().size(); j++) {
                        outputFitnessClass.appendText("\t" + schedule.getClasses()[i].getClassMembers().get(j) + "\n");
                    }
                }
                if (schedule.getClasses()[i].getGuests().size() > 0) {
                    outputFitnessClass.appendText("\n- Guests -\n");
                    for (int j = 0; j < schedule.getClasses()[i].getGuests().size(); j++) {
                        outputFitnessClass.appendText("\t" + schedule.getClasses()[i].getGuests().get(j) + "\n");
                    }
                }
            }
        }
        outputFitnessClass.appendText("\n");
    }

    /**
     * Check if a member is in the membership database by comparing the member to
     * every person in the membership database. Compare members based on first name,
     * last name, and date of birth.
     *
     * @param listOfMembers listOfMembers is the total list of all the members in the membership database
     * @param firstName     member's first name
     * @param lastName      member's last name
     * @param dob           member's date of birth
     * @return true if the member is not found, false if the member is found
     */
    private boolean memberExists(Member[] listOfMembers, String firstName, String lastName, Date dob) {
        boolean flag = true;
        for (Member m : listOfMembers) {
            if (m == null) {
                break;
            } else if (m.getFname().equalsIgnoreCase(firstName) && m.getLname().equalsIgnoreCase(lastName) &&
                    m.getDob().compareTo(dob) == 0) {
                flag = false;
            }
        }
        return flag;
    }


    /**
     * Takes in input about the guest who wants to drop a class and checks that the class exists.
     *
     * @param event the event of clicking on the button to drop a guest from a class
     */
    @FXML
    public void dropGuestClass(ActionEvent event) {
        try {
            String fName = classFirstName.getText().trim();
            String lName = classLastName.getText().trim();
            String dobStr = classDob.getValue().toString().trim();
            Date dob = new Date(dobStr);
            String nameOfClass = className.getText().trim();
            String instructor = instructorName.getText().trim();
            String memberLocation = locationName.getText().trim();
            if (!checkInvalidInputFitness()) {
                return;
            }
            if (!containsIntOrDoubleFitness()) {
                return;
            }
            if (!checkSize()) {
                return;
            }
            if (!(checkBasicRequirements(nameOfClass, instructor, memberLocation))) {
                this.checkAndDropGuest(nameOfClass, instructor, memberLocation, fName, lName, dob);
            }
        } catch (Exception ex) { //Ask?
            outputFitnessClass.appendText("Must enter all inputs required.\n");
        }
    }

    /**
     * Checks out a guest from a class once they are done.
     * Accesses the member who invited them through the database ana makes sure the credentials for the class are correct.
     * Once verified, removes the guest from the ArrayList guests.
     *
     * @param className       name of class
     * @param instructorName  name of instructor
     * @param locationOfClass location of class
     * @param firstName       first name of Member
     * @param lastName        last name of Member
     * @param dob             Member's date of birth
     */
    private void checkAndDropGuest(String className, String instructorName, String locationOfClass, String firstName,
                                   String lastName, Date dob) {
        for (int i = 0; i < schedule.getNumClasses(); i++) {
            Member memberFound = timeToFindMember(accessMemberList.getMList(), firstName, lastName, dob);
            if ((schedule.getClasses()[i].getCourse().getClassName().equalsIgnoreCase(className) &&
                    schedule.getClasses()[i].getCourse().getInstructor().equalsIgnoreCase(instructorName)
                    && schedule.getClasses()[i].getLocation().getCity().equalsIgnoreCase(locationOfClass))
                    && (schedule.getClasses()[i].getGuests().contains(memberFound))) {
                schedule.getClasses()[i].getGuests().remove(memberFound);
                if (memberFound instanceof Family) {
                    ((Family) memberFound).increasePasses();
                }
                outputFitnessClass.appendText(firstName + " " + lastName + " Guest done with the class.\n");

                break;
            }
        }
    }


}