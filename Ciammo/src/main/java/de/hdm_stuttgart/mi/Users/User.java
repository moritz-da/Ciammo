package de.hdm_stuttgart.mi.Users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 * This classs defines the user data (password, username, name, email, birthdate and wgId)
 */
public class User implements Serializable {

    private String password = null;
    private String username =  null;
    private String name = null;
    private String email = null;
    private String birthdate = null;
    private int wgID = 0;

    /**
     * Constructor for class User
     *
     * @param username the chosen username for this user
     * @param password password for this user
     * @param name  name for this user
     * @param email email from a user for this user
     * @param birthdate birthdate from a user for this user
     */
    @JsonCreator
    public User(
            @JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("name") String name,
            @JsonProperty("email") String email,
            @JsonProperty("birthdate") String birthdate) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
    }

    /**
     * changes username of the user
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * changes password of the user
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * changes email of the user
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * changes wgId of the user
     *
     * @param wgID
     */
    public void setWgID(int wgID) {
        this.wgID = wgID;
    }

    /**
     * delivers the password
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * delivers the username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * delivers the name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * delivers the email
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * delivers the birthdate
     *
     * @return birthdate
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     * delivers the wgId
     *
     * @return wgID
     */
    public int getWgID() {
        return wgID;
    }
}
