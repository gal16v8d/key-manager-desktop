package com.gsdd.keymanager.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.Getter;

/**
 * Ejecutor empleado para emplear concurrencia de hilos donde sea posible.
 *
 * @author Great System Development Dynamic <GSDD> <br>
 *     Alexander Galvis Grisales <br>
 *     alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@Getter
public final class ExecutorKeyManager {

  private static final ExecutorKeyManager INSTANCE = new ExecutorKeyManager();
  private final ExecutorService executor;

  private ExecutorKeyManager() {
    executor = Executors.newFixedThreadPool(3);
  }

  public static ExecutorKeyManager getInstance() {
    return INSTANCE;
  }
}
