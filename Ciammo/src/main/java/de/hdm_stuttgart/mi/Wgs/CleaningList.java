package de.hdm_stuttgart.mi.Wgs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.hdm_stuttgart.mi.Users.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;

/**
 * This class manages the cleaning list.
 */
public class CleaningList extends List {

    private static final Logger log = LogManager.getLogger(CleaningList.class);
    private static CleaningList instance = null;

    /**
     * Constructor for class CleaningList
     *
     * @param objects objects of the cleaning list
     */
    @JsonCreator
    public CleaningList(
            @JsonProperty("objects") ArrayList<ListObject> objects) {
        super(objects);
    }

    /**
     * This method delivers the instance of the CleaningList.
     *
     * @return instance of CleaningList
     */
    public static CleaningList getCleaningListInstance() {
        log.debug("getCleaningListInstance method started");
        if (instance == null)
            instance = new CleaningList(new ArrayList<>());
        return instance;
    }

    /**
     * This method sets CleaningList's instance null.
     */
    public void setCleaningListInstanceNull() {
        log.debug("setCleaningListInstanceNull method started");
        instance = null;
    }

    /**
     * This method adds object to cleaning list.
     *
     * @param note cleaning task that needs to be done
     * @param value calender week
     * @param user user that needs to do the task
     */
    @Override
    public void add(String note, double value, User user) {
        log.debug("add method started");
        // Double values are converted to integers
        ListObject object = new ListObject(note, value, user);
        objects.add(object);
    }
}