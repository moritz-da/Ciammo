package de.hdm_stuttgart.mi.GuiController;

import de.hdm_stuttgart.mi.CiammoGUI;
import de.hdm_stuttgart.mi.Helper.Utilities;
import de.hdm_stuttgart.mi.Wgs.WgManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WgSettingsController {

    private static final Logger log = LogManager.getLogger(ShoppingListController.class);
    @FXML private TextField newWgNameField;
    @FXML private PasswordField newWgPasswordField;
    @FXML private PasswordField repeatNewWgPasswordField;
    @FXML private Label errorMessageLabel1;
    @FXML private Label errorMessageLabel2;
    @FXML private Label errorMessageLabel3;

    /**
     * The initialize method will directly start after setup and the scene is loaded.
     */
    @FXML
    public void initialize() {
        log.debug("initialize method started");
        errorMessageLabel1.setOpacity(0);
        errorMessageLabel2.setOpacity(0);
        errorMessageLabel3.setOpacity(0);
    }

    /**
     * This method saves new chosen wg name.
     *
     * @param event Button "Speichern" is clicked
     */
    @FXML
    public void saveNewWgName(ActionEvent event) {
        log.debug("saveNewWgName method started");
        errorMessageLabel1.setOpacity(0);
        boolean errorMessage1 = false;
        final String newWgName = newWgNameField.getText();

        if (!WgManager.getWgInstance().isWgNameAvailable(newWgName) || newWgName.isEmpty() || newWgName.equals(" ")){
            errorMessageLabel1.setOpacity(1);
            errorMessageLabel1.setText("WG-Name nicht verfügbar");
            errorMessage1 = true;
            log.error("saveNewWgName method, username already exists");
        }
        if(!errorMessage1){
            errorMessageLabel1.setOpacity(1);
            errorMessageLabel1.setText("WG-Name wurde geändert");
            CiammoGUI.getCurrentWg().setWgNameName(newWgNameField.getText());
          }
    }

    /**
     * This method saves new chosen wg password.
     *
     * @param event Button "Speichern" is clicked
     */
    @FXML public void saveNewWgPassword (ActionEvent event) {
        log.debug("saveNewWgPassword method started");
        errorMessageLabel2.setOpacity(0);
        errorMessageLabel3.setOpacity(0);

        boolean errorMessage2 = false;
        boolean errorMessage3 = false;

        final String password = newWgPasswordField.getText().trim();
        final String repeatPassword = repeatNewWgPasswordField.getText().trim();

        if (password.isEmpty() || !Utilities.provePasswordConditions(password)) {
            errorMessageLabel2.setOpacity(1);
            errorMessage2 = true;
            log.error("saveNewWgPassword method, password does not match the requirements");
        }
        if (!password.equals(repeatPassword)) {
            errorMessageLabel3.setOpacity(1);
            errorMessage3 = true;
            log.error("saveNewWgPassword method, passwords do not match");
        }

        if(!errorMessage2 && !errorMessage3){
            errorMessageLabel3.setOpacity(1);
            errorMessageLabel3.setText("Passwort wurde geändert");
            CiammoGUI.getCurrentWg().setWgPassword(newWgPasswordField.getText());
        }
    }

    /**
     * This method deletes wg.
     *
     * @param event Button "WG löschen" is clicked
     */
    @FXML
    public void deleteWg (ActionEvent event) {
        log.debug("deleteWg method started");
        WgManager.getWgInstance().deleteWg(CiammoGUI.getCurrentWg());
        CiammoGUI.getCurrentUser().setWgID(0);
        WgManager.getWgInstance().updateCurrentWg();
        CiammoGUI.showScene("/fxml/userOverview.fxml");
        log.info("delteWg, Wg was deleted");
    }

    /**
     * This method shows scene settings.fxml.
     *
     * @param event Button "Zurück" is clicked
     */
    @FXML
    public void backToSettings (ActionEvent event) {
        CiammoGUI.showScene("/fxml/settings.fxml");
    }
}
