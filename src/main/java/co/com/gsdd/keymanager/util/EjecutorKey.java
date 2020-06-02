package co.com.gsdd.keymanager.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Ejecutor empleado para emplear concurrencia de hilos donde sea posible.
 * 
 * @author Great System Development Dynamic <GSDD> <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 *
 */
public class EjecutorKey {

    /**
     * Objeto ejecutor.
     */
    private final ExecutorService executor;
    /**
     * Instancia de la clase.
     */
    public static final EjecutorKey INSTANCE = new EjecutorKey();

    /**
     * Constructor privado.
     */
    private EjecutorKey() {
        executor = Executors.newFixedThreadPool(3);
    }

    /**
     * 
     * @return el ejecutor.
     */
    public ExecutorService getExecutor() {
        return executor;
    }

    /**
     * Obtiene la instancia del ejecutor de servicios.
     * 
     * @return la instancia del ejecutor.
     */
    public static EjecutorKey getInstance() {
        return INSTANCE;
    }

}
