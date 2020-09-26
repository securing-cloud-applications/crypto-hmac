package com.example.payments;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PaymentsApplicationTests {

  @Autowired private PaymentService paymentService;

  @Value("${refunds.password}")
  private String password;

  @Test
  void testRefundProcessing() {
    System.out.println("'" + password + "'");
    paymentService.processRefunds(Path.of("../data/refunds.json"), password);
  }
}
