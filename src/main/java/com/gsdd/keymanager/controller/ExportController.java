package com.gsdd.keymanager.controller;

import com.gsdd.constants.GUIConstants;
import com.gsdd.constants.GralConstants;
import com.gsdd.gui.util.JOptionUtil;
import com.gsdd.keymanager.constants.KeyManagerConstants;
import com.gsdd.keymanager.entities.Account;
import com.gsdd.keymanager.lang.KeyManagerLanguage;
import com.gsdd.keymanager.service.AccountLoginService;
import com.gsdd.keymanager.util.CipherKeyManager;
import com.gsdd.keymanager.util.XLSWriter;
import java.util.Objects;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExportController {

  public AccountLoginService getEjbModel() {
    return new AccountLoginService();
  }

  public boolean exportData(String out, Account dto) {
    String passw = null;
    JPanel panel = new JPanel();
    JLabel label =
        new JLabel(KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.JOP_PASS_MSG));
    JPasswordField pass = new JPasswordField(32);
    panel.add(label);
    panel.add(pass);
    String[] options =
        new String[] {
          KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.JOP_PASS_OK),
          KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.JOP_PASS_CANCEL)
        };
    int option =
        JOptionPane.showOptionDialog(
            null,
            panel,
            KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.JOP_PASS),
            JOptionPane.NO_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[1]);
    // 0 : Boton Aceptar
    if (option == 0) {
      passw = String.valueOf(pass.getPassword());
      log.info(dto.getLogin());
      String currentPass = CipherKeyManager.DECYPHER.apply(dto.getPassword());
      boolean passMatch = Objects.equals(passw.trim(), currentPass);
      log.info("{}", passMatch);
      if (passMatch) {
        XLSWriter writer = new XLSWriter();
        boolean b =
            writer.writeExcel(
                getEjbModel().list(),
                new StringBuilder(out)
                    .append(System.getProperty("file.separator"))
                    .append(KeyManagerConstants.EXPORT_NAME)
                    .append(GralConstants.DOT)
                    .append(KeyManagerConstants.EXC_EXT1)
                    .toString());
        log.info("Write on xls file -> {}", b);
        return b;
      }
    } else {
      JOptionUtil.showErrorMessage(
          GUIConstants.ERROR,
          KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_ERROR_PASS));
    }

    return false;
  }
}
