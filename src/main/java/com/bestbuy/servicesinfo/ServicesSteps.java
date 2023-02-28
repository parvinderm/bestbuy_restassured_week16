package com.bestbuy.servicesinfo;

import com.bestbuy.constants.ServicesEndsPoints;
import com.bestbuy.model.ServicesPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ServicesSteps {
    @Step("getting all information:{0}")
    public ValidatableResponse getAllServicesInfo() {
        return SerenityRest.given()
                .when()
                .get(ServicesEndsPoints.GET_ALL_SERVICES)
                .then();
    }

    @Step("creating service with name:{0}")
    public ValidatableResponse createServices(String name) {
        ServicesPojo servicesPojo = new ServicesPojo();
        servicesPojo.setName(name);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(servicesPojo)
                .when()
                .post(ServicesEndsPoints.CREATE_NEW_SERVICES)
                .then().log().all().statusCode(201);
    }


    @Step("getting service info by name:{0}")
    public HashMap<String, Object> getServicesInfoByName(String name){
        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";

        return SerenityRest.given()
                .log().all()
                .when()
                .get(ServicesEndsPoints.GET_ALL_SERVICES)
                .then().statusCode(200).extract().path(part1+name+part2);
    }
    @Step("update service info with servicesId:{0},name:{1}")
    public ValidatableResponse updateServices(String name,int servicesId){
        ServicesPojo servicesPojo=new ServicesPojo();
        servicesPojo.setName(name);


        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("servicesID",servicesId)
                .body(servicesPojo)
                .when()
                .patch(ServicesEndsPoints.UPDATE_SERVICES_BY_ID)
                .then();

    }
    @Step("deleteing services information with servicesId:{0}")
    public ValidatableResponse deleteServicesInfoByID(int servicesId) {
        return SerenityRest.given()
                .pathParam("servicesID", servicesId)
                .when()
                .delete(ServicesEndsPoints.DELETE_SERVICES_BY_ID)
                .then();

    }
    @Step("getting services info by servicesId:{0}")
    public ValidatableResponse getServicesInfoByServicesId(int servicesId){
        return SerenityRest.given()
                .pathParam("servicesID",servicesId)
                .when()
                .get(ServicesEndsPoints.GET_SINGLE_SERVICES_BY_ID)
                .then();

    }
    }
