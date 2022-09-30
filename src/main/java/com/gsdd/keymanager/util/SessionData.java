package com.gsdd.keymanager.util;

import com.gsdd.keymanager.entities.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SessionData {

  private static final SessionData INSTANCE = new SessionData();
  private Account sessionDto;

  public static SessionData getInstance() {
    return INSTANCE;
  }
}
