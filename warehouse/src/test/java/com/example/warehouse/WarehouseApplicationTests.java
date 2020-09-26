package com.example.warehouse;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WarehouseApplicationTests {

  @Autowired private RefundGenerationService refundGenerationService;

  @Value("${refunds.password}")
  private String password;

  @Test
  void generateReport() {
    var refunds =
        List.of(
            new Refund("5555555555554444", BigDecimal.valueOf(500)),
            new Refund("4012888888881881", BigDecimal.valueOf(250)));

    refundGenerationService.generateReport(Path.of("../data/refunds.json"), refunds, password);
  }
}
