package com.yixia.camera.demo.ui.widget.coverflow;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.yixia.camera.demo.R;
import com.yixia.camera.demo.ui.widget.coverflow.SlidingCard.OnPageChangeListener;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;



public class DiscoverContainerView extends RelativeLayout implements
		OnPageChangeListener {

	private Activity activity;

	private ConcurrentLinkedQueue<UserVo> dataList = new ConcurrentLinkedQueue<UserVo>();

	private ContainerInterface containerInterface;

	public static int TYPE_HEARTBEAT = 1;

	public static int TYPE_NOFEEL = -1;

	private int feelType = TYPE_HEARTBEAT;

	public DiscoverContainerView(Context context) {
		super(context);
	}

	public DiscoverContainerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void addToView(View child) {
		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
		addView(child, 0, layoutParams);
	}

	@Override
	public void addView(View child) {
		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
		addView(child, 0, layoutParams);
	}

	public void initCardView(Activity activity) {
		this.activity = activity;
		dataList.add(new UserVo());
		dataList.add(new UserVo());
		dataList.add(new UserVo());
		if (dataList != null && dataList.size() > 0) {
			for (int i = 1; i < 4; i++) {
				UserVo userVo = dataList.poll();
				if (userVo != null) {
					SlidingCard mSlidingCard = new SlidingCard(this.activity);
					mSlidingCard.setContent(R.layout.sliding_card_item);
					mSlidingCard.setUserVo(userVo);
					View contentView = mSlidingCard.getContentView();
					LayoutParams params = new LayoutParams(
							contentView.getLayoutParams());
					params.topMargin = (i - 1)
							* getResources().getDimensionPixelSize(
									R.dimen.card_item_margin);
					params.leftMargin = i
							* getResources().getDimensionPixelSize(
									R.dimen.card_item_margin);
					params.rightMargin = i
							* getResources().getDimensionPixelSize(
									R.dimen.card_item_margin);
					contentView.setLayoutParams(params);
					mSlidingCard.setListIndex(i);
					mSlidingCard
							.setSlidingMode(SlidingCard.SLIDINGMODE_LEFT_RIGHT);
					mSlidingCard.setCurrentItem(1, false);
					mSlidingCard.setOnPageChangeListener(this);
					addToView(mSlidingCard);
				}
			}
		}
	}

	public SlidingCard getCurrentView() {
		if (getChildCount() > 0) {
			return (SlidingCard) getChildAt(getChildCount() - 1);
		}
		return null;
	}

	public SlidingCard getNextView() {
		if (getChildCount() - 1 > 0) {
			return (SlidingCard) getChildAt(getChildCount() - 2);
		}
		return null;
	}

	@Override
	public void onPageScrolled(SlidingCard v, int position,
			float positionOffset, int positionOffsetPixels) {
		if (positionOffset == 0f) {
			positionOffset = 1f;
		}
		if (positionOffset > 0) {
			feelType = TYPE_NOFEEL;
		} else if (positionOffset < 0) {
			feelType = TYPE_HEARTBEAT;
		}
		if (v != null) {
			v.setUserImageShady(Math.abs(positionOffset), feelType);
		}
		SlidingCard slidingCard = getNextView();
		if (slidingCard != null) {
			if (Math.abs(positionOffsetPixels) != 0) {
				View contentView = slidingCard.getContentView();
				LayoutParams params = new LayoutParams(
						contentView.getLayoutParams());
				params.topMargin = (int) ((1 - Math.abs(positionOffset)) * getResources()
						.getDimensionPixelSize(R.dimen.card_item_margin));
				params.leftMargin = (int) ((2 - Math.abs(positionOffset)) * getResources()
						.getDimensionPixelSize(R.dimen.card_item_margin));
				params.rightMargin = (int) ((2 - Math.abs(positionOffset)) * getResources()
						.getDimensionPixelSize(R.dimen.card_item_margin));
				contentView.setLayoutParams(params);
				// slidingCard.setScaleX(Math.abs(positionOffset) / 10f + 0.9f);
				// slidingCard.setScaleY(Math.abs(positionOffset) / 20f +
				// 0.95f);
				// slidingCard.setScrollY((int)
				// (getResources().getDimensionPixelSize(
				// R.dimen.card_item_margin) * Math.abs(positionOffset)));
			}
		}
	}

	@Override
	public void onPageSelectedAfterAnimation(SlidingCard v, int prevPosition,
			int curPosition) {
		if (activity != null) {
			removeViewAt(getChildCount() - 1);
			UserVo userVo = dataList.poll();
			if (userVo != null) {
				SlidingCard mSlidingCard = new SlidingCard(activity);
				mSlidingCard.setContent(R.layout.sliding_card_item);
				mSlidingCard.setUserVo(userVo);
				View contentView = mSlidingCard.getContentView();
				LayoutParams params = new LayoutParams(
						contentView.getLayoutParams());
				params.topMargin = 2 * getResources().getDimensionPixelSize(
						R.dimen.card_item_margin);
				params.leftMargin = 2 * getResources().getDimensionPixelSize(
						R.dimen.card_item_margin);
				params.rightMargin = 2 * getResources().getDimensionPixelSize(
						R.dimen.card_item_margin);
				contentView.setLayoutParams(params);
				mSlidingCard.setSlidingMode(SlidingCard.SLIDINGMODE_LEFT_RIGHT);
				mSlidingCard.setCurrentItem(1, false);
				mSlidingCard.setOnPageChangeListener(this);
				addView(mSlidingCard);
			}
			if (containerInterface != null) {
				if (dataList.size() < 3) {
					containerInterface.loadMore();
				}
				containerInterface.onFeelOperat(userVo, feelType);
			}
			Log.e("test", "onPageSelectedAfterAnimation:" + curPosition + ","
					+ getChildCount());
		}
	}

	@Override
	public void onPageSelected(SlidingCard v, int prevPosition, int curPosition) {
		Log.e("test", "onPageSelected:" + curPosition);
	}

	@Override
	public void onPageScrollStateChanged(SlidingCard v, int state) {
		Log.e("test", "state change:" + state);
	}

	public ConcurrentLinkedQueue<UserVo> getDataList() {
		return dataList;
	}

	public void setDataList(ConcurrentLinkedQueue<UserVo> dataList) {
		this.dataList = dataList;
	}

	public interface ContainerInterface {
		public void loadMore();

		public void onFeelOperat(UserVo userVo, int feelType);

	}

	public ContainerInterface getContainerInterface() {
		return containerInterface;
	}

	public void setContainerInterface(ContainerInterface containerInterface) {
		this.containerInterface = containerInterface;
	}

}
