package com.gsdd.keymanager.constants;

import com.gsdd.constants.GralConstants;
import com.gsdd.keymanager.lang.KeyManagerLanguage;
import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constantes asociadas al modelo del programa.
 *
 * @author Great System Development Dynamic <GSDD> <br>
 *     Alexander Galvis Grisales <br>
 *     alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KeyManagerConstants {

  public static final String DERBY_CONNECTION = "org.apache.derby.jdbc.EmbeddedDriver";
  public static final String DERBY_DB_NAME =
      "." + File.separator + "kmgr" + File.separator + "kmgr.DB";
  public static final String DERBY_LOCATION = "jdbc:derby:" + DERBY_DB_NAME + ";";
  public static final String DERBY_CREATE = "create=true";
  public static final String DERBY_SHUTDOWN = "shutdown=true";
  public static final String DERBY_MAIN_TABLE = "ACCOUNT";
  public static final String DERBY_MAIN_SCHEMA = "APP";
  public static final String DERBY_DATE_FORMAT = "yyyy-MM-dd";

  public static final String QUERY_PROPS = "/query/query.properties";
  public static final String IMAGE_ICON = "/images/KeyMgr-Icon.png";
  public static final String IMAGE_CXU = "/images/KeyMgr-CXU.png";
  public static final String IMAGE_CUENTA = "/images/KeyMgr-Cuenta.png";
  public static final String IMAGE_PPAL = "/images/KeyMgr-Ppal.png";
  public static final String IMAGE_USER = "/images/KeyMgr-User.png";

  public static final String LOCALE_ES = "ES";

  public static final String EXC_EXT1 = "xlsx";
  public static final String EXC_EXT2 = "xls";
  public static final String EXPORT_NAME = "Kmgr-Exported";

  public static final Integer TBL_PAGE_SIZE = 10;
  // After 180 days it suggest to update the password.
  public static final int TIME_TO_CHANGE = 180;

  public static final String MASK_TEXTO = "******";
  public static final char HIDE_TEXT = '*';

  protected static final String[] ACCOUNTXUSER_TBL_MODEL = {
    KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TBL_USER),
    KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TBL_ACCOUNT),
    KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TBL_ACCOUNT_USER),
    KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TBL_ACCOUNT_PASS),
    KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TBL_ACCOUNT_URL),
    KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TBL_ACCOUNT_DATE),
    KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TBL_ACCOUNT_SUGGESTION)
  };

  protected static final String[] USER_TBL_MODEL = {
    KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TBL_FN),
    KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TBL_LN),
    KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TBL_USER),
    KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TBL_ROLE)
  };
  
  /**
   * Generates a suggestion depending on difference in time, since password changes.
   *
   * @param currentDate
   * @param dbDate date in database.
   * @return suggestion depending on time.
   */
  public static final BiFunction<Date, Date, String> SHOW_SUGGESTION = (currentDate,
      dbDate) -> (TimeUnit.MILLISECONDS
          .toDays(currentDate.getTime() - dbDate.getTime()) >= TIME_TO_CHANGE)
              ? KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_INFO_SUGGESTION)
              : GralConstants.EMPTY;

  public static String[] getAccountXUserTableModel() {
    return ACCOUNTXUSER_TBL_MODEL;
  }

  public static String[] getUserTableModel() {
    return USER_TBL_MODEL;
  }

  /**
   * Date control formatter.
   *
   * @return formatter for derby dates.
   */
  public static SimpleDateFormat getFormater() {
    return new SimpleDateFormat(DERBY_DATE_FORMAT, new Locale(LOCALE_ES));
  }

}
