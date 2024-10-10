package com.gsdd.keymanager.service;

import com.gsdd.dbutil.DbConnection;
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
    DbConnection.getInstance()
        .setPst(
            DbConnection.getInstance()
                .getCon()
                .prepareStatement(QueryConstants.ACCOUNT_TYPE_INSERT));
    DbConnection.getInstance().getPst().setLong(1, data.getTypeId());
    DbConnection.getInstance().getPst().setString(2, data.getName());
  }

  @Override
  public void defineUpdateData(AccountType data, AccountType oldData) throws SQLException {
    DbConnection.getInstance()
        .setPst(
            DbConnection.getInstance()
                .getCon()
                .prepareStatement(QueryConstants.ACCOUNT_TYPE_UPDATE));
    DbConnection.getInstance().getPst().setString(1, data.getName());
    DbConnection.getInstance().getPst().setLong(2, data.getTypeId());
  }

  @Override
  public void defineDeleteData(AccountType data) throws SQLException {
    DbConnection.getInstance()
        .setPst(
            DbConnection.getInstance()
                .getCon()
                .prepareStatement(QueryConstants.ACCOUNT_TYPE_DELETE));
    DbConnection.getInstance().getPst().setLong(1, data.getTypeId());
  }

  @Override
  public List<?> list() {
    List<AccountType> lu = new ArrayList<>();
    try {
      DbConnection.getInstance()
          .setPst(
              DbConnection.getInstance()
                  .getCon()
                  .prepareStatement(QueryConstants.ACCOUNT_TYPE_LIST));
      DbConnection.getInstance().setRs(DbConnection.getInstance().getPst().executeQuery());
      while (DbConnection.getInstance().getRs().next()) {
        AccountType accountType =
            AccountType.builder().name(DbConnection.getInstance().getRs().getString(2)).build();
        lu.add(accountType);
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      DbConnection.getInstance().closeQuery();
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
      DbConnection.getInstance()
          .setPst(
              DbConnection.getInstance()
                  .getCon()
                  .prepareStatement(QueryConstants.ACCOUNT_TYPE_SEARCH));
      DbConnection.getInstance().getPst().setString(1, key);
      DbConnection.getInstance().setRs(DbConnection.getInstance().getPst().executeQuery());
      while (DbConnection.getInstance().getRs().next()) {
        accountType =
            AccountType.builder()
                .typeId(DbConnection.getInstance().getRs().getLong(1))
                .name(DbConnection.getInstance().getRs().getString(2))
                .build();
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      DbConnection.getInstance().closeQuery();
    }
    return accountType;
  }
}
