package com.yixia.camera.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yixia.camera.demo.R;
import com.yixia.camera.demo.VCameraDemoBaseActivity;
import com.yixia.camera.demo.adapter.CoverFlowAdapter;
import com.yixia.camera.demo.fragment.VideoListFlowGFragment;
import com.yixia.camera.demo.ui.record.MediaRecorderActivity;
import com.yixia.camera.demo.ui.widget.coverflow.CoverFlow;
import com.yixia.camera.demo.ui.widget.coverflow.DiscoverContainerView;

/**
 * 通过地球的转动选择特定的模块
 * 
 * @author WuFangxue
 * 
 */
public class FourModuleChooseActivity extends VCameraDemoBaseActivity {

	private ImageView iv_change_module;
	private ImageView iv_video;
	private DiscoverContainerView flow;

	private FragmentManager manager;
	private final String TAG = "FourModuleChooseActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setView(R.layout.activity_four_module_page, LAYOUT_TYPE_HEADER);
		baseLayout.setHeaderBarStyle("原创频道", R.drawable.page_loading_bar,
				R.drawable.page_loading_bar);
		initView();
		flow = (DiscoverContainerView) findViewById(R.id.cover_flow);
		flow.initCardView(FourModuleChooseActivity.this);
//		CoverFlowAdapter adapter = new CoverFlowAdapter(this);
//		flow.setAdapter(adapter);
//		flow.setGravity(Gravity.CENTER_HORIZONTAL);
		// getFragment();
	}

	public void setMargin(int height) {
		RelativeLayout.LayoutParams mp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		mp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		mp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		mp.setMargins(0, 0, 0, -(height / 2));
		iv_change_module.setLayoutParams(mp);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		iv_change_module = (ImageView) findViewById(R.id.infoOperating);
		iv_video = (ImageView) findViewById(R.id.iv_camera);
		iv_video.bringToFront();
		iv_video.setOnClickListener(this);

	}

	private void getFragment() {
		manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		VideoListFlowGFragment fragment = new VideoListFlowGFragment();
		ft.replace(R.id.frag_cover_flow, fragment).commit();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		int Id = v.getId();
		switch (Id) {
		case R.id.iv_camera:

			startActivityForAnima(new Intent().setClass(mContext,
					MediaRecorderActivity.class));
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		Log.d(TAG, "FourModule  onTouchEvent" + event.getAction() + "  "
				+ super.onTouchEvent(event));

		return super.onTouchEvent(event);
	}

}
