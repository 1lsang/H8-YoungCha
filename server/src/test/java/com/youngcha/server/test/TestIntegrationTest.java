package com.youngcha.server.test;

import com.youngcha.server.IntegrationTestBase;
import com.youngcha.server.common.dto.ErrorResponse;
import com.youngcha.server.common.dto.SuccessResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class TestIntegrationTest extends IntegrationTestBase {

    @Autowired
    TestController testController;

    @Test
    @DisplayName("성공 응답을 받는다.")
    void test() {
        ExtractableResponse<Response> response = RestAssured
                .given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)

                .when()
                .get("/test/ok")

                .then()
                .log().all()
                .extract();

        SuccessResponse<SuccessResponse<String>> successResponse = response.body().as(new TypeRef<>() {
        });
        softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        softAssertions.assertThat(successResponse.getData().getData()).isEqualTo("ok");
    }

    @Test
    @DisplayName("bad request 예외가 발생한다.")
    void error() {
        ExtractableResponse<Response> response = RestAssured
                .given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)

                .when()
                .get("/test/error")

                .then()
                .log().all()
                .extract();

        ErrorResponse errorResponse = response.body().as(new TypeRef<>() {
        });
        softAssertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        softAssertions.assertThat(errorResponse.getMessage()).isEqualTo("error");
    }
}