package com.bestbuy.categoriesinfo;

import com.bestbuy.constants.CategoriesEndPoints;
import com.bestbuy.model.CategoriesPojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.PropertyReader;
import com.bestbuy.utils.TestUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.swing.text.AbstractDocument;
import javax.xml.ws.Endpoint;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class CategoriesCURDTest extends TestBase {
    static String name = "keychainS giftS"+ TestUtils.getRandomValue();//keychain gift82351
    static String id="dfgty678"+TestUtils.getRandomValue(); //dfgty67815618
    static Object categoriesId;



    @Title("This will create a new categories")
    @Test
    public void test001(){
        CategoriesPojo categoriesPojo=new CategoriesPojo();
        categoriesPojo.setName(name);
        categoriesPojo.setId(id);

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(categoriesPojo)
                .when()
                .post(CategoriesEndPoints.CREATE_NEW_CATEGORIES)
                .then().log().all().statusCode(201);

    }
    @Title("Verify if categories was created")
    @Test
    public void test002(){
       String part1 ="data.findAll{it.name='";
       String part2="'}.get(0)";



        HashMap<String,?>categoriesMapData=SerenityRest.given()
                .log().all()
                .when()
                .get(CategoriesEndPoints.GET_ALL_CATEGORIES)
                .then().statusCode(200).extract().path(part1 +name+part2);
        Assert.assertThat(categoriesMapData,hasValue(name));
        categoriesId=categoriesMapData.get("id");
        System.out.println(categoriesId);
    }
    @Title("Update the user and verify the updates information")
    @Test
    public void test003(){
        name = name+"hard";

        CategoriesPojo categoriesPojo=new CategoriesPojo();
        categoriesPojo.setName(name);
        categoriesPojo.setId(id);

        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("categoriesID",categoriesId)
                .body(categoriesPojo)
                .when()
                .patch(CategoriesEndPoints.UPDATE_CATEGORIES_BY_ID)
                .then().log().all().statusCode(200);


        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";

        HashMap<String,Object>categoriesMapData =SerenityRest.given()
                .when()
                .get(CategoriesEndPoints.GET_ALL_CATEGORIES)
                .then().statusCode(200).extract().path(part1+ name + part2); //findAll{it.firstName=='akshit69136'}.get(0)
        Assert.assertThat(categoriesMapData, hasValue(name));

    }
    @Title("Delete the categories and verify if the student is deleted")
    @Test
    public void test004(){

        SerenityRest.given()
                .pathParam("categoriesID",categoriesId)
                .when()
                .delete(CategoriesEndPoints.DELETE_CATEGORIES_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
                .contentType(ContentType.JSON)
                .pathParam("categoriesID",categoriesId)
                .when()
                .get(CategoriesEndPoints.GET_SINGLE_CATEGORIES_BY_ID)
                .then().log().all().statusCode(404);

    }

}
