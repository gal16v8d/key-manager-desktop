package co.com.gsdd.constantes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constantes Generales usadas en los objetos de interfaz.
 * 
 * @author Great System Development Dynamic <GSDD> <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConstantesInterfaz {

    public static final String IMAGE_ICON = "/images/KeyMgr-Icon.png";
    public static final String IMAGE_CXU = "/images/KeyMgr-CXU.png";
    public static final String IMAGE_CUENTA = "/images/KeyMgr-Cuenta.png";
    public static final String IMAGE_PPAL = "/images/KeyMgr-Ppal.png";
    public static final String IMAGE_USER = "/images/KeyMgr-User.png";

    public static final String LABEL_USUARIO = "Usuario: ";
    public static final String LABEL_PASS = "Contraseña: ";
    public static final String LABEL_LOGGED = "Bienvenido (a): ";

    public static final String LABEL_C_NOMBRE = "Nombre Cuenta:";

    public static final Integer LIMITE_TABLA_PAG = 10;

    protected static final String[] TABLA_CUENTA = { "Nombre" };
    protected static final String[] TABLA_CUENTAXUSER = { "Usuario", "Cuenta", "Usuario Cuenta", "Clave Cuenta", "Url",
            "Fecha", "Recomendación" };
    protected static final String[] TABLA_USUARIO = { "Primer Nombre", "Primer Apellido", "Usuario", "Rol" };

    public static final String JOP_TITULO_EXPORTAR = "EXPORTAR";
    public static final String JOP_TITULO_EXITO = "ÉXITO";
    public static final String JOP_TITULO_ERROR = "ERROR";
    public static final String JOP_TITULO_BUSCAR = "BUSCAR";
    public static final String JOP_TITULO_CONSULTA = "Ingresa la consulta a ejecutar:";
    public static final String JOP_PASS = "Exportar. Confirma tus datos";
    public static final String JOP_PASS_CANCEL = "Cancelar";
    public static final String JOP_PASS_MSJ = "Escribe tu contraseña: ";
    public static final String JOP_PASS_OK = "Aceptar";
    public static final String JOP_EXITO = "Se realizó la exportación de datos exitosamente";

    public static final String E_MSJ_NO_EXISTE = "El usuario no existe.";
    public static final String E_MSJ_LOGIN = "Debes hacer login para navegar al menú.";
    public static final String E_MSJ_PASS = "La contraseña ingresada es incorrecta.";
    public static final String E_MSJ_INESPERADO = "Ooops... Acaba de ocurrir un error inesperado.";
    public static final String E_MSJ_DATOS = "Los datos que acaba de ingresar son incorrectos.";
    public static final String E_MSJ_FK_C = "Quizás otro usuario está utilizando la cuenta.";
    public static final String E_MSJ_EXPORT = "Como administrador no se pueden exportar datos de usuario.";
    public static final String E_MSJ_XLS = "No se pudieron exportar los datos.";
    public static final String E_MSJ_ADMIN = "Se requiere permisos de administrador para realizar la acción.";

    public static final String ARROW_FIRST = "|<";
    public static final String ARROW_PREV = "<";
    public static final String ARROW_NEXT = ">";
    public static final String ARROW_LAST = ">|";

    public static final String MASK_TEXTO = "******";
    public static final char ESCONDER_TEXTO = '*';

    /**
     * @return the tablaCuenta
     */
    public static String[] getTablaCuenta() {
        return TABLA_CUENTA;
    }

    /**
     * @return the tablaCuentaxuser
     */
    public static String[] getTablaCuentaxuser() {
        return TABLA_CUENTAXUSER;
    }

    /**
     * @return the tablaUsuario
     */
    public static String[] getTablaUsuario() {
        return TABLA_USUARIO;
    }

}
