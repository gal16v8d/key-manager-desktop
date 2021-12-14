package com.gsdd.keymanager.controller;

import java.awt.event.ActionEvent;
import com.gsdd.constants.GUIConstants;
import com.gsdd.constants.GralConstants;
import com.gsdd.gui.util.JOptionUtil;
import com.gsdd.keymanager.ejb.UsuarioEjb;
import com.gsdd.keymanager.lang.KeyManagerLanguage;
import com.gsdd.keymanager.util.SessionData;
import com.gsdd.keymanager.view.LoginView;
import com.gsdd.keymanager.view.MainView;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Great System Development Dynamic <GSDD> <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@Slf4j
@Getter
@Setter
public class LoginController {

  private final UsuarioEjb model;
  private final MainView parentFrame;
  private final LoginView view;

  public LoginController(MainView parentFrame) {
    this.model = new UsuarioEjb();
    this.view = new LoginView();
    this.parentFrame = parentFrame;
    addButtonActions();
  }

  private void addButtonActions() {
    getView().getLoginButton().addActionListener((ActionEvent evt) -> authenticate());
  }

  private void authenticate() {
    try {
      String username = getView().getTextUsuario().getText().trim();
      String pass = String.valueOf(getView().getTextPass().getPassword()).trim();
      SessionData sessionData = SessionData.getInstance();
      sessionData.setSessionDto(getModel().login(username, pass));
      if (sessionData.getSessionDto() != null) {
        getLoged();
        sessionData.getSessionDto().setUsername(username);
        getParentFrame()
            .changeTitle(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TITLE_LOGIN));
        String session = KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_LOGGED)
            + sessionData.getSessionDto().getPrimerNombre() + " "
            + sessionData.getSessionDto().getPrimerApellido();
        getParentFrame().getSessionMenu().setText(session);
      } else {
        String msg = KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_ERROR_USER_NE);
        log.info("{}", msg);
        JOptionUtil.showErrorMessage(GUIConstants.ERROR, msg);
        getView().getTextPass().setText(GralConstants.EMPTY);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      JOptionUtil.showErrorMessage(GUIConstants.ERROR, e.getMessage());
    }
  }

  private void clearText() {
    getView().getTextUsuario().setText(GralConstants.EMPTY);
    getView().getTextPass().setText(GralConstants.EMPTY);
  }

  private void enableComponents(boolean flag) {
    getView().getLabelUsuario().setVisible(flag);
    getView().getLabelPass().setVisible(flag);
    getView().getTextUsuario().setVisible(flag);
    getView().getTextPass().setVisible(flag);
    getView().getLoginButton().setVisible(flag);
  }

  private void getLoged() {
    enableComponents(false);
  }

  public void getInit() {
    clearText();
    getParentFrame().getSessionMenu()
        .setText(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MENU_SESSION));
    enableComponents(true);
  }

}
