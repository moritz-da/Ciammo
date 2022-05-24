package de.hdm_stuttgart.mi.Wgs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.hdm_stuttgart.mi.Users.User;
import de.hdm_stuttgart.mi.Users.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Wg class defines the wg data (wgName, wgID, wgIdCount, wgPassword, their users, their lists).
 */
public class Wg implements Serializable {

    private static final Logger log = LogManager.getLogger(Wg.class);
    private String wgName;
    private int wgID;
    private static int wgIdCount = 100;
    private String wgPassword;

    private ArrayList<User> users = new ArrayList<>();
    private ShoppingList shoppingList = ShoppingList.getShoppingListInstance();
    private ExpenseList expenseList = ExpenseList.getExpenseListInstance();
    private CleaningList cleaningList = CleaningList.getCleaningListInstance();

    /**
     * Constructor for class Wg
     *
     * @param wgName the chosen wg name
     * @param wgPassword password for this wg
     */
    @JsonCreator
    public Wg(
            @JsonProperty("wgName") String wgName,
            @JsonProperty("wgPassword") String wgPassword) {
        this.wgName = wgName;
        this.wgPassword = wgPassword;
        this.wgID = wgIdCount++;
    }

    /**
     * This method changes wgName of the wg.
     *
     * @param wgName chosen wgName
     */
    public void setWgNameName(String wgName) {
        this.wgName = wgName;
    }

    /**
     * This method changes wgPassword of the wg.
     *
     * @param wgPassword chosen wgPassword
     */
    public void setWgPassword(String wgPassword) {
        this.wgPassword = wgPassword;
    }

    /**
     * This method delivers the wgName.
     *
     * @return wgName
     */
    public String getWgName() {
        return wgName;
    }

    /**
     * This method delivers the wgID.
     *
     * @return wgID
     */
    public int getWgID() {
        return wgID;
    }


    /**
     * This method delivers the wgPassword.
     *
     * @return wgPassword
     */
    public String getWgPassword() {
        return wgPassword;
    }

    /**
     * This method delivers the shopping list.
     *
     * @return shoppingList
     */
    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    /**
     * This method delivers the expense list.
     *
     * @return expenseList
     */
    public ExpenseList getExpenseList() {
        return expenseList;
    }

    /**
     * This method delivers the cleaning list.
     *
     * @return cleaningList
     */
    public CleaningList getCleaningList() {
        return cleaningList;
    }

    /**
     * This method adds an user to a wg.
     *
     * @param user user that is supposed to be added
     */
    public void addUser(User user) {
        log.debug("addUser method started");
        if (user != null) {
            users.add(user);
            user.setWgID(this.wgID);
            log.info("addUser method, a user has been added to a Wg");
        }
    }

    /**
     * This method removes user from a wg.
     *
     * @param user user that is supposed to be removed
     */
    public void removeUser(User user) {
        log.debug("removeUser method started");
        users.remove(user);
        UserManager.getInstance().resetWgID(user);
    }

    /**
     * This method delivers an Arraylist with all users.
     *
     * @return usersCopy
     */
    public ArrayList<User> getUsers() {
        log.debug("getUsers method started");
        ArrayList<User> usersCopy = new ArrayList<>(users);
        return usersCopy;
    }

    /**
     * This method delivers a String with wg data.
     *
     * @return String
     */
    @Override
    public String toString() {
        log.debug("toString method started");
        return "Wg-Daten: " + wgName + ", " + wgID + ", " + wgPassword + ", " + users + ", Shopping List: " + shoppingList;
    }
}
