package com.gsdd.keymanager.util;

import com.gsdd.cipher.CipherAlgorithm;
import com.gsdd.cipher.CipherUtil;
import com.gsdd.cipher.DigestAlgorithm;
import java.util.function.UnaryOperator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CipherKeyManager {

  private static final String KM_SALT = "8ad7d96acc9ce8d129df900ec373c1399d29fb99";

  public static final UnaryOperator<String> CYPHER = data -> CipherUtil.encode(data, KM_SALT,
      DigestAlgorithm.SHA512, CipherAlgorithm.AES_WITH_PADDING);

  public static final UnaryOperator<String> DECYPHER = data -> CipherUtil.decode(data, KM_SALT,
      DigestAlgorithm.SHA512, CipherAlgorithm.AES_WITH_PADDING);
  
}
