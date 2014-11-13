package com.yixia.camera.demo.bean;

import java.io.Serializable;

/*
 * 评论列表数据类
 */
public class CommentData implements Serializable {
	/**
		 *
		 */
	private static final long serialVersionUID = 2L;
	public String username; // 用户名
	public String content; // 评论内容
	public String pubtime; // 发布日期
	public String peopleUrl = "";

	public long id; // 评论id

	public CommentData(String _username, String _content, String _pubtime,
			String _url, long _id) {
		username = _username;
		content = _content;
		pubtime = _pubtime;
		id = _id;
		peopleUrl = _url;

	}
}
