package com.hl.htk_customer.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.facebook.drawee.view.SimpleDraweeView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.ImageModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/1/6.
 */
public class MyBanner {
    ViewPager viewPager;
    TextView textView;
    List<ImageModel> list;
    Context context;
    //BxBitmap bitMap;

    public MyBanner(ViewPager viewPager, TextView textView, List<ImageModel> list, Context context) {
        this.viewPager = viewPager;
        this.textView = textView;
        this.list = list;
        this.context = context;
        //  bitMap = new BxBitmap();

    }

    public void initMyBanner() {


        List<View> vs = new ArrayList<View>();
        LayoutInflater inflater = LayoutInflater.from(context);
        for (int i = 0; i < list.size(); i++) {
            vs.add(inflater.inflate(R.layout.ui_banner, null));
            //ImageView img = (ImageView) vs.get(i).findViewById(R.id.ivBannera);
            SimpleDraweeView img = (SimpleDraweeView) vs.get(i).findViewById(R.id.ivBannera);

            // bitMap.display(img, list.get(i).getActivityimgAbb());
            img.setImageURI(Uri.parse(list.get(i).getUrl()));

            final int finalI = i;
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (list.size() != 0) {

                        //BxUtil.showMessage(context, "敬请期待");
                                 /*   Intent intent = new Intent(context, UiDesignLvMoreImageMore.class);

                                    for (int i = 0; i < list.size(); i++) {
                                          intent.putExtra(i + "", list.get(i).getActivityimgAbb());
                                    }
                                    intent.putExtra("num", list.size());
                                    intent.putExtra("position", finalI);
                                    context.startActivity(intent);*/
                    } else {


                    }

                }
            });


        }
        try {
            if (vs.size() > 0) {
                viewPager.setAdapter(new ada(vs));
            }
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(final int position, float positionOffset, int positionOffsetPixels) {
                    //   RadioButton tempButton = (RadioButton) radioGroup.findViewById(position);
                    //   tempButton.setChecked(true);
                    textView.setText(position + 1 + "/" + list.size());
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            Timer timer = new Timer();
            timer.schedule(tast, 1000, 4000);
        } catch (Exception e) {

        }

    }

    TimerTask tast = new TimerTask() {
        @Override
        public void run() {

            if (viewPager.getCurrentItem() + 1 == list.size()) {
                Message message = new Message();
                message.arg1 = 1000;
                handler.sendMessage(message);

            } else {
                Message message = new Message();
                message.arg1 = 2000;
                handler.sendMessage(message);
            }
        }
    };

    android.os.Handler handler = new android.os.Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 1000:
                    viewPager.setCurrentItem(0);
                    break;
                case 2000:
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    class ada extends PagerAdapter {
        private List<View> vs;

        public ada(List<View> vs) {
            super();
            this.vs = vs;
        }

        @Override
        public int getCount() {

            return vs.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(vs.get(position));
        }

        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(vs.get(position));
            return vs.get(position);
        }


    }
}
