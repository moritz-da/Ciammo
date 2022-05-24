package de.hdm_stuttgart.mi.GuiController;

import de.hdm_stuttgart.mi.CiammoGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WgOverviewController {

    private static final Logger log = LogManager.getLogger(WgOverviewController.class);
    @FXML private Label wgNameLabel;

    /**
     * The initialize method will directly start after setup and the scene is loaded.
     */
    @FXML
    public void initialize(){
        log.debug("initialize method started");
        wgNameLabel.setText("WG " + CiammoGUI.getCurrentWg().getWgName());
    }

    /**
     * This method shows scene expenseList.fxml.
     *
     * @param event Button "Ausgaben" is clicked
     */
    @FXML
    public void showExpenseList(ActionEvent event) {
        CiammoGUI.showScene("/fxml/expenseList.fxml");
    }

    /**
     * This method shows the scene cleaningList.fxml.
     *
     * @param event Button "Putzplan" is clicked
     */
    @FXML
    public void showCleaningList(ActionEvent event) {
        CiammoGUI.showScene("/fxml/cleaningList.fxml");
    }

    /**
     * This method shows the scene shoppingList.fxml.
     *
     * @param event Button "Einkaufsliste" is clicked
     */
    @FXML
    public void showShoppingList(ActionEvent event) {
        CiammoGUI.showScene("/fxml/shoppingList.fxml");
    }

    /**
     * This method shows the scene showWgUsers.fxml.
     *
     * @param event Button "Mitbewohner" is clicked
     */
    @FXML
    public void showUsers(ActionEvent event) {
        CiammoGUI.showScene("/fxml/showWgUsers.fxml");
    }

    /**
     * This method shows the scene userOverview.fxml.
     *
     * @param event Button "Zurück" is clicked
     */
    @FXML
    public void goBack(ActionEvent event) {
        CiammoGUI.showScene("/fxml/userOverview.fxml");
    }
}
