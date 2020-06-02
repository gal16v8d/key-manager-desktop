package co.com.gsdd.keymanager.entities;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Great System Development Dynamic <GSDD> <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@Getter
@Setter
public class CuentaXUsuario implements Serializable {

    private static final long serialVersionUID = 8137178755748222207L;
    /**
     * Usuario dueño de la cuenta.
     */
    private Long codigousuario;
    /**
     * Pk de la tabla.
     */
    private String nombreCuenta;
    /**
     * Usuario de la cuenta.
     */
    private String username;
    /**
     * Pass de la cuenta.
     */
    private String password;
    /**
     * Indica una URL opcional.
     */
    private String url;

}
