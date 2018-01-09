package com.hl.htk_customer.hldc.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hl.htk_customer.R;
import com.hl.htk_customer.hldc.bean.MineBean;
import com.hl.htk_customer.hldc.http.HttpHelper;
import com.hl.htk_customer.hldc.http.JsonHandler;
import com.hl.htk_customer.hldc.utils.ToolUtils;

import org.apache.http.Header;
import org.json.JSONObject;


/**
 * Created by asus on 2017/10/25.
 */

public class MyFragment extends BaseFragment implements OnClickListener{
    private static final String TAG = MyFragment.class.getSimpleName();
    private View viewHead;
    private TextView tvTitle, tvNickName, tvPhone, tvCoupon, tvJiFen, tvBeiZhu;
    private ImageView imgRightIcon, imgHeadIcon, imgGoRight, imgGoFavor, imgJiFen,imgBeiZhu,
            imgGoToAbout, imgGoToAdvise;

    private static final int REFRESH_UI = 0;
    private static final int GET_CHAIRNO = 1;
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case REFRESH_UI:
                    refreshUI();
                    break;
                case GET_CHAIRNO:
                    break;
            }
        }
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.mine_layout,container,false);
        initView(mView);
        return mView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            //            Toast.makeText(mActivity, "show", Toast.LENGTH_SHORT).show();
            getMineInfo();
        }else {
            //            Toast.makeText(mActivity, "hide", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getMineInfo();
    }

    private void initView(View mView){
        viewHead = mView.findViewById(R.id.headview);
        tvTitle = viewHead.findViewById(R.id.tv_common_title);
        imgRightIcon = viewHead.findViewById(R.id.img_righticon);
        tvTitle.setText(getResources().getString(R.string.mine_str));
        imgRightIcon.setBackgroundResource(R.drawable.icon_messages);
        tvNickName = mView.findViewById(R.id.tv_nickname);
        tvPhone = mView.findViewById(R.id.tv_phonenumber);
        imgHeadIcon = mView.findViewById(R.id.img_headicon);
        imgGoRight = mView.findViewById(R.id.img_arrow);
        tvCoupon = mView.findViewById(R.id.tv_couponnumber);
        tvJiFen = mView.findViewById(R.id.tv_jifennumber);
        tvBeiZhu = mView.findViewById(R.id.tv_beizhu1);
        imgGoFavor = mView.findViewById(R.id.img_gotomyfavorite);
        imgJiFen = mView.findViewById(R.id.img_gotojifenduihuan);
        imgBeiZhu = mView.findViewById(R.id.img_gotobeizhu);
        imgGoToAbout = mView.findViewById(R.id.img_gotoabout);
        imgGoToAdvise = mView.findViewById(R.id.img_gotojianyi);
        initClickListener();
//        getMineInfo();
    }

    private void refreshUI(){
        Glide.with(getActivity()).load(mineBean.getImgUrl()).into(imgHeadIcon);
        tvPhone.setText(""+mineBean.getUserPhone());
        tvNickName.setText(""+mineBean.getNickName());
        tvJiFen.setText(""+mineBean.getIntegralVal());
        tvCoupon.setText(mineBean.getDiscountCouponCount()+"个");
    }

    private void initClickListener(){
        imgRightIcon.setOnClickListener(this);
        imgGoRight.setOnClickListener(this);
        imgGoFavor.setOnClickListener(this);
        imgJiFen.setOnClickListener(this);
        imgBeiZhu.setOnClickListener(this);
        imgGoToAbout.setOnClickListener(this);
        imgGoToAdvise.setOnClickListener(this);
    }

    MineBean mineBean = new MineBean();
    private void getMineInfo(){
        HttpHelper.getInstance().getMineInfo(getActivity(), new JsonHandler<String>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString, Object response) {
                Log.d(TAG,"onSuccess() === >>>"+responseString);
                int state = ToolUtils.getNetBackCode(responseString);
                String result = ToolUtils.getJsonParseResult(responseString);
                if(state == 100){
                    JSONObject obj =  null;
                    try{
                        obj = new JSONObject(result);
                        if(obj != null){
                            mineBean.setNickName(obj.getString("nickName"));
                            mineBean.setUserPhone(obj.getString("userPhone"));
                            mineBean.setImgUrl(obj.getString("imgUrl"));
                            mineBean.setDiscountCouponCount(obj.getInt("discountCouponCount"));
                            mineBean.setIntegralVal(obj.getInt("integralVal"));
                            myHandler.obtainMessage(REFRESH_UI); //sendEmptyMessage(1);
                        }else{
                            Toast.makeText(getActivity(),"未成功获取个人信息",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getActivity(), "Loading Failed...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String responseString, Object errorResponse) {
                Log.d(TAG,"getMineInfo()==onFailure==>>"+responseString);
                Toast.makeText(getActivity(), "Loading Failed...", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return null;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_righticon:
                Toast.makeText(getActivity(),"跳转到消息中心",Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_arrow:
                Toast.makeText(getActivity(),"跳转到个人中心",Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_gotomyfavorite:
                Toast.makeText(getActivity(),"跳转到我的收藏",Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_gotojifenduihuan:
                Toast.makeText(getActivity(),"跳转到积分兑换",Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_gotobeizhu:
                Toast.makeText(getActivity(),"跳转到备注",Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_gotoabout:
                Toast.makeText(getActivity(),"跳转到关于",Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_gotojianyi:
                Toast.makeText(getActivity(),"跳转到建议",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
