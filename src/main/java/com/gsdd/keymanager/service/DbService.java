package com.gsdd.keymanager.service;

import com.gsdd.dbutil.DbConnection;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;

public interface DbService<T extends Serializable> {

  Logger getLogger();

  DbConnection getDb();

  void defineInsertData(T data) throws SQLException;

  void defineUpdateData(T data, T oldData) throws SQLException;

  void defineDeleteData(T data) throws SQLException;

  default boolean save(T value) {
    DbOperation<T> dbUpdate = (newData, oldData) -> defineInsertData(newData);
    return dbUpdate.executeDbUpdate(getDb(), value, null, getLogger());
  }

  default boolean update(T value, T oldValue) {
    DbOperation<T> dbUpdate = this::defineUpdateData;
    return dbUpdate.executeDbUpdate(getDb(), value, oldValue, getLogger());
  }

  default boolean delete(T data) {
    DbOperation<T> dbUpdate = (newData, oldData) -> defineDeleteData(newData);
    return dbUpdate.executeDbUpdate(getDb(), data, null, getLogger());
  }

  List<?> list();

  List<String> suggest();

  T search(String key);

  @FunctionalInterface
  interface DbOperation<T extends Serializable> {

    void defineQueryBody(T data, T oldData) throws SQLException;

    default boolean executeDbUpdate(DbConnection db, T data, T oldData, Logger log) {
      boolean result = false;
      try {
        defineQueryBody(data, oldData);
        db.getPst().executeUpdate();
        result = true;
      } catch (SQLException e) {
        log.error(e.getMessage(), e);
      } finally {
        db.closeQuery();
      }
      return result;
    }
  }
}
