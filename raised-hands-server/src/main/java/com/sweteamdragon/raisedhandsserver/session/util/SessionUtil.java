package com.sweteamdragon.raisedhandsserver.session.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class SessionUtil {

    //TODO: Make values configurable
    private static final char[] ch = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static String generateSessionId() {
        return generateSecureRandomString(9);
    }

    public static String generateSessionPasscode() {
        return generateSecureRandomString(6);
    }

    private static String generateSecureRandomString(int len) {
        //TODO: Make values configurable
        char[] c = new char[9];
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < len; i++) {
            c[i] = ch[random.nextInt(ch.length)];
        }

        return new String(c);
    }
}
