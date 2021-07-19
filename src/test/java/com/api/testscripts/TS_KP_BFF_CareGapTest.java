package com.api.testscripts;

import com.api.common.CommonUtil;
import com.api.dependencyInjector.ConfigurationModule;
import com.api.restassured.BffParamGenerator;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Guice(modules = {ConfigurationModule.class})
public class TS_KP_BFF_CareGapTest extends TS_KP_BFF_BaseTest{


    @BeforeClass
    public void setCareGapUrl(){

       logger.info("before class");
    }

    @Test
    public void tc001_CareGapSuccess() throws Exception {

        commonUtil.logExecutionStart();
        String url="https://reqres.in/api/users";
        Map<String ,Object> parameters=new HashMap<>();
        parameters.put("page",2);
        Response apiResponse=requestUtil.getRequestWithHeadersAndParam(url,appHeaders,parameters,true);
        currentTestCase.info(appUser.toString());
        currentTestCase.assignAuthor("Deepak Tiwari");
        currentTestCase.info(this.getClass().getSimpleName()+" TC1");
        commonUtil.logExecutionEnd();

    }


    @Test
    public void tc002_CareGapFailure() throws Exception {

        commonUtil.logExecutionStart();
        currentTestCase.info(appUser.toString());
        currentTestCase.assignAuthor("Deepak Tiwari");
        currentTestCase.info(this.getClass().getSimpleName()+" TC2");
        commonUtil.logExecutionEnd();

    }


    @Test
    public void tc002_testPutMethod() throws Exception {


        commonUtil.logExecutionStart();
        currentTestCase.assignAuthor("Deepak Tiwari");
        String url="https://reqres.in/api/users/2";
        JSONObject body= BffParamGenerator.getUpdateLoginParam("deepak","deepak");
        Response response = requestUtil.putRequestWithHeadersAndBody(url, appHeaders,body,true);
        commonUtil.logExecutionEnd();
    }

    @Test(dataProvider = "newUserDataProvider")
    public void tc002_testData(JSONObject object) throws Exception {


        commonUtil.logExecutionStart();
        logger.info(CommonUtil.getFormattedJSON(object.toString()));
        currentTestCase.assignAuthor("Deepak Tiwari");
        currentTestCase.info(CommonUtil.getStringForReport("Test Data \n<b>"+CommonUtil.getFormattedJSON(object.toString())+"</b>"));
        //String url="https://reqres.in/api/users/2";
        //JSONObject body= BffParamGenerator.getUpdateLoginParam("deepak","deepak");
       // Response response = requestUtil.putRequestWithHeadersAndBody(url, appHeaders,body,true);
        commonUtil.logExecutionEnd();

    }

    @DataProvider
    public Object[][] newUserDataProvider() throws JSONException, IOException {


        String testDataFilePath=CommonUtil.getAbsolutePath("src","main","resources", "testcases","data.json");
        //String testDataFile=CommonUtil.getProjectDir()+"/src/main/resources/testdata/data.json";
        JSONArray array=commonUtil.readJsonArrayFromFile(testDataFilePath);
        Object[][] testData=new Object[array.length()][1];
        for (int i=0;i<array.length();i++){
            testData[i][0]=array.get(i);
        }

        return  testData;
    }

}
