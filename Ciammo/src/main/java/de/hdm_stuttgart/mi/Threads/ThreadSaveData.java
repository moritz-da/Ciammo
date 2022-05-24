package de.hdm_stuttgart.mi.Threads;

import de.hdm_stuttgart.mi.Users.UserManager;
import de.hdm_stuttgart.mi.Wgs.WgManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class ThreadSaveData creates Threads which help with data saving and data loading
 */
public class ThreadSaveData implements Runnable {

    private static Logger log = LogManager.getLogger(ThreadSaveData.class);
    private final String threadName;
    private final ReadWriteEnum readWrite;

    /**
     * Constructor for Class ThreadSvadeData
     *
     * @param readWrite the name of the thread
     * @param threadName enum state. Read should be used for Data loading, Write for Data saving.
     */
    public ThreadSaveData(String threadName, ReadWriteEnum readWrite) {
        this.threadName = threadName;
        this.readWrite = readWrite;
    }


    /**
     * This method lets threads run
     */
    @Override
    public void run() {
        if (readWrite == ReadWriteEnum.WriteUserManager) {
            log.debug("FileThread choose WriteUserManager");
            UserManager.saveUserManager();
        }
        if (readWrite == ReadWriteEnum.WriteWgManager) {
            log.debug("FileThread choose WriteWgManager");
            WgManager.saveWgManager();
        }
        if (readWrite == ReadWriteEnum.ReadUserManager) {
            log.debug("FileThread choose ReadUserManager");
            UserManager.loadUserManager();
        }
        if (readWrite == ReadWriteEnum.ReadWgManager) {
            log.debug("FileThread choose ReadWgManager");
            WgManager.loadWgManager();
        }
    }
}
