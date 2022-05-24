package de.hdm_stuttgart.mi.GuiController;

import de.hdm_stuttgart.mi.CiammoGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserOverviewController {

    private static final Logger log = LogManager.getLogger(UserOverviewController.class);
    @FXML private Label welcomeLabel;
    @FXML private Button showWgButton;
    @FXML private Button createWgButton;
    @FXML private Button joinWgButton;

    /**
     * The initialize method will directly start after setup and the scene is loaded.
     */
    public void initialize(){
        log.debug("initialize method started");
        welcomeLabel.setText("Willkommen " + CiammoGUI.getCurrentUser().getName() + "!");
        if (CiammoGUI.getCurrentUser().getWgID() == 0) {
            showWgButton.setDisable(true);
        }
        else {
            createWgButton.setDisable(true);
            joinWgButton.setDisable(true);
        }
    }

    /**
     * This method logs user out of Ciammo.
     *
     * @param event Button "Logout" is clicked
     */
    @FXML
    public void logoutUser(ActionEvent event) {
        log.debug("logoutUser method started");
        CiammoGUI.setCurrentUser(null);
        CiammoGUI.showScene("/fxml/login.fxml");
    }

    /**
     * This method shows scene joinWg.fxml.
     *
     * @param event Button "WG beitreten" is clicked
     */
    @FXML
    public void joinWg(ActionEvent event) {
        CiammoGUI.showScene("/fxml/joinWg.fxml");
    }

    /**
     * This method shows scene wgOverview.fxml.
     *
     * @param event Button "WG anzeigen" is clicked
     */
    @FXML
    public void showWg(ActionEvent event) {
        CiammoGUI.showScene("/fxml/wgOverview.fxml");
    }

    /**
     * This method shows scene createWg.fxml.
     *
     * @param event Button "WG erstellen" is clicked
     */
    @FXML
    public void createWg(ActionEvent event) {
        CiammoGUI.showScene("/fxml/createWg.fxml");
    }

    /**
     * This method shows scene settings.fxml.
     *
     * @param event Button "Einstellungen" is clicked
     */
    @FXML
    public void openSettings (ActionEvent event) {
        CiammoGUI.showScene("/fxml/settings.fxml");
    }
}

