package com.bestbuy.storesinfo;


import com.bestbuy.constants.StoresEndPoints;
import com.bestbuy.model.StoresPojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StoresCURDTest extends TestBase {
    static String name = "excel"+ TestUtils.getRandomValue();
    static Object storesId;


    @Title("This will create a new store")
    @Test
    public void test001(){
        StoresPojo storesPojo = new StoresPojo();
        storesPojo.setName(name);
        storesPojo.setType("bigto");
        storesPojo.setAddress("12 drt ave");
        storesPojo.setAddress2("16 rft ave");
        storesPojo.setCity("ruhani");
        storesPojo.setState("rtf");
        storesPojo.setZip("gthy");
//        storesPojo.setLat(3456);
//        storesPojo.setLng(4567);
        storesPojo.setHours("Tue:9-5; Wed: 10-6; Sat:10:4;");

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(storesPojo)
                .when()
                .post(StoresEndPoints.CREATE_NEW_STORES)
                .then().log().all().statusCode(201);
    }
    @Title("Verify if stores was created")
    @Test
    public void test002(){
        String part1 ="data.findAll{it.firstName='";
        String part2 ="'}.get(0)";

        HashMap<String,?> storesMapData =SerenityRest.given()
                .log().all()
                .when()
                .get(StoresEndPoints.GET_ALL_STORES)
                .then().statusCode(200).extract().path(part1 + name + part2);
        Assert.assertThat(storesMapData, hasValue(name) );
        storesId=  storesMapData.get("id");
        System.out.println(storesId);

    }
    @Title("Update the stores and verify the updated information")
    @Test
    public void test003() {
        name = name + "ruhuti";

        StoresPojo storesPojo=new StoresPojo();
        storesPojo.setName(name);

        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("storesID",storesId)
                .body(storesPojo)
                .when()
                .patch(StoresEndPoints.UPDATE_STORES_BY_ID)
                .then()
                .statusCode(200);


        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";

        HashMap<String,Object>storesMapData =SerenityRest.given()
                .when()
                .get(StoresEndPoints.GET_ALL_STORES)
                .then().statusCode(200).extract().path(part1+ name + part2);
        Assert.assertThat(storesMapData, hasValue(name));


    }
    @Title("Delete the store and verify if the store is deleted")
    @Test
    public void test004(){

        SerenityRest.given()
                .pathParam("storesID",storesId)
                .when()
                .delete(StoresEndPoints.DELETE_STORES_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
                .pathParam("storesID",storesId)
                .when()
                .get(StoresEndPoints.GET_SINGLE_STORES_BY_ID)
                .then().log().all().statusCode(404);

    }

}
