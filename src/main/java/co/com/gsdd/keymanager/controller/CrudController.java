package co.com.gsdd.keymanager.controller;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.slf4j.Logger;

import co.com.gsdd.constants.GUIConstants;
import co.com.gsdd.gui.util.JOptionListBox;
import co.com.gsdd.gui.util.JOptionUtil;
import co.com.gsdd.gui.util.JPaginateTable;
import co.com.gsdd.keymanager.ejb.Ejb;
import co.com.gsdd.keymanager.enums.ButtonOptions;
import co.com.gsdd.keymanager.enums.MenuOption;
import co.com.gsdd.keymanager.lang.KeyManagerLanguage;
import co.com.gsdd.keymanager.view.AbstractCrudView;
import co.com.gsdd.keymanager.view.MainView;

/**
 * 
 * @author Great System Development Dynamic <GSDD> <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 * 
 */
public interface CrudController<T extends Serializable> {

    Logger getLogger();

    <U extends Ejb<T>> U getEjbModel();

    MainView getParentFrame();

    <V extends AbstractCrudView> V getView();

    T getOld();

    default void startButtons(boolean flag) {
        getView().getUpdateButton().setEnabled(flag);
        getView().getDeleteButton().setEnabled(flag);
        getView().getSaveButton().setEnabled(!flag);
    }

    default void startTable(JPaginateTable table) {
        setTableModel(table);
        fillTable(table);
    }

    default void buildView() {
        startButtons(false);
        startTable(getView().getDataTable());
        getView().getSaveButton().addActionListener((ActionEvent evt) -> selectOption(ButtonOptions.SAVE));
        getView().getUpdateButton().addActionListener((ActionEvent evt) -> selectOption(ButtonOptions.UPDATE));
        getView().getDeleteButton().addActionListener((ActionEvent evt) -> selectOption(ButtonOptions.DELETE));
        getView().getSearchButton().addActionListener((ActionEvent evt) -> selectOption(ButtonOptions.SEARCH));
        getView().getBackButton().addActionListener((ActionEvent evt) -> selectOption(ButtonOptions.BACK));
    }

    void updateTableModel(DefaultTableModel dtm, List<?> data);

    void setTableModel(JPaginateTable table);

    default void fillTable(JPaginateTable table) {
        List<?> listDB = getEjbModel().list();
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        int tam;
        tam = dtm.getRowCount();
        for (int i = 0; i < tam; i++) {
            dtm.removeRow(dtm.getRowCount() - 1);
        }
        if (listDB != null) {
            updateTableModel(dtm, listDB);
        }
        table.setPaginateSorter(new TableRowSorter<TableModel>(dtm));
        table.setRowSorter(table.getPaginateSorter());
        table.initFilterAndButton(table.getPaginateSorter(), table.getItemsPerPage());
    }

    T getDataFromForm();

    boolean validateData(T data);

    String getSuccessMsg(String loadMsg, T data);

    default void selectOption(ButtonOptions op) {
        switch (op) {
        case SAVE:
            saveData();
            break;
        case UPDATE:
            updateData(getOld());
            break;
        case DELETE:
            deleteData();
            break;
        case SEARCH:
            searchData();
            break;
        case BACK:
            getParentFrame().sendRedirect(MenuOption.LOGIN.name(),
                    KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.TITLE_LOGIN));
            break;
        default:
            break;
        }
    }

    void clearFields();

    void performUIActionsAfterSave();

    default void saveData() {
        try {
            T data = getDataFromForm();
            if (validateData(data)) {
                if (getEjbModel().save(data)) {
                    String msg = getSuccessMsg(KeyManagerLanguage.MSG_INFO_SAVE, data);
                    getLogger().info(msg);
                    fillTable(getView().getDataTable());
                    JOptionUtil.showAppMessage(
                            KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.JOP_TITLE_SUCCESS), msg,
                            JOptionPane.INFORMATION_MESSAGE);
                    performUIActionsAfterSave();
                } else {
                    JOptionUtil.showErrorMessage(GUIConstants.ERROR,
                            KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_ERROR_GRAL));
                }
            } else {
                JOptionUtil.showErrorMessage(GUIConstants.ERROR,
                        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_ERROR_DATA));
            }
        } catch (Exception e) {
            getLogger().error(e.getMessage(), e);
            JOptionUtil.showErrorMessage(GUIConstants.ERROR, e.getMessage());
        }
    }

    void performUIActionsAfterUpdate();

    default void updateData(T oldData) {
        try {
            T data = getDataFromForm();
            if (validateData(data)) {
                if (getEjbModel().update(data, oldData != null ? oldData : data)) {
                    String msg = getSuccessMsg(KeyManagerLanguage.MSG_INFO_UPDATE, data);
                    getLogger().info(msg);
                    fillTable(getView().getDataTable());
                    JOptionUtil.showAppMessage(
                            KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.JOP_TITLE_SUCCESS), msg,
                            JOptionPane.INFORMATION_MESSAGE);
                    performUIActionsAfterUpdate();
                } else {
                    JOptionUtil.showErrorMessage(GUIConstants.ERROR,
                            KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_ERROR_GRAL));
                }
            } else {
                JOptionUtil.showErrorMessage(GUIConstants.ERROR,
                        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_ERROR_DATA));
            }
        } catch (Exception e) {
            getLogger().error(e.getMessage(), e);
            JOptionUtil.showErrorMessage(GUIConstants.ERROR, e.getMessage());
        }
    }

    void performUIActionsAfterDelete();

    default void deleteData() {
        try {
            T data = getDataFromForm();
            if (getEjbModel().delete(data)) {
                String msg = getSuccessMsg(KeyManagerLanguage.MSG_INFO_DELETE, data);
                getLogger().info(msg);
                fillTable(getView().getDataTable());
                JOptionUtil.showAppMessage(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.JOP_TITLE_SUCCESS),
                        msg, JOptionPane.INFORMATION_MESSAGE);
                performUIActionsAfterDelete();
            } else {
                JOptionUtil.showErrorMessage(GUIConstants.ERROR,
                        KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_ERROR_GRAL));
            }
        } catch (Exception e) {
            getLogger().error(e.getMessage(), e);
            JOptionUtil.showErrorMessage(GUIConstants.ERROR, e.getMessage());
        }
    }

    void performUIActionsAfterSearch(T searchData);

    default void searchData() {
        try {
            List<String> param = getEjbModel().suggest();
            JOptionListBox jolb = new JOptionListBox(param,
                    KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.JOP_TITLE_SEARCH),
                    KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.LABEL_U_USER));
            String value = jolb.getSelectedValue();
            if (value != null) {
                T searchData = getEjbModel().search(value);
                if (searchData != null) {
                    performUIActionsAfterSearch(searchData);
                } else {
                    JOptionUtil.showErrorMessage(GUIConstants.ERROR,
                            KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_ERROR_GRAL));
                }
            }
        } catch (Exception e) {
            getLogger().error(e.getMessage(), e);
            JOptionUtil.showErrorMessage(GUIConstants.ERROR, e.getMessage());
        }
    }

}
