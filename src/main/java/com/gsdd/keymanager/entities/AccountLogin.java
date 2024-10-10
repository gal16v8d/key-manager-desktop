package com.gsdd.keymanager.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
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
public class AccountLogin implements Serializable {

  @Serial private static final long serialVersionUID = 8137178755748222207L;
  @Default private Long id = System.nanoTime();
  private Long accountId;
  private String accountName;
  private Long typeId;
  private String login;
  private String password;
  private String url;
  private Date modificationDate;
}
