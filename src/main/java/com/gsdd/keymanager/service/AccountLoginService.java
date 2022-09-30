package com.gsdd.keymanager.service;

import com.gsdd.dbutil.DBConnection;
import com.gsdd.keymanager.constants.QueryConstants;
import com.gsdd.keymanager.entities.AccountLogin;
import com.gsdd.keymanager.entities.dto.AccountLoginDto;
import com.gsdd.keymanager.enums.RolEnum;
import com.gsdd.keymanager.util.SessionData;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

@Slf4j
public class AccountLoginService implements DbService<AccountLogin> {

  @Override
  public Logger getLogger() {
    return log;
  }

  @Override
  public void defineInsertData(AccountLogin c) throws SQLException {
    DBConnection.getInstance()
        .setPst(
            DBConnection.getInstance()
                .getCon()
                .prepareStatement(QueryConstants.ACCOUNT_LOGIN_INSERT));
    DBConnection.getInstance().getPst().setLong(1, c.getId());
    DBConnection.getInstance().getPst().setLong(2, c.getAccountId());
    DBConnection.getInstance().getPst().setString(3, c.getAccountName());
    DBConnection.getInstance().getPst().setString(4, c.getLogin());
    DBConnection.getInstance().getPst().setString(5, c.getPassword());
    DBConnection.getInstance().getPst().setString(6, c.getUrl());
    DBConnection.getInstance().getPst().setDate(7, Date.valueOf(LocalDate.now()));
  }

  @Override
  public void defineUpdateData(AccountLogin cxu, AccountLogin old) throws SQLException {
    DBConnection.getInstance()
        .setPst(
            DBConnection.getInstance()
                .getCon()
                .prepareStatement(QueryConstants.ACCOUNT_LOGIN_UPDATE));
    DBConnection.getInstance().getPst().setString(1, cxu.getAccountName());
    DBConnection.getInstance()
        .getPst()
        .setLong(2, SessionData.getInstance().getSessionDto().getAccountId());
    DBConnection.getInstance().getPst().setString(3, cxu.getLogin());
    DBConnection.getInstance().getPst().setString(4, cxu.getPassword());
    DBConnection.getInstance().getPst().setString(5, cxu.getUrl());
    DBConnection.getInstance().getPst().setDate(6, Date.valueOf(LocalDate.now()));
    // where filter
    DBConnection.getInstance().getPst().setString(7, old.getAccountName());
    DBConnection.getInstance().getPst().setLong(8, old.getAccountId());
  }

  @Override
  public void defineDeleteData(AccountLogin cxu) throws SQLException {
    DBConnection.getInstance()
        .setPst(
            DBConnection.getInstance()
                .getCon()
                .prepareStatement(QueryConstants.ACCOUNT_LOGIN_DELETE));
    DBConnection.getInstance().getPst().setString(1, cxu.getAccountName());
    DBConnection.getInstance()
        .getPst()
        .setLong(2, SessionData.getInstance().getSessionDto().getAccountId());
  }

  public List<AccountLoginDto> list() {
    List<AccountLoginDto> lc = new ArrayList<>();
    try {
      if (SessionData.getInstance()
          .getSessionDto()
          .getRole()
          .equals(Long.valueOf(RolEnum.ADMIN.getCode()))) {
        DBConnection.getInstance()
            .setPst(
                DBConnection.getInstance()
                    .getCon()
                    .prepareStatement(QueryConstants.ACCOUNT_LOGIN_LIST_ADMIN));
      } else {
        DBConnection.getInstance()
            .setPst(
                DBConnection.getInstance()
                    .getCon()
                    .prepareStatement(QueryConstants.ACCOUNT_LOGIN_LIST));
        DBConnection.getInstance()
            .getPst()
            .setLong(1, SessionData.getInstance().getSessionDto().getAccountId());
      }
      DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
      while (DBConnection.getInstance().getRs().next()) {
        AccountLoginDto c =
            AccountLoginDto.builder().accountId(DBConnection.getInstance().getRs().getLong(1))
                .accountName(DBConnection.getInstance().getRs().getString(2))
                .sessionLogin(DBConnection.getInstance().getRs().getString(3))
                .url(DBConnection.getInstance().getRs().getString(4))
                .login(DBConnection.getInstance().getRs().getString(5))
                .pass(DBConnection.getInstance().getRs().getString(6))
                .modificationDate(DBConnection.getInstance().getRs().getDate(7)).build();
        lc.add(c);
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      DBConnection.getInstance().closeQuery();
    }
    return lc;
  }

  @Override
  public List<String> suggest() {
    List<String> lc = new ArrayList<>();
    try {
      DBConnection.getInstance()
          .setPst(
              DBConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNT_LOGIN_LIST));
      DBConnection.getInstance()
          .getPst()
          .setLong(1, SessionData.getInstance().getSessionDto().getAccountId());
      DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
      while (DBConnection.getInstance().getRs().next()) {
        lc.add(DBConnection.getInstance().getRs().getString(2));
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      DBConnection.getInstance().closeQuery();
    }
    return lc;
  }

  @Override
  public AccountLogin search(String nombreCuenta) {
    AccountLogin accountLogin = null;
    try {
      DBConnection.getInstance()
          .setPst(
              DBConnection.getInstance()
                  .getCon()
                  .prepareStatement(QueryConstants.ACCOUNT_LOGIN_SEARCH));
      DBConnection.getInstance().getPst().setString(1, nombreCuenta);
      DBConnection.getInstance()
          .getPst()
          .setLong(2, SessionData.getInstance().getSessionDto().getAccountId());
      DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
      while (DBConnection.getInstance().getRs().next()) {
        accountLogin = AccountLogin.builder()
            .accountId(DBConnection.getInstance().getRs().getLong(1))
            .login(DBConnection.getInstance().getRs().getString(5))
            .password(DBConnection.getInstance().getRs().getString(6))
            .accountName(DBConnection.getInstance().getRs().getString(2))
            .url(DBConnection.getInstance().getRs().getString(4))
            .build();
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      DBConnection.getInstance().closeQuery();
    }
    return accountLogin;
  }
}
