package de.hdm_stuttgart.mi.Wgs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.hdm_stuttgart.mi.Users.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class List implements Serializable {

    private static final Logger log = LogManager.getLogger(List.class);
    protected ArrayList<ListObject> objects;

    /**
     * Constructor for class List
     *
     * @param objects Arraylist with objects
     */
    @JsonCreator
    public List (
            @JsonProperty("objects") ArrayList<ListObject> objects) {
        this.objects = objects;
    }

    /**
     * Delivers object list
     *
     * @return ArrayList
     */
    public ArrayList<ListObject> getObjects() {
        log.debug("getObjects method started");
        return new ArrayList<>(objects);
    }

    /**
     * This method removes a given object
     *
     * @param object object given from user
     */
    public void remove(ListObject object) {
        log.debug("removeObjects method started");
        objects.remove(object);
    }

    /**
     * This method checks if list was deleted successfully
     *
     * @return true if list was deleted successfully, false otherwise
     */
    @JsonIgnore
    public boolean isListSuccessfullyDeleted() {
        log.debug("isListSuccessfullyDeleted method started");
        if (!objects.isEmpty()) {
            objects = new ArrayList<ListObject>();
            return true;
        }
        return false;
    }

    /**
     * This method adds a new row with note, value, user
     */
    public abstract void add(String note, double value, User user);
}
