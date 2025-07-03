package com.gsdd.keymanager;

import com.gsdd.constants.GralConstants;
import com.gsdd.dbutil.DbConnection;
import com.gsdd.dbutil.DbQueryUtil;
import com.gsdd.exception.TechnicalException;
import com.gsdd.keymanager.constants.KeyManagerConstants;
import com.gsdd.keymanager.controller.MainController;
import com.gsdd.keymanager.view.MainView;
import java.awt.EventQueue;
import java.io.File;
import javax.swing.WindowConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KeyManagerApp {

  private static final DbConnection DB_CONNECTION = new DbConnection();

  public static void main(String[] args) {
    EventQueue.invokeLater(
        () -> {
          try {
            initApp();
          } catch (Exception e) {
            log.error(e.getMessage(), e);
            System.exit(1);
          }
        });
  }

  private static void initApp() {
    log.info("Init...");
    MainView view = new MainView();
    if (initDb()) {
      launchGui(view);
    }
  }

  private static boolean initDb() {
    boolean success = true;
    try {
      System.setProperty("derby.system.home", "." + File.separator + "kmgr" + File.separator);
      System.setProperty(
          "derby.stream.error.file",
          ".." + File.separator + "KMgr-log" + File.separator + "derby.log");
      DB_CONNECTION.connectDB(
          KeyManagerConstants.DERBY_CONNECTION,
          KeyManagerConstants.DERBY_LOCATION + KeyManagerConstants.DERBY_CREATE,
          GralConstants.EMPTY,
          GralConstants.EMPTY);
      boolean b = DbQueryUtil.dbExist(KeyManagerConstants.DERBY_MAIN_TABLE, DB_CONNECTION);
      log.info("DB exists -> {}", b);
      if (!b) {
        DB_CONNECTION.executeImport(Boolean.TRUE);
        log.info("[OK]");
      }
      log.info("{}", DB_CONNECTION.getCon().toString());
    } catch (TechnicalException e) {
      log.error("[FAILED]: {}", e, e);
      success = false;
    }
    return success;
  }

  private static void launchGui(MainView view) {
    view.setVisible(true);
    view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    new MainController(view, DB_CONNECTION);
  }
}
