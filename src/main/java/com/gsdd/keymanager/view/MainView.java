package com.gsdd.keymanager.view;

import com.gsdd.keymanager.constants.KeyManagerConstants;
import com.gsdd.keymanager.lang.KeyManagerLanguage;
import com.gsdd.keymanager.util.ExecutorKeyManager;
import java.awt.CardLayout;
import java.io.Serial;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Great System Development Dynamic <GSDD> <br>
 *     Alexander Galvis Grisales <br>
 *     alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@Slf4j
@Getter
@Setter
public class MainView extends JFrame {

  @Serial private static final long serialVersionUID = 1L;
  private JPanel cards;
  private CardLayout cl;
  private JMenu adminMenu;
  private JMenu sessionMenu;
  private JMenuItem accountTypeMenuItem;
  private JMenuItem accountUserMenuItem;
  private JMenuItem userMenuItem;
  private JMenuItem exportMenuItem;
  private JMenuItem sessionMenuItem;
  private JMenuItem exitMenuItem;
  private JMenuItem infoMenuItem;

  public MainView() {
    try {
      KeyManagerLanguage.initBundle(getLocale());
      buildMenu();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      System.exit(1);
    }
  }

  private void buildMenu() {
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    adminMenu = new JMenu(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MENU_ADMON));
    menuBar.add(adminMenu);
    sessionMenu = new JMenu(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MENU_SESSION));
    menuBar.add(sessionMenu);
    JMenu menuInfo = new JMenu(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MENU_INFO));
    menuBar.add(menuInfo);
    ExecutorKeyManager.getInstance().getExecutor().execute(this::addAdmonMenuItems);
    ExecutorKeyManager.getInstance().getExecutor().execute(this::addSessionMenuItems);
    ExecutorKeyManager.getInstance().getExecutor().execute(() -> addInfoMenuItems(menuInfo));
    setIconImage(new ImageIcon(getClass().getResource(KeyManagerConstants.IMAGE_ICON)).getImage());
  }

  private JMenuItem addMenuItem(String text, JMenu parent) {
    JMenuItem menuItem = new JMenuItem(KeyManagerLanguage.getMessageByLocale(text));
    parent.add(menuItem);
    return menuItem;
  }

  private void addAdmonMenuItems() {
    setAccountTypeMenuItem(addMenuItem(KeyManagerLanguage.MENU_ITEM_ACCOUNT_TYPE, adminMenu));
    adminMenu.add(new JSeparator());
    setAccountUserMenuItem(addMenuItem(KeyManagerLanguage.MENU_ITEM_ACCOUNT_USER, adminMenu));
    adminMenu.add(new JSeparator());
    setUserMenuItem(addMenuItem(KeyManagerLanguage.MENU_ITEM_USER, adminMenu));
    adminMenu.add(new JSeparator());
    setExportMenuItem(addMenuItem(KeyManagerLanguage.MENU_ITEM_EXPORT, adminMenu));
  }

  private void addSessionMenuItems() {
    setSessionMenuItem(addMenuItem(KeyManagerLanguage.MENU_ITEM_SESSION, sessionMenu));
    sessionMenu.add(new JSeparator());
    setExitMenuItem(addMenuItem(KeyManagerLanguage.MENU_ITEM_EXIT, sessionMenu));
  }

  private void addInfoMenuItems(JMenu menuInfo) {
    setInfoMenuItem(addMenuItem(KeyManagerLanguage.MENU_ITEM_INFO, menuInfo));
  }

  public void changeTitle(String title) {
    setTitle(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TITLE_MAIN) + title);
  }

  public void sendRedirect(String panel, String title) {
    changeTitle(title);
    getCl().show(getCards(), panel);
  }
}
