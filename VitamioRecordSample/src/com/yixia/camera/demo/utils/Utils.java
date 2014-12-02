package com.yixia.camera.demo.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.yixia.camera.demo.R;
import com.yixia.camera.demo.VCameraDemoApplication;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Utils {

	public static Handler handler;

	// /**
	// * 弹出SoufunProcessDialog
	// *
	// */
	// public static Dialog showProcessDialog(Context context) {
	// Dialog customDialog = new Dialog(context, R.style.Theme_Light_Dialog);
	// customDialog.setContentView(R.layout.process_dialog);
	// customDialog.findViewById(R.id.piv_loading_process).setVisibility(View.VISIBLE);
	// customDialog.show();
	// return customDialog;
	// }

	// /**
	// * 弹出SoufunProcessDialog
	// *
	// * @param content
	// * 显示内容
	// */
	// public static Dialog showProcessDialog(Context context, String content) {
	// Dialog customDialog = new Dialog(context,
	// R.style.Theme_Light_ProcessDialog_Blue);
	// customDialog.setContentView(R.layout.process_dialog);
	// customDialog.findViewById(R.id.piv_loading_process).setVisibility(View.VISIBLE);
	// ((TextView) customDialog.findViewById(R.id.tv_process)).setText(content);
	// customDialog.show();
	// return customDialog;
	// }

	/**
	 * 获得指定目录的剩余空间.
	 * 
	 * @param path
	 * @return
	 */
	public static long getFreeSpace(String path) {
		StatFs sf = new StatFs(path);
		long blockSize = sf.getBlockSize();
		long availCount = sf.getAvailableBlocks();

		return availCount * blockSize;
	}

	/**
	 * toast （默认 时间Toast.LENGTH_LONG）
	 * 
	 * @param c
	 * @param msg
	 *            内容
	 */
	public static void toast(Context c, String msg) {
		toast(c, msg, true);
	}

	/**
	 * toast
	 * 
	 * @param c
	 * @param msg
	 *            内容
	 * @param duration
	 *            时间
	 */
	public static void toast(Context c, String msg, int duration) {
		toast(c, msg, true, duration);
	}

	/**
	 * toast 时间（默认 时间Toast.LENGTH_LONG）
	 * 
	 * @param c
	 * @param msg
	 *            内容
	 * @param show
	 *            是否显示
	 */
	public static void toast(Context c, String msg, boolean show) {
		toast(c, msg, show, Toast.LENGTH_LONG);
	}

	/**
	 * toast
	 * 
	 * @param c
	 * @param msg
	 *            内容
	 * @param show
	 *            是否显示
	 * @param duration
	 *            时间
	 */
	public static void toast(Context c, String msg, boolean show, int duration) {
		if (!show)
			return;
		VCameraDemoApplication.toastMgr.builder.display(msg, duration);
	}

	/**
	 * toast 时间（默认 时间Toast.LENGTH_LONG）
	 * 
	 * @param c
	 * @param resid
	 *            内容资源id
	 */
	public static void toast(Context c, int resid) {
		toast(c, resid, true);
	}

	/**
	 * toast
	 * 
	 * @param c
	 * @param resid
	 *            内容资源id
	 * @param duration
	 *            时间
	 */
	public static void toast(Context c, int resid, int duration) {
		toast(c, resid, true, duration);
	}

	/**
	 * toast 时间（默认 时间Toast.LENGTH_LONG）
	 * 
	 * @param c
	 * @param resid
	 *            内容资源id
	 * @param show
	 *            是否显示
	 */
	public static void toast(Context c, int resid, boolean show) {
		toast(c, resid, show, Toast.LENGTH_LONG);
	}

	/**
	 * toast
	 * 
	 * @param c
	 * @param resid
	 *            内容资源id
	 * @param show
	 *            是否显示
	 * @param duration
	 *            时间
	 */
	public static void toast(Context c, int resid, boolean show, int duration) {
		if (!show)
			return;
		VCameraDemoApplication.toastMgr.builder.display(resid, duration);
	}

	public static void toast(Context c, String msg, boolean show, int duration,
			int position, int yOffset) {
		if (!show)
			return;
		VCameraDemoApplication.toastMgr.builder.display(msg, duration,
				position, yOffset);
	}

	/**
	 * 检查SDCard是否存在
	 * 
	 * @return
	 */
	public static boolean checkSDCardPresent() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;

	}

	/**
	 * 检查SDCard是否可读
	 * 
	 * @return
	 */
	public static boolean checkSDCardRead() {
		if (checkSDCardPresent())
			return Environment.getExternalStorageDirectory().canRead();
		else
			return false;
	}

	/**
	 * 检查SDCard是否可写
	 * 
	 * @return
	 */
	public static boolean checkSDCardWriter() {
		if (checkSDCardPresent())
			return Environment.getExternalStorageDirectory().canWrite();
		else
			return false;
	}

	/**
	 * 检查sdcard的剩余容量是否超过size
	 * 
	 * @param size
	 *            单位是KB
	 * @return
	 */
	public static boolean checkSDCardCapacity(int size) {
		// 取得sdcard文件路径
		File pathFile = android.os.Environment.getExternalStorageDirectory();
		android.os.StatFs statfs = new android.os.StatFs(pathFile.getPath());
		// 获取SDCard上每个block的SIZE
		long blocSize = statfs.getBlockSize();
		// 获取可供程序使用的Block的数量
		long availaBlock = statfs.getAvailableBlocks();
		if ((availaBlock * blocSize / 1024) > size)
			return true;
		else
			return false;
	}

	/**
	 * 检查sdcard中是否存在制定路径的文件
	 * 
	 * @param path
	 * @return
	 */
	public static boolean checkSDCardFile(String path) {
		if (path == null || "".equals(path.trim()))
			return false;
		File file = new File(path);
		return file.exists();
	}

	/**
	 * 解压数据库文件并保存(注意这个方法比较耗时请在线程里使用)
	 * 
	 * @param fileName
	 *            数据库文件名称
	 * @param context
	 * @throws Exception
	 */
	public static void UnZipFolder(String fileName, Context context)
			throws Exception {
		// 获取指定数据库绝对路径
		String outPathString = context.getDatabasePath("db").getAbsolutePath()
				+ "/";
		outPathString = outPathString.substring(0, outPathString.length() - 3);
		UnZipFolder(fileName, outPathString, context);
	}

	public static void UnZipFolder(String fileName, String outPathString,
			Context context) throws Exception {
		InputStream in = context.getAssets().open(fileName);
		java.util.zip.ZipInputStream inZip = new java.util.zip.ZipInputStream(
				in);
		java.util.zip.ZipEntry zipEntry;
		String szName = "";
		while ((zipEntry = inZip.getNextEntry()) != null) {
			szName = zipEntry.getName();
			if (zipEntry.isDirectory()) {
				szName = szName.substring(0, szName.length() - 1);
				java.io.File folder = new java.io.File(outPathString
						+ java.io.File.separator + szName);
				folder.mkdirs();
			} else {
				java.io.File file = new java.io.File(outPathString
						+ java.io.File.separator + szName);
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
				java.io.FileOutputStream out = new java.io.FileOutputStream(
						file);
				int len;
				byte[] buffer = new byte[1024];
				while ((len = inZip.read(buffer)) != -1) {
					out.write(buffer, 0, len);
					out.flush();
				}
				out.close();
			}
		}
		inZip.close();
	}

	/**
	 * 保存数据库文件(注意这个方法比较耗时请在线程里使用)
	 * 
	 * @param context
	 * @param fileName
	 * @param path
	 * @return
	 */
	public static boolean retrieveApkFromAssets(String fileName, Context context) {
		String outPathString = context.getDatabasePath("db").getAbsolutePath()
				+ "/";
		outPathString = outPathString.substring(0, outPathString.length() - 3);
		return retrieveApkFromAssets(fileName, outPathString, context);
	}

	public static boolean retrieveApkFromAssets(String fileName,
			String outPathString, Context context) {
		boolean bRet = false;

		try {
			InputStream is = context.getAssets().open(fileName);

			File file = new File(outPathString + "/" + fileName);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);

			byte[] temp = new byte[1024];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}

			fos.close();
			is.close();

			bRet = true;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return bRet;
	}

	/**
	 * 隐藏软键盘
	 * 
	 * @param activity
	 *            要隐藏软键盘的activity
	 */
	public static void hideSoftKeyBoard(Activity activity) {
		final View v = activity.getWindow().peekDecorView();
		if (v != null && v.getWindowToken() != null) {
			try {
				((InputMethodManager) activity
						.getSystemService(Context.INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(activity.getCurrentFocus()
								.getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 显示软键盘
	 * 
	 * @param context
	 * @param editText
	 */
	public static void showSoftKeyBroad(Context context, EditText editText) {
		InputMethodManager mgr = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// only will trigger it if no physical keyboard is open
		mgr.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
	}

	/**
	 * <功能详细描述>判断网络是否可用<br>
	 * 
	 * @param context
	 * @return<br>
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否联网
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetConn(Context context) {
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = connectivityManager.getActiveNetworkInfo();
			if (info != null && info.isAvailable()) {
				String name = info.getTypeName();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	/**
	 * 隐藏软键盘
	 * 
	 * @param context
	 * @param editText
	 */
	public static void hideSoftKeyBroad(Context context, EditText editText) {
		InputMethodManager mgr = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	/**
	 * 显示软键盘，和上面的showSoftKeyBroad方法的区别在于，如果从其他activity返回的时候需要延迟一点才能显示软键盘
	 * 
	 * @param context
	 * @param editText
	 */
	public static void showKeyBoardLater(final Context context,
			final EditText editText) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				showSoftKeyBroad(context, editText);
			}
		}, 500);
	}

	/**
	 * 显示软键盘，和上面的showSoftKeyBroad方法的区别在于，如果从其他activity返回的时候需要延迟一点才能显示软键盘
	 * 
	 * @param context
	 * @param editText
	 */
	public static void showKeyBoardLater(final Context context,
			final EditText editText, long laterTime) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				showSoftKeyBroad(context, editText);
			}
		}, laterTime);
	}

	/**
	 * 获取网络连接类型
	 * 
	 * @return -1表示没有网络
	 */
	public static final int TYPE_WIFI = 0;
	public static final int TYPE_3G = 1;
	public static final int TYPE_GPRS = 2;

	public static final int getNetWorkType(Context c) {
		ConnectivityManager conn = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conn == null) {
			return -1;
		}
		NetworkInfo info = conn.getActiveNetworkInfo();
		if (info == null || !info.isAvailable()) {
			return -1;
		}

		int type = info.getType(); // MOBILE（GPRS）;WIFI
		if (type == ConnectivityManager.TYPE_WIFI) {
			return TYPE_WIFI;
		} else {
			TelephonyManager tm = (TelephonyManager) c
					.getSystemService(Context.TELEPHONY_SERVICE);
			switch (tm.getNetworkType()) {
			case TelephonyManager.NETWORK_TYPE_CDMA:
				return TYPE_GPRS;
			case TelephonyManager.NETWORK_TYPE_EDGE:
				return TYPE_GPRS;
			case TelephonyManager.NETWORK_TYPE_GPRS:
				return TYPE_GPRS;
			default:
				return TYPE_3G;
			}
		}
	}

	public static final String getConnMode(Context context) {
		ConnectivityManager conn = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conn == null) {
			return null;
		}
		NetworkInfo info = conn.getActiveNetworkInfo();
		if (info == null || !info.isAvailable()) {
			return null;
		}

		int type = info.getType(); // MOBILE（GPRS）;WIFI
		if (type == ConnectivityManager.TYPE_WIFI) {
			return APN_TYPE_WIFI;
		} else {
			return getApnType(context);
		}
	}

	private static Uri PREFERRED_APN_URI = Uri
			.parse("content://telephony/carriers/preferapn");
	public static String APN_TYPE_WIFI = "wifi";
	public static String APN_TYPE_CTNET = "ctnet";
	public static String APN_TYPE_CTWAP = "ctwap";
	public static String APN_TYPE_CMNET = "cmnet";
	public static String APN_TYPE_CMWAP = "cmwap";
	public static String APN_TYPE_UNINET = "uninet";
	public static String APN_TYPE_UNIWAP = "uniwap";

	public static String getApnType(Context context) {
		String apntype = "nomatch";
		Cursor c = null;
		try {
			c = context.getContentResolver().query(PREFERRED_APN_URI, null,
					null, null, null);
			c.moveToFirst();
			String user = c.getString(c.getColumnIndex("user")).toLowerCase();

			if (user.startsWith(APN_TYPE_CTNET)) {
				apntype = APN_TYPE_CTNET;
			} else if (user.startsWith(APN_TYPE_CTWAP)) {
				apntype = APN_TYPE_CTWAP;
			} else if (user.startsWith(APN_TYPE_CMNET)) {
				apntype = APN_TYPE_CMNET;
			} else if (user.startsWith(APN_TYPE_CMWAP)) {
				apntype = APN_TYPE_CMWAP;
			} else if (user.startsWith(APN_TYPE_UNINET)) {
				apntype = APN_TYPE_UNINET;
			} else if (user.startsWith(APN_TYPE_UNIWAP)) {
				apntype = APN_TYPE_UNIWAP;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}

		}
		return apntype;
	}

	/**
	 * 本应用是否启动
	 * 
	 * @param context
	 * @param PackageName
	 * @return
	 */
	public static boolean isRuning(Context context, String PackageName) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(100);
		for (RunningTaskInfo info : list) {
			if (info.topActivity.getPackageName().equals(PackageName)
					&& info.baseActivity.getPackageName().equals(PackageName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 本应用是否启动
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isRuning(Context context) {
		return isRuning(context, context.getPackageName());
	}

	/**
	 * 用来判断服务是否运行
	 * 
	 * @param mContext
	 * @param className
	 *            判断的服务名字
	 * @return true在运行 false 不在运行
	 */
	public static boolean isServiceRunning(Context mContext, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(Integer.MAX_VALUE);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			// UtilsLog.e("congjianfei",
			// "启动的服务有哪些呢？"+i+serviceList.get(i).service.getClassName());
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

	// /**
	// * 写文件
	// *
	// * @param file_name
	// * @param is
	// * @return
	// */
	// public static File file_put_contents(String urlStr, InputStream is) {
	// if (!checkSDCardPresent()) {
	// return null;
	// }
	// // String path = SoufunConstants.PIC_DIR + File.separator +
	// // urlStr.hashCode();
	// String path = SoufunConstants.PIC_CACHE_DIR_PATH + File.separator +
	// urlStr.hashCode();
	// File file = new File(path);
	// if (!file.getParentFile().exists()) {
	// file.getParentFile().mkdirs();
	// }
	// if (file.exists()) {
	// file.delete();
	// }
	// OutputStream outputStream = null;
	//
	// try {
	// final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	// outputStream = new BufferedOutputStream(dataStream, 4 * 1024);
	// copy(is, outputStream);
	// outputStream.flush();
	//
	// final byte[] data = dataStream.toByteArray();
	//
	// FileOutputStream fos = new FileOutputStream(file);
	// fos.write(data, 0, data.length);
	// fos.flush();
	// fos.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// if (is != null) {
	// is.close();
	// }
	// if (outputStream != null) {
	// outputStream.close();
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// return file;
	// }
	//
	public static void copy(InputStream in, OutputStream out)
			throws IOException {
		byte[] b = new byte[4 * 1024];
		int read;
		while ((read = in.read(b)) != -1) {
			out.write(b, 0, read);
		}
	}

	// /**
	// * 图片地址转化成本地uri
	// *
	// * @param url
	// * @return
	// */
	// public static Uri getUriFromUrl(final String urlStr) {
	// if (!checkSDCardPresent()) {
	// return null;
	// }
	// // String path = SoufunConstants.PIC_DIR + File.separator+
	// urlStr.hashCode();
	// String path = SoufunConstants.PIC_CACHE_DIR_PATH + File.separator+
	// urlStr.hashCode();
	//
	// File f = new File(path);
	// if (!f.exists()) {
	// new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	//
	// InputStream is = null;
	// try {
	// URL url = new URL(urlStr);
	// URLConnection conn = url.openConnection();
	// conn.connect();
	// is = conn.getInputStream();
	// file_put_contents(urlStr, is);
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// if (is != null) {
	// is.close();
	// }
	// } catch (Exception e2) {
	// }
	// }
	// }
	// }).start();
	// }
	// UtilsLog.e("url", path);
	// return Uri.fromFile(f);
	// }
	//

	/*
	 * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
	 * 
	 * @param context
	 * 
	 * @return true 表示开启
	 */
	public static final boolean isOPen(final Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
		boolean network = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (gps || network) {
			return true;
		}
		return false;
	}

	/**
	 * 强制帮用户打开GPS(需要权限)
	 * 
	 * @param context
	 */
	public static final void openGPS(Context context) {
		Intent GPSIntent = new Intent();
		GPSIntent.setClassName("com.android.settings",
				"com.android.settings.widget.SettingsAppWidgetProvider");
		GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
		GPSIntent.setData(Uri.parse("custom:3"));
		try {
			PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
		} catch (CanceledException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打开GPS设置
	 */
	public static final void setGPS(Activity activity) {
		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		try {
			activity.startActivityForResult(intent, 0);
		} catch (ActivityNotFoundException ex) {
			intent.setAction(Settings.ACTION_SETTINGS);
			try {
				activity.startActivityForResult(intent, 0);
			} catch (Exception e) {
			}
		}
	}

	

}