package com.gsdd.keymanager.controller;

import com.gsdd.constants.GUIConstants;
import com.gsdd.dbutil.DBConnection;
import com.gsdd.gui.util.JOptionUtil;
import com.gsdd.keymanager.constants.KeyManagerConstants;
import com.gsdd.keymanager.enums.MenuOption;
import com.gsdd.keymanager.enums.RolEnum;
import com.gsdd.keymanager.lang.KeyManagerLanguage;
import com.gsdd.keymanager.util.SessionData;
import com.gsdd.keymanager.view.MainView;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class MainController {

  private final MainView view;
  private LoginController loginController;
  private AccountLoginController cuentaXUsuarioController;
  private AccountController usuarioController;
  private ExportController exportController;

  public MainController(MainView view) {
    this.view = view;
    buildView();
  }

  private void buildView() {
    getView()
        .addWindowListener(
            new WindowAdapter() {
              @Override
              public void windowClosing(WindowEvent e) {
                exitApp();
              }
            });
    getView()
        .changeTitle(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TITLE_CUENTAXUSER));
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    getView().setBounds(0, 0, (int) dim.getWidth(), (int) dim.getHeight());
    initPanel();
    addMenuActions();
  }

  private void initPanel() {
    getView().setCards(new JPanel(new CardLayout()));
    if (getLoginController() == null) {
      setLoginController(new LoginController(getView()));
    }
    getView().getCards().add(getLoginController().getView(), MenuOption.LOGIN.name());
    getView().setCl((CardLayout) (getView().getCards().getLayout()));
    getView().getCl().show(getView().getCards(), MenuOption.LOGIN.name());
    getView().getContentPane().add(getView().getCards());
  }

  private void addMenuActions() {
    getView()
        .getCuentaXUsuarioMenuItem()
        .addActionListener((ActionEvent evt) -> selectOption(MenuOption.CUENTAXUSUARIO));
    getView()
        .getUsuarioMenuItem()
        .addActionListener((ActionEvent evt) -> selectOption(MenuOption.USUARIO));
    getView()
        .getExportMenuItem()
        .addActionListener((ActionEvent evt) -> selectOption(MenuOption.EXPORT));
    getView()
        .getSessionMenuItem()
        .addActionListener((ActionEvent evt) -> selectOption(MenuOption.SESSION));
    getView()
        .getExitMenuItem()
        .addActionListener((ActionEvent evt) -> selectOption(MenuOption.EXIT));
    getView()
        .getInfoMenuItem()
        .addActionListener((ActionEvent evt) -> selectOption(MenuOption.CREDITS));
  }

  private void addPanel(JPanel view, String opcion, String titulo) {
    getView().getCards().add(view, opcion);
    getView().sendRedirect(opcion, titulo);
  }

  private void showMessageBasedOnLogin() {
    JOptionUtil.showErrorMessage(
        GUIConstants.ERROR,
        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_ERROR_LOGIN));
  }

  private void selectOption(MenuOption op) {
    switch (op) {
      case CUENTAXUSUARIO -> navigateToCuentaXUsuario();
      case CREDITS -> showCredits();
      case EXPORT -> exportData();
      case EXIT -> exitApp();
      case SESSION -> closeSession();
      case USUARIO -> navigateToUsuario();
      default -> log.warn("Operacion no reconocida: {}", op);
    }
  }

  private void navigateToUsuario() {
    if (SessionData.getInstance().getSessionDto() != null) {
      if (getUsuarioController() == null) {
        setUsuarioController(new AccountController(getView()));
      }
      addPanel(
          getUsuarioController().getView(),
          MenuOption.USUARIO.name(),
          KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TITLE_USUARIO));
    } else {
      showMessageBasedOnLogin();
    }
  }

  private void navigateToCuentaXUsuario() {
    if (SessionData.getInstance().getSessionDto() != null) {
      setCuentaXUsuarioController(new AccountLoginController(getView()));
      addPanel(
          getCuentaXUsuarioController().getView(),
          MenuOption.CUENTAXUSUARIO.name(),
          KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TITLE_CUENTAXUSER));
    } else {
      showMessageBasedOnLogin();
    }
  }

  private void closeSession() {
    getView()
        .sendRedirect(
            MenuOption.LOGIN.name(),
            KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TITLE_LOGIN));
    SessionData.getInstance().setSessionDto(null);
    getLoginController().getInit();
  }

  private void showCredits() {
    try {
      JTextArea areaMC = new JTextArea();
      areaMC.setVisible(true);
      areaMC.setEditable(false);
      areaMC.setText(
          KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TEXT_INFO_AUTHOR)
              + "\n"
              + KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TEXT_INFO_CP));
      JOptionPane.showMessageDialog(
          null,
          areaMC,
          KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TEXT_INFO_VERSION),
          JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  private void exportData() {
    try {
      if (SessionData.getInstance()
          .getSessionDto()
          .getRole()
          .equals(Long.valueOf(RolEnum.ADMIN.getCode()))) {
        JOptionUtil.showErrorMessage(
            GUIConstants.ERROR,
            KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_ERROR_EXPORT));
      } else {
        String out =
            getDirectory(
                KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TITLE_FILECHOOSER));
        if (out == null) {
          JOptionUtil.showAppMessage(
              KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.JOP_TITLE_EXPORT),
              KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_INFO_EXPORT),
              JOptionPane.INFORMATION_MESSAGE);
        } else {
          tryToGenerateReport(out);
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  private void tryToGenerateReport(String out) {
    if (getExportController() == null) {
      setExportController(new ExportController());
    }
    boolean e = getExportController().exportData(out, SessionData.getInstance().getSessionDto());
    if (!e) {
      JOptionUtil.showErrorMessage(
          GUIConstants.ERROR,
          KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_ERROR_XLS));
    } else {
      JOptionUtil.showAppMessage(
          KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.JOP_TITLE_SUCCESS),
          KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.JOP_TITLE_SUCCESS),
          JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void exitApp() {
    int z =
        JOptionPane.showConfirmDialog(
            null,
            KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_INFO_EXIT),
            KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TEXT_INFO_VERSION),
            JOptionPane.YES_NO_OPTION);
    if (z == JOptionPane.YES_OPTION) {
      getView().setVisible(false);
      log.info("Cerrando...");
      shutdownDB();
      System.exit(0);
    }
  }

  private void shutdownDB() {
    try {
      DBConnection.getInstance().disconnectDB();
      DriverManager.getConnection(
          KeyManagerConstants.DERBY_LOCATION + KeyManagerConstants.DERBY_SHUTDOWN);
    } catch (SQLException e) {
      if (!e.getMessage()
          .contains("Database '" + KeyManagerConstants.DERBY_DB_NAME + "' shutdown")) {
        log.error(e.getMessage(), e);
      }
    }
  }

  private String getDirectory(String msg) {
    JFileChooser chooser = new JFileChooser();
    String userDirLocation = System.getProperty("user.dir");
    chooser.setCurrentDirectory(new File(userDirLocation));
    chooser.setDialogTitle(msg);
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    chooser.setAcceptAllFileFilterUsed(false);
    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      return chooser.getSelectedFile().getAbsolutePath();
    } else {
      return null;
    }
  }
}
