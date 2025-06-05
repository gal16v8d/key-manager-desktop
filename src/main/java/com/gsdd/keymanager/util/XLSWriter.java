package com.gsdd.keymanager.util;

import com.gsdd.keymanager.constants.KeyManagerConstants;
import com.gsdd.keymanager.entities.dto.AccountLoginDto;
import com.gsdd.keymanager.lang.KeyManagerLanguage;
import com.gsdd.xls.util.XlsUtil;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author Great System Development Dynamic <GSDD> <br>
 *     Alexander Galvis Grisales <br>
 *     alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 * @since 2015-12-07
 */
@Slf4j
public class XLSWriter {
  /** Workbook to write. */
  private Workbook workbook;

  /**
   * @author Great System Development Dynamic <GSDD> <br>
   *     Alexander Galvis Grisales <br>
   *     alex.galvis.sistemas@gmail.com <br>
   * @version 1.0
   * @since 2015-12-07
   * @param objects object list.
   * @param sheetName sheet name.
   * @param headers headers for output file.
   * @param excelFilePath file path.
   * @throws FileNotFoundException if not found on file system.
   * @throws IOException when can not be open by any reason.
   */
  private void buildSheet(List<?> objects, String sheetName, String[] headers, String excelFilePath)
      throws IOException {
    Sheet sheet = workbook.createSheet(sheetName);
    int rowCount = 0;
    createHeaderRow(sheet, headers);
    for (Object o : objects) {
      Row row = sheet.createRow(++rowCount);
      if (o instanceof AccountLoginDto data) {
        writeData(data, row);
      }
    }
    try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
      workbook.write(outputStream);
    }
  }

  /**
   * write to workbook the results.
   *
   * @author Great System Development Dynamic <GSDD> <br>
   *     Alexander Galvis Grisales <br>
   *     alex.galvis.sistemas@gmail.com <br>
   * @since 2015-12-07
   * @param listR object list to write.
   * @param excelFilePath xls file path.
   * @return true if everything goes well.
   */
  public boolean writeExcel(List<AccountLoginDto> listR, String excelFilePath) {
    try {
      log.info(excelFilePath);
      workbook =
          XlsUtil.getWorkbook(
              null,
              excelFilePath,
              KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_INFO_XLS));
      if (!listR.isEmpty()) {
        buildSheet(
            listR,
            KeyManagerConstants.EXPORT_NAME,
            KeyManagerConstants.getAccountLoginTableModel(),
            excelFilePath);
      }
      return true;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    } finally {
      try {
        if (workbook != null) {
          workbook.close();
        }
      } catch (IOException e) {
        log.error(e.getMessage(), e);
      }
    }
    return false;
  }

  private void createHeaderRow(Sheet sheet, String[] headers) {
    Row row = sheet.createRow(0);
    int cellCountC = 0;
    for (String s : headers) {
      Cell cellTitle = row.createCell(cellCountC);
      cellTitle.setCellValue(s);
      cellCountC++;
    }
  }

  /**
   * @author Great System Development Dynamic <GSDD> <br>
   *     Alexander Galvis Grisales <br>
   *     alex.galvis.sistemas@gmail.com <br>
   * @since 2015-12-07
   * @param dto account data.
   * @param row to copy the diff values.
   */
  private void writeData(AccountLoginDto dto, Row row) {
    Cell cellUser = row.createCell(0);
    cellUser.setCellValue(dto.getSessionLogin());
    Cell cellAccountName = row.createCell(1);
    cellAccountName.setCellValue(dto.getAccountName());
    Cell cellType = row.createCell(2);
    cellType.setCellValue(dto.getAccountType());
    Cell cellLogin = row.createCell(3);
    cellLogin.setCellValue(dto.getLogin());
    String decryptedPass = CipherKeyManager.DECIPHER.apply(dto.getPass());
    Cell cellPass = row.createCell(4);
    cellPass.setCellValue(decryptedPass);
    Cell cellUrl = row.createCell(5);
    cellUrl.setCellValue(dto.getUrl());
    Date fd = dto.getModificationDate();
    Date fa = Date.valueOf(LocalDate.now());
    String date = KeyManagerConstants.getFormater().format(fd);
    Cell cellDate = row.createCell(6);
    cellDate.setCellValue(date);
    Cell cellSuggest = row.createCell(7);
    cellSuggest.setCellValue(KeyManagerConstants.SHOW_SUGGESTION.apply(fa, fd));
  }
}
