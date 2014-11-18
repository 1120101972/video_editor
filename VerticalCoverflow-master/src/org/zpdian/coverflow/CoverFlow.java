
package org.zpdian.coverflow;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.ImageView;

public class CoverFlow extends VerticalGallery {

    private Camera mCamera = new Camera();

    private int mMaxRotationAngle = 70;

    private int mMaxZoom = -500;

    private int mCoveflowCenter;

    private boolean mAlphaMode = true;

    private boolean mCircleMode = true;
    
    private GestureDetector mGestureDetector;
    
    public CoverFlow(Context context) {
        super(context);
    }

    public CoverFlow(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setStaticTransformationsEnabled(true);
        this.setSpacing(-20);
//        this.setSelection(10);
        setChildrenDrawingOrderEnabled(true);
        mGestureDetector = new GestureDetector(this);
    }

    public CoverFlow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public int getMaxRotationAngle() {
        return mMaxRotationAngle;
    }

    public void setMaxRotationAngle(int maxRotationAngle) {
        mMaxRotationAngle = maxRotationAngle;
    }

    public boolean getCircleMode() {
        return mCircleMode;
    }

    public void setCircleMode(boolean isCircle) {
        mCircleMode = isCircle;
    }

    public boolean getAlphaMode() {
        return mAlphaMode;
    }

    public void setAlphaMode(boolean isAlpha) {
        mAlphaMode = isAlpha;
    }

    public int getMaxZoom() {
        return mMaxZoom;
    }

    public void setMaxZoom(int maxZoom) {
        mMaxZoom = maxZoom;
    }

    private int getCenterOfCoverflow() {
        return (getHeight() - getPaddingTop() - getPaddingBottom()) / 2 + getPaddingTop();
    }

    private static int getCenterOfView(View view) {
        return view.getTop() + view.getHeight() / 2;
    }
    
    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {

        final int childCenter = getCenterOfView(child);
        float rotationAngle = 0;
        t.clear();
        t.setTransformationType(Transformation.TYPE_MATRIX);
        if (childCenter == mCoveflowCenter) {
            transformImageBitmap((ImageView) child, t, 0, 0);
        } else {
            rotationAngle = (((float) (mCoveflowCenter - childCenter) / 170) * mMaxRotationAngle);
            transformImageBitmap((ImageView) child, t, rotationAngle, 0);
        }
        return true;
    }

    
    
    /**
     * This is called during layout when the size of this view has changed. If
     * you were just added to the view hierarchy, you're called with the old
     * values of 0.
     * 
     * @param w Current width of this view.
     * @param h Current height of this view.
     * @param oldw Old width of this view.
     * @param oldh Old height of this view.
     */
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCoveflowCenter = getCenterOfCoverflow();
        super.onSizeChanged(w, h, oldw, oldh);
    }
    
    /**
     * Transform the Image Bitmap by the Angle passed
     * 
     * @param imageView ImageView the ImageView whose bitmap we want to rotate
     * @param t transformation
     * @param rotationAngle the Angle by which to rotate the Bitmap
     */
    private void transformImageBitmap(ImageView child, Transformation t, float rotationAngle, int d) {
//        child.setVisibility(View.VISIBLE);
        mCamera.save();
        final Matrix imageMatrix = t.getMatrix();
        final int imageHeight = child.getLayoutParams().height;
        final int imageWidth = child.getLayoutParams().width;
        final int rotation = Math.abs((int) rotationAngle);
        float zoomAmount = (float) (mMaxZoom + (rotation * 2.5) + 100);
//        ((ImageView) (child)).setAlpha(255);
        if (mAlphaMode) {
        Log.d("myTag","rotationAngle  ================>>>>>>>>>>>>>>>> " + rotationAngle + " " + imageHeight);
        if (rotationAngle > 0) { 
            int alpha = (int) (255 - rotation * 2.5);
            if (alpha > 120) {
                ((ImageView) (child)).setAlpha(alpha);
            } else {
                ((ImageView) (child)).setAlpha(0);
            }
        } else if (rotationAngle < 0) {
            int alpha = (int) (255 - rotation * 2.5);
            if (alpha > 120) {
                ((ImageView) (child)).setAlpha(alpha);
            } else {
                ((ImageView) (child)).setAlpha(0);
            }
        }
        mCamera.translate(0.0f, -(float) (rotationAngle * rotation * rotation * 0.0001), zoomAmount);
        }
        mCamera.getMatrix(imageMatrix);
        imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2.0f));
        imageMatrix.postTranslate((imageWidth / 2), (imageHeight / 2.0f));
        mCamera.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("CovermyTag","CoverFlow onTouchEvent_event ===== " + event.getAction() + super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
//        View position = this.getSelectedView();
//        position.setBackgroundColor(0xFF00FF00);
        Log.d("myTag","e.x = + e.y = " + e.getX() + " " + e.getY());
        return super.onSingleTapUp(e);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("myTag","onFling");
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return super.onDown(e);
    }

    @Override
    public void onLongPress(MotionEvent e) {
        super.onLongPress(e);
    }

    @Override
    public void onShowPress(MotionEvent e) {
        super.onShowPress(e);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
    	 Log.d("CovermyTag","CoverFlow onInterceptTouchEvent ===== " + ev.getAction() + " " + super.onTouchEvent(ev));
        return super.onTouchEvent(ev);
    }
    
    
}
