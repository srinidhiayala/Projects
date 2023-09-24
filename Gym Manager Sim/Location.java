package com.example.project3;

/**
 * Houses and performs operations on either of 5 gym locations (based on city) that a member joins
 *
 * @author Srinidhi Ayalasomayajula, Palak Mehta
 */
public enum Location {
    BRIDGEWATER("BRIDGEWATER", "08807", "Somerset"),
    EDISON("EDISON", "08837", "Middlesex"),
    FRANKLIN("FRANKLIN", "08873", "Somerset"),
    PISCATAWAY("PISCATAWAY", "08854", "Middlesex"),
    SOMERVILLE("SOMERVILLE", "08876", "Somerset");

    private final String city;
    private final String zipCode;
    private final String county;

    /**
     * Assigns a new enum location with the given parameters.
     *
     * @param city    city of gym location
     * @param zipCode zipcode of city
     * @param county  county of gym location
     */
    Location(String city, String zipCode, String county) {
        this.zipCode = zipCode;
        this.county = county;
        this.city = city;
    }

    /**
     * Returns zipcode of location parsing the string into an integers.
     *
     * @return zipcode as int
     */
    public int getZipCode() {
        return Integer.parseInt(this.zipCode);
    }

    /**
     * Returns gym location's county.
     *
     * @return gym location's county as String
     */
    public String getCounty() {
        return this.county;
    }

    /**
     * Returns gym location's city.
     *
     * @return gym location's city as String
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Evaluates if the location is one of the 5 gym locations.
     *
     * @return true if valid location, false otherwise
     */
    public boolean validLocation() {

        if ((city.equals("BRIDGEWATER")) || (city.equals("EDISON"))
                || (city.equals("FRANKLIN")) || (city.equals("PISCATAWAY"))
                || (city.equals("SOMERVILLE"))) {
            return true;
        }
        return false;
    }

    /**
     * Finds the gym.Location that matches the parameter String city.
     *
     * @param locationCity locationCity is the String representation of the city name
     * @return the gym.Location that is to be matched and found, null if location does not match
     */
    public Location findLocation(String locationCity) {
        if (locationCity.equalsIgnoreCase("BRIDGEWATER")) {
            return BRIDGEWATER;
        } else if (locationCity.equalsIgnoreCase("EDISON")) {
            return EDISON;
        } else if (locationCity.equalsIgnoreCase("FRANKLIN")) {
            return FRANKLIN;
        } else if (locationCity.equalsIgnoreCase("PISCATAWAY")) {
            return PISCATAWAY;
        } else if (locationCity.equalsIgnoreCase("SOMERVILLE")) {
            return SOMERVILLE;
        } else {
            return null;
        }
    }

    /**
     * Returns a string detailing the instance variables of the gym location.
     *
     * @return String of CITY, zipcode, COUNTY
     */
    @Override
    public String toString() {
        return city.toUpperCase() + ", " + zipCode + ", " + county.toUpperCase();
    }

}