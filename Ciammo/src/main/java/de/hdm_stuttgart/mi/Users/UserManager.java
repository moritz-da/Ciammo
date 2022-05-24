package de.hdm_stuttgart.mi.Users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.hdm_stuttgart.mi.CiammoGUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * UserManager class manages all users
 */
public class UserManager implements Serializable {

    private static final Logger log = LogManager.getLogger(UserManager.class);
    private ArrayList<User> users;
    private static UserManager instance = null;

    /**
     * Constructor for class UserManager
     *
     * @param users a Arraylist which contains all users
     */
    @JsonCreator
    private UserManager(
            @JsonProperty("users") ArrayList<User> users) {
        this.users = users;
    }

    /**
     * Delivers the instance of UserManager. We use Singelton Access. There should be only one UserManager.
     *
     * @return instance of UserManager
     */
    public static UserManager getInstance() {
        log.debug("getInstance method started");
        if (instance == null)
            instance = new UserManager(new ArrayList<>());
        return instance;
    }

    /**
     * saves permanent Data for the program
     */
    public static synchronized String saveUserManager() {
        log.debug("saveUserManager method started");
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            String resourcePath = "";
            try {
                final String os = System.getProperty("os.name"); // os = operating system

                if (os.contains("Windows")) {
                    resourcePath = ".\\src\\main\\resources\\data\\saveUserData.json";
                } else {
                    resourcePath = "./src/main/resources/data/saveUserData.json";
                }
            } catch (final Exception e) {
                log.error("Error while trying to load resourcePath in loadUserManager method: " + e.getMessage());
            }

            // Java objects to JSON file
            mapper.writeValue(new File(resourcePath), getInstance());
            log.info("Permanent Data (UserManager instance) is saved - saveUserManager method");

            // Java objects to JSON string - pretty-print for the console if needed
            String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getInstance());
            return jsonInString;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Saving data failed - saveUserManager method");
            return "";
        }
    }

    /**
     * Loads permanent data for the program
     *
     * @return UserManager object if data was saved already, null if it cannot load the data
     */
    public static synchronized UserManager loadUserManager() {
        log.debug("loadUserManager method started");
        ObjectMapper mapper = new ObjectMapper();

        try {
            String resourcePath = "";
            try {
                final String os = System.getProperty("os.name"); // os = operating system

                if (os.contains("Windows")) {
                    resourcePath = ".\\src\\main\\resources\\data\\saveUserData.json";
                } else {
                    resourcePath = "./src/main/resources/data/saveUserData.json";
                }
            } catch (final Exception e) {
                log.fatal("Error while trying to load resourcePath in loadUserManager method");
            }

            // JSON file to Java object
            instance = mapper.readValue(new File(resourcePath), UserManager.class);

            log.info("Permanent data is loaded - loadUserManager method");
            return instance;
        } catch (IOException e) {
            e.printStackTrace();
            log.fatal("Loading permanent data failed - loadUserManager method");
            return null;
        }
    }

    /**
     * Changes the instance of UserManager.
     */
    public void setInstanceNull() {
        log.debug("setInstanceNull method started");
        instance = null;
    }

    /**
     * delivers Arraylist with all users
     *
     * @return list with all users
     */
    public ArrayList<User> getUsers() {
        log.debug("getUsers method started");
        ArrayList<User> accountsCopy = new ArrayList<>(users);
        return accountsCopy;
    }

    /**
     * adds an user
     *
     * @param user user that should be added,
     * @return true if user was added, false otherwise
     */
    public boolean addUser(User user) {
        log.debug("addUser method started");
        if (user == null) {
            log.error("addAccount method, an empty account was tried to add in the accounts ArrayList");
            return false;
        }
        else {
            users.add(user);
            log.info("addAccount method, a user was added to UserManager");
            return true;
        }
    }

    /**
     * deletes an user completely
     *
     * @param user user that should be deleted,
     * @return false if user cannot be deleted, true otherwise
     */
    public boolean deleteUser(User user) {
        log.debug("deleteUser method started");
        if (user != null) {
            if (user.getWgID() == 0) {
                users.remove(user);
                log.info("deleteUser method, User has been deleted");
                return true;
            }
            log.debug("deleteUser method, User cannot be deleted because he is still a member of a Wg");
        }
        return false;
    }

    /**
     * reset WgID of the user (set WgID to 0)
     *
     * @param user
     */
    public void resetWgID(User user) {
        log.debug("resetWgID method started");
        for (int i=0; i < users.size(); i++) {
            if (user.getUsername().equals(users.get(i).getUsername())) {
                users.get(i).setWgID(0);
            }
        }
    }

    /**
     * Proves if the username already exists or not in the Arraylist users
     *
     * @param username
     * @return return true if username is available, false otherwise
     */
    public boolean isUsernameAvailable(String username) {
        log.debug("isUsernameAvailable method started");
        boolean isAvailable = true;
        for (int i=0; i < users.size(); i++) {
            if (username.equals(users.get(i).getUsername())) {
                isAvailable = false;
                break;
            }
        }
        if (isAvailable) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * delivers user
     *
     * @param username
     * @return user
     */
    public User getUser(String username) {
        log.debug("getUser method started");
        if (username != null) {
            for (int i=0; i<users.size(); i++) {
                if (users.get(i).getUsername().equals(username)) {
                    return users.get(i);
                }
            }
        }
        return null;
    }

    /**
     * login of one user
     *
     * @param username username of the user
     * @param password password of the user
     * @return false if login failed, true otherwise
     */
    public boolean login(String username, String password) {
        log.debug("login method started");
        for (int i=0; i < users.size(); i++) {
            if (username.equals(users.get(i).getUsername())) {
                if (password.equals(users.get(i).getPassword())) {
                    CiammoGUI.setCurrentUser(users.get(i));
                    return true;
                }
            }
        }
        log.debug("login method, User login failed");
        return false;
    }

    /**
     * Delivers a String
     *
     * @return String with name, wgID
     */
    @Override public String toString() {
        log.debug("toString method started");
        StringBuffer result = new StringBuffer();
        Iterator<User> iter = users.iterator();
        while (iter.hasNext()) {
            result.append(iter.next().getName());
            result.append(": ");
            result.append(iter.next().getWgID());
            if (iter.hasNext()) {
                result.append(", ");
            }
        }
        return result.toString();
    }
}
