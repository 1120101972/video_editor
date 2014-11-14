package com.yixia.camera.demo.ui.record;

import java.io.Console;
import java.io.File;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.annotation.TargetApi;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yixia.camera.demo.R;
import com.yixia.camera.demo.VCameraDemoBaseActivity;
import com.yixia.camera.demo.ui.BaseActivity;
import com.yixia.camera.demo.ui.widget.SurfaceVideoView;
import com.yixia.camera.util.DeviceUtils;
import com.yixia.camera.util.StringUtils;

/**
 * 通用单独播放界面
 * 
 * @author tangjun
 * 
 */
public class VideoPlayerActivity extends VCameraDemoBaseActivity implements
		SurfaceVideoView.OnPlayStateListener, OnErrorListener,
		OnPreparedListener, OnClickListener, OnCompletionListener,
		OnInfoListener {

	/** 播放控件 */
	private SurfaceVideoView mVideoView;
	/** 暂停按钮 */
	private View mPlayerStatus;
	private View mLoading;

	/** 播放路径 */
	private String mPath;
	/** 是否需要回复播放 */
	private boolean mNeedResume;

	/**
	 * 上传文件
	 */
	private HttpPost post;
	private static final int POST_SUCCESS = 10024;
	private static final int POST_FAILURE = 10025;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 防止锁屏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		mPath = getIntent().getStringExtra("path");
		// mPath = "http://10.1.112.123:6060/vedio/hello.mp4" ;
		Toast.makeText(VideoPlayerActivity.this, mPath, 0).show();
		Log.d(this.getClass().toString(), mPath);
		if (StringUtils.isEmpty(mPath)) {
			finish();
			return;
		}

		setView(R.layout.activity_video_player, LAYOUT_TYPE_HEADER);

		baseLayout.setTitleAndButton("视频信息", "发布");
		mVideoView = (SurfaceVideoView) findViewById(R.id.videoview);
		mPlayerStatus = findViewById(R.id.play_status);
		mLoading = findViewById(R.id.loading);

		mVideoView.setOnPreparedListener(this);
		mVideoView.setOnPlayStateListener(this);
		mVideoView.setOnErrorListener(this);
		mVideoView.setOnClickListener(this);
		mVideoView.setOnInfoListener(this);
		mVideoView.setOnCompletionListener(this);
		baseLayout.btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();

			}
		});

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
		// .getScreenWidth(VideoPlayerActivity.this) * radio);
		// // Toast.makeText(VideoPlayerActivity.this,"比例" +radio+
		// // "高度"+(int)
		// // (DeviceUtils.getScreenWidth(VideoPlayerActivity.this)*radio)
		// // , 0).show();
		// }
		// });

		findViewById(R.id.root).setOnClickListener(this);
		mVideoView.setVideoPath(mPath);

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
					Toast.makeText(VideoPlayerActivity.this, "上传成功", 0).show();
					mPath = "http://10.1.112.123:5080/live/vedio/"
							+ object.getString("message");
					Intent intent = new Intent();
					intent.setClass(VideoPlayerActivity.this,
							VideoPlayerTwoActivity.class);
					intent.putExtra("path", mPath);
					startActivity(intent);

				} else {
					Toast.makeText(VideoPlayerActivity.this, "上传成功失败，请重新上传", 0)
							.show();

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	};

	private class postLostFound implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message message = new Message();
			MultipartEntity entity = new MultipartEntity();
			try {

				File file = new File(mPath);
				entity.addPart("uploadfile", new FileBody(file));
				post.setEntity(entity);
				// 请求服务器
				BasicHttpParams httpParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
				HttpConnectionParams.setSoTimeout(httpParams, 30000);

				HttpResponse response = new DefaultHttpClient(httpParams)
						.execute(post);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					message.what = POST_SUCCESS;
					String strResult = EntityUtils.toString(response
							.getEntity());
					message.obj = strResult;

				} else {
					message.what = POST_FAILURE;
				}
			} catch (Exception e) {
				message.what = 0;
			} finally {
				handler.sendMessage(message);
			}

		}

	}

	@Override
	public void onResume() {
		super.onResume();
		if (mVideoView != null && mNeedResume) {
			mNeedResume = false;
			if (mVideoView.isRelease())
				mVideoView.reOpen();
			else
				mVideoView.start();
		}
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
			mVideoView.release();
			mVideoView = null;
		}
		super.onDestroy();
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		mVideoView.setVolume(SurfaceVideoView.getSystemVolumn(this));
		mVideoView.start();
		// new Handler().postDelayed(new Runnable() {
		//
		// @SuppressWarnings("deprecation")
		// @Override
		// public void run() {
		// if (DeviceUtils.hasJellyBean()) {
		// mVideoView.setBackground(null);
		// } else {
		// mVideoView.setBackgroundDrawable(null);
		// }
		// }
		// }, 300);
		mLoading.setVisibility(View.GONE);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		switch (event.getKeyCode()) {// 跟随系统音量走
		case KeyEvent.KEYCODE_VOLUME_DOWN:
		case KeyEvent.KEYCODE_VOLUME_UP:
			mVideoView.dispatchKeyEvent(this, event);
			break;
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	public void onStateChanged(boolean isPlaying) {
		mPlayerStatus.setVisibility(isPlaying ? View.GONE : View.VISIBLE);
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		if (!isFinishing()) {
			// 播放失败
		}
		finish();
		return false;
	}

	@Override
	protected void handleHeaderTextEvent() {
		// TODO Auto-generated method stub
		super.handleHeaderTextEvent();

		toast("OK");
		if (post == null) {
			post = new HttpPost("http://10.1.112.123:8080/vedio/upload.action");
		}
		Toast.makeText(VideoPlayerActivity.this, "开始上传", 0).show();
		new Thread(new postLostFound()).start();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.root:
			finish();
			break;

		case R.id.videoview:
			if (mVideoView.isPlaying())
				mVideoView.pause();
			else
				mVideoView.start();
			break;
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		if (!isFinishing())
			mVideoView.reOpen();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		switch (what) {
		case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
			// 音频和视频数据不正确
			break;
		case MediaPlayer.MEDIA_INFO_BUFFERING_START:
			if (!isFinishing())
				mVideoView.pause();
			break;
		case MediaPlayer.MEDIA_INFO_BUFFERING_END:
			if (!isFinishing())
				mVideoView.start();
			break;
		case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
			if (DeviceUtils.hasJellyBean()) {
				mVideoView.setBackground(null);
			} else {
				mVideoView.setBackgroundDrawable(null);
			}
			break;
		}
		return false;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}
