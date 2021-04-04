# EXCHANGE API
Simple foreign exchange application.
1. Exchange Rate API:
   - input: currency pair to retrieve the exchange rate
   - output: exchange rate
2. Conversion API:
   - input: source amount, source currency, target currency
   - output: amount in target currency, and transaction id.
3. Conversion List API:
   - input: transaction id or transaction date (at least one of the inputs shall be provided for each call)
   - output: list of conversions filtered by the inputs and paging is required
   
## 1 - How To Run

### Running with Docker Compose

Project is already dockerized. 

Use below url in the application.properties file for the docker-compose run
 ```sh
 spring.datasource.url=jdbc:postgresql://dbpostgresql:5432/dockerdb
 ```
Then, run below command and you are ready to go.

 ```sh
$ docker-compose up
$ docker-compose down
 ```

### Creating Dockerize PostgreSQL Database
Check my medium article to learn how to run PostgreSQL on Docker and connect with Springboot application:
https://topallfurkan.medium.com/how-to-run-postgresql-on-docker-1a8f512d00e5

Use below url in the application.properties file for your local machine run
 ```sh
spring.datasource.url=jdbc:postgresql://localhost:5432/dockerdb
 ```

### Database Design

**conversion**

   - transaction_id
   - exchange_rate
   - source_amount
   - source_currency
   - target_amount
   - target_currency
   - transaction_date

## 2 - Used Technologies

* ```Spring Boot```- 2.4.4  RELEASE
* ```JDK``` - 11
* ```Spring Data``` JPA
* ```Maven``` - 3.3+
* ```IDE``` - IntelliJ IDEA
* ```PostgreSQL``` - 42.2.19 RELEASE
* ```Spring Web```
* ```Docker```
* ```SonarLint```

## 3 - REST APIs Test Results via Postman

Find api queries in the attached postman collection test results: [here](exchangeapi.postman_test_run.json).

## Contributing

Any code reviews and recommendations are welcome.
