package co.com.gsdd.keymanager.ejb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import co.com.gsdd.constantes.ConstantesQuery;
import co.com.gsdd.dbutil.DBConnection;
import co.com.gsdd.keymanager.controller.PrincipalController;
import co.com.gsdd.keymanager.entities.CuentaXUsuario;
import co.com.gsdd.keymanager.entities.dto.CuentaXUsuarioDto;
import co.com.gsdd.keymanager.enums.RolEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CuentaXUsuarioEjb {

    private static final CuentaXUsuarioEjb INSTANCE = new CuentaXUsuarioEjb();

    /**
     * Guarda datos de usuario en la aplicación.
     * 
     * @param c
     *            la cuenta a guardar.
     * @return TRUE si el proceso se completa exitosamente.
     */
    public Boolean save(CuentaXUsuario c) {
        boolean retorno = Boolean.FALSE;
        try {
            DBConnection.getInstance()
                    .setPst(DBConnection.getInstance().getCon().prepareStatement(ConstantesQuery.INSERT_CUENTAXUSER));
            DBConnection.getInstance().getPst().setLong(1, c.getCodigocuenta());
            DBConnection.getInstance().getPst().setLong(2, c.getCodigousuario());
            DBConnection.getInstance().getPst().setString(3, c.getNombreCuenta());
            DBConnection.getInstance().getPst().setString(4, c.getUsername());
            DBConnection.getInstance().getPst().setString(5, c.getPassword());
            DBConnection.getInstance().getPst().setString(6, c.getUrl());
            DBConnection.getInstance().getPst().setDate(7,
                    new java.sql.Date(Calendar.getInstance().getTime().getTime()));
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
     * @param c
     *            la cuenta a actualizar.
     * @param cc
     *            codigocuenta anterior.
     * @param cu
     *            codigousuario anterior.
     * @return TRUE si el proceso se completa exitosamente.
     */
    public Boolean update(CuentaXUsuario c, String cc, Long cu) {
        boolean retorno = Boolean.FALSE;
        try {
            DBConnection.getInstance()
                    .setPst(DBConnection.getInstance().getCon().prepareStatement(ConstantesQuery.UPDATE_CUENTAXUSER));
            DBConnection.getInstance().getPst().setString(1, c.getNombreCuenta());
            // Obtiene el usuario de la sesion
            DBConnection.getInstance().getPst().setLong(2,
                    PrincipalController.getInstance().getLoControl().getDto().getCodigousuario());
            DBConnection.getInstance().getPst().setString(3, c.getUsername());
            DBConnection.getInstance().getPst().setString(4, c.getPassword());
            DBConnection.getInstance().getPst().setString(5, c.getUrl());
            DBConnection.getInstance().getPst().setDate(6,
                    new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            // Datos anteriores
            DBConnection.getInstance().getPst().setString(7, cc);
            DBConnection.getInstance().getPst().setLong(8, cu);
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
     * @param c
     *            la cuenta a eliminar.
     * @return TRUE si el proceso se completa exitosamente.
     */
    public Boolean delete(CuentaXUsuario c) {
        boolean retorno = Boolean.FALSE;
        try {
            // Elimina la cuenta
            DBConnection.getInstance()
                    .setPst(DBConnection.getInstance().getCon().prepareStatement(ConstantesQuery.DELETE_CUENTAXUSER));
            DBConnection.getInstance().getPst().setString(1, c.getNombreCuenta());
            // Obtiene el usuario de la sesion
            DBConnection.getInstance().getPst().setLong(2,
                    PrincipalController.getInstance().getLoControl().getDto().getCodigousuario());
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
     * Lista las cuentas presentes en la BD.
     * 
     * @return la lista de cuentas.
     */
    public List<CuentaXUsuarioDto> list() {
        List<CuentaXUsuarioDto> lc = new ArrayList<>();
        try {
            if (PrincipalController.getInstance().getLoControl().getDto().getRol()
                    .equals(Long.valueOf(RolEnum.ADMIN.getCode()))) {
                DBConnection.getInstance().setPst(
                        DBConnection.getInstance().getCon().prepareStatement(ConstantesQuery.LISTAR_CUENTAXUSER_ADMIN));
            } else {
                DBConnection.getInstance().setPst(
                        DBConnection.getInstance().getCon().prepareStatement(ConstantesQuery.LISTAR_CUENTAXUSER));
                // Toma el codigo de usuario de la sesion
                DBConnection.getInstance().getPst().setLong(1,
                        PrincipalController.getInstance().getLoControl().getDto().getCodigousuario());
            }
            DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
            while (DBConnection.getInstance().getRs().next()) {
                CuentaXUsuarioDto c = new CuentaXUsuarioDto();
                c.setCodigousuario(DBConnection.getInstance().getRs().getLong(1));
                c.setNombrecuenta(DBConnection.getInstance().getRs().getString(2));
                c.setNombreusuario(DBConnection.getInstance().getRs().getString(3));
                c.setUrl(DBConnection.getInstance().getRs().getString(4));
                c.setUsuario(DBConnection.getInstance().getRs().getString(5));
                c.setPass(DBConnection.getInstance().getRs().getString(6));
                c.setFecha(DBConnection.getInstance().getRs().getDate(7));
                lc.add(c);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            DBConnection.getInstance().closeQuery();
        }
        return lc;
    }

    /**
     * Obtiene la lista de resultados como parametro para busqueda.
     * 
     * @return la lista de parametro.
     */
    public List<String> suggest() {
        List<String> lc = new ArrayList<>();
        try {
            DBConnection.getInstance()
                    .setPst(DBConnection.getInstance().getCon().prepareStatement(ConstantesQuery.LISTAR_CUENTAS));
            DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
            while (DBConnection.getInstance().getRs().next()) {
                lc.add(DBConnection.getInstance().getRs().getString(1));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            DBConnection.getInstance().closeQuery();
        }
        return lc;
    }

    /**
     * Retorna una cuenta dependiendo de su id.
     * 
     * @param nombreCuenta.
     * @return la cuenta con dicha clave.
     */
    public CuentaXUsuario search(String nombreCuenta) {
        CuentaXUsuario c = null;
        try {
            DBConnection.getInstance()
                    .setPst(DBConnection.getInstance().getCon().prepareStatement(ConstantesQuery.SEARCH_CUENTAXUSER));
            DBConnection.getInstance().getPst().setString(1, nombreCuenta);
            // Usuario de la sesion (evita que admin lo modifique)
            DBConnection.getInstance().getPst().setLong(2,
                    PrincipalController.getInstance().getLoControl().getDto().getCodigousuario());
            DBConnection.getInstance().setRs(DBConnection.getInstance().getPst().executeQuery());
            while (DBConnection.getInstance().getRs().next()) {
                c = new CuentaXUsuario();
                c.setCodigousuario(DBConnection.getInstance().getRs().getLong(1));
                c.setUsername(DBConnection.getInstance().getRs().getString(2));
                c.setPassword(DBConnection.getInstance().getRs().getString(3));
                c.setNombreCuenta(DBConnection.getInstance().getRs().getString(4));
                c.setUrl(DBConnection.getInstance().getRs().getString(5));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        } finally {
            DBConnection.getInstance().closeQuery();
        }
        return c;
    }

    /**
     * @return the instance
     */
    public static CuentaXUsuarioEjb getInstance() {
        return INSTANCE;
    }

}
