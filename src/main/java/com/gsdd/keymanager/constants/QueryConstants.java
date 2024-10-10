package com.gsdd.keymanager.constants;

import lombok.experimental.UtilityClass;

/**
 * @author Great System Development Dynamic <GSDD> <br>
 *     Alexander Galvis Grisales <br>
 *     alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@UtilityClass
public final class QueryConstants {

  // Account queries
  public static final String ACCOUNT_INSERT =
      """
      INSERT INTO account (account_id, first_name, last_name,
      login, password, role)
      VALUES (?, ?, ?, ?, ?, ?)
      """;
  public static final String ACCOUNT_DELETE =
      """
      DELETE FROM account
      WHERE account_id = ?
      """;
  public static final String ACCOUNT_UPDATE =
      """
      UPDATE account SET first_name = ?, last_name = ?,
      login = ?, password = ?
      WHERE account_id = ?
      """;
  private static final String ACCOUNT_SELECT =
      "SELECT account_id, first_name, last_name, login, password, role ";
  public static final String ACCOUNT_LIST =
      """
      %s
      FROM account
      WHERE account_id = ?
      ORDER BY first_name, last_name, login
      """
          .formatted(ACCOUNT_SELECT);
  public static final String ACCOUNT_LIST_ADMIN =
      """
      %s
      FROM account
      ORDER BY first_name, last_name, login
      """
          .formatted(ACCOUNT_SELECT);
  public static final String ACCOUNT_SEARCH =
      """
      %s
      FROM account
      WHERE login = ?
      """.formatted(ACCOUNT_SELECT);
  public static final String ACCOUNT_MAX =
      """
      SELECT MAX(account_id)
      FROM account
      """;
  // Account login queries
  public static final String ACCOUNT_LOGIN_INSERT =
      """
      INSERT INTO account_login (id, account_id, account_name,
      login, password, url, modification_date)
      VALUES (?, ?, ?, ?, ?, ?, ?)
      """;
  public static final String ACCOUNT_LOGIN_DELETE =
      """
      DELETE FROM account_login al
      WHERE al.account_name=? AND al.account_id=?
      """;
  public static final String ACCOUNT_LOGIN_UPDATE =
      """
      UPDATE account_login SET account_name = ?, account_id = ?,
      login = ?, password = ?, url = ?, modification_date = ?
      WHERE account_name = ? AND account_id = ?
      """;
  private static final String ACCOUNT_LOGIN_SELECT =
      """
      SELECT al.account_id, al.account_name, t.type_id, a.login,
      al.url, al.login, al.password, al.modification_date
      FROM account_login al
      LEFT JOIN account a ON (a.account_id = al.account_id)
      LEFT JOIN account_type t ON (al.type_id = t.type_id)
      """;
  public static final String ACCOUNT_LOGIN_LIST =
      """
      %s
      WHERE a.account_id = ?
      ORDER BY a.login, al.account_name
      """
          .formatted(ACCOUNT_LOGIN_SELECT);
  public static final String ACCOUNT_LOGIN_LIST_ADMIN =
      """
      %s
      ORDER BY a.login, al.account_name
      """
          .formatted(ACCOUNT_LOGIN_SELECT);
  public static final String ACCOUNT_LOGIN_SEARCH =
      """
      %s
      WHERE al.account_name = ? AND al.account_id = ?
      """
          .formatted(ACCOUNT_LOGIN_SELECT);
  public static final String ACCOUNT_LOGIN_MAX =
      """
      SELECT MAX(id)
      FROM account_login
      WHERE account_id = ?
      """;
  // Account type queries
  public static final String ACCOUNT_TYPE_INSERT =
      """
      INSERT INTO account_type (type_id, name)
      VALUES (?, ?)
      """;
  public static final String ACCOUNT_TYPE_DELETE =
      """
      DELETE FROM account_type
      WHERE type_id = ?
      """;
  public static final String ACCOUNT_TYPE_UPDATE =
      """
      UPDATE account_type SET name = ?
      WHERE type_id = ?
      """;
  private static final String ACCOUNT_TYPE_SELECT = "SELECT type_id, name ";
  public static final String ACCOUNT_TYPE_LIST =
      """
      %s
      FROM account_type
      ORDER BY name
      """
          .formatted(ACCOUNT_TYPE_SELECT);
  public static final String ACCOUNT_TYPE_SEARCH =
      """
      %s
      FROM account_type
      WHERE name = ?
      """
          .formatted(ACCOUNT_TYPE_SELECT);
  public static final String ACCOUNT_TYPE_MAX =
      """
      SELECT MAX(type_id)
      FROM account_type
      """;
  // Validation query
  public static final String VALIDATE_DATA = "SELECT COUNT(account_id) FROM account";
}
