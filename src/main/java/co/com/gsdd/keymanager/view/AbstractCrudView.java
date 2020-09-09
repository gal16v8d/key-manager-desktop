package co.com.gsdd.keymanager.view;

import javax.swing.JButton;

import co.com.gsdd.gui.util.JPaginateTable;
import co.com.gsdd.keymanager.lang.KeyManagerLanguage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractCrudView extends AbstractCommonView {

    private static final long serialVersionUID = 6947887554070713694L;
    protected static final int POS_INI_X_TEXT_C1 = 150;
    protected static final int POS_INI_X_TEXT_C2 = 370;
    protected static final int POS_INI_X_TEXT_C3 = 590;
    protected static final int POS_Y_ROW_1 = 60;
    protected static final int POS_Y_ROW_2 = 90;
    private static final int POS_Y_BUTTON = 210;

    private JPaginateTable dataTable;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JButton backButton;

    public void initTable() {
        setDataTable(addPaginateTable());
    }

    public void initCommonButtons() {
        setSaveButton(addButton(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.BUTTON_SAVE), INI_X_LABEL_C1,
                POS_Y_BUTTON));
        setUpdateButton(addButton(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.BUTTON_UPDATE),
                POS_INI_X_TEXT_C1, POS_Y_BUTTON));
        setDeleteButton(addButton(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.BUTTON_DELETE),
                INI_X_LABEL_C2, POS_Y_BUTTON));
        setSearchButton(addButton(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.BUTTON_SEARCH),
                POS_INI_X_TEXT_C2, POS_Y_BUTTON));
        setBackButton(addButton(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.BUTTON_BACK),
                POS_INI_X_TEXT_C3, POS_Y_BUTTON));
    }

}
