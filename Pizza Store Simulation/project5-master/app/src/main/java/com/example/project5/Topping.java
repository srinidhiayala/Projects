package com.example.project5;


/**
 * Houses constants Topping enum objects for the pizzas
 * @author Srinidhi Ayalasomayajula, Palak Mehta
 */
public enum Topping {
    SAUSAGE,
    BBQCHICKEN,
    PROVOLONE,
    CHEDDAR,
    BEEF,
    HAM,
    PEPPERONI,
    GREENPEPPER,
    ONION,
    MUSHROOM,
    PINEAPPLE,
    BLACKOLIVES,
    SPINACH;

    /**
     * Matches the string topping to the enum topping values
     * @param name String topping to be converted to an enum topping
     * @return enum topping
     */
    public static Topping fromString(String name) {
        Topping returnVal = null;
        for(Topping topping: Topping.values()) {
            if(name.equalsIgnoreCase(topping.name())) {
                returnVal = topping;
            }
        }
        return returnVal;
    }

}
