package com.gsdd.keymanager.ejb;

import com.gsdd.dbutil.DBConnection;
import com.gsdd.keymanager.constants.QueryConstants;
import com.gsdd.keymanager.entities.Account;
import com.gsdd.keymanager.enums.RolEnum;
import com.gsdd.keymanager.util.CypherKeyManager;
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
      DBConnection.getInstance()
          .setPst(DBConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNT_SEARCH));
      DBConnection.getInstance().getPst().setString(1, user);
      DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
      while (DBConnection.getInstance().getRs().next()) {
        boolean valid = Objects.equals(pass,
            CypherKeyManager.decodeKM(DBConnection.getInstance().getRs().getString(5)));
        if (valid) {
          account = Account.builder().accountId(DBConnection.getInstance().getRs().getLong(1))
              .firstName(DBConnection.getInstance().getRs().getString(2))
              .lastName(DBConnection.getInstance().getRs().getString(3))
              .password(DBConnection.getInstance().getRs().getString(5))
              .role(DBConnection.getInstance().getRs().getLong(6))
              .build();
        }
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      DBConnection.getInstance().closeQuery();
    }
    return account;
  }

  @Override
  public void defineInsertData(Account u) throws SQLException {
    DBConnection.getInstance()
        .setPst(DBConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNT_INSERT));
    DBConnection.getInstance().getPst().setLong(1, u.getAccountId());
    DBConnection.getInstance().getPst().setString(2, u.getFirstName());
    DBConnection.getInstance().getPst().setString(3, u.getLastName());
    DBConnection.getInstance().getPst().setString(4, u.getLogin());
    DBConnection.getInstance().getPst().setString(5, u.getPassword());
    DBConnection.getInstance().getPst().setString(6, RolEnum.USER.getCode());
  }

  @Override
  public void defineUpdateData(Account u, Account oldData) throws SQLException {
    DBConnection.getInstance()
        .setPst(DBConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNT_UPDATE));
    DBConnection.getInstance().getPst().setString(1, u.getFirstName());
    DBConnection.getInstance().getPst().setString(2, u.getLastName());
    DBConnection.getInstance().getPst().setString(3, u.getLogin());
    DBConnection.getInstance().getPst().setString(4, u.getPassword());
    DBConnection.getInstance().getPst().setLong(5, u.getAccountId());
  }

  @Override
  public void defineDeleteData(Account u) throws SQLException {
    DBConnection.getInstance()
        .setPst(DBConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNT_DELETE));
    DBConnection.getInstance().getPst().setLong(1, u.getAccountId());
  }

  @Override
  public List<Account> list() {
    List<Account> lu = new ArrayList<>();
    try {
      if (Long.valueOf(RolEnum.ADMIN.getCode())
          .equals(SessionData.getInstance().getSessionDto().getRole())) {
        DBConnection.getInstance()
            .setPst(
                DBConnection.getInstance()
                    .getCon()
                    .prepareStatement(QueryConstants.ACCOUNT_LIST_ADMIN));
      } else {
        DBConnection.getInstance()
            .setPst(DBConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNT_LIST));
        DBConnection.getInstance()
            .getPst()
            .setLong(1, SessionData.getInstance().getSessionDto().getAccountId());
      }
      DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
      while (DBConnection.getInstance().getRs().next()) {
        Account account = Account.builder()
            .firstName(DBConnection.getInstance().getRs().getString(2))
            .lastName(DBConnection.getInstance().getRs().getString(3))
            .login(DBConnection.getInstance().getRs().getString(4))
            .role(DBConnection.getInstance().getRs().getLong(6))
            .build();
        lu.add(account);
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
    List<String> lu = new ArrayList<>();
    try {
      if (Long.valueOf(RolEnum.ADMIN.getCode())
          .equals(SessionData.getInstance().getSessionDto().getRole())) {
        DBConnection.getInstance()
            .setPst(
                DBConnection.getInstance()
                    .getCon()
                    .prepareStatement(QueryConstants.ACCOUNT_LIST_ADMIN));
      } else {
        DBConnection.getInstance()
            .setPst(DBConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNT_LIST));
        DBConnection.getInstance()
            .getPst()
            .setLong(1, SessionData.getInstance().getSessionDto().getAccountId());
      }
      DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
      while (DBConnection.getInstance().getRs().next()) {
        lu.add(DBConnection.getInstance().getRs().getString(4));
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      DBConnection.getInstance().closeQuery();
    }
    return lu;
  }

  @Override
  public Account search(String username) {
    Account account = null;
    try {
      DBConnection.getInstance()
          .setPst(DBConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNT_SEARCH));
      DBConnection.getInstance().getPst().setString(1, username);
      DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
      while (DBConnection.getInstance().getRs().next()) {
        account = Account.builder()
            .accountId(DBConnection.getInstance().getRs().getLong(1))
            .firstName(DBConnection.getInstance().getRs().getString(2))
            .lastName(DBConnection.getInstance().getRs().getString(3))
            .login(DBConnection.getInstance().getRs().getString(4))
            .password(DBConnection.getInstance().getRs().getString(5))
            .role(DBConnection.getInstance().getRs().getLong(6))
            .build();
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      DBConnection.getInstance().closeQuery();
    }
    return account;
  }
}
