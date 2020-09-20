package com.example.warehouse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

@Component
public class RefundGenerationService {

  public void generateReport(Path refundsFile, List<Refund> refunds, String password) {
    this.generateRefundFile(refundsFile, refunds);
    this.generateSha256HmacFile(refundsFile,password);
  }

  private void generateRefundFile(Path refundsFile, List<Refund> refunds) {
    try {
      var objectMapper = new ObjectMapper();
      objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
      objectMapper.writeValue(refundsFile.toFile(), refunds);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void generateSha256HmacFile(Path refundsFile, String password) {
    try {
      var hashValue = computeHmac(refundsFile,password);
      var hashFile = refundsFile.resolveSibling(refundsFile.getFileName() + ".hs256");
      Files.writeString(hashFile, hashValue);
    } catch (IOException e) {
      throw new RuntimeException();
    }
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
}
