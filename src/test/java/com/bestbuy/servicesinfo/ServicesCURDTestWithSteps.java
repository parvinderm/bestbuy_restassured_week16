package com.bestbuy.servicesinfo;

import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
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
public class ServicesCURDTestWithSteps extends TestBase {
    static String name="Ruhani"+ TestUtils.getRandomValue();
    static int servicesId;

    @Steps
    ServicesSteps servicesSteps;

    @Title("This will create a new service")
    @Test
    public void test001(){

        ValidatableResponse response =servicesSteps.createServices(name);
        response.statusCode(201);
    }
    @Title("verify if services is created")
    @Test
    public void test002(){
        HashMap<String,Object> servicesMapData = servicesSteps.getServicesInfoByName(name);
        Assert.assertThat(servicesMapData,hasValue(name));
        servicesId=(int) servicesMapData.get("id");
        System.out.println(servicesId);
        System.out.println(servicesMapData);

    }
    @Title("update the user information")
    @Test
    public void test003(){
        name = name + "rani";

        servicesSteps.updateServices(name,servicesId);
        HashMap<String,Object> categoriesMap=servicesSteps.getServicesInfoByName(name);
        Assert.assertThat(categoriesMap,hasValue(name));

    }
    @Title("Delete service info by ServicesID and verify its deleted")
    @Test
    public void test004(){
        servicesSteps.deleteServicesInfoByID(servicesId).statusCode(200);
        servicesSteps.getServicesInfoByServicesId(servicesId).statusCode(404);
    }
}
