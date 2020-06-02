package co.com.gsdd.keymanager.util;

import co.com.gsdd.cypher.CypherAlgorithm;
import co.com.gsdd.cypher.CypherUtil;
import co.com.gsdd.cypher.DigestAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CifradoKeyManager {

    private static final String KM_SALT = "8ad7d96acc9ce8d129df900ec373c1399d29fb99";

    public static String cifrarKM(String dato) {
        return CypherUtil.encode(dato, KM_SALT, DigestAlgorithm.SHA512, CypherAlgorithm.AES_WITH_PADDING);
    }

    public static String descifrarKM(String dato) {
        return CypherUtil.decode(dato, KM_SALT, DigestAlgorithm.SHA512, CypherAlgorithm.AES_WITH_PADDING);
    }

}
