package com.yixia.camera.demo.adapter;

import java.util.ArrayList;
import java.util.LinkedList;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yixia.camera.demo.R;
import com.yixia.camera.demo.bean.CommentData;
import com.yixia.camera.demo.bean.PersonalWithCommentData;
import com.yixia.camera.demo.bean.VideoDetailBean;
import com.yixia.camera.demo.bean.VideoRedesignBean;
import com.yixia.camera.demo.ui.widget.MyListView;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PersonalWithCommentAdapter extends BaseAdapter {

	private ArrayList<PersonalWithCommentData> data;
	private Activity activity;

	public PersonalWithCommentAdapter(Activity activity,
			ArrayList<PersonalWithCommentData> data) {
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
					R.layout.item_personal_withcomment, parent, false);

			viewHolder.title = (TextView) convertView
					.findViewById(R.id.tv_title);

			viewHolder.describle = (TextView) convertView
					.findViewById(R.id.tv_content);
			viewHolder.videoView = (ImageView) convertView
					.findViewById(R.id.iv_video);

			viewHolder.lv_comment = (MyListView) convertView
					.findViewById(R.id.lv_list);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		PersonalWithCommentData design = data.get(position);

		viewHolder.title.setText(design.getTitle());
		viewHolder.describle.setText(design.getContent());

		CommentListAdapter adapter = new CommentListAdapter(activity, data.get(
				position).getcommentDatas());
		viewHolder.lv_comment.setAdapter(adapter);

		// ImageLoader.getInstance().displayImage(
		// (String) data.get(position).getowner_url(),
		// viewHolder.personView);
		ImageLoader.getInstance()
				.displayImage((String) data.get(position).getVideoUrl(),
						viewHolder.videoView);

		return convertView;
	}

	private class ViewHolder {
		TextView title;
		TextView describle;
		ImageView videoView;
		MyListView lv_comment;

	}

}
