package com.example.util;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.security.crypto.codec.Hex;

public class CryptoUtils {

  public static String hmacSha256(byte[] input, String key) {
    try {
      Mac hmac = Mac.getInstance("HmacSHA256");
      hmac.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
      byte[] hash = hmac.doFinal(input);
      return String.valueOf(Hex.encode(hash));
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      throw new RuntimeException(e);
    }
  }

  public static String sha256(byte[] input) {
    try {
      MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
      byte[] hash = sha256.digest(input);
      return String.valueOf(Hex.encode(hash));
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
}
