package de.hdm_stuttgart.mi.Wgs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.hdm_stuttgart.mi.Users.User;
import java.io.Serializable;

final public class ListObject implements Serializable {

    private String note;
    private double value;
    private User user;

    /**
     * Constructor for class ListObject
     *
     * @param note name of item
     * @param value price of item
     * @param user  current user
     */
    @JsonCreator
    public ListObject(
            @JsonProperty("note") String note,
            @JsonProperty("value") double value,
            @JsonProperty("user") User user) {
        this.note = note;
        this.user = user;
        this.value = value;
    }

    /**
     * This method returns note
     *
     * @return note
     */
    public String getNote() {
        return note;
    }

    /**
     * This method returns value
     *
     * @return value
     */
    public double getValue() {
        return value;
    }

    /**
     * This method returns user
     *
     * @return user
     */
    public User getUser() {
        return user;
    }
}