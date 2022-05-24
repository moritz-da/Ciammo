package de.hdm_stuttgart.mi.GuiController;

import de.hdm_stuttgart.mi.CiammoGUI;
import de.hdm_stuttgart.mi.Users.UserManager;
import de.hdm_stuttgart.mi.Wgs.WgManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginController {

    private static final Logger log = LogManager.getLogger(LoginController.class);
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel1;

    /**
     * The initialize method will directly start after setup and the scene is loaded.
     */
    @FXML
    public void initialize() {
        log.debug("initialize method started");
        errorLabel1.setOpacity(0);
    }

    /**
     * This method logs user into Ciammo.
     *
     * @param event Button "Einloggen" is clicked
     */
    public void loginUser(ActionEvent event) {
        log.debug("loginUser method started");
        final String username = usernameField.getText();
        final String password = passwordField.getText();

        if (UserManager.getInstance().login(username, password)) {
            WgManager.getWgInstance().updateCurrentWg();
            log.info("loginUser method, User successfully logged in");
            CiammoGUI.showScene("/fxml/userOverview.fxml");
        }
        else {
            errorLabel1.setOpacity(1);
            log.error("loginUser method, login failed");
        }
    }

    /**
     * here a user can register into Ciammo.
     *
     * @param event Button "registrieren" is clicked
     */
    @FXML
    public void registerUser(ActionEvent event) {
        CiammoGUI.showScene("/fxml/createNewUser.fxml");
    }
}
