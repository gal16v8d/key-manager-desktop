package co.com.gsdd.keymanager.view;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;

import co.com.gsdd.constantes.ConstantesKeyManager;
import co.com.gsdd.gui.util.JPaginateTable;
import co.com.gsdd.gui.util.JValidateTextField;
import co.com.gsdd.keymanager.controller.CuentaXUsuarioController;
import co.com.gsdd.keymanager.controller.PrincipalController;
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
public class CuentaXUsuarioView extends AbstractCommonView {

    private static final long serialVersionUID = 1L;
    private JPaginateTable tableCuentaXUsuario;

    private JComboBox<String> comboUsuario;
    private JValidateTextField textCuenta;
    private JValidateTextField textUserName;
    private JPasswordField textPass;
    private JValidateTextField textUrl;

    private JButton bGuardar;
    private JButton bActualizar;
    private JButton bEliminar;
    private JButton bConsultar;
    private JButton bVolver;
    private JButton bMostrar;

    private final int posInixLabelC3 = 480;
    private final int posInixTextC1 = 150;
    private final int posInixTextC2 = 370;
    private final int posInixTextC3 = 590;
    private final int posYFila1 = 60;
    private final int posYFila2 = 90;

    private static final CuentaXUsuarioView INSTANCE = new CuentaXUsuarioView();

    /**
     * Create the panel.
     */
    private CuentaXUsuarioView() {
        setLayout(null);
        initLabels();
        initFields();
        initButtons();
        initTable();
        initBackGround(ConstantesKeyManager.IMAGE_CXU);
    }

    /**
     * Inicializa/Crea los labels.
     */
    public void initLabels() {
        addLabel(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_CXU_ACCOUNT), INI_X_LABEL_C1,
                posYFila1);
        addLabel(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_U_USER), INI_X_LABEL_C2, posYFila1);
        addLabel(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_CXU_USER), INI_X_LABEL_C1, posYFila2);
        addLabel(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_CXU_PASS), INI_X_LABEL_C2, posYFila2);
        addLabel(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_CXU_URL), posInixLabelC3, posYFila1);
    }

    /**
     * Inicializa/Crea los campos gestionables.
     */
    public void initFields() {
        textCuenta = addValidateTextField(posInixTextC1, posYFila1);

        comboUsuario = new JComboBox<>();
        comboUsuario.setBounds(posInixTextC2, posYFila1, DEF_ANCHO, DEF_ALTO);
        add(comboUsuario);

        textUserName = addValidateTextField(posInixTextC1, posYFila2);

        textPass = new JPasswordField();
        textPass.setBounds(posInixTextC2, posYFila2, DEF_ANCHO, DEF_ALTO);
        add(textPass);

        textUrl = addValidateTextField(posInixTextC3, posYFila1);
    }

    /**
     * Inicializa/crea los botones.
     */
    public void initButtons() {
        final int posYButton = 210;
        bMostrar = addButton(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_SHOW), posInixTextC3,
                posYFila2);
        bMostrar.addActionListener((ActionEvent evt) -> CuentaXUsuarioController.getInstance().eventoMostrar());

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
        tableCuentaXUsuario = addPaginateTable();
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
            CuentaXUsuarioController.getInstance().eventoGuardar();
            break;
        case ACTUALIZAR:
            CuentaXUsuarioController.getInstance().eventoActualizar();
            break;
        case ELIMINAR:
            CuentaXUsuarioController.getInstance().eventoEliminar();
            break;
        case CONSULTAR:
            CuentaXUsuarioController.getInstance().eventoConsultar();
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
    public static CuentaXUsuarioView getInstance() {
        return INSTANCE;
    }

}