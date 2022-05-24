package de.hdm_stuttgart.mi.GuiController;

import de.hdm_stuttgart.mi.CiammoGUI;
import de.hdm_stuttgart.mi.Users.User;
import de.hdm_stuttgart.mi.Wgs.Wg;
import de.hdm_stuttgart.mi.Wgs.WgManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowWgUsersController {

    private static final Logger log = LogManager.getLogger(ShowWgUsersController.class);
    @FXML private Label errorMessageLabel;
    @FXML private TableView tableTableView;
    @FXML private TableColumn nameColumn;
    @FXML private TableColumn emailColumn;
    @FXML private TableColumn birthdateColumn;
    @FXML private ObservableList<User> data;

    /**
     * The initialize method will directly start after setup and the scene is loaded.
     */
    @FXML
    public void initialize() {
        log.debug("initialize method started");
        // Initialize TableView
        data = FXCollections.observableArrayList(CiammoGUI.getCurrentWg().getUsers());
        nameColumn.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<User,String>("email"));
        birthdateColumn.setCellValueFactory(new PropertyValueFactory<User,String>("birthdate"));
        tableTableView.setItems(data);

        // Initialize Error Message
        errorMessageLabel.setOpacity(0);
    }

    /**
     * This method goes back to scene wgOverview.fxml.
     *
     * @param event Button "Zurück" is clicked
     */
    @FXML
    public void goBack(ActionEvent event) {
        CiammoGUI.showScene("/fxml/wgOverview.fxml");
    }

    /**
     * This method shows scene inviteUser.fxml.
     *
     * @param event button "Mitbewohner einladen" is clicked
     */
    @FXML
    public void inviteUser(ActionEvent event) {
        CiammoGUI.showScene("/fxml/inviteUser.fxml");
    }

    /**
     * This method removes user from wg.
     *
     * @param event Button "Ausgewählten Mitbewohner entfernen" is clicked
     */
    @FXML
    public void removeUser(ActionEvent event) {
        log.debug("removeUser method started");
        User currentUser = CiammoGUI.getCurrentUser();
        Wg currentWg = CiammoGUI.getCurrentWg();
        errorMessageLabel.setOpacity(0);
        User object = (User) tableTableView.getSelectionModel().getSelectedItem();
        boolean errorMessageNull = false;
        boolean errorMessageLessThanOneUser = false;

        if (object == null) {
            errorMessageNull = true;
            errorMessageLabel.setOpacity(1);
            errorMessageLabel.setText("Es ist kein Mitbewohner ausgewählt!");
            log.error("removeUser method, no user selected");
        }
        else {
            if (currentWg.getUsers().size() <= 1) {
                errorMessageLessThanOneUser = true;
                errorMessageLabel.setOpacity(1);
                errorMessageLabel.setText("Du bist das einzige Mitglied in der WG " + currentWg.getWgName()
                        + ". Gehe in die Einstellungen und lösche die Wg dort. Dann wirst Du auch automatisch aus dieser entfernt.");
                log.error("removeUser method, current user cannot be removed from wg, wg would otherwise be empty");
            }
        }

        if (!errorMessageLessThanOneUser && !errorMessageNull) {
            WgManager.getWgInstance().removeUserFromWg(currentWg, object);
            WgManager.getWgInstance().updateCurrentWg();
            if (object.getUsername().equals(currentUser.getUsername())) {
                CiammoGUI.showScene("/fxml/userOverview.fxml");
            }
            else {
                initialize();
            }
        }
    }
}
