# crypto-hmac

Demo project showing how to use HMAC functions with SHA-256 to guarantee data integrity 
and authenticate who generates a file. There are three subprojects in this repo.

* utils - contains shared utility classes `CrptoUtils` and `JsonUtils`.  
* warehouse - generates `data/refunds.json` file, depends on the utils project. 
* payments -  reads `data/refunds.json` and verifies its integrity using HMAC SHA-256, depends 
on utils project.

## software prerequisites 

* Java 11 JDK 
* Java IDE 

## run on the command line

* run warehouse app `java -jar warehouse/target/warehouse-0.0.1-SNAPSHOT.jar` to generate the 
  `data/refunds.json` and `data/refunds.json.hs256` files
* run payments app `java -jar payments/target/payments-0.0.1-SNAPSHOT.jar` to read the 
  `data/refunds.json` and verify it's integrity  against  `data/refunds.json.hs256`
* edit `data/refunds.json` to simulate corruption. you can add a newline at the end of the file.
* run payments app `java -jar payments/target/payments-0.0.1-SNAPSHOT.jar` you will an exception. 

## run from the IDE 

* run `com.example.warehouse.WarehouseApplication` to generate the `data/refunds.json` and `data/refunds.json.sha256`
* run `com.example.payments.PaymentsApplication` to read the `data/refunds.json` and verify its integrity 
* edit `data/refunds.json` to simulate corruption. you can add a newline at the end of the file.
* run `com.example.payments.PaymentsApplication` to get a data corruption exception 
* restore `data/refunds.json` to its original state
* edit the refunds key in `src/main/resources/application.yml`
* run `com.example.payments.PaymentsApplication` to get a data corruption exception 

## interesting files to look at 

* `util/src/main/java/com/example/util/CryptoUtils.java` to examine how th sha-256 is computed
* `warehouse/src/main/java/com/example/warehouse/RefundGenerationService.java` to examine the code
that generates the `refund.json` and `refund.json.hs256` file
* `payments/src/main/java/com/example/payments/PaymentService.java` to examine the code that 
 verifies the integrity of the `refund.json` file before consuming it.
