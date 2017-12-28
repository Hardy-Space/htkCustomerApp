package com.hl.htk_customer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.hl.htk_customer.base.BaseFragment;

import java.util.List;


/**
 * 作者 龙威陶
 * 说明
 * 创建时间 2016/9/26
 * 公司名称 百迅科技
 * 描述
 */
public class FragmentAdapter extends FragmentPagerAdapter {

      List<String> titles;
      List<BaseFragment> fragmentList;


      public FragmentAdapter(FragmentManager fm, List<String> titles, List<BaseFragment> fragmentList) {
            super(fm);
            this.titles = titles;
            this.fragmentList = fragmentList;
      }

      @Override
      public Fragment getItem(int position) {
            return fragmentList.get(position);
      }

      @Override
      public int getCount() {
            return fragmentList.size();
      }

      @Override
      public CharSequence getPageTitle(int position) {
            return titles.get(position);
      }
}
