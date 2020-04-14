package com.sweteamdragon.raisedhandsserver.session.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class SessionUtil {

    //TODO: Make values configurable
    private static final char[] alphanum = "0123456789ABCDEFGHJKMNPQRSTUVWXYZ".toCharArray();
    private static final char[] digits = "0123456789".toCharArray();

    public static String generateSessionId() {
        return generateSecureRandomString(9, alphanum);
    }

    public static String generateSessionPasscode() {
        return generateSecureRandomString(6, digits);
    }

    private static String generateSecureRandomString(int len, char[] ch) {
        //TODO: Make values configurable
        char[] c = new char[len];
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < len; i++) {
            c[i] = ch[random.nextInt(ch.length)];
        }

        return new String(c);
    }
}
