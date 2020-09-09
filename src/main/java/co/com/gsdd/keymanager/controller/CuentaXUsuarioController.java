package co.com.gsdd.keymanager.controller;

import java.awt.event.ActionEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;

import co.com.gsdd.constants.GralConstants;
import co.com.gsdd.gui.util.JPaginateTable;
import co.com.gsdd.keymanager.constants.KeyManagerConstants;
import co.com.gsdd.keymanager.ejb.CuentaXUsuarioEjb;
import co.com.gsdd.keymanager.ejb.UsuarioEjb;
import co.com.gsdd.keymanager.entities.CuentaXUsuario;
import co.com.gsdd.keymanager.entities.dto.CuentaXUsuarioDto;
import co.com.gsdd.keymanager.enums.RolEnum;
import co.com.gsdd.keymanager.lang.KeyManagerLanguage;
import co.com.gsdd.keymanager.util.CypherKeyManager;
import co.com.gsdd.keymanager.util.SessionData;
import co.com.gsdd.keymanager.view.CuentaXUsuarioView;
import co.com.gsdd.keymanager.view.MainView;
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
public class CuentaXUsuarioController implements CrudController<CuentaXUsuario> {

    private final CuentaXUsuarioEjb model;
    private final UsuarioEjb userModel;
    private final CuentaXUsuarioView view;
    private final MainView parentFrame;
    private CuentaXUsuario old;

    public CuentaXUsuarioController(MainView parentFrame) {
        this.model = new CuentaXUsuarioEjb();
        this.userModel = new UsuarioEjb();
        this.view = new CuentaXUsuarioView();
        this.parentFrame = parentFrame;
        loadView();
    }

    private void loadView() {
        try {
            buildView();
            fillCombo();
            addActionsToButtons();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void fillCombo() {
        List<String> user = getUserModel().suggest();
        for (String u : user) {
            getView().getComboUsuario().addItem(u);
        }
        getView().getComboUsuario().repaint();
    }

    private void addActionsToButtons() {
        getView().getShowOrHideButton().addActionListener((ActionEvent evt) -> showOrHidePass());
    }

    @Override
    public Logger getLogger() {
        return log;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CuentaXUsuarioEjb getEjbModel() {
        return getModel();
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void setTableModel(JPaginateTable tabla) {
        Class[] types = new Class[] { java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class };
        tabla.setTableModel(KeyManagerConstants.getAccountXUserTableModel(), types);
        tabla.setItemsPerPage(KeyManagerConstants.TBL_PAGE_SIZE);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateTableModel(DefaultTableModel dtm, List<?> data) {
        List<CuentaXUsuarioDto> listDB = (List<CuentaXUsuarioDto>) data;
        int i = 0;
        for (CuentaXUsuarioDto dto : listDB) {
            dtm.addRow(new Object[1]);
            dtm.setValueAt(dto.getNombreusuario(), i, 0);
            dtm.setValueAt(dto.getNombrecuenta(), i, 1);
            dtm.setValueAt(dto.getUsuario(), i, 2);
            String dp = showOrHidePass(dto.getPass(), false);
            dtm.setValueAt(dp, i, 3);
            dtm.setValueAt(dto.getUrl(), i, 4);
            Date fd = dto.getFecha();
            Date fa = Date.valueOf(LocalDate.now());
            String fecha = KeyManagerConstants.getFormater().format(fd);
            dtm.setValueAt(fecha, i, 5);
            dtm.setValueAt(KeyManagerConstants.getSuggestion(fa, fd), i, 6);
            i++;
        }
    }

    @Override
    public CuentaXUsuario getDataFromForm() {
        CuentaXUsuario data = null;
        try {
            data = new CuentaXUsuario();
            data.setNombreCuenta(getView().getTextCuenta().getText().trim());
            SessionData sessionData = SessionData.getInstance();
            data.setCodigousuario(String.valueOf(sessionData.getSessionDto().getRol()).equals(RolEnum.ADMIN.getCode())
                    ? getUserModel().search((String) getView().getComboUsuario().getSelectedItem()).getCodigousuario()
                    : sessionData.getSessionDto().getCodigousuario());
            data.setUsername(getView().getTextUserName().getText().trim());
            data.setPassword(CypherKeyManager.encodeKM(String.valueOf(getView().getTextPass().getPassword()).trim()));
            data.setUrl(getView().getTextUrl().getText());
            return data;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private String showOrHidePass(String ePass, boolean show) {
        return show ? CypherKeyManager.decodeKM(ePass) : KeyManagerConstants.MASK_TEXTO;
    }

    @Override
    public boolean validateData(CuentaXUsuario data) {
        return (data != null && data.getNombreCuenta() != null && data.getCodigousuario() != null
                && data.getUsername() != null && data.getPassword() != null);
    }

    @Override
    public String getSuccessMsg(String loadMsg, CuentaXUsuario data) {
        return KeyManagerLanguage.getMessageByLocale(loadMsg) + data.getNombreCuenta();
    }

    @Override
    public void clearFields() {
        getView().getTextCuenta().setText(GralConstants.EMPTY);
        getView().getTextUrl().setText(GralConstants.EMPTY);
        getView().getTextUserName().setText(GralConstants.EMPTY);
        getView().getTextPass().setText(GralConstants.EMPTY);
    }

    public void clearCombo() {
        getView().getComboUsuario().removeAllItems();
    }

    @Override
    public void performUIActionsAfterSave() {
        clearFields();
        clearCombo();
        fillCombo();
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
    public void performUIActionsAfterSearch(CuentaXUsuario searchData) {
        setFields(searchData);
        setOld(searchData);
        startButtons(true);
    }

    public void setFields(CuentaXUsuario dto) {
        getView().getComboUsuario().setSelectedItem(dto.getUsername());
        getView().getTextCuenta().setText(dto.getNombreCuenta());
        getView().getTextUserName().setText(dto.getUsername());
        getView().getTextPass().setText(CypherKeyManager.decodeKM(dto.getPassword()));
        getView().getTextUrl().setText(dto.getUrl());
    }

    public void showOrHidePass() {
        if (KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_SHOW)
                .equals(getView().getShowOrHideButton().getToolTipText())) {
            getView().getTextPass().setEchoChar((char) 0);
            getView().getShowOrHideButton()
                    .setToolTipText(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_HIDE));
            getView().getShowOrHideButton()
                    .setText(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_HIDE));
        } else {
            getView().getTextPass().setEchoChar(KeyManagerConstants.HIDE_TEXT);
            getView().getShowOrHideButton()
                    .setToolTipText(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_SHOW));
            getView().getShowOrHideButton()
                    .setText(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TOOL_SHOW));
        }
    }

}
