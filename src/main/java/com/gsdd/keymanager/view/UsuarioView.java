package com.gsdd.keymanager.view;

import com.gsdd.constants.GralConstants;
import com.gsdd.constants.RegexConstants;
import com.gsdd.gui.util.JValidateTextField;
import com.gsdd.keymanager.constants.KeyManagerConstants;
import com.gsdd.keymanager.lang.KeyManagerLanguage;
import javax.swing.JLabel;
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
public class UsuarioView extends AbstractCrudView {

  private static final long serialVersionUID = 1L;
  private static final int POS_Y_ROW_3 = 120;

  private JLabel labelPk;

  private JValidateTextField textPNombre;
  private JValidateTextField textPApellido;
  private JValidateTextField textUserName;
  private JPasswordField textPass;
  private JLabel labelVRol;

  public UsuarioView() {
    setLayout(null);
    initLabels();
    initFields();
    initButtons();
    initTable();
    initBackGround(KeyManagerConstants.IMAGE_USER);
  }

  private void initLabels() {
    // store pk
    labelPk = new JLabel(GralConstants.EMPTY);
    labelPk.setBounds(0, 0, 0, 0);
    labelPk.setVisible(false);

    addLabel(
        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_U_FN),
        INI_X_LABEL_C1,
        POS_Y_ROW_1);
    addLabel(
        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_U_LN),
        INI_X_LABEL_C2,
        POS_Y_ROW_1);
    addLabel(
        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_U_USER),
        INI_X_LABEL_C1,
        POS_Y_ROW_2);
    addLabel(
        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_U_PASS),
        INI_X_LABEL_C2,
        POS_Y_ROW_2);
    addLabel(
        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_U_ROLE),
        INI_X_LABEL_C1,
        POS_Y_ROW_3);
  }

  private void initFields() {
    textPNombre = addValidateTextField(POS_INI_X_TEXT_C1, POS_Y_ROW_1);
    textPNombre.setTextProperties(
        RegexConstants.TEXT,
        DEF_TEXT_SIZE,
        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_TEXT));

    textPApellido = addValidateTextField(POS_INI_X_TEXT_C2, POS_Y_ROW_1);
    textPApellido.setTextProperties(
        RegexConstants.TEXT,
        DEF_TEXT_SIZE,
        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_TEXT));

    textUserName = addValidateTextField(POS_INI_X_TEXT_C1, POS_Y_ROW_2);
    textUserName.setTextProperties(
        RegexConstants.ALFA,
        DEF_TEXT_SIZE,
        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_ALFA));

    textPass = new JPasswordField();
    textPass.setBounds(POS_INI_X_TEXT_C2, POS_Y_ROW_2, DEF_WIDTH, DEF_HEIGHT);
    add(textPass);

    labelVRol = addLabel("", POS_INI_X_TEXT_C1, POS_Y_ROW_3);
  }

  private void initButtons() {
    initCommonButtons();
  }
}
