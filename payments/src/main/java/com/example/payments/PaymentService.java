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
      byte[] refunds = Files.readAllBytes(refundsFile);
      String actualHmac = CryptoUtils.hmacSha256(refunds, key);

      String hmacFileName = refundsFile.getFileName() + ".hs256";
      Path hashFile = refundsFile.resolveSibling(hmacFileName);
      String exceptedHmac = Files.readString(hashFile);

      if (!actualHmac.equals(exceptedHmac)) {
        throw new CorruptRefundFileException();
      }

      System.out.println("Issuing Refund to");
      System.out.println(Files.readString(refundsFile));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
