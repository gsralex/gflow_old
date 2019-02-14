package com.gsralex.gflow.core.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author gsralex
 * @version 2018/12/13
 */
public class SecurityUtils {

    public static boolean check(String accessKey, String accessToken) {
        return encrypt(accessKey).equals(accessToken);
    }

    public static String encrypt(String accessKey) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(accessKey.getBytes());
            String md5 = new BigInteger(1, md.digest()).toString(16);
            return md5;
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }
}
