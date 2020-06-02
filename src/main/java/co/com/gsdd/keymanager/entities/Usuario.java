package co.com.gsdd.keymanager.entities;

import java.io.Serializable;
import java.util.Random;

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
public class Usuario implements Serializable {

    private static final long serialVersionUID = 5662910925451501908L;
    /**
     * Pk de la tabla.
     */
    private Long codigousuario;
    /**
     * Primer nombre del usuario.
     */
    private String primerNombre;
    /**
     * Primer apellido del usuario.
     */
    private String primerApellido;
    /**
     * Nombre de usuario.
     */
    private String username;
    /**
     * Pass de usuario.
     */
    private String password;
    /**
     * Rol del usuario.
     */
    private Long rol;

    /**
     * Constructor por defecto.
     */
    public Usuario() {
        codigousuario = System.nanoTime() * new Random().nextInt();
    }

}
