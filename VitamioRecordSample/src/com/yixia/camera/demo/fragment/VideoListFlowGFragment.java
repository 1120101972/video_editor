package com.yixia.camera.demo.fragment;

import java.util.ArrayList;

import com.yixia.camera.demo.R;
import com.yixia.camera.demo.adapter.ImageAdapter;
import com.yixia.camera.demo.bean.VideoDetailBean;
import com.yixia.camera.demo.ui.widget.coverflow.CoverFlow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;

/**
 * CoverFlow ,便于存储已有的数据，减少刷新次数
 * 
 * @author WuFangxue
 * 
 */
public class VideoListFlowGFragment extends Fragment {

	private RelativeLayout rl_main;
	CoverFlow cf;
	View view;
	private ArrayList<VideoDetailBean> list = new ArrayList<VideoDetailBean>();
	private ImageAdapter adapter;
	private GridView gv_main;

	private int[] back_image = { R.drawable.spring_main,
			R.drawable.summer_main, R.drawable.autunm_main,
			R.drawable.winter_main };
	private int type;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_videolist_flow, null);
		type = (int) getArguments().getInt("module");

		// FancyCoverFlow fancyCoverFlow = (FancyCoverFlow) view
		// .findViewById(R.id.fancyCoverFlow);
		// fancyCoverFlow.setReflectionEnabled(true);
		// fancyCoverFlow.setReflectionRatio(0.3f);
		// fancyCoverFlow.setReflectionGap(0);
		if (list.size() == 0)
			for (int i = 0; i < 8; i++) {
				VideoDetailBean bean = new VideoDetailBean();
				bean.seturl("http://img.tupianzj.com/uploads/allimg/141113/3-141113114414.jpg");
				bean.setid(i + "");
				list.add(bean);
			}

		initView();
		//
		// fancyCoverFlow.setAdapter(new ViewGroupExampleAdapter(getActivity(),
		// list));
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

	private void initView() {
		rl_main = (RelativeLayout) view.findViewById(R.id.rl_back);
		rl_main.setBackgroundResource(back_image[type]);
		gv_main = (GridView) view.findViewById(R.id.mainPageGridview);
		adapter = new ImageAdapter(getActivity(), list);
		gv_main.setAdapter(adapter);

	}

}
