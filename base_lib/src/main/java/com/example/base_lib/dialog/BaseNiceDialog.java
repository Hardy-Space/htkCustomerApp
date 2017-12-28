package com.example.base_lib.dialog;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.base_lib.R;
import com.example.base_lib.utils.Utils;

/**
 * Created by Administrator on 2017/9/22.
 */

public abstract class BaseNiceDialog extends DialogFragment {

    private static final String MARGIN = "margin";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String DIM = "dim_amount";
    private static final String BOTTOM = "show_bottom";
    private static final String CANCEL = "out_cancel";
    private static final String ANIM = "anim_style";
    private static final String LAYOUT = "layout_id";

    private int margin;//左右边距
    private int width;//宽度
    private int height;//高度
    private float dimAmount = 0.5f;//灰度深浅
    private boolean showBottom;//是否底部显示
    private boolean outCancel = true;//是否点击外部取消
    @StyleRes
    private int animStyle;

    @LayoutRes
    protected int layoutId;

    /**
     * 设置布局
     * @return 布局资源
     */
    public abstract int iniLayoutId();

    /**
     * UI操作
     */
    public abstract void convertView(ViewHolder holder , BaseNiceDialog dialog);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE , R.style.NiceDialog);

        layoutId = iniLayoutId();

        //恢复保存的数据
        if (savedInstanceState != null) {
            margin = savedInstanceState.getInt(MARGIN);
            width = savedInstanceState.getInt(WIDTH);
            height = savedInstanceState.getInt(HEIGHT);
            dimAmount = savedInstanceState.getFloat(DIM);
            showBottom = savedInstanceState.getBoolean(BOTTOM);
            outCancel = savedInstanceState.getBoolean(CANCEL);
            animStyle = savedInstanceState.getInt(ANIM);
            layoutId = savedInstanceState.getInt(LAYOUT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutId, container, false);
        convertView(ViewHolder.create(view) , this);
        return view;
    }

    /**
     * 屏幕旋转等导致DialogFragment销毁后重建时保存数据
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MARGIN, margin);
        outState.putInt(WIDTH, width);
        outState.putInt(HEIGHT, height);
        outState.putFloat(DIM, dimAmount);
        outState.putBoolean(BOTTOM, showBottom);
        outState.putBoolean(CANCEL, outCancel);
        outState.putInt(ANIM, animStyle);
        outState.putInt(LAYOUT, layoutId);
    }

    @Override
    public void onStart() {
        super.onStart();
        initParams();
    }

    private void initParams() {
        Window window = getDialog().getWindow();
        if (window != null){
            WindowManager.LayoutParams attributes = window.getAttributes();
            //设置背景透明度[0-1]，默认0.5f
            attributes.dimAmount = dimAmount;
            //是否在底部显示
            if (showBottom){
                attributes.gravity = Gravity.BOTTOM;
                if (animStyle == 0){
                    animStyle = R.style.DefaultAnimation;
                }
            }

            //设置宽度
            if (width == 0){
                attributes.width = Utils.getScreenWidth(getContext()) - 2 * Utils.dp2px(getContext() , margin);
            }else if (width == -1){
                attributes.width = WindowManager.LayoutParams.WRAP_CONTENT;
            }else {
                attributes.width = Utils.dp2px(getContext() , width);
            }

            //设置高度
            if (height == 0){
                attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
            }else {
                attributes.height = Utils.dp2px(getContext() , height);
            }

            //设置进出动画
            window.setWindowAnimations(animStyle);
            window.setAttributes(attributes);
        }
        setCancelable(outCancel);
    }

    public BaseNiceDialog setMargin(int margin) {
        this.margin = margin;
        return this;
    }

    public BaseNiceDialog setWidth(int width) {
        this.width = width;
        return this;
    }

    public BaseNiceDialog setHeight(int height) {
        this.height = height;
        return this;
    }

    public BaseNiceDialog setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    public BaseNiceDialog setShowBottom(boolean showBottom) {
        this.showBottom = showBottom;
        return this;
    }

    public BaseNiceDialog setOutCancel(boolean outCancel) {
        this.outCancel = outCancel;
        return this;
    }

    public BaseNiceDialog setAnimStyle(int animStyle) {
        this.animStyle = animStyle;
        return this;
    }

    public BaseNiceDialog show(FragmentManager manager){
        super.show(manager , String.valueOf(System.currentTimeMillis()));
        return this;
    }
}
