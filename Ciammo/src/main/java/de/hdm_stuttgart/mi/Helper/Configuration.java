package de.hdm_stuttgart.mi.Helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.util.Properties;

public final class Configuration {

    private static final Logger log = LogManager.getLogger(Configuration.class);

    private Configuration() {}

    /**
     * This return path of operating system (os).
     *
     * @return path oath of os
     */
    private static String getPath() {
        log.debug("getPath method started");
        String path = null;
        try {
            final String os = System.getProperty("os.name"); // os = operating system
            if (os.contains("Windows")) {
                path = ".\\src\\main\\resources\\de\\hdm_stuttgart\\mi\\config.properties";
            } else {
                path = "src/main/resources/de/hdm_stuttgart/mi/config.properties";
            }
        } catch (final Exception e) {
            log.fatal("Error while trying to load resourcePath in Configuration method");
        }
        return path;
    }

    /**
     * This method read properties.
     *
     * @param key parameter stored as a pair of strings
     */
    public static String readProp(String key) {
        log.debug("readProp method started");
        String value = null;
        try {
            InputStream inputStream = new FileInputStream(getPath());
            Properties props = new Properties();
            props.load(inputStream);
            value = props.getProperty(key);
            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error("readProp method, Reading configuration data failed");
        }
        log.debug("readProp method, configuration data was loaded");
        return value;
    }

    /**
     * This method write properties.
     *
     * @param key
     * @param value
     * @param comment
     */
    public static void writeProp(String key, String value, String comment) {
        log.debug("writeProp method started");
        try (OutputStream output = new FileOutputStream(getPath())) {
            Properties prop = new Properties();
            prop.setProperty(key, value);
            prop.store(output, comment);
            log.info("writeProp method, configuration data was saved");
        } catch (IOException io) {
            io.printStackTrace();
            log.error("writeProp method, Saving configuration data failed");
        }
    }
}
