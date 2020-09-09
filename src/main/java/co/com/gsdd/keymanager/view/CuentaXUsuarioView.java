package co.com.gsdd.keymanager.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;

import co.com.gsdd.gui.util.JValidateTextField;
import co.com.gsdd.keymanager.constants.KeyManagerConstants;
import co.com.gsdd.keymanager.lang.KeyManagerLanguage;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Great System Development Dynamic <GSDD> <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@Getter
@Setter
public class CuentaXUsuarioView extends AbstractCrudView {

    private static final long serialVersionUID = 1L;

    private JComboBox<String> comboUsuario;
    private JValidateTextField textCuenta;
    private JValidateTextField textUserName;
    private JPasswordField textPass;
    private JValidateTextField textUrl;

    private JButton showOrHideButton;

    private static final int INI_X_LABEL_C3 = 480;

    public CuentaXUsuarioView() {
        setLayout(null);
        initLabels();
        initFields();
        initButtons();
        initTable();
        initBackGround(KeyManagerConstants.IMAGE_CXU);
    }

    private void initLabels() {
        addLabel(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_CXU_ACCOUNT), INI_X_LABEL_C1,
                POS_Y_ROW_1);
        addLabel(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_U_USER), INI_X_LABEL_C2, POS_Y_ROW_1);
        addLabel(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_CXU_USER), INI_X_LABEL_C1, POS_Y_ROW_2);
        addLabel(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_CXU_PASS), INI_X_LABEL_C2, POS_Y_ROW_2);
        addLabel(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_CXU_URL), INI_X_LABEL_C3, POS_Y_ROW_1);
    }

    private void initFields() {
        textCuenta = addValidateTextField(POS_INI_X_TEXT_C1, POS_Y_ROW_1);

        comboUsuario = new JComboBox<>();
        comboUsuario.setBounds(POS_INI_X_TEXT_C2, POS_Y_ROW_1, DEF_WIDTH, DEF_HEIGHT);
        add(comboUsuario);

        textUserName = addValidateTextField(POS_INI_X_TEXT_C1, POS_Y_ROW_2);

        textPass = new JPasswordField();
        textPass.setBounds(POS_INI_X_TEXT_C2, POS_Y_ROW_2, DEF_WIDTH, DEF_HEIGHT);
        add(textPass);

        textUrl = addValidateTextField(POS_INI_X_TEXT_C3, POS_Y_ROW_1);
    }

    private void initButtons() {
        showOrHideButton = addButton(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_SHOW),
                POS_INI_X_TEXT_C3, POS_Y_ROW_2);
        initCommonButtons();
    }

}