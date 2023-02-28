package com.bestbuy.servicesinfo;

import com.bestbuy.constants.ServicesEndsPoints;
import com.bestbuy.model.ServicesPojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;
@RunWith(SerenityRunner.class)
public class ServicesCURDTest extends TestBase {
    static String name="Ruhani"+ TestUtils.getRandomValue();
    static Object servicesId;


    @Title("This will create new services")
    @Test
    public void test001(){
        ServicesPojo servicesPojo =new ServicesPojo();
          servicesPojo.setName(name);

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(servicesPojo)
                .when()
                .post(ServicesEndsPoints.CREATE_NEW_SERVICES)
                .then().log().all().statusCode(201);
    }
    @Title("Verify if services was created")
    @Test
    public void test002(){
        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";

        HashMap<String,?> servicesMapData =SerenityRest.given()
                .log().all()
                .when()
                .get(ServicesEndsPoints.GET_ALL_SERVICES)
                .then().statusCode(200).extract().path(part1+name+part2);
        Assert.assertThat(servicesMapData, hasValue(name));
       servicesId= servicesMapData.get("id");
        System.out.println(servicesId);

    }
    @Title("Update the user and verify the updated information")
    @Test
    public void test003(){
        name = name + "rana";

       ServicesPojo servicesPojo=new ServicesPojo();
       servicesPojo.setName(name);


        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("servicesID",servicesId)
                .body(servicesPojo)
                .when()
                .patch(ServicesEndsPoints.UPDATE_SERVICES_BY_ID)
                .then().statusCode(200);


        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";

        HashMap<String,Object>servicesMapData =SerenityRest.given()
                .when()
                .get(ServicesEndsPoints.GET_ALL_SERVICES)
                .then().statusCode(200).extract().path(part1 + name + part2);
        Assert.assertThat(servicesMapData, hasValue(name));


    }
    @Title("Delete the services and verify if the services is deleted")
    @Test
    public void test004(){

       SerenityRest.given()
                .pathParam("servicesID",servicesId)
                .when()
                .delete(ServicesEndsPoints.DELETE_SERVICES_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
//                .header("Content-Type","application/json;charset=utf-8")
                .pathParam("servicesID",servicesId)
                .when()
                .get(ServicesEndsPoints.GET_SINGLE_SERVICES_BY_ID)
                .then().log().all().statusCode(404);

    }

}
