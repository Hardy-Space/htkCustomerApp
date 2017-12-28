package com.hl.htk_customer.dialog;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public abstract class BasePopupWindowForListView<T> extends PopupWindow {

    /**
     * 布局文件的最外层View
     */
    protected View mContentView;
    protected Context context;
    /**
     * ListView的数据集
     */
    protected List<T> mDatas;

    public BasePopupWindowForListView(View contentView, int width, int height, boolean focusable) {
        this(contentView, width, height, focusable , null);
    }

    public BasePopupWindowForListView(View contentView, int width, int height, boolean focusable , List<T> data) {
        super(contentView, width, height, focusable);
        this.mContentView = contentView;
        this.context = contentView.getContext();

        if (data != null)
            this.mDatas = data;

        setBackgroundDrawable(new BitmapDrawable());
        setTouchable(true);
        setOutsideTouchable(true);
        setTouchInterceptor(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        initViews();
        initEvents();
        init();
    }

    public abstract void initViews();

    public abstract void initEvents();

    public abstract void init();

    public View findViewById(int id)
    {
        return mContentView.findViewById(id);
    }

    protected static int dpToPx(Context context, int dp)
    {
        return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
    }
}
