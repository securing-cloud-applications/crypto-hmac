package com.example.payments;

import com.example.util.CryptoUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Component;

@Component
public class PaymentService {

  public void processRefunds(Path refundsFile, String key) {
    try {
      if (!isValid(refundsFile, key)) {
        throw new CorruptRefundFileException();
      }
      System.out.println("Issuing Refund to");
      System.out.println(Files.readString(refundsFile));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private boolean isValid(Path refundsFile, String key) throws IOException {
    Path hashFile = refundsFile.resolveSibling(refundsFile.getFileName() + ".hs256");
    String actualHash = CryptoUtils.hmacSha256(Files.readAllBytes(refundsFile), key);
    String exceptedHash = Files.readString(hashFile);
    return actualHash.equals(exceptedHash);
  }
}
