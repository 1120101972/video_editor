package com.yixia.camera.demo.bean;

import java.io.Serializable;

public class VideoDetailBean implements Serializable {

	private String title;
	private String brif;
	private String url;
	private String id;
	private int like;
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

	public String geturl() {
		return url;
	}

	public void seturl(String parameter) {
		url = parameter;
	}

	public String getid() {
		return id;
	}

	public void setid(String parameter) {
		id = parameter;
	}

	public int getlike() {
		return like;
	}

	public void setlike(int parameter) {
		like = parameter;
	}

}
