package com.gsdd.keymanager.lang;

import java.util.Locale;
import java.util.ResourceBundle;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KeyManagerLanguage {

  private static final String BUNDLE_RESOURCE = "msg/messages";
  // Init bundles
  public static final String BUTTON_LOGIN = "button.login";
  public static final String BUTTON_SAVE = "button.save";
  public static final String BUTTON_UPDATE = "button.update";
  public static final String BUTTON_DELETE = "button.delete";
  public static final String BUTTON_SEARCH = "button.search";
  public static final String BUTTON_BACK = "button.back";
  public static final String JOP_TITLE_EXPORT = "jop.title.export";
  public static final String JOP_TITLE_SUCCESS = "jop.title.success";
  public static final String JOP_TITLE_ERROR = "jop.title.error";
  public static final String JOP_TITLE_SEARCH = "jop.title.search";
  public static final String JOP_TITLE_QUERY = "jop.title.query";
  public static final String JOP_PASS = "jop.pass";
  public static final String JOP_PASS_OK = "jop.pass.ok";
  public static final String JOP_PASS_CANCEL = "jop.pass.cancel";
  public static final String JOP_PASS_MSG = "jop.pass.msg";
  public static final String JOP_EXPORT_SUCCESS = "jop.export.success";
  public static final String LABEL_USER = "label.user";
  public static final String LABEL_PASS = "label.pass";
  public static final String LABEL_LOGGED = "label.logged";
  public static final String LABEL_U_FN = "label.user.fn";
  public static final String LABEL_U_LN = "label.user.ln";
  public static final String LABEL_U_USER = "label.user.user";
  public static final String LABEL_U_PASS = "label.user.pass";
  public static final String LABEL_U_ROLE = "label.user.role";
  public static final String LABEL_AL_ACCOUNT = "label.al.account";
  public static final String LABEL_AL_USER = "label.al.user";
  public static final String LABEL_AL_PASS = "label.al.pass";
  public static final String LABEL_AL_TYPE = "label.al.type";
  public static final String LABEL_AL_URL = "label.al.url";
  public static final String LABEL_T_NAME = "label.type.name";
  public static final String MENU_ADMON = "menu.admon";
  public static final String MENU_INFO = "menu.info";
  public static final String MENU_SESSION = "menu.session";
  public static final String MENU_ITEM_ACCOUNT_TYPE = "menu.item.account.type";
  public static final String MENU_ITEM_ACCOUNT_USER = "menu.item.account.user";
  public static final String MENU_ITEM_EXIT = "menu.item.exit";
  public static final String MENU_ITEM_EXPORT = "menu.item.export";
  public static final String MENU_ITEM_INFO = "menu.item.info";
  public static final String MENU_ITEM_SESSION = "menu.item.session";
  public static final String MENU_ITEM_USER = "menu.item.user";
  public static final String MSG_INFO_EXIT = "msg.info.exit";
  public static final String MSG_INFO_UPDATE = "msg.info.update";
  public static final String MSG_INFO_DELETE = "msg.info.delete";
  public static final String MSG_INFO_SAVE = "msg.info.save";
  public static final String MSG_INFO_EXPORT = "msg.info.export";
  public static final String MSG_INFO_SUGGESTION = "msg.info.suggestion";
  public static final String MSG_INFO_XLS = "msg.info.xls";
  public static final String MSG_ERROR_USER_NE = "msg.error.user.ne";
  public static final String MSG_ERROR_LOGIN = "msg.error.login";
  public static final String MSG_ERROR_XLS = "msg.error.xls";
  public static final String MSG_ERROR_EXPORT = "msg.error.export";
  public static final String MSG_ERROR_ADMIN = "msg.error.admin";
  public static final String MSG_ERROR_PASS = "msg.error.pass";
  public static final String MSG_ERROR_DATA = "msg.error.data";
  public static final String MSG_ERROR_GRAL = "msg.error.gral";
  public static final String TBL_FN = "tbl.fn";
  public static final String TBL_LN = "tbl.ln";
  public static final String TBL_USER = "tbl.user";
  public static final String TBL_ROLE = "tbl.role";
  public static final String TBL_ACCOUNT = "tbl.account";
  public static final String TBL_ACCOUNT_TYPE = "tbl.account.type";
  public static final String TBL_ACCOUNT_USER = "tbl.account.user";
  public static final String TBL_ACCOUNT_PASS = "tbl.account.pass";
  public static final String TBL_ACCOUNT_URL = "tbl.account.url";
  public static final String TBL_ACCOUNT_DATE = "tbl.account.date";
  public static final String TBL_ACCOUNT_SUGGESTION = "tbl.account.suggestion";
  public static final String TBL_TYPE_NAME = "tbl.type.name";
  public static final String TEXT_INFO_VERSION = "text.info.version";
  public static final String TEXT_INFO_AUTHOR = "text.info.author";
  public static final String TEXT_INFO_CP = "text.info.cp";
  public static final String TITLE_MAIN = "title.main";
  public static final String TITLE_INIT = "title.init";
  public static final String TITLE_ACCOUNT_LOGIN = "title.account.login";
  public static final String TITLE_ACCOUNT_TYPE = "title.account.type";
  public static final String TITLE_LOGIN = "title.login";
  public static final String TITLE_USER = "title.user";
  public static final String TITLE_FILECHOOSER = "title.export.location";
  public static final String TOOL_ALFA = "tool.alfa";
  public static final String TOOL_TEXT = "tool.text";
  public static final String TOOL_SHOW = "tool.show";
  public static final String TOOL_HIDE = "tool.hide";

  private static ResourceBundle bundle;

  public static void initBundle(Locale locale) {
    try {
      bundle = ResourceBundle.getBundle(BUNDLE_RESOURCE, locale);
    } catch (Exception e) {
      bundle = ResourceBundle.getBundle(BUNDLE_RESOURCE, Locale.ENGLISH);
    }
  }

  public static String getMessageByLocale(String property) {
    return bundle.getString(property);
  }
}
