package com.gl.procamp.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.gl.procamp.config.Config.*;
import static com.gl.procamp.config.Config.getAuthBearerToken;
import static com.gl.procamp.config.ConfigReader.readConfigurationFromFile;
import static com.gl.procamp.repository.UserUtility.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RestApiTest {
    private static final Logger logger = LoggerFactory.getLogger(RestApiTest.class);

    private static String userId;
    private String PROPERTIES_FILE_NAME = "src/main/resources/config.properties";

    @BeforeClass
    public void setup() {
        readConfigurationFromFile(PROPERTIES_FILE_NAME);
        RestAssured.baseURI = getBaseUrl();
    }

    @Test
    public void testWhenGetPostsThenStatus200AndContentTypeJson() {
        given()
                .when().get(getGetPostsPath())
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .and()
                .statusCode(200)
                .and()
                .body("code", equalTo(200));
    }

    @Test(dependsOnMethods = "testWhenGetPostsThenStatus200AndContentTypeJson")
    public void testWhenCreateUserThenNewUserCreated() {
        String validRequest = getUserJson();
        logger.info("Create user request %s", validRequest);

        Response response =
                given()
                        .headers(
                                "Authorization",
                                "Bearer " + getAuthBearerToken(),
                                "Content-Type", ContentType.JSON,
                                "Accept", ContentType.JSON)
                        .body(validRequest)
                        .post(getUserPath())
                        .then()
                        .statusCode(200)
                        .body("code", equalTo(201))
                        .body("data.name", equalTo(getName()))
                        .body("data.email", equalTo(getEmail()))
                        .body("data.status", equalTo(getStatus()))
                        .body("data.gender", equalTo(getGender()))
                        .log().all()
                        .extract().response();

        userId = response.
                path("data.id").toString();
    }

    @Test(dependsOnMethods = "testWhenCreateUserThenNewUserCreated")
    public void testWhenPatchUserThenStatus200() {
        String validRequest = getUserPatchedJson(userId);
        logger.info("Patch user request %s", validRequest);

        given()
                .headers(
                        "Authorization",
                        "Bearer " + getAuthBearerToken(),
                        "Content-Type", ContentType.JSON,
                        "Accept", ContentType.JSON)
                .body(validRequest)
                .patch(getUserPath() + "/" + userId)
                .then()
                .statusCode(200)
                .body("code", equalTo(200))
                .body("data.name", equalTo(getNamePatched()))
                .body("data.email", equalTo(getEmail()))
                .body("data.status", equalTo(getStatus()))
                .body("data.gender", equalTo(getGender()))
                .log().all()
                .extract().response();
    }

    @Test(dependsOnMethods = "testWhenPatchUserThenStatus200")
    public void testWhenDeleteCreatedUserThenUserDeleted() {
        given()
                .headers(
                        "Authorization",
                        "Bearer " + getAuthBearerToken(),
                        "Content-Type", ContentType.JSON,
                        "Accept", ContentType.JSON)
                .delete(getUserPath() + "/" + userId)
                .then()
                .statusCode(200)
                .body("code", equalTo(204))
                .log().all()
                .extract().response();
    }
}
