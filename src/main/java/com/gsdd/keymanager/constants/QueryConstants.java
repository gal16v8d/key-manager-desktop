package com.gsdd.keymanager.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Great System Development Dynamic <GSDD> <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QueryConstants {

  public static final String ACCOUNT_INSERT = """
      INSERT INTO account (account_id, first_name, last_name,
      login, password, role)
      VALUES (?, ?, ?, ?, ?, ?)
      """;
  public static final String ACCOUNT_DELETE = """
      DELETE FROM account 
      WHERE account_id = ? 
      """;
  public static final String ACCOUNT_UPDATE = """
      UPDATE account SET first_name = ?, last_name = ?,
      login = ?, password = ?
      WHERE account_id = ?
      """;
  private static final String ACCOUNT_SELECT =
      "SELECT account_id, first_name, last_name, login, password, role ";
  public static final String ACCOUNT_LIST = """
      %s 
      FROM account
      WHERE account_id = ?
      ORDER BY first_name, last_name, login
      """.formatted(ACCOUNT_SELECT);
  public static final String ACCOUNT_LIST_ADMIN = """
      %s 
      FROM account 
      ORDER BY first_name, last_name, login
      """.formatted(ACCOUNT_SELECT);
  public static final String ACCOUNT_SEARCH = """
      %s 
      FROM account 
      WHERE login = ?
      """.formatted(ACCOUNT_SELECT);
  public static final String VALIDATE_DATA = "SELECT COUNT(account_id) FROM account";
  public static final String ACCOUNT_LOGIN_INSERT = """
      INSERT INTO account_login (id, account_id, account_name,
      login, password, url, modification_date)
      VALUES (?, ?, ?, ?, ?, ?, ?)
      """;
  public static final String ACCOUNT_LOGIN_DELETE = """
      DELETE FROM account_login al
      WHERE al.account_name=? AND al.account_id=?
      """;
  public static final String ACCOUNT_LOGIN_UPDATE = """
      UPDATE account_login SET account_name = ?, account_id = ?,
      login = ?, password = ?, url = ?, modification_date = ?
      WHERE account_name = ? AND account_id = ?
      """;
  private static final String ACCOUNT_LOGIN_SELECT = """
      SELECT al.account_id, al.account_name, al.login,
      al.url, al.login, al.password, al.modification_date
      FROM account_login al
      JOIN account a ON (a.account_id = al.account_id)
      """;
  public static final String ACCOUNT_LOGIN_LIST = """
      %s 
      WHERE a.account_id = ? 
      ORDER BY a.login, al.account_name
      """.formatted(ACCOUNT_LOGIN_SELECT);
  public static final String ACCOUNT_LOGIN_LIST_ADMIN = """
      %s 
      ORDER BY a.login, al.account_name
      """.formatted(ACCOUNT_LOGIN_SELECT);
  public static final String ACCOUNT_LOGIN_SEARCH = """
      %s 
      WHERE al.account_name = ? AND al.account_id = ?
      """.formatted(ACCOUNT_LOGIN_SELECT);
}
