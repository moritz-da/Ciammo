package de.hdm_stuttgart.mi.GuiController;

import de.hdm_stuttgart.mi.CiammoGUI;
import de.hdm_stuttgart.mi.Helper.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShoppingListController {

    private static final Logger log = LogManager.getLogger(ShoppingListController.class);
    @FXML private ListView shoppingListView;
    @FXML private Label errorMessageLabel;
    @FXML private TextField addItemTextField;
    @FXML private ObservableList<String> items;

    /**
     * The initialize method will directly start after setup and the scene is loaded.
     */
    @FXML
    public void initialize() {
        log.debug("initialize method started");
        // Initialize ListView
        items = FXCollections.observableArrayList(CiammoGUI.getCurrentWg().getShoppingList().getNotes());
        shoppingListView.setItems(items);

        // Initialize Error Message
        errorMessageLabel.setOpacity(0);
    }

    /**
     * This method goes back to scene wgOverview.fxml.
     *
     * @param event the button "Zurück" is being clicked
     */
    @FXML
    public void goBack(ActionEvent event) {
        CiammoGUI.showScene("/fxml/wgOverview.fxml");
    }

    /**
     * This method adds item to shopping list.
     *
     * @param event Button "Hinzufügen" is being clicked
     */
    @FXML
    public void addItem(ActionEvent event) {
        log.debug("addItem method started");
        String textField = addItemTextField.getText();
        if (textField != null && !textField.isEmpty() && !textField.isBlank()) {
            String word = Utilities.firstLetterUpperCase(textField);
            CiammoGUI.getCurrentWg().getShoppingList().add(word);
            log.debug("shopping List, item added");
            initialize();
        }
        else {
            errorMessageLabel.setOpacity(1);
            log.error("addItem method, empty field");
        }
    }

    /**
     * This method removes item from shopping list.
     *
     * @param event Button "Ausgewähltes Item entfernen" is being clicked
     */
    @FXML
    public void removeItem(ActionEvent event) {
        log.debug("removeItem method started");
        String item;
        Object object = shoppingListView.getSelectionModel().getSelectedItem();
        if (object != null) {
            item = object.toString();
            CiammoGUI.getCurrentWg().getShoppingList().remove(item);
            log.debug("shopping List, item removed");
            initialize();
        }
    }

    /**
     * This method removes all items from shopping list.
     *
     * @param event button "Alle Items entfernen" is being clicked
     */
    @FXML
    public void removeAll(ActionEvent event) {
        log.debug("removeAll method started");
        CiammoGUI.getCurrentWg().getShoppingList().removeAll();
        initialize();
    }

    /**
     * This method sorts all items alphabetically.
     *
     * @param event button "Alphabetisch sortieren" is being clicked
     */
    @FXML
    public void sortAlpha(ActionEvent event) {
        log.debug("sortAlpha method started");
        CiammoGUI.getCurrentWg().getShoppingList().sort();
        initialize();
    }
}
