package com.api.restassured;

import org.json.JSONException;
import org.json.JSONObject;

public class BffParamGenerator {



    public static JSONObject getUpdateLoginParam(String email, String job) throws JSONException {

        JSONObject body=new JSONObject();
        body.put("email",email);
        body.put("job",job);
        return body;
    }

}
