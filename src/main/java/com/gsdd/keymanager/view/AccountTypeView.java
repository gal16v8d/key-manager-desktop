package com.gsdd.keymanager.view;

import com.gsdd.constants.GralConstants;
import com.gsdd.gui.util.JValidateTextField;
import com.gsdd.keymanager.constants.KeyManagerConstants;
import com.gsdd.keymanager.lang.KeyManagerLanguage;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountTypeView extends AbstractCrudView {

  private static final long serialVersionUID = -1108830552175862445L;

  private JLabel labelPk;

  private JComboBox<String> comboUser;
  private JValidateTextField textName;

  public AccountTypeView() {
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
        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_T_NAME),
        INI_X_LABEL_C1,
        POS_Y_ROW_1);
  }

  private void initFields() {
    textName = addValidateTextField(POS_INI_X_TEXT_C1, POS_Y_ROW_1);
  }
  
  private void initButtons() {
    initCommonButtons();
  }

}
