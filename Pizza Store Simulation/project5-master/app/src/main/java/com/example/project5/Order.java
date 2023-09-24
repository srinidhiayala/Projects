package com.example.project5;




import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class defines an abstract data type Order that represents an order
 * of pizzas from a store. It keeps tracks of the pizzas placed as well
 * as their total amounts before tax and after tax.
 *
 * @author Srinidhi Ayalasomayajula, Palak Mehta
 */
public class Order implements Customizable {

    private int orderID;

    private ArrayList<Pizza> items;
    private ArrayList<String> pizzaToString;

    private double costBeforeTax;

    private static final double NJ_SALES_TAX = 0.06625;
    private boolean isSelected;

    private DecimalFormat df = new DecimalFormat("#.##");


    /**
     * Constructor creates a new Order object with items set as a new
     * ArrayList and subtotals set to 0. Also initializes a String Array List
     * for the textual representations of the orders.
     */

    public Order() {
        items = new ArrayList<>();
        pizzaToString = new ArrayList<>();
        costBeforeTax = 0.00;
        isSelected = false;
    }

    /**
     * Sets the value of the isSelected field to be equal to the value of the parameter
     * userSelected
     * @param userSelected the value to set the isSelected field equal to
     */
    public void setSelected(boolean userSelected){
        this.isSelected = userSelected;
    }

    /**
     * Returns the value of the isSelected field
     * @return the value of the isSelected field
     */
    public boolean isSelected(){
        return this.isSelected;
    }

    /**
     * Returns the array list containing the orders of pizzas.
     *
     * @return the array list of pizzas
     */
    public ArrayList<Pizza> getItems() {
        return items;
    }

    /**
     * Sets a given number as the order ID for an order.
     *
     * @param ID number to set as order ID.
     */
    public void setOrderID(int ID) {
        orderID = ID;
    }

    /**
     * Gets the order ID of an order.
     *
     * @return orderID of order.
     */
    public int getOrderId() {
        return orderID;
    }

    /**
     * Gets the cost before tax (subtotal).
     *
     * @return subtotal of the order.
     */
    public double getCostBeforeTax() {
        return costBeforeTax;
    }

    /**
     * Sets the subtotal amount.
     *
     * @param amount amount to be the subtotal.
     */
    public void setCostBeforeTax(double amount) {
        costBeforeTax = amount;
    }

    /**
     * Calculates how much tax will be added to the order.
     *
     * @return sales tax on the order.
     */
    public double getSalesTax() {
        return costBeforeTax * NJ_SALES_TAX;
    }

    /**
     * Calculates the total amount due for the order.
     *
     * @return total cost with tax.
     */
    public double getCostAfterTax() {
        return (costBeforeTax * NJ_SALES_TAX) + costBeforeTax;
    }


    /**
     * Indicates whether a given object 'equals' this Order.
     *
     * @param obj to be casted into an Order and compared with.
     * @return true if the OrderIDs are equal, false of otherwise or if obj
     * is not an Order.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Order)) {
            return false;
        }
        return this.orderID == ((Order) obj).orderID;
    }

    /**
     * Adds a pizza to the order.
     *
     * @param obj Object representing the item to be added.
     * @return true if the object was added, false if otherwise.
     */
    @Override
    public boolean add(Object obj) {
        if (!(obj instanceof Pizza)) {
            return false;
        }
        items.add((Pizza) obj);
        if (obj instanceof Deluxe) {
            costBeforeTax += ((Deluxe) obj).price();
        } else if (obj instanceof BBQChicken) {
            costBeforeTax += ((BBQChicken) obj).price();
        } else if (obj instanceof Meatzza) {
            costBeforeTax += ((Meatzza) obj).price();
        } else if (obj instanceof BuildYourOwn) {
            costBeforeTax += ((BuildYourOwn) obj).price();
        }

        return true;
    }

    public String getToppings(Object obj){
        String s = "";
        ArrayList<Topping> toppings = null;
        if (obj instanceof Deluxe) {
            toppings = (ArrayList<Topping>) ((Deluxe) obj).getToppings();
        } else if (obj instanceof BBQChicken) {
            toppings = (ArrayList<Topping>) ((BBQChicken) obj).getToppings();
        } else if (obj instanceof Meatzza) {
            toppings = (ArrayList<Topping>) ((Meatzza) obj).getToppings();
        } else {
            toppings = (ArrayList<Topping>) ((BuildYourOwn) obj).getToppings();
        }
        if (toppings == null ) {
            return "NO TOPPINGS";
        }
        else {
            for(int i=0; i<toppings.size(); i++) {
                if (i == toppings.size() - 1) {
                    s += toppings.get(i);
                } else {
                    s += toppings.get(i) + ", ";
                }
            }
        }
        return s;
    }

    /**
     * Creates a textual representation of each order and adds it to the String array containing details of each pizza.
     *
     * @param obj       Object representing the item to be added.
     * @param pizzaType String indicating style of Pizza (Chicago or NY)
     * @return true if a textual representation of the added object was added, false otherwise
     */
    public boolean addToStringArray(Object obj, String pizzaType) {
        if (!(obj instanceof Pizza)) {
            return false;
        }
        String toppings = getToppings(obj);
        if (obj instanceof Deluxe) {
            Crust crust = ((Deluxe) obj).getCrust();
            Size size = ((Deluxe) obj).getSize();
            double price = ((Deluxe) obj).price();
            pizzaToString.add("Deluxe (" + pizzaType + " - " + crust + "), " + toppings + "," +
                    size + "," + "$" + (String.valueOf(df.format(price))));
        } else if (obj instanceof BBQChicken) {
            Crust crust = ((BBQChicken) obj).getCrust();
            Size size = ((BBQChicken) obj).getSize();
            double price = ((BBQChicken) obj).price();
            pizzaToString.add("BBQ Chicken (" + pizzaType + " - " + crust + "), " + toppings + "," +
                    size + "," + "$" + (String.valueOf(df.format(price))));
        } else if (obj instanceof Meatzza) {
            Crust crust = ((Meatzza) obj).getCrust();
            Size size = ((Meatzza) obj).getSize();
            double price = ((Meatzza) obj).price();
            pizzaToString.add("Meatzza (" + pizzaType + " - " + crust + "), " + toppings + "," +
                    size + "," + "$" + ((String.valueOf(df.format(price)))));
        } else if (obj instanceof BuildYourOwn) {
            Crust crust = ((BuildYourOwn) obj).getCrust();
            Size size = ((BuildYourOwn) obj).getSize();
            double price = ((BuildYourOwn) obj).price();
            pizzaToString.add("BuildYourOwn (" + pizzaType + " - " + crust + "), " + toppings + "," +
                    size + "," + "$" + ((String.valueOf(df.format(price)))));
        }
        return true;
    }


    /**
     * Removes a pizza  from the order.
     *
     * @param obj Object representing the item to be removed.
     * @return true if the object was removed, false if otherwise.
     */
    @Override
    public boolean remove(Object obj) {
        if (!items.contains(obj)) {
            return false;
        }
        if (obj instanceof Deluxe) {
            costBeforeTax -= ((Deluxe) obj).price();
        } else if (obj instanceof BBQChicken) {
            costBeforeTax -= ((BBQChicken) obj).price();
        } else if (obj instanceof Meatzza) {
            costBeforeTax -= ((Meatzza) obj).price();
        } else if (obj instanceof BuildYourOwn) {
            costBeforeTax -= ((BuildYourOwn) obj).price();
        }
        items.remove((Pizza) obj);
        return true;
    }

    /**
     * A list to be set into ListView of the pizzas.
     *
     * @return an observable list of the Pizzas.
     */
    //  public ObservableList<Pizza> getListPizzas() {
    //    return FXCollections.observableList(items);
    // }

    /**
     * Returns the array list that has the textual details of the orders.
     *
     * @return Array List of string representation.
     */
    public ArrayList<String> getPizzaToString() {
        return pizzaToString;
    }

    /**
     * Returns an observable list of the string representations of the orders.
     *
     * @return an observable list of the string representations of the orders.
     */
    // public ObservableList<String> getStringPizzas() {
    //     return FXCollections.observableList(pizzaToString);
    // }

    /**
     * Returns a textual representation of a numerical monetary amount.
     *
     * @param input value to be formatted
     * @return String representation of input double in format $0.00.
     */
    private String amountString(double input) {
        DecimalFormat twoDecimalPlaces = new DecimalFormat("$0.00");
        return twoDecimalPlaces.format(input);
    }

    /**
     * Returns a string that details all the information about a single order.
     *
     * @return String formatted with the pizza orders, subtotal, sales tax and total for each order.
     */
    public String storeOrders() {
        String orderList = "OrderID: " + orderID + "\n";
        for (String i : pizzaToString) {
            orderList += i + "\n";
        }
        orderList += "Subtotal: " +
                amountString(costBeforeTax) + ", Tax: " +
                amountString(costBeforeTax * NJ_SALES_TAX) + ", Total: " +
                amountString(getCostAfterTax()) + "\n";
        return orderList;
    }

    /**
     * Returns a textual representation of this Order.
     *
     * @return String representation of this order with ID, subtotal, tax,
     * total, and all items in the order.
     */
    @Override
    public String toString() {
        String orderList = "OrderID: " + orderID + ", Subtotal: " +
                amountString(costBeforeTax) + ", Tax: " +
                amountString(costBeforeTax * NJ_SALES_TAX) + ", Total: " +
                amountString(getCostAfterTax());
        for (String i : pizzaToString) {
            orderList += "\n" + i;
        }
        return orderList;
    }

}
