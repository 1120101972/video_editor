package adqr_util;

import java.io.Serializable;

/**
 * 记录信息
 * 
 * @author wufangxue
 * 
 */
public class ContentData implements Serializable {
	/**
		 *
		 */
	private static final long serialVersionUID = 2L;
	public int address_id; // 地址的id
	public String photoTime; // 拍摄的时间点
	public String userNumber; // 用户名
	public String advert_number; // 广告的编号

	public int ifget;// 用来判断这个算过没有

	public ContentData() {

	}

	public void setIfGet(int _ifget) {
		ifget = _ifget;
	}

	public void setAddressId(int address) {
		address_id = address;
	}

	public void setPhotoTime(String _photoTime) {
		photoTime = _photoTime;
	}

	public void setUserNumber(String _userNumber) {
		userNumber = _userNumber;
	}

	public void setadvert_number(String _advert_number) {
		advert_number = _advert_number;
	}

	public int getIfGet() {
		return ifget;
	}

	public int getAddressId() {
		return address_id;
	}

	public String getPhotoTime() {
		return photoTime;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public String getadvert_number() {
		return advert_number;
	}
}
