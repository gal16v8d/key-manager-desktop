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
public record AccountLoginService(DbConnection db) implements DbService<AccountLogin> {

  @Override
  public Logger getLogger() {
    return log;
  }

  @Override
  public DbConnection getDb() {
    return db;
  }

  @Override
  public void defineInsertData(AccountLogin c) throws SQLException {
    db().setPst(db().getCon().prepareStatement(QueryConstants.ACCOUNT_LOGIN_INSERT));
    db().getPst().setLong(1, c.getId());
    db().getPst().setLong(2, c.getAccountId());
    db().getPst().setString(3, c.getAccountName());
    db().getPst().setString(4, c.getLogin());
    db().getPst().setString(5, c.getPassword());
    db().getPst().setString(6, c.getUrl());
    db().getPst().setDate(7, Date.valueOf(LocalDate.now()));
  }

  @Override
  public void defineUpdateData(AccountLogin cxu, AccountLogin old) throws SQLException {
    db().setPst(db().getCon().prepareStatement(QueryConstants.ACCOUNT_LOGIN_UPDATE));
    db().getPst().setString(1, cxu.getAccountName());
    db().getPst().setLong(2, SessionData.getInstance().getSessionDto().getAccountId());
    db().getPst().setString(3, cxu.getLogin());
    db().getPst().setString(4, cxu.getPassword());
    db().getPst().setString(5, cxu.getUrl());
    db().getPst().setDate(6, Date.valueOf(LocalDate.now()));
    // where filter
    db().getPst().setString(7, old.getAccountName());
    db().getPst().setLong(8, old.getAccountId());
  }

  @Override
  public void defineDeleteData(AccountLogin cxu) throws SQLException {
    db().setPst(db().getCon().prepareStatement(QueryConstants.ACCOUNT_LOGIN_DELETE));
    db().getPst().setString(1, cxu.getAccountName());
    db().getPst().setLong(2, SessionData.getInstance().getSessionDto().getAccountId());
  }

  public List<AccountLoginDto> list() {
    List<AccountLoginDto> lc = new ArrayList<>();
    try {
      if (SessionData.getInstance()
          .getSessionDto()
          .getRole()
          .equals(Long.valueOf(RolEnum.ADMIN.getCode()))) {
        db().setPst(db().getCon().prepareStatement(QueryConstants.ACCOUNT_LOGIN_LIST_ADMIN));
      } else {
        db().setPst(db().getCon().prepareStatement(QueryConstants.ACCOUNT_LOGIN_LIST));
        db().getPst().setLong(1, SessionData.getInstance().getSessionDto().getAccountId());
      }
      db().setRs(db().getPst().executeQuery());
      while (db().getRs().next()) {
        AccountLoginDto c =
            AccountLoginDto.builder()
                .accountId(db().getRs().getLong(1))
                .accountName(db().getRs().getString(2))
                .accountType(db().getRs().getString(3))
                .sessionLogin(db().getRs().getString(4))
                .url(db().getRs().getString(5))
                .login(db().getRs().getString(6))
                .pass(db().getRs().getString(7))
                .modificationDate(db().getRs().getDate(8))
                .build();
        lc.add(c);
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      db().closeQuery();
    }
    return lc;
  }

  @Override
  public List<String> suggest() {
    List<String> lc = new ArrayList<>();
    try {
      db().setPst(db().getCon().prepareStatement(QueryConstants.ACCOUNT_LOGIN_LIST));
      db().getPst().setLong(1, SessionData.getInstance().getSessionDto().getAccountId());
      db().setRs(db().getPst().executeQuery());
      while (db().getRs().next()) {
        lc.add(db().getRs().getString(2));
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      db().closeQuery();
    }
    return lc;
  }

  @Override
  public AccountLogin search(String accountName) {
    AccountLogin accountLogin = null;
    try {
      db().setPst(db().getCon().prepareStatement(QueryConstants.ACCOUNT_LOGIN_SEARCH));
      db().getPst().setString(1, accountName);
      db().getPst().setLong(2, SessionData.getInstance().getSessionDto().getAccountId());
      db().setRs(db().getPst().executeQuery());
      while (db().getRs().next()) {
        accountLogin =
            AccountLogin.builder()
                .accountId(db().getRs().getLong(1))
                .login(db().getRs().getString(6))
                .password(db().getRs().getString(7))
                .accountName(db().getRs().getString(2))
                .url(db().getRs().getString(5))
                .modificationDate(db().getRs().getDate(8))
                .typeId(db().getRs().getLong(3))
                .build();
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      db().closeQuery();
    }
    return accountLogin;
  }
}
