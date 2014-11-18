package com.yixia.camera.demo.adapter;

import java.util.ArrayList;
import java.util.LinkedList;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yixia.camera.demo.R;
import com.yixia.camera.demo.bean.CommentData;
import com.yixia.camera.demo.bean.VideoRedesignBean;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChooseRedesignVedioAdapter extends BaseAdapter {

	private ArrayList<VideoRedesignBean> data;
	private Activity activity;

	public ChooseRedesignVedioAdapter(Activity activity,
			ArrayList<VideoRedesignBean> data) {
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
					R.layout.item_choose_video, parent, false);

			viewHolder.title = (TextView) convertView
					.findViewById(R.id.tv_videoTitle);
			viewHolder.timeView = (TextView) convertView
					.findViewById(R.id.tv_time);
			viewHolder.chooseTime = (TextView) convertView
					.findViewById(R.id.tv_changeNumber);
			viewHolder.personView = (ImageView) convertView
					.findViewById(R.id.iv_recode);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		VideoRedesignBean design = data.get(position);

		viewHolder.title.setText(design.getTitle());
		viewHolder.timeView.setText(design.getLong_time());
		viewHolder.chooseTime.setText(design.getchangeTime());

		ImageLoader.getInstance().displayImage(
				(String) data.get(position).getimage_url(),
				viewHolder.personView);

		return convertView;
	}

	private class ViewHolder {
		TextView title;
		TextView timeView;
		TextView chooseTime;
		ImageView personView;
	}

}
