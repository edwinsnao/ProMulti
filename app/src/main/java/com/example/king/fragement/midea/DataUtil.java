package com.example.king.fragement.midea;

import android.util.Log;

import com.edwinsnao.midea.CommonException;
import com.example.king.fragement.main.LogWrap;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataUtil
{
//    static int responseCode;


    public static String doGet(String urlStr) throws CommonException
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10000);
            conn.setDoInput(true);
//            conn.setDoOutput(true);
//            responseCode = conn.getResponseCode();
//			有乱码
//            if (conn.getResponseCode() == 200)
//            {
//                InputStream is = conn.getInputStream();
//                int len = 0;
//                byte[] buf = new byte[1024];
//
//                while ((len = is.read(buf)) != -1)
//                {
//                    sb.append(new String(buf, 0, len, "UTF-8"));
//                }
//
//                is.close();
//            转换为字节就不会乱码
//            LogWrap.d("responsecode in datautil"+responseCode);
            if (conn.getResponseCode() == 200)
            {
                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is,"UTF-8");
                int len = 0;
                char[] buf = new char[1024];

                while ((len = isr.read(buf)) != -1)
                {
                    sb.append(new String(buf, 0, len));
                }

                is.close();
                isr.close();
            } else
            {
                throw new CommonException("网络失败");
            }

        } catch (Exception e)
        {
            throw new CommonException("网络失败");
        }
        return sb.toString();
    }

//    public static int getResponseCode(){
//        return responseCode;
//    }



}
