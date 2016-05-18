package com.tracelijing.androiddemo.customview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Trace (Tapatalk) on 2016/5/18.
 */
public class MultiViewGroup extends ViewGroup {
	private Context mContext;
	private int viewWidth,viewHeight;

	public MultiViewGroup(Context context) {
		this(context,null);
	}

	public MultiViewGroup(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public MultiViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.mContext = context;
		init();
	}

	private void init(){
		LinearLayout linearLayout1 = new LinearLayout(mContext);
		linearLayout1.setBackgroundColor(Color.RED);
//		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		addView(linearLayout1,0);

		LinearLayout linearLayout2 = new LinearLayout(mContext);
		linearLayout2.setBackgroundColor(Color.BLUE);
		addView(linearLayout2,1);

		LinearLayout linearLayout3 = new LinearLayout(mContext);
		linearLayout2.setBackgroundColor(Color.BLACK);
		addView(linearLayout3,2);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		viewWidth = width;
		viewHeight = height;
		setMeasuredDimension(width, height);

		for(int i=0; i<getChildCount(); i++){
			View child = getChildAt(i);
			child.measure(width,height);
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int startLeft = 0;
		int startTop = 0;
		for(int i=0; i<getChildCount(); i++){
			View childView = getChildAt(i);
			childView.layout(startLeft,startTop,startLeft+viewWidth,startTop+viewHeight);
			startLeft = startLeft + viewWidth;
		}
	}


	public int getViewWidth() {
		return viewWidth;
	}

}
