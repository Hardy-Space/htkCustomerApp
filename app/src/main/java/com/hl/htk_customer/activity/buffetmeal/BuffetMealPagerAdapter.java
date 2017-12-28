package com.hl.htk_customer.activity.buffetmeal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hl.htk_customer.entity.DianCanFenLeiEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 *
 */

public class BuffetMealPagerAdapter extends FragmentPagerAdapter {

    private List<String> titles;
    private List<Integer> shopIDs;

    public BuffetMealPagerAdapter(FragmentManager fm , List<DianCanFenLeiEntity.DataBean> datas ) {
        super(fm);
        titles = new ArrayList<>();
        shopIDs = new ArrayList<>();
        for (DianCanFenLeiEntity.DataBean data : datas) {
            titles.add(data.getCategoryName());
            shopIDs.add(data.getId());
        }
    }

    @Override
    public Fragment getItem(int position) {
        return BuffetMealFragment.newInstance(shopIDs.get(position));
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

}
