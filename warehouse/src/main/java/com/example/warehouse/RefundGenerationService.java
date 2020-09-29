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
      String refundsJson = JsonUtils.toJson(refunds);
      Files.writeString(refundsFile, refundsJson);
      String hashValue = CryptoUtils.hmacSha256(refundsJson.getBytes(), key);

      String hmacFileName = refundsFile.getFileName() + ".hs256";
      Path hashFile = refundsFile.resolveSibling(hmacFileName);
      Files.writeString(hashFile, hashValue);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
