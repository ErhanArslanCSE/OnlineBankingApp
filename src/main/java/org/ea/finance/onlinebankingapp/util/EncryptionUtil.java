package org.ea.finance.onlinebankingapp.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;

public class EncryptionUtil {

    private static final String SECRET_KEY_ALGORITHM = "AES";
    private static final String ENCRYPTION_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String SECRET_KEY = "YourSecretKey"; // Change this to your secret key

    public static String encrypt(String plainText) throws Exception {
        SecretKeySpec secretKey = generateSecretKey();
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedText) throws Exception {
        SecretKeySpec secretKey = generateSecretKey();
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

    private static SecretKeySpec generateSecretKey() throws Exception {
        byte[] key = SECRET_KEY.getBytes();
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec keySpec = new PBEKeySpec(SECRET_KEY.toCharArray(), key, 65536, 256);
        SecretKey secretKey = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(secretKey.getEncoded(), SECRET_KEY_ALGORITHM);
    }
}
