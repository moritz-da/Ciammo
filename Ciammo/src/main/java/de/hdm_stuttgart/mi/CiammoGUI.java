package de.hdm_stuttgart.mi;

import de.hdm_stuttgart.mi.Helper.Configuration;
import de.hdm_stuttgart.mi.Threads.ReadWriteEnum;
import de.hdm_stuttgart.mi.Threads.ThreadSaveData;
import de.hdm_stuttgart.mi.Users.User;
import de.hdm_stuttgart.mi.Wgs.Wg;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Objects;

public class CiammoGUI extends Application{

    private static final Logger log = LogManager.getLogger(CiammoGUI.class);
    private static Stage stage;
    private static final int width = 600;
    private static final int height = 400;
    private static User currentUser;
    private static Wg currentWg;
    private static final String cssLight = "/styles/lightMode.css";
    private static final String cssDark = "/styles/darkMode.css";

    /**
     * Main method starts the program
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
        writeData();
        log.info("CiammoGUI finished");
    }

    /**
     * This method writes data
     *
     */
    public static void writeData() {
        log.debug("writeData method started");
        Thread thread3 = new Thread(new ThreadSaveData("thread4", ReadWriteEnum.WriteWgManager));
        Thread thread4 = new Thread(new ThreadSaveData("thread3", ReadWriteEnum.WriteUserManager));
        thread3.start();
        thread4.start();
    }

    /**
     * Defines how a scene should look like
     *
     * @param fxmlFile defines the look of one page
     * @return true if the scene can be shown, false otherwise
     */
    public static boolean showScene(String fxmlFile) {
        try {
            log.debug("Show scene of fxmlFile: " + (fxmlFile));
            final FXMLLoader loader = new FXMLLoader();
            final Parent rootNode = loader.load(CiammoGUI.class.getResourceAsStream(fxmlFile));
            final Scene scene = new Scene(loader.getRoot(), CiammoGUI.width, CiammoGUI.height);
            if (Boolean.parseBoolean(Configuration.readProp("isLightMode"))) {
                scene.getStylesheets().add(Objects.requireNonNull(CiammoGUI.class.getResource(cssLight)).toExternalForm());
            } else {
                scene.getStylesheets().add(Objects.requireNonNull(CiammoGUI.class.getResource(cssDark)).toExternalForm());
            }
            stage.setScene(scene);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return false;
        }
        return true;
    }

    /**
     * This method loads data
     *
     */
    public static void loadData() {
        log.debug("loadData method started");
        Thread thread0 = new Thread(new ThreadSaveData("thread0", ReadWriteEnum.ReadWgManager));
        Thread thread1 = new Thread(new ThreadSaveData("thread0", ReadWriteEnum.ReadUserManager));
        thread0.start();
        thread1.start();
    }

    /**
     * Makes the login scene, when the program is started. And loads the data, which is needed.
     *
     * @param firststage
     * @throws Exception
     */
    @Override
    public void start(Stage firststage) throws Exception {
        log.info("CiammoGUI started");
        stage = firststage;
        loadData();
        stage.setTitle("Ciammo");
        stage.setMinWidth(601);
        stage.setMinHeight(428);
        stage.setMaxWidth(601);
        stage.setMaxHeight(428);
        showScene("/fxml/login.fxml");
        stage.show();
    }

    /**
     * Set the user account, who is logged in at the moment.
     *
     * @param currentUser account that is logged in now
     */
    public static void setCurrentUser(User currentUser) {
        CiammoGUI.currentUser = currentUser;
    }

    /**
     * Set the wg of current user, who is logged in at the moment
     *
     * @param currentWg wg of user that is logged in now
     */
    public static void setCurrentWg(Wg currentWg) {
        CiammoGUI.currentWg = currentWg;
    }

    /**
     * Delivers the user account, who is logged in at the moment.
     *
     * @return account of the user
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Delivers wg of current user, who is logged in at the moment.
     *
     * @return wg of the user
     */
    public static Wg getCurrentWg() {
        return currentWg;
    }
}