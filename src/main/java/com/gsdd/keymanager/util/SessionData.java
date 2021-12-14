package com.gsdd.keymanager.util;

import com.gsdd.keymanager.entities.Usuario;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SessionData {

  private static final SessionData INSTANCE = new SessionData();
  private Usuario sessionDto;

  public static SessionData getInstance() {
    return INSTANCE;
  }
}
