package com.yixia.camera.demo.adapter;



import com.yixia.camera.demo.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CoverFlowAdapter extends BaseAdapter {
	TextView mNumber;
	TextView mDate;
	TextView mReadmms;
	TextView mTextnum;
	TextView mMsgtext;
	ImageView mMmsicon;
	ImageView mTopdivider;
	ImageView mButtomdivider;
	ImageView mTiview;
	ImageView mBiview;
	ImageButton mPhoto;
	ImageButton mReplay;
	ImageButton mDelete;

	RelativeLayout.LayoutParams mLayoutWrap_Wrap = new RelativeLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT,
			LayoutParams.WRAP_CONTENT);
	RelativeLayout.LayoutParams mLayoutFill_Wrap = new RelativeLayout.LayoutParams(
			LayoutParams.FILL_PARENT,
			LayoutParams.WRAP_CONTENT);
	LayoutParams mLayoutGallery = new Gallery.LayoutParams(150, 120);

	private Context mContext;

	public CoverFlowAdapter(Context context) {
		this.mContext = context;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return 100;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View messagelayout(int position) {
		RelativeLayout relat = new RelativeLayout(mContext);
		relat.setBackgroundResource(R.drawable.message_card);
		relat.layout(0, 0, 380, 319);
		relat.setPadding(25, 23, 25, 38);
		relat.setLayoutParams(mLayoutFill_Wrap);

		mNumber = new TextView(mContext);
		mNumber.setText("Joseph Miller" + position);
		mNumber.setTextColor(0xFF000000);
		mNumber.setTypeface(null, Typeface.BOLD);
		mNumber.setTextSize(25);
		mNumber.layout(42, 25, 268, 60);
		relat.addView(mNumber, mLayoutWrap_Wrap);

		mDate = new TextView(mContext);
		mDate.setText("987654321");
		mDate.setTextColor(0xFF000000);
		mDate.layout(42, 60, 268, 81);
		// text2.addRule(RelativeLayout.BELOW, NUM_ID);
		relat.addView(mDate, mLayoutWrap_Wrap);

		mMmsicon = new ImageView(mContext);
		mMmsicon.setBackgroundResource(R.drawable.icon_icon_sms);
		mMmsicon.layout(42, 81, 64, 110);
		relat.addView(mMmsicon, mLayoutWrap_Wrap);

		mReadmms = new TextView(mContext);
		mReadmms.setText("3 unRead message");
		mReadmms.setTextColor(0xFF555555);
		mReadmms.layout(70, 83, 268, 110);
		relat.addView(mReadmms, mLayoutWrap_Wrap);

		mPhoto = new ImageButton(mContext);
		mPhoto.setBackgroundResource(R.drawable.message_photo_base);
		mPhoto.setBackgroundResource(R.drawable.message_photo_base);
		mPhoto.setImageResource(R.drawable.msg_default_photo);
		mPhoto.layout(270, 28, 350, 110);
		relat.addView(mPhoto, mLayoutWrap_Wrap);

		mTopdivider = new ImageView(mContext);
		mTopdivider.setBackgroundResource(R.drawable.msg_divider);
		mTopdivider.layout(30, 114, 350, 116);
		relat.addView(mTopdivider, mLayoutFill_Wrap);

		mReplay = new ImageButton(mContext);
		mReplay.setBackgroundResource(R.drawable.replay_icon_rest);
		mReplay.layout(40, 233, 68, 265);
		relat.addView(mReplay, mLayoutWrap_Wrap);

		mTiview = new ImageView(mContext);
		mTiview.setBackgroundResource(R.drawable.message_separators);
		mTiview.layout(78, 233, 80, 265);
		relat.addView(mTiview, mLayoutWrap_Wrap);

		mDelete = new ImageButton(mContext);
		mDelete.setBackgroundResource(R.drawable.common_icon_delete);
		mDelete.layout(320, 232, 345, 263);
		relat.addView(mDelete, mLayoutWrap_Wrap);

		mBiview = new ImageView(mContext);
		mBiview.setBackgroundResource(R.drawable.message_separators);
		mBiview.layout(308, 233, 310, 265);
		relat.addView(mBiview, mLayoutWrap_Wrap);

		mTextnum = new TextView(mContext);
		mTextnum.setText("3/25");
		mTextnum.setTextColor(0xFF000000);
		mTextnum.layout(175, 233, 215, 265);
		relat.addView(mTextnum, mLayoutWrap_Wrap);

		mButtomdivider = new ImageView(mContext);
		mButtomdivider.setBackgroundResource(R.drawable.divider_line);
		mButtomdivider.layout(0, 228, 380, 230);
		relat.addView(mButtomdivider, mLayoutFill_Wrap);

		mMsgtext = new TextView(mContext);
		mMsgtext.layout(40, 118, 345, 226);
		relat.addView(mMsgtext, mLayoutFill_Wrap);
		relat.buildDrawingCache();
		return relat;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			ImageView imageView = new ImageView(mContext);
			Bitmap newBmp = null;
			View v = messagelayout(position);
			newBmp = getViewBitmap((View) v);
			imageView.setImageBitmap(newBmp);
			imageView.setLayoutParams(mLayoutGallery);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			return imageView;
		} else {
			return convertView;
		}
	}

	private Bitmap getViewBitmap(View v) {
		v.clearFocus();
		v.setPressed(false);

		boolean willNotCache = v.willNotCacheDrawing();
		v.setWillNotCacheDrawing(false);

		// Reset the drawing cache background color to fully transparent
		// for the duration of this operation
		int color = v.getDrawingCacheBackgroundColor();
		v.setDrawingCacheBackgroundColor(0);

		if (color != 0) {
			v.destroyDrawingCache();
		}
		v.buildDrawingCache();
		Bitmap cacheBitmap = v.getDrawingCache();
		if (cacheBitmap == null) {
			Log.e("myTag", "failed getViewBitmap(" + v + ")",
					new RuntimeException());
			return null;
		}

		Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

		// Restore the view
		v.destroyDrawingCache();
		v.setWillNotCacheDrawing(willNotCache);
		v.setDrawingCacheBackgroundColor(color);

		return bitmap;
	}

}
