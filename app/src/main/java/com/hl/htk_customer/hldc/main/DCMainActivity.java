package com.hl.htk_customer.hldc.main;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hl.htk_customer.R;
import com.hl.htk_customer.hldc.bean.GoodBean;
import com.hl.htk_customer.hldc.http.HttpHelper;
import com.hl.htk_customer.hldc.http.JsonHandler;
import com.hl.htk_customer.hldc.utils.PreferencesUtils;
import com.hl.htk_customer.hldc.utils.ToolUtils;

public class DCMainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private static final String TAG = DCMainActivity.class.getSimpleName();
    public static DCMainActivity mainActivity;
    private RadioGroup bottomTab;
    private RadioButton btnDianCai;
    private RadioButton btnOrder;
    private RadioButton btnMine;

    private BaseFragment dcFragment;
    private OrderDetailFragment orderFragment;
    private BaseFragment mineFragment;

    private FragmentManager manager;
    private int shopId;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mIntent = this.getIntent();
        shopId = mIntent.getIntExtra("shopId",0);
        strJiaCai = mIntent.getStringExtra("jiacai");
        mainActivity = this;
        setContentView(R.layout.hldc_main);
        initView();
    }

    private void initView(){
        createFragment();
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        bottomTab = (RadioGroup) this.findViewById(R.id.bottomTabs);
        btnDianCai = (RadioButton) this.findViewById(R.id.rb_diancai);
        btnOrder = (RadioButton) this.findViewById(R.id.rb_order);
        btnMine = (RadioButton) this.findViewById(R.id.rb_mine);
        manager = getSupportFragmentManager();
        bottomTab.setOnCheckedChangeListener(this);
        onCheckedChanged(bottomTab, R.id.rb_diancai); //
    }

    private void createFragment(){
        if(dcFragment == null){
            dcFragment = new DianCaiFragment();
        }
        if(orderFragment == null){
            orderFragment = new OrderDetailFragment();
        }
        if(mineFragment == null){
            mineFragment = new MyFragment();
        }
    }

//    boolean isFirstIn = false;
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d(TAG, "onResume() === >>>>") ;
//        if(isFirstIn){
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if(orderFragment == null){
//                        orderFragment = new OrderDetailFragment();
//                    }
//                    orderFragment.onResume();
//                }
//            }, 1000);
//        }if(!isFirstIn){
//            isFirstIn = true;
//        }
//    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragments(transaction);
        switch(i){
            case R.id.rb_diancai:
                showFragment(dcFragment, transaction);
                break;
            case R.id.rb_order:
                showFragment(orderFragment, transaction);
//                orderFragment.onHiddenChanged(true);
                break;
            case R.id.rb_mine:
                showFragment(mineFragment, transaction);
                break;
        }
    }

    private String strJiaCai;
    public void addFoodToList(GoodBean goodBean1){
        Log.d(TAG,"strJiaCai == >>>>"+strJiaCai);
        if(!TextUtils.isEmpty(strJiaCai)){
            if(!strJiaCai.equals("diancan")){
                Log.e("addFoodToList","addFoodToList()=="+goodBean1.toString());
                Intent mIntent = new Intent();
                mIntent.putExtra("food",goodBean1);
                this.setResult(1,mIntent);
                this.finish();
            }else{
                GoodBean goodBean = goodBean1;
                Log.d(TAG,"goodId == >>>>"+goodBean1.getId());
                quickPostOrder(goodBean1);
            }
        }
    }

    private void quickPostOrder(GoodBean mGoodBean){
        HttpHelper.getInstance().quickOrdered(this, mGoodBean.getPrice(), PreferencesUtils.getInt(DCMainActivity.this,"shopId"),
                "["+mGoodBean.toString()+"]", //待修改jsonProductList
                new JsonHandler<String>() {

                    @Override
                    public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString, Object response) {
                        Log.d(TAG,"postOrder()==onSuccess()"+responseString);
                        int state = ToolUtils.getNetBackCode(responseString);
                        String result = ToolUtils.getJsonParseResult(responseString);
                        if(state == 100){
                            PreferencesUtils.putString(DCMainActivity.this,"orderNumber",result);
                            Intent mIntent = new Intent(DCMainActivity.this, OrderedListActivity.class);
                            mIntent.putExtra("type","xiadan");
                            startActivity(mIntent);
                        }else{
                            Toast.makeText(DCMainActivity.this,"加入点餐列表失败",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, String responseString, Object errorResponse) {
                        Log.d(TAG,"postOrder()==onFailure()"+responseString);
                        Toast.makeText(DCMainActivity.this,"确认下单失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected Object parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        return null;
                    }
                });
    }

    private void showFragment(BaseFragment fragment, FragmentTransaction transaction) {
        if (!fragment.isAdded()) transaction.add(R.id.mainFragment, fragment);
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (null != dcFragment) {
            transaction.hide(dcFragment);
        }
        if (null != orderFragment) {
            transaction.hide(orderFragment);
        }
        if (null != mineFragment) {
            transaction.hide(mineFragment);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
