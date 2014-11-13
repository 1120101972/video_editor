package com.yixia.camera.demo.utils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.content.Context;
import android.text.Spannable;
import android.text.style.URLSpan;
import android.widget.TextView;

/**
 * 处理字符串工具类
 * 
 * @author
 * 
 */
public class StringUtils {

	/**
	 * 判断是否为空
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isNullOrEmpty(String text) {
		if (text == null || "".equals(text.trim()) || text.trim().length() == 0
				|| "null".equals(text.trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获得MD5加密字符串
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	/**
	 * 得到字符串长度
	 * 
	 * @param text
	 * @return
	 */
	public static int getCharCount(String text) {
		String Reg = "^[\u4e00-\u9fa5]{1}$";
		int result = 0;
		for (int i = 0; i < text.length(); i++) {
			String b = Character.toString(text.charAt(i));
			if (b.matches(Reg))
				result += 2;
			else
				result++;
		}
		return result;
	}

	/**
	 * 获取截取后的字符串
	 * 
	 * @param text
	 *            原字符串
	 * @param length
	 *            截取长度
	 * @return
	 */
	public static String getSubString(String text, int length) {
		return getSubString(text, length, true);
	}

	/**
	 * 获取截取后的字符串
	 * 
	 * @param text
	 *            原字符串
	 * @param length
	 *            截取长度
	 * @param isOmit
	 *            是否加上省略号
	 * @return
	 */
	public static String getSubString(String text, int length, boolean isOmit) {
		if (isNullOrEmpty(text)) {
			return "";
		}
		if (getCharCount(text) <= length + 1) {
			return text;
		}

		StringBuffer sb = new StringBuffer();
		String Reg = "^[\u4e00-\u9fa5]{1}$";
		int result = 0;
		for (int i = 0; i < text.length(); i++) {
			String b = Character.toString(text.charAt(i));
			if (b.matches(Reg)) {
				result += 2;
			} else {
				result++;
			}

			if (result <= length + 1) {
				sb.append(b);
			} else {
				if (isOmit) {
					sb.append("...");
				}
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * 电话号码验证
	 * 
	 * @param phoneNumber
	 *            手机号码
	 * @return
	 */
	public static boolean validatePhoneNumber(String phoneNumber) {
		Pattern pattern = Pattern
				.compile("^((13[0-9])|(14[7])|(15[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = pattern.matcher(phoneNumber);
		return m.matches();
	}

	/**
	 * 邮箱验证
	 * 
	 * @param mail
	 *            邮箱
	 * @return
	 */
	public static boolean validateEmail(String mail) {
		Pattern pattern = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher m = pattern.matcher(mail);
		return m.matches();
	}

	/**
	 * 验证字符串内容是否合法
	 * 
	 * @param content
	 *            字符串内容
	 * @return
	 */
	public static boolean validateLegalString(String content) {
		String illegal = "`~!#%^&*=+\\|{};:'\",<>/?○●★☆☉♀♂※¤╬の〆";
		boolean legal = true;
		L1: for (int i = 0; i < content.length(); i++) {
			for (int j = 0; j < illegal.length(); j++) {
				if (content.charAt(i) == illegal.charAt(j)) {
					legal = false;
					break L1;
				}
			}
		}
		return legal;
	}

	/**
	 * 获取更新的时间
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public static String getCreateString(String dateStr) {
		if (dateStr != null && !"".equals(dateStr)) {
			try {
				if (dateStr.indexOf(".") > -1) {
					dateStr = dateStr.substring(0, dateStr.indexOf("."));
				}
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date date = sdf.parse(dateStr);
				Calendar calendar = Calendar.getInstance();

				int oneMinuteUnit = 60;
				int oneHourUnit = 60 * 60;
				int oneDayUnit = 60 * 60 * 24;
				long i = (calendar.getTimeInMillis() - date.getTime()) / 1000;
				if (i < oneMinuteUnit && i > 0) {
					return i + "秒前";
				} else if (i < oneHourUnit && i > oneMinuteUnit) {
					return i / 60 + "分钟前";
				} else if (i < oneDayUnit && i > oneHourUnit) {
					return (i / (60 * 60)) + "小时前";
				} else {
					return dateStr;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取更新的时间
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public static String getCreateString(Date date) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (calendar.get(Calendar.YEAR) - (date.getYear() + 1900) > 0) {
			return sdf.format(date);
		} else if (calendar.get(Calendar.MONTH) - date.getMonth() > 0) {
			return sdf.format(date);
		} else if (calendar.get(Calendar.DAY_OF_MONTH) - date.getDate() > 0) {
			return sdf.format(date);
		} else if (calendar.get(Calendar.HOUR_OF_DAY) - date.getHours() > 0) {
			int i = calendar.get(Calendar.HOUR_OF_DAY) - date.getHours();
			return i + "小时前";
		} else if (calendar.get(Calendar.MINUTE) - date.getMinutes() > 0) {
			int i = calendar.get(Calendar.MINUTE) - date.getMinutes();
			return i + "分钟前";
		} else if (calendar.get(Calendar.SECOND) - date.getSeconds() > 0) {
			int i = calendar.get(Calendar.SECOND) - date.getSeconds();
			return i + "秒前";
		} else {
			return sdf.format(date);
		}
	}

	/**
	 * 获取更新的时间
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public static String getDateString(Date date) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (calendar.get(Calendar.YEAR) - (date.getYear() + 1900) > 0) {
			return sdf.format(date);
		} else if (calendar.get(Calendar.MONTH) - date.getMonth() > 0) {
			return sdf.format(date);
		} else if (calendar.get(Calendar.DAY_OF_MONTH) - date.getDate() > 6) {
			return sdf.format(date);
		} else if ((calendar.get(Calendar.DAY_OF_MONTH) - date.getDate() > 0)
				&& (calendar.get(Calendar.DAY_OF_MONTH) - date.getDate() < 6)) {
			int i = calendar.get(Calendar.DAY_OF_MONTH) - date.getDate();
			return i + "天前";
		} else if (calendar.get(Calendar.HOUR_OF_DAY) - date.getHours() > 0) {
			int i = calendar.get(Calendar.HOUR_OF_DAY) - date.getHours();
			return i + "小时前";
		} else if (calendar.get(Calendar.MINUTE) - date.getMinutes() > 0) {
			int i = calendar.get(Calendar.MINUTE) - date.getMinutes();
			return i + "分钟前";
		} else if (calendar.get(Calendar.SECOND) - date.getSeconds() > 0) {
			int i = calendar.get(Calendar.SECOND) - date.getSeconds();
			return i + "秒前";
		} else if (calendar.get(Calendar.SECOND) - date.getSeconds() == 0) {
			return "刚刚";
		} else {
			return sdf.format(date);
		}
	}

	/**
	 * 获取更新的时间
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public static String getCreateString_XF(Date date) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		if (calendar.get(Calendar.YEAR) - (date.getYear() + 1900) > 0) {
			return sdf.format(date);
		} else if (calendar.get(Calendar.MONTH) - date.getMonth() > 0) {
			return sdf.format(date);
		} else if (calendar.get(Calendar.DAY_OF_MONTH) - date.getDate() > 0) {
			return sdf.format(date);
		} else if (calendar.get(Calendar.HOUR_OF_DAY) - date.getHours() > 0) {
			int i = calendar.get(Calendar.HOUR_OF_DAY) - date.getHours();
			return i + "小时前";
		} else if (calendar.get(Calendar.MINUTE) - date.getMinutes() > 0) {
			int i = calendar.get(Calendar.MINUTE) - date.getMinutes();
			return i + "分钟前";
		} else if (calendar.get(Calendar.SECOND) - date.getSeconds() > 0) {
			int i = calendar.get(Calendar.SECOND) - date.getSeconds();
			return i + "秒前";
		} else {
			return sdf.format(date);
		}
	}
	/**
	 * 获取更新的时间
	 * @param date
	 * @return
	 */
	public static String getCreateString_PG(Date date) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (calendar.get(Calendar.YEAR) - (date.getYear() + 1900) > 0) {
			return sdf.format(date);
		} else if (calendar.get(Calendar.MONTH) - date.getMonth() > 0
				&& calendar.get(Calendar.MONTH) - date.getMonth() < 12) {
			int i = calendar.get(Calendar.MONTH) - date.getDate();
			return i + "个月前";
		} else if ((calendar.get(Calendar.DAY_OF_MONTH) - date.getDate() > 0)
				&& (calendar.get(Calendar.DAY_OF_MONTH) - date.getDate() < 30)) {
			int i = calendar.get(Calendar.DAY_OF_MONTH) - date.getDate();
			return i + "天前";
		} else if (calendar.get(Calendar.HOUR_OF_DAY) - date.getHours() > 0) {
			int i = calendar.get(Calendar.HOUR_OF_DAY) - date.getHours();
			return i + "小时前";
		} else if (calendar.get(Calendar.MINUTE) - date.getMinutes() > 0) {
			int i = calendar.get(Calendar.MINUTE) - date.getMinutes();
			return i + "分钟前";
		} else if (calendar.get(Calendar.SECOND) - date.getSeconds() > 0) {
			int i = calendar.get(Calendar.SECOND) - date.getSeconds();
			return i + "秒前";
		} else if (calendar.get(Calendar.SECOND) - date.getSeconds() == 0) {
			return "刚刚";
		} else {
			return sdf.format(date);
		}
	}

	
	/**
	 * 对聊天的图片进行动态调整
	 */
	public static String getChatImgUrl(String url, int width, int height) {
		int temp = url.indexOf("?");
		if (temp > -1) {
			String[] urls = url.split("\\?");
			if (urls.length > 1) {
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append(urls[0]);
				stringBuffer.append("?");
				stringBuffer.append("px=");
				stringBuffer.append(width);
				stringBuffer.append("x");
				stringBuffer.append(height);
				stringBuffer.append("&");
				stringBuffer.append(urls[1]);
				return stringBuffer.toString();
			}
		}
		return null;
	}

	/**
	 * 如果为空显示暂无信息
	 * 
	 * @param tv
	 *            控件名
	 * @param str
	 *            信息
	 */
	public static void viewText(TextView tv, String str) {
		if (isNullOrEmpty(str)) {
			tv.setText("暂无资料");
		} else {
			tv.setText(str);
		}
	}

	/**
	 * 对流转化成字符串
	 * 
	 * @param is
	 * @return
	 */
	public static String getContentByString(InputStream is) {
		try {
			if (is == null)
				return null;
			byte[] b = new byte[1024];
			int len = -1;
			StringBuilder sb = new StringBuilder();
			while ((len = is.read(b)) != -1) {
				sb.append(new String(b, 0, len));
			}
			return sb.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 对流转化成字符串
	 * 
	 * @param is
	 * @return
	 */
	public static String getStringByStream(InputStream is) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line + "\n");
			}
			return buffer.toString().replaceAll("\n\n", "\n");
		} catch (OutOfMemoryError o) {
			System.gc();
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 截取字符串，去掉sign后边的
	 * 
	 * @param source
	 *            原始字符串
	 * @param sign
	 * @return
	 */
	public static String splitByIndex(String source, String sign) {
		String temp = "";
		if (isNullOrEmpty(source)) {
			return temp;
		}
		int length = source.indexOf(sign);
		if (length > -1) {
			temp = source.substring(0, length);
		} else {
			return source;
		}
		return temp;
	}

	/**
	 * 截取字符串，返回sign分隔的字符串
	 * 
	 */
	public static String splitNumAndStr(String res, String sign) {
		StringBuffer buffer;
		String reg = "\\d+";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(res);
		if (m.find()) {
			buffer = new StringBuffer();
			String s = m.group();
			buffer.append(s);
			buffer.append(sign);
			buffer.append(res.replace(s, ""));
			return buffer.toString();
		}
		return null;
	}

	/**
	 * 保留小数点后一位
	 * 
	 * @param d
	 * @return
	 * @throws Exception
	 */
	public static String formatNumber(double d) {
		try {
			DecimalFormat df = new DecimalFormat("#,##0.0");
			return df.format(d);
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * 保留小数点后两位
	 * 
	 * @param d
	 * @return
	 * @throws Exception
	 */
	public static String formatNumber2(double d) {
		try {
			DecimalFormat df = new DecimalFormat("0.00");
			return df.format(d);
		} catch (Exception e) {
		}
		return "";
	}

	public static String formatNumber(String d) {
		return formatNumber(Double.parseDouble(d));
	}

	/**
	 * 把对象放进map里
	 * 
	 * @param o
	 *            实体
	 */
	public static Map<String, String> getMapForEntry(Object o) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Field[] fields = o.getClass().getFields();
			for (Field f : fields) {
				String key = f.getName();
				try {
					String value = (String) f.get(o);
					if (StringUtils.isNullOrEmpty(value)
							|| value.indexOf("不限") > -1) {
						continue;
					}
					map.put(key, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
		}
		return map;
	}

	/**
	 * map 转化为实体
	 * 
	 * @param <T>
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static <T> T setMapForEntry(Map<String, String> map, Class<T> clazz) {
		T t = null;
		try {
			t = clazz.newInstance();
			for (Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey();
				Field field = t.getClass().getField(key);
				field.set(t, entry.getValue());
			}
		} catch (Exception e) {
		}
		return t;
	}

	/**
	 * 实体转化
	 * 
	 * @param o
	 * @return
	 */
	public static <T> T convertEntry(Object o) {
		T t = null;
		try {
			t = (T) o.getClass().newInstance();
			Field[] fields = o.getClass().getFields();
			for (Field f : fields) {
				try {
					String value = (String) f.get(o);
					if (StringUtils.isNullOrEmpty(value)
							|| value.indexOf("不限") > -1) {
						f.set(t, "");
					} else {
						f.set(t, value);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
		}
		return t;
	}

	/*
	 * 获取字符串格式的当前时间
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/*
	 * 时间格式转换，yyyy-MM-dd xx:xx:xx为：yyyy-MM-dd
	 */
	public static String getStringDate(String date) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		try {
			d = formatter.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dateString = formatter.format(d);
		return dateString;

	}

	/*
	 * 时间格式转换，yyyy-MM-dd xx:xx:xx为：MM-dd xx：xx
	 */
	public static String getStringDateNew(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
		Calendar calendar = Calendar.getInstance();
		// formatter.setCalendar(calendar);

		// Log.d("congjianfei", date);
		Date d = new Date();
		try {
			d = formatter.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		calendar.setTime(d);
		// return formatter.format(d);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(calendar.get(Calendar.MONTH) + 1 < 10 ? "0"
				+ (calendar.get(Calendar.MONTH) + 1) : calendar
				.get(Calendar.MONTH) + 1);
		stringBuilder.append("-");
		stringBuilder.append(calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0"
				+ calendar.get(Calendar.DAY_OF_MONTH) : calendar
				.get(Calendar.DAY_OF_MONTH));
		stringBuilder.append(" ");
		stringBuilder.append(calendar.get(Calendar.HOUR_OF_DAY) < 10 ? "0"
				+ calendar.get(Calendar.HOUR_OF_DAY) : calendar
				.get(Calendar.HOUR_OF_DAY));
		stringBuilder.append(":");
		stringBuilder
				.append(calendar.get(Calendar.MINUTE) < 10 ? "0"
						+ calendar.get(Calendar.MINUTE) : calendar
						.get(Calendar.MINUTE));
		return stringBuilder.toString();
	}

	/*
	 * 截取sign后边的数字
	 */
	public static String getStringNum(String str, String sign) {
		String reg = ":split:";
		return str.replace(sign, reg).replaceAll(reg + "\\d+", "")
				.replaceAll(" ", "").trim();

	}

	public static String getRegText(String xml, String tag) {
		Pattern pattern = Pattern.compile("<" + tag + ">(.*?)</" + tag + ">",
				Pattern.UNICODE_CASE | Pattern.DOTALL);
		Matcher m = pattern.matcher(xml);
		if (m.find()) {
			xml = m.group(1);
			xml = xml.replace("<![CDATA[", "").replace("<![cdata[", "")
					.replace("]]>", "");
			return xml;
		} else {
			return null;
		}
	}

	/*
	 * 时间格式转换，yyyy-MM-dd xx:xx:xx为：MM-dd xx:xx 不要年和秒
	 */
	public static String getStringForDate(String date) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat f = new SimpleDateFormat("MM-dd HH:mm");
		Date d = new Date();
		try {
			d = formatter.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dateString = f.format(d);
		return dateString;

	}

	/*
	 * 时间格式转换，yyyy-MM-dd xx:xx:xx为：MM-dd xx:xx 不要年和秒
	 */
	public static String getStringForDate_new(String date) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		SimpleDateFormat f = new SimpleDateFormat("MM-dd HH:mm");
		Date d = new Date();
		try {
			d = formatter.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String dateString = f.format(d);
		return dateString;

	}

	/*
	 * 时间格式转换，yyyy-MM-dd xx:xx:xx为：MM-dd xx:xx 不要年和秒
	 */
	public static String getStringForDate(long date) {

		SimpleDateFormat f = new SimpleDateFormat("MM-dd HH:mm");
		Date d = new Date(date);
		String dateString = f.format(d);
		return dateString;

	}

	/*
	 * 判断价格是否为0或空
	 */
	public static boolean isPriceZero(String price) {
		if (isNullOrEmpty(price)) {
			return true;
		}
		price = splitByIndex(price, ".");
		if ("0".equals(price)) {
			return true;
		}
		return false;

	}

	/**
	 * 取价格的整数，去掉单位
	 * 
	 * @param price
	 * @return
	 */
	public static String getPrice(String price) {
		Pattern p = Pattern.compile("^\\d+");
		Matcher m = p.matcher(price);
		if (m.find()) {
			return m.group();
		}
		return "";
	}

	/**
	 * 判断是否全为数字
	 * 
	 * @param content
	 * @return
	 */
	public static boolean isAllNumber(String content) {
		boolean isAllNumber = true;
		if (isNullOrEmpty(content)) {
			return false;
		}
		for (int i = 0; i < content.length(); i++) {
			if (content.charAt(i) < '0' || content.charAt(i) > '9') {
				isAllNumber = false;
			}
		}
		return isAllNumber;
	}

	/**
	 * 整数转字节数组
	 * 
	 * @param i
	 * @return
	 */
	public static byte[] intToByte(int i) {
		byte[] bt = new byte[4];
		bt[0] = (byte) (0xff & i);
		bt[1] = (byte) ((0xff00 & i) >> 8);
		bt[2] = (byte) ((0xff0000 & i) >> 16);
		bt[3] = (byte) ((0xff000000 & i) >> 24);
		return bt;
	}

	/**
	 * 字节数组转整数
	 * 
	 * @param bytes
	 * @return
	 */
	public static int bytesToInt(byte[] bytes) {
		int num = bytes[0] & 0xFF;
		num |= ((bytes[1] << 8) & 0xFF00);
		num |= ((bytes[2] << 16) & 0xFF0000);
		num |= ((bytes[3] << 24) & 0xFF000000);
		return num;
	}



	/**
	 * 获取字符串中的数字
	 * 
	 * @return
	 */
	public static String getPriceNum(String price) {
		String regEx = "[^0-9.]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(price);
		String pricetype = m.replaceAll("").trim();

		return pricetype;
	}

	/**
	 * 按字节截取字符串
	 * 
	 * @param orignal
	 *            原始字符串
	 * @param count
	 *            截取位数
	 * @return 截取后的字符串
	 * @throws UnsupportedEncodingException
	 *             使用了JAVA不支持的编码格式
	 */
	public static String substring(String orignal, int count)
			throws UnsupportedEncodingException {
		// 原始字符不为null，也不是空字符串
		if (null != orignal && !"".equals(orignal)) {
			// 将原始字符串转换为GBK编码格式
			String orignal_byte = new String(orignal.getBytes("UTF-8"), "UTF-8");
			if (count > 0 && count < orignal.getBytes("UTF-8").length) {
				StringBuffer buff = new StringBuffer();
				char c;
				String s = "";
				int num = 0;
				for (int i = 0; i < count; i++) {
					// charAt(int index)也是按照字符来分解字符串的
					if (orignal_byte.length() > i) {
						c = orignal_byte.charAt(i);
						buff.append(c);
						if (isChineseChar(c)) {// 遇到中文汉字，字节总数+2
							num += 2; // 一般汉字在utf-8中为3个字节长度
						} else {
							num += 1;
						}
						if (num == count) {
							s = buff.toString() + "...";
							continue;
						} else if (num > count) {
							if (num == 15) {
								return buff.toString() + "...";
							} else {
								return s;
							}
						}
					}

				}
				return buff.toString();
			}
			// 要截取的字节数大于0，且小于原始字符串的字节数
		}
		return orignal;
	}

	public static boolean isChineseChar(char c) {
		// 如果字节数大于1，是汉字
		try {
			return String.valueOf(c).getBytes("UTF-8").length > 1;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

		/**
	 * 给定一个秒数，转化为00:00:00格式的时间
	 * 
	 * @param time
	 * @return
	 */
	public static String formateTime(int time) {
		int mHour, mMinute, mSecond;// 时分秒
		StringBuilder sb_time = new StringBuilder();
		mHour = time / 3600;
		if (mHour > 9)
			sb_time.append(mHour + ":");
		else if (mHour >= 0) {
			sb_time.append("0" + mHour + ":");
		} else {
			sb_time.append("00:");
		}
		time = time % 3600;
		mMinute = time / 60;
		if (mMinute > 9) {
			sb_time.append(mMinute + ":");
		} else if (mMinute >= 0) {
			sb_time.append("0" + mMinute + ":");
		} else {
			sb_time.append("00:");
		}
		mSecond = time % 60;
		if (mSecond > 9) {
			sb_time.append(mSecond);
		} else if (mSecond >= 0) {
			sb_time.append("0" + mSecond);
		}
		return sb_time.toString();
	}

	

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 返回汉字个数
	 * 
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public static int getChineseCount(String s) {// 获得汉字的长度
		char c;
		int chineseCount = 0;
		if (!"".equals("")) {// 判断是否为空
			try {
				s = new String(s.getBytes(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 进行统一编码
		}
		for (int i = 0; i < s.length(); i++) {// for循环
			c = s.charAt(i); // 获得字符串中的每个字符
			if (isChineseChar(c)) {// 调用方法进行判断是否是汉字
				chineseCount++; // 等同于chineseCount=chineseCount+1
			}
		}
		return chineseCount; // 返回汉字个数
	}

	

	

	/**
	 * 获取保留3位小数的当前时间
	 * */
	public static String getStringDateFor3Decimal() {
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.sss");
		Date date = new Date();
		return format.format(date);
	}
	/**
	 * 聊天去除l:
	 * @param name
	 * @return
	 */
	public static String getChatNameString(String name){
		String chatname = name;
		if(chatname!=null&&chatname.contains(":")){
            try{
			    return chatname.substring(chatname.indexOf(":")+1);
            }catch (Exception e){
                e.printStackTrace();
            }
		}
		return chatname;
	}
}
