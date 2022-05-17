package com.gsdd.keymanager.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public enum RolEnum {
  ADMIN("1"),
  USER("2");

  private final String code;
}
