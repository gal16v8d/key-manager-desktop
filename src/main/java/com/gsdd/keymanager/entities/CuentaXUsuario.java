package com.gsdd.keymanager.entities;

import java.io.Serializable;
import java.util.Date;
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
  private Long codigocuenta;
  private Long codigousuario;
  private String nombreCuenta;
  private String username;
  private String password;
  private String url;
  private Date fecha;

  public CuentaXUsuario() {
    codigocuenta = System.nanoTime();
  }

}
