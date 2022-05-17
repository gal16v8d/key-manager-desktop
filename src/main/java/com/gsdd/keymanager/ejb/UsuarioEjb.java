package com.gsdd.keymanager.ejb;

import com.gsdd.dbutil.DBConnection;
import com.gsdd.keymanager.constants.QueryConstants;
import com.gsdd.keymanager.entities.Usuario;
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
public class UsuarioEjb implements Ejb<Usuario> {

  @Override
  public Logger getLogger() {
    return log;
  }

  public Usuario login(String user, String pass) {
    Usuario u = null;
    try {
      DBConnection.getInstance()
          .setPst(DBConnection.getInstance().getCon().prepareStatement(QueryConstants.USER_SEARCH));
      DBConnection.getInstance().getPst().setString(1, user);
      DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
      while (DBConnection.getInstance().getRs().next()) {
        u = new Usuario();
        u.setCodigousuario(DBConnection.getInstance().getRs().getLong(1));
        u.setPassword(DBConnection.getInstance().getRs().getString(5));
        boolean valido = Objects.equals(pass, CypherKeyManager.decodeKM(u.getPassword()));
        if (!valido) {
          u = null;
        } else {
          u.setPrimerNombre(DBConnection.getInstance().getRs().getString(2));
          u.setPrimerApellido(DBConnection.getInstance().getRs().getString(3));
          u.setRol(DBConnection.getInstance().getRs().getLong(6));
        }
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      DBConnection.getInstance().closeQuery();
    }
    return u;
  }

  @Override
  public void defineInsertData(Usuario u) throws SQLException {
    DBConnection.getInstance()
        .setPst(DBConnection.getInstance().getCon().prepareStatement(QueryConstants.USER_INSERT));
    DBConnection.getInstance().getPst().setLong(1, u.getCodigousuario());
    DBConnection.getInstance().getPst().setString(2, u.getPrimerNombre());
    DBConnection.getInstance().getPst().setString(3, u.getPrimerApellido());
    DBConnection.getInstance().getPst().setString(4, u.getUsername());
    DBConnection.getInstance().getPst().setString(5, u.getPassword());
    DBConnection.getInstance().getPst().setString(6, RolEnum.USER.getCode());
  }

  @Override
  public void defineUpdateData(Usuario u, Usuario oldData) throws SQLException {
    DBConnection.getInstance()
        .setPst(DBConnection.getInstance().getCon().prepareStatement(QueryConstants.USER_UPDATE));
    DBConnection.getInstance().getPst().setString(1, u.getPrimerNombre());
    DBConnection.getInstance().getPst().setString(2, u.getPrimerApellido());
    DBConnection.getInstance().getPst().setString(3, u.getUsername());
    DBConnection.getInstance().getPst().setString(4, u.getPassword());
    DBConnection.getInstance().getPst().setLong(5, u.getCodigousuario());
  }

  @Override
  public void defineDeleteData(Usuario u) throws SQLException {
    DBConnection.getInstance()
        .setPst(DBConnection.getInstance().getCon().prepareStatement(QueryConstants.USER_DELETE));
    DBConnection.getInstance().getPst().setLong(1, u.getCodigousuario());
  }

  @Override
  public List<Usuario> list() {
    List<Usuario> lu = new ArrayList<>();
    try {
      if (Long.valueOf(RolEnum.ADMIN.getCode())
          .equals(SessionData.getInstance().getSessionDto().getRol())) {
        DBConnection.getInstance()
            .setPst(
                DBConnection.getInstance()
                    .getCon()
                    .prepareStatement(QueryConstants.USER_LIST_ADMIN));
      } else {
        DBConnection.getInstance()
            .setPst(DBConnection.getInstance().getCon().prepareStatement(QueryConstants.USER_LIST));
        DBConnection.getInstance()
            .getPst()
            .setLong(1, SessionData.getInstance().getSessionDto().getCodigousuario());
      }
      DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
      while (DBConnection.getInstance().getRs().next()) {
        Usuario u = new Usuario();
        u.setPrimerNombre(DBConnection.getInstance().getRs().getString(2));
        u.setPrimerApellido(DBConnection.getInstance().getRs().getString(3));
        u.setUsername(DBConnection.getInstance().getRs().getString(4));
        u.setRol(DBConnection.getInstance().getRs().getLong(6));
        lu.add(u);
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
          .equals(SessionData.getInstance().getSessionDto().getRol())) {
        DBConnection.getInstance()
            .setPst(
                DBConnection.getInstance()
                    .getCon()
                    .prepareStatement(QueryConstants.USER_LIST_ADMIN));
      } else {
        DBConnection.getInstance()
            .setPst(DBConnection.getInstance().getCon().prepareStatement(QueryConstants.USER_LIST));
        DBConnection.getInstance()
            .getPst()
            .setLong(1, SessionData.getInstance().getSessionDto().getCodigousuario());
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
  public Usuario search(String username) {
    Usuario u = null;
    try {
      DBConnection.getInstance()
          .setPst(DBConnection.getInstance().getCon().prepareStatement(QueryConstants.USER_SEARCH));
      DBConnection.getInstance().getPst().setString(1, username);
      DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
      while (DBConnection.getInstance().getRs().next()) {
        u = new Usuario();
        u.setCodigousuario(DBConnection.getInstance().getRs().getLong(1));
        u.setPrimerNombre(DBConnection.getInstance().getRs().getString(2));
        u.setPrimerApellido(DBConnection.getInstance().getRs().getString(3));
        u.setUsername(DBConnection.getInstance().getRs().getString(4));
        u.setPassword(DBConnection.getInstance().getRs().getString(5));
        u.setRol(DBConnection.getInstance().getRs().getLong(6));
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      DBConnection.getInstance().closeQuery();
    }
    return u;
  }
}
