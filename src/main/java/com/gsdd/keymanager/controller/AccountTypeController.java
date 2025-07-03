package com.gsdd.keymanager.controller;

import com.gsdd.constants.GralConstants;
import com.gsdd.dbutil.DbConnection;
import com.gsdd.gui.util.JPaginateTable;
import com.gsdd.keymanager.constants.KeyManagerConstants;
import com.gsdd.keymanager.entities.AccountType;
import com.gsdd.keymanager.lang.KeyManagerLanguage;
import com.gsdd.keymanager.service.AccountTypeService;
import com.gsdd.keymanager.view.AccountTypeView;
import com.gsdd.keymanager.view.MainView;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.table.DefaultTableModel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

@Slf4j
@Getter
@Setter
public class AccountTypeController implements CrudController<AccountType> {

  private final AccountTypeService model;
  private final AccountTypeView view;
  private final MainView parentFrame;
  private AccountType old;

  public AccountTypeController(MainView parentFrame, DbConnection db) {
    this.model = new AccountTypeService(db);
    this.view = new AccountTypeView();
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
    Class[] types = new Class[] {java.lang.Object.class};
    table.setTableModel(KeyManagerConstants.getAccountTypeTableModel(), types);
    table.setItemsPerPage(KeyManagerConstants.TBL_PAGE_SIZE);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void updateTableModel(DefaultTableModel dtm, List<?> data) {
    AtomicInteger pos = new AtomicInteger();
    ((List<AccountType>) data)
        .forEach(
            (AccountType type) -> {
              dtm.addRow(new Object[1]);
              dtm.setValueAt(type.getName(), pos.get(), 0);
              pos.getAndIncrement();
            });
  }

  @Override
  public AccountType getDataFromForm() {
    AccountType type;
    try {
      String labelText = getView().getLabelPk().getText();
      Long id =
          (labelText != null && !labelText.equals(GralConstants.EMPTY)
              ? Long.parseLong(labelText.trim())
              : (long) (System.nanoTime() * (Math.random())));
      type =
          AccountType.builder().typeId(id).name(getView().getTextName().getText().trim()).build();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      type = null;
    }
    return type;
  }

  @Override
  public boolean validateData(AccountType data) {
    return data != null && data.getName() != null;
  }

  @Override
  public String getSuccessMsg(String loadMsg, AccountType data) {
    return KeyManagerLanguage.getMessageByLocale(loadMsg) + data.getName();
  }

  @Override
  public void clearFields() {
    getView().getLabelPk().setText(GralConstants.EMPTY);
    getView().getTextName().setText(GralConstants.EMPTY);
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
  public void performUIActionsAfterSearch(AccountType searchData) {
    setFields(searchData);
    startButtons(true);
  }

  public void setFields(AccountType dto) {
    getView().getLabelPk().setText(String.valueOf(dto.getTypeId()));
    getView().getTextName().setText(dto.getName());
  }
}
