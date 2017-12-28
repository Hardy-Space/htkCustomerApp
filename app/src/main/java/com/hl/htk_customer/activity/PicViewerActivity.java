package com.hl.htk_customer.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.TuanGouShopPhotoEntity;
import com.hl.htk_customer.fragment.PictureSlideFragment;

import java.util.ArrayList;
import java.util.Collections;

/**Simple TouchGallery demo based on ViewPager and Photoview.
 * Created by Trojx on 2016/1/3.
 */
public class PicViewerActivity extends BaseActivity {

    private ViewPager viewPager;
    private TextView tv_indicator;
    private ArrayList<String> urlList;
    private Bundle bundle;
    private ArrayList<TuanGouShopPhotoEntity.DataBean> datas;
    private int position;

    private ImageView imgBack;
    private TextView title;
    private TextView titleRight;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_viewer);

        bundle = getIntent().getExtras();
        datas = (ArrayList<TuanGouShopPhotoEntity.DataBean>)bundle.get("datas");
        position = bundle.getInt("position");

        imgBack = (ImageView) findViewById(R.id.img_back);
        title = (TextView) findViewById(R.id.title);
        titleRight = (TextView) findViewById(R.id.title_right);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        title.setText("图片详情");

        urlList = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++)
        {
            urlList.add(datas.get(i).getImgUrl());
        }
//        String[] urls={"http://7xla0x.com1.z0.glb.clouddn.com/picJarvanIV_0.jpg",
//                "http://7xla0x.com1.z0.glb.clouddn.com/picJarvanIV_1.jpg",
//                "http://7xla0x.com1.z0.glb.clouddn.com/picJarvanIV_2.jpg",
//                "http://7xla0x.com1.z0.glb.clouddn.com/picJarvanIV_3.jpg",
//                "http://7xla0x.com1.z0.glb.clouddn.com/picJarvanIV_4.jpg",
//                "http://7xla0x.com1.z0.glb.clouddn.com/picJarvanIV_5.jpg",
//                "http://7xla0x.com1.z0.glb.clouddn.com/picJarvanIV_6.jpg",};
//
//
//        Collections.addAll(urlList, urls);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tv_indicator = (TextView) findViewById(R.id.tv_indicator);

        viewPager.setAdapter(new PictureSlidePagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tv_indicator.setText(String.valueOf(position+1)+"/"+datas.size());
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private  class PictureSlidePagerAdapter extends FragmentStatePagerAdapter {

        public PictureSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PictureSlideFragment.newInstance(urlList.get(position));
        }

        @Override
        public int getCount() {
            return urlList.size();
        }
    }
}
