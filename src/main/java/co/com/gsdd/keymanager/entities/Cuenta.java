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
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 8764304357251754716L;
    /**
     * Pk de la tabla.
     */
    private Long codigocuenta;
    /**
     * Nombre de la cuenta.
     */
    private String nombre;

    /**
     * Constructor por defecto.
     */
    public Cuenta() {
        codigocuenta = System.nanoTime() * new Random().nextInt();
    }

}
