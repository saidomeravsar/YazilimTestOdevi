package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ApiTest {

    @Before
    public void setup() {
        // Java 25 ile uyumlu ve engelleme yapmayan test servisi
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void kullaniciBilgisiGetirTest() {
        given()
                // Bazı servisler User-Agent olmadan cevap vermez, bunu ekliyoruz
                .header("User-Agent", "Java/25")
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                // Java 25 çok hızlıdır ama internet hızına göre süre değişebilir
                // Garantilemek için süreyi biraz esnettik (5000ms)
                .time(lessThan(5000L))
                .body("name", equalTo("Leanne Graham"))
                .body("email", equalTo("Sincere@april.biz"));

        System.out.println("✅ GET testi Java 25 üzerinde başarıyla çalıştı.");
    }

    @Test
    public void yeniGonderiOlusturTest() {
        String requestBody = "{\n" +
                "    \"title\": \"Java 25 Test\",\n" +
                "    \"body\": \"Rest Assured Proje Odevi\",\n" +
                "    \"userId\": 1\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .header("User-Agent", "Java/25")
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("Java 25 Test"))
                .body("userId", equalTo(1));

        System.out.println("✅ POST testi Java 25 üzerinde başarıyla çalıştı.");
    }
}