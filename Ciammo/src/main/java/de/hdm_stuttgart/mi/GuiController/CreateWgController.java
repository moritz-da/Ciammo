package de.hdm_stuttgart.mi.GuiController;

import de.hdm_stuttgart.mi.CiammoGUI;
import de.hdm_stuttgart.mi.Helper.Utilities;
import de.hdm_stuttgart.mi.Wgs.Wg;
import de.hdm_stuttgart.mi.Wgs.WgManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateWgController {

    private static final Logger log = LogManager.getLogger(CreateWgController.class);
    @FXML private TextField setWgName;
    @FXML private PasswordField setWgPassword;
    @FXML private PasswordField repeatWgPassword;
    @FXML private Label createWgLabel;
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
     * This method creates a new wg, if wg name does not exist yet and password is safe enough.
     *
     * @param event Button "WG erstellen" is clicked
     */
    @FXML
    public void createWg(ActionEvent event) {
        log.debug("createWg method started");
        errorMessageLabel1.setOpacity(0);
        errorMessageLabel2.setOpacity(0);
        errorMessageLabel3.setOpacity(0);
        boolean errorMessage1 = false;
        boolean errorMessage2 = false;
        boolean errorMessage3 = false;

        final String wgName = setWgName.getText();
        final String wgPassword = setWgPassword.getText();
        final String repeatPassword = repeatWgPassword.getText();

        if (!WgManager.getWgInstance().isWgNameAvailable(wgName) && !wgName.isEmpty()){
            errorMessageLabel1.setOpacity(1);
            errorMessage1 = true;
            log.error("createWg method, username already exists");
        }

        if (wgPassword.isEmpty() || !Utilities.provePasswordConditions(wgPassword)) {
            errorMessageLabel2.setOpacity(1);
            errorMessage2 = true;
            log.error("createWg method, password does not match the requirements");
        }

        if (!wgPassword.equals(repeatPassword)) {
            errorMessageLabel3.setOpacity(1);
            errorMessage3 = true;
            log.error("createWg method, passwords do not match");
        }

        if (!errorMessage1 && !errorMessage2 && !errorMessage3){
            Wg wg = new Wg (wgName, wgPassword);
            WgManager.getWgInstance().addWg(wg);
            wg.addUser(CiammoGUI.getCurrentUser());
            WgManager.getWgInstance().updateCurrentWg();
            log.info("createWg method, Wg was successfully created and added to the userManager");
            CiammoGUI.showScene("/fxml/userOverview.fxml");
        }
    }

    /**
     * This method shows userOverview scene.
     *
     * @param event Button "zurück" is clicked
     */
    @FXML
    public void backToUserOverview (ActionEvent event) {
        CiammoGUI.showScene("/fxml/userOverview.fxml");
    }
}
