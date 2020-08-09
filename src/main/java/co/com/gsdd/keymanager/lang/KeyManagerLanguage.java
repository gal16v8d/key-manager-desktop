package co.com.gsdd.keymanager.lang;

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
    public static final String LABEL_U_FN = "label.usuario.fn";
    public static final String LABEL_U_LN = "label.usuario.ln";
    public static final String LABEL_U_USER = "label.usuario.user";
    public static final String LABEL_U_PASS = "label.usuario.pass";
    public static final String LABEL_U_ROLE = "label.usuario.role";
    public static final String LABEL_CXU_ACCOUNT = "label.cxu.account";
    public static final String LABEL_CXU_USER = "label.cxu.user";
    public static final String LABEL_CXU_PASS = "label.cxu.pass";
    public static final String LABEL_CXU_URL = "label.cxu.url";
    public static final String MENU_ADMON = "menu.admon";
    public static final String MENU_INFO = "menu.info";
    public static final String MENU_SESSION = "menu.session";
    public static final String MENU_ITEM_EXPORT = "menu.item.export";
    public static final String MENU_ITEM_SESSION = "menu.item.session";
    public static final String MENU_ITEM_INFO = "menu.item.info";
    public static final String MENU_ITEM_SEARCH = "menu.item.search";
    public static final String MENU_ITEM_CUENTAXUSUARIO = "menu.item.cuentaxusuario";
    public static final String MENU_ITEM_USUARIO = "menu.item.usuario";
    public static final String MENU_ITEM_EXIT = "menu.item.exit";
    public static final String MSG_INFO_EXIT = "msg.info.exit";
    public static final String MSG_INFO_UPDATE = "msg.info.update";
    public static final String MSG_INFO_DELETE = "msg.info.delete";
    public static final String MSG_INFO_SAVE = "msg.info.save";
    public static final String MSG_INFO_EXPORT = "msg.info.export";
    public static final String TEXT_INFO_VERSION = "text.info.version";
    public static final String TEXT_INFO_AUTHOR = "text.info.author";
    public static final String TEXT_INFO_CP = "text.info.cp";
    public static final String TITLE_MAIN = "title.main";
    public static final String TITLE_INIT = "title.init";
    public static final String TITLE_CUENTAXUSER = "title.cxu";
    public static final String TITLE_LOGIN = "title.login";
    public static final String TITLE_USUARIO = "title.usuario";
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
