package com.gsdd.keymanager.view;

import com.gsdd.keymanager.constants.KeyManagerConstants;
import com.gsdd.keymanager.lang.KeyManagerLanguage;
import java.io.Serial;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
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
public class LoginView extends AbstractCommonView {

  @Serial private static final long serialVersionUID = 1L;
  private static final int POS_INI_X_LABEL = 100;
  private static final int POS_INI_Y_LABEL = 100;
  private static final int POS_INI_X_TEXT = 210;
  private static final int SPACE_Y = 30;
  private static final int COL_LENGTH = 16;

  private JLabel labelUser;
  private JLabel labelPass;

  private JTextField textUser;
  private JPasswordField textPass;

  private JButton loginButton;

  public LoginView() {
    setLayout(null);
    initLabels();
    initFields();
    initButtons();
    initBackGround(KeyManagerConstants.IMAGE_PPAL);
  }

  private void initLabels() {
    labelUser =
        addLabel(
            KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_USER),
            POS_INI_X_LABEL,
            POS_INI_Y_LABEL);
    labelPass =
        addLabel(
            KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_PASS),
            POS_INI_X_LABEL,
            POS_INI_Y_LABEL + SPACE_Y);
  }

  private void initFields() {
    textUser = new JTextField();
    textUser.setBounds(POS_INI_X_TEXT, POS_INI_Y_LABEL, DEF_WIDTH, DEF_HEIGHT);
    textUser.setColumns(COL_LENGTH);
    add(textUser);

    textPass = new JPasswordField();
    textPass.setBounds(POS_INI_X_TEXT, POS_INI_Y_LABEL + SPACE_Y, DEF_WIDTH, DEF_HEIGHT);
    textPass.setColumns(COL_LENGTH);
    add(textPass);
  }

  private void initButtons() {
    loginButton =
        addButton(
            KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.BUTTON_LOGIN),
            POS_INI_X_TEXT,
            POS_INI_Y_LABEL + (SPACE_Y * 2));
  }
}
