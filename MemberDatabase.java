/**
 * Stores and updates the gym's members regardless of location in one central
 * database Adds, finds, and removes members from the database Prints the
 * members in the database in 4 different ways in ascending order Displays
 * members unordered, sorted by county and zipcode, sorted by last and first
 * name, or sorted by expiration date
 * @author Srinidhi Ayalasomayajula, Palak Mehta
 */
public class MemberDatabase {
    private Member[] mlist;
    private int size;

    /**
     * This is a default constructor that initializes the list of members and sets a
     * default value for the size
     */
    public MemberDatabase() {
        final int SIZE=4;
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
     * @return members in mlist as Member array
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
        final int NOT_FOUND = -1;
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
     * Returns if a member is found in the membership database using the private find(Member member) method.
     * @param member member that needs to be found in the member database
     * @return index of member in membership database if found, else return the constant -1
     */
    public int foundMember(Member member){
        return find(member);
    }

    /**
     * Creates a copy of the membership database and increases the length by 4.
     * Copies the copy array back into the original array keeping track of the
     * membership database.
     */
    private void grow() {
        Member[] copy = new Member[mlist.length + 4];
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
        final int NOT_FOUND=-1;
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
     * Prints all the members currently in the membership database in the order
     * they were added. Empty membership databases should print that the database is
     * empty. Adds in the starting and ending format specific phrases "-list of
     * members-" and "-end of list-".
     */
    public void print() {
        if (size == 0) {
            System.out.println("Member database is empty!");
            return;
        }
        System.out.println();
        System.out.println("-list of members-");
        for (int i = 0; i < size; i++) {
            System.out.println(mlist[i].toString());
        }
        System.out.println("-end of list-");
        System.out.println();

    }

    /**
     * Creates a new membership array and copies the membership database into the
     * new membership array. Initializes the new membership array with the same size
     * as the number of members in the membership database. Deep copies the original membership array.
     *
     * @return the copied membership database as Member array
     */
    private Member[] memberCopyArray() {
        Member[] copy = new Member[size];
        for (int i = 0; i < size; i++) {
            copy[i] = mlist[i];
        }
        return copy;
    }

    /**
     * Prints the membership array after being sorted by using insertion sort. Adds
     * in the ending format specific phrases "-end of list-".
     *
     * @param arr member array that needs to be printed
     */
    private void printArray(Member[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i].toString());
        }
        System.out.println("-end of list-");
    }

    /**
     * Sorts the membership array by county and if the counties are the same, then
     * sort by zip code by using insertion sort. Checks in the membership database
     * is empty. Adds in the starting format specific phrases "-list of members
     * sorted by county and zipcode-".
     */
    public void printByCounty() {
        if (size == 0) {
            System.out.println("Member database is empty!");
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
        System.out.println();
        System.out.println("-list of members sorted by county and zipcode-");
        printArray(copy);
        System.out.println();
    }

    /**
     * Sorts the membership array by expiration date and if the expiration dates are
     * the same, then order of sorting does not matter by using insertion sort.
     * Checks in the membership database is empty. Adds in the starting format
     * specific phrases "-list of members sorted by membership expiration date-".
     */
    public void printByExpirationDate() {
        if (size == 0) {
            System.out.println("Member database is empty!");
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
        System.out.println();
        System.out.println("-list of members sorted by membership expiration date-");
        printArray(copy);
        System.out.println();
    }

    /**
     * Sorts the membership array by name and if the name are the same, then order
     * of sorting does not matter by using insertion sort. Checks in the membership
     * database is empty. Adds in the starting format specific phrases "-list of
     * members sorted by last name, and first name-".
     */
    public void printByName() {
        {
            if (size == 0) {
                System.out.println("Member database is empty!");
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
            System.out.println();
            System.out.println("-list of members sorted by last name, and first name-");
            printArray(copy);
            System.out.println();
        }
    }

}