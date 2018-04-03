package com.hl.htk_customer.hldc.main;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hl.htk_customer.R;
import com.hl.htk_customer.hldc.base.BaseViewpager;
import com.hl.htk_customer.hldc.bean.CategoryBean;
import com.hl.htk_customer.hldc.bean.SeatBean;
import com.hl.htk_customer.hldc.http.HttpHelper;
import com.hl.htk_customer.hldc.http.JsonHandler;
import com.hl.htk_customer.hldc.pager.Tuijianpager;
import com.hl.htk_customer.hldc.utils.PreferencesUtils;
import com.hl.htk_customer.hldc.utils.ToolUtils;
import com.hl.htk_customer.hldc.view.SeatPopupWindow;
import com.hl.htk_customer.utils.MPHUtils;
import com.hl.htk_customer.utils.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by asus on 2017/10/25.author lu
 */

public class DianCaiFragment extends BaseFragment implements OnClickListener {
    public static final String TAG = DianCaiFragment.class.getSimpleName();
    private TextView tv_leftstate, tv_title;
    private ImageView img_lefticon, img_righticon;
    private ViewPager pager;
    private TabLayout tab;
    private View mView;

    private ArrayList<BaseViewpager> pagerList = new ArrayList<>();
    private List<String> groupList = new ArrayList<>();
    private List<CategoryBean> myList = new ArrayList<>();

    private static final int GET_PAGER_LIST = 1;

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d(TAG, "groupList.size() == >>>" + groupList.size());
                    for (int i = 0; i < groupList.size(); i++) {
                        pagerList.add(new Tuijianpager(getActivity(), Integer.valueOf(myList.get(i).getId())));
                    }
                    initsetAdapter();
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = View.inflate(getActivity(), R.layout.layout_diancai, null);
        initView();
        getCategoryList();
        return mView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //        if (!hidden) {
        //            //            Toast.makeText(mActivity, "show", Toast.LENGTH_SHORT).show();
        //            getCategoryList();
        //        } else {
        //            //            Toast.makeText(mActivity, "hide", Toast.LENGTH_SHORT).show();
        //        }
    }

    //    @Override
    //    public void onResume() {
    //        super.onResume();
    //        getCategoryList();
    //    }

    private void initsetAdapter() {
        myPagerAdapter myPagerAdapter = new myPagerAdapter();
        pager.setAdapter(myPagerAdapter);
        myPagerAdapter.notifyDataSetChanged();
        tab.setupWithViewPager(pager);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void initView() {
        pager = mView.findViewById(R.id.viewPager);
        tab = mView.findViewById(R.id.tabLayout);
        tv_leftstate = mView.findViewById(R.id.tv_leftstate);
        tv_title = mView.findViewById(R.id.tv_common_title);
        img_lefticon = mView.findViewById(R.id.img_lefticon);
        img_righticon = mView.findViewById(R.id.img_righticon);
        tv_title.setText("点菜");
        tv_leftstate.setText("默认");
        img_lefticon.setImageResource(R.drawable.icon_location);
        img_righticon.setImageResource(R.mipmap.search);
        tv_leftstate.setOnClickListener(this);
        getPositionList();
    }

    private String zhuoNo;
    /**
     * popup窗口
     */
    private SeatPopupWindow typeSelectPopup;
    /**
     * 数据
     */
    private List<SeatBean> chairData = new ArrayList<>();

    private void initPopupWindow() {
        if (chairData == null || chairData.size() <= 0) {
            Toast.makeText(getContext(), "获取座位列表失败", Toast.LENGTH_SHORT).show();
            return;
        } else {
            //            Toast.makeText(ComfirmOrderActivity.this,"chairData.size()==>>>"+chairData.size(),Toast.LENGTH_SHORT).show();
        }
        typeSelectPopup = new SeatPopupWindow(mActivity, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                typeSelectPopup.showPopupWindow(tv_leftstate);
                zhuoNo = chairData.get(position).getSeatName();
                tv_leftstate.setText(zhuoNo + "");
                PreferencesUtils.putString(getActivity(), "zhuoNo", "" + zhuoNo);
            }
        }, chairData);
        typeSelectPopup.showPopupWindow(tv_leftstate);
    }

    private List<SeatBean> seatList = new ArrayList<>();
    private int shopId = 0;

    private void getPositionList() {
        shopId = PreferencesUtils.getInt(getContext(), "shopId");

        final Dialog loading = MPHUtils.createLoadingDialog(getActivity(), "");
        loading.show();

        HttpHelper.getInstance().getSeatPosition(getContext(), shopId, new JsonHandler<String>() {

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {

                loading.dismiss();

                Log.d(TAG, "getPositionList=>" + responseString);
                int state = ToolUtils.getNetBackCode(responseString);
                String result = ToolUtils.getJsonParseResult(responseString);
                if (state == 100) {
                    JSONArray mArr;
                    try {
                        mArr = new JSONArray(result);
                        for (int i = 0; i < mArr.length(); i++) {
                            JSONObject obj = mArr.getJSONObject(i);
                            SeatBean seatBean = new SeatBean();
                            seatBean.setNumberSeat(obj.getInt("numberSeat"));
                            seatBean.setSeatName(obj.getString("seatName"));
                            seatBean.setShopId(obj.getInt("shopId"));
                            seatList.add(seatBean);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (chairData != null && chairData.size() > 0) {
                        chairData.clear();
                    }
                    chairData.addAll(seatList);
                    if (chairData != null && chairData.size() > 0)
                        tv_leftstate.setText(chairData.get(0).getSeatName());
                    PreferencesUtils.putString(getActivity(), "zhuoNo", "" + zhuoNo);
                } else {
                    Toast.makeText(getContext(), "Loading Failed...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                loading.dismiss();
                Toast.makeText(getContext(), "获取座位列表失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }

        });
    }

    class myPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return groupList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pagerList.get(position).view);
            pagerList.get(position).initData();
            return pagerList.get(position).view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return groupList.get(position);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_righticon:
                Toast.makeText(getActivity(), "点击进入查询界面", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_leftstate:
                initPopupWindow();
                break;
        }
    }

    private void getCategoryList() {

        final Dialog loading = MPHUtils.createLoadingDialog(getActivity(), "");
        loading.show();

        HttpHelper.getInstance().getCategoryList(getActivity(), PreferencesUtils.getInt(getActivity(), "shopId"), new JsonHandler<String>() {

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
                loading.dismiss();
                Log.d(TAG, "onSuccess=statusCode>" + statusCode + "<<==responseString==>>" + responseString);
                try {
                    JSONObject  object = new JSONObject(responseString);
                    int state = object.optInt("code");
                    if (state==30){
                        Toast.makeText(MyApplication.getContext(), "您扫描的商家已过有效期,请联系商家", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                        return;
                    }
                    convertStringToList(ToolUtils.getJsonParseResult(responseString));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String rawJsonData, Object errorResponse) {
                loading.dismiss();
                Log.d(TAG, "onFailure=statusCode>" + statusCode);
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    private void convertStringToList(String result) {
        myList.clear();
        groupList.clear();
        pagerList.clear();
        JSONArray obj = null;
        try {
            obj = new JSONArray(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (obj.length() > 0) {
            for (int i = 0; i < obj.length(); i++) {
                CategoryBean bean = new CategoryBean();
                try {
                    bean.setId(obj.getJSONObject(i).getString("id"));
                    bean.setCategoryName(obj.getJSONObject(i).getString("categoryName"));
                    bean.setShopId(obj.getJSONObject(i).getInt("shopId"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                myList.add(bean);
            }
            for (int i = 0; i < myList.size(); i++) {
                groupList.add(myList.get(i).getCategoryName());
            }
            Log.d(TAG, "groupList.size()==>>>" + groupList.size());
            Log.d(TAG, "myList.size()==>>>" + myList.size());
            if (myList.size() > 0) {
                myHandler.sendEmptyMessage(GET_PAGER_LIST);
            }
        }
    }

}
