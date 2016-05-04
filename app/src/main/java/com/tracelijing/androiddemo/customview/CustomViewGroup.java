package com.tracelijing.androiddemo.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.tracelijing.androiddemo.R;

/**
 * Created by Trace (Tapatalk) on 2016/4/26.
 */
public class CustomViewGroup extends ViewGroup {
	public Orientation mOrientation;

	public CustomViewGroup(Context context) {
		super(context);
	}

	public CustomViewGroup(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.CustomViewGroup);
		int oValue = ta.getInt(R.styleable.CustomViewGroup_orientation, 0);
		Log.v("trace","oValue is "+ oValue);
		mOrientation = Orientation.valueOf(oValue);
		ta.recycle();
	}


	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int parentLeft = getPaddingLeft();
		int parentTop = getPaddingTop();

		int left = parentLeft;
		int top = parentTop;

		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View view = getChildAt(i);
			if (view.getVisibility() != View.GONE) {
				LayoutParams lp = (LayoutParams) view.getLayoutParams();
				final int childWidth = view.getMeasuredWidth();
				final int childHeight = view.getMeasuredHeight();
				if(mOrientation == Orientation.HORIZONTAL) {
					left += lp.leftMargin;
					top  = parentTop + lp.topMargin;
					view.layout(left, top, left + childWidth, top + childHeight);
					left += childWidth + lp.rightMargin;
				}else{
					left = parentLeft + lp.leftMargin;
					top += lp.topMargin;
					view.layout(left, top, left + childWidth, top + childHeight);
					top += childHeight + lp.bottomMargin;
				}
			}
		}
	}

	/**
	 * 水平方向viewGroup
	 **/
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int desireWidth = 0, desireHeight = 0;
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View childView = getChildAt(i);
			if (childView.getVisibility() != View.GONE) {

				LayoutParams lp = (LayoutParams) childView.getLayoutParams();
				//将measureChild改为measureChildWithMargin
				measureChildWithMargins(childView, widthMeasureSpec, 0,
						heightMeasureSpec, 0);
				if(mOrientation == Orientation.HORIZONTAL) {
					//这里在计算宽度时加上margin
					desireWidth += childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
					desireHeight = Math
							.max(desireHeight, childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
				}else{
					desireHeight += childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
					desireWidth = Math
							.max(desireWidth, childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
				}
			}
		}
		desireWidth += getPaddingLeft() + getPaddingRight();
		desireHeight += getPaddingTop() + getPaddingBottom();

		// see if the size is big enough
		desireWidth = Math.max(desireWidth, getSuggestedMinimumWidth());
		desireHeight = Math.max(desireHeight, getSuggestedMinimumHeight());

		setMeasuredDimension(resolveSize(desireWidth, widthMeasureSpec),
				resolveSize(desireHeight, heightMeasureSpec));
	}

//	public MarginLayoutParams generateLayoutParams(AttributeSet attrs)
//	{
//		return new MarginLayoutParams(getContext(), attrs);
//	}

	@Override
	public MarginLayoutParams generateLayoutParams(AttributeSet attrs)
	{
		return new LayoutParams(getContext(), attrs);
	}


	public static class LayoutParams extends MarginLayoutParams {

		public LayoutParams(Context c, AttributeSet attrs) {
			super(c, attrs);
		}

	}

	public static enum Orientation {
		HORIZONTAL(0), VERTICAL(1);

		private int value;

		private Orientation(int i) {
			value = i;
		}

		public int value() {
			return value;
		}

		public static Orientation valueOf(int i) {
			switch (i) {
				case 0:
					return HORIZONTAL;
				case 1:
					return VERTICAL;
				default:
					throw new RuntimeException("[0->HORIZONTAL, 1->VERTICAL]");
			}
		}
	}

}
