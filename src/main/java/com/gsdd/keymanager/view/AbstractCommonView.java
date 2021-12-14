package com.gsdd.keymanager.view;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.gsdd.gui.util.JPaginateTable;
import com.gsdd.gui.util.JValidateTextField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractCommonView extends JPanel {

  private static final long serialVersionUID = -8418224352606849605L;
  public static final int INI_X_LABEL_C1 = 40;
  public static final int INI_X_LABEL_C2 = 260;

  protected static final int DEF_WIDTH = 100;
  protected static final int DEF_HEIGHT = 20;
  protected static final int DEF_TEXT_SIZE = 64;

  protected void initBackGround(String resource) {
    try {
      JLabel backgroundLabel = new JLabel();
      ImageIcon ii = new ImageIcon(AbstractCommonView.class.getResource(resource));
      backgroundLabel.setIcon(ii);
      backgroundLabel.setBounds(0, 0, ii.getIconWidth(), ii.getIconHeight());
      add(backgroundLabel);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  protected JLabel addLabel(String initText, int x, int y) {
    JLabel label = new JLabel(initText);
    label.setBounds(x, y, DEF_WIDTH, DEF_HEIGHT);
    label.setForeground(Color.WHITE);
    add(label);
    return label;
  }

  protected JButton addButton(String text, int x, int y) {
    JButton button = new JButton(text);
    button.setToolTipText(text);
    button.setBounds(x, y, DEF_WIDTH, DEF_HEIGHT);
    add(button);
    return button;
  }

  protected JValidateTextField addValidateTextField(int x, int y) {
    JValidateTextField textField = new JValidateTextField();
    textField.setBounds(x, y, DEF_WIDTH, DEF_HEIGHT);
    textField.setColumns(DEF_TEXT_SIZE);
    textField.setMaxSize(DEF_TEXT_SIZE);
    add(textField);
    return textField;
  }

  protected JPaginateTable addPaginateTable() {
    JPaginateTable paginatedTable = new JPaginateTable();
    paginatedTable.setComponentBounds(40, 260, 600, 200);
    add(paginatedTable.getTableFirst());
    add(paginatedTable.getTablePrev());
    add(paginatedTable.getField());
    add(paginatedTable.getLabel());
    add(paginatedTable.getTableNext());
    add(paginatedTable.getTableLast());
    add(paginatedTable.getTableScroll());
    return paginatedTable;
  }

}
