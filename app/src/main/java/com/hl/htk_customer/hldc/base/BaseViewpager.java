package com.hl.htk_customer.hldc.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.hl.htk_customer.R;


/**
 * 五个viewpager的界面父类
 *
 */
public abstract class BaseViewpager {

	public Context mactivity;
	public FrameLayout flContainer;
	public View view;


	public BaseViewpager(Context activity) {
		this.mactivity = activity;
		view = initView();
	}

	public View initView() {
		View view = View.inflate(mactivity, R.layout.layout_base_pager, null);
		//tvTitle = (TextView) view.findViewById(R.id.tv_title);
		flContainer = (FrameLayout) view.findViewById(R.id.fl_container);
		return view;
	}
	public abstract void initData();

}
