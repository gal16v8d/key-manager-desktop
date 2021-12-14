package com.gsdd.keymanager.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Great System Development Dynamic <GSDD> <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 * 
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QueryConstants {

  public static final String USER_INSERT =
      "INSERT INTO usuario(codigousuario,primer_nombre,primer_apellido,username,password,rol)"
          + " VALUES (?,?,?,?,?,?)";
  public static final String USER_DELETE = "DELETE FROM usuario WHERE codigousuario=?";
  public static final String USER_UPDATE =
      "UPDATE usuario SET primer_nombre = ?, primer_apellido = ?, "
          + "username = ?, password = ? WHERE codigousuario = ?";
  private static final String USER_SELECT =
      "SELECT codigousuario, primer_nombre, primer_apellido, username, password, rol ";
  public static final String USER_LIST = USER_SELECT
      + "FROM usuario WHERE codigousuario = ? ORDER BY primer_nombre, primer_apellido, username";
  public static final String USER_LIST_ADMIN =
      USER_SELECT + "FROM usuario ORDER BY primer_nombre, primer_apellido, username";
  public static final String USER_SEARCH = USER_SELECT + "FROM usuario WHERE username = ?";

  public static final String VALIDATE_DATA = "SELECT COUNT(codigousuario) FROM usuario";
  public static final String ACCOUNT_LIST =
      "SELECT cxu.nombrecuenta FROM cuentaxusuario cxu WHERE cxu.codigousuario = ? ORDER BY cxu.nombrecuenta ASC";
  public static final String ACCOUNTXUSER_INSERT =
      "INSERT INTO cuentaxusuario(codigocuenta,codigousuario,nombrecuenta,username,"
          + "password,url,fecha) VALUES (?,?,?,?,?,?,?)";
  public static final String ACCOUNTXUSER_DELETE =
      "DELETE FROM cuentaxusuario cxu WHERE cxu.nombrecuenta=? AND cxu.codigousuario=?";
  public static final String ACCOUNTXUSER_UPDATE =
      "UPDATE cuentaxusuario SET nombrecuenta = ?, codigousuario = ?, "
          + "username = ?, password = ?, url = ?, fecha = ? WHERE nombrecuenta = ? AND codigousuario = ?";
  private static final String ACCOUNTXUSER_SELECT =
      "SELECT cxu.codigousuario, cxu.nombrecuenta, u.username, cxu.url, "
          + "cxu.username, cxu.password, cxu.fecha "
          + "FROM cuentaxusuario cxu JOIN usuario u ON (u.codigousuario = cxu.codigousuario) ";
  public static final String ACCOUNTXUSER_LIST =
      ACCOUNTXUSER_SELECT + "WHERE u.codigousuario = ? ORDER BY u.username,cxu.nombrecuenta";
  public static final String ACCOUNTXUSER_LIST_ADMIN =
      ACCOUNTXUSER_SELECT + "ORDER BY u.username,cxu.nombrecuenta";
  public static final String ACCOUNTXUSER_SEARCH =
      ACCOUNTXUSER_SELECT + "WHERE cxu.nombrecuenta = ? AND cxu.codigousuario = ?";

}
