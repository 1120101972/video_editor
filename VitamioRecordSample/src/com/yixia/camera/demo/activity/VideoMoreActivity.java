package com.yixia.camera.demo.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yixia.camera.demo.R;
import com.yixia.camera.demo.VCameraDemoBaseActivity;
import com.yixia.camera.demo.adapter.ChooseRedesignVedioAdapter;
import com.yixia.camera.demo.adapter.MoreVedioAdapter;
import com.yixia.camera.demo.bean.VideoDetailBean;
import com.yixia.camera.demo.bean.VideoRedesignBean;
import com.yixia.camera.demo.ui.record.MediaRecorderActivity;

public class VideoMoreActivity extends VCameraDemoBaseActivity {

	private PullToRefreshListView pullToRefreshListView;
	private RelativeLayout rl_sort;
	private TextView tv_latest, tv_hottest;

	private MoreVedioAdapter adapter;
	private ArrayList<VideoDetailBean> chooseDatas = new ArrayList<VideoDetailBean>();
	Drawable rightDrawable;

	private boolean mIsRefresh = false;
	private boolean mIsLatest = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setView(R.layout.activity_listview, LAYOUT_TYPE_HEADER);
		baseLayout.setHeaderBarStyle(getIntent().getStringExtra("title"), 0, 0);
		baseLayout.setTitleRightImage();
		initView();
		initData();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		rl_sort = (RelativeLayout) findViewById(R.id.rl_sort);
		rl_sort.setTag("Gone");
		tv_hottest = (TextView) findViewById(R.id.hottest);
		tv_latest = (TextView) findViewById(R.id.latest);
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
					doRefresh();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		rightDrawable = getResources().getDrawable(R.drawable.point);
		rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(),
				rightDrawable.getMinimumHeight());
		tv_latest.setCompoundDrawables(null, null, rightDrawable, null);

		baseLayout.tv_header.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (rl_sort.getTag().equals("Gone")) {
					rl_sort.setTag("Visible");
					rl_sort.setVisibility(View.VISIBLE);
					rl_sort.bringToFront();
				} else {
					rl_sort.setTag("Gone");
					rl_sort.setVisibility(View.GONE);
				}

			}
		});
		tv_hottest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				rl_sort.setTag("Gone");
				rl_sort.setVisibility(View.GONE);
				mIsLatest = false;
				tv_hottest
						.setCompoundDrawables(null, null, rightDrawable, null);
				tv_latest.setCompoundDrawables(null, null, null, null);

			}
		});
		tv_latest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mIsLatest = true;
				rl_sort.setTag("Gone");
				rl_sort.setVisibility(View.GONE);
				tv_latest.setCompoundDrawables(null, null, rightDrawable, null);
				tv_hottest.setCompoundDrawables(null, null, null, null);

			}
		});
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 5; i++) {
			VideoDetailBean bean = new VideoDetailBean();
			bean.seturl("http://img.tupianzj.com/uploads/allimg/141113/3-141113114414.jpg");
			bean.setowner_url("http://www.qqw21.com/article/UploadPic/2013-7/201373011175212899.jpg");
			bean.setTitle("爸爸去哪兒");
			bean.setbrif("关于秋天的回忆，是否也有那么一刻不能忘记");
			bean.setLong_time("22:10");
			bean.setOwner("小雪~哈哈");
			chooseDatas.add(bean);
		}
		adapter = new MoreVedioAdapter(VideoMoreActivity.this, chooseDatas);
		pullToRefreshListView.setAdapter(adapter);
		pullToRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				startActivityForAnima(new Intent().setClass(
						VideoMoreActivity.this, VideoDetailActivity.class));

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

}
