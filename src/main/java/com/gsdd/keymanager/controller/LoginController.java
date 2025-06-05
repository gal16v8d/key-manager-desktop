package com.gsdd.keymanager.controller;

import com.gsdd.constants.GralConstants;
import com.gsdd.constants.GuiConstants;
import com.gsdd.gui.util.JOptionUtil;
import com.gsdd.keymanager.lang.KeyManagerLanguage;
import com.gsdd.keymanager.service.AccountService;
import com.gsdd.keymanager.util.SessionData;
import com.gsdd.keymanager.view.LoginView;
import com.gsdd.keymanager.view.MainView;
import java.awt.event.ActionEvent;
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
public class LoginController {

  private final AccountService model;
  private final MainView parentFrame;
  private final LoginView view;

  public LoginController(MainView parentFrame) {
    this.model = new AccountService();
    this.view = new LoginView();
    this.parentFrame = parentFrame;
    addButtonActions();
  }

  private void addButtonActions() {
    getView().getLoginButton().addActionListener((ActionEvent evt) -> authenticate());
  }

  private void authenticate() {
    try {
      String username = getView().getTextUser().getText().trim();
      String pass = String.valueOf(getView().getTextPass().getPassword()).trim();
      SessionData sessionData = SessionData.getInstance();
      sessionData.setSessionDto(getModel().login(username, pass));
      if (sessionData.getSessionDto() != null) {
        getLogged();
        sessionData.getSessionDto().setLogin(username);
        getParentFrame()
            .changeTitle(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TITLE_LOGIN));
        String session =
            KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_LOGGED)
                + sessionData.getSessionDto().getFirstName()
                + " "
                + sessionData.getSessionDto().getLastName();
        getParentFrame().getSessionMenu().setText(session);
      } else {
        String msg = KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_ERROR_USER_NE);
        log.info("{}", msg);
        JOptionUtil.showErrorMessage(GuiConstants.ERROR, msg);
        getView().getTextPass().setText(GralConstants.EMPTY);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      JOptionUtil.showErrorMessage(GuiConstants.ERROR, e.getMessage());
    }
  }

  private void clearText() {
    getView().getTextUser().setText(GralConstants.EMPTY);
    getView().getTextPass().setText(GralConstants.EMPTY);
  }

  private void enableComponents(boolean flag) {
    getView().getLabelUser().setVisible(flag);
    getView().getLabelPass().setVisible(flag);
    getView().getTextUser().setVisible(flag);
    getView().getTextPass().setVisible(flag);
    getView().getLoginButton().setVisible(flag);
  }

  private void getLogged() {
    enableComponents(false);
  }

  public void getInit() {
    clearText();
    getParentFrame()
        .getSessionMenu()
        .setText(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MENU_SESSION));
    enableComponents(true);
  }
}
