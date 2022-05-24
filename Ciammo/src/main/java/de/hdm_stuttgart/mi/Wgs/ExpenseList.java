package de.hdm_stuttgart.mi.Wgs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.hdm_stuttgart.mi.Users.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;

/**
 * This class manages all expenses.
 */
public class ExpenseList extends List {

    private static final Logger log = LogManager.getLogger(ExpenseList.class);
    private static ExpenseList instance = null;

    /**
     * Constructor for class ExpenseList
     *
     * @param objects objects of the expense list
     */
    @JsonCreator
    public ExpenseList(
            @JsonProperty("objects") ArrayList<ListObject> objects) {
        super(objects);
    }

    /**
     * This method delivers the instance of the ExpenseList.
     *
     * @return instance of ExpenseList
     */
    public static ExpenseList getExpenseListInstance() {
        log.debug("getExpenseListInstance method started");
        if (instance == null)
            instance = new ExpenseList(new ArrayList<>());
        return instance;
    }

    /**
     * This method sets ExpenseList's instance null.
     */
    public void setExpenseListInstanceNull() {
        log.debug("setExpenseListInstanceNull method started");
        instance = null;
    }

    /**
     * This method adds object to ExpenseList.
     *
     * @param note what the money was for
     * @param value money that was used
     * @param user who paid for it
     */
    @Override
    public void add(String note, double value, User user) {
        log.debug("add method started");
        ListObject object = new ListObject(note, value, user);
        objects.add(object);
    }

    /**
     * This method filters expenses by username.
     *
     * @param username
     * @return if username is not blank, sum of all expenses by that specific user, -1 otherwise
     */
    public double filterUserExpenses(String username) {
        log.debug("filterUserExpenses method started");
        if (!username.isBlank()) {
            double sum = objects
                    .stream()
                    .parallel()
                    .filter(o -> o.getUser().getUsername().equals(username))
                    .mapToDouble(ListObject::getValue)
                    .sum();
            return sum;
        }
        return -1;
    }
}


