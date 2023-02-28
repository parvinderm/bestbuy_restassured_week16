package com.bestbuy.categoriesinfo;

import com.bestbuy.constants.CategoriesEndPoints;
import com.bestbuy.model.CategoriesPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class CategoriesSteps {
    @Step("getting all information:{0}")
    public ValidatableResponse getAllCategoriesInfo() {
        return SerenityRest.given()
                .when()
                .get(CategoriesEndPoints.GET_ALL_CATEGORIES)
                .then();
    }

    @Step("creating categories with name:{0},id:{1}")
    public ValidatableResponse createCategories(String name,String id){

        CategoriesPojo categoriesPojo=new CategoriesPojo();
        categoriesPojo.setName(name);
        categoriesPojo.setId(id);
        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(categoriesPojo)
                .when()
                .post(CategoriesEndPoints.CREATE_NEW_CATEGORIES)
                .then().log().all().statusCode(201);
    }
    @Step("getting categories info by name:{0}")
    public HashMap<String,Object>getCategoriesInfoByName(String name) {
        String part1 ="data.findAll{it.name='";
        String part2="'}.get(0)";

        return SerenityRest.given()
                .log().all()
                .when()
                .get(CategoriesEndPoints.GET_ALL_CATEGORIES)
                .then()
                .statusCode(200)
                .extract().path(part1 + name + part2);

    }
    @Step("update categories info with name:{1}")
    public ValidatableResponse updateCategories(String categoriesId,String name) {

        CategoriesPojo categoriesPojo = new CategoriesPojo();
        categoriesPojo.setName(name);
//        categoriesPojo.setId(id);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("categoriesID",categoriesId)
                .body(categoriesPojo)
                .when()
                .patch(CategoriesEndPoints.UPDATE_CATEGORIES_BY_ID)
                .then();
    }
    @Step("deleting Category information with categoriesId:{0}")
    public ValidatableResponse deleteCategoriesInfoByID(String categoriesId){
          return SerenityRest.given()
                .pathParam("categoriesID",categoriesId)
                .when()
                .delete(CategoriesEndPoints.DELETE_CATEGORIES_BY_ID)
                .then();

    }
    @Step("getting category info By categoriesId:{0}")
    public ValidatableResponse getCategoriesInfoByCategoriesID(String categoriesId){
        return SerenityRest.given()
                .pathParam("categoriesID",categoriesId)
                .when()
                .get(CategoriesEndPoints.GET_SINGLE_CATEGORIES_BY_ID)
                .then();
    }


}
