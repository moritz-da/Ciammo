package de.hdm_stuttgart.mi.GuiController;

import de.hdm_stuttgart.mi.CiammoGUI;
import de.hdm_stuttgart.mi.Users.User;
import de.hdm_stuttgart.mi.Users.UserManager;
import de.hdm_stuttgart.mi.Wgs.ListObject;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;

public class ExpenseListController {

    private static final Logger log = LogManager.getLogger(ExpenseListController.class);
    @FXML private Label errorMessage;
    @FXML private TextField valueTextField;
    @FXML private MenuButton chooseUserMenuButton;
    @FXML private MenuItem item;
    @FXML private TableView expenseListTableView;
    @FXML private TableColumn<ListObject, String> noteColumn;
    @FXML private TableColumn<ListObject, String> valueColumn;
    @FXML private TableColumn<ListObject, User> userColumn;
    @FXML private ObservableList<Object> data;
    @FXML private TextField noteTextField;
    @FXML private Spinner valueSpinner;
    private String selectedUsername = null;

    @FXML
    EventHandler<ActionEvent> chooseUserMenuButtonEvent = event -> {
        selectedUsername = ((MenuItem)event.getSource()).getText();
        chooseUserMenuButton.setText(selectedUsername);
    };

    /**
     * The initialize method will directly start after setup and the scene is loaded.
     */
    @FXML
    public void initialize() {
        log.debug("initialize method started");
        // Initialize TableView
        updateTableView();

        // Initialize Error Message Label
        errorMessage.setOpacity(0);

        // Initialize calendar week Text Field
        valueTextField.setDisable(true);
        valueTextField.setOpacity(1);

        // Initialize Menu Button
        ArrayList<User> copyUsers = new ArrayList<>(CiammoGUI.getCurrentWg().getUsers());
        for (User copyUser : copyUsers) {
            item = new MenuItem(copyUser.getUsername());
            chooseUserMenuButton.getItems().add(item);
            item.setOnAction(chooseUserMenuButtonEvent);
        }
    }

    /**
     * This method updates the expense list table.
     */
    public void updateTableView() {
        log.debug("updateTableView method started");
        data = FXCollections.observableArrayList(CiammoGUI.getCurrentWg().getExpenseList().getObjects());
        noteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNote()));
        valueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getValue())));
        userColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getUser().getUsername()));
        expenseListTableView.setItems(data);
    }

    /**
     * This method shows wgOverview scene.
     *
     * @param event Button "Zurück" is clicked
     */
    @FXML
    public void goBack(ActionEvent event) {
        CiammoGUI.showScene("/fxml/wgOverview.fxml");
    }

    /**
     * This method sets a new item to expense list.
     *
     * @param event Button "Hinzufügen" is clicked
     */
    @FXML
    public void addRow(ActionEvent event) {
        log.debug("addRow method started");
        final String noteInput = noteTextField.getText();
        final Object spinnerInput = valueSpinner.getValue();

        if (noteInput != null && !noteInput.trim().isBlank() && spinnerInput != null) {
            CiammoGUI.getCurrentWg().getExpenseList().add(noteInput, (double) spinnerInput, CiammoGUI.getCurrentUser());
            updateTableView();
        }
        else {
            errorMessage.setText("Bitte die Felder links ausfüllen");
            errorMessage.setOpacity(1);
            log.error("addRow method, empty field(s)");
        }
    }

    /**
     * This method removes one row from expense list.
     *
     * * @param event Button "Ausgewählte Zeile entfernen" is clicked
     */
    @FXML
    public void removeRow(ActionEvent event) {
        log.debug("removeRow method started");
        ListObject object = (ListObject) expenseListTableView.getSelectionModel().getSelectedItem();
        if (object != null) {
            CiammoGUI.getCurrentWg().getExpenseList().remove(object);
            updateTableView();
        }
    }

    /**
     * This method removes all items from expense list.
     *
     * * @param event Button "Gesamte Zeile löschen" is clicked
     */
    @FXML
    public void removeAll(ActionEvent event) {
        log.debug("removeAll method started");
        if (CiammoGUI.getCurrentWg().getExpenseList().isListSuccessfullyDeleted()) {
            updateTableView();
        }
    }

    /**
     * This method filters list by username and shows sum.
     *
     * * @param event Button "Start" is clicked
     */
    @FXML
    public void filter(ActionEvent event) {
        log.debug("filter method started");
        errorMessage.setOpacity(0);
        User user = UserManager.getInstance().getUser(selectedUsername);
        double sum = -1;
        if (user != null) {
            sum = CiammoGUI.getCurrentWg().getExpenseList().filterUserExpenses(selectedUsername);
        }
        else {
            errorMessage.setText("Bitte die Felder oben ausfüllen");
            errorMessage.setOpacity(1);
            log.error("filter method, empty field");
        }
        if (sum != -1) {
            valueTextField.setText(Double.toString(sum));
        }
    }
}
