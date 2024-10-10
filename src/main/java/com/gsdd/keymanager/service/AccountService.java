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
public class AccountService implements DbService<Account> {

  @Override
  public Logger getLogger() {
    return log;
  }

  public Account login(String user, String pass) {
    Account account = null;
    try {
      DbConnection.getInstance()
          .setPst(
              DbConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNT_SEARCH));
      DbConnection.getInstance().getPst().setString(1, user);
      DbConnection.getInstance().setRs(DbConnection.getInstance().getPst().executeQuery());
      while (DbConnection.getInstance().getRs().next()) {
        boolean valid =
            Objects.equals(
                pass,
                CipherKeyManager.DECIPHER.apply(DbConnection.getInstance().getRs().getString(5)));
        if (valid) {
          account =
              Account.builder()
                  .accountId(DbConnection.getInstance().getRs().getLong(1))
                  .firstName(DbConnection.getInstance().getRs().getString(2))
                  .lastName(DbConnection.getInstance().getRs().getString(3))
                  .password(DbConnection.getInstance().getRs().getString(5))
                  .role(DbConnection.getInstance().getRs().getLong(6))
                  .build();
        }
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      DbConnection.getInstance().closeQuery();
    }
    return account;
  }

  @Override
  public void defineInsertData(Account u) throws SQLException {
    DbConnection.getInstance()
        .setPst(
            DbConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNT_INSERT));
    DbConnection.getInstance().getPst().setLong(1, u.getAccountId());
    DbConnection.getInstance().getPst().setString(2, u.getFirstName());
    DbConnection.getInstance().getPst().setString(3, u.getLastName());
    DbConnection.getInstance().getPst().setString(4, u.getLogin());
    DbConnection.getInstance().getPst().setString(5, u.getPassword());
    DbConnection.getInstance().getPst().setString(6, RolEnum.USER.getCode());
  }

  @Override
  public void defineUpdateData(Account u, Account oldData) throws SQLException {
    DbConnection.getInstance()
        .setPst(
            DbConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNT_UPDATE));
    DbConnection.getInstance().getPst().setString(1, u.getFirstName());
    DbConnection.getInstance().getPst().setString(2, u.getLastName());
    DbConnection.getInstance().getPst().setString(3, u.getLogin());
    DbConnection.getInstance().getPst().setString(4, u.getPassword());
    DbConnection.getInstance().getPst().setLong(5, u.getAccountId());
  }

  @Override
  public void defineDeleteData(Account u) throws SQLException {
    DbConnection.getInstance()
        .setPst(
            DbConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNT_DELETE));
    DbConnection.getInstance().getPst().setLong(1, u.getAccountId());
  }

  @Override
  public List<Account> list() {
    List<Account> lu = new ArrayList<>();
    try {
      if (Long.valueOf(RolEnum.ADMIN.getCode())
          .equals(SessionData.getInstance().getSessionDto().getRole())) {
        DbConnection.getInstance()
            .setPst(
                DbConnection.getInstance()
                    .getCon()
                    .prepareStatement(QueryConstants.ACCOUNT_LIST_ADMIN));
      } else {
        DbConnection.getInstance()
            .setPst(
                DbConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNT_LIST));
        DbConnection.getInstance()
            .getPst()
            .setLong(1, SessionData.getInstance().getSessionDto().getAccountId());
      }
      DbConnection.getInstance().setRs(DbConnection.getInstance().getPst().executeQuery());
      while (DbConnection.getInstance().getRs().next()) {
        Account account =
            Account.builder()
                .firstName(DbConnection.getInstance().getRs().getString(2))
                .lastName(DbConnection.getInstance().getRs().getString(3))
                .login(DbConnection.getInstance().getRs().getString(4))
                .role(DbConnection.getInstance().getRs().getLong(6))
                .build();
        lu.add(account);
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
    List<String> lu = new ArrayList<>();
    try {
      if (Long.valueOf(RolEnum.ADMIN.getCode())
          .equals(SessionData.getInstance().getSessionDto().getRole())) {
        DbConnection.getInstance()
            .setPst(
                DbConnection.getInstance()
                    .getCon()
                    .prepareStatement(QueryConstants.ACCOUNT_LIST_ADMIN));
      } else {
        DbConnection.getInstance()
            .setPst(
                DbConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNT_LIST));
        DbConnection.getInstance()
            .getPst()
            .setLong(1, SessionData.getInstance().getSessionDto().getAccountId());
      }
      DbConnection.getInstance().setRs(DbConnection.getInstance().getPst().executeQuery());
      while (DbConnection.getInstance().getRs().next()) {
        lu.add(DbConnection.getInstance().getRs().getString(4));
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      DbConnection.getInstance().closeQuery();
    }
    return lu;
  }

  @Override
  public Account search(String username) {
    Account account = null;
    try {
      DbConnection.getInstance()
          .setPst(
              DbConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNT_SEARCH));
      DbConnection.getInstance().getPst().setString(1, username);
      DbConnection.getInstance().setRs(DbConnection.getInstance().getPst().executeQuery());
      while (DbConnection.getInstance().getRs().next()) {
        account =
            Account.builder()
                .accountId(DbConnection.getInstance().getRs().getLong(1))
                .firstName(DbConnection.getInstance().getRs().getString(2))
                .lastName(DbConnection.getInstance().getRs().getString(3))
                .login(DbConnection.getInstance().getRs().getString(4))
                .password(DbConnection.getInstance().getRs().getString(5))
                .role(DbConnection.getInstance().getRs().getLong(6))
                .build();
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      DbConnection.getInstance().closeQuery();
    }
    return account;
  }
}
