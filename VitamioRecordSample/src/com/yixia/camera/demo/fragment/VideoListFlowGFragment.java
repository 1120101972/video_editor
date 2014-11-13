package com.yixia.camera.demo.fragment;

import com.yixia.camera.demo.R;
import com.yixia.camera.demo.adapter.CoverFlowAdapter;
import com.yixia.camera.demo.ui.widget.coverflow.CoverFlow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
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

	private CoverFlow flow;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_videolist_flow, null);
		flow = (CoverFlow) view.findViewById(R.id.cover_flow);
		CoverFlowAdapter adapter = new CoverFlowAdapter(getActivity());
		flow.setAdapter(adapter);
		flow.setGravity(Gravity.TOP);
		return view;
	}

}
