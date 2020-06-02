package co.com.gsdd.keymanager.ejb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import co.com.gsdd.constantes.ConstantesQuery;
import co.com.gsdd.dbutil.DBConnection;
import co.com.gsdd.keymanager.controller.PrincipalController;
import co.com.gsdd.keymanager.entities.Usuario;
import co.com.gsdd.keymanager.enums.RolEnum;
import co.com.gsdd.keymanager.util.CifradoKeyManager;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Great System Development Dynamic <GSDD> <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 * 
 */
@Slf4j
public class UsuarioEjb {

    private static final UsuarioEjb INSTANCE = new UsuarioEjb();

    /**
     * Login en la aplicación.
     * 
     * @param user
     *            el usuario.
     * @param pass
     *            la contraseña.
     * @return el usuario si existe o null.
     */
    public Usuario login(String user, String pass) {
        Usuario u = null;
        try {
            DBConnection.getInstance()
                    .setPst(DBConnection.getInstance().getCon().prepareStatement(ConstantesQuery.LOGIN));
            DBConnection.getInstance().getPst().setString(1, user);
            DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
            while (DBConnection.getInstance().getRs().next()) {
                u = new Usuario();
                u.setCodigousuario(DBConnection.getInstance().getRs().getLong(1));
                u.setPassword(DBConnection.getInstance().getRs().getString(2));
                boolean valido = Objects.equals(pass, CifradoKeyManager.descifrarKM(u.getPassword()));
                if (!valido) {
                    u = null;
                } else {
                    u.setPrimerNombre(DBConnection.getInstance().getRs().getString(3));
                    u.setPrimerApellido(DBConnection.getInstance().getRs().getString(4));
                    u.setRol(DBConnection.getInstance().getRs().getLong(5));
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            DBConnection.getInstance().closeQuery();
        }
        return u;
    }

    /**
     * Guarda datos de usuario en la aplicación.
     * 
     * @param u
     *            el usuario a actualizar.
     * @return TRUE si el proceso se completa exitosamente.
     */
    public Boolean save(Usuario u) {
        boolean retorno = Boolean.FALSE;
        try {
            DBConnection.getInstance()
                    .setPst(DBConnection.getInstance().getCon().prepareStatement(ConstantesQuery.INSERT_USER));
            DBConnection.getInstance().getPst().setLong(1, u.getCodigousuario());
            DBConnection.getInstance().getPst().setString(2, u.getPrimerNombre());
            DBConnection.getInstance().getPst().setString(3, u.getPrimerApellido());
            DBConnection.getInstance().getPst().setString(4, u.getUsername());
            DBConnection.getInstance().getPst().setString(5, u.getPassword());
            DBConnection.getInstance().getPst().setString(6, RolEnum.USER.getCode());
            DBConnection.getInstance().getPst().executeUpdate();
            retorno = Boolean.TRUE;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            DBConnection.getInstance().closeQuery();
        }
        return retorno;
    }

    /**
     * Guarda datos de usuario en la aplicación.
     * 
     * @param u
     *            el usuario a eliminar.
     * @return TRUE si el proceso se completa exitosamente.
     */
    public Boolean update(Usuario u) {
        boolean retorno = Boolean.FALSE;
        try {
            DBConnection.getInstance()
                    .setPst(DBConnection.getInstance().getCon().prepareStatement(ConstantesQuery.UPDATE_USER));
            DBConnection.getInstance().getPst().setString(1, u.getPrimerNombre());
            DBConnection.getInstance().getPst().setString(2, u.getPrimerApellido());
            DBConnection.getInstance().getPst().setString(3, u.getUsername());
            DBConnection.getInstance().getPst().setString(4, u.getPassword());
            DBConnection.getInstance().getPst().setLong(5, u.getCodigousuario());
            DBConnection.getInstance().getPst().executeUpdate();
            retorno = Boolean.TRUE;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            DBConnection.getInstance().closeQuery();
        }
        return retorno;
    }

    /**
     * Guarda datos de usuario en la aplicación.
     * 
     * @param u
     *            el usuario a guardar.
     * @return TRUE si el proceso se completa exitosamente.
     */
    public Boolean delete(Usuario u) {
        boolean retorno = Boolean.FALSE;
        try {
            // Elimina al usuario
            DBConnection.getInstance()
                    .setPst(DBConnection.getInstance().getCon().prepareStatement(ConstantesQuery.DELETE_USER));
            DBConnection.getInstance().getPst().setLong(1, u.getCodigousuario());
            DBConnection.getInstance().getPst().executeUpdate();
            retorno = Boolean.TRUE;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            DBConnection.getInstance().closeQuery();
        }
        return retorno;
    }

    /**
     * Lista los usuarios presentes en la BD.
     * 
     * @return la lista de usuarios.
     */
    public List<Usuario> list() {
        List<Usuario> lu = new ArrayList<>();
        try {
            if (PrincipalController.getInstance().getLoControl().getDto().getRol()
                    .equals(Long.valueOf(RolEnum.ADMIN.getCode()))) {
                DBConnection.getInstance().setPst(
                        DBConnection.getInstance().getCon().prepareStatement(ConstantesQuery.LISTAR_USUARIOS_ADMIN));
            } else {
                DBConnection.getInstance()
                        .setPst(DBConnection.getInstance().getCon().prepareStatement(ConstantesQuery.LISTAR_USUARIOS));
                // Toma el codigo de usuario de la sesion
                DBConnection.getInstance().getPst().setLong(1,
                        PrincipalController.getInstance().getLoControl().getDto().getCodigousuario());
            }
            DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
            while (DBConnection.getInstance().getRs().next()) {
                Usuario u = new Usuario();
                u.setPrimerNombre(DBConnection.getInstance().getRs().getString(2));
                u.setPrimerApellido(DBConnection.getInstance().getRs().getString(3));
                u.setUsername(DBConnection.getInstance().getRs().getString(4));
                u.setRol(DBConnection.getInstance().getRs().getLong(6));
                lu.add(u);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            DBConnection.getInstance().closeQuery();
        }
        return lu;
    }

    /**
     * Obtiene la lista de resultados como parametro para busqueda.
     * 
     * @return la lista de parametro.
     */
    public List<String> suggest() {
        List<String> lu = new ArrayList<>();
        try {
            if (PrincipalController.getInstance().getLoControl().getDto().getRol()
                    .equals(Long.valueOf(RolEnum.ADMIN.getCode()))) {
                DBConnection.getInstance().setPst(
                        DBConnection.getInstance().getCon().prepareStatement(ConstantesQuery.LISTAR_USUARIOS_ADMIN));
            } else {
                DBConnection.getInstance()
                        .setPst(DBConnection.getInstance().getCon().prepareStatement(ConstantesQuery.LISTAR_USUARIOS));
                // Toma el codigo de usuario de la sesion
                DBConnection.getInstance().getPst().setLong(1,
                        PrincipalController.getInstance().getLoControl().getDto().getCodigousuario());
            }
            DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
            while (DBConnection.getInstance().getRs().next()) {
                lu.add(DBConnection.getInstance().getRs().getString(4));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            DBConnection.getInstance().closeQuery();
        }
        return lu;
    }

    /**
     * Retorna un usuario dependiendo de su username.
     * 
     * @param username
     *            nombre del usuario.
     * @return el usuario con dicha clave.
     */
    public Usuario search(String username) {
        Usuario u = null;
        try {
            DBConnection.getInstance()
                    .setPst(DBConnection.getInstance().getCon().prepareStatement(ConstantesQuery.SEARCH_USER));
            DBConnection.getInstance().getPst().setString(1, username);
            DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
            while (DBConnection.getInstance().getRs().next()) {
                u = new Usuario();
                u.setCodigousuario(DBConnection.getInstance().getRs().getLong(1));
                u.setPrimerNombre(DBConnection.getInstance().getRs().getString(2));
                u.setPrimerApellido(DBConnection.getInstance().getRs().getString(3));
                u.setUsername(DBConnection.getInstance().getRs().getString(4));
                u.setPassword(DBConnection.getInstance().getRs().getString(5));
                u.setRol(DBConnection.getInstance().getRs().getLong(6));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            DBConnection.getInstance().closeQuery();
        }
        return u;
    }

    /**
     * @return the instance
     */
    public static UsuarioEjb getInstance() {
        return INSTANCE;
    }

}
