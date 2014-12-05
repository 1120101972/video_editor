package com.yixia.camera.demo.bean;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * 评论列表数据类
 */
public class PersonalWithCommentData implements Serializable {
	/**
		 *
		 */
	private static final long serialVersionUID = 2L;
	private String title;
	private String content;
	private String videoUrl = "";
	private ArrayList<CommentData> commentDatas = new ArrayList<CommentData>();
	private String id; //

	public PersonalWithCommentData() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String parameter) {
		title = parameter;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String parameter) {
		content = parameter;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String parameter) {
		videoUrl = parameter;
	}

	public String getid() {
		return id;
	}

	public void setid(String parameter) {
		id = parameter;
	}

	public ArrayList<CommentData> getcommentDatas() {
		return commentDatas;
	}

	public void setcommentDatas(ArrayList<CommentData> parameter) {
		commentDatas = parameter;
	}
}
