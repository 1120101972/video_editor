package com.yixia.camera.demo.ui.widget;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import com.yixia.camera.demo.activity.FourModuleChooseActivity;
import com.yixia.camera.demo.utils.StringUtils;

import android.R.bool;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 随触摸旋转ImageView
 * 
 * @author liupoolo
 * @since 2013-02-22
 * @version 1.00
 */
public class TouchRoateImageView extends ImageView implements
		GestureDetector.OnGestureListener {

	private final static float MIN_DEGREE = 0f;
	private final static float MAX_DEGREE = 360f;

	private Matrix m;

	private float saveX; // 当前保存的x
	private float saveY; // 当前保存的y
	private float curTouchX; // 当前触屏的x
	private float curTouchY; // 当前触摸的y
	private float centerX; // 中心点x
	private float centerY; // 中心点y
	private float curDegree; // 当前角度
	private float changeDegree;
	private Context currentContext;
	RotateAnimation animate = null;
	private TouchRoateImageView current;
	private boolean if_animate = false;
	private GestureDetector mGestureDetector;
	private int Count = 0;

	public TouchRoateImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setScaleType(ScaleType.MATRIX);// 重点
		// DisplayMetrics dm = new DisplayMetrics();
		// ((Activity) context).getWindowManager().getDefaultDisplay()
		// .getMetrics(dm);
		// windowWidth = dm.widthPixels; // 得到宽度
		// windowHeight = dm.heightPixels; // 得到高度
		currentContext = context;
		current = this;
		mGestureDetector = new GestureDetector(context, this);
		mGestureDetector.setIsLongpressEnabled(true);

		m = new Matrix();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// centerX = (float) this.getWidth() / 2;
		// centerY = (float) this.getHeight() / 2;
		centerX = (float) this.getHeight() / 2;
		centerY = (float) this.getHeight() / 2;

		if (Count == 0) {
			((FourModuleChooseActivity) currentContext).setMargin(
					this.getWidth(), this.getHeight());
			Count++;
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		handleTouch(event);

		return mGestureDetector.onTouchEvent(event);
	}

	private void handleTouch(MotionEvent event) {
		curTouchX = event.getX();
		curTouchY = event.getY();

		Log.d("TouchRoateImageView", "X" + curTouchX + ";Y" + curTouchY);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			saveTouchPoint();
			break;
		case MotionEvent.ACTION_MOVE:
			handleTouchMove();
			break;
		case MotionEvent.ACTION_UP:
			// 可以使用访问者模式这里让访问者获得当前角度
			handleFinishTouch();
			break;
		}

		Log.d("TouchRoateImageView", "onTouchEvent" + event.getAction());
	}

	private void handleTouchMove() {
		changeDegree = (float) getActionDegrees(centerX, centerY, saveX, saveY,
				curTouchX, curTouchY);
		float tempDegree = (float) curDegree + changeDegree;
		// if (tempDegree >= MIN_DEGREE && tempDegree <= MAX_DEGREE) {
		optimize(tempDegree);// 优化变动
		m.setRotate(curDegree, centerX, centerY);
		setImageMatrix(m);// 此方法会 调用invalidate() 从而重绘界面
		// }

		saveTouchPoint();
	}

	/**
	 * 实现动画效果
	 */

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:

				if (flag == 0) {
					if (tempDegree > 5f) {
						tempDegree -= 5f;
					} else {
						if_animate = false;
						tempDegree = 0f;
						curDegree = 0f;
					}
				} else if (flag == 1) {
					if (tempDegree < 90f && (90f - tempDegree) > 5f) {
						tempDegree += 5f;
					} else {
						if_animate = false;
						tempDegree = 90f;
						curDegree = 90f;
					}
				} else if (flag == 2) {
					if (tempDegree < 135f && (tempDegree - 90f) > 5f) {
						tempDegree -= 5f;
					} else {
						if_animate = false;
						tempDegree = 90f;
						curDegree = 90f;
					}
				} else if (flag == 3) {
					if (tempDegree < 180f && (180f - tempDegree) > 5f) {
						tempDegree += 5f;
					} else {
						if_animate = false;
						tempDegree = 180f;
						curDegree = 180f;
					}
				} else if (flag == 4) {
					if (tempDegree < 225f && (tempDegree - 180f) > 5f) {
						tempDegree -= 5f;
					} else {
						if_animate = false;
						tempDegree = 180f;
						curDegree = 180f;
					}
				} else if (flag == 5) {
					if (tempDegree < 270f && (270f - tempDegree) > 5f) {
						tempDegree += 5f;
					} else {
						if_animate = false;
						tempDegree = 270f;
						curDegree = 270f;
					}
				} else if (flag == 6) {
					if (tempDegree < 315f && (tempDegree - 270f) > 5f) {
						tempDegree -= 5f;
					} else {
						if_animate = false;
						tempDegree = 270f;
						curDegree = 270f;
					}
				} else if (flag == 7) {
					if (tempDegree < 360f && (360f - tempDegree) > 5f) {
						tempDegree += 5f;
					} else {
						if_animate = false;
						tempDegree = 0f;
						curDegree = 0f;
					}
				}
				if (if_animate == true)
					new Thread(new FLingRunnable()).start();

				m.setRotate(tempDegree, centerX, centerY);
				setImageMatrix(m);// 此方法会 调用invalidate() 从而重绘界面

				break;
			}
			super.handleMessage(msg);
		}

	};

	private class FLingRunnable implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			Message message = Message.obtain();
			message.what = 1;
			handler.sendMessage(message);

		}

	}

	float tempDegree;
	private int flag = 0;

	/**
	 * 旋转完成后自定义动画旋转
	 */
	private void handleFinishTouch() {
		if_animate = true;
		changeDegree = (float) getActionDegrees(centerX, centerY, saveX, saveY,
				curTouchX, curTouchY);
		tempDegree = (float) curDegree + changeDegree;
		if (tempDegree >= 0f && tempDegree < 45f) {
			flag = 0;
			((FourModuleChooseActivity) currentContext).getFragment(0);
		} else if (tempDegree >= 45f && tempDegree < 90f) {
			flag = 1;
			((FourModuleChooseActivity) currentContext).getFragment(1);
		} else if (tempDegree >= 90f && tempDegree < 135f) {
			((FourModuleChooseActivity) currentContext).getFragment(1);
			flag = 2;
		} else if (tempDegree >= 135f && tempDegree < 180f) {
			((FourModuleChooseActivity) currentContext).getFragment(2);
			flag = 3;
		} else if (tempDegree >= 180f && tempDegree < 225f) {
			((FourModuleChooseActivity) currentContext).getFragment(2);
			flag = 4;
		} else if (tempDegree >= 225f && tempDegree < 270f) {
			((FourModuleChooseActivity) currentContext).getFragment(3);
			flag = 5;
		} else if (tempDegree >= 270f && tempDegree < 315f) {
			((FourModuleChooseActivity) currentContext).getFragment(3);
			flag = 6;
		} else {
			((FourModuleChooseActivity) currentContext).getFragment(0);
			flag = 7;
		}
		new Thread(new FLingRunnable()).start();
		saveTouchPoint();
	}

	private void optimize(float tempDegree) {
		if (tempDegree > MAX_DEGREE - 1) {
			curDegree = MAX_DEGREE - 360f;
		} else if (tempDegree < MIN_DEGREE + 1) {
			curDegree = MIN_DEGREE + 360f;
		} else {
			this.curDegree = tempDegree;
		}

	}

	private void saveTouchPoint() {
		saveX = curTouchX;
		saveY = curTouchY;

	}

	/**
	 * 获取两点到第三点的夹角。
	 * 
	 * @param x
	 * @param y
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	private int a = 0;

	private double getActionDegrees(float x, float y, float x1, float y1,
			float x2, float y2) {

		double a = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		double b = Math.sqrt((x - x2) * (x - x2) + (y - y2) * (y - y2));
		double c = Math.sqrt((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y));
		// 余弦定理
		double cosA = (b * b + c * c - a * a) / (2 * b * c);
		// 返回余弦值为指定数字的角度，Math函数为我们提供的方法
		double arcA = Math.acos(cosA);
		double degree = arcA * 180 / Math.PI;

		// 接下来我们要讨论正负值的关系了，也就是求出是顺时针还是逆时针。
		// 第1、2象限
		if (y1 < y && y2 < y) {
			if (x1 < x && x2 > x) {// 由2象限向1象限滑动
				return degree;
			}
			// 由1象限向2象限滑动
			else if (x1 >= x && x2 <= x) {
				return -degree;
			}
		}
		// 第3、4象限
		if (y1 > y && y2 > y) {
			// 由3象限向4象限滑动
			if (x1 < x && x2 > x) {
				return -degree;
			}
			// 由4象限向3象限滑动
			else if (x1 > x && x2 < x) {
				return degree;
			}

		}
		// 第2、3象限
		if (x1 < x && x2 < x) {
			// 由2象限向3象限滑动
			if (y1 < y && y2 > y) {
				return -degree;
			}
			// 由3象限向2象限滑动
			else if (y1 > y && y2 < y) {
				return degree;
			}
		}
		// 第1、4象限
		if (x1 > x && x2 > x) {
			// 由4向1滑动
			if (y1 > y && y2 < y) {
				return -degree;
			}
			// 由1向4滑动
			else if (y1 < y && y2 > y) {
				return degree;
			}
		}

		// 在特定的象限内
		float tanB = (y1 - y) / (x1 - x);
		float tanC = (y2 - y) / (x2 - x);
		if ((x1 > x && y1 > y && x2 > x && y2 > y && tanB > tanC)// 第一象限
				|| (x1 > x && y1 < y && x2 > x && y2 < y && tanB > tanC)// 第四象限
				|| (x1 < x && y1 < y && x2 < x && y2 < y && tanB > tanC)// 第三象限
				|| (x1 < x && y1 > y && x2 < x && y2 > y && tanB > tanC))// 第二象限
			return -degree;
		return degree;
	}

	public float getCurDegree() {
		return curDegree;
	}

	public void setCurDegree(float curDegree) {
		if (curDegree >= MIN_DEGREE && curDegree <= MAX_DEGREE) {
			this.curDegree = curDegree;
			m.setRotate(curDegree, centerX, centerY);
			setImageMatrix(m);
		}

	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}
