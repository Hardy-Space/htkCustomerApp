package com.hl.htk_customer.hldc.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hl.htk_customer.R;
import com.hl.htk_customer.hldc.bean.SeatBean;

import java.util.List;


/**
 * Created by asus on 2017/12/11.
 */

public class SeatPopupWindow extends PopupWindow {
    /** popup窗口里的ListView */
    private ListView mChairLv;
    private View conentView;
    /** 数据 */
    private List<SeatBean> chairData = null;
    private Activity mAct;
    public SeatPopupWindow(Activity mcontext,AdapterView.OnItemClickListener listener,List<SeatBean> mList){
        mAct = mcontext;
        chairData = mList;
        LayoutInflater inflater = (LayoutInflater)mcontext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popupwindow_layout, null);
        mChairLv = conentView.findViewById(R.id.poplist);
        mChairLv.setOnItemClickListener(listener);
        int h = mcontext.getWindowManager().getDefaultDisplay().getHeight();
        int w = mcontext.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w/2-300);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
        } else {
            this.dismiss();
        }
    }

    public void update(){
        mChairLv.setAdapter(new SeatAdapter());
    }

    class SeatAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return chairData.size();
        }

        @Override
        public Object getItem(int position) {
            return chairData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mHolder = null;
            if(mHolder == null){
                mHolder = new ViewHolder();
                convertView = LayoutInflater.from(mAct).inflate(R.layout.popup_listitem_view, null);
                mHolder.tvName = convertView.findViewById(R.id.tvseatname);
                convertView.setTag(mHolder);
            }else{
                mHolder = (ViewHolder) convertView.getTag();
            }
            mHolder.tvName.setText(""+chairData.get(position).getSeatName());
            return convertView;
        }

        class ViewHolder{
            public TextView tvName;
        }

    }
}
