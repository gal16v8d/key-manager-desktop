package com.gsdd.keymanager.controller;

import com.gsdd.constants.GralConstants;
import com.gsdd.gui.util.JPaginateTable;
import com.gsdd.keymanager.constants.KeyManagerConstants;
import com.gsdd.keymanager.ejb.AccountLoginService;
import com.gsdd.keymanager.ejb.AccountService;
import com.gsdd.keymanager.entities.AccountLogin;
import com.gsdd.keymanager.entities.dto.AccountLoginDto;
import com.gsdd.keymanager.enums.RolEnum;
import com.gsdd.keymanager.lang.KeyManagerLanguage;
import com.gsdd.keymanager.util.CypherKeyManager;
import com.gsdd.keymanager.util.SessionData;
import com.gsdd.keymanager.view.AccountLoginView;
import com.gsdd.keymanager.view.MainView;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

/**
 * @author Great System Development Dynamic <GSDD> <br>
 *     Alexander Galvis Grisales <br>
 *     alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@Slf4j
@Getter
@Setter
public class AccountLoginController implements CrudController<AccountLogin> {

  private final AccountLoginService model;
  private final AccountService userModel;
  private final AccountLoginView view;
  private final MainView parentFrame;
  private AccountLogin old;

  public AccountLoginController(MainView parentFrame) {
    this.model = new AccountLoginService();
    this.userModel = new AccountService();
    this.view = new AccountLoginView();
    this.parentFrame = parentFrame;
    loadView();
  }

  private void loadView() {
    try {
      buildView();
      fillCombo();
      addActionsToButtons();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  public void fillCombo() {
    List<String> user = getUserModel().suggest();
    for (String u : user) {
      getView().getComboUsuario().addItem(u);
    }
    getView().getComboUsuario().repaint();
  }

  private void addActionsToButtons() {
    getView().getShowOrHideButton().addActionListener((ActionEvent evt) -> showOrHidePass());
  }

  @Override
  public Logger getLogger() {
    return log;
  }

  @Override
  @SuppressWarnings("unchecked")
  public AccountLoginService getEjbModel() {
    return getModel();
  }

  @Override
  @SuppressWarnings("rawtypes")
  public void setTableModel(JPaginateTable tabla) {
    Class[] types =
        new Class[] {
          java.lang.Object.class,
          java.lang.Object.class,
          java.lang.Object.class,
          java.lang.Object.class,
          java.lang.Object.class,
          java.lang.Object.class,
          java.lang.Object.class
        };
    tabla.setTableModel(KeyManagerConstants.getAccountXUserTableModel(), types);
    tabla.setItemsPerPage(KeyManagerConstants.TBL_PAGE_SIZE);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void updateTableModel(DefaultTableModel dtm, List<?> data) {
    List<AccountLoginDto> listDB = (List<AccountLoginDto>) data;
    int i = 0;
    for (AccountLoginDto dto : listDB) {
      dtm.addRow(new Object[1]);
      dtm.setValueAt(dto.getSessionLogin(), i, 0);
      dtm.setValueAt(dto.getAccountName(), i, 1);
      dtm.setValueAt(dto.getLogin(), i, 2);
      String dp = showOrHidePass(dto.getPass(), false);
      dtm.setValueAt(dp, i, 3);
      dtm.setValueAt(dto.getUrl(), i, 4);
      Date fd = dto.getModificationDate();
      Date fa = Date.valueOf(LocalDate.now());
      String fecha = KeyManagerConstants.getFormater().format(fd);
      dtm.setValueAt(fecha, i, 5);
      dtm.setValueAt(KeyManagerConstants.getSuggestion(fa, fd), i, 6);
      i++;
    }
  }

  @Override
  public AccountLogin getDataFromForm() {
    AccountLogin data = null;
    try {
      SessionData sessionData = SessionData.getInstance();
      data = AccountLogin.builder().accountName(getView().getTextCuenta().getText().trim())
          .accountId(
              String.valueOf(sessionData.getSessionDto().getRole()).equals(RolEnum.ADMIN.getCode())
                  ? getUserModel().search((String) getView().getComboUsuario().getSelectedItem())
                      .getAccountId()
                  : sessionData.getSessionDto().getAccountId())
          .login(getView().getTextUserName().getText().trim())
          .password(CypherKeyManager
              .encodeKM(String.valueOf(getView().getTextPass().getPassword()).trim()))
          .url(getView().getTextUrl().getText()).build();
      return data;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  private String showOrHidePass(String ePass, boolean show) {
    return show ? CypherKeyManager.decodeKM(ePass) : KeyManagerConstants.MASK_TEXTO;
  }

  @Override
  public boolean validateData(AccountLogin data) {
    return (data != null
        && data.getAccountName() != null
        && data.getAccountId() != null
        && data.getLogin() != null
        && data.getPassword() != null);
  }

  @Override
  public String getSuccessMsg(String loadMsg, AccountLogin data) {
    return KeyManagerLanguage.getMessageByLocale(loadMsg) + data.getAccountName();
  }

  @Override
  public void clearFields() {
    getView().getTextCuenta().setText(GralConstants.EMPTY);
    getView().getTextUrl().setText(GralConstants.EMPTY);
    getView().getTextUserName().setText(GralConstants.EMPTY);
    getView().getTextPass().setText(GralConstants.EMPTY);
  }

  public void clearCombo() {
    getView().getComboUsuario().removeAllItems();
  }

  @Override
  public void performUIActionsAfterSave() {
    clearFields();
    clearCombo();
    fillCombo();
  }

  @Override
  public void performUIActionsAfterUpdate() {
    performUIActionsAfterSave();
    startButtons(false);
    fillTable(getView().getDataTable());
  }

  @Override
  public void performUIActionsAfterDelete() {
    performUIActionsAfterUpdate();
  }

  @Override
  public void performUIActionsAfterSearch(AccountLogin searchData) {
    setFields(searchData);
    setOld(searchData);
    startButtons(true);
  }

  public void setFields(AccountLogin dto) {
    getView().getComboUsuario().setSelectedItem(dto.getLogin());
    getView().getTextCuenta().setText(dto.getAccountName());
    getView().getTextUserName().setText(dto.getLogin());
    getView().getTextPass().setText(CypherKeyManager.decodeKM(dto.getPassword()));
    getView().getTextUrl().setText(dto.getUrl());
  }

  public void showOrHidePass() {
    if (KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_SHOW)
        .equals(getView().getShowOrHideButton().getToolTipText())) {
      getView().getTextPass().setEchoChar((char) 0);
      getView()
          .getShowOrHideButton()
          .setToolTipText(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_HIDE));
      getView()
          .getShowOrHideButton()
          .setText(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_HIDE));
    } else {
      getView().getTextPass().setEchoChar(KeyManagerConstants.HIDE_TEXT);
      getView()
          .getShowOrHideButton()
          .setToolTipText(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_SHOW));
      getView()
          .getShowOrHideButton()
          .setText(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_SHOW));
    }
  }
}
