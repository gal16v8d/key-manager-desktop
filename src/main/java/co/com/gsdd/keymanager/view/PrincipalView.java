package co.com.gsdd.keymanager.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import co.com.gsdd.constantes.ConstantesInterfaz;
import co.com.gsdd.constantes.ConstantesKeyManager;
import co.com.gsdd.constants.GUIConstants;
import co.com.gsdd.constants.GralConstants;
import co.com.gsdd.dbutil.DBConnection;
import co.com.gsdd.dbutil.DBQueryUtil;
import co.com.gsdd.exception.TechnicalException;
import co.com.gsdd.gui.util.JOptionUtil;
import co.com.gsdd.keymanager.controller.CuentaXUsuarioController;
import co.com.gsdd.keymanager.controller.PrincipalController;
import co.com.gsdd.keymanager.controller.UsuarioController;
import co.com.gsdd.keymanager.enums.OpcionMenu;
import co.com.gsdd.keymanager.enums.RolEnum;
import co.com.gsdd.keymanager.util.EjecutorKey;
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
public class PrincipalView extends JFrame {

    /**
     * versi\u00F3n serial de esta clase.
     */
    private static final long serialVersionUID = 1L;
    private JMenu menuAdmon;
    private JMenu menuInfo;
    private JMenu menuSesion;
    private JMenuItem itemCuenta;
    private JMenuItem itemCuentaXUsuario;
    private JMenuItem itemUsuario;
    private JMenuItem itemExportar;
    private JMenuItem itemSalir;
    private JMenuItem itemCreditos;
    private JMenuItem itemConsulta;
    private JMenuItem itemSesion;

    /**
     * La instancia de la ventana.
     */
    private static final PrincipalView INSTANCE = new PrincipalView();

    /**
     * Construye una instancia de la ventana.
     */
    private PrincipalView() {
        try {
            buildMenu();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            System.exit(1);
        }
    }

    /**
     * @param args
     *            los Argumentos para construir la ventana.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    iniciarApp();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    System.exit(1);
                }
            }
        });
    }

    /**
     * Inicia la carga de la aplicacion.
     */
    private static void iniciarApp() {
        log.info("Inicializando...");
        iniciarBD();
        arrancarInterfaz();
    }

    /**
     * Permite arrancar la interfaz gráfica de la aplicación.
     */
    private static void arrancarInterfaz() {
        PrincipalView.getInstance().setVisible(true);
        PrincipalView.getInstance().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PrincipalView.getInstance().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                PrincipalController.getInstance().eventoSalir();
            }
        });
        PrincipalController.getInstance();
        PrincipalController.getInstance().buildPrincipal();
    }

    /**
     * Crea los elementos de la barra de men\u00fa.
     */
    private void buildMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuAdmon = new JMenu(ConstantesInterfaz.MENU_ADMON);
        menuBar.add(menuAdmon);
        menuSesion = new JMenu(ConstantesInterfaz.MENU_SESION);
        menuBar.add(menuSesion);
        menuInfo = new JMenu(ConstantesInterfaz.MENU_INFO);
        menuBar.add(menuInfo);
        EjecutorKey.getInstance().getExecutor().execute(() -> agregarItemsAdmon());
        EjecutorKey.getInstance().getExecutor().execute(() -> agregarItemsSesion());
        EjecutorKey.getInstance().getExecutor().execute(() -> agregarItemsInfo());
        setIconImage(new ImageIcon(getClass().getResource(ConstantesInterfaz.IMAGE_ICON)).getImage());
    }

    /**
     * Permite agregar los items al menú de administración.
     */
    private void agregarItemsAdmon() {
        agregarMenuItemConsulta();
        menuAdmon.add(new JSeparator());
        agregarMenuItemCuentaXUsuario();
        menuAdmon.add(new JSeparator());
        agregarMenuItemUsuario();
        menuAdmon.add(new JSeparator());
        agregarMenuItemExportar();
    }

    /**
     * Permite agregar en presentación el menuitem de consultas.
     */
    private void agregarMenuItemConsulta() {
        itemConsulta = new JMenuItem(ConstantesInterfaz.MENUITEM_CONSULTA);
        itemConsulta.addActionListener((ActionEvent evt) -> seleccionarOpcion(OpcionMenu.CONSULTA));
        menuAdmon.add(itemConsulta);
    }

    /**
     * Permite agregar en presentación el menuitem de cuentaxusuario.
     */
    private void agregarMenuItemCuentaXUsuario() {
        itemCuentaXUsuario = new JMenuItem(ConstantesInterfaz.MENUITEM_CUENTAXUSUARIO);
        itemCuentaXUsuario.addActionListener((ActionEvent evt) -> seleccionarOpcion(OpcionMenu.CUENTAXUSUARIO));
        menuAdmon.add(itemCuentaXUsuario);
    }

    /**
     * Permite agregar en presentación el menuitem de usuario.
     */
    private void agregarMenuItemUsuario() {
        itemUsuario = new JMenuItem(ConstantesInterfaz.MENUITEM_USUARIO);
        itemUsuario.addActionListener((ActionEvent evt) -> seleccionarOpcion(OpcionMenu.USUARIO));
        menuAdmon.add(itemUsuario);
    }

    /**
     * Permite agregar en presentación el menuitem de exportar.
     */
    private void agregarMenuItemExportar() {
        itemExportar = new JMenuItem(ConstantesInterfaz.MENUITEM_EXPORTAR);
        itemExportar.addActionListener((ActionEvent evt) -> seleccionarOpcion(OpcionMenu.EXPORTAR));
        menuAdmon.add(itemExportar);
    }

    /**
     * Permite agregar los items al menú de sesion.
     */
    private void agregarItemsSesion() {
        agregarMenuItemSesion();
        menuSesion.add(new JSeparator());
        agregarMenuItemSalir();
    }

    /**
     * Permite agregar en presentación el menuitem de sesion.
     */
    private void agregarMenuItemSesion() {
        itemSesion = new JMenuItem(ConstantesInterfaz.MENUITEM_SESION);
        itemSesion.addActionListener((ActionEvent evt) -> seleccionarOpcion(OpcionMenu.SESION));
        menuSesion.add(itemSesion);
    }

    /**
     * Permite agregar en presentación el menuitem de salir.
     */
    private void agregarMenuItemSalir() {
        itemSalir = new JMenuItem(ConstantesInterfaz.MENUITEM_SALIR);
        itemSalir.addActionListener((ActionEvent evt) -> seleccionarOpcion(OpcionMenu.SALIR));
        menuSesion.add(itemSalir);
    }

    /**
     * Permite agregar los items al menú de info.
     */
    private void agregarItemsInfo() {
        itemCreditos = new JMenuItem(ConstantesInterfaz.MENUITEM_CREDITOS);
        itemCreditos.addActionListener((ActionEvent evt) -> seleccionarOpcion(OpcionMenu.CREDITOS));
        menuInfo.add(itemCreditos);
    }

    /**
     * Selecciona la opción según el menú.
     * 
     * @param op
     *            las posibles opciones.
     */
    private void seleccionarOpcion(OpcionMenu op) {
        switch (op) {
        case CONSULTA:
            if (PrincipalController.getInstance().getLoControl().getDto() != null) {
                Long rolAdmin = Long.parseLong(RolEnum.ADMIN.getCode());
                if (PrincipalController.getInstance().getLoControl().getDto().getRol().equals(rolAdmin)) {
                    String query = JOptionPane.showInputDialog(null, ConstantesInterfaz.JOP_TITULO_CONSULTA);
                    if (query != null && !query.isEmpty()) {
                        try {
                            DBConnection.getInstance().setSt(DBConnection.getInstance().getCon().createStatement());
                            DBConnection.getInstance().getSt().execute(query);
                        } catch (SQLException e) {
                            log.error(e.getMessage(), e);
                        }
                    }
                } else {
                    JOptionUtil.showErrorMessage(GUIConstants.ERROR, ConstantesInterfaz.E_MSJ_ADMIN);
                }
            } else {
                JOptionUtil.showErrorMessage(GUIConstants.ERROR, ConstantesInterfaz.E_MSJ_LOGIN);
            }
            break;
        case CUENTAXUSUARIO:
            if (PrincipalController.getInstance().getLoControl().getDto() != null) {
                addPanel(CuentaXUsuarioController.getInstance().getView(), OpcionMenu.CUENTAXUSUARIO.name(),
                        ConstantesInterfaz.TITULO_CUENTAXUSER);
                if (PrincipalController.getInstance().getReload().equals(Boolean.TRUE)) {
                    // Refresca los combos con datos
                    CuentaXUsuarioController.getInstance().clearCombo();
                    CuentaXUsuarioController.getInstance().fillCombo();
                    CuentaXUsuarioController.getInstance()
                            .fillTable(CuentaXUsuarioController.getInstance().getView().getTableCuentaXUsuario());
                    PrincipalController.getInstance().setReload(Boolean.FALSE);
                }
            } else {
                JOptionUtil.showErrorMessage(GUIConstants.ERROR, ConstantesInterfaz.E_MSJ_LOGIN);
            }
            break;
        case CREDITOS:
            PrincipalController.getInstance().eventoInfo();
            break;
        case EXPORTAR:
            PrincipalController.getInstance().eventoExport();
            break;
        case SALIR:
            PrincipalController.getInstance().eventoSalir();
            break;
        case SESION:
            PrincipalController.getInstance().eventoSesion();
            break;
        case USUARIO:
            if (PrincipalController.getInstance().getLoControl().getDto() != null) {
                addPanel(UsuarioController.getInstance().getView(), OpcionMenu.USUARIO.name(),
                        ConstantesInterfaz.TITULO_USUARIO);
            } else {
                JOptionUtil.showErrorMessage(GUIConstants.ERROR, ConstantesInterfaz.E_MSJ_LOGIN);
            }
            break;
        default:
            break;
        }
    }

    /**
     * Evita sobre-cargar el inicio, instancia y añade la vista cuando es necesaria.
     * 
     * @param view
     *            la vista a agregar.
     * @param opcion
     *            la opcion asociada a la vista.
     * @param titulo
     *            el titulo asociado a la vista.
     */
    private void addPanel(JPanel view, String opcion, String titulo) {
        boolean flagAdd = false;
        if (!flagAdd) {
            PrincipalController.getInstance().getCards().add(view, opcion);
            flagAdd = true;
        }
        PrincipalController.getInstance().sendRedirect(opcion, titulo);
    }

    /**
     * Inicializa los datos (BD).
     */
    private static void iniciarBD() {
        DBConnection.getInstance();
        try {
            System.setProperty("derby.system.home", "./kmgr/");
            System.setProperty("derby.stream.error.file", "../KMgr-log/derby.log");
            Boolean b = DBQueryUtil.dbExist(ConstantesKeyManager.DERBY_MAIN_TABLE,
                    ConstantesKeyManager.DERBY_CONNECTION, ConstantesKeyManager.DERBY_LOCATION, GralConstants.EMPTY,
                    GralConstants.EMPTY);
            log.info("BD existe -> {}", b);
            if (!b) {
                DBConnection.getInstance().executeImport(Boolean.TRUE);
                log.info(ConstantesKeyManager.OK);
            }
            if (DBConnection.getInstance().getCon() == null) {
                DBConnection.getInstance().connectDB(ConstantesKeyManager.DERBY_CONNECTION,
                        ConstantesKeyManager.DERBY_LOCATION, GralConstants.EMPTY, GralConstants.EMPTY);
            }
            log.info("{}", DBConnection.getInstance().getCon().toString());
        } catch (TechnicalException e) {
            log.error(ConstantesKeyManager.FALLO + GralConstants.COLON + e, e);
        }
    }

    /**
     * @return the instance
     */
    public static PrincipalView getInstance() {
        return INSTANCE;
    }

}