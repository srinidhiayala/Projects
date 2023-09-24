package com.example.project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * Keeps track of all the available fitness classes along with who is teaching them, what time and what location.
 *
 * @author Srinidhi Ayalasomayajula and Palak Mehta
 */
public class ClassSchedule {
    private FitnessClass[] classes;
    private int numClasses;
    public static final int MAX_SIZE = 50;
    public static boolean LOADED_CLASSES = false;
    private String[] eachPhraseArray = new String[SIZE_OF_INPUT];
    public static final int SIZE_OF_INPUT = 6;
    private static final int LOADED = 2;


    /**
     * Initializes the size of the gym.Fitness array to however many classes are offered through the user file
     * and initializes the number of classes to zero.
     */
    public ClassSchedule() {
        classes = new FitnessClass[MAX_SIZE];
        numClasses = 0;
    }

    /**
     * Adds a course from the time schedule to the gym.FitnessClass array
     *
     * @param course gym.Fitness course to add to the gym.FitnessClass array
     */
    public void addClass(FitnessClass course) {
        classes[numClasses] = course;
        numClasses++;
    }

    /**
     * Returns the instance variable classes
     *
     * @return gym.FitnessClass array classes
     */
    public FitnessClass[] getClasses() {
        return this.classes;
    }

    /**
     * Returns the instance variable numClasses
     *
     * @return number of classes
     */
    public int getNumClasses() {
        return numClasses;
    }

    /**
     * Reads the file and adds the fitness classes to an array.
     *
     * @param sourceFile file to read from.
     * @param schedule   ClassSchedule that holds all the fitness class schedules.
     * @return 1 if the file is loaded properly,0 if there is an exception (file not found), 2 if file has already been
     * loaded.
     */
    public int importFile(File sourceFile, ClassSchedule schedule) {
        if (!LOADED_CLASSES) {
            try {
                Scanner userFile = new Scanner(sourceFile);
                while (userFile.hasNext()) {
                    String input = userFile.nextLine();
                    eachPhraseArray = input.split(" ");
                    FitnessClass course = new FitnessClass(eachPhraseArray[0], eachPhraseArray[1], eachPhraseArray[2],
                            eachPhraseArray[3]);
                    schedule.addClass(course);
                }
                LOADED_CLASSES = true;
                return 1;
            } catch (FileNotFoundException e) {
                return 0;
            }
        } else {
            return LOADED;
        }

    }

}
