package co.com.gsdd.keymanager.controller;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import co.com.gsdd.constantes.ConstantesKeyManager;
import co.com.gsdd.constants.GUIConstants;
import co.com.gsdd.constants.GralConstants;
import co.com.gsdd.gui.util.JOptionListBox;
import co.com.gsdd.gui.util.JOptionUtil;
import co.com.gsdd.gui.util.JPaginateTable;
import co.com.gsdd.keymanager.ejb.CuentaXUsuarioEjb;
import co.com.gsdd.keymanager.ejb.UsuarioEjb;
import co.com.gsdd.keymanager.entities.CuentaXUsuario;
import co.com.gsdd.keymanager.entities.dto.CuentaXUsuarioDto;
import co.com.gsdd.keymanager.enums.RolEnum;
import co.com.gsdd.keymanager.lang.KeyManagerLanguage;
import co.com.gsdd.keymanager.util.CifradoKeyManager;
import co.com.gsdd.keymanager.view.CuentaXUsuarioView;
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
public class CuentaXUsuarioController implements InterfaceController {
    /**
     * La instancia definida para la vista.
     */
    private CuentaXUsuarioView view;
    /**
     * La instancia definida como modelo.
     */
    private CuentaXUsuarioEjb modelo;
    /**
     * Apuntador a ref vieja.
     */
    private CuentaXUsuario old;
    /**
     * La instancia definida de la clase.
     */
    private static final CuentaXUsuarioController INSTANCE = new CuentaXUsuarioController();

    /**
     * Constructor por Defecto.
     */
    private CuentaXUsuarioController() {
        this.modelo = CuentaXUsuarioEjb.getInstance();
        this.view = CuentaXUsuarioView.getInstance();
        buildVistaInicial();
    }

    @Override
    public void buildVistaInicial() {
        try {
            fillCombo();
            startButtons(false);
            startTable();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Inicializa los combos.
     */
    public void fillCombo() {
        List<String> user = UsuarioEjb.getInstance().suggest();
        for (String u : user) {
            view.getComboUsuario().addItem(u);
        }
        view.getComboUsuario().repaint();
    }

    /**
     * Limpia los combos.
     */
    public void clearCombo() {
        view.getComboUsuario().removeAllItems();
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
     * Inicializa y crea la tabla.
     */
    public void startTable() {
        setTableModel(view.getTableCuentaXUsuario());
        fillTable(view.getTableCuentaXUsuario());
    }

    /**
     * Obtiene los datos del formulario y los mapea a un DTO.
     * 
     * @return el dto si supera el proceso de validacion.
     */
    public CuentaXUsuario getDataFromForm() {
        CuentaXUsuario datos = null;
        try {
            datos = new CuentaXUsuario();
            // Define el codigo de la cuenta asociado
            datos.setNombreCuenta(view.getTextCuenta().getText().trim());
            // Usuario de la sesion o elegido del combo dependiendo
            // del rol
            datos.setCodigousuario(String.valueOf(PrincipalController.getInstance().getLoControl().getDto().getRol())
                    .equals(RolEnum.ADMIN.getCode())
                            ? UsuarioEjb.getInstance().search((String) view.getComboUsuario().getSelectedItem())
                                    .getCodigousuario()
                            : PrincipalController.getInstance().getLoControl().getDto().getCodigousuario());
            datos.setUsername(view.getTextUserName().getText().trim());
            // Encripta la contraseña ingresada
            datos.setPassword(CifradoKeyManager.cifrarKM(new String(view.getTextPass().getPassword()).trim()));
            datos.setUrl(view.getTextUrl().getText());
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
    public void setFields(CuentaXUsuario dto) {
        // Se agregan temporalmente a los combos los valores
        view.getComboUsuario().setSelectedItem(dto.getUsername());
        view.getTextCuenta().setText(dto.getNombreCuenta());
        view.getTextUserName().setText(dto.getUsername());
        // Desencriptando clave
        view.getTextPass().setText(CifradoKeyManager.descifrarKM(dto.getPassword()));
        view.getTextUrl().setText(dto.getUrl());
    }

    @Override
    public void clearFields() {
        view.getTextCuenta().setText(GralConstants.EMPTY);
        view.getTextUrl().setText(GralConstants.EMPTY);
        view.getTextUserName().setText(GralConstants.EMPTY);
        view.getTextPass().setText(GralConstants.EMPTY);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void setTableModel(JPaginateTable tabla) {
        Class[] types = new Class[] { java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class };
        tabla.setTableModel(ConstantesKeyManager.getAccountXUserTableModel(), types);
        tabla.setItemsPerPage(ConstantesKeyManager.TBL_PAGE_SIZE);
    }

    @Override
    public void fillTable(JPaginateTable tabla) {
        List<CuentaXUsuarioDto> listaBD = modelo.list();
        DefaultTableModel dtm = (DefaultTableModel) tabla.getModel();
        int tam;
        tam = dtm.getRowCount();
        for (int i = 0; i < tam; i++) {
            dtm.removeRow(dtm.getRowCount() - 1);
        }
        if (listaBD != null) {
            int size = listaBD.size();
            for (int i = 0; i < size; i++) {
                dtm.addRow(new Object[1]);
                dtm.setValueAt(listaBD.get(i).getNombreusuario(), i, 0);
                dtm.setValueAt(listaBD.get(i).getNombrecuenta(), i, 1);
                dtm.setValueAt(listaBD.get(i).getUsuario(), i, 2);
                // Muestra la clave desencriptada
                String dp = obtenerPass(listaBD.get(i).getPass(), Boolean.FALSE);
                dtm.setValueAt(dp, i, 3);
                dtm.setValueAt(listaBD.get(i).getUrl(), i, 4);
                // Muestra la fecha
                Date fd = listaBD.get(i).getFecha();
                Date fa = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                String fecha = ConstantesKeyManager.getFormater().format(fd);
                dtm.setValueAt(fecha, i, 5);
                dtm.setValueAt(ConstantesKeyManager.getSuggestion(fa, fd), i, 6);
            }
        }
        tabla.setPaginateSorter(new TableRowSorter<TableModel>(dtm));
        tabla.setRowSorter(tabla.getPaginateSorter());
        tabla.initFilterAndButton(tabla.getPaginateSorter(), tabla.getItemsPerPage());
    }

    /**
     * Manda la info de pass a la tabla.
     * 
     * @param ePass
     * @param mostrar
     * @return
     */
    private String obtenerPass(String ePass, Boolean mostrar) {
        return mostrar ? CifradoKeyManager.descifrarKM(ePass) : ConstantesKeyManager.MASK_TEXTO;
    }

    /**
     * Valida que los datos ingresados sean correctos.
     * 
     * @param datos
     *            objeto a validar.
     * @return TRUE si los campos obligatorios han sido diligenciados.
     */
    private Boolean validateData(CuentaXUsuario datos) {
        return (datos != null && datos.getNombreCuenta() != null && datos.getCodigousuario() != null
                && datos.getUsername() != null && datos.getPassword() != null);
    }

    @Override
    public void eventoGuardar() {
        try {
            CuentaXUsuario datos = getDataFromForm();
            if (validateData(datos)) {
                Boolean retorno = modelo.save(datos);
                if (retorno) {
                    String msg = KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_INFO_SAVE)
                            + datos.getNombreCuenta();
                    log.info(msg);
                    fillTable(view.getTableCuentaXUsuario());
                    JOptionUtil.showAppMessage(
                            KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.JOP_TITLE_SUCCESS), msg,
                            JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                    clearCombo();
                    fillCombo();
                } else {
                    JOptionUtil.showErrorMessage(GUIConstants.ERROR,
                            KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_ERROR_GRAL));
                }
            } else {
                JOptionUtil.showErrorMessage(GUIConstants.ERROR,
                        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_ERROR_DATA));
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
            CuentaXUsuario datos = getDataFromForm();
            if (validateData(datos)) {
                Boolean retorno = modelo.update(datos, old.getNombreCuenta(), old.getCodigousuario());
                if (retorno) {
                    String msg = KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_INFO_UPDATE)
                            + datos.getUsername();
                    log.info(msg);
                    fillTable(view.getTableCuentaXUsuario());
                    JOptionUtil.showAppMessage(
                            KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.JOP_TITLE_SUCCESS), msg,
                            JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                    clearCombo();
                    fillCombo();
                    startButtons(false);
                } else {
                    JOptionUtil.showErrorMessage(GUIConstants.ERROR,
                            KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_ERROR_GRAL));
                }
            } else {
                JOptionUtil.showErrorMessage(GUIConstants.ERROR,
                        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_ERROR_DATA));
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
            CuentaXUsuario datos = getDataFromForm();
            Boolean retorno = modelo.delete(datos);
            if (retorno) {
                String msg = KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_INFO_DELETE)
                        + datos.getUsername();
                log.info(msg);
                fillTable(view.getTableCuentaXUsuario());
                JOptionUtil.showAppMessage(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.JOP_TITLE_SUCCESS),
                        msg, JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                clearCombo();
                fillCombo();
                startButtons(false);
            } else {
                JOptionUtil.showErrorMessage(GUIConstants.ERROR,
                        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_ERROR_GRAL));
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
            JOptionListBox jolb = new JOptionListBox(param,
                    KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.JOP_TITLE_SEARCH),
                    KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_U_USER));
            String nombreCuenta = jolb.getSelectedValue();
            if (nombreCuenta != null) {
                CuentaXUsuario c = modelo.search(nombreCuenta);
                if (c != null) {
                    setFields(c);
                    old = c;
                    startButtons(true);
                } else {
                    JOptionUtil.showErrorMessage(GUIConstants.ERROR,
                            KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_ERROR_GRAL));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            JOptionUtil.showErrorMessage(GUIConstants.ERROR, e.getMessage());
        }
    }

    /**
     * Permite mostrar u ocultar la contraseña.
     */
    public void eventoMostrar() {
        if (KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_SHOW)
                .equals(view.getBMostrar().getToolTipText())) {
            view.getTextPass().setEchoChar((char) 0);
            view.getBMostrar().setToolTipText(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_HIDE));
            view.getBMostrar().setText(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_HIDE));
        } else {
            view.getTextPass().setEchoChar(ConstantesKeyManager.HIDE_TEXT);
            view.getBMostrar().setToolTipText(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_SHOW));
            view.getBMostrar().setText(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_SHOW));
        }
    }

    /**
     * @return the instance
     */
    public static CuentaXUsuarioController getInstance() {
        return INSTANCE;
    }

}
