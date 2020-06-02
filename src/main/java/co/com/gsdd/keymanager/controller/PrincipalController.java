package co.com.gsdd.keymanager.controller;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;

import co.com.gsdd.constantes.ConstantesInterfaz;
import co.com.gsdd.constantes.ConstantesKeyManager;
import co.com.gsdd.constants.GUIConstants;
import co.com.gsdd.constants.GralConstants;
import co.com.gsdd.dbutil.DBConnection;
import co.com.gsdd.gui.util.JOptionUtil;
import co.com.gsdd.keymanager.ejb.CuentaXUsuarioEjb;
import co.com.gsdd.keymanager.enums.OpcionMenu;
import co.com.gsdd.keymanager.enums.RolEnum;
import co.com.gsdd.keymanager.util.CifradoKeyManager;
import co.com.gsdd.keymanager.util.XLSWriter;
import co.com.gsdd.keymanager.view.PrincipalView;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Great System Development Dynamic <GSDD> <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@Slf4j
@Getter
@Setter
public class PrincipalController {
    /**
     * Administra los diferentes paneles de la App.
     */
    private JPanel cards;
    /**
     * El layout recomendado para el cambio panel.
     */
    private CardLayout cl;
    /**
     * la vista principal que se administra.
     */
    private PrincipalView view;
    /**
     * Obtiene los datos necesarios del control (login).
     */
    private LoginController loControl;
    /**
     * Bandera que indica que debe ser recargado algun elemento.
     */
    private Boolean reload;
    /**
     * 
     */
    public static final PrincipalController INSTANCE = new PrincipalController();

    /**
     * Constructor por Defecto.
     */
    public PrincipalController() {
        reload = Boolean.FALSE;
        view = PrincipalView.getInstance();
    }

    /**
     * construye la ventana a llenar con elementos.
     */
    public void buildPrincipal() {
        reload = Boolean.FALSE;
        changeTitle(ConstantesInterfaz.TITULO_CUENTA);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        view.setBounds(0, 0, (int) dim.getWidth(), (int) dim.getHeight());
        addPanel();
    }

    /**
     * añade los paneles a la vista principal.
     */
    public void addPanel() {
        this.loControl = LoginController.getInstance();
        cards = new JPanel(new CardLayout());
        cards.add(this.loControl.getView(), OpcionMenu.LOGIN.name());
        cl = (CardLayout) (cards.getLayout());
        cl.show(cards, OpcionMenu.LOGIN.name());
        view.getContentPane().add(cards);
    }

    /**
     * cambia el titulo de la ventana.
     * 
     * @param title
     *            el titulo de la ventana.
     */
    public void changeTitle(String title) {
        view.setTitle(ConstantesInterfaz.TITULO_MAIN + title);
    }

    /**
     * Método general de redirección.
     * 
     * @param panel
     *            el panel a pintar(mostrar).
     * @param titulo
     *            el titulo a cambiar.
     */
    public void sendRedirect(String panel, String titulo) {
        changeTitle(titulo);
        cl.show(cards, panel);
    }

    /**
     * cierra la sesi\u00f3n .
     */
    public void eventoSesion() {
        sendRedirect(OpcionMenu.LOGIN.name(), ConstantesInterfaz.TITULO_LOGIN);
        // Elimina la sesión asociada
        loControl.setDto(null);
        loControl.getInit();
    }

    /**
     * evento de clic en info.
     */
    public void eventoInfo() {
        try {
            JTextArea areaMC = new JTextArea();
            areaMC.setVisible(true);
            areaMC.setEditable(false);
            areaMC.setText(ConstantesInterfaz.C_MSJ_INFO_A1 + "\n" + ConstantesInterfaz.C_MSJ_INFO_A2);
            JOptionPane.showMessageDialog(null, areaMC, ConstantesInterfaz.C_MSJ_INFO_T,
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * evento de clic en export.
     */
    public void eventoExport() {
        try {
            if (PrincipalController.getInstance().getLoControl().getDto().getRol()
                    .equals(Long.valueOf(RolEnum.ADMIN.getCode()))) {
                JOptionUtil.showErrorMessage(GUIConstants.ERROR, ConstantesInterfaz.E_MSJ_EXPORT);
            } else {
                String out = getDirectory(ConstantesInterfaz.TITULO_FILECHOOSER);
                if (out == null) {
                    JOptionUtil.showAppMessage(ConstantesInterfaz.JOP_TITULO_EXPORTAR,
                            ConstantesInterfaz.I_MSJ_EXPORTAR, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Boolean e = exportData(out);
                    if (!e) {
                        JOptionUtil.showErrorMessage(GUIConstants.ERROR, ConstantesInterfaz.E_MSJ_XLS);
                    } else {
                        JOptionUtil.showAppMessage(ConstantesInterfaz.JOP_TITULO_EXITO, ConstantesInterfaz.JOP_EXITO,
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * El evento de salir de la app.
     */
    public void eventoSalir() {
        int z = JOptionPane.showConfirmDialog(null, ConstantesInterfaz.I_MSJ_SALIR, ConstantesInterfaz.C_MSJ_INFO_T,
                JOptionPane.YES_NO_OPTION);
        if (z == JOptionPane.YES_OPTION) {
            view.setVisible(false);
            log.info("Cerrando...");
            apagarBD();
            System.exit(0);
        }
    }

    /**
     * Permite apagar adecuadamente el motor de BD embebido.
     */
    private void apagarBD() {
        try {
            DBConnection.getInstance().disconnectDB();
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException e) {
            if (!e.getMessage().contains("Derby system shutdown.")) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * Obtiene el directorio en el cual se va a guardar los datos exportados desde la aplicación.
     * 
     * @param msj
     *            , titulo del filechooser.
     * @return la representación en cadena de texto de la ruta de salida.
     */
    private String getDirectory(String msj) {
        JFileChooser chooser = new JFileChooser();
        String userDirLocation = System.getProperty("user.dir");
        chooser.setCurrentDirectory(new File(userDirLocation));
        // Titulo que llevara la ventana
        chooser.setDialogTitle(msj);
        // Elegiremos archivos del directorio
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        // Si seleccionamos algún archivo retornaremos su directorio
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        } else {
            return null;
        }
    }

    /**
     * Indica el resultado de haber exportado los datos.
     * 
     * @param out
     *            ruta de salida para el archivo.
     * @return TRUE si el proceso se completa exitosamente.
     */
    private Boolean exportData(String out) {
        String passw = null;
        JPanel panel = new JPanel();
        JLabel label = new JLabel(ConstantesInterfaz.JOP_PASS_MSJ);
        JPasswordField pass = new JPasswordField(32);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[] { ConstantesInterfaz.JOP_PASS_OK, ConstantesInterfaz.JOP_PASS_CANCEL };
        int option = JOptionPane.showOptionDialog(null, panel, ConstantesInterfaz.JOP_PASS, JOptionPane.NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
        // 0 : Boton Aceptar
        if (option == 0) {
            char[] password = pass.getPassword();
            passw = new String(password);
            if (passw != null) {
                log.info(PrincipalController.getInstance().getLoControl().getDto().getUsername());
                String currentPass = CifradoKeyManager
                        .descifrarKM(PrincipalController.getInstance().getLoControl().getDto().getPassword());
                boolean passMatch = Objects.equals(passw.trim(), currentPass);
                log.info("{}", passMatch);
                if (passMatch) {
                    XLSWriter writer = new XLSWriter();
                    Boolean b = writer.writeExcel(CuentaXUsuarioEjb.getInstance().list(),
                            new StringBuilder(out).append(System.getProperty("file.separator"))
                                    .append(ConstantesKeyManager.EXPORT_NAME).append(GralConstants.DOT)
                                    .append(ConstantesKeyManager.EXC_EXT1).toString());
                    log.info("Escribio en el excel -> {}", b);
                    return b;
                }
            }
        } else {
            JOptionUtil.showErrorMessage(GUIConstants.ERROR, ConstantesInterfaz.E_MSJ_PASS);
        }

        return Boolean.FALSE;
    }

    /**
     * @return the instance
     */
    public static PrincipalController getInstance() {
        return INSTANCE;
    }

}
