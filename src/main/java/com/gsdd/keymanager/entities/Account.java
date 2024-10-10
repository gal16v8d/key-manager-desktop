package com.gsdd.keymanager.entities;

import java.io.Serial;
import java.io.Serializable;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Great System Development Dynamic <GSDD> <br>
 *     Alexander Galvis Grisales <br>
 *     alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@Builder
@Getter
@Setter
public class Account implements Serializable {

  @Serial private static final long serialVersionUID = 5662910925451501908L;
  @Default private Long accountId = System.nanoTime();
  private String firstName;
  private String lastName;
  private String login;
  private String password;
  private Long role;
}
