package co.com.gsdd.keymanager.controller;

import co.com.gsdd.gui.util.JPaginateTable;

/**
 * 
 * @author Great System Development Dynamic <GSDD> <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 * 
 */
public interface InterfaceController {

    /**
     * Construye la vista inicial del Panel.
     */
    void buildVistaInicial();

    /**
     * Limpia los campos gestionables.
     */
    void clearFields();

    /**
     * Define el modelo a crear de tabla.
     * 
     * @param tabla
     *            la tabla a definir.
     */
    void setTableModel(JPaginateTable tabla);

    /**
     * Llena con datos la tabla.
     * 
     * @param tabla
     *            la tabla a poblar.
     */
    void fillTable(JPaginateTable tabla);

    /**
     * Almacena valores en BD.
     */
    void eventoGuardar();

    /**
     * Actualiza valores en BD.
     */
    void eventoActualizar();

    /**
     * Elimina valores en BD.
     */
    void eventoEliminar();

    /**
     * Consulta valores en BD.
     */
    void eventoConsultar();

}
