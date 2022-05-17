package com.gsdd.keymanager.entities;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Great System Development Dynamic <GSDD> <br>
 *     Alexander Galvis Grisales <br>
 *     alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@Getter
@Setter
public class Usuario implements Serializable {

  private static final long serialVersionUID = 5662910925451501908L;
  private Long codigousuario;
  private String primerNombre;
  private String primerApellido;
  private String username;
  private String password;
  private Long rol;

  public Usuario() {
    codigousuario = System.nanoTime();
  }
}
