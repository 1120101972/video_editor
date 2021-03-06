package com.yixia.camera.demo.activity;

import java.util.ArrayList;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yixia.camera.demo.R;
import com.yixia.camera.demo.VCameraDemoBaseActivity;
import com.yixia.camera.demo.adapter.CoverFlowAdapter;
import com.yixia.camera.demo.fragment.VideoListFlowGFragment;
import com.yixia.camera.demo.ui.record.MediaRecorderActivity;
import com.yixia.camera.demo.ui.widget.TouchRoateImageView;
import com.yixia.camera.demo.ui.widget.coverflow.CoverFlow;
import com.yixia.camera.demo.utils.Constants;
import com.yixia.camera.demo.utils.Constants.Module;
import com.yixia.camera.demo.utils.StringUtils;

/**
 * 通过地球的转动选择特定的模块
 * 
 * @author WuFangxue
 * 
 */
public class FourModuleChooseActivity extends VCameraDemoBaseActivity {

	private TouchRoateImageView iv_change_module;
	private ImageView iv_video;
	private TextView tv_more;

	private FragmentManager manager;
	private final String TAG = "FourModuleChooseActivity";

	private ArrayList<VideoListFlowGFragment> fragments = new ArrayList<VideoListFlowGFragment>();

	CoverFlow cf;

	private String[] title = { "原创频道", "改编频道", "记者频道", "演播室" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setView(R.layout.activity_four_module_page, LAYOUT_TYPE_HEADER);
		baseLayout.setHeaderBarStyle("原创频道", R.drawable.search, 0,
				R.drawable.menu);

		initView();
		Constants.current = 0;
		for (int i = 0; i < 4; i++) {
			fragments.add(null);
		}
		// CoverFlowAdapter adapter = new CoverFlowAdapter(
		// FourModuleChooseActivity.this);
		// // flow.setAdapter(adapter);
		// // flow.setGravity(Gravity.CENTER_HORIZONTAL);
		//
		// cf = (CoverFlow) findViewById(R.id.cover_flow);
		// // ImageAdapter adapter = new ImageAdapter(this);
		// cf.setAdapter(adapter);
		// cf.setGravity(Gravity.CENTER_HORIZONTAL);

		getFragment(0);

		// CoverFlowAdapter adapter = new CoverFlowAdapter(this);
		// flow.setAdapter(adapter);
		// flow.setGravity(Gravity.CENTER_HORIZONTAL);

		// CoverFlow cf = (CoverFlow) this.findViewById(R.id.cover_flow);
		// //ImageAdapter adapter = new ImageAdapter(this);
		// cf.setAdapter(adapter);
		// cf.setGravity(Gravity.CENTER_HORIZONTAL);
	}

	@Override
	protected void handleHeaderEvent1() {
		// TODO Auto-generated method stub
		super.handleHeaderEvent1();
		startActivity(new Intent().setClass(mContext,
				PersonalMainActivity.class));

	}

	public void setMargin(int width, int height) {
		RelativeLayout.LayoutParams mp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		mp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		mp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		// if ((1600 - width) / 2 > 0)
		DisplayMetrics dm = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);

		mp.setMargins(-(1560 - dm.widthPixels), 0, 0,
				-(int) ((height / 2) * (1 + (Math.sqrt(2.0) / 2))));

		// -(int) ((height / 2) * (1 + (Math.sqrt(2.0) / 2)))
		// else {
		// mp.setMargins(0, 0, 0,
		// -(int) ((height / 2) * (1 + (Math.sqrt(2.0) / 2))));
		// }

		iv_change_module.setLayoutParams(mp);
		iv_change_module.setVisibility(View.VISIBLE);
		// Toast.makeText(
		// FourModuleChooseActivity.this,
		// StringUtils.px2dip(FourModuleChooseActivity.this,
		// (int) ((height/2) * (1 + (Math.sqrt(2.0) / 2)))) + "", 0)
		// .show();
		// Toast.makeText(
		// FourModuleChooseActivity.this,
		// StringUtils.px2dip(FourModuleChooseActivity.this, 700 - height)
		// + "", 0).show();

	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			iv_change_module.invalidate();

		}
	};

	private Runnable myRunnable = new Runnable() {
		public void run() {
			Message message = Message.obtain();
			message.what = 1;
			handler.sendMessage(message);

		}
	};

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		iv_change_module = (TouchRoateImageView) findViewById(R.id.infoOperating);
		iv_video = (ImageView) findViewById(R.id.iv_camera);
		iv_video.bringToFront();
		iv_video.setOnClickListener(this);
		tv_more = (TextView) findViewById(R.id.tv_more);
		tv_more.setOnClickListener(this);

		baseLayout.btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent().setClass(mContext,
						LoginActivity.class));
			}
		});

	}

	public void getFragment(int type) {

		baseLayout.setHeaderBarStyle(title[type], R.drawable.menu, 0,
				R.drawable.search);
		Constants.current = type;
		manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.setCustomAnimations(R.anim.anim_alpha_show, R.anim.anim_alpha_fade);

		if (fragments.get(type) != null) {
			ft.replace(R.id.frag_cover_flow, fragments.get(type)).commit();
		} else {
			Bundle bundle1 = new Bundle();
			bundle1.putInt("module", type);
			VideoListFlowGFragment fragment = new VideoListFlowGFragment();
			fragment.setArguments(bundle1);
			ft.replace(R.id.frag_cover_flow, fragment).commit();
			fragments.add(type, fragment);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		int Id = v.getId();
		switch (Id) {
		case R.id.iv_camera:
			if (Constants.current == 1)
				startActivityForAnima(new Intent().setClass(mContext,
						VideoRedesignChooseActivity.class));
			else {
				startActivityForAnima(new Intent().setClass(mContext,
						MediaRecorderActivity.class));
			}

			break;
		case R.id.tv_more:

			startActivityForAnima(new Intent().setClass(mContext,
					VideoMoreActivity.class).putExtra("title",
					title[Constants.current]));

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

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}
