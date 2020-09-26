package com.example.payments;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

@Component
public class PaymentService {

  public void processRefunds(Path refundsFile, String password) {
    if (!isValid(refundsFile, password)) {
      throw new CorruptRefundFileException();
    }
    try {
      System.out.println("Issuing Refund to");
      System.out.println(Files.readString(refundsFile));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private boolean isValid(Path refundsFile, String password) {
    var actualHash = computeHmac(refundsFile, password);
    var exceptedHash = readExpectedHash(refundsFile);
    return actualHash.equals(exceptedHash);
  }

  private String computeHmac(Path refundsFile, String password) {
    try {
      Mac hmac = Mac.getInstance("HmacSHA256");
      hmac.init(new SecretKeySpec(password.getBytes(), "HmacSHA256"));
      byte[] hash = hmac.doFinal(Files.readAllBytes(refundsFile));
      return String.valueOf(Hex.encode(hash));
    } catch (NoSuchAlgorithmException | IOException | InvalidKeyException e) {
      throw new RuntimeException(e);
    }
  }

  private String readExpectedHash(Path refundsFile) {
    try {
      var hashFile = refundsFile.resolveSibling(refundsFile.getFileName() + ".hs256");
      return Files.readString(hashFile);
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }
}
