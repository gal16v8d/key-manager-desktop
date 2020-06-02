package co.com.gsdd.keymanager.controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import co.com.gsdd.constantes.ConstantesInterfaz;
import co.com.gsdd.constants.GUIConstants;
import co.com.gsdd.constants.GralConstants;
import co.com.gsdd.gui.util.JOptionListBox;
import co.com.gsdd.gui.util.JOptionUtil;
import co.com.gsdd.gui.util.JPaginateTable;
import co.com.gsdd.keymanager.ejb.UsuarioEjb;
import co.com.gsdd.keymanager.entities.Usuario;
import co.com.gsdd.keymanager.enums.RolEnum;
import co.com.gsdd.keymanager.util.CifradoKeyManager;
import co.com.gsdd.keymanager.view.UsuarioView;
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
public class UsuarioController implements InterfaceController {
    /**
     * La instancia definida para la vista.
     */
    private UsuarioView view;
    /**
     * La instancia definida como modelo.
     */
    private UsuarioEjb modelo;
    /**
     * La instancia definida de la clase.
     */
    private static final UsuarioController INSTANCE = new UsuarioController();

    /**
     * Constructor por Defecto.
     */
    private UsuarioController() {
        this.modelo = UsuarioEjb.getInstance();
        this.view = UsuarioView.getInstance();
        buildVistaInicial();
    }

    @Override
    public void buildVistaInicial() {
        try {
            startButtons(false);
            startTable();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Inicializa y crea la tabla.
     */
    public void startTable() {
        setTableModel(view.getTableUsuario());
        fillTable(view.getTableUsuario());
    }

    /**
     * @param flag
     *            habilita/deshabilita los botones.
     */
    public void startButtons(boolean flag) {
        view.getBActualizar().setEnabled(flag);
        view.getBEliminar().setEnabled(flag);
        view.getBGuardar().setEnabled(!flag);
    }

    /**
     * Obtiene los datos del formulario y los mapea a un DTO.
     * 
     * @return el dto si supera el proceso de validacion.
     */
    public Usuario getDataFromForm() {
        Usuario datos = null;
        try {
            datos = new Usuario();
            String textoLabel = view.getLabelPk().getText();
            Long id = (textoLabel != null && !textoLabel.equals(GralConstants.EMPTY) ? Long.parseLong(textoLabel.trim())
                    : (long) (System.nanoTime() * (Math.random())));
            datos.setCodigousuario(id);
            datos.setPrimerNombre(view.getTextPNombre().getText().trim());
            datos.setPrimerApellido(view.getTextPApellido().getText().trim());
            datos.setUsername(view.getTextUserName().getText().trim());
            // Clave encriptada
            datos.setPassword(CifradoKeyManager.cifrarKM(new String(view.getTextPass().getPassword()).trim()));
            datos.setRol(view.getLabelVRol().getText().equals(RolEnum.ADMIN.name())
                    ? Long.valueOf(RolEnum.ADMIN.getCode()) : Long.valueOf(RolEnum.USER.getCode()));
            return datos;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Llena los campos del formulario con lo obtenido en base de datos.
     * 
     * @param dto
     *            el resultado obtenido de base de datos.
     */
    public void setFields(Usuario dto) {
        view.getLabelPk().setText(String.valueOf(dto.getCodigousuario()));
        view.getTextPNombre().setText(dto.getPrimerNombre());
        view.getTextPApellido().setText(dto.getPrimerApellido());
        view.getTextUserName().setText(dto.getUsername());
        // Desencriptando clave
        view.getTextPass().setText(CifradoKeyManager.descifrarKM(dto.getPassword()));
        view.getLabelVRol().setText((String.valueOf(dto.getRol()).equals(RolEnum.ADMIN.getCode()))
                ? RolEnum.ADMIN.name() : RolEnum.USER.name());
    }

    @Override
    public void clearFields() {
        view.getLabelPk().setText(GralConstants.EMPTY);
        view.getTextPNombre().setText(GralConstants.EMPTY);
        view.getTextPApellido().setText(GralConstants.EMPTY);
        view.getTextUserName().setText(GralConstants.EMPTY);
        view.getTextPass().setText(GralConstants.EMPTY);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void setTableModel(JPaginateTable tabla) {
        Class[] types = new Class[] { java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class };
        tabla.setTableModel(ConstantesInterfaz.getTablaUsuario(), types);
        tabla.setItemsPerPage(ConstantesInterfaz.LIMITE_TABLA_PAG);
    }

    @Override
    public void fillTable(JPaginateTable tabla) {
        List<Usuario> listaBD = modelo.list();
        DefaultTableModel dtm = (DefaultTableModel) tabla.getModel();
        int tam = dtm.getRowCount();
        for (int i = 0; i < tam; i++) {
            dtm.removeRow(dtm.getRowCount() - 1);
        }
        if (listaBD != null) {
            for (int i = 0; i < listaBD.size(); i++) {
                dtm.addRow(new Object[1]);
                dtm.setValueAt(listaBD.get(i).getPrimerNombre(), i, 0);
                dtm.setValueAt(listaBD.get(i).getPrimerApellido(), i, 1);
                dtm.setValueAt(listaBD.get(i).getUsername(), i, 2);
                dtm.setValueAt(String.valueOf(listaBD.get(i).getRol()).equals(RolEnum.ADMIN.getCode())
                        ? RolEnum.ADMIN.name() : RolEnum.USER.name(), i, 3);
            }
        }
        tabla.setPaginateSorter(new TableRowSorter<TableModel>(dtm));
        tabla.setRowSorter(tabla.getPaginateSorter());
        tabla.initFilterAndButton(tabla.getPaginateSorter(), tabla.getItemsPerPage());
    }

    /**
     * Valida que los datos ingresados sean correctos.
     * 
     * @param datos
     *            objeto a validar.
     * @return TRUE si los campos obligatorios han sido diligenciados.
     */
    private Boolean validateData(Usuario datos) {
        return (datos != null && datos.getPrimerNombre() != null && datos.getPrimerApellido() != null
                && datos.getUsername() != null && datos.getPassword() != null);
    }

    @Override
    public void eventoGuardar() {
        try {
            Usuario datos = getDataFromForm();
            if (validateData(datos)) {
                Boolean retorno = modelo.save(datos);
                if (retorno) {
                    String msg = ConstantesInterfaz.I_MSJ_GUARDADO + datos.getUsername();
                    log.info(msg);
                    fillTable(view.getTableUsuario());
                    JOptionUtil.showAppMessage(ConstantesInterfaz.JOP_TITULO_EXITO, msg,
                            JOptionPane.INFORMATION_MESSAGE);
                    PrincipalController.getInstance().setReload(Boolean.TRUE);
                    clearFields();
                } else {
                    JOptionUtil.showErrorMessage(GUIConstants.ERROR, ConstantesInterfaz.E_MSJ_INESPERADO);
                }
            } else {
                JOptionUtil.showErrorMessage(GUIConstants.ERROR, ConstantesInterfaz.E_MSJ_DATOS);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            JOptionUtil.showErrorMessage(GUIConstants.ERROR, e.getMessage());
        }
    }

    /**
     * Toma los datos del formulario y los actualiza en base de datos.
     */
    public void eventoActualizar() {
        try {
            Usuario datos = getDataFromForm();
            if (validateData(datos)) {
                Boolean retorno = modelo.update(datos);
                if (retorno) {
                    String msg = ConstantesInterfaz.I_MSJ_ACTUALIZADO + datos.getUsername();
                    log.info(msg);
                    fillTable(view.getTableUsuario());
                    JOptionUtil.showAppMessage(ConstantesInterfaz.JOP_TITULO_EXITO, msg,
                            JOptionPane.INFORMATION_MESSAGE);
                    PrincipalController.getInstance().setReload(Boolean.TRUE);
                    clearFields();
                    startButtons(false);
                } else {
                    JOptionUtil.showErrorMessage(GUIConstants.ERROR, ConstantesInterfaz.E_MSJ_INESPERADO);
                }
            } else {
                JOptionUtil.showErrorMessage(GUIConstants.ERROR, ConstantesInterfaz.E_MSJ_DATOS);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            JOptionUtil.showErrorMessage(GUIConstants.ERROR, e.getMessage());
        }
    }

    /**
     * Toma los datos del formulario y los actualiza en base de datos.
     */
    public void eventoEliminar() {
        try {
            Usuario datos = getDataFromForm();
            Boolean retorno = modelo.delete(datos);
            if (retorno) {
                String msg = ConstantesInterfaz.I_MSJ_BORRADO + datos.getUsername();
                log.info(msg);
                fillTable(view.getTableUsuario());
                JOptionUtil.showAppMessage(ConstantesInterfaz.JOP_TITULO_EXITO, msg, JOptionPane.INFORMATION_MESSAGE);
                PrincipalController.getInstance().setReload(Boolean.TRUE);
                clearFields();
                startButtons(false);
            } else {
                JOptionUtil.showErrorMessage(GUIConstants.ERROR, ConstantesInterfaz.E_MSJ_INESPERADO);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            JOptionUtil.showErrorMessage(GUIConstants.ERROR, e.getMessage());
        }
    }

    /**
     * Busca mediante los datos ingresados el usuario correspondiente.
     */
    public void eventoConsultar() {
        try {
            List<String> param = modelo.suggest();
            JOptionListBox jolb = new JOptionListBox(param, ConstantesInterfaz.JOP_TITULO_BUSCAR,
                    ConstantesInterfaz.LABEL_U_USER);
            String username = jolb.getSelectedValue();
            if (username != null) {
                Usuario u = modelo.search(username);
                if (u != null) {
                    setFields(u);
                    startButtons(true);
                } else {
                    JOptionUtil.showErrorMessage(GUIConstants.ERROR, ConstantesInterfaz.E_MSJ_INESPERADO);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            JOptionUtil.showErrorMessage(GUIConstants.ERROR, e.getMessage());
        }
    }

    /**
     * @return the instance
     */
    public static UsuarioController getInstance() {
        return INSTANCE;
    }

}
