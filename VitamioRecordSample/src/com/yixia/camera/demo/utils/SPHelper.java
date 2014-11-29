package com.yixia.camera.demo.utils;


import com.yixia.camera.demo.VCameraDemoApplication;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences工具类
 * 
 * @author zxp
 * 
 */
public class SPHelper {
	private SPHelper() {

	}

	private static SharedPreferences sp;
	private static SPHelper spHelper = new SPHelper();

	public static SPHelper getInstance(Context context) {
		sp = context.getSharedPreferences("info", Context.MODE_PRIVATE);
		return spHelper;
	}

	/**
	 * 
	 * @param SPNmame
	 * @return 当前name的SharedPreferences
	 */
	public static SharedPreferences getSharedPreferencesByName(String SPNmame) {
		return VCameraDemoApplication.getContext().getApplicationContext()
				.getSharedPreferences(SPNmame, Context.MODE_PRIVATE);
		
	}

	/**
	 * 根据key值，查找对应的boolean值
	 * 
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key, boolean def) {
		return sp.getBoolean(key, def);
	}

	/**
	 * 存储boolean值
	 * 
	 * @param key
	 * @param value
	 */
	public void setBoolean(String key, boolean value) {
		sp.edit().putBoolean(key, value).commit();
	}

	/**
	 * 存储String值
	 * 
	 * @param key
	 * @param value
	 */
	public void setString(String key, String value) {
		sp.edit().putString(key, value).commit();
	}

	/**
	 * 根据key值，查找对应的String值
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key, String def) {
		return sp.getString(key, def);
	}

	public int getInt(String key, int def) {
		return sp.getInt(key, def);
	}

	public void setInt(String key, int value) {
		sp.edit().putInt(key, value).commit();
	}
}
