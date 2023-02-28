package com.bestbuy.storesinfo;

import com.bestbuy.constants.StoresEndPoints;
import com.bestbuy.model.StoresPojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StoresCURDTestWithSteps extends TestBase {
      static String name = "excel"+ TestUtils.getRandomValue();
     static String type="Tanu" + TestUtils.getRandomValue();
     static String address="12 drt ave" +TestUtils.getRandomValue();
     static String  address2="16 rft ave" + TestUtils.getRandomValue();
      static String city= "ruhani" + TestUtils.getRandomValue();
      static String  state="rtf" +TestUtils.getRandomValue();
      static String zip="gthy" + TestUtils.getRandomValue();
//      static String lat = "7878" + TestUtils.getRandomValue();
//      static String lng = 76785 +TestUtils.getRandomValue();
       static String hours="Tue:9-5; Wed: 10-6; Sat:10:4;" +TestUtils.getRandomValue();

    static int storesId;

    @Steps
    StoreSteps storeSteps;
    @Title("This will create a new stores")
    @Test
    public void test001(){
        ValidatableResponse response =storeSteps.createStores(name,type,address,address2,city,state,zip,hours);
        response.statusCode(201);

    }
    @Title("verify if store is created")
    @Test
    public void test002() {
        HashMap<String,Object> storesMapData =storeSteps.getStoresInfoByName(name);
        Assert.assertThat(storesMapData,hasValue(name));
       storesId= (int) storesMapData.get("id");
        System.out.println(storesId);

    }
    @Title("Update the stores information")
    @Test
    public void test003(){
        name = name + "ruhuti";

        storeSteps.updateStores(storesId,name);
        HashMap<String,Object> categoriesMap=storeSteps.getStoresInfoByName(name);
        Assert.assertThat(categoriesMap,hasValue(name));


    }
    @Title("Delete stores info by storesID and verify its deleted")
    @Test
    public void test004() {

        storeSteps.deleteStoresInfoByStoresId(storesId).statusCode(200);
        storeSteps.getStoresInfoByStoresId(storesId).statusCode(404);

    }

    }
