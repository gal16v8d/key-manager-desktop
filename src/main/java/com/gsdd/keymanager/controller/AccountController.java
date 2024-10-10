package com.gsdd.keymanager.controller;

import com.gsdd.constants.GralConstants;
import com.gsdd.gui.util.JPaginateTable;
import com.gsdd.keymanager.constants.KeyManagerConstants;
import com.gsdd.keymanager.entities.Account;
import com.gsdd.keymanager.enums.RolEnum;
import com.gsdd.keymanager.lang.KeyManagerLanguage;
import com.gsdd.keymanager.service.AccountService;
import com.gsdd.keymanager.util.CipherKeyManager;
import com.gsdd.keymanager.view.AccountView;
import com.gsdd.keymanager.view.MainView;
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
public class AccountController implements CrudController<Account> {

  private final AccountService model;
  private final AccountView view;
  private final MainView parentFrame;
  private Account old;

  public AccountController(MainView parentFrame) {
    this.model = new AccountService();
    this.view = new AccountView();
    this.parentFrame = parentFrame;
    loadView();
  }

  private void loadView() {
    try {
      buildView();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
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
          java.lang.Object.class
        };
    table.setTableModel(KeyManagerConstants.getAccountTableModel(), types);
    table.setItemsPerPage(KeyManagerConstants.TBL_PAGE_SIZE);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void updateTableModel(DefaultTableModel dtm, List<?> data) {
    List<Account> listDb = (List<Account>) data;
    int i = 0;
    for (Account u : listDb) {
      dtm.addRow(new Object[1]);
      dtm.setValueAt(u.getFirstName(), i, 0);
      dtm.setValueAt(u.getLastName(), i, 1);
      dtm.setValueAt(u.getLogin(), i, 2);
      dtm.setValueAt(
          String.valueOf(u.getRole()).equals(RolEnum.ADMIN.getCode())
              ? RolEnum.ADMIN.name()
              : RolEnum.USER.name(),
          i,
          3);
      i++;
    }
  }

  @Override
  public Account getDataFromForm() {
    Account account;
    try {
      String labelText = getView().getLabelPk().getText();
      Long id =
          (labelText != null && !labelText.equals(GralConstants.EMPTY)
              ? Long.parseLong(labelText.trim())
              : (long) (System.nanoTime() * (Math.random())));
      account =
          Account.builder()
              .accountId(id)
              .firstName(getView().getTextFirstName().getText().trim())
              .lastName(getView().getTextLastName().getText().trim())
              .login(getView().getTextLogin().getText().trim())
              .password(
                  CipherKeyManager.CYPHER.apply(
                      String.valueOf(getView().getTextPass().getPassword()).trim()))
              .role(
                  RolEnum.ADMIN.name().equals(getView().getLabelVRole().getText())
                      ? Long.valueOf(RolEnum.ADMIN.getCode())
                      : Long.valueOf(RolEnum.USER.getCode()))
              .build();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      account = null;
    }
    return account;
  }

  @Override
  public boolean validateData(Account data) {
    return (data != null
        && data.getFirstName() != null
        && data.getLastName() != null
        && data.getLogin() != null
        && data.getPassword() != null);
  }

  @Override
  public String getSuccessMsg(String loadMsg, Account data) {
    return KeyManagerLanguage.getMessageByLocale(loadMsg) + data.getLogin();
  }

  @Override
  public void clearFields() {
    getView().getLabelPk().setText(GralConstants.EMPTY);
    getView().getTextFirstName().setText(GralConstants.EMPTY);
    getView().getTextLastName().setText(GralConstants.EMPTY);
    getView().getTextLogin().setText(GralConstants.EMPTY);
    getView().getTextPass().setText(GralConstants.EMPTY);
  }

  @Override
  public void performUIActionsAfterSave() {
    clearFields();
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
  public void performUIActionsAfterSearch(Account searchData) {
    setFields(searchData);
    startButtons(true);
  }

  public void setFields(Account dto) {
    getView().getLabelPk().setText(String.valueOf(dto.getAccountId()));
    getView().getTextFirstName().setText(dto.getFirstName());
    getView().getTextLastName().setText(dto.getLastName());
    getView().getTextLogin().setText(dto.getLogin());
    getView().getTextPass().setText(CipherKeyManager.DECIPHER.apply(dto.getPassword()));
    getView()
        .getLabelVRole()
        .setText(
            (RolEnum.ADMIN.getCode().equals(String.valueOf(dto.getRole())))
                ? RolEnum.ADMIN.name()
                : RolEnum.USER.name());
  }
}
