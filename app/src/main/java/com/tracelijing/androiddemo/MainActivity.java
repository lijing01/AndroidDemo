package com.tracelijing.androiddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.tracelijing.androiddemo.customview.MultiViewGroup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

	private Button preBtn,nextBtn;
	private MultiViewGroup mMultiViewGroup;
	private int viewGroupWidth;
	private int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		preBtn = (Button) findViewById(R.id.pre);
		nextBtn = (Button) findViewById(R.id.next);
		mMultiViewGroup = (MultiViewGroup) findViewById(R.id.multi_group);
		//获得屏幕分辨率大小
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		viewGroupWidth =  metric.widthPixels ;
		preBtn.setOnClickListener(this);
		nextBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.pre:
				mMultiViewGroup.scrollTo(-viewGroupWidth,0);
				break;
			case R.id.next:
				mMultiViewGroup.scrollTo(viewGroupWidth,0);
				break;
		}
	}
}
