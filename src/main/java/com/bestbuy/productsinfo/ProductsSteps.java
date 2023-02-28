package com.bestbuy.productsinfo;


import com.bestbuy.constants.ProductEndPoints;
import com.bestbuy.constants.ServicesEndsPoints;
import com.bestbuy.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

import java.util.HashMap;

public class ProductsSteps {
     @Step("getting all information:{0}")
     public ValidatableResponse getAllProductsInfo() {
        return SerenityRest.given()
                .when()
                .get(ProductEndPoints.GET_ALL_PRODUCTS)
                .then();
    }



    @Step("creating products with name:{0},type:{1},upc:{2},description:{5},manufacturer:{6},model:{7},url:{8},image:{9}")
    public ValidatableResponse createProducts(String name, String type,String upc,
                                               String description, String manufacturer, String model, String url, String image) {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setUpc(upc);
//        productPojo.setPrice(1450);
//        productPojo.setShipping(36);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(productPojo)
                .when()
                .post(ProductEndPoints.CREATE_NEW_PRODUCTS)
                .then().log().all().statusCode(201);

    }
     @Step("getting products information by name:{0}")
     public HashMap<String, Object> getProductsInfoByName(String name) {
         String part1 ="data.findAll{it.name='";
         String part2 ="'}.get(0)";

         return SerenityRest.given()
                 .log().all()
                 .when()
                 .get(ProductEndPoints.GET_ALL_PRODUCTS)
                 .then().statusCode(200).extract().path(part1 +name +part2);
     }
     @Step("Update products information with productsId:{0},name:{1}")
     public ValidatableResponse updateProducts(String name,int productsId){
         ProductPojo productPojo=new ProductPojo();
         productPojo.setName(name);

        return SerenityRest.given().log().all()
                 .contentType(ContentType.JSON)
                 .pathParam("productsID",productsId)
                 .body(productPojo)
                 .when()
                 .patch(ProductEndPoints.UPDATE_PRODUCT_BY_ID)
                 .then()
                 .statusCode(200);
     }
    @Step("delete products info with productsId:{0}")
    public ValidatableResponse deleteProductsInfoByProductsId(int productsId){
        return SerenityRest.given()
                .pathParam("productsID",productsId)
                .when()
                .delete(ProductEndPoints.DELETE_PRODUCT_BY_ID)
                .then();


    }
    @Step("getting Products info by productsId:{0}")
    public ValidatableResponse getProductsInfoByProductsId(int productsId){
        return SerenityRest.given()
                .pathParam("productsID",productsId)
                .when()
                .get(ProductEndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then();

    }
}
