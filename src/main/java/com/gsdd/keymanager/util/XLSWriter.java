package com.gsdd.keymanager.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.gsdd.keymanager.constants.KeyManagerConstants;
import com.gsdd.keymanager.entities.dto.CuentaXUsuarioDto;
import com.gsdd.keymanager.lang.KeyManagerLanguage;
import com.gsdd.xls.util.XLSUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Great System Development Dynamic <GSDD> <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 * @since 2015-12-07
 */
@Slf4j
public class XLSWriter {
  /**
   * El workbook a escribir.
   */
  private Workbook workbook;

  /**
   * @author Great System Development Dynamic <GSDD> <br>
   *         Alexander Galvis Grisales <br>
   *         alex.galvis.sistemas@gmail.com <br>
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
      if (o instanceof CuentaXUsuarioDto) {
        writeData((CuentaXUsuarioDto) o, row);
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
   *         Alexander Galvis Grisales <br>
   *         alex.galvis.sistemas@gmail.com <br>
   * @version 1.0
   * @since 2015-12-07
   * @param listR lista de objetos a mapear.
   * @param excelFilePath ruta de salida de excel.
   * @return true si se genera correctamente.
   */
  public boolean writeExcel(List<CuentaXUsuarioDto> listR, String excelFilePath) {
    try {
      log.info(excelFilePath);
      workbook = XLSUtil.getWorkbook(null, excelFilePath,
          KeyManagerLanguage.getMessageByLocale(KeyManagerLanguage.MSG_INFO_XLS));
      if (!listR.isEmpty()) {
        buildSheet(listR, KeyManagerConstants.EXPORT_NAME,
            KeyManagerConstants.getAccountXUserTableModel(), excelFilePath);
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
   *         Alexander Galvis Grisales <br>
   *         alex.galvis.sistemas@gmail.com <br>
   * @version 1.0
   * @since 2015-12-07
   * @param dto objeto de tipo CuentaXUsuarioDto.
   * @param row fila en la que se copian los valores.
   */
  private void writeData(CuentaXUsuarioDto dto, Row row) {
    Cell cnu = row.createCell(0);
    cnu.setCellValue(dto.getNombreusuario());
    Cell cnc = row.createCell(1);
    cnc.setCellValue(dto.getNombrecuenta());
    Cell cnuc = row.createCell(2);
    cnuc.setCellValue(dto.getUsuario());
    String dp = CypherKeyManager.decodeKM(dto.getPass());
    Cell cncp = row.createCell(3);
    cncp.setCellValue(dp);
    Cell curl = row.createCell(4);
    curl.setCellValue(dto.getUrl());
    Date fd = dto.getFecha();
    Date fa = Date.valueOf(LocalDate.now());
    String fecha = KeyManagerConstants.getFormater().format(fd);
    Cell cnf = row.createCell(5);
    cnf.setCellValue(fecha);
    Cell cnr = row.createCell(6);
    cnr.setCellValue(KeyManagerConstants.getSuggestion(fa, fd));
  }
}
