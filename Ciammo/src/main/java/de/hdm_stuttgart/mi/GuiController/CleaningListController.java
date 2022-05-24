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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CleaningListController {

    private static final Logger log = LogManager.getLogger(CleaningListController.class);
    @FXML private Label errorMessage;
    @FXML private TextField weekTextField;
    @FXML private MenuButton chooseUserMenuButton;
    @FXML private TableView cleaningListTableView;
    @FXML private TableColumn<ListObject, String> noteColumn;
    @FXML private TableColumn<ListObject, String> valueColumn;
    @FXML private TableColumn<ListObject, User> userColumn;
    @FXML private ObservableList<Object> data;
    @FXML private TextField noteTextField;
    @FXML private MenuItem item;
    @FXML private DatePicker valueDatePicker;
    private String selectedUsername = null;
    private int calendarWeek = 0;

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

        // Initialize Menu Button
        ArrayList<User> copyUsers = new ArrayList<>(CiammoGUI.getCurrentWg().getUsers());
        for (int i=0; i<copyUsers.size(); i++) {
            item = new MenuItem(copyUsers.get(i).getUsername());
            chooseUserMenuButton.getItems().add(item);
            item.setOnAction(chooseUserMenuButtonEvent);
        }

        // Initialize calendar week Text Field
        weekTextField.setDisable(true);
        weekTextField.setOpacity(1);

        // Initialize Error Message Label
        errorMessage.setOpacity(0);
    }

    /**
     * This method updates the cleaning list table.
     */
    public void updateTableView() {
        log.debug("updateTableView method started");
        data = FXCollections.observableArrayList(CiammoGUI.getCurrentWg().getCleaningList().getObjects());
        noteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNote()));
        valueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString((int) cellData.getValue().getValue())));
        userColumn.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getUser().getUsername()));
        cleaningListTableView.setItems(data);
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
     * This method sets a new item to cleaning list.
     *
     * @param event Button "Hinzufügen" is clicked
     */
    @FXML
    public void addRow(ActionEvent event) {
        log.debug("addRow method started");
        errorMessage.setOpacity(0);
        User user = UserManager.getInstance().getUser(selectedUsername);
        String note = noteTextField.getText();
        if (note != null && !note.trim().isBlank() && calendarWeek != 0 && user != null) {
            CiammoGUI.getCurrentWg().getCleaningList().add(note, calendarWeek, user);
            updateTableView();
        }
        else {
            errorMessage.setOpacity(1);
            log.error("addRow method (cleaningList), empty field(s)");
        }
    }
    /**
     * This method sets a calendar week.
     *
     * @param event calendar button is clicked
     */
    @FXML
    public void setWeek(ActionEvent event) {
        log.debug("setWeek method started");
        LocalDate date = valueDatePicker.getValue();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("w");
        if (date != null) {
            calendarWeek = Integer.parseInt(date.format(dtf));
            weekTextField.setText("KW: " + calendarWeek);
        }
    }

    /**
     * This method removes one row from cleaning list.
     *
     * * @param event Button "Ausgewählte Zeile entfernen" is clicked
     */
    @FXML
    public void removeRow(ActionEvent event) {
        log.debug("removeRow method started");
        ListObject object = (ListObject) cleaningListTableView.getSelectionModel().getSelectedItem();
        if (object != null) {
            CiammoGUI.getCurrentWg().getCleaningList().remove(object);
            updateTableView();
        }
    }

    /**
     * This method removes all items from cleaning list.
     *
     * * @param event Button "Gesamte Zeile löschen" is clicked
     */
    @FXML
    public void removeAll(ActionEvent event) {
        log.debug("removeAll method started");
        if (CiammoGUI.getCurrentWg().getCleaningList().isListSuccessfullyDeleted()) {
            updateTableView();
        }
    }
}
