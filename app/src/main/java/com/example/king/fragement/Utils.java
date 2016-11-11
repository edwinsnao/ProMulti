package com.example.king.fragement;

import android.content.Context;
import android.util.Log;

import com.example.king.fragement.main.LogWrap;
import com.example.king.fragement.main.SharedPreferrenceHelper;

import java.io.InputStream;
import java.io.OutputStream;

public class Utils {
	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static int getCurrentTheme(Context context){
		String value = SharedPreferrenceHelper.gettheme(context);
		return Integer.parseInt(value);
	}

	public static void switchAppTheme(Context context){
		String value = SharedPreferrenceHelper.gettheme(context);
		LogWrap.d("value in switchappTheme"+value);
		switch (Integer.valueOf(value)){
			case 1:
				SharedPreferrenceHelper.settheme(context,"2");
				break;
			case 2:
				SharedPreferrenceHelper.settheme(context,"1");
				break;
			default:
				SharedPreferrenceHelper.settheme(context,"1");
				break;
		}
	}
	public static int getAppTheme(Context context){
		String value = SharedPreferrenceHelper.gettheme(context);
		switch (Integer.valueOf(value)){
			case 1:
				return R.style.AppTheme;
			case 2:
				return R.style.AppBaseTheme_Night;
			default:
				return R.style.AppTheme;
		}
	}
}