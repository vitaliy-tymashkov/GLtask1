package com.gl.procamp.tests.functional.gorestApi;

import static com.gl.procamp.config.Config.getAuthBearerToken;
import static com.gl.procamp.config.Config.getBaseUrl;
import static com.gl.procamp.config.Config.getGetPostsPath;
import static com.gl.procamp.config.Config.getUserPath;
import static com.gl.procamp.config.ConfigReader.readConfigurationFromFile;
import static com.gl.procamp.repository.UserUtility.getEmail;
import static com.gl.procamp.repository.UserUtility.getGender;
import static com.gl.procamp.repository.UserUtility.getName;
import static com.gl.procamp.repository.UserUtility.getNamePatched;
import static com.gl.procamp.repository.UserUtility.getStatus;
import static com.gl.procamp.repository.UserUtility.getUserJson;
import static com.gl.procamp.repository.UserUtility.getUserPatchedJson;

import com.gl.procamp.config.Config;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestApiTest extends RestApiBaseClass {
    private static final Logger logger = LoggerFactory.getLogger(RestApiTest.class);

    private static String userId;

    @BeforeClass
    public void setup() {
        readConfigurationFromFile(PROPERTIES_FILE_NAME);
        RestAssured.baseURI = getBaseUrl();
    }

    @Test
    public void testWhenGetPostsThenStatus200AndContentTypeJson() {
        RestAssured.given()
                .when().get(getGetPostsPath())
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .and()
                .statusCode(200)
                .and()
                .body("code", Matchers.equalTo(200));
    }

    @Test(dependsOnMethods = "testWhenGetPostsThenStatus200AndContentTypeJson")
    public void testWhenCreateUserThenNewUserCreated() {
        String validRequest = getUserJson();
        logger.info("Create user request %s", validRequest);

        Response response =
                RestAssured.given()
                        .headers(
                                "Authorization",
                                "Bearer " + getAuthBearerToken(),
                                "Content-Type", ContentType.JSON,
                                "Accept", ContentType.JSON)
                        .body(validRequest)
                        .post(Config.getUserPath())
                        .then()
                        .statusCode(200)
                        .body("code", Matchers.equalTo(201))
                        .body("data.name", Matchers.equalTo(getName()))
                        .body("data.email", Matchers.equalTo(getEmail()))
                        .body("data.status", Matchers.equalTo(getStatus()))
                        .body("data.gender", Matchers.equalTo(getGender()))
                        .log().all()
                        .extract().response();

        userId = response.
                path("data.id").toString();
    }

    @Test(dependsOnMethods = "testWhenCreateUserThenNewUserCreated")
    public void testWhenPatchUserThenStatus200() {
        String validRequest = getUserPatchedJson(userId);
        logger.info("Patch user request %s", validRequest);

        RestAssured.given()
                .headers(
                        "Authorization",
                        "Bearer " + getAuthBearerToken(),
                        "Content-Type", ContentType.JSON,
                        "Accept", ContentType.JSON)
                .body(validRequest)
                .patch(getUserPath() + "/" + userId)
                .then()
                .statusCode(200)
                .body("code", Matchers.equalTo(200))
                .body("data.name", Matchers.equalTo(getNamePatched()))
                .body("data.email", Matchers.equalTo(getEmail()))
                .body("data.status", Matchers.equalTo(getStatus()))
                .body("data.gender", Matchers.equalTo(getGender()))
                .log().all()
                .extract().response();
    }

    @Test(dependsOnMethods = "testWhenPatchUserThenStatus200")
    public void testWhenDeleteCreatedUserThenUserDeleted() {
        RestAssured.given()
                .headers(
                        "Authorization",
                        "Bearer " + getAuthBearerToken(),
                        "Content-Type", ContentType.JSON,
                        "Accept", ContentType.JSON)
                .delete(getUserPath() + "/" + userId)
                .then()
                .statusCode(200)
                .body("code", Matchers.equalTo(204))
                .log().all()
                .extract().response();
    }
}
