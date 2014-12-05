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
import android.widget.ExpandableListView;
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
import com.yixia.camera.demo.adapter.PersonalWithCommentAdapter;
import com.yixia.camera.demo.bean.CommentData;
import com.yixia.camera.demo.bean.PersonalWithCommentData;
import com.yixia.camera.demo.bean.VideoDetailBean;
import com.yixia.camera.demo.bean.VideoRedesignBean;
import com.yixia.camera.demo.ui.record.MediaRecorderActivity;
import com.yixia.camera.demo.ui.widget.MyListView;

public class PersonalMainActivity extends VCameraDemoBaseActivity {

	private ListView pullToRefreshListView;
	private TextView tv_good, tv_store;

	private PersonalWithCommentAdapter adapter;
	private ArrayList<PersonalWithCommentData> chooseDatas = new ArrayList<PersonalWithCommentData>();
	Drawable bottomDrawable;

	private boolean mIsRefresh = false;
	private boolean mIsLatest = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setView(R.layout.activity_personal_page, LAYOUT_TYPE_HEADER);
		baseLayout.setHeaderBarStyle("个人主页", 0, R.drawable.setting);

		initView();
		initData();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub

		tv_good = (TextView) findViewById(R.id.tv_good);
		tv_store = (TextView) findViewById(R.id.tv_store);
		pullToRefreshListView = (ListView) findViewById(R.id.video_list);

		bottomDrawable = getResources().getDrawable(R.drawable.personaal_chose);
		bottomDrawable.setBounds(0, 0, bottomDrawable.getMinimumWidth(),
				bottomDrawable.getMinimumHeight());
		tv_good.setCompoundDrawables(null, null, null, bottomDrawable);

		tv_good.setTag("Choose");

		tv_good.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tv_good.setCompoundDrawables(null, null, null, bottomDrawable);
				tv_store.setCompoundDrawables(null, null, null, null);
				tv_good.setTag("Choose");

			}
		});
		tv_store.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tv_store.setCompoundDrawables(null, null, null, bottomDrawable);
				tv_good.setCompoundDrawables(null, null, null, null);
				tv_good.setTag("None");

			}
		});
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 5; i++) {
			PersonalWithCommentData bean = new PersonalWithCommentData();
			bean.setVideoUrl("http://img.tupianzj.com/uploads/allimg/141113/3-141113114414.jpg");
			bean.setTitle("爸爸去哪兒");
			bean.setContent("关于秋天的回忆，是否也有那么一刻不能忘记");
			ArrayList<CommentData> commentDatas = new ArrayList<CommentData>();
			for (int j = 0; j < 3; j++) {
				CommentData data = new CommentData(
						"小雪",
						"真是太萌了受不了了~~",
						"2014-11-14 19:36",
						"http://img.tupianzj.com/uploads/allimg/141113/3-141113114414.jpg",
						j);
				commentDatas.add(data);

			}
			bean.setcommentDatas(commentDatas);
			chooseDatas.add(bean);
		}
		adapter = new PersonalWithCommentAdapter(PersonalMainActivity.this,
				chooseDatas);
		pullToRefreshListView.setAdapter(adapter);
		pullToRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				startActivityForAnima(new Intent().setClass(
						PersonalMainActivity.this, VideoDetailActivity.class));

			}
		});

	}

}
