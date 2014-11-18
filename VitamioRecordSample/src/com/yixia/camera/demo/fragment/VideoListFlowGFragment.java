package com.yixia.camera.demo.fragment;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yixia.camera.demo.R;
import com.yixia.camera.demo.adapter.CoverFlowAdapter;
import com.yixia.camera.demo.bean.VideoDetailBean;
import com.yixia.camera.demo.ui.widget.coverflow.CoverFlow;
import com.yixia.camera.demo.utils.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.FancyCoverFlowAdapter;

/**
 * CoverFlow ,便于存储已有的数据，减少刷新次数
 * 
 * @author WuFangxue
 * 
 */
public class VideoListFlowGFragment extends Fragment {

	private ArrayList<VideoDetailBean> list = new ArrayList<VideoDetailBean>();

	CoverFlow cf;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.layout_inflate_example, null);
		FancyCoverFlow fancyCoverFlow = (FancyCoverFlow) view
				.findViewById(R.id.fancyCoverFlow);
		fancyCoverFlow.setReflectionEnabled(true);
		fancyCoverFlow.setReflectionRatio(0.3f);
		fancyCoverFlow.setReflectionGap(0);
		for (int i = 0; i < 5; i++) {
			VideoDetailBean bean = new VideoDetailBean();
			bean.seturl("http://img.tupianzj.com/uploads/allimg/141113/3-141113114414.jpg");
			bean.setbrif("OK 真好看");
			list.add(bean);
		}

		fancyCoverFlow.setAdapter(new ViewGroupExampleAdapter(getActivity(),
				list));
		// CoverFlowAdapter adapter = new CoverFlowAdapter(getActivity());
		// // flow.setAdapter(adapter);
		// // flow.setGravity(Gravity.CENTER_HORIZONTAL);
		//
		// cf = (CoverFlow) view.findViewById(R.id.cover_flow);
		// // ImageAdapter adapter = new ImageAdapter(this);
		// cf.setAdapter(adapter);
		// cf.setGravity(Gravity.CENTER_HORIZONTAL);
		return view;
	}

	private static class ViewGroupExampleAdapter extends FancyCoverFlowAdapter {

		// =============================================================================
		// Private members
		// =============================================================================

		private ArrayList<VideoDetailBean> dataArrayList;

		private LayoutInflater mInflater;
		private DisplayImageOptions displayImageOptions;

		private ViewGroupExampleAdapter(Context context,
				ArrayList<VideoDetailBean> list) {
			mInflater = LayoutInflater.from(context);
			dataArrayList = list;

			// displayImageOptions = new DisplayImageOptions.Builder()
			// .showStubImage(R.drawable.image1)
			// .showImageForEmptyUri(R.drawable.image1)
			// .showImageOnFail(R.drawable.image1).cacheInMemory(true) //
			// default
			// .cacheOnDisc(true) // default
			// .build();
		}

		// =============================================================================
		// Supertype overrides
		// =============================================================================

		@Override
		public int getCount() {
			if (dataArrayList == null)
				return 0;
			return dataArrayList.size();
		}

		@Override
		public Object getItem(int i) {
			return null;
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getCoverFlowItem(int i, View reuseableView,
				ViewGroup viewGroup) {
			CustomViewGroup customViewGroup = null;

			if (reuseableView != null) {
				customViewGroup = (CustomViewGroup) reuseableView;
			} else {
				customViewGroup = new CustomViewGroup(viewGroup.getContext());
				customViewGroup
						.setLayoutParams(new FancyCoverFlow.LayoutParams(300,
								600));
			}

			ImageLoader.getInstance().displayImage(
					dataArrayList.get(i).geturl(),
					customViewGroup.getImageView());
			customViewGroup.getTextView().setText(
					dataArrayList.get(i).getbrif());

			return customViewGroup;
		}
	}

	private static class CustomViewGroup extends LinearLayout {
		// =============================================================================
		// Child views
		// =============================================================================

		private ImageView imageView;

		private TextView textView;

		// =============================================================================
		// Constructor
		// =============================================================================

		private CustomViewGroup(Context context) {
			super(context);

			this.setOrientation(VERTICAL);
			this.setWeightSum(5);

			this.imageView = new ImageView(context);
			this.textView = new TextView(context);

			LayoutParams layoutParams = new LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);

			LayoutParams layoutParams2 = new LayoutParams(StringUtils.dip2px(
					context, 150), StringUtils.dip2px(context, 250));

			this.imageView.setLayoutParams(layoutParams2);
			this.textView.setLayoutParams(layoutParams);

			this.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			this.imageView.setBackgroundColor(getResources().getColor(
					R.color.white));
			this.imageView.setAdjustViewBounds(true);
			this.imageView.setClickable(true);

			this.imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent i = new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("https://davidschreiber.github.com/FancyCoverFlow"));
					view.getContext().startActivity(i);
				}
			});

			this.addView(this.imageView);
			this.addView(this.textView);
		}

		// =============================================================================
		// Getters
		// =============================================================================

		private ImageView getImageView() {
			return imageView;
		}

		private TextView getTextView() {
			return textView;
		}

	}

}
