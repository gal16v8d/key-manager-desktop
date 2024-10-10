package com.gsdd.keymanager.service;

import com.gsdd.dbutil.DbConnection;
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
    DbConnection.getInstance()
        .setPst(
            DbConnection.getInstance()
                .getCon()
                .prepareStatement(QueryConstants.ACCOUNT_LOGIN_INSERT));
    DbConnection.getInstance().getPst().setLong(1, c.getId());
    DbConnection.getInstance().getPst().setLong(2, c.getAccountId());
    DbConnection.getInstance().getPst().setString(3, c.getAccountName());
    DbConnection.getInstance().getPst().setString(4, c.getLogin());
    DbConnection.getInstance().getPst().setString(5, c.getPassword());
    DbConnection.getInstance().getPst().setString(6, c.getUrl());
    DbConnection.getInstance().getPst().setDate(7, Date.valueOf(LocalDate.now()));
  }

  @Override
  public void defineUpdateData(AccountLogin cxu, AccountLogin old) throws SQLException {
    DbConnection.getInstance()
        .setPst(
            DbConnection.getInstance()
                .getCon()
                .prepareStatement(QueryConstants.ACCOUNT_LOGIN_UPDATE));
    DbConnection.getInstance().getPst().setString(1, cxu.getAccountName());
    DbConnection.getInstance()
        .getPst()
        .setLong(2, SessionData.getInstance().getSessionDto().getAccountId());
    DbConnection.getInstance().getPst().setString(3, cxu.getLogin());
    DbConnection.getInstance().getPst().setString(4, cxu.getPassword());
    DbConnection.getInstance().getPst().setString(5, cxu.getUrl());
    DbConnection.getInstance().getPst().setDate(6, Date.valueOf(LocalDate.now()));
    // where filter
    DbConnection.getInstance().getPst().setString(7, old.getAccountName());
    DbConnection.getInstance().getPst().setLong(8, old.getAccountId());
  }

  @Override
  public void defineDeleteData(AccountLogin cxu) throws SQLException {
    DbConnection.getInstance()
        .setPst(
            DbConnection.getInstance()
                .getCon()
                .prepareStatement(QueryConstants.ACCOUNT_LOGIN_DELETE));
    DbConnection.getInstance().getPst().setString(1, cxu.getAccountName());
    DbConnection.getInstance()
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
        DbConnection.getInstance()
            .setPst(
                DbConnection.getInstance()
                    .getCon()
                    .prepareStatement(QueryConstants.ACCOUNT_LOGIN_LIST_ADMIN));
      } else {
        DbConnection.getInstance()
            .setPst(
                DbConnection.getInstance()
                    .getCon()
                    .prepareStatement(QueryConstants.ACCOUNT_LOGIN_LIST));
        DbConnection.getInstance()
            .getPst()
            .setLong(1, SessionData.getInstance().getSessionDto().getAccountId());
      }
      DbConnection.getInstance().setRs(DbConnection.getInstance().getPst().executeQuery());
      while (DbConnection.getInstance().getRs().next()) {
        AccountLoginDto c =
            AccountLoginDto.builder()
                .accountId(DbConnection.getInstance().getRs().getLong(1))
                .accountName(DbConnection.getInstance().getRs().getString(2))
                .accountType(DbConnection.getInstance().getRs().getString(3))
                .sessionLogin(DbConnection.getInstance().getRs().getString(4))
                .url(DbConnection.getInstance().getRs().getString(5))
                .login(DbConnection.getInstance().getRs().getString(6))
                .pass(DbConnection.getInstance().getRs().getString(7))
                .modificationDate(DbConnection.getInstance().getRs().getDate(8))
                .build();
        lc.add(c);
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      DbConnection.getInstance().closeQuery();
    }
    return lc;
  }

  @Override
  public List<String> suggest() {
    List<String> lc = new ArrayList<>();
    try {
      DbConnection.getInstance()
          .setPst(
              DbConnection.getInstance()
                  .getCon()
                  .prepareStatement(QueryConstants.ACCOUNT_LOGIN_LIST));
      DbConnection.getInstance()
          .getPst()
          .setLong(1, SessionData.getInstance().getSessionDto().getAccountId());
      DbConnection.getInstance().setRs(DbConnection.getInstance().getPst().executeQuery());
      while (DbConnection.getInstance().getRs().next()) {
        lc.add(DbConnection.getInstance().getRs().getString(2));
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      DbConnection.getInstance().closeQuery();
    }
    return lc;
  }

  @Override
  public AccountLogin search(String accountName) {
    AccountLogin accountLogin = null;
    try {
      DbConnection.getInstance()
          .setPst(
              DbConnection.getInstance()
                  .getCon()
                  .prepareStatement(QueryConstants.ACCOUNT_LOGIN_SEARCH));
      DbConnection.getInstance().getPst().setString(1, accountName);
      DbConnection.getInstance()
          .getPst()
          .setLong(2, SessionData.getInstance().getSessionDto().getAccountId());
      DbConnection.getInstance().setRs(DbConnection.getInstance().getPst().executeQuery());
      while (DbConnection.getInstance().getRs().next()) {
        accountLogin =
            AccountLogin.builder()
                .accountId(DbConnection.getInstance().getRs().getLong(1))
                .login(DbConnection.getInstance().getRs().getString(6))
                .password(DbConnection.getInstance().getRs().getString(7))
                .accountName(DbConnection.getInstance().getRs().getString(2))
                .url(DbConnection.getInstance().getRs().getString(5))
                .modificationDate(DbConnection.getInstance().getRs().getDate(8))
                .typeId(DbConnection.getInstance().getRs().getLong(3))
                .build();
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      DbConnection.getInstance().closeQuery();
    }
    return accountLogin;
  }
}
