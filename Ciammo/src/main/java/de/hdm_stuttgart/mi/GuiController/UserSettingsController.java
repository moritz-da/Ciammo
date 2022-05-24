package de.hdm_stuttgart.mi.GuiController;

import de.hdm_stuttgart.mi.CiammoGUI;
import de.hdm_stuttgart.mi.Helper.Utilities;
import de.hdm_stuttgart.mi.Users.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserSettingsController {
    private static final Logger log = LogManager.getLogger(ShoppingListController.class);

    @FXML private TextField newMailField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField repeatNewPasswordField;
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
        errorMessageLabel1.setOpacity(0);
        errorMessageLabel2.setOpacity(0);
        errorMessageLabel3.setOpacity(0);
        errorMessageLabel4.setOpacity(0);
    }

    /**
     * This method saves the new chosen email.
     *
     * @param event Button "Speichern" is clicked
     */
    @FXML public void saveNewMail (ActionEvent event){
        errorMessageLabel4.setOpacity(0);
        final String newMail = newMailField.getText();

        log.debug("saveNewMail method started");
        if(newMail.isEmpty() || newMail.equals(" ")){
            errorMessageLabel4.setOpacity(1);
        }
        CiammoGUI.getCurrentUser().setEmail(newMail);
    }

    /**
     * This method saves the new chosen password.
     *
     * @param event Button "Speichern" is clicked
     */
    @FXML public void saveNewPassword (ActionEvent event){
        log.debug("saveNewPassword method started");
        errorMessageLabel2.setOpacity(0);
        errorMessageLabel3.setOpacity(0);

        boolean errorMessage2 = false;
        boolean errorMessage3 = false;

        final String password = newPasswordField.getText().trim();
        final String repeatPassword = repeatNewPasswordField.getText().trim();

        if (password.isEmpty() || !Utilities.provePasswordConditions(password)) {
            errorMessageLabel2.setOpacity(1);
            errorMessage2 = true;
            log.error("saveNewPassword method, password does not match the requirements");
        }
        if (!password.equals(repeatPassword)) {
            errorMessageLabel3.setOpacity(1);
            errorMessage3 = true;
            log.error("saveNewPassword method, passwords do not match");
        }

        if(!errorMessage2 && !errorMessage3){
            errorMessageLabel3.setOpacity(1);
            errorMessageLabel3.setText("Passwort wurde geändert");
            CiammoGUI.getCurrentUser().setPassword(newPasswordField.getText());
        }

    }

    /**
     * This method deletes user from Ciammo.
     *
     * @param event Button "Benutzer löschen" is clicked
     */
    @FXML public void deleteUser (ActionEvent event){
        errorMessageLabel1.setOpacity(0);
        log.debug("deleteUser method started");
        if(CiammoGUI.getCurrentUser().getWgID() == 0){
            UserManager.getInstance().deleteUser(CiammoGUI.getCurrentUser());
            CiammoGUI.setCurrentUser(null); // Logout
            CiammoGUI.showScene("/fxml/login.fxml");
            log.info("deleteUser method, User was deleted");
        }
        else {
            errorMessageLabel1.setOpacity(1);
            log.error("deleteUser method, Wg id is not 0");
        }
    }

    /**
     * This method shows the scene settings.fxml.
     *
     * @param event Button "Zurück" is clicked
     */
    @FXML
    public void backToSettings (ActionEvent event) {
        CiammoGUI.showScene("/fxml/settings.fxml");
    }
}
