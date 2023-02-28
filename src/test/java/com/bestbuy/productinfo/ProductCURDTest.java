package com.bestbuy.productinfo;

import com.bestbuy.constants.ProductEndPoints;
import com.bestbuy.model.ProductPojo;
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
public class ProductCURDTest extends TestBase {
    static String name = "gandhi_battery"+ TestUtils.getRandomValue();

    static Object productsId;

    @Title("This will create a new products")
    @Test
    public void test001(){
        ProductPojo productPojo=new ProductPojo();
        productPojo.setName(name);
        productPojo.setType("SoftGood");
//        productPojo.setPrice(1450);
//        productPojo.setShipping(36);
        productPojo.setUpc("45678");
        productPojo.setDescription("tghp");
        productPojo.setManufacturer("fordit");
        productPojo.setModel("erthg");
        productPojo.setUrl("dfg");
        productPojo.setImage("rftgh");

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(productPojo)
                .when()
                .post(ProductEndPoints.CREATE_NEW_PRODUCTS)
                .then().log().all().statusCode(201);

    }
    @Title("Verify if product was created")
    @Test
    public void test002(){
        String part1 ="data.findAll{it.firstName='";
        String part2 ="'}.get(0)";

        HashMap<String,?> productMapData =SerenityRest.given()
                .log().all()
                .when()
                .get(ProductEndPoints.GET_ALL_PRODUCTS)
                .then().statusCode(200).extract().path(part1 + name + part2);
        Assert.assertThat(productMapData, hasValue(name) );
        productsId=  productMapData.get("id");
        System.out.println(productsId);

    }
    @Title("Update the product and verify the updated information")
    @Test
    public void test003() {
      name = name + "radhu";

      ProductPojo productPojo=new ProductPojo();
         productPojo.setName(name);

        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("productsID",productsId)
                .body(productPojo)
                .when()
                .patch(ProductEndPoints.UPDATE_PRODUCT_BY_ID)
                .then()
                .statusCode(200);


        String part1 ="data.findAll{it.name='";
        String part2 ="'}.get(0)";

        HashMap<String,Object>productsMapData =SerenityRest.given()
                .when()
                .get(ProductEndPoints.GET_ALL_PRODUCTS)
                .then().statusCode(200).extract().path(part1+ name + part2);
        Assert.assertThat(productsMapData, hasValue(name));


    }
    @Title("Delete the student and verify if the student is deleted")
    @Test
    public void test004(){

        SerenityRest.given()
                .pathParam("productsID",productsId)
                .when()
                .delete(ProductEndPoints.DELETE_PRODUCT_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
                .pathParam("productsID",productsId)
                .when()
                .get(ProductEndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then().log().all().statusCode(404);

    }


}
