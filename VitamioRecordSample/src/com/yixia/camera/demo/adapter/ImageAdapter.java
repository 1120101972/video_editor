package com.yixia.camera.demo.adapter;

import java.util.ArrayList;
import java.util.LinkedList;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yixia.camera.demo.R;
import com.yixia.camera.demo.bean.CommentData;
import com.yixia.camera.demo.bean.VideoDetailBean;
import com.yixia.camera.demo.bean.VideoRedesignBean;
import com.yixia.camera.demo.utils.StringUtils;

import android.app.Activity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {

	private ArrayList<VideoDetailBean> data;
	private Activity activity;

	public ImageAdapter(Activity activity, ArrayList<VideoDetailBean> data) {
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
		//if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.item_main_gridview, parent, false);

			viewHolder.videoView = (ImageView) convertView
					.findViewById(R.id.iv_main);
			viewHolder.backView = (ImageView) convertView
					.findViewById(R.id.iv_back);

			DisplayMetrics dm = new DisplayMetrics();
			activity.getWindow().getWindowManager().getDefaultDisplay()
					.getMetrics(dm);
			int width = dm.widthPixels;
			int height = dm.heightPixels;

			AbsListView.LayoutParams params = new AbsListView.LayoutParams(
					(width - StringUtils.dip2px(activity, 30)) / 2,
					((width - StringUtils.dip2px(activity, 30)) / 2) * 5 / 7);
			convertView.setLayoutParams(params);

		// convertView.setTag(viewHolder);
		// } else {
		// viewHolder = (ViewHolder) convertView.getTag();
		// }

		VideoDetailBean design = data.get(position);

		// ImageLoader.getInstance().displayImage(
		// (String) data.get(position).getowner_url(),
		// viewHolder.personView);
		viewHolder.videoView.setAnimation(AnimationUtils.loadAnimation(activity,
				R.anim.guide_welcome_fade_in));
		ImageLoader.getInstance().displayImage(
				(String) data.get(position).geturl(), viewHolder.videoView);

		return convertView;  
	}

	private class ViewHolder {

		ImageView videoView;
		ImageView backView;

	}

}
