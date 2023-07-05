package util;

import org.apache.commons.codec.digest.DigestUtils;

public final class CriptografiaUtils {

    public static String changeToHash(String password) {
        String md5Hex = DigestUtils
                .md5Hex(password).toUpperCase();
        return md5Hex;
    }

}
