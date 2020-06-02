package co.com.gsdd.constantes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Great System Development Dynamic <GSDD> <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 * 
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConstantesQuery {

    private static final String TABLA_CXU = " cuentaxusuario cxu ";
    private static final String CAMPO_CXU_CU = " cxu.codigousuario ";
    private static final String CAMPO_CXU_NC = " cxu.nombrecuenta ";

    private static final String SELECT = "SELECT";
    private static final String DELETE = "DELETE";
    private static final String FROM = " FROM ";
    private static final String WHERE = " WHERE ";
    private static final String AND = " AND ";
    private static final String ORDER_BY = " ORDER BY ";
    private static final String ASC = "ASC";
    private static final String IGUAL_PARAM = "= ?";

    // DELETE QUERY
    public static final String DELETE_CUENTAXUSER = new StringBuilder().append(DELETE).append(FROM).append(TABLA_CXU)
            .append(WHERE).append(CAMPO_CXU_NC).append(IGUAL_PARAM).append(AND).append(CAMPO_CXU_CU).append(IGUAL_PARAM)
            .toString();

    public static final String DELETE_USER = DELETE + FROM + "usuario" + WHERE + "codigousuario" + IGUAL_PARAM;

    // INSERT QUERY
    public static final String INSERT_CUENTAXUSER = "INSERT INTO cuentaxusuario VALUES (?,?,?,?,?,?)";
    public static final String INSERT_USER = "INSERT INTO usuario VALUES (?,?,?,?,?,?)";

    // LIST QUERY
    public static final String LISTAR_CUENTAS = new StringBuilder().append(SELECT).append(CAMPO_CXU_NC).append(FROM)
            .append(TABLA_CXU).append(ORDER_BY).append(CAMPO_CXU_NC).append(ASC).toString();

    public static final String LISTAR_CUENTAXUSER = "SELECT cxu.codigousuario," + CAMPO_CXU_NC + ",u.username,"
            + "cxu.url, cxu.username, cxu.password, cxu.fecha " + FROM + TABLA_CXU
            + "JOIN usuario u ON (u.codigousuario = " + CAMPO_CXU_CU + ") " + "WHERE u.codigousuario = ? " + ORDER_BY
            + "u.username," + CAMPO_CXU_NC;

    public static final String LISTAR_CUENTAXUSER_ADMIN = "SELECT cxu.codigousuario," + CAMPO_CXU_NC + ",u.username,"
            + "cxu.url, cxu.username, cxu.password, cxu.fecha " + FROM + TABLA_CXU
            + "JOIN usuario u ON (u.codigousuario = " + CAMPO_CXU_CU + ") " + ORDER_BY + "u.username," + CAMPO_CXU_NC;

    public static final String LISTAR_CUENTAXUSER_UNUSED = "SELECT codigocuenta, nombre FROM cuenta "
            + "WHERE codigocuenta NOT IN ( " + "SELECT DISTINCT(codigocuenta) " + "FROM cuentaxusuario cxu "
            + "WHERE cxu.codigousuario = ?) " + "ORDER BY nombre";

    public static final String LISTAR_USUARIOS = "SELECT codigousuario, primer_nombre, primer_apellido, username, password, rol "
            + "FROM usuario " + "WHERE codigousuario = ? " + "ORDER BY primer_nombre, primer_apellido, username";

    public static final String LISTAR_USUARIOS_ADMIN = "SELECT codigousuario, primer_nombre, primer_apellido, username, password, rol "
            + "FROM usuario " + "ORDER BY primer_nombre, primer_apellido, username";

    // SP QUERY
    public static final String LOGIN = "SELECT codigousuario, password, primer_nombre, primer_apellido, rol "
            + "FROM usuario WHERE username = ?";

    // SEARCH QUERY
    public static final String SEARCH_CUENTAXUSER = SELECT + CAMPO_CXU_CU + ", cxu.username, cxu.password,"
            + CAMPO_CXU_NC + ",cxu.url " + "FROM cuentaxusuario cxu " + "JOIN usuario u ON (u.codigousuario = "
            + CAMPO_CXU_CU + ") " + WHERE + CAMPO_CXU_NC + IGUAL_PARAM + AND + CAMPO_CXU_CU + IGUAL_PARAM;
    public static final String SEARCH_USER = "SELECT codigousuario, primer_nombre, primer_apellido, username, password, rol "
            + "FROM usuario WHERE username = ?";

    // UPDATE QUERY
    public static final String UPDATE_CUENTAXUSER = "UPDATE cuentaxusuario SET nombrecuenta = ?, codigousuario = ?,"
            + "username = ?, password = ?, url = ?, fecha = ? " + "WHERE nombrecuenta = ? AND codigousuario = ?";
    public static final String UPDATE_USER = "UPDATE usuario SET primer_nombre = ?, primer_apellido = ?, "
            + "username = ?, password = ? WHERE codigousuario = ?";

    // VALIDATION QUERY
    public static final String VALIDAR_DATA = "SELECT COUNT(*) FROM usuario";

}
