package com.yixia.camera.demo.ui.widget;

import com.yixia.camera.demo.R;
import com.yixia.camera.demo.utils.StringUtils;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BaseLayout extends RelativeLayout {

	private static final int HEADER = 1;
	private static final int PROGRESS = 2;
	private static final int HEADERANDPROGRESS = 3;
	private static final int SEARCH = 4;
	private Context mContext;
	public ImageView btn_back;
	public ImageView img_right2, img_right1;
	public TextView tv_button;
	private TextView tv_header;
	private View ll_header_right, header_bar;

	public View progressbg;
	public Button btn_refresh;
	public PageLoadingView plv_loading;
	public TextView tv_load_error, tv_city_header, tv_des1, tv_des2;

	public BaseLayout(Context context, int layoutResourceId, int type) {
		super(context);
		mContext = context;
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		switch (type) {
		case PROGRESS:
			setProgressBg(layoutInflater);
			break;
		case HEADER:
			setHeaderBar(layoutInflater);
			break;

		case HEADERANDPROGRESS:
			setHeaderBar(layoutInflater);
			setProgressBg(layoutInflater);
			break;
		}

		View view = layoutInflater.inflate(layoutResourceId, null);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

		if (progressbg != null) {
			params.addRule(RelativeLayout.BELOW, R.id.process_page);
		} else {
			params.addRule(RelativeLayout.BELOW, R.id.header_bar);
		}
		addView(view, params);

	}

	protected void setHeaderBar(LayoutInflater layoutInflater) {
		header_bar = layoutInflater.inflate(R.layout.header_bar, null);
		tv_header = (TextView) header_bar.findViewById(R.id.tv_header);
		btn_back = (ImageView) header_bar.findViewById(R.id.btn_back);
		img_right2 = (ImageView) header_bar.findViewById(R.id.img_right2);
		img_right1 = (ImageView) header_bar.findViewById(R.id.img_right1);
		tv_button = (TextView) header_bar.findViewById(R.id.tv_button);
		ll_header_right = header_bar.findViewById(R.id.ll_header_right);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		addView(header_bar, params);
	}

	protected void setProgressBg(LayoutInflater layoutInflater) {
		progressbg = layoutInflater.inflate(R.layout.process_page, null);
		progressbg.setId(R.id.process_page);
		plv_loading = (PageLoadingView) progressbg
				.findViewById(R.id.plv_loading);
		tv_load_error = (TextView) progressbg.findViewById(R.id.tv_load_error);
		tv_des1 = (TextView) progressbg.findViewById(R.id.tv_des1);
		tv_des2 = (TextView) progressbg.findViewById(R.id.tv_des2);
		btn_refresh = (Button) progressbg.findViewById(R.id.btn_refresh);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		params.addRule(RelativeLayout.BELOW, R.id.header_bar);
		addView(progressbg, params);
	}

	public void setTitleAndButton(String title, String btn_text) {
		setTitleAndButton(title, btn_text, "");
	}

	public void setTitleAndButton(String title, String btn_text,
			String btn_text1) {
		if (!StringUtils.isNullOrEmpty(title)) {
			tv_header.setVisibility(View.VISIBLE);
			tv_header.setText(title);
		} else {
			tv_header.setVisibility(View.GONE);
		}
		if (StringUtils.isNullOrEmpty(btn_text)
				&& StringUtils.isNullOrEmpty(btn_text1)) {
			ll_header_right.setVisibility(View.INVISIBLE);
		} else {
			ll_header_right.setVisibility(View.VISIBLE);
			tv_button.setVisibility(View.VISIBLE);
			tv_button.setText(btn_text);

		}
	}

	public void setTitleAndButton(String title) {
		setTitleAndButton(title, "", "");
	}

	public void setHeaderBarStyle(String title, int topRight1, int topRight2) {
		setHeaderBarStyle(title, 0, topRight1, topRight2);
	}
	public void setHeaderBarStyle(String title,int left, int topRight1, int topRight2) {
		if (!TextUtils.isEmpty(title) || topRight1 != 0 || topRight2 != 0) {
			ll_header_right.setVisibility(View.VISIBLE);
			if (!TextUtils.isEmpty(title)) {
				tv_header.setVisibility(View.VISIBLE);
				tv_header.setText(title);
			} else {
				tv_header.setVisibility(View.GONE);
			}
			if (topRight1 != 0) {
				img_right1.setImageResource(topRight1);
				img_right1.setVisibility(View.VISIBLE);
			}
			if (topRight2 != 0) {
				img_right2.setImageResource(topRight2);
				img_right2.setVisibility(View.VISIBLE);
			}
			if(left!=0)
			{
				btn_back.setImageResource(left);
			}
		}
	}
}
