package com.gsdd.keymanager.ejb;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import com.gsdd.dbutil.DBConnection;
import com.gsdd.keymanager.constants.QueryConstants;
import com.gsdd.keymanager.entities.CuentaXUsuario;
import com.gsdd.keymanager.entities.dto.CuentaXUsuarioDto;
import com.gsdd.keymanager.enums.RolEnum;
import com.gsdd.keymanager.util.SessionData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CuentaXUsuarioEjb implements Ejb<CuentaXUsuario> {

  @Override
  public Logger getLogger() {
    return log;
  }

  @Override
  public void defineInsertData(CuentaXUsuario c) throws SQLException {
    DBConnection.getInstance().setPst(
        DBConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNTXUSER_INSERT));
    DBConnection.getInstance().getPst().setLong(1, c.getCodigocuenta());
    DBConnection.getInstance().getPst().setLong(2, c.getCodigousuario());
    DBConnection.getInstance().getPst().setString(3, c.getNombreCuenta());
    DBConnection.getInstance().getPst().setString(4, c.getUsername());
    DBConnection.getInstance().getPst().setString(5, c.getPassword());
    DBConnection.getInstance().getPst().setString(6, c.getUrl());
    DBConnection.getInstance().getPst().setDate(7, Date.valueOf(LocalDate.now()));
  }

  @Override
  public void defineUpdateData(CuentaXUsuario cxu, CuentaXUsuario old) throws SQLException {
    DBConnection.getInstance().setPst(
        DBConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNTXUSER_UPDATE));
    DBConnection.getInstance().getPst().setString(1, cxu.getNombreCuenta());
    DBConnection.getInstance().getPst().setLong(2,
        SessionData.getInstance().getSessionDto().getCodigousuario());
    DBConnection.getInstance().getPst().setString(3, cxu.getUsername());
    DBConnection.getInstance().getPst().setString(4, cxu.getPassword());
    DBConnection.getInstance().getPst().setString(5, cxu.getUrl());
    DBConnection.getInstance().getPst().setDate(6, Date.valueOf(LocalDate.now()));
    // where filter
    DBConnection.getInstance().getPst().setString(7, old.getNombreCuenta());
    DBConnection.getInstance().getPst().setLong(8, old.getCodigousuario());
  }

  @Override
  public void defineDeleteData(CuentaXUsuario cxu) throws SQLException {
    DBConnection.getInstance().setPst(
        DBConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNTXUSER_DELETE));
    DBConnection.getInstance().getPst().setString(1, cxu.getNombreCuenta());
    DBConnection.getInstance().getPst().setLong(2,
        SessionData.getInstance().getSessionDto().getCodigousuario());
  }

  public List<CuentaXUsuarioDto> list() {
    List<CuentaXUsuarioDto> lc = new ArrayList<>();
    try {
      if (SessionData.getInstance().getSessionDto().getRol()
          .equals(Long.valueOf(RolEnum.ADMIN.getCode()))) {
        DBConnection.getInstance().setPst(DBConnection.getInstance().getCon()
            .prepareStatement(QueryConstants.ACCOUNTXUSER_LIST_ADMIN));
      } else {
        DBConnection.getInstance().setPst(
            DBConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNTXUSER_LIST));
        DBConnection.getInstance().getPst().setLong(1,
            SessionData.getInstance().getSessionDto().getCodigousuario());
      }
      DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
      while (DBConnection.getInstance().getRs().next()) {
        CuentaXUsuarioDto c = new CuentaXUsuarioDto();
        c.setCodigousuario(DBConnection.getInstance().getRs().getLong(1));
        c.setNombrecuenta(DBConnection.getInstance().getRs().getString(2));
        c.setNombreusuario(DBConnection.getInstance().getRs().getString(3));
        c.setUrl(DBConnection.getInstance().getRs().getString(4));
        c.setUsuario(DBConnection.getInstance().getRs().getString(5));
        c.setPass(DBConnection.getInstance().getRs().getString(6));
        c.setFecha(DBConnection.getInstance().getRs().getDate(7));
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
      DBConnection.getInstance().setPst(
          DBConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNT_LIST));
      DBConnection.getInstance().getPst().setLong(1,
          SessionData.getInstance().getSessionDto().getCodigousuario());
      DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
      while (DBConnection.getInstance().getRs().next()) {
        lc.add(DBConnection.getInstance().getRs().getString(1));
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      DBConnection.getInstance().closeQuery();
    }
    return lc;
  }

  @Override
  public CuentaXUsuario search(String nombreCuenta) {
    CuentaXUsuario c = null;
    try {
      DBConnection.getInstance().setPst(
          DBConnection.getInstance().getCon().prepareStatement(QueryConstants.ACCOUNTXUSER_SEARCH));
      DBConnection.getInstance().getPst().setString(1, nombreCuenta);
      DBConnection.getInstance().getPst().setLong(2,
          SessionData.getInstance().getSessionDto().getCodigousuario());
      DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
      while (DBConnection.getInstance().getRs().next()) {
        c = new CuentaXUsuario();
        c.setCodigousuario(DBConnection.getInstance().getRs().getLong(1));
        c.setUsername(DBConnection.getInstance().getRs().getString(5));
        c.setPassword(DBConnection.getInstance().getRs().getString(6));
        c.setNombreCuenta(DBConnection.getInstance().getRs().getString(2));
        c.setUrl(DBConnection.getInstance().getRs().getString(4));
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
    } finally {
      DBConnection.getInstance().closeQuery();
    }
    return c;
  }

}
