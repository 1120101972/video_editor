package com.yixia.camera.demo;


import com.yixia.camera.demo.ui.widget.BaseLayout;
import com.yixia.camera.demo.utils.StringUtils;
import com.yixia.camera.demo.utils.Utils;

import android.app.Activity;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

public abstract class VCameraDemoBaseActivity extends FragmentActivity implements OnClickListener {

	public VCameraDemoBaseActivity() {
		mContext = this;
		mApp = (VCameraDemoApplication)VCameraDemoApplication.getContext();
	}

	protected Context mContext;
	protected VCameraDemoApplication mApp ;

	/**
	 * 当前Activity是否在在最顶端
	 */
	protected boolean mIsFront = false;
	/**
	 * 是否重新初始化贷款计算器数据
	 */
	public static boolean isClear = false;
	public static boolean isFirst = false;
	
	/**
	 * 退出app的时候发送一条广播，所有子类都将finish()
	 */
//	private BroadcastReceiver mExitAppReceiver = new BroadcastReceiver() {
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			finish();
//		}
//	};

	protected static final int LAYOUT_TYPE_NORMAL = 0;
	protected static final int LAYOUT_TYPE_HEADER = 1;
	protected static final int LAYOUT_TYPE_PROGRESS = 2;
	protected static final int LAYOUT_TYPE_HEADER_PROGRESS = 3;
	protected static final int LAYOUT_TYPE_HEADER_SEARCH = 4;

	protected BaseLayout baseLayout;
	
	public abstract void initView();
	
	public abstract void initData();

	/**
	 * 设置布局
	 * 
	 * @param layoutResId
	 *            布局id
	 * @param type
	 *            (类型：layout_type_normal为一般布局，layout_type_header为有header布局，
	 *            layout_type_progress为有progressbg布局
	 *            ，layout_type_header_progress为既有header布局又有progressbg布局)
	 */
	protected void setView(int layoutResId, int type) {
		switch (type) {
		case LAYOUT_TYPE_NORMAL:
			setContentView(layoutResId);
			break;

		default:
			baseLayout = new BaseLayout(this, layoutResId, type);
			setContentView(baseLayout);
			break;
		}
		if (baseLayout != null) {
			if (baseLayout.btn_refresh != null)
				baseLayout.btn_refresh.setOnClickListener(this);
			if (baseLayout.img_right2 != null)
				baseLayout.img_right2.setOnClickListener(this);
			if (baseLayout.img_right1 != null)
				baseLayout.img_right1.setOnClickListener(this);
			if (baseLayout.btn_back != null)
				baseLayout.btn_back.setOnClickListener(this);
			if (baseLayout.tv_button != null)
				baseLayout.tv_button.setOnClickListener(this);
			// if (baseLayout.ll_city_header != null)
			// baseLayout.ll_city_header.setOnClickListener(this);
		}
		// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
		// | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (mContext == this) {
			super.onCreate(savedInstanceState);

//			if (!Utils.isNetworkAvailable(this)) {
//
//			}
//			registerReceiver(mExitAppReceiver, new IntentFilter(
//					SoufunConstants.INTENT_ACTION_EXIT_APP));
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		mIsFront = true;
//		if (!Tools.isServiceRunning(getApplicationContext())
//				&& Utils.isNetConn(mContext)) {
//			startService(new Intent(mContext, ChatService.class));
//		}
//		if (activityType == NAVIGATION_TYPE_MAIN) {
//			mApp.push(this);
//		}
//		MobclickAgent.onResume(mContext);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mIsFront = false;
	//	MobclickAgent.onPause(mContext);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_right1:
			handleHeaderEvent();
			break;
		case R.id.img_right2:
			handleHeaderEvent1();
			break;
		case R.id.btn_back:
			exit();
			break;
		case R.id.tv_button:
			handleHeaderTextEvent();
			break;

		case R.id.btn_refresh:
			handleOnClickProgress();
			break;

		}
	}

	protected View more;
	private TextView tv_more_text;


	

	/**
	 * 预处理progressbg
	 */
	protected void onPreExecuteProgress() {
		baseLayout.progressbg.setVisibility(View.VISIBLE);
		baseLayout.plv_loading.setVisibility(View.VISIBLE);
		baseLayout.tv_load_error.setVisibility(View.INVISIBLE);
		baseLayout.btn_refresh.setVisibility(View.INVISIBLE);
	}

	/**
	 * 无数据处理
	 */
	public void onExecuteProgressNoData() {
		onExecuteProgressNoData("没有符合条件的楼盘\n你可以换个地方改变条件试试");
	}

	/**
	 * 无数据处理
	 */
	public void onExecuteProgressNoData(String title) {
		baseLayout.plv_loading.setVisibility(View.GONE);
		baseLayout.btn_refresh.setVisibility(View.GONE);
		baseLayout.tv_load_error.setVisibility(View.VISIBLE);
		baseLayout.tv_load_error.setText(title);
	}

	/**
	 * 无数据处理
	 */
	public void onExecuteProgressNoData(String title, String des) {
		baseLayout.plv_loading.setVisibility(View.GONE);
		baseLayout.btn_refresh.setVisibility(View.GONE);
		baseLayout.tv_load_error.setVisibility(View.GONE);
		baseLayout.tv_des1.setVisibility(View.VISIBLE);
		baseLayout.tv_des1.setText(title);
		baseLayout.tv_des2.setVisibility(View.VISIBLE);
		baseLayout.tv_des2.setText(des);
	}

	/**
	 * 加载失败处理progressbg
	 */
	protected void onExecuteProgressError() {
		baseLayout.plv_loading.stopAnimation();
		AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(500);
		baseLayout.btn_refresh.startAnimation(animation);
		baseLayout.tv_load_error.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) { // 动画结束时执行此方法
				baseLayout.btn_refresh.setVisibility(View.VISIBLE);
				baseLayout.tv_load_error.setVisibility(View.VISIBLE);
			}
		});
	}

	/**
	 * 加载成功处理progressbg
	 */
	protected void onPostExecuteProgress() {
		AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
		animation.setDuration(400);
		baseLayout.progressbg.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) { // 动画结束时执行此方法
				baseLayout.progressbg.setVisibility(View.GONE);
			}
		});
	}

	/** 如果统一设置progress，处理业务逻辑，子类必须重写此方法 */
	protected void handleOnClickProgress() {
		onPreExecuteProgress();
	}


	/**
	 * 设置HeaderBar的内容
	 * 
	 * @param title
	 *            中间Text文本 （不能为空）
	 * @param btn_text
	 *            右边按钮文本
	 */
	protected void setHeaderBar(String title, String btn_text) {
		baseLayout.setTitleAndButton(title, btn_text);
	}

	protected void setHeaderCity(String city) {
		// baseLayout.ll_city_header.setVisibility(View.VISIBLE);
		baseLayout.tv_city_header.setText(city);
	}

	protected void setHeaderBar(String title, String btn_text, String btn_text1) {
		baseLayout.setTitleAndButton(title, btn_text, btn_text1);
	}

	/**
	 * 设置HeaderBar的内容
	 * 
	 * @param title
	 *            中间Text文本 （不能为空）
	 */
	protected void setHeaderBar(String title) {
		baseLayout.setTitleAndButton(title);
	}

	/**
	 * 设置HeaderBar的imageView
	 * 
	 * @param topRight1
	 *            右上角第一个图片的resID
	 * @param topRight2
	 *            右上角第二个图片的resID
	 */
	protected void setHeaderBarIcon(String title, int topRight1, int topRight2) {
		baseLayout.setHeaderBarStyle(title, topRight1, topRight2);
	}

	/**
	 * 处理Header右边按钮点击事件
	 * 
	 */
	protected void handleHeaderEvent() {
	}

	/**
	 * 处理Header右边按钮点击事件
	 * 
	 */
	protected void handleHeaderEvent1() {
	}
	
	/**
	 * 处理Header右边按钮点击事件
	 * 
	 */
	protected void handleHeaderTextEvent() {
	}


	/**
	 * <功能详细描述>是否响应物理按键到搜索，onKeyDown中调用<br>
	 * 
	 * @return<br>
	 */
	protected boolean isCanSearch() {
		return true;
	}

	public void exit() {
		onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(KeyEvent.KEYCODE_BACK,
				KeyEvent.ACTION_DOWN));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_SEARCH) {
			if (!isCanSearch()) {
				return true;
			}
			// Intent intentSearch = new Intent(Intent.ACTION_VIEW,
			// Uri.parse(AppConst.SEARCH_URI));
			// intentSearch.putExtra(AppConst.PARA_SEARCH_TAB, getSearchTab());
			// mActualContext.startActivity(intentSearch);
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onDestroy() {
//		if (mContext == this) {
//			unregisterReceiver(mExitAppReceiver);
//		}

		if (null != mApp) {
			mApp.pull((Activity) mContext);
		}
		super.onDestroy();
	}

	/**
	 * toast（默认 时间Toast.LENGTH_LONG）
	 * 
	 * @param msg
	 *            内容
	 */
	protected void toast(String msg) {
		if (StringUtils.isNullOrEmpty(msg)) {
			return;
		}
		Utils.toast(mContext, msg, mIsFront);
	}

	/**
	 * toast（默认 时间Toast.LENGTH_LONG）
	 * 
	 * @param msg
	 *            内容
	 * @param duration
	 *            时间
	 */
	protected void toast(int resid) {
		Utils.toast(mContext, resid, mIsFront);
	}

	/**
	 * toast
	 * 
	 * @param resid
	 *            内容资源id
	 * @param duration
	 *            时间
	 */
	protected void toast(String msg, int duration) {
		if (StringUtils.isNullOrEmpty(msg)) {
			return;
		}
		Utils.toast(mContext, msg, mIsFront, duration);
	}

	protected void toast(int resid, int duration) {
		Utils.toast(mContext, resid, mIsFront, duration);
	}

	protected void toast(String msg, int duration, int position, int yOffset) {
		Utils.toast(mContext, msg, mIsFront, duration, position, yOffset);
	}

	

	/**
	 * 启动activity带有动画切换
	 * 
	 * @param intent
	 * @param requestCode
	 */
	protected void startActivityForResultAndAnima(Intent intent, int requestCode) {
		startActivityForResultAndAnima(intent, requestCode, null);
	}

	/**
	 * 启动activity带有动画切换
	 * 
	 * @param intent
	 */
	protected void startActivityForAnima(Intent intent) {
		startActivityForAnima(intent, null);
	}

	/**
	 * 启动activity带有动画切换
	 * 
	 * @param intent
	 * @param requestCode
	 * @param parentActivity
	 */
	protected void startActivityForResultAndAnima(Intent intent,
			int requestCode, Activity parentActivity) {
		if (intent != null) {
			if (parentActivity != null) {
				parentActivity.startActivityForResult(intent, requestCode);
				parentActivity.overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			} else {
				startActivityForResult(intent, requestCode);
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			}
		}
	}

	/**
	 * 启动activity带有动画切换
	 * 
	 * @param intent
	 * @param parentActivity
	 */
	protected void startActivityForAnima(Intent intent, Activity parentActivity) {
		if (intent != null) {
			if (parentActivity != null) {
				parentActivity.startActivity(intent);
				parentActivity.overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			} else {
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			}
		}
	}

	@Override
	public View findViewById(int id) {
		if (baseLayout != null) {
			return baseLayout.findViewById(id);
		} else {
			return super.findViewById(id);
		}

	}

	public int getScreenWith() {
		return getWindowManager().getDefaultDisplay().getWidth();
	}
}


