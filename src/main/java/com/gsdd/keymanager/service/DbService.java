package com.gsdd.keymanager.service;

import com.gsdd.dbutil.DBConnection;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface DbService<T extends Serializable> {

  Logger getLogger();

  void defineInsertData(T data) throws SQLException;

  void defineUpdateData(T data, T oldData) throws SQLException;

  void defineDeleteData(T data) throws SQLException;

  default boolean save(T value) {
    DbOperation<T> dbUpdate = this::defineInsertData;
    return dbUpdate.executeDbUpdate(value);
  }

  default boolean update(T value, T oldValue) {
    boolean retorno = false;
    try {
      defineUpdateData(value, oldValue);
      DBConnection.getInstance().getPst().executeUpdate();
      retorno = true;
    } catch (SQLException e) {
      getLogger().error(e.getMessage(), e);
    } finally {
      DBConnection.getInstance().closeQuery();
    }
    return retorno;
  }

  default boolean delete(T data) {
    DbOperation<T> dbUpdate = this::defineDeleteData;
    return dbUpdate.executeDbUpdate(data);
  }

  List<?> list();

  List<String> suggest();

  T search(String key);

  interface DbOperation<T extends Serializable> {

    void defineQueryBody(T data) throws SQLException;

    default boolean executeDbUpdate(T data) {
      boolean retorno = false;
      try {
        defineQueryBody(data);
        DBConnection.getInstance().getPst().executeUpdate();
        retorno = true;
      } catch (SQLException e) {
        LoggerFactory.getLogger(DbOperation.class).error(e.getMessage(), e);
      } finally {
        DBConnection.getInstance().closeQuery();
      }
      return retorno;
    }
  }
}
