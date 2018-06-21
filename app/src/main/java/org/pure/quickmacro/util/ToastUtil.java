package org.pure.quickmacro.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * 提示工具
 */
public class ToastUtil {
	private static Toast mToast;

	@SuppressLint("ShowToast")
	public static void showToast(Context context, CharSequence msg) {
		if (ToastUtil.mToast == null) {
			mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(msg);
		}
		if(Looper.myLooper() == Looper.getMainLooper()){
			mToast.show();
		}else{
			Looper.prepare();  
			mToast.show();
	        Looper.loop();  
		}
	}
}
