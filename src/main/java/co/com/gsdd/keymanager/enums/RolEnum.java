package co.com.gsdd.keymanager.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author Great System Development Dynamic <GSDD> <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public enum RolEnum {
    ADMIN("1"), USER("2");

    /**
     * Código que identifica al rol.
     */
    private final String code;
}
