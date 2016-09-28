package com.example.king.fragement.midea;

import org.json.JSONException;

/**
 * Created by Kings on 2016/4/10.
 */
public class DefaultParser implements RespParser<String> {
    @Override
    public String parseResponse(String result) throws JSONException {
        return result;
    }
}
