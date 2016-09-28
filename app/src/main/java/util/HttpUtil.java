package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtil {

	/**
	 * @param  PATH  URL地址
	 * @param  params  发送的数据
	 * @param  encode 编码类型
	 * @return String 状态码
	 * @throws Exception
	 * */
	public static String sendPostMessage(String PATH,Map<String,String> params,String encode){
		//作为StringBuffer初始化的字符串
		
		StringBuffer buffer = new StringBuffer(); 
		//buffer.append("?");
		try {
			if(params != null &&! params.isEmpty()){
				for(Map.Entry<String, String> entry : params.entrySet()){
					//URLEncoder.encode防止乱码
					buffer.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(),encode)).append("&");

				}
				//删除掉最后一个&
				buffer.deleteCharAt(buffer.length()-1);
			}

			URL url = new URL(PATH);
			
			//System.out.println(buffer.toString());
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(5000);
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoInput(true);//表示从服务器获取数据
			urlConnection.setDoOutput(true);//表示向服务器写数据
			//获得上传信息的字节大小以及长度
			byte[] mydata = buffer.toString().getBytes();

			//表示设置请求体的类型是文本类型
			urlConnection.setRequestProperty("content-Type", "application/x-www-form-urlencoded");
			urlConnection.setRequestProperty("content-Length", String.valueOf(mydata.length));
			//获得输出流，向服务器输出数据
			OutputStream outputStream = urlConnection.getOutputStream();
			outputStream.write(mydata,0,mydata.length);
			outputStream.flush();
			outputStream.close();
			//获得服务器响应的结果和状态码
			int responseCode = urlConnection.getResponseCode();
			if(responseCode==200){
				return changeInputStream(urlConnection.getInputStream(),encode);
			}
			
		}catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}

	//将一个输入流转换成指定编码的字符串
	private static String changeInputStream(InputStream inputStream,String encode) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = "";
		if(inputStream!=null){
			try {
				while((len = inputStream.read(data))!=-1){
					outputStream.write(data, 0, len);
				}
				result = new String(outputStream.toByteArray(), encode);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	
	
}






