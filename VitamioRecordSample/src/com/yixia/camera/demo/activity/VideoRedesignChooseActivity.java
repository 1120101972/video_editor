package com.yixia.camera.demo.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.yixia.camera.demo.R;
import com.yixia.camera.demo.VCameraDemoBaseActivity;
import com.yixia.camera.demo.adapter.ChooseRedesignVedioAdapter;
import com.yixia.camera.demo.bean.VideoRedesignBean;
import com.yixia.camera.demo.ui.record.MediaRecorderActivity;

public class VideoRedesignChooseActivity extends VCameraDemoBaseActivity {

	private ListView lv_choose;
	private ChooseRedesignVedioAdapter adapter;
	private ArrayList<VideoRedesignBean> chooseDatas = new ArrayList<VideoRedesignBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setView(R.layout.activity_listview, LAYOUT_TYPE_HEADER);
		baseLayout.setHeaderBarStyle("改编曲目", 0, 0);
		initView();
		initData();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		lv_choose = (ListView) findViewById(R.id.lv_list);

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 5; i++) {
			VideoRedesignBean bean = new VideoRedesignBean();
			bean.setimage_url("http://img.tupianzj.com/uploads/allimg/141113/3-141113114414.jpg");
			bean.setTitle("爸爸去哪兒");
			bean.setlongTime("22:10");
			bean.setchangeTime(5);
			chooseDatas.add(bean);
		}
		adapter = new ChooseRedesignVedioAdapter(
				VideoRedesignChooseActivity.this, chooseDatas);
		lv_choose.setAdapter(adapter);
		lv_choose.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				startActivityForAnima(new Intent().setClass(mContext,
						VideoEditorActivity.class));

			}
		});

	}

}
