package de.hdm_stuttgart.mi.Wgs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.hdm_stuttgart.mi.CiammoGUI;
import de.hdm_stuttgart.mi.Users.User;
import de.hdm_stuttgart.mi.Users.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class manages all wgs.
 */
public class WgManager implements Serializable {

    private static final Logger log = LogManager.getLogger(WgManager.class);
    private ArrayList<Wg> wgs;
    private static WgManager instance = null;

    /**
     * Constructor for the class WgManager
     *
     * @param wgs an arraylist which contains all users
     */
    @JsonCreator
    private WgManager(
            @JsonProperty("wgs") ArrayList<Wg> wgs) {
        this.wgs = wgs;
    }

    /**
     * This method delivers the instance of WgManager.
     *
     * @return instance of WgManager
     */
    public static WgManager getWgInstance() {
        log.debug("getWgInstance method started");
        if (instance == null)
            instance = new WgManager(new ArrayList<>());
        return instance;
    }

    /**
     * This method saves permanent Data for the program.
     *
     * @return jsonInString if saved, "" otherwise
     */
    public static synchronized String saveWgManager() {
        log.debug("saveWgManager method started");
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            String resourcePath = "";
            try {
                final String os = System.getProperty("os.name"); // os = operating system

                if (os.contains("Windows")) {
                    resourcePath = ".\\src\\main\\resources\\data\\saveWgData.json";
                } else {
                    resourcePath = "./src/main/resources/data/saveWgData.json";
                }
            } catch (final Exception e) {
                log.error("Error while trying to load resourcePath in loadWgManager method: " + e.getMessage());
            }

            // Java objects to JSON file
            mapper.writeValue(new File(resourcePath), getWgInstance());
            log.info("Permanent Data (WgManager instance) is saved - saveWgManager method");

            // Java objects to JSON string - pretty-print for the console if needed
            String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getWgInstance());
            return jsonInString;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Saving data failed - saveWgManager method");
            return "";
        }
    }

    /**
     * This method loads permanent data for the program.
     *
     * @return WgManager object if data was saved already, null if it cannot load the data
     */
    public static synchronized WgManager loadWgManager() {
        log.debug("loadWgManager method started");
        ObjectMapper mapper = new ObjectMapper();

        try {
            String resourcePath = "";
            try {
                final String os = System.getProperty("os.name"); // os = operating system

                if (os.contains("Windows")) {
                    resourcePath = ".\\src\\main\\resources\\data\\saveWgData.json";
                } else {
                    resourcePath = "./src/main/resources/data/saveWgData.json";
                }
            } catch (final Exception e) {
                log.fatal("Error while trying to load resourcePath in loadWgManager method");
            }

            // JSON file to Java object
            instance = mapper.readValue(new File(resourcePath), WgManager.class);

            log.info("Permanent data is loaded - loadWgManager method");
            return instance;
        } catch (IOException e) {
            e.printStackTrace();
            log.fatal("Loading permanent data failed - loadWgManager method");
            return null;
        }
    }

    /**
     * This method checks if wgname is still available.
     *
     * @param wgname chosen wgname
     * @return true, if available, false otherwise
     */
    public boolean isWgNameAvailable(String wgname) {
        log.debug("isWgNameAvailable method started");
        boolean isAvailable = true;
        for (Wg wg : wgs) {
            if (wgname.equals(wg.getWgName())) {
                isAvailable = false;
                break;
            }
        }
        return isAvailable;
    }

    /**
     * This method sets WgManager's instance null.
     */
    public void setWgInstanceNull() {
        log.debug("setWgInstanceNull method started");
        instance = null;
    }

    /**
     * This method delivers Arraylist with all wgs.
     *
     * @return list with all wgs
     */
    public ArrayList<Wg> getWgs() {
        log.debug("getWgs method started");
        ArrayList<Wg> wgsCopy = new ArrayList<>(wgs);
        return wgsCopy;
    }

    /**
     * This method adds wg to wgs Arraylist.
     *
     * @param wg wg that is supposed to be added
     * @return true, if wg was added to WgManager, false otherwise
     */
    public boolean addWg(Wg wg) {
        log.debug("addWg method started");
        if (wg == null) {
            log.error("addWg method, an empty wg was tried to add in the wgs ArrayList");
            return false;
        }
        else {
            wgs.add(wg);
            log.info("addWg method, a wg was added to WgManager");
            return true;
        }
    }

    /**
     * This method deletes wg from wgs arraylist.
     *
     * @param wg wg that is supposed to be deleted
     */
    public void deleteWg(Wg wg) {
        log.debug("deleteWg method started");
        ArrayList<User> users = new ArrayList<>(wg.getUsers());
        if (!users.isEmpty()) {
            for (User user : users) {
                UserManager.getInstance().resetWgID(user);
            }
        }
        wgs.remove(wg);
        log.info("deleteWg method, a wg has been deleted");
    }

    /**
     * This method removes user from wg.
     *
     * @param wg wg that user is in
     * @param user user that is supposed to be removed from wg
     */
    public void removeUserFromWg(Wg wg, User user) {
        log.debug("removeUserFromWg method started");
        for (Wg value : wgs) {
            if (value.getWgName().equals(wg.getWgName())) {
                value.removeUser(user);
                log.info("removeUserFromWg method, a user has benn removed from a wg");
            }
        }
    }

    /**
     * This method updates current wgID.
     */
    public void updateCurrentWg() {
        log.debug("updateCurrentWg method started");
        int currentWgID = CiammoGUI.getCurrentUser().getWgID();
        if (currentWgID != 0){
            for (Wg wg : wgs) {
                if (currentWgID == wg.getWgID()) {
                    CiammoGUI.setCurrentWg(wg);
                }
            }
        } else {
            CiammoGUI.setCurrentWg(null);
        }
    }

    /**
     * This method user logs into wg.
     *
     * @param wgId wgId of wg
     * @param password password of wg
     * @param user user that wants to log into wg
     * @return true, if user could log into wg, false otherwise
     */
    public boolean login(int wgId, String password, User user) {
        log.debug("login method started");
        for (Wg wg : wgs) {
            if (wgId == wg.getWgID()) {
                if (password.equals(wg.getWgPassword())) {
                    wg.addUser(user);
                    user.setWgID(wg.getWgID());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method delivers a String.
     *
     * @return String with wg name
     */
    @Override public String toString() {
        log.debug("toString method started");
        StringBuffer result = new StringBuffer();
        Iterator<Wg> iter = wgs.iterator();
        while (iter.hasNext()) {
            result.append(iter.next().getWgName());
            if (iter.hasNext()) {
                result.append(", ");
            }
        }
        return result.toString();
    }
}
