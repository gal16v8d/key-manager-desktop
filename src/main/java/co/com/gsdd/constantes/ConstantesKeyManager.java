package co.com.gsdd.constantes;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import co.com.gsdd.constants.GralConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constantes asociadas al modelo del programa.
 * 
 * @author Great System Development Dynamic <GSDD> <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 * 
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConstantesKeyManager {

    public static final String DERBY_CONNECTION = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String DERBY_LOCATION = "jdbc:derby:.\\kmgr\\kmgr.DB;create=true";
    public static final String DERBY_MAIN_TABLE = "USUARIO";
    public static final String DERBY_MAIN_SCHEMA = "APP";
    public static final String DERBY_DATE_FORMAT = "yyyy-MM-dd";

    public static final String LOCALE_ES = "ES";
    public static final String FALLO = "[FALLO]";
    public static final String OK = "[OK]";

    public static final String FILE_MSJ = "El archivo seleccionado NO corresponde a archivos Excel (.xls , .xlsx)";
    public static final String EXC_EXT1 = "xlsx";
    public static final String EXC_EXT2 = "xls";
    public static final String EXPORT_NAME = "Kmgr-Exported";

    // Al pasar 180 días se muestra el msj recomendación.
    public static final int DIFERENCIA_CAMBIO = 180;
    public static final String RECOMENDACION = "Se recomienda cambiar está clave.";

    /**
     * Permite obtener el formater para el control de fechas.
     * 
     * @return el formater adecuado.
     */
    public static SimpleDateFormat getFormater() {
        return new SimpleDateFormat(DERBY_DATE_FORMAT, new Locale(LOCALE_ES));
    }

    /**
     * Permite generar o no una recomendación en base a la comparación de fechas.
     * 
     * @param fa
     *            la fecha actual.
     * @param fbd
     *            la fecha en base de datos.
     * @return la recomendacion en base a la comparacion.
     */
    public static final String getRecomendacion(Date fa, Date fbd) {
        long duration = fa.getTime() - fbd.getTime();
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        if (days >= DIFERENCIA_CAMBIO) {
            return RECOMENDACION;
        } else {
            return GralConstants.EMPTY;
        }
    }
}
