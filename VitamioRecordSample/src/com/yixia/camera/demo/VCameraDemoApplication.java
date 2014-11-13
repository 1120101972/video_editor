package com.yixia.camera.demo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yixia.camera.VCamera;
import com.yixia.camera.demo.service.AssertService;
import com.yixia.camera.util.DeviceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VCameraDemoApplication extends Application {

	private static VCameraDemoApplication application;
	private List<Activity> mActivitys = new ArrayList<Activity>();
	@Override
	public void onCreate() {
		super.onCreate();
		application = this;

		// 设置拍摄视频缓存路径
		File dcim = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		if (DeviceUtils.isZte()) {
			if (dcim.exists()) {
				VCamera.setVideoCachePath(dcim + "/Camera/VCameraDemo/");
				VCamera.setImageCachePath(dcim + "/Camera/VCameraImage/");
			} else {
				VCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/",
						"/sdcard-ext/")
						+ "/Camera/VCameraDemo/");
				VCamera.setImageCachePath(dcim.getPath().replace("/sdcard/",
						"/sdcard-ext/")
						+ "/Camera/VCameraImage/");
			}
		} else {
			VCamera.setVideoCachePath(dcim + "/Camera/VCameraDemo/");
			VCamera.setImageCachePath(dcim + "/Camera/VCameraImage/");
		}
		// 开启log输出,ffmpeg输出到logcat
		VCamera.setDebugMode(true);
		// 初始化拍摄SDK，必须
		VCamera.initialize(this);

		// 解压assert里面的文件
		startService(new Intent(this, AssertService.class));
	}

	public static Context getContext() {
		return application;
	}
	
	public void push(Activity c) {
		for (int i = 0; i < mActivitys.size(); i++) {
			if (mActivitys.get(i) == c) {
				return;
			}
		}
		mActivitys.add(c);
	}
	
	public void pull(Activity c) {
		for (int i = 0; i < mActivitys.size(); i++) {
			if (mActivitys.get(i) == c) {
				mActivitys.remove(i);
				return;
			}
		}
	}
	
	/**
	 * toast singleton，用来统一显示toast，这样就可以实现toast的快速刷新
	 * 
	 */
	public enum toastMgr {
		builder;

		private View v;
		private TextView tv;
		private Toast toast;

		private void init(Context c) {
			// v = Toast.makeText(c, "", Toast.LENGTH_SHORT).getView();
			v = LayoutInflater.from(c).inflate(R.layout.toast, null);
			tv = (TextView) v.findViewById(R.id.tv_toast);
			toast = new Toast(c);
			toast.setView(v);
		}

		public void display(CharSequence text, int duration) {
			if (text.length() != 0) {
				tv.setText(text);
				toast.setDuration(duration);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
		}

		public void display(int Resid, int duration) {
			if (Resid != 0) {
				tv.setText(Resid);
				toast.setDuration(duration);
				toast.show();
			}
		}

		public void display(CharSequence text, int duration, int position, int yOffset) {
			if (text.length() != 0) {
				tv.setText(text);
				toast.setDuration(duration);
				toast.setGravity(position, 0, yOffset);
				toast.show();
			}
		}
	}

}
