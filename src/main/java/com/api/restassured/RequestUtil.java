package com.api.restassured;

import com.api.common.CommonUtil;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.google.inject.Inject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class RequestUtil {

    RequestSpecification request = RestAssured.given();
    public static final String HTTP_METHOD_GET = "GET";
    public static final String HTTP_METHOD_POST = "POST";
    public static final String HTTP_METHOD_PUT = "PUT";
    public static final String HTTP_METHOD_DELETE = "DELETE";

    @Inject
    private Logger logger;

    @Inject
    private CommonUtil commonUtil;


    /**
     * @param url
     * @param headers
     * @param parameters
     * @param isLoggingRequired
     * @return
     */
    public Response getRequestWithHeadersAndParam(String url, Map<String, Object> headers, Map<String, Object> parameters, boolean isLoggingRequired) {

        printRequestLogsToConsole(HTTP_METHOD_GET, url, headers, parameters, "");
        Response response = null;
        if (isLoggingRequired)
            printReqLogsToReport(HTTP_METHOD_GET, url, headers, null, parameters);
        try {
            response = request.given().headers(headers)
                    .and()
                    .params(parameters)
                    .when()
                    .get(url)
                    .then()
                    .extract().response();
            if (isLoggingRequired)
                printResponseLogsToReport(response);
        } catch (Exception e) {
            printExceptionLogsToConsole(e);
            printExceptionLogsToReport(e);
        }

        printResponseLogsToConsole(response);
        return response;

    }


    /**
     * @param url
     * @param headers
     * @param params
     * @param body
     * @param isLoggingRequired
     * @return
     */
    public Response postRequestWithHeadersParamAndBody(String url, Map<String, Object> headers, Map<String, Object> params, Object body, boolean isLoggingRequired) {

        printRequestLogsToConsole(HTTP_METHOD_POST, url, headers, params, body.toString());
        Response response = null;
        if (isLoggingRequired)
            printReqLogsToReport(HTTP_METHOD_POST, url, headers, body, params);
        try {
            response = request.given().headers(headers)
                    .and()
                    .params(params)
                    .body(body.toString())
                    .when()
                    .post(url)
                    .then()
                    .extract().response();
            if (isLoggingRequired)
                printResponseLogsToReport(response);
            printResponseLogsToConsole(response);
        } catch (Exception e) {
            printExceptionLogsToConsole(e);
            printExceptionLogsToReport(e);
        }

        return response;

    }


    /**
     * @param url
     * @param headers
     * @param body
     * @param isLoggingRequired
     * @return
     */
    public Response postRequestWithHeadersAndBody(String url, Map<String, Object> headers, Object body, boolean isLoggingRequired) {

        printRequestLogsToConsole(HTTP_METHOD_POST, url, headers, null, body.toString());
        Response response = null;
        if (isLoggingRequired)
            printReqLogsToReport(HTTP_METHOD_POST, url, headers, body, null);
        try {
            response = request.given().
                    headers(headers)
                    .and()
                    .body(body.toString())
                    .when()
                    .post(url)
                    .then()
                    .extract().response();
            if (isLoggingRequired)
                printResponseLogsToReport(response);
            printResponseLogsToConsole(response);
        } catch (Exception e) {
            printExceptionLogsToConsole(e);
            printExceptionLogsToReport(e);
        }
        return response;

    }

    /**
     * @param url
     * @param headers
     * @param body
     * @param isLoggingRequired
     * @return
     */
    public Response postRequestWithHeadersAndJsonBody(String url, Map<String, Object> headers, JSONObject body, boolean isLoggingRequired) {

        Response response = null;
        if (isLoggingRequired)
            printReqLogsToReport(HTTP_METHOD_POST, url, headers, body, null);
        try {
            response = request.given().headers(headers)
                    .and()
                    .body(body.toString())
                    .when()
                    .post(url)
                    .then()
                    .extract().response();
            if (isLoggingRequired)
                printResponseLogsToReport(response);
        } catch (Exception e) {
            printExceptionLogsToReport(e);
        }
        return response;

    }


    /**
     * @param url
     * @param username
     * @param pwd
     * @param headers
     * @param body
     * @param isLoggingRequired
     * @return
     */
    public Response postRequestWithBasicAuth(String url, String username, String pwd, Map<String, Object> headers, Object body, boolean isLoggingRequired) {

        Response response = null;
        printRequestLogsToConsole(HTTP_METHOD_POST, url, headers, null, body);
        if (isLoggingRequired) {
            commonUtil.getCurrentTestInstance().info(CommonUtil.getStringForReport("<b>AUTH DETAILS - </b>" + " username : <b>" + username + "</b> , password: <b>" + pwd + "</b>"));
            printReqLogsToReport(HTTP_METHOD_POST, url, headers, body, null);
        }
        try {
            response = request.given().auth().preemptive().basic(username, pwd)
                    .headers(headers)
                    .contentType(ContentType.JSON)
                    .body(body.toString())
                    .post(url)
                    .then()
                    .extract().response();

            if (isLoggingRequired)
                printResponseLogsToReport(response);

            printResponseLogsToConsole(response);
        } catch (Exception e) {
            printExceptionLogsToConsole(e);
            printExceptionLogsToReport(e);
        }
        return response;

    }


    /**
     * @param url
     * @param headers
     * @param body
     * @param isLoggingRequired
     * @return
     */
    public Response putRequestWithHeadersAndBody(String url, Map<String, Object> headers, Object body, boolean isLoggingRequired) {

        printRequestLogsToConsole(HTTP_METHOD_PUT, url, headers, null, body.toString());
        Response response = null;
        if (isLoggingRequired)
            printReqLogsToReport(HTTP_METHOD_PUT, url, headers, body, null);
        try {
            response = request.given().headers(headers)
                    .and()
                    .body(body.toString())
                    .when()
                    .put(url)
                    .then()
                    .extract().response();
            if (isLoggingRequired)
                printResponseLogsToReport(response);
            printResponseLogsToConsole(response);
        } catch (Exception e) {
            printExceptionLogsToConsole(e);
            printExceptionLogsToReport(e);
        }

        return response;

    }

    /**
     * @param httpMethod
     * @param apiEndPoint
     * @param apiHeaders
     * @param apiRequest
     * @param parameters
     */
    public void printReqLogsToReport(String httpMethod, String apiEndPoint, Map<String, Object> apiHeaders, Object apiRequest, Map<String, Object> parameters) {

        commonUtil.getCurrentTestInstance().info(CommonUtil.getStringForReport("<b>REQUEST METHOD </b>" + httpMethod));
        commonUtil.getCurrentTestInstance().info(CommonUtil.getStringForReport("<b>REQUEST URL</b> \n\n" + apiEndPoint));
        if (apiHeaders !=null)
          commonUtil.getCurrentTestInstance().info(CommonUtil.getStringForReport("<b>REQUEST HEADERS</b> \n\n" + CommonUtil.prettyPrintHeaders(apiHeaders)));

        commonUtil.getCurrentTestInstance().info(CommonUtil.getStringForReport("<b>REQUEST PARAMETERS</b> \n\n" + parameters));

        if (apiRequest !=null)
           commonUtil.getCurrentTestInstance().info(CommonUtil.getStringForReport("<b>REQUEST BODY</b>\n\n" + CommonUtil.getFormattedJSON(apiRequest.toString())));

    }

    /**
     * @param apiResponse
     */
    public void printResponseLogsToReport(Response apiResponse) {

        commonUtil.getCurrentTestInstance().info(CommonUtil.getStringForReport("<b>RESPONSE CODE</b> " + apiResponse.getStatusCode()));
        commonUtil.getCurrentTestInstance().info(CommonUtil.getStringForReport("<b>RESPONSE HEADERS</b> \n\n" + apiResponse.getHeaders()));
        commonUtil.getCurrentTestInstance().info(CommonUtil.getStringForReport("<b>RESPONSE BODY </b>\n\n" + apiResponse.asPrettyString()));

    }

    /**
     * @param exception
     */
    public void printExceptionLogsToReport(Exception exception) {

        commonUtil.getCurrentTestInstance().info(CommonUtil.getStringForReport("EXCEPTION OCCURED "));
        commonUtil.getCurrentTestInstance().info(CommonUtil.getStringForReport("<b>ERROR MESSAGE </b>\n" + exception.getMessage()));
        commonUtil.getCurrentTestInstance().info(CommonUtil.getStringForReport("<b>EXCEPTION STACK TRACE </b>"));
        commonUtil.getCurrentTestInstance().info(MarkupHelper.createCodeBlock(exception.toString()));

    }

    /**
     * @param reqMethod
     * @param reqUrl
     * @param headers
     * @param parameters
     * @param body
     */
    public void printRequestLogsToConsole(String reqMethod, String reqUrl, Map<String, Object> headers, Map<String, Object> parameters, Object body) {

        logger.info("Making " + reqMethod + " Request to " + reqUrl);

        if (headers != null)
            logger.info("Request Headers \n" + CommonUtil.prettyPrintHeaders(headers));
        else
            logger.info("Request Headers \n" + headers);

        logger.info("Request Parameters \n" + parameters);
        if (body != null)
            logger.info("Request Body \n" + CommonUtil.getFormattedJSON(body.toString()));
        else
            logger.info("Request Body \n" + body);
    }

    /**
     * @param response
     */
    public void printResponseLogsToConsole(Response response) {

        logger.info("API Response:\n" + CommonUtil.getFormattedJSON(response.asString()));
        logger.info("Response Headers \n" + response.getHeaders());
    }


    /**
     * @param e
     */
    public void printExceptionLogsToConsole(Exception e) {

        logger.log(Level.SEVERE, "Request failed...");
        logger.log(Level.SEVERE, " Failed Message ::" + e.getLocalizedMessage());
        e.printStackTrace();
    }

}
