package com.example.warehouse;

import com.example.util.CryptoUtils;
import com.example.util.JsonUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class RefundGenerationService {

  public void generateReport(Path refundsFile, List<Refund> refunds, String key) {
    try {
      this.generateRefundFile(refundsFile, refunds);
      this.generateSha256HmacFile(refundsFile, key);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void generateRefundFile(Path refundsFile, List<Refund> refunds) throws IOException {
    String refundsJson = JsonUtils.toJson(refunds);
    Files.writeString(refundsFile, refundsJson);
  }

  private void generateSha256HmacFile(Path refundsFile, String key) throws IOException {
    Path hashFile = refundsFile.resolveSibling(refundsFile.getFileName() + ".hs256");
    String hashValue = CryptoUtils.hmacSha256(Files.readAllBytes(refundsFile), key);
    Files.writeString(hashFile, hashValue);
  }
}
