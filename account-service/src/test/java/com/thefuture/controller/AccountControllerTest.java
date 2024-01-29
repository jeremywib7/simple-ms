package com.thefuture.controller;

import com.thefuture.entity.AccountEntity;
import com.thefuture.repository.AccountEntityRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
class AccountControllerTest {

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    AccountEntityRepository accountEntityRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        accountEntityRepository.deleteAll();
    }

    @Test
    void shouldCreateAccount() {
        AccountEntity accountEntity = AccountEntity.builder()
                .accountHolder("Jason")
                .balance(new BigDecimal("100000.00"))
                .build();

        given()
                .contentType("application/json")  // Set the Content-Type header to JSON
                .body(accountEntity)
                .when()
                .post("/api/account/v1")
                .then()
                .statusCode(201)  // Change to the appropriate status code (201 Created)
                .body("accountHolder", equalTo("Jason"))
                .body("balance", equalTo(100000.00f));
    }

    @Test
    void shouldGetAccountByPublicId() {
        AccountEntity accountEntity = AccountEntity.builder()
                .accountHolder("Jason")
                .balance(new BigDecimal("100000.00"))
                .build();

        AccountEntity res = accountEntityRepository.save(accountEntity);
        System.out.println("the account : " + res.getPublicId());

        given()
                .queryParam("publicId", res.getPublicId())
                .when()
                .get("/api/account/v1")
                .then()
                .statusCode(200)
                .body("accountHolder", equalTo("Jason"))
                .body("balance", equalTo(100000.00f));
    }


}
