package com.gsdd.keymanager.service;

import com.gsdd.dbutil.DbConnection;
import com.gsdd.keymanager.constants.QueryConstants;
import com.gsdd.keymanager.entities.AccountType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

@Slf4j
public record AccountTypeService(DbConnection db) implements DbService<AccountType> {

  @Override
  public Logger getLogger() {
    return log;
  }

  @Override
  public DbConnection getDb() {
    return db;
  }

  @Override
  public void defineInsertData(AccountType data) throws SQLException {
    db.setPst(db.getCon().prepareStatement(QueryConstants.ACCOUNT_TYPE_INSERT));
    db.getPst().setLong(1, data.getTypeId());
    db.getPst().setString(2, data.getName());
  }

  @Override
  public void defineUpdateData(AccountType data, AccountType oldData) throws SQLException {
    db.setPst(db.getCon().prepareStatement(QueryConstants.ACCOUNT_TYPE_UPDATE));
    db.getPst().setString(1, data.getName());
    db.getPst().setLong(2, data.getTypeId());
  }

  @Override
  public void defineDeleteData(AccountType data) throws SQLException {
    db.setPst(db.getCon().prepareStatement(QueryConstants.ACCOUNT_TYPE_DELETE));
    db.getPst().setLong(1, data.getTypeId());
  }

  @Override
  public List<?> list() {
    List<AccountType> lu = new ArrayList<>();
    try {
      db.setPst(db.getCon().prepareStatement(QueryConstants.ACCOUNT_TYPE_LIST));
      db.setRs(db.getPst().executeQuery());
      while (db.getRs().next()) {
        AccountType accountType = AccountType.builder().name(db.getRs().getString(2)).build();
        lu.add(accountType);
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      db.closeQuery();
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
      db.setPst(db.getCon().prepareStatement(QueryConstants.ACCOUNT_TYPE_SEARCH));
      db.getPst().setString(1, key);
      db.setRs(db.getPst().executeQuery());
      while (db.getRs().next()) {
        accountType =
                AccountType.builder()
                        .typeId(db.getRs().getLong(1))
                        .name(db.getRs().getString(2))
                        .build();
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      db.closeQuery();
    }
    return accountType;
  }
}
