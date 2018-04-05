package com.sabiantools.utilities;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Brian Sabana on 20/03/2017.
 */
public class SabianEncryption {

    private String key;

    private byte[] keyBytes;

    private SecretKeySpec keySpec;

    public SabianEncryption(String key){

        this.key=key;
    }
    private void generatePaddedKey() throws NoSuchAlgorithmException, UnsupportedEncodingException {

        keyBytes=key.getBytes("UTF-8");

        MessageDigest sha= MessageDigest.getInstance("SHA-1");

        keyBytes=sha.digest(keyBytes);

        keyBytes= Arrays.copyOf(keyBytes, 16);

        keySpec=new SecretKeySpec(keyBytes, "AES");
    }


    /**
     * 34 * gets the AES encryption key. In your actual programs, this should be
     * safely 35 * stored. 36 * @return 37 * @throws Exception 38
     */

    /**
     * 47 * Encrypts plainText in AES using the secret key 48 * @param plainText
     * 49 * @param secKey 50 * @return 51 * @throws Exception 52
     */
    public String encryptText(String plainText) throws InvalidKeyException,Exception {

        generatePaddedKey();

        Cipher aesCipher = Cipher.getInstance("AES");

        aesCipher.init(Cipher.ENCRYPT_MODE, this.keySpec);

        byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());

        return BytesToBase64(byteCipherText);
    }

    /**
     * 62 * Decrypts encrypted byte array using the key used for encryption. 63
     * * @param byteCipherText 64 * @param secKey 65 * @return 66 * @throws
     * Exception 67
     */
    public String decryptText(String cipherText) throws InvalidKeyException, Exception {

        generatePaddedKey();

        byte[] byteCipherText= Base64ToBytes(cipherText);

        Cipher aesCipher = Cipher.getInstance("AES");

        aesCipher.init(Cipher.DECRYPT_MODE, this.keySpec);

        byte[] bytePlainText = aesCipher.doFinal(byteCipherText);

        return new String(bytePlainText);
    }

    /**
     * 77 * Convert a binary byte array into readable Base64 * @param hash
     * 79 * @returnø 80
     */
    private String BytesToBase64(byte[] hash) {

        return Base64.encodeToString(hash,Base64.DEFAULT);
    }
    private byte[] Base64ToBytes(String hex){

        return Base64.decode(hex,Base64.DEFAULT);
    }
}
