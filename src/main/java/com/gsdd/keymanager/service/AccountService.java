package com.gsdd.keymanager.service;

import com.gsdd.dbutil.DbConnection;
import com.gsdd.keymanager.constants.QueryConstants;
import com.gsdd.keymanager.entities.Account;
import com.gsdd.keymanager.enums.RolEnum;
import com.gsdd.keymanager.util.CipherKeyManager;
import com.gsdd.keymanager.util.SessionData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

/**
 * @author Great System Development Dynamic <GSDD> <br>
 *     Alexander Galvis Grisales <br>
 *     alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@Slf4j
public record AccountService(DbConnection db) implements DbService<Account> {

  @Override
  public Logger getLogger() {
    return log;
  }

  @Override
  public DbConnection getDb() {
    return db;
  }

  public Account login(String user, String pass) {
    Account account = null;
    try {
      db.setPst(db.getCon().prepareStatement(QueryConstants.ACCOUNT_SEARCH));
      db.getPst().setString(1, user);
      db.setRs(db.getPst().executeQuery());
      while (db.getRs().next()) {
        boolean valid =
            Objects.equals(pass, CipherKeyManager.DECIPHER.apply(db.getRs().getString(5)));
        if (valid) {
          account =
              Account.builder()
                  .accountId(db.getRs().getLong(1))
                  .firstName(db.getRs().getString(2))
                  .lastName(db.getRs().getString(3))
                  .password(db.getRs().getString(5))
                  .role(db.getRs().getLong(6))
                  .build();
        }
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      db.closeQuery();
    }
    return account;
  }

  @Override
  public void defineInsertData(Account u) throws SQLException {
    db.setPst(db.getCon().prepareStatement(QueryConstants.ACCOUNT_INSERT));
    db.getPst().setLong(1, u.getAccountId());
    db.getPst().setString(2, u.getFirstName());
    db.getPst().setString(3, u.getLastName());
    db.getPst().setString(4, u.getLogin());
    db.getPst().setString(5, u.getPassword());
    db.getPst().setString(6, RolEnum.USER.getCode());
  }

  @Override
  public void defineUpdateData(Account u, Account oldData) throws SQLException {
    db.setPst(db.getCon().prepareStatement(QueryConstants.ACCOUNT_UPDATE));
    db.getPst().setString(1, u.getFirstName());
    db.getPst().setString(2, u.getLastName());
    db.getPst().setString(3, u.getLogin());
    db.getPst().setString(4, u.getPassword());
    db.getPst().setLong(5, u.getAccountId());
  }

  @Override
  public void defineDeleteData(Account u) throws SQLException {
    db.setPst(db.getCon().prepareStatement(QueryConstants.ACCOUNT_DELETE));
    db.getPst().setLong(1, u.getAccountId());
  }

  @Override
  public List<Account> list() {
    List<Account> lu = new ArrayList<>();
    try {
      if (Long.valueOf(RolEnum.ADMIN.getCode())
          .equals(SessionData.getInstance().getSessionDto().getRole())) {
        db.setPst(db.getCon().prepareStatement(QueryConstants.ACCOUNT_LIST_ADMIN));
      } else {
        db.setPst(db.getCon().prepareStatement(QueryConstants.ACCOUNT_LIST));
        db.getPst().setLong(1, SessionData.getInstance().getSessionDto().getAccountId());
      }
      db.setRs(db.getPst().executeQuery());
      while (db.getRs().next()) {
        Account account =
            Account.builder()
                .firstName(db.getRs().getString(2))
                .lastName(db.getRs().getString(3))
                .login(db.getRs().getString(4))
                .role(db.getRs().getLong(6))
                .build();
        lu.add(account);
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
    List<String> lu = new ArrayList<>();
    try {
      if (Long.valueOf(RolEnum.ADMIN.getCode())
          .equals(SessionData.getInstance().getSessionDto().getRole())) {
        db.setPst(db.getCon().prepareStatement(QueryConstants.ACCOUNT_LIST_ADMIN));
      } else {
        db.setPst(db.getCon().prepareStatement(QueryConstants.ACCOUNT_LIST));
        db.getPst().setLong(1, SessionData.getInstance().getSessionDto().getAccountId());
      }
      db.setRs(db.getPst().executeQuery());
      while (db.getRs().next()) {
        lu.add(db.getRs().getString(4));
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      db.closeQuery();
    }
    return lu;
  }

  @Override
  public Account search(String username) {
    Account account = null;
    try {
      db.setPst(db.getCon().prepareStatement(QueryConstants.ACCOUNT_SEARCH));
      db.getPst().setString(1, username);
      db.setRs(db.getPst().executeQuery());
      while (db.getRs().next()) {
        account =
            Account.builder()
                .accountId(db.getRs().getLong(1))
                .firstName(db.getRs().getString(2))
                .lastName(db.getRs().getString(3))
                .login(db.getRs().getString(4))
                .password(db.getRs().getString(5))
                .role(db.getRs().getLong(6))
                .build();
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      db.closeQuery();
    }
    return account;
  }
}
