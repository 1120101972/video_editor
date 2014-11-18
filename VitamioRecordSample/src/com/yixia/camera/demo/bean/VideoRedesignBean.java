package com.yixia.camera.demo.bean;

import java.io.Serializable;

public class VideoRedesignBean implements Serializable {

	private String title;
	private String brif;
	private String video_url;
	/** 字幕地址 **/
	private String subtitle_url;
	private String id;
	/** 改编次数 **/
	private int changedTime;
	/** 时长 **/
	private String longTime;
	
	private String image_url;
	private static final long serialVersionUID = 2L;

	public String getTitle() {
		return title;
	}

	public void setTitle(String parameter) {
		title = parameter;
	}

	public String getbrif() {
		return brif;
	}

	public void setbrif(String parameter) {
		brif = parameter;
	}

	public String getvideo_url() {
		return video_url;
	}

	public void setvideo_url(String parameter) {
		video_url = parameter;
	}

	public String getimage_url() {
		return image_url;
	}

	public void setimage_url(String parameter) {
		image_url = parameter;
	}

	public String getsubtitle_url() {
		return subtitle_url;
	}

	public void setsubtitle_url(String parameter) {
		subtitle_url = parameter;
	}

	public String getLong_time() {
		return longTime;
	}

	public void setlongTime(String parameter) {
		longTime = parameter;
	}

	public String getid() {
		return id;
	}

	public void setid(String parameter) {
		id = parameter;
	}

	public int getchangeTime() {
		return changedTime;
	}

	public void setchangeTime(int parameter) {
		changedTime = parameter;
	}

}
