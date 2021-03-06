package com.yixia.camera.demo.activity;

import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.yixia.camera.demo.R;
import com.yixia.camera.demo.VCameraDemoBaseActivity;
import com.yixia.camera.demo.adapter.CommentListAdapter;
import com.yixia.camera.demo.bean.CommentData;
import com.yixia.camera.demo.ui.BaseActivity;
import com.yixia.camera.demo.ui.widget.MyListView;
import com.yixia.camera.demo.ui.widget.SurfaceVideoView;
import com.yixia.camera.demo.utils.Constants;
import com.yixia.camera.demo.utils.SPHelper;
import com.yixia.camera.demo.utils.Utils;
import com.yixia.camera.util.DeviceUtils;
import com.yixia.camera.util.StringUtils;

/**
 * 通用单独播放界面
 * 
 * @author tangjun
 * 
 */
public class VideoDetailActivity extends VCameraDemoBaseActivity implements
		OnClickListener {

	/** 播放控件 */
	private VideoView mVideoView;
	/** 暂停按钮 */
	private View mPlayerStatus;
	private View mLoading;
	private MyListView lv_comment;
	private MediaController controller;

	private ImageView iv_like, iv_store, iv_share;
	private TextView tv_like, tv_store, tv_share, tv_comment;

	private ScrollView rl_main;

	/** 播放路径 */
	private String mPath;
	/** 是否需要回复播放 */
	private boolean mNeedResume;
	/** 评论数据 */
	private ArrayList<CommentData> datas = new ArrayList<CommentData>();

	private int[] back_image = { R.drawable.spring_back,
			R.drawable.summer_back, R.drawable.autunm_back,
			R.drawable.winter_back };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!LibsChecker.checkVitamioLibs(this))
			return;
		// 防止锁屏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		// mPath = getIntent().getStringExtra("path");
		// mPath = "http://10.1.112.123:6060/vedio/hello.mp4" ;
		// mPath = "/mnt/sdcard/DCIM/Camera/VCameraDemo/1417253773244.mp4";
		mPath = SPHelper.getInstance(VideoDetailActivity.this).getString(
				Constants.CURRENT_VIDEOPATH, "");
		if (StringUtils.isEmpty(mPath)) {
			finish();
			return;
		}
		setView(R.layout.activity_video_detail, LAYOUT_TYPE_HEADER);
		baseLayout.setHeaderBarStyle("我的原创视频", 0, 0);

		initView();
		initData();

		// mVideoView
		// .setOnVideoSizeChangedListener(new OnVideoSizeChangedListener() {
		//
		// @Override
		// public void onVideoSizeChanged(MediaPlayer arg0, int arg1,
		// int arg2) {
		// // TODO Auto-generated method stub
		// int width = mVideoView.getVideoWidth();
		// int height = mVideoView.getHeight();
		// float radio = (float) height / (float) width;
		// mVideoView.getLayoutParams().height = (int) (DeviceUtils
		// .getScreenWidth(VideoDetailActivity.this) * radio);
		// // Toast.makeText(VideoDetailActivity.this,"比例" +radio+
		// // "高度"+(int)
		// // (DeviceUtils.getScreenWidth(VideoDetailActivity.this)*radio)
		// // , 0).show();
		// }
		// });

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		mVideoView = (VideoView) findViewById(R.id.videoview);
		mPlayerStatus = findViewById(R.id.play_status);
		mLoading = findViewById(R.id.loading);

		controller = (MediaController) findViewById(R.id.controller);
		controller.bringToFront();

		iv_like = (ImageView) findViewById(R.id.iv_like);
		iv_share = (ImageView) findViewById(R.id.iv_share);
		iv_store = (ImageView) findViewById(R.id.iv_store);
		tv_like = (TextView) findViewById(R.id.tv_like);
		tv_share = (TextView) findViewById(R.id.tv_share);
		tv_store = (TextView) findViewById(R.id.tv_store);
		tv_comment = (TextView) findViewById(R.id.tv_comment);

		iv_like.setOnClickListener(this);
		iv_share.setOnClickListener(this);
		iv_store.setOnClickListener(this);
		tv_like.setOnClickListener(this);
		tv_share.setOnClickListener(this);
		tv_store.setOnClickListener(this);
		tv_comment.setOnClickListener(this);

		rl_main = (ScrollView) findViewById(R.id.rl_main);
		rl_main.setBackgroundResource(back_image[Constants.current]);
		//
		// mVideoView.setOnPreparedListener(this);
		// mVideoView.setOnPlayStateListener(this);
		// mVideoView.setOnErrorListener(this);
		mVideoView.setOnClickListener(this);
		// mVideoView.setOnInfoListener(this);
		// mVideoView.setOnCompletionListener(this);

		baseLayout.btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		// findViewById(R.id.root).setOnClickListener(this);
		mVideoView.setVideoPath(mPath);
		mVideoView.setMediaController(controller);
		mVideoView.requestFocus();
		mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mediaPlayer) {
				// optional need Vitamio 4.0
				// mediaPlayer.setPlaybackSpeed(1.0f);
			}
		});

		lv_comment = (MyListView) findViewById(R.id.lv_comment);

	}

	/**
	 * 数据异步上传
	 */
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle bundle = msg.getData();

			try {
				JSONObject object = new JSONObject(msg.obj.toString());
				if (object.getBoolean("success")) {
					Toast.makeText(VideoDetailActivity.this, "上传成功", 0).show();
					mPath = "http://10.1.112.123:5080/live/vedio/"
							+ object.getString("message");
					// Intent intent = new Intent();
					// intent.setClass(VideoDetailActivity.this,
					// VideoPlayerTwoActivity.class);
					// intent.putExtra("path", mPath);
					// startActivity(intent);

				} else {
					Toast.makeText(VideoDetailActivity.this, "上传成功失败，请重新上传", 0)
							.show();

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	};

	// private class postLostFound implements Runnable {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// Message message = new Message();
	// MultipartEntity entity = new MultipartEntity();
	// try {
	//
	// File file = new File(mPath);
	// entity.addPart("uploadfile", new FileBody(file));
	// post.setEntity(entity);
	// // 请求服务器
	// BasicHttpParams httpParams = new BasicHttpParams();
	// HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
	// HttpConnectionParams.setSoTimeout(httpParams, 30000);
	//
	// HttpResponse response = new DefaultHttpClient(httpParams)
	// .execute(post);
	// if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	// message.what = POST_SUCCESS;
	// String strResult = EntityUtils.toString(response
	// .getEntity());
	// message.obj = strResult;
	//
	// } else {
	// message.what = POST_FAILURE;
	// }
	// } catch (Exception e) {
	// message.what = 0;
	// } finally {
	// handler.sendMessage(message);
	// }
	//
	// }
	//
	// }

	@Override
	public void onResume() {
		super.onResume();
		// if (mVideoView != null && mNeedResume) {
		// mNeedResume = false;
		// if (mVideoView.isRelease())
		// mVideoView.reOpen();
		// else
		// mVideoView.start();
		// }
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mVideoView != null) {
			if (mVideoView.isPlaying()) {
				mNeedResume = true;
				mVideoView.pause();
			}
		}
	}

	@Override
	public void onDestroy() {
		if (mVideoView != null) {
			// mVideoView.release();
			mVideoView = null;
		}
		super.onDestroy();
	}

	// @Override
	// public void onPrepared(MediaPlayer mp) {
	// mVideoView.setVolume(SurfaceVideoView.getSystemVolumn(this));
	// mVideoView.start();
	// // new Handler().postDelayed(new Runnable() {
	// //
	// // @SuppressWarnings("deprecation")
	// // @Override
	// // public void run() {
	// // if (DeviceUtils.hasJellyBean()) {
	// // mVideoView.setBackground(null);
	// // } else {
	// // mVideoView.setBackgroundDrawable(null);
	// // }
	// // }
	// // }, 300);
	// mLoading.setVisibility(View.GONE);
	// }

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		switch (event.getKeyCode()) {// 跟随系统音量走
		case KeyEvent.KEYCODE_VOLUME_DOWN:
		case KeyEvent.KEYCODE_VOLUME_UP:
			// mVideoView.dispatchKeyEvent(this, event);
			break;
		}
		return super.dispatchKeyEvent(event);
	}

	// @Override
	// public void onStateChanged(boolean isPlaying) {
	// mPlayerStatus.setVisibility(isPlaying ? View.GONE : View.VISIBLE);
	// }
	//
	// @Override
	// public boolean onError(MediaPlayer mp, int what, int extra) {
	// if (!isFinishing()) {
	// // 播放失败
	// }
	// finish();
	// return false;
	// }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.root:
			finish();
			break;
		case R.id.iv_like:
		case R.id.tv_like:
			Utils.toast(VideoDetailActivity.this, "喜欢");
			break;
		case R.id.iv_share:
		case R.id.tv_share:
			break;
		case R.id.iv_store:
		case R.id.tv_store:
			break;
		case R.id.tv_comment:
			myselfDialog(VideoDetailActivity.this);
			break;
		// case R.id.btn_upload:
		// if (post == null) {
		// post = new HttpPost(
		// "http://10.1.112.123:8080/vedio/upload.action");
		// }
		// Toast.makeText(VideoDetailActivity.this, "开始上传", 0).show();
		// new Thread(new postLostFound()).start();
		// break;
		case R.id.videoview:
			if (mVideoView.isPlaying())
				mVideoView.pause();
			else
				mVideoView.start();
			break;
		}
	}

	// @Override
	// public void onCompletion(MediaPlayer mp) {
	// if (!isFinishing())
	// mVideoView.reOpen();
	// }
	//
	// @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	// @Override
	// public boolean onInfo(MediaPlayer mp, int what, int extra) {
	// switch (what) {
	// case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
	// // 音频和视频数据不正确
	// break;
	// case MediaPlayer.MEDIA_INFO_BUFFERING_START:
	// if (!isFinishing())
	// mVideoView.pause();
	// break;
	// case MediaPlayer.MEDIA_INFO_BUFFERING_END:
	// if (!isFinishing())
	// mVideoView.start();
	// break;
	// case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
	// if (DeviceUtils.hasJellyBean()) {
	// mVideoView.setBackground(null);
	// } else {
	// mVideoView.setBackgroundDrawable(null);
	// }
	// break;
	// }
	// return false;
	// }

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 5; i++) {
			CommentData data = new CommentData(
					"小雪",
					"真是太萌了受不了了~~",
					"2014-11-14 19:36",
					"http://img.tupianzj.com/uploads/allimg/141113/3-141113114414.jpg",
					i);
			datas.add(data);
		}

		CommentListAdapter adapter = new CommentListAdapter(
				VideoDetailActivity.this, datas);
		lv_comment.setAdapter(adapter);

	}

	/**
	 * 输入文字设置
	 */
	Dialog dialog = null;
	private ImageView imgOK, imgNO;
	private EditText etxtName, etxtComment;

	public void myselfDialog(final Context context) {

		dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.outputcomment);

		Window diaWindow = dialog.getWindow();
		WindowManager.LayoutParams lp = diaWindow.getAttributes();
		diaWindow.setGravity(Gravity.BOTTOM);
		lp.width = LayoutParams.FILL_PARENT;
		diaWindow.setAttributes(lp);
		imgOK = (ImageView) dialog.findViewById(R.id.imgOK);
		imgNO = (ImageView) dialog.findViewById(R.id.imgNo);
		etxtName = (EditText) dialog.findViewById(R.id.etxtName);
		etxtName.setVisibility(View.GONE);
		etxtComment = (EditText) dialog.findViewById(R.id.etxtCommentDetail);
		etxtComment.setHint("写评论...");
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(etxtComment, InputMethodManager.SHOW_FORCED);
		imgNO.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		imgOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String mBody = etxtComment.getText().toString();

				InputMethodManager imm = (InputMethodManager) context
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(etxtComment.getWindowToken(), 0); // 强制隐藏键盘

				// 将评论的信息发送到数据库进行存储

				// 每次将评论发表之后都要刷新一次得到最新的评论信息
				dialog.dismiss();

			}
		});
		dialog.setTitle("评论");

		dialog.show();

	}

}
