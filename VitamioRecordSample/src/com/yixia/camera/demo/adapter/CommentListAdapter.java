package com.yixia.camera.demo.adapter;

import java.util.LinkedList;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yixia.camera.demo.R;
import com.yixia.camera.demo.bean.CommentData;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentListAdapter extends BaseAdapter {

	private LinkedList<CommentData> data;
	private Activity activity;

	public CommentListAdapter(Activity activity, LinkedList<CommentData> data) {
		this.activity = activity;
		this.data = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.item_detail_comment, parent, false);

			viewHolder.userNameView = (TextView) convertView
					.findViewById(R.id.txtUserName);
			viewHolder.timeView = (TextView) convertView
					.findViewById(R.id.txtTime);
			viewHolder.commentView = (TextView) convertView
					.findViewById(R.id.txtcommentBody);
			viewHolder.personView = (ImageView) convertView
					.findViewById(R.id.imgPeople);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		CommentData comment = data.get(position);
		String name = comment.username;
		if (TextUtils.isEmpty(name)) {
			name = "匿名";
		}
		viewHolder.userNameView.setText(name);
		viewHolder.timeView.setText(comment.pubtime);
		viewHolder.commentView.setText(comment.content);

		ImageLoader.getInstance().displayImage(
				(String) data.get(position).peopleUrl, viewHolder.personView);

		return convertView;
	}

	private class ViewHolder {
		TextView userNameView;
		TextView timeView;
		TextView commentView;
		ImageView personView;
	}

}
