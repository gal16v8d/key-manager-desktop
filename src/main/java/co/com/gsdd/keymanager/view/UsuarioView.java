package co.com.gsdd.keymanager.view;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import co.com.gsdd.constantes.ConstantesKeyManager;
import co.com.gsdd.constants.GralConstants;
import co.com.gsdd.constants.RegexConstants;
import co.com.gsdd.gui.util.JPaginateTable;
import co.com.gsdd.gui.util.JValidateTextField;
import co.com.gsdd.keymanager.controller.PrincipalController;
import co.com.gsdd.keymanager.controller.UsuarioController;
import co.com.gsdd.keymanager.enums.OpcionBoton;
import co.com.gsdd.keymanager.enums.OpcionMenu;
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
public class UsuarioView extends AbstractCommonView {

    private static final long serialVersionUID = 1L;
    private JPaginateTable tableUsuario;

    private JLabel labelPk;

    private JValidateTextField textPNombre;
    private JValidateTextField textPApellido;
    private JValidateTextField textUserName;
    private JPasswordField textPass;
    private JLabel labelVRol;

    private JButton bGuardar;
    private JButton bActualizar;
    private JButton bEliminar;
    private JButton bConsultar;
    private JButton bVolver;

    // private final int posInixLabelC3 = 480;
    private final int posInixTextC1 = 150;
    private final int posInixTextC2 = 370;
    private final int posInixTextC3 = 590;
    private final int posYFila1 = 60;
    private final int posYFila2 = 90;
    private final int posYFila3 = 120;

    private static final UsuarioView INSTANCE = new UsuarioView();

    /**
     * Create the panel.
     */
    private UsuarioView() {
        setLayout(null);
        initLabels();
        initFields();
        initButtons();
        initTable();
        initBackGround(ConstantesKeyManager.IMAGE_USER);
    }

    /**
     * Inicializa/Crea los labels.
     */
    public void initLabels() {
        // Label utilizado para almacenar la PK en caso de ser necesario.
        labelPk = new JLabel(GralConstants.EMPTY);
        labelPk.setBounds(0, 0, 0, 0);
        labelPk.setVisible(false);

        addLabel(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_U_FN), INI_X_LABEL_C1, posYFila1);
        addLabel(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_U_LN), INI_X_LABEL_C2, posYFila1);
        addLabel(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_U_USER), INI_X_LABEL_C1, posYFila2);
        addLabel(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_U_PASS), INI_X_LABEL_C2, posYFila2);
        addLabel(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_U_ROLE), INI_X_LABEL_C1, posYFila3);
    }

    /**
     * Inicializa/Crea los campos gestionables.
     */
    public void initFields() {
        textPNombre = addValidateTextField(posInixTextC1, posYFila1);
        textPNombre.setTextProperties(RegexConstants.TEXT, DEF_TAMANO_TEXTO,
                KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_TEXT));

        textPApellido = addValidateTextField(posInixTextC2, posYFila1);
        textPApellido.setTextProperties(RegexConstants.TEXT, DEF_TAMANO_TEXTO,
                KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_TEXT));

        textUserName = addValidateTextField(posInixTextC1, posYFila2);
        textUserName.setTextProperties(RegexConstants.ALFA, DEF_TAMANO_TEXTO,
                KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_ALFA));

        textPass = new JPasswordField();
        textPass.setBounds(posInixTextC2, posYFila2, DEF_ANCHO, DEF_ALTO);
        add(textPass);

        labelVRol = addLabel("", posInixTextC1, posYFila3);
    }

    /**
     * Inicializa/crea los botones.
     */
    public void initButtons() {
        final int posYButton = 210;
        bGuardar = addButton(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.BUTTON_SAVE), INI_X_LABEL_C1,
                posYButton);
        bGuardar.addActionListener((ActionEvent evt) -> seleccionarOpcion(OpcionBoton.GUARDAR));

        bActualizar = addButton(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.BUTTON_UPDATE), posInixTextC1,
                posYButton);
        bActualizar.addActionListener((ActionEvent evt) -> seleccionarOpcion(OpcionBoton.ACTUALIZAR));

        bEliminar = addButton(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.BUTTON_DELETE), INI_X_LABEL_C2,
                posYButton);
        bEliminar.addActionListener((ActionEvent evt) -> seleccionarOpcion(OpcionBoton.ELIMINAR));

        bConsultar = addButton(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.BUTTON_SEARCH), posInixTextC2,
                posYButton);
        bConsultar.addActionListener((ActionEvent evt) -> seleccionarOpcion(OpcionBoton.CONSULTAR));

        bVolver = addButton(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.BUTTON_BACK), posInixTextC3,
                posYButton);
        bVolver.addActionListener((ActionEvent evt) -> seleccionarOpcion(OpcionBoton.VOLVER));
    }

    /**
     * Inicializa/crea la tabla de datos.
     */
    public void initTable() {
        tableUsuario = addPaginateTable();
    }

    /**
     * Selecciona la opción según el botón.
     * 
     * @param op
     *            las posibles opciones.
     */
    private void seleccionarOpcion(OpcionBoton op) {
        switch (op) {
        case GUARDAR:
            UsuarioController.getInstance().eventoGuardar();
            break;
        case ACTUALIZAR:
            UsuarioController.getInstance().eventoActualizar();
            break;
        case ELIMINAR:
            UsuarioController.getInstance().eventoEliminar();
            break;
        case CONSULTAR:
            UsuarioController.getInstance().eventoConsultar();
            break;
        case VOLVER:
            PrincipalController.getInstance().sendRedirect(OpcionMenu.LOGIN.name(),
                    KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TITLE_LOGIN));
            break;
        default:
            break;
        }
    }

    /**
     * @return the instance
     */
    public static UsuarioView getInstance() {
        return INSTANCE;
    }

}
