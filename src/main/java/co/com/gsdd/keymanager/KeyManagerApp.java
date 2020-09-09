package co.com.gsdd.keymanager;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.WindowConstants;

import co.com.gsdd.constants.GralConstants;
import co.com.gsdd.dbutil.DBConnection;
import co.com.gsdd.dbutil.DBQueryUtil;
import co.com.gsdd.exception.TechnicalException;
import co.com.gsdd.keymanager.constants.KeyManagerConstants;
import co.com.gsdd.keymanager.controller.MainController;
import co.com.gsdd.keymanager.view.MainView;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KeyManagerApp {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                initApp();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                System.exit(1);
            }
        });
    }

    private static void initApp() {
        log.info("Inicializando...");
        MainView view = new MainView();
        if (initDB()) {
            launchGUI(view);
        }
    }

    private static boolean initDB() {
        boolean success = true;
        DBConnection.getInstance();
        try {
            System.setProperty("derby.system.home", "." + File.separator + "kmgr" + File.separator);
            System.setProperty("derby.stream.error.file",
                    ".." + File.separator + "KMgr-log" + File.separator + "derby.log");
            boolean b = DBQueryUtil.dbExist(KeyManagerConstants.DERBY_MAIN_TABLE, KeyManagerConstants.DERBY_CONNECTION,
                    KeyManagerConstants.DERBY_LOCATION + KeyManagerConstants.DERBY_CREATE, GralConstants.EMPTY,
                    GralConstants.EMPTY);
            log.info("BD existe -> {}", b);
            if (!b) {
                DBConnection.getInstance().executeImport(Boolean.TRUE);
                log.info("[OK]");
            }
            if (DBConnection.getInstance().getCon() == null) {
                DBConnection.getInstance().connectDB(KeyManagerConstants.DERBY_CONNECTION,
                        KeyManagerConstants.DERBY_LOCATION, GralConstants.EMPTY, GralConstants.EMPTY);
            }
            log.info("{}", DBConnection.getInstance().getCon().toString());
        } catch (TechnicalException e) {
            log.error("[FALLO]: {}", e, e);
            success = false;
        }
        return success;
    }

    private static void launchGUI(MainView view) {
        view.setVisible(true);
        view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        new MainController(view);
    }

}
