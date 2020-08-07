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

    public static final String BOTON_LOGIN = "Ingresar";
    public static final String BOTON_GUARDAR = "Guardar";
    public static final String BOTON_ACTUALIZAR = "Actualizar";
    public static final String BOTON_ELIMINAR = "Eliminar";
    public static final String BOTON_CONSULTAR = "Consultar";
    public static final String BOTON_VOLVER = "Volver";

    public static final String IMAGE_ICON = "/images/KeyMgr-Icon.png";
    public static final String IMAGE_CXU = "/images/KeyMgr-CXU.png";
    public static final String IMAGE_CUENTA = "/images/KeyMgr-Cuenta.png";
    public static final String IMAGE_PPAL = "/images/KeyMgr-Ppal.png";
    public static final String IMAGE_USER = "/images/KeyMgr-User.png";

    public static final String LABEL_USUARIO = "Usuario: ";
    public static final String LABEL_PASS = "Contraseña: ";
    public static final String LABEL_LOGGED = "Bienvenido (a): ";

    public static final String LABEL_U_PN = "Primer Nombre:";
    public static final String LABEL_U_PA = "Primer Apellido:";
    public static final String LABEL_U_USER = "Usuario:";
    public static final String LABEL_U_PASS = "Contraseña:";
    public static final String LABEL_U_ROL = "Rol:";

    public static final String LABEL_CXU_CUENTA = "Cuenta:";
    public static final String LABEL_CXU_UCUENTA = "Usuario Cuenta:";
    public static final String LABEL_CXU_PCUENTA = "Pass Cuenta:";
    public static final String LABEL_CXU_PURL = "Url:";

    public static final String LABEL_C_NOMBRE = "Nombre Cuenta:";

    public static final String MENUITEM_EXPORTAR = "Exportar Datos";
    public static final String MENUITEM_SESION = "Cerrar Sesión";
    public static final String MENUITEM_CREDITOS = "Créditos";
    public static final String MENUITEM_CONSULTA = "Consulta";
    public static final String MENUITEM_CUENTAXUSUARIO = "Cuentas por Usuarios";
    public static final String MENUITEM_USUARIO = "Usuarios";
    public static final String MENUITEM_SALIR = "Salir";

    public static final Integer LIMITE_TABLA_PAG = 10;

    protected static final String[] TABLA_CUENTA = { "Nombre" };
    protected static final String[] TABLA_CUENTAXUSER = { "Usuario", "Cuenta", "Usuario Cuenta", "Clave Cuenta", "Url",
            "Fecha", "Recomendación" };
    protected static final String[] TABLA_USUARIO = { "Primer Nombre", "Primer Apellido", "Usuario", "Rol" };

    public static final String TITULO_MAIN = "KeyManager v 1.0 - ";
    public static final String TITULO_INICIO = "Inicio de Sesión";
    public static final String TITULO_CUENTA = "Gestión de Cuentas";
    public static final String TITULO_CUENTAXUSER = "Gestión de Cuentas de Usuario";
    public static final String TITULO_LOGIN = "Inicio [Logeado]";
    public static final String TITULO_USUARIO = "Gestión de Usuarios";
    public static final String TITULO_FILECHOOSER = "Seleccione donde guardará el archivo";

    public static final String TOOL_ALFA = "Sólo se permite texto y números sin espacios.";
    public static final String TOOL_TEXT = "Sólo se permite texto sin espacios.";
    public static final String TOOL_MOSTRAR = "Mostrar";
    public static final String TOOL_ESCONDER = "Esconder";

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

    public static final String I_MSJ_SALIR = "¿Realmente desea salir?";
    public static final String I_MSJ_ACTUALIZADO = "Se actualizó correctamente: ";
    public static final String I_MSJ_BORRADO = "Se eliminó correctamente: ";
    public static final String I_MSJ_GUARDADO = "Se guardo correctamente: ";
    public static final String I_MSJ_EXPORTAR = "Se canceló el proceso de exportación.";
    public static final String E_MSJ_NO_EXISTE = "El usuario no existe.";
    public static final String E_MSJ_LOGIN = "Debes hacer login para navegar al menú.";
    public static final String E_MSJ_PASS = "La contraseña ingresada es incorrecta.";
    public static final String E_MSJ_INESPERADO = "Ooops... Acaba de ocurrir un error inesperado.";
    public static final String E_MSJ_DATOS = "Los datos que acaba de ingresar son incorrectos.";
    public static final String E_MSJ_FK_C = "Quizás otro usuario está utilizando la cuenta.";
    public static final String E_MSJ_EXPORT = "Como administrador no se pueden exportar datos de usuario.";
    public static final String E_MSJ_XLS = "No se pudieron exportar los datos.";
    public static final String E_MSJ_ADMIN = "Se requiere permisos de administrador para realizar la acción.";

    public static final String C_MSJ_INFO_T = "KeyManager v 1.0";
    public static final String C_MSJ_INFO_A1 = "Elaborado por: Alexander Galvis <alex.galvis.sistemas@gmail.com>";
    public static final String C_MSJ_INFO_A2 = "© 2016-2100 Todos los derechos reservados.";

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
