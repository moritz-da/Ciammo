package de.hdm_stuttgart.mi.GuiController;

import de.hdm_stuttgart.mi.CiammoGUI;
import de.hdm_stuttgart.mi.Helper.Configuration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SettingsController {

    private static final Logger log = LogManager.getLogger(SettingsController.class);
    @FXML private Button setNightModeButton;
    @FXML private Button WgSettingsButton;

    /**
     * The initialize method will directly start after setup and the scene is loaded.
     */
    @FXML public void initialize () {
        log.debug("initialize method started");
        if (Boolean.parseBoolean(Configuration.readProp("isLightMode"))) {
            setNightModeButton.setText("Nachtmodus");
        } else {
            setNightModeButton.setText("Tagmodus");
        }

        // Setup WgSettingsButton
        if (CiammoGUI.getCurrentUser().getWgID() == 0) {
            WgSettingsButton.setDisable(true);
        }
        else {
            WgSettingsButton.setDisable(false);
        }
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
     * This method shows userSettings scene.
     *
     * @param event Button "Benutzer" is clicked
     */
    @FXML
    public void userSettings (ActionEvent event) {
        CiammoGUI.showScene("/fxml/userSettings.fxml");
    }

    /**
     * This method changes appearance to darkmode or lightmode and colors will change.
     *
     * @param event Button "Nachtmodus" or "Tagmodus" is clicked
     */
    @FXML
    public void setNightMode (ActionEvent event) {
        log.debug("setNightMode method started");
        boolean isLightMode = Boolean.parseBoolean(Configuration.readProp("isLightMode"));
        Configuration.writeProp("isLightMode", Boolean.toString(!isLightMode), "Setting for appearance of the GUI");
        CiammoGUI.showScene("/fxml/settings.fxml");
    }

    /**
     * This method shows wgSettings scene.
     *
     * @param event Button "WG" is clicked
     */
    @FXML
    public void wgSettings (ActionEvent event) {
        CiammoGUI.showScene("/fxml/wgSettings.fxml");
    }
}
