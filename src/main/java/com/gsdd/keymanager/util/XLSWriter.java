package com.gsdd.keymanager.util;

import com.gsdd.keymanager.constants.KeyManagerConstants;
import com.gsdd.keymanager.entities.dto.AccountLoginDto;
import com.gsdd.keymanager.lang.KeyManagerLanguage;
import com.gsdd.xls.util.XLSUtil;
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
  /** El workbook a escribir. */
  private Workbook workbook;

  /**
   * @author Great System Development Dynamic <GSDD> <br>
   *     Alexander Galvis Grisales <br>
   *     alex.galvis.sistemas@gmail.com <br>
   * @version 1.0
   * @since 2015-12-07
   * @param objects lista de objetos.
   * @param sheetName nombre de la hoja definida.
   * @param headers encabezados del archivo de salida.
   * @param excelFilePath ruta de salida.
   * @throws FileNotFoundException cuando no encuentra el archivo.
   * @throws IOException cuando no se puede abrir el archivo.
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
   * Método que escribe en excel los resultados obtenidos.
   *
   * @author Great System Development Dynamic <GSDD> <br>
   *     Alexander Galvis Grisales <br>
   *     alex.galvis.sistemas@gmail.com <br>
   * @version 1.0
   * @since 2015-12-07
   * @param listR lista de objetos a mapear.
   * @param excelFilePath ruta de salida de excel.
   * @return true si se genera correctamente.
   */
  public boolean writeExcel(List<AccountLoginDto> listR, String excelFilePath) {
    try {
      log.info(excelFilePath);
      workbook =
          XLSUtil.getWorkbook(
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

  /**
   * @version 1.0
   * @param sheet Hoja en la que se escriben los encabezados.
   * @param type tipo de datos al que se agrega el encabezado.
   */
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
   * @version 1.0
   * @since 2015-12-07
   * @param dto objeto de tipo CuentaXUsuarioDto.
   * @param row fila en la que se copian los valores.
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
    String decrypedPass = CipherKeyManager.DECYPHER.apply(dto.getPass());
    Cell cellPass = row.createCell(4);
    cellPass.setCellValue(decrypedPass);
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
