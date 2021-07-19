/**
 *
 */
package com.api.dependencyInjector;


import com.api.common.CommonUtil;
import com.api.common.KpBffUtil;
import com.api.common.User;
import com.api.reporting.ExtentManager;
import com.api.restassured.RequestUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.name.Names;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConfigurationModule extends AbstractModule {


    private ExtentReports extent;
    private ExtentTest test;

    @Inject
    private Logger logger;

    @Override
    protected void configure() {

        extent=ExtentManager.getInstance();
        bind(ExtentReports.class).toInstance(extent);
        Names.bindProperties(binder(), getTestEnvironmentProperties());
        Names.bindProperties(binder(), getTestDataProperties());
        bind(RequestUtil.class).toInstance(new RequestUtil());
        bind(CommonUtil.class).toInstance(new CommonUtil());
        bind(KpBffUtil.class).toInstance(new KpBffUtil());
        bind(User.class).toProvider(UserProvider.class);
    }


    /**
     * @return
     */
    private Map<String, String> getTestEnvironmentProperties() {


        test=extent.createTest("Reading Test Data and Config file");
        logger.info("Reading config file...");
        String runMode = null;
        Properties prop = new Properties();
        InputStream input = null;
        Map<String, String> propertiesMap = new HashMap<String, String>();
        try {

            String configFilePath=CommonUtil.getProjectDir() + File.separator+"config"+File.separator+"config.properties";
            logger.info("Config File Path\n"+configFilePath);
            test.info(CommonUtil.getStringForReport("Config File Path:<b>" + configFilePath+"</b>"));
            input = new FileInputStream(configFilePath);
            // load a properties file
            prop.load(input);
            runMode = prop.getProperty("RunMode");
            propertiesMap.put("RunMode", prop.getProperty("RunMode"));
            switch (runMode.toLowerCase()) {
                case "prod":
                    propertiesMap.put("envUrl", prop.getProperty("HOST.KP.PROD"));
                    break;
                case "pp":
                    propertiesMap.put("envUrl", prop.getProperty("HOST.KP.PROD"));
                    break;
                case "release":
                    propertiesMap.put("envUrl", prop.getProperty("HOST.KP.PROD"));
                    //put preprod properties;
                    break;
                default:
                    break;
            }
            // get the property value and print it out

            logger.info("\nReading config file successful\n");
            logger.info("\n*******************Test  Environment is " + runMode + "  *******************\n");
            extent.setSystemInfo("Test Environment",runMode);
            extent.setSystemInfo("Test Environment Url",propertiesMap.get("envUrl"));
            test.pass(CommonUtil.getStringForReport("Test  Environment is <b>" + runMode+"</b>"));
            test.pass(CommonUtil.getStringForReport("Test  Environment Properties <b>" + propertiesMap+"</b>"));
           // System.out.println("Test Environment Properties " + propertiesMap);
            logger.info("Test Environment Properties..\n"+propertiesMap);

        } catch (IOException ex) {
            logger.log(Level.SEVERE,"Reading config file failed..");
            logger.log(Level.SEVERE,ex.getLocalizedMessage());
            test.error(ex.getLocalizedMessage());
            test.error(ex.getStackTrace().toString());
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    test.error(ex.getLocalizedMessage());
                    test.error(ex.getStackTrace().toString());
                    ex.printStackTrace();
                }
            }
        }
        return propertiesMap;
    }


    /**
     * @return
     */
    private Map<String, String> getTestDataProperties() {


        String runMode = null;
        Properties prop = new Properties();
        InputStream input = null;
        Map<String, String> dataPropsMap = new HashMap<String, String>();
        try {
            logger.info("Reading Test Data file");
            String testDataFilePath=CommonUtil.getProjectDir() +File.separator+"config"+File.separator+"data.properties";
            logger.info("Test Data File Path\n"+testDataFilePath);
            test.info(CommonUtil.getStringForReport("Test Data File Path:<b>" + testDataFilePath+"</b>"));
            input = new FileInputStream(testDataFilePath);
            // load a properties file
            prop.load(input);
            dataPropsMap.put("username", prop.getProperty("KP.SUBSCRIBER.USERNAME"));
            dataPropsMap.put("password", prop.getProperty("KP.SUBSCRIBER.PASSWORD"));
            dataPropsMap.put("plateform", prop.getProperty("KP.PLATEFORM.NAME"));
            dataPropsMap.put("plateformVersion", prop.getProperty("KP.PLATEFORM.VERSION"));
            dataPropsMap.put("app", prop.getProperty("KP.APP.NAME"));
            dataPropsMap.put("device", prop.getProperty("KP.APP.DEVICE"));
            logger.info("\nTest Data Properties..\n"+dataPropsMap);
            test.pass(CommonUtil.getStringForReport("Test Data Properties <b>" + dataPropsMap+"</b>"));

        } catch (IOException ex) {
            logger.log(Level.SEVERE,"Reading Test Data file failed..");
            logger.log(Level.SEVERE,ex.getLocalizedMessage());
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    test.error(ex.getLocalizedMessage());
                    test.error(ex.getStackTrace().toString());
                    ex.printStackTrace();
                }
            }
        }
        return dataPropsMap;
    }


}
