package com.myencurter.util;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SecureRandom;

@Component
public class HashGenerator {

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generateHash() {

        int length = 7;

        SecureRandom random = new SecureRandom();
        BigInteger number = new BigInteger(130, random);
        StringBuilder sb = new StringBuilder();

        while (number.compareTo(BigInteger.ZERO) > 0 && sb.length() < length) {
            int remainder = number.mod(BigInteger.valueOf(62)).intValue();
            sb.append(BASE62.charAt(remainder));
            number = number.divide(BigInteger.valueOf(62));
        }

        while (sb.length() < length) {
            sb.append(BASE62.charAt(random.nextInt(62)));
        }

        return sb.toString();
    }
}
