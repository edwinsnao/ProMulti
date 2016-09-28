package com.example.king.fragement.midea;

import org.json.JSONException;

/**
 * Created by Kings on 2016/4/10.
 */
public interface RespParser<T> {
    public T parseResponse(String result) throws JSONException;
}
