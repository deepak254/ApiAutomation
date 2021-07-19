package com.api.testscripts;

import com.api.common.CommonUtil;
import com.api.common.KpBffUtil;
import com.api.common.User;
import com.api.dependencyInjector.ConfigurationModule;
import com.api.restassured.RequestUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

@Guice(modules = {ConfigurationModule.class})
public class TS_KP_BFF_BaseTest {


    @Inject
    @Named("envUrl")
    public String envUrl;

    @Inject
    @Named("username")
    public String username;

    @Inject
    @Named("password")
    public String password;

    @Inject
    @Named("plateformVersion")
    public String plateformVersion;

    @Inject
    @Named("plateform")
    public String plateform;

    @Inject
    @Named("app")
    public String appName;

    @Inject
    @Named("device")
    public String deviceName;

    @Inject
    public User appUser;

    @Inject
    public ExtentReports extent;

    @Inject
    public RequestUtil requestUtil;

    @Inject
    public KpBffUtil bffUtil;

    @Inject
    public CommonUtil commonUtil;

    @Inject
    public Logger logger;


    public ExtentTest currentTestCase;
    LinkedHashMap<String,Object> appHeaders;

    @BeforeSuite
    public void beforeSuite() {

        commonUtil.logExecutionStart();
        appHeaders=bffUtil.getBffHeadersAfterSignIn(appUser);
        logger.info("\n****************APP HEADERS******************\n");
        logger.info(CommonUtil.prettyPrintHeaders(appHeaders));
        commonUtil.logExecutionEnd();

    }

    @BeforeTest
    public void initializeTest() {

       commonUtil.logExecutionStart();
       logger.info(appUser.toString());
       commonUtil.logExecutionEnd();
    }


    @BeforeMethod(alwaysRun = true)
    public void beforeEachTestCase(Method method, XmlTest xmlTest) {


        currentTestCase = extent.createTest(method.getName());
        currentTestCase.assignCategory(method.getDeclaringClass().getSimpleName());
        commonUtil.setCurrentTestInstance(currentTestCase);
        bffUtil.setCurrentTestInstance(currentTestCase);

    }

    @AfterMethod
    public void afterEachTestCase(ITestResult result, XmlTest test) {
        int status = result.getStatus();
        try {
            switch (status) {
                case 1:
                    currentTestCase.pass("TEST PASSED");
                    break;
                case 2:
                    currentTestCase.fail("TEST FAILED ");
                    currentTestCase.error(CommonUtil.getStringForReport(result.getThrowable().getMessage()));
                    break;
                case 3:
                    currentTestCase.skip("TEST SKIPPED ");
                    currentTestCase.skip(result.getThrowable());
                    break;
            }
        } catch (Exception e) {
            currentTestCase.error(e);
        }
        currentTestCase.info("<pre>ENDING TESTCASE...." + result.getName() + " </pre> ");
    }


    @AfterSuite
    public void tear() {
        extent.flush();

    }


}
