package de.hdm_stuttgart.mi.GuiController;

import de.hdm_stuttgart.mi.CiammoGUI;
import de.hdm_stuttgart.mi.Wgs.WgManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JoinWgController {
    private static final Logger log = LogManager.getLogger(JoinWgController.class);
    @FXML private TextField wgId;
    @FXML private PasswordField wgPassword;
    @FXML private Label errorMessageLabel1;

    /**
     * The initialize method will directly start after setup and the scene is loaded.
     */
    @FXML
    public void initialize() {
        log.debug("initialize method started");
        errorMessageLabel1.setOpacity(0);
    }

    /**
     * This method shows userOverview scene.
     *
     * @param event Button "Zurück" is clicked
     */
    @FXML
    public void backToUserOverview (ActionEvent event) {
        CiammoGUI.showScene("/fxml/userOverview.fxml");
    }

    /**
     * This method adds a current user to a wg name, if wg id and wg password was filled in correct.
     *
     * @param event Button "Beitreten" is clicked
     */
    @FXML
    public void addUserToWg(ActionEvent event) {
        log.debug("addUserToWg method started");
        errorMessageLabel1.setOpacity(0);
        if (CiammoGUI.getCurrentUser().getWgID() == 0) {
            int wgIDInput = Integer.parseInt(wgId.getText());
            String wgPasswordInput = wgPassword.getText();
            if (WgManager.getWgInstance().login(wgIDInput, wgPasswordInput, CiammoGUI.getCurrentUser())) {
                WgManager.getWgInstance().updateCurrentWg();
                CiammoGUI.showScene("/fxml/wgOverview.fxml");
            }
            else {
                errorMessageLabel1.setOpacity(1);
                log.error("addUserToWg method, Login failed");
            }
        }
    }
}