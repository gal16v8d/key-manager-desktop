package com.gsdd.keymanager.service;

import com.gsdd.dbutil.DBConnection;
import com.gsdd.keymanager.constants.QueryConstants;
import com.gsdd.keymanager.entities.AccountType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

@Slf4j
public class AccountTypeService implements DbService<AccountType> {

  @Override
  public Logger getLogger() {
    return log;
  }

  @Override
  public void defineInsertData(AccountType data) throws SQLException {
    DBConnection.getInstance()
        .setPst(
            DBConnection.getInstance()
                .getCon()
                .prepareStatement(QueryConstants.ACCOUNT_TYPE_INSERT));
    DBConnection.getInstance().getPst().setLong(1, data.getTypeId());
    DBConnection.getInstance().getPst().setString(2, data.getName());
  }

  @Override
  public void defineUpdateData(AccountType data, AccountType oldData) throws SQLException {
    DBConnection.getInstance()
        .setPst(
            DBConnection.getInstance()
                .getCon()
                .prepareStatement(QueryConstants.ACCOUNT_TYPE_UPDATE));
    DBConnection.getInstance().getPst().setString(1, data.getName());
    DBConnection.getInstance().getPst().setLong(2, data.getTypeId());
  }

  @Override
  public void defineDeleteData(AccountType data) throws SQLException {
    DBConnection.getInstance()
        .setPst(
            DBConnection.getInstance()
                .getCon()
                .prepareStatement(QueryConstants.ACCOUNT_TYPE_DELETE));
    DBConnection.getInstance().getPst().setLong(1, data.getTypeId());
  }

  @Override
  public List<?> list() {
    List<AccountType> lu = new ArrayList<>();
    try {
      DBConnection.getInstance()
          .setPst(
              DBConnection.getInstance()
                  .getCon()
                  .prepareStatement(QueryConstants.ACCOUNT_TYPE_LIST));
      DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
      while (DBConnection.getInstance().getRs().next()) {
        AccountType accountType =
            AccountType.builder().name(DBConnection.getInstance().getRs().getString(2)).build();
        lu.add(accountType);
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      DBConnection.getInstance().closeQuery();
    }
    return lu;
  }

  @Override
  public List<String> suggest() {
    return list().stream().map(element -> (AccountType) element).map(AccountType::getName).toList();
  }

  @Override
  public AccountType search(String key) {
    AccountType accountType = null;
    try {
      DBConnection.getInstance()
          .setPst(
              DBConnection.getInstance()
                  .getCon()
                  .prepareStatement(QueryConstants.ACCOUNT_TYPE_SEARCH));
      DBConnection.getInstance().getPst().setString(1, key);
      DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
      while (DBConnection.getInstance().getRs().next()) {
        accountType = AccountType.builder()
            .typeId(DBConnection.getInstance().getRs().getLong(1))
            .name(DBConnection.getInstance().getRs().getString(2))
            .build();
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      DBConnection.getInstance().closeQuery();
    }
    return accountType;
  }

}
