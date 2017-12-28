package com.hl.htk_customer.dialog;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hl.htk_customer.R;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */

public class PopupWindowStringList extends BasePopupWindowForListView<String> {

    private ListView mList;

    public PopupWindowStringList(View contentView , int width, int height, List<String> datas) {
        super(contentView , width , height, true , datas);
    }

    @Override
    public void initViews() {
        mList = (ListView) findViewById(R.id.pop_list);
        mList.setAdapter(new PopAdapter() );
    }

    @Override
    public void initEvents() {
//        mContentView.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                if (i == KeyEvent.KEYCODE_BACK) {
//                    dismiss();
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        // 点击其他地方消失
//        mContentView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                dismiss();
//                return false;
//            }
//        });
    }

    @Override
    public void init() {
        setAnimationStyle(R.style.PopupWindowAnim);
    }

    /**
     半透明背景出现的动画
     */
    private void propertyAnim(ImageView iv) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv, "alpha", 0, 0.2f, 0.5f, 0.7f, 0.9f, 1);
        animator.setDuration(200);
        animator.setRepeatCount(0);
        animator.start();
        iv.setVisibility(View.VISIBLE);
    }

    /**
     * 半透明背景消失的动画
     * @param iv
     */
    public static void propertyAnim2(final ImageView iv){
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv,"alpha",1,0.9f,0.7f,0.5f,0.2f,0);
        animator.setDuration(200);
        animator.setRepeatCount(0);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                iv.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    class PopAdapter extends BaseAdapter{

        private TextView tv;

        @Override
        public int getCount() {
            return mDatas == null ? 0 : mDatas.size();
        }

        @Override
        public Object getItem(int i) {
            return mDatas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (view == null){
                view = LayoutInflater.from(context).inflate(R.layout.item_pop_list , null);
                tv = view.findViewById(R.id.item_pop_list_tv);
                viewHolder = new ViewHolder();
                view.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) view.getTag();
            }

            tv.setText(mDatas.get(i));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(i);
                }
            });

            return view;
        }
    }

    class ViewHolder{
        TextView tv;
    }
    private onItemClickListener onItemClickListener;

    public void setOnItemClickListener(PopupWindowStringList.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener{
        void onItemClick(int position);
    }
}
