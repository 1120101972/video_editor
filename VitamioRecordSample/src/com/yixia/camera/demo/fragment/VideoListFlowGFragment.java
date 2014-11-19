package com.yixia.camera.demo.fragment;

import java.util.ArrayList;

import com.yixia.camera.demo.R;
import com.yixia.camera.demo.bean.VideoDetailBean;
import com.yixia.camera.demo.ui.widget.coverflow.CoverFlow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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
		View view = inflater.inflate(R.layout.fragment_videolist_flow, null);
		// FancyCoverFlow fancyCoverFlow = (FancyCoverFlow) view
		// .findViewById(R.id.fancyCoverFlow);
		// fancyCoverFlow.setReflectionEnabled(true);
		// fancyCoverFlow.setReflectionRatio(0.3f);
		// fancyCoverFlow.setReflectionGap(0);
		// for (int i = 0; i < 5; i++) {
		// VideoDetailBean bean = new VideoDetailBean();
		// bean.seturl("http://img.tupianzj.com/uploads/allimg/141113/3-141113114414.jpg");
		// bean.setbrif("OK 真好看");
		// list.add(bean);
		// }
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

}
