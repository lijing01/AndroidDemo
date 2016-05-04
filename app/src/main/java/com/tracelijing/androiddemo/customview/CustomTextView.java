package com.tracelijing.androiddemo.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.tracelijing.androiddemo.R;

/**
 * Created by Trace (Tapatalk) on 2016/4/24.
 * 实现自定义view 类似于textview
 */
public class CustomTextView extends View {
	private String text;
	private int textSize;
	private int textColor;
	private int textBgColor;
	private Paint mPaint;
	private Rect mRect;

	public CustomTextView(Context context) {
		this(context, null);
	}

	public CustomTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTextView, defStyleAttr, 0);
		for (int i = 0; i < a.getIndexCount(); i++) {
			int arrt = a.getIndex(i);
			switch (arrt) {
				case R.styleable.CustomTextView_ttext:
					text = a.getString(arrt);
					break;
				case R.styleable.CustomTextView_ttextColor:
					textColor = a.getColor(arrt, Color.BLACK);
					break;
				case R.styleable.CustomTextView_ttextBgColor:
					textBgColor = a.getColor(arrt, Color.BLUE);
					break;
				case R.styleable.CustomTextView_ttextSize:
					textSize = a.getDimensionPixelSize(arrt, (int) TypedValue.applyDimension(
							TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));

					break;

			}
		}
		if(textSize == 0){
			textSize = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());
		}
		a.recycle();
		mPaint = new Paint();
		mPaint.setTextSize(textSize);
		mPaint.setColor(textColor);
		mRect = new Rect();
		mPaint.getTextBounds(text, 0, text.length(), mRect);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int width;
		int height;
		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else {
			mPaint.setTextSize(textSize);
			mPaint.getTextBounds(text, 0, text.length(), mRect);
			float textWidth = mRect.width();
			int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
			width = desired;
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			mPaint.setTextSize(textSize);
			mPaint.getTextBounds(text, 0, text.length(), mRect);
			float textHeight = mRect.height();
			int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
			height = desired;
		}
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setColor(textBgColor);
		canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
		mPaint.setColor(textColor);
		canvas.drawText(text, (getWidth() - mRect.width()) / 2, (getHeight() + mRect.height()) / 2, mPaint);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}
}
