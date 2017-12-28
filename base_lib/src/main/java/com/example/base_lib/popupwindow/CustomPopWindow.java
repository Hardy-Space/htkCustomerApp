package com.example.base_lib.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.AnimatorRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2017/10/9.
 */

public class CustomPopWindow extends PopupWindow {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private int resLayoutId;
    private int width = -1;
    private int height = -1;
    private int animationStyle = -1;
    private boolean focusable = true;
    private boolean outsideTouchable = true;

    public CustomPopWindow(Context mContext) {
        this.mContext = mContext;
    }

    public void build(){
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resLayoutId, null);
        setContentView(view);

        if (height != -1 && width != -0){
            setHeight(height);
            setWidth(width);
        }else {
            setWidth(ViewGroup.LayoutParams.WRAP_CONTENT );
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        if (animationStyle != -1){
            setAnimationStyle(animationStyle);
        }

        this.setFocusable(focusable);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setOutsideTouchable(outsideTouchable);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClick();
            }
        });

    }

    public CustomPopWindow showAtLocation(View view){
        super.showAsDropDown(view);
        return this;
    }

    public static class CustomPopWindowBuilder{

        private CustomPopWindow mCustomPopWindow;

        public CustomPopWindowBuilder(Context context) {
            if (mCustomPopWindow == null)
                mCustomPopWindow = new CustomPopWindow(context);
        }

        public CustomPopWindow create(){
            mCustomPopWindow.build();
            return mCustomPopWindow;
        }

        public CustomPopWindowBuilder setLayoutId(@LayoutRes int layoutId){
            mCustomPopWindow.resLayoutId = layoutId;
            return this;
        }

        public CustomPopWindowBuilder setHeight(int height){
            mCustomPopWindow.height = height;
            return this;
        }

        public CustomPopWindowBuilder setWidth(int width){
            mCustomPopWindow.width = width;
            return this;
        }

        public CustomPopWindowBuilder setAnimationStyle(@AnimatorRes int animation){
            mCustomPopWindow.animationStyle = animation;
            return this;
        }

        public CustomPopWindowBuilder setFocusable(boolean focusable){
            mCustomPopWindow.focusable = focusable;
            return this;
        }

        public CustomPopWindowBuilder setOutsideTouchable(boolean outsideTouchable){
            mCustomPopWindow.outsideTouchable = outsideTouchable;
            return this;
        }

    }

    private OnClick onClick;

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public interface OnClick{
        void onClick();
    }
}
