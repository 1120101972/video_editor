package com.yixia.camera.demo.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yixia.camera.demo.R;
import com.yixia.camera.demo.VCameraDemoBaseActivity;
import com.yixia.camera.demo.adapter.ChooseRedesignVedioAdapter;
import com.yixia.camera.demo.bean.VideoRedesignBean;
import com.yixia.camera.demo.ui.record.MediaRecorderActivity;

public class VideoRedesignChooseActivity extends VCameraDemoBaseActivity {

	private PullToRefreshListView pullToRefreshListView;
	private ChooseRedesignVedioAdapter adapter;
	private ArrayList<VideoRedesignBean> chooseDatas = new ArrayList<VideoRedesignBean>();

	private boolean mIsRefresh = false;

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
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_list);
		pullToRefreshListView.setShowIndicator(false);
		pullToRefreshListView
				.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.BOTH);
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {

				try {
					doRefresh();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				try {
					onMore();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

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
		pullToRefreshListView.setAdapter(adapter);
		pullToRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				startActivityForAnima(new Intent().setClass(mContext,
						VideoEditorActivity.class));

			}
		});

	}

	private static Handler handler = new Handler();

	private void doRefresh() throws UnsupportedEncodingException {
		if (!mIsRefresh) {
			// 此时标记为下拉开始
			mIsRefresh = true;
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					// 通过版本号来判断是否是第一次安装
					handler.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							pullToRefreshListView.onRefreshComplete();
							mIsRefresh = false;
						}
					});

				}
			}, 1000);

		} else {
			return;
		}
	}

	private void onMore() throws UnsupportedEncodingException {
		if (!mIsRefresh) {
			// 此时标记为下拉开始
			mIsRefresh = true;
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					// 通过版本号来判断是否是第一次安装
					handler.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							pullToRefreshListView.onRefreshComplete();
							mIsRefresh = false;
						}
					});
				}
			}, 1000);

		} else {
			return;
		}

	}
}
