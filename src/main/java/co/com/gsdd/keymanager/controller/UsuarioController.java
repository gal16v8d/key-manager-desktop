package co.com.gsdd.keymanager.controller;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;

import co.com.gsdd.constants.GralConstants;
import co.com.gsdd.gui.util.JPaginateTable;
import co.com.gsdd.keymanager.constants.KeyManagerConstants;
import co.com.gsdd.keymanager.ejb.UsuarioEjb;
import co.com.gsdd.keymanager.entities.Usuario;
import co.com.gsdd.keymanager.enums.RolEnum;
import co.com.gsdd.keymanager.lang.KeyManagerLanguage;
import co.com.gsdd.keymanager.util.CypherKeyManager;
import co.com.gsdd.keymanager.view.MainView;
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
public class UsuarioController implements CrudController<Usuario> {

    private final UsuarioEjb model;
    private final UsuarioView view;
    private final MainView parentFrame;
    private Usuario old;

    public UsuarioController(MainView parentFrame) {
        this.model = new UsuarioEjb();
        this.view = new UsuarioView();
        this.parentFrame = parentFrame;
        loadView();
    }

    private void loadView() {
        try {
            buildView();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public Logger getLogger() {
        return log;
    }

    @Override
    @SuppressWarnings("unchecked")
    public UsuarioEjb getEjbModel() {
        return getModel();
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void setTableModel(JPaginateTable tabla) {
        Class[] types = new Class[] { java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class };
        tabla.setTableModel(KeyManagerConstants.getUserTableModel(), types);
        tabla.setItemsPerPage(KeyManagerConstants.TBL_PAGE_SIZE);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateTableModel(DefaultTableModel dtm, List<?> data) {
        List<Usuario> listDB = (List<Usuario>) data;
        int i = 0;
        for (Usuario u : listDB) {
            dtm.addRow(new Object[1]);
            dtm.setValueAt(u.getPrimerNombre(), i, 0);
            dtm.setValueAt(u.getPrimerApellido(), i, 1);
            dtm.setValueAt(u.getUsername(), i, 2);
            dtm.setValueAt(String.valueOf(u.getRol()).equals(RolEnum.ADMIN.getCode()) ? RolEnum.ADMIN.name()
                    : RolEnum.USER.name(), i, 3);
            i++;
        }
    }

    @Override
    public Usuario getDataFromForm() {
        Usuario datos = null;
        try {
            datos = new Usuario();
            String textoLabel = getView().getLabelPk().getText();
            Long id = (textoLabel != null && !textoLabel.equals(GralConstants.EMPTY) ? Long.parseLong(textoLabel.trim())
                    : (long) (System.nanoTime() * (Math.random())));
            datos.setCodigousuario(id);
            datos.setPrimerNombre(getView().getTextPNombre().getText().trim());
            datos.setPrimerApellido(getView().getTextPApellido().getText().trim());
            datos.setUsername(getView().getTextUserName().getText().trim());
            datos.setPassword(CypherKeyManager.encodeKM(String.valueOf(getView().getTextPass().getPassword()).trim()));
            datos.setRol(RolEnum.ADMIN.name().equals(getView().getLabelVRol().getText())
                    ? Long.valueOf(RolEnum.ADMIN.getCode()) : Long.valueOf(RolEnum.USER.getCode()));
            return datos;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean validateData(Usuario data) {
        return (data != null && data.getPrimerNombre() != null && data.getPrimerApellido() != null
                && data.getUsername() != null && data.getPassword() != null);
    }

    @Override
    public String getSuccessMsg(String loadMsg, Usuario data) {
        return KeyManagerLanguage.getMessageByLocale(loadMsg) + data.getUsername();
    }

    @Override
    public void clearFields() {
        getView().getLabelPk().setText(GralConstants.EMPTY);
        getView().getTextPNombre().setText(GralConstants.EMPTY);
        getView().getTextPApellido().setText(GralConstants.EMPTY);
        getView().getTextUserName().setText(GralConstants.EMPTY);
        getView().getTextPass().setText(GralConstants.EMPTY);
    }

    @Override
    public void performUIActionsAfterSave() {
        clearFields();
    }

    @Override
    public void performUIActionsAfterUpdate() {
        performUIActionsAfterSave();
        startButtons(false);
        fillTable(getView().getDataTable());
    }

    @Override
    public void performUIActionsAfterDelete() {
        performUIActionsAfterUpdate();
    }

    @Override
    public void performUIActionsAfterSearch(Usuario searchData) {
        setFields(searchData);
        startButtons(true);
    }

    public void setFields(Usuario dto) {
        getView().getLabelPk().setText(String.valueOf(dto.getCodigousuario()));
        getView().getTextPNombre().setText(dto.getPrimerNombre());
        getView().getTextPApellido().setText(dto.getPrimerApellido());
        getView().getTextUserName().setText(dto.getUsername());
        getView().getTextPass().setText(CypherKeyManager.decodeKM(dto.getPassword()));
        getView().getLabelVRol().setText((RolEnum.ADMIN.getCode().equals(String.valueOf(dto.getRol())))
                ? RolEnum.ADMIN.name() : RolEnum.USER.name());
    }

}
