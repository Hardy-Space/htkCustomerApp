package com.hl.htk_customer.fragment.dingdan;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/4.
 */

public class OrderSeatListFragment extends BaseFragment {

    private boolean isPrepared = false;
    private boolean isFirst = true;
    private View view = null;

    @Bind(R.id.listView)
    PullToRefreshListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_order_seat, null);
            isPrepared = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lazyInitData();
                }
            }, 200);
        }
        ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void lazyInitData() {
        if (isVisible && isFirst && isPrepared) {
            isFirst = false;

            //  initWidget();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
