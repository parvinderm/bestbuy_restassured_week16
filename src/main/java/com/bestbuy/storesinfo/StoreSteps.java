package com.bestbuy.storesinfo;

import com.bestbuy.constants.CategoriesEndPoints;
import com.bestbuy.constants.StoresEndPoints;
import com.bestbuy.model.StoresPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class StoreSteps {
    @Step("getting all information:{0}")
    public ValidatableResponse getAllCategoriesInfo() {
        return SerenityRest.given()
                .when()
                .get(CategoriesEndPoints.GET_ALL_CATEGORIES)
                .then();
    }
    @Step("creating Store with name:{0},type:{1},address:{2},address2:{3},city:{4},state:{5},zip:{6},lat:{7},lng:{8},hours:{9}")
    public ValidatableResponse createStores(String name,String type,String address,String address2,String city,String state
    ,String zip,String hours) {
        StoresPojo storesPojo = new StoresPojo();
        storesPojo.setName(name);
        storesPojo.setType(type);
        storesPojo.setAddress(address);
        storesPojo.setAddress2(address2);
        storesPojo.setCity(city);
        storesPojo.setState(state);
        storesPojo.setZip(zip);
//        storesPojo.setLat(3456);
//        storesPojo.setLng(4567);
        storesPojo.setHours(hours);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(storesPojo)
                .when()
                .post(StoresEndPoints.CREATE_NEW_STORES)
                .then().log().all().statusCode(201);

    }
    @Step("getting Stores info by name:{0}")
    public HashMap<String, Object> getStoresInfoByName(String name){

        String part1 ="data.findAll{it.firstName='";
        String part2 ="'}.get(0)";

       return SerenityRest.given()
                .log().all()
                .when()
                .get(StoresEndPoints.GET_ALL_STORES)
                .then().statusCode(200).extract().path(part1 + name + part2);


    }
    @Step("update Stores info with storesId:{0},name:{1} ")
    public ValidatableResponse updateStores(int storesId,String name){
        StoresPojo storesPojo=new StoresPojo();
        storesPojo.setName(name);

       return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("storesID",storesId)
                .body(storesPojo)
                .when()
                .patch(StoresEndPoints.UPDATE_STORES_BY_ID)
                .then()
                .statusCode(200);

    }
       @Step("deleting Stores info with storesId:{0}")
        public ValidatableResponse deleteStoresInfoByStoresId(int storesId){
         return SerenityRest.given()
                  .pathParam("storesID",storesId)
                  .when()
                  .delete(StoresEndPoints.DELETE_STORES_BY_ID)
                  .then();


    }
    @Step("getting stores info by storesId:{0}")
    public ValidatableResponse getStoresInfoByStoresId(int storesId){
        return SerenityRest.given()
                .pathParam("storesID",storesId)
                .when()
                .get(StoresEndPoints.GET_SINGLE_STORES_BY_ID)
                .then().log().all().statusCode(404);

    }

}
