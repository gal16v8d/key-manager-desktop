package com.gsdd.keymanager.entities;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AccountType implements Serializable {

  private static final long serialVersionUID = 1617669387071102479L;
  private Long typeId;
  private String name;
  
}
