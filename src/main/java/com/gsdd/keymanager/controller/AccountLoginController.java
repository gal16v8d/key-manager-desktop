package com.gsdd.keymanager.controller;

import com.gsdd.constants.GralConstants;
import com.gsdd.gui.util.JPaginateTable;
import com.gsdd.keymanager.constants.KeyManagerConstants;
import com.gsdd.keymanager.entities.AccountLogin;
import com.gsdd.keymanager.entities.AccountType;
import com.gsdd.keymanager.entities.dto.AccountLoginDto;
import com.gsdd.keymanager.enums.RolEnum;
import com.gsdd.keymanager.lang.KeyManagerLanguage;
import com.gsdd.keymanager.service.AccountLoginService;
import com.gsdd.keymanager.service.AccountService;
import com.gsdd.keymanager.service.AccountTypeService;
import com.gsdd.keymanager.util.CipherKeyManager;
import com.gsdd.keymanager.util.SessionData;
import com.gsdd.keymanager.view.AccountLoginView;
import com.gsdd.keymanager.view.MainView;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
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
  private final AccountTypeService typeModel;
  private final AccountLoginView view;
  private final MainView parentFrame;
  private AccountLogin old;
  private List<AccountType> types;

  private BiFunction<String, Boolean, String> showOrHidePass =
      (ePass, show) ->
          show.booleanValue()
              ? CipherKeyManager.DECIPHER.apply(ePass)
              : KeyManagerConstants.MASK_TEXT;

  public AccountLoginController(MainView parentFrame) {
    this.model = new AccountLoginService();
    this.userModel = new AccountService();
    this.typeModel = new AccountTypeService();
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
    user.forEach(getView().getComboUser()::addItem);
    getView().getComboUser().repaint();
    List<String> type = getTypeModel().suggest();
    type.forEach(getView().getComboType()::addItem);
    getView().getComboType().repaint();
  }

  private void addActionsToButtons() {
    getView().getShowOrHideButton().addActionListener((ActionEvent evt) -> showOrHidePass());
  }

  @Override
  public Logger getLogger() {
    return log;
  }

  @Override
  @SuppressWarnings("rawtypes")
  public void setTableModel(JPaginateTable table) {
    Class[] types =
        new Class[] {
          java.lang.Object.class,
          java.lang.Object.class,
          java.lang.Object.class,
          java.lang.Object.class,
          java.lang.Object.class,
          java.lang.Object.class,
          java.lang.Object.class,
          java.lang.Object.class
        };
    table.setTableModel(KeyManagerConstants.getAccountLoginTableModel(), types);
    table.setItemsPerPage(KeyManagerConstants.TBL_PAGE_SIZE);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void updateTableModel(DefaultTableModel dtm, List<?> data) {
    List<AccountLoginDto> listDb = (List<AccountLoginDto>) data;
    int i = 0;
    for (AccountLoginDto dto : listDb) {
      dtm.addRow(new Object[1]);
      dtm.setValueAt(dto.getSessionLogin(), i, 0);
      dtm.setValueAt(dto.getAccountName(), i, 1);
      dtm.setValueAt(dto.getAccountType(), i, 2);
      dtm.setValueAt(dto.getLogin(), i, 3);
      String dp = showOrHidePass.apply(dto.getPass(), false);
      dtm.setValueAt(dp, i, 4);
      dtm.setValueAt(dto.getUrl(), i, 5);
      Date fd = dto.getModificationDate();
      Date fa = Date.valueOf(LocalDate.now());
      String date = KeyManagerConstants.getFormater().format(fd);
      dtm.setValueAt(date, i, 6);
      dtm.setValueAt(KeyManagerConstants.SHOW_SUGGESTION.apply(fa, fd), i, 7);
      i++;
    }
  }

  @Override
  public AccountLogin getDataFromForm() {
    AccountLogin accountLogin;
    try {
      SessionData sessionData = SessionData.getInstance();
      accountLogin =
          AccountLogin.builder()
              .accountName(getView().getTextAccount().getText().trim())
              .accountId(
                  String.valueOf(sessionData.getSessionDto().getRole())
                          .equals(RolEnum.ADMIN.getCode())
                      ? getUserModel()
                          .search((String) getView().getComboUser().getSelectedItem())
                          .getAccountId()
                      : sessionData.getSessionDto().getAccountId())
              .login(getView().getTextUserName().getText().trim())
              .password(
                  CipherKeyManager.CYPHER.apply(
                      String.valueOf(getView().getTextPass().getPassword()).trim()))
              .url(getView().getTextUrl().getText())
              .build();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      accountLogin = null;
    }
    return accountLogin;
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
    getView().getTextAccount().setText(GralConstants.EMPTY);
    getView().getTextUrl().setText(GralConstants.EMPTY);
    getView().getTextUserName().setText(GralConstants.EMPTY);
    getView().getTextPass().setText(GralConstants.EMPTY);
  }

  public void clearCombo() {
    getView().getComboUser().removeAllItems();
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

  @SuppressWarnings("unchecked")
  public void setFields(AccountLogin dto) {
    this.types = (List<AccountType>) typeModel.list();
    this.types = Optional.ofNullable(types).orElseGet(Collections::emptyList);
    this.types.stream()
        .filter(t -> Objects.equals(t.getTypeId(), dto.getTypeId()))
        .findAny()
        .map(AccountType::getName)
        .ifPresent(name -> getView().getComboType().setSelectedItem(name));
    getView().getComboUser().setSelectedItem(dto.getLogin());
    getView().getTextAccount().setText(dto.getAccountName());
    getView().getTextUserName().setText(dto.getLogin());
    getView().getTextPass().setText(CipherKeyManager.DECIPHER.apply(dto.getPassword()));
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
