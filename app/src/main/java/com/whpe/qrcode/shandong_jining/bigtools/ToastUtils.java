package com.whpe.qrcode.shandong_jining.bigtools;


import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

/***
 * ������Toast
 */
public class ToastUtils {
	
	
	private static Toast mToast;
	private static TextView tv_toast;
	
	public static void showToast(Context context, CharSequence text){
		if (mToast == null) {

				mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		}else {
			mToast.setText(text);
		}
		mToast.show();
	}
	

}
