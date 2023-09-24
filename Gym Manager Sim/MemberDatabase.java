package com.example.project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Stores and updates the gym's members regardless of location in one central
 * database Adds, finds, and removes members from the database Prints the
 * members in the database in 4 different ways in ascending order Displays
 * members unordered, sorted by county and zipcode, sorted by last and first
 * name, or sorted by expiration date
 *
 * @author Srinidhi Ayalasomayajula, Palak Mehta
 */
public class MemberDatabase {
    private Member[] mlist;
    private int size;
    public static final int SIZE = 4;
    public static final int NOT_FOUND = -1;
    public static boolean LOADED_MEMBERS = false;
    private String[] eachPhraseArray = new String[SIZE_OF_INPUT];
    public static final int SIZE_OF_INPUT = 6;
    private static final int LOADED = 2;


    /**
     * This is a default constructor that initializes the list of members and sets a
     * default value for the size.
     */
    public MemberDatabase() {
        mlist = new Member[SIZE];
        size = 0;
    }

    /**
     * Returns the size of the total number members in the database that are members
     * at the gym.
     *
     * @return memberlist's size as int
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns all the members in the member list array.
     *
     * @return members in mlist as gym.Member array
     */
    public Member[] getMList() {
        return mlist;
    }

    /**
     * Returns if a member is found in the membership database. Creates a final
     * constant to track if the member does no exist in the database.
     *
     * @param member member that needs to be found in the member database
     * @return index of member in membership database if found, else return the
     * constant -1
     */
    private int find(Member member) {
        if (size == 0) {
            return NOT_FOUND;
        }
        for (int i = 0; i < size; i++) {
            if (mlist[i].equals(member)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Returns if a member is found in the membership database using the private find(gym.Member member) method.
     *
     * @param member member that needs to be found in the member database
     * @return index of member in membership database if found, else return the constant -1
     */
    public int foundMember(Member member) {
        return find(member);
    }

    /**
     * Creates a copy of the membership database and increases the length by 4.
     * Copies the copy array back into the original array keeping track of the
     * membership database.
     */
    private void grow() {
        Member[] copy = new Member[mlist.length + SIZE];
        for (int i = 0; i < mlist.length; i++) {
            copy[i] = mlist[i];
        }
        mlist = copy;
    }

    /**
     * Adds the member into the membership database and increases the size counter
     * of the mlist.
     *
     * @param member member that needs to be added in the member database
     * @return true once member is added
     */
    public boolean add(Member member) {
        if (this.size == mlist.length) {
            grow();
        }
        mlist[size] = member;
        size++;
        return true;
    }

    /**
     * Removes the member from the membership database and decrease the size counter
     * of the mlist.
     *
     * @param member member that needs to be removed from the member database
     * @return true once member is removed, false if member does not exist in the
     * database
     */
    public boolean remove(Member member) {
        int found = find(member);
        if (found == NOT_FOUND) {
            return false;
        } else {
            mlist[found] = null;
            for (int i = found; i < mlist.length - 1; i++) {
                mlist[i] = mlist[i + 1];
            }
            mlist[size - 1] = null;
            size--;
            return true;
        }
    }


    /**
     * Creates a new membership array and copies the membership database into the
     * new membership array. Initializes the new membership array with the same size
     * as the number of members in the membership database. Deep copies the original membership array.
     *
     * @return the copied membership database as gym.Member array
     */
    private Member[] memberCopyArray() {
        Member[] copy = new Member[size];
        for (int i = 0; i < size; i++) {
            copy[i] = mlist[i];
        }
        return copy;
    }


    /**
     * Returns a sorted array based on last and then first name.
     *
     * @return sorted gym Member array by last and first name
     */
    public Member[] printingByName() {
        {
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
            return copy;
        }
    }

    /**
     * Read from the file and adds the members into the database.
     *
     * @param sourceFile       file to be read from.
     * @param accessMemberList Database of members to add to.
     * @return 1 if members are added successfully, 0 if file is not found and 2 if file is already laoded.
     */
    public int importFile(File sourceFile, MemberDatabase accessMemberList) {
        if (!LOADED_MEMBERS) {
            try {
                Scanner userFile = new Scanner(sourceFile);
                while (userFile.hasNext()) {
                    String input = userFile.nextLine();
                    this.addFileMembers(input, accessMemberList);
                }
                LOADED_MEMBERS = true;
                return 1;
            } catch (FileNotFoundException e) {
                return 0;
            }
        } else {
            return LOADED;
        }

    }

    /**
     * Creates a member object by parsing the parameter with space delimiters.
     * Then the member object is added to the member database.
     *
     * @param userInput        line of String input with details about member to add
     * @param accessMemberList member database that houses all members.
     */
    public void addFileMembers(String userInput, MemberDatabase accessMemberList) {
        StringTokenizer parsedInput = new StringTokenizer(userInput, " ");
        String fname = parsedInput.nextToken();
        String lname = parsedInput.nextToken();
        String dob = parsedInput.nextToken();
        String expireDate = parsedInput.nextToken();
        String location = parsedInput.nextToken();
        Date expire = new Date(expireDate);
        Member person = new Member(fname, lname, dob, expire, location);
        accessMemberList.add(person);
    }

}
