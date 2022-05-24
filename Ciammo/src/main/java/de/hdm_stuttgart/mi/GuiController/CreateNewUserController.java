package de.hdm_stuttgart.mi.GuiController;

import de.hdm_stuttgart.mi.CiammoGUI;
import de.hdm_stuttgart.mi.Helper.Utilities;
import de.hdm_stuttgart.mi.Users.User;
import de.hdm_stuttgart.mi.Users.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateNewUserController {

    private static final Logger log = LogManager.getLogger(CreateNewUserController.class);
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField repeatPasswordField;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private DatePicker birthdateField;
    @FXML private Label errorMessageLabel1;
    @FXML private Label errorMessageLabel2;
    @FXML private Label errorMessageLabel3;
    @FXML private Label errorMessageLabel4;

    /**
     * The initialize method will directly start after setup and the scene is loaded.
     */
    @FXML
    public void initialize() {
        log.debug("initialize method started");
        birthdateField.setValue(LocalDate.now().minusYears(18));
        errorMessageLabel1.setOpacity(0);
        errorMessageLabel2.setOpacity(0);
        errorMessageLabel3.setOpacity(0);
        errorMessageLabel4.setOpacity(0);
    }

    /**
     * This method creates a new user account, if username does not exist yet and password is safe enough.
     *
     * @param event Button "Account erstellen" is clicked
     */
    @FXML
    public void createUser(ActionEvent event) {
        log.debug("createUser method started");
        errorMessageLabel1.setOpacity(0);
        errorMessageLabel2.setOpacity(0);
        errorMessageLabel3.setOpacity(0);
        errorMessageLabel4.setOpacity(0);
        final String username = usernameField.getText().trim();
        final String password = passwordField.getText().trim();
        final String repeatPassword = repeatPasswordField.getText().trim();
        final String name = nameField.getText().trim();
        final String email = emailField.getText().trim();
        final String birthdate = birthdateField.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        boolean errorMessage1 = false;
        boolean errorMessage2 = false;
        boolean errorMessage3 = false;
        boolean errorMessage4 = false;

        if (!UserManager.getInstance().isUsernameAvailable(username)) {
            errorMessageLabel1.setOpacity(1);
            errorMessage1 = true;
            log.error("createUser method, username already exists");
        }
        if (!password.isEmpty() && !Utilities.provePasswordConditions(password)) {
            errorMessageLabel2.setOpacity(1);
            errorMessage2 = true;
            log.error("createUser method, password does not match the requirements");
        }
        if (!password.equals(repeatPassword)) {
            errorMessageLabel3.setOpacity(1);
            errorMessage3 = true;
            log.error("createUser method, passwords do not match");
        }
        if (username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty() || name.isEmpty() || email.isEmpty() || birthdate.isEmpty()) {
            errorMessageLabel4.setOpacity(1);
            errorMessage4 = true;
            log.error("createUser method, empty field");
        }

        if (!errorMessage1 && !errorMessage2 && !errorMessage3 && !errorMessage4) {
            User user = new User(username, password, name, email, birthdate);
            UserManager.getInstance().addUser(user);
            CiammoGUI.setCurrentUser(user);
            log.info("createUser method, user was successfully created and added to the userManager");
            CiammoGUI.showScene("/fxml/userOverview.fxml");
        }
    }
    /**
     * This method shows login scene.
     *
     * @param event Button "zurück" is clicked
     */
    @FXML
    public void backToLogin (ActionEvent event) {
        CiammoGUI.showScene("/fxml/login.fxml");
    }
}
