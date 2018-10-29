package com.gsralex.gflow.core.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author gsralex
 * @version 2018/10/23
 */
public class AccessTokenUtils {

    public static boolean check(String accessKey, String accessToken) {
        return StringUtils.equals(encrypt(accessKey), accessToken);
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
