package de.hdm_stuttgart.mi.Wgs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * This class manages the shopping list.
 */
public class ShoppingList implements Serializable {

    private static final Logger log = LogManager.getLogger(ShoppingList.class);
    private ArrayList<String> notes;
    private static ShoppingList instance = null;


    /**
     * Constructor for class ShoppingList
     *
     * @param notes items that need to be bought
     */
    @JsonCreator
    private ShoppingList(
            @JsonProperty("notes") ArrayList<String> notes) {
        log.debug("Konstruktor von ShoppingList aufgerufen");
        this.notes = notes;
    }

    /**
     * This method delivers the instance of the ShoppingList.
     *
     * @return instance of ShoppingList
     */
    public static ShoppingList getShoppingListInstance() {
        log.debug("getShoppingListInstance method started");
        if (instance == null)
            instance = new ShoppingList(new ArrayList<>());
        return instance;
    }

    /**
     * This method sets the instance of the ShoppingList null.
     */
    public void setShoppingListInstanceNull() {
        log.debug("setShoppingListInstanceNull method started");
        instance = null;
    }

    /**
     * This method delivers notes of the shopping list.
     *
     * @return notesCopy
     */
    public ArrayList<String> getNotes() {
        log.debug("getNotes method started");
        ArrayList<String> notesCopy = new ArrayList<>(notes);
        return notesCopy;
    }

    /**
     * This method adds note to shopping list.
     *
     * @param note item that needs to be bought
     */
    public void add(String note) {
        log.debug("add method started");
        this.notes.add(note);
    }

    /**
     * This method removes note from shopping list.
     */
    public void remove(String note) {
        log.debug("remove method started");
        this.notes.remove(note);
    }

    /**
     * This method removes all notes from shopping list.
     */
    public void removeAll() {
        log.debug("removeAll method started");
        if (!notes.isEmpty()) {
            notes = new ArrayList<>();
            log.debug("notes ArrayList deleted");
        }
    }

    /**
     * This method sorts notes alphabetically.
     */
    public void sort() {
        log.debug("sort method started");
        ArrayList<String> notesCopy = new ArrayList<>(notes);
        notes = notesCopy
                .stream()
                .parallel()
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));
    }
}