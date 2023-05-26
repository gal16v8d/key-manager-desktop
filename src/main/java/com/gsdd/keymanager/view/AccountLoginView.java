package com.gsdd.keymanager.view;

import com.gsdd.gui.util.JValidateTextField;
import com.gsdd.keymanager.constants.KeyManagerConstants;
import com.gsdd.keymanager.lang.KeyManagerLanguage;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Great System Development Dynamic <GSDD> <br>
 *     Alexander Galvis Grisales <br>
 *     alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@Getter
@Setter
public class AccountLoginView extends AbstractCrudView {

  private static final long serialVersionUID = 1L;
  private static final int INI_X_LABEL_C3 = 480;
  private static final int POS_Y_ROW_3 = 120;

  private JComboBox<String> comboUser;
  private JComboBox<String> comboType;
  private JValidateTextField textAccount;
  private JValidateTextField textUserName;
  private JPasswordField textPass;
  private JValidateTextField textUrl;

  private JButton showOrHideButton;

  public AccountLoginView() {
    setLayout(null);
    initLabels();
    initFields();
    initButtons();
    initTable();
    initBackGround(KeyManagerConstants.IMAGE_CXU);
  }

  private void initLabels() {
    addLabel(
        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_AL_ACCOUNT),
        INI_X_LABEL_C1,
        POS_Y_ROW_1);
    addLabel(
        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_U_USER),
        INI_X_LABEL_C2,
        POS_Y_ROW_1);
    addLabel(
        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_AL_USER),
        INI_X_LABEL_C1,
        POS_Y_ROW_2);
    addLabel(
        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_AL_PASS),
        INI_X_LABEL_C2,
        POS_Y_ROW_2);
    addLabel(
        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_AL_TYPE),
        INI_X_LABEL_C3,
        POS_Y_ROW_1);
    addLabel(
        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_AL_URL),
        INI_X_LABEL_C1,
        POS_Y_ROW_3);
  }

  private void initFields() {
    textAccount = addValidateTextField(POS_INI_X_TEXT_C1, POS_Y_ROW_1);

    comboUser = new JComboBox<>();
    comboUser.setBounds(POS_INI_X_TEXT_C2, POS_Y_ROW_1, DEF_WIDTH, DEF_HEIGHT);
    add(comboUser);
    
    comboType = new JComboBox<>();
    comboType.setBounds(POS_INI_X_TEXT_C3, POS_Y_ROW_1, DEF_WIDTH, DEF_HEIGHT);
    add(comboType);

    textUserName = addValidateTextField(POS_INI_X_TEXT_C1, POS_Y_ROW_2);

    textPass = new JPasswordField();
    textPass.setBounds(POS_INI_X_TEXT_C2, POS_Y_ROW_2, DEF_WIDTH, DEF_HEIGHT);
    add(textPass);

    textUrl = addValidateTextField(POS_INI_X_TEXT_C1, POS_Y_ROW_3);
  }

  private void initButtons() {
    showOrHideButton =
        addButton(
            KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_SHOW),
            POS_INI_X_TEXT_C3,
            POS_Y_ROW_2);
    initCommonButtons();
  }
}
