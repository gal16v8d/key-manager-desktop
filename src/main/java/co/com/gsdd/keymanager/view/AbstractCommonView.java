package co.com.gsdd.keymanager.view;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import co.com.gsdd.gui.util.JPaginateTable;
import co.com.gsdd.gui.util.JValidateTextField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractCommonView extends JPanel {

    private static final long serialVersionUID = -8418224352606849605L;
    public static final int INI_X_LABEL_C1 = 40;
    public static final int INI_X_LABEL_C2 = 260;

    protected static final int DEF_ANCHO = 100;
    protected static final int DEF_ALTO = 20;
    protected static final int DEF_TAMANO_TEXTO = 64;

    /**
     * Construye elementos de ventana.
     */
    protected void initBackGround(String recurso) {
        try {
            JLabel labelFondo = new JLabel();
            ImageIcon ii = new ImageIcon(AbstractCommonView.class.getResource(recurso));
            labelFondo.setIcon(ii);
            labelFondo.setBounds(0, 0, ii.getIconWidth(), ii.getIconHeight());
            add(labelFondo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    protected JLabel addLabel(String initText, int x, int y) {
        JLabel label = new JLabel(initText);
        label.setBounds(x, y, DEF_ANCHO, DEF_ALTO);
        label.setForeground(Color.WHITE);
        add(label);
        return label;
    }

    protected JButton addButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setToolTipText(text);
        button.setBounds(x, y, DEF_ANCHO, DEF_ALTO);
        add(button);
        return button;
    }

    protected JValidateTextField addValidateTextField(int x, int y) {
        JValidateTextField textField = new JValidateTextField();
        textField.setBounds(x, y, DEF_ANCHO, DEF_ALTO);
        textField.setColumns(DEF_TAMANO_TEXTO);
        textField.setMaxSize(DEF_TAMANO_TEXTO);
        add(textField);
        return textField;
    }

    protected JPaginateTable addPaginateTable() {
        JPaginateTable tablaPaginada = new JPaginateTable();
        tablaPaginada.setComponentBounds(40, 260, 600, 200);
        add(tablaPaginada.getTableFirst());
        add(tablaPaginada.getTablePrev());
        add(tablaPaginada.getField());
        add(tablaPaginada.getLabel());
        add(tablaPaginada.getTableNext());
        add(tablaPaginada.getTableLast());
        add(tablaPaginada.getTableScroll());
        return tablaPaginada;
    }

}
