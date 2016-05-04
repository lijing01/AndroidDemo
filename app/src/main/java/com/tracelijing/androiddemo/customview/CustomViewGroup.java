package com.tracelijing.androiddemo.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Trace (Tapatalk) on 2016/4/26.
 * http://my.oschina.net/fengheju/blog/196266
 * http://my.oschina.net/fengheju/blog/196455
 */
public class CustomViewGroup extends ViewGroup {


	public CustomViewGroup(Context context) {
		super(context);
	}

	public CustomViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
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
				MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
				left += lp.leftMargin;
				final int childWidth = view.getMeasuredWidth();
				final int childHeight = view.getMeasuredHeight();
				view.layout(left, top+lp.topMargin, left + childWidth, top + childHeight+lp.bottomMargin);
				left += childWidth + lp.rightMargin;
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

				MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
				//将measureChild改为measureChildWithMargin
				measureChildWithMargins(childView, widthMeasureSpec, 0,
						heightMeasureSpec, 0);
				//这里在计算宽度时加上margin
				desireWidth += childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
				desireHeight = Math
						.max(desireHeight, childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
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

	public MarginLayoutParams generateLayoutParams(AttributeSet attrs)
	{
		return new MarginLayoutParams(getContext(), attrs);
	}


	public static class LayoutParams extends MarginLayoutParams {
		public int gravity = -1;

		public LayoutParams(Context c, AttributeSet attrs) {
			super(c, attrs);

//			TypedArray ta = c.obtainStyledAttributes(attrs,
//						R.styleable.SlideGroup);
//
//			gravity = ta.getInt(R.styleable.SlideGroup_layout_gravity, -1);
//
//			ta.recycle();
		}

		public LayoutParams(int width, int height) {
			this(width, height, -1);
		}

		public LayoutParams(int width, int height, int gravity) {
			super(width, height);
			this.gravity = gravity;
		}

		public LayoutParams(android.view.ViewGroup.LayoutParams source) {
			super(source);
		}

		public LayoutParams(MarginLayoutParams source) {
			super(source);
		}
	}


}
