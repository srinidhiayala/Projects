package com.example.project5;

import java.io.Serializable;

/**
 * This class defines the data structure of an item to be displayed in the RecyclerView
 * @author Lily Chang
 */
public class Item implements Serializable {
    private String pizzaString;
    //private int image;

    /**
     * Parameterized constructor.
     * @param pizzaString
     */
    public Item(String pizzaString) {
        this.pizzaString = pizzaString;
        //this.image = image;
    }

    /**
     * Getter method that returns the item name of an item.
     * @return the item name of an item.
     */
    public String getPizzaString() {
        return pizzaString;
    }

    /**
     * Getter method that returns the image of an item.
     * @return the image of an item.
     */
    //public int getImage() {return image;}

    /**
     * Getter method that returns the unit price.
     * @return the unit price of the item.
     */
}
