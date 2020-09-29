package com.example.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.security.crypto.codec.Hex;

public class CryptoUtils {

  public static String hmacSha256(byte[] input, String key) {
    try {
      Mac hmac = Mac.getInstance("HmacSHA256");

      var hmacKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
      hmac.init(hmacKey);

      byte[] hash = hmac.doFinal(input);
      return String.valueOf(Hex.encode(hash));
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      throw new RuntimeException(e);
    }
  }
}
