package com.termproject.bookstore.validation;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;

/**
 * Hashes
 */
public class Hasher{
    private static final int ITERATIONS = 1000;
    private static final int KEYLENGTH = 100;
    private static final int KEY = 50;

    //value used for salting
    private static final String salt= "dh32brfchvhf";

    public Hasher(){}


    public String hashPassword(String password){
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();

        PBEKeySpec spec = new PBEKeySpec(passwordChars,saltBytes,ITERATIONS,KEYLENGTH);
        try {
            SecretKeyFactory key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hashedPassword = key.generateSecret(spec).getEncoded();
            return String.format("%x", new BigInteger(hashedPassword));
        }
        catch (Exception e) {
            return "F";
        }
    }


    public String hashCardNumber(String password){
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();

        PBEKeySpec spec = new PBEKeySpec(passwordChars,saltBytes,ITERATIONS,KEY);
        try {
            SecretKeyFactory key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hashedPassword = key.generateSecret(spec).getEncoded();
            return String.format("%x", new BigInteger(hashedPassword));
        }
        catch (Exception e) {
            return "F";
        }
    }
}