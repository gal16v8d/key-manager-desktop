package co.com.gsdd.keymanager.view;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import co.com.gsdd.constantes.ConstantesInterfaz;
import co.com.gsdd.keymanager.controller.LoginController;
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
public class LoginView extends AbstractCommonView {

    private static final long serialVersionUID = 1L;
    private JLabel labelUsuario;
    private JLabel labelPass;

    private JTextField textUsuario;
    private JPasswordField textPass;

    private JButton btnIngresar;

    private static final int POS_INI_X_LABEL = 100;
    private static final int POS_INI_Y_LABEL = 100;
    private static final int POS_INI_X_TEXTO = 210;
    private static final int ESPACIO_Y = 30;

    private static final LoginView INSTANCE = new LoginView();

    /**
     * Create the panel.
     */
    private LoginView() {
        setLayout(null);
        initLabels();
        initFields();
        initButtons();
        initBackGround(ConstantesInterfaz.IMAGE_PPAL);
    }

    /**
     * Inicializa/Crea los labels.
     */
    public void initLabels() {
        labelUsuario = addLabel(ConstantesInterfaz.LABEL_USUARIO, POS_INI_X_LABEL, POS_INI_Y_LABEL);
        labelPass = addLabel(ConstantesInterfaz.LABEL_PASS, POS_INI_X_LABEL, POS_INI_Y_LABEL + ESPACIO_Y);
    }

    /**
     * Inicializa/Crea los campos gestionables.
     */
    public void initFields() {
        final int colLenght1 = 16;
        textUsuario = new JTextField();
        textUsuario.setBounds(POS_INI_X_TEXTO, POS_INI_Y_LABEL, DEF_ANCHO, DEF_ALTO);
        textUsuario.setColumns(colLenght1);
        add(textUsuario);

        textPass = new JPasswordField();
        textPass.setBounds(POS_INI_X_TEXTO, POS_INI_Y_LABEL + ESPACIO_Y, DEF_ANCHO, DEF_ALTO);
        textPass.setColumns(colLenght1);
        add(textPass);
    }

    /**
     * Inicializa/Crea los botones.
     */
    public void initButtons() {
        btnIngresar = addButton(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.BUTTON_LOGIN), POS_INI_X_TEXTO,
                POS_INI_Y_LABEL + (ESPACIO_Y * 2));
        btnIngresar.addActionListener((ActionEvent evt) -> autenticar(evt));
    }

    /**
     * @param evt
     *            el evento de oprimir boton Ingresar.
     */
    public void autenticar(ActionEvent evt) {
        LoginController.getInstance().autenticar();
    }

    /**
     * @return the instance
     */
    public static LoginView getInstance() {
        return INSTANCE;
    }

}
