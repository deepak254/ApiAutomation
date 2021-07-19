package com.api.testscripts;

import com.api.common.KpBffEndPoints;
import com.api.restassured.BffParamGenerator;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class TS_KP_BFF_SignOnTest extends TS_KP_BFF_BaseTest {

    private String signInUrl;


    @BeforeClass
    public void setSignInUrl() {

        signInUrl = envUrl + KpBffEndPoints.EP_SIGNON;
    }

    @Test
    public void tc001_SignInSuccess() throws Exception {

        commonUtil.logExecutionStart();
        currentTestCase.assignAuthor("Deepak Tiwari");
        Response signInResponse = requestUtil.postRequestWithBasicAuth(signInUrl, username, password, appHeaders, "", true);
        Assert.assertNotNull(signInResponse.jsonPath().getJsonObject("user"));
        signInResponse.then().assertThat().body(matchesJsonSchemaInClasspath("response-schema-rules//signInResponseSchema.json"));
        commonUtil.logExecutionEnd();
    }


    @Test
    public void tc002_SignInFailureWithInvalidCred() throws Exception {


        commonUtil.logExecutionStart();
        currentTestCase.assignAuthor("Deepak Tiwari");
        Response signInResponse = requestUtil.postRequestWithBasicAuth(signInUrl, "1", "password7", appHeaders, "", true);
        Assert.assertNull(signInResponse.jsonPath().getJsonObject("user"));
        commonUtil.logExecutionEnd();
    }




}
