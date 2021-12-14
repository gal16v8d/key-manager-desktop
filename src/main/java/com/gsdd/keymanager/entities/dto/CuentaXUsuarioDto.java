package com.gsdd.keymanager.entities.dto;

import java.io.Serializable;
import java.sql.Date;
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
public class CuentaXUsuarioDto implements Serializable {

  private static final long serialVersionUID = 3500185729680511899L;
  /**
   * Relación foranea con el código del usuario.
   */
  private Long codigousuario;
  /**
   * Nombre del usuario en el aplicativo.
   */
  private String nombreusuario;
  /**
   * Nombre de la cuenta en el aplicativo.
   */
  private String nombrecuenta;
  /**
   * Usuario de la cuenta creada.
   */
  private String usuario;
  /**
   * Contraseña de la cuenta creada.
   */
  private String pass;
  /**
   * Fecha de ultimo cambio de contraseña.
   */
  private Date fecha;
  /**
   * La url en caso de haberla
   */
  private String url;

}
