package de.hdm_stuttgart.mi.GuiController;

import de.hdm_stuttgart.mi.CiammoGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InviteUserController {

    private static final Logger log = LogManager.getLogger(InviteUserController.class);
    @FXML private Label wgIDLabel;
    @FXML private Label wgPasswordLabel;

    /**
     * The initialize method will directly start after setup and the scene is loaded.
     */
    @FXML
    public void initialize() {
        log.debug("initialize method started");
        wgIDLabel.setText(String.valueOf(CiammoGUI.getCurrentWg().getWgID()));
        wgPasswordLabel.setText(CiammoGUI.getCurrentWg().getWgPassword());
    }

    /**
     * This method shows showWgUser scene.
     *
     * @param event Button "Zurück" is clicked
     */
    @FXML
    public void goBack(ActionEvent event) {
        CiammoGUI.showScene("/fxml/showWgUsers.fxml");
    }
}
