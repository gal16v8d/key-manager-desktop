package co.com.gsdd.keymanager.lang;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KeyManagerLanguage {

	private static final String BUNDLE_RESOURCE = "msg/messages";
	// Init bundles
	public static final String MENU_ADMON = "menu.admon";
	public static final String MENU_INFO = "menu.info";
	public static final String MENU_SESSION = "menu.session";

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
