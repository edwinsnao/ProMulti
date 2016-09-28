package com.example.king.fragement.main.fastJson;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Kings on 2016/4/9.
 */
public class FastJsonRequest<T> extends Request<T> {
    private final Class<T> mClazz;
    private final Response.Listener<T> mListener;
    private final Map<String,String> mHeaders;
    public FastJsonRequest(String url, Response.ErrorListener listener, Class<T> mClazz, Response.Listener<T> mListener, Map<String, String> mHeaders) {
//        super(url, listener);
//        this.mClazz = mClazz;
//        this.mListener = mListener;
//        this.mHeaders = mHeaders;
        this(Method.GET,url,listener,mClazz,mListener,null);
    }

    public FastJsonRequest(int method, String url, Response.ErrorListener listener, Class<T> mClazz, Response.Listener<T> mListener, Map<String, String> mHeaders) {
        super(method, url, listener);
        this.mClazz = mClazz;
        this.mListener = mListener;
        this.mHeaders = mHeaders;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
//        return super.getHeaders();
        return mHeaders !=null ?
                mHeaders:super.getHeaders();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String json = new String(networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));
            return Response.success(JSON.parseObject(json,mClazz),
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T t) {
        mListener.onResponse(t);
    }
}
