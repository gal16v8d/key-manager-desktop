package com.gsdd.keymanager.entities.dto;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import lombok.Builder;
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
public class AccountLoginDto implements Serializable {

  @Serial private static final long serialVersionUID = 3500185729680511899L;
  private Long accountId;
  private String sessionLogin;
  private String accountName;
  private String accountType;
  private String login;
  private String pass;
  private Date modificationDate;
  private String url;
}
