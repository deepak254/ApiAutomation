package com.api.common;

import com.api.restassured.RequestUtil;
import com.aventstack.extentreports.ExtentTest;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import io.restassured.response.Response;

import java.util.LinkedHashMap;

import static com.api.common.APIConstants.*;
import static com.api.common.APIConstants.CHANNEL_IOS;

public class KpBffUtil {



    @Inject
    private RequestUtil requestUtil;

    @Inject
    @Named("envUrl")
    private String baseUrl;


    @Inject
    @Named("plateformVersion")
    private  String plateformVersion;

    @Inject
    @Named("plateform")
    private  String plateform;

    @Inject
    @Named("app")
    private  String appName;

    @Inject
    @Named("device")
    private  String deviceName;


    private ExtentTest currentTestCase;

    public KpBffUtil() {

    }


    public ExtentTest getCurrentTestInstance() {

        return currentTestCase;
    }

    public void setCurrentTestInstance(ExtentTest extentTest) {
        this.currentTestCase = extentTest;
    }


    public  LinkedHashMap<String, Object> getHeaders() {
        LinkedHashMap<String, Object> headerInfo = new LinkedHashMap<>();
        headerInfo.put("X-apiKey","kaisermobil93908784817875726966");
        headerInfo.put("Content-Type","application/json");
        headerInfo.put("X-useragentcategory",plateform.substring(0,1).toUpperCase());
        headerInfo.put("X-useragenttype", deviceName);
        headerInfo.put("X-osversion", plateformVersion);
        headerInfo.put("X-appName", appName);
        return headerInfo;
    }

    public User signIn(String plateForm, String username, String password,boolean isLoggingRequired) {

        User user = new User();
        String signInUrl = this.baseUrl.concat(KpBffEndPoints.EP_SIGNON);
        LinkedHashMap<String, Object> appHeaders = getHeaders();
        Response signInResponse = requestUtil.postRequestWithBasicAuth(signInUrl, username, password, appHeaders, "", isLoggingRequired);
        if (signInResponse.jsonPath().getJsonObject("user") != null) {
            user.setEmail(signInResponse.jsonPath().getString("user.email"));
            user.setFirstName(signInResponse.jsonPath().getString("user.firstName"));
            user.setLastName(signInResponse.jsonPath().getString("user.lastName "));
            user.setGuid(signInResponse.jsonPath().getString("user.guid"));
            user.setRegion(signInResponse.jsonPath().getString("user.region"));
            user.setSsoSession(signInResponse.header("ssosession"));
            user.setCorrelationID(signInResponse.header("X-CorrelationID"));
        }
        return user;

    }



    public  LinkedHashMap<String, Object> getBffHeadersAfterSignIn(User user) {

        LinkedHashMap<String, Object> headerInfo = new LinkedHashMap<>();

        headerInfo.put("X-apiKey","kaisermobil93908784817875726966");
        headerInfo.put("Content-Type","application/json");
        headerInfo.put("X-useragentcategory",plateform.substring(0,1).toUpperCase());
        headerInfo.put("X-useragenttype", deviceName);
        headerInfo.put("X-osversion", plateformVersion);
        headerInfo.put("X-appName", appName);
        headerInfo.put("X-idType", "GUID");
        headerInfo.put("X-guid", user.getGuid());
        headerInfo.put("X-region", user.getRegion());
        headerInfo.put("X-Correlationid", user.getCorrelationID());
        headerInfo.put("X-mrnregion", user.getRegion());
        headerInfo.put("X-ssosession", user.getSsoSession());

        return headerInfo;
    }



}
