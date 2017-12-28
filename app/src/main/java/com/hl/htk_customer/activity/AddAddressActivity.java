package com.hl.htk_customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hl.htk_customer.R;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.AddressListEntity;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20.
 * 新增地址
 */

public class AddAddressActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.tv_contact_mark)
    TextView tvContactMark;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.rl_name)
    RelativeLayout rlName;
    @Bind(R.id.man)
    TextView man;
    @Bind(R.id.woman)
    TextView woman;
    @Bind(R.id.line2)
    View line2;
    @Bind(R.id.tv_phone_mark)
    TextView tvPhoneMark;
    @Bind(R.id.et_phoneNumber)
    EditText etPhoneNumber;
    @Bind(R.id.line3)
    View line3;
    @Bind(R.id.tv_address_mark)
    TextView tvAddressMark;
    @Bind(R.id.et_address1)
    TextView tvAddress1;
    @Bind(R.id.line4)
    View line4;
    @Bind(R.id.address2)
    EditText address2;
    @Bind(R.id.tv_ok)
    TextView tvOk;
    //     姓名： String userName 性别： String sex 电话：Long phone

    //   小区：String location 详细地址：address
    private String userName = "";
    private int sex = -1;
    private String phone = "";
    private String location = "";
    private String address = "";

    private final int REQUEST_CODE = 102;

    private final int RESULT_CODE = 204;


    private int tag = -1;
    private AddressListEntity.DataBean item;

    private int addressId = -1;

    private void getIntentExtra() {

        tag = getIntent().getIntExtra("tag", -1);
        item = (AddressListEntity.DataBean) getIntent().getSerializableExtra("info");

        if (tag != 1) return;
        if (item == null) return;
        addressId = item.getAddressId();
        etName.setText(item.getUserName());
        etPhoneNumber.setText(item.getPhone() + "");
        tvAddress1.setText(item.getLocation());
        address2.setText(item.getAddress());
        sex = item.getSex();

        if (sex == 1) {
            clear();
            man.setBackgroundResource(R.drawable.selected_sex);
            man.setTextColor(getResources().getColor(R.color.colorWhite));
            sex = 1;
        } else {
            clear();
            woman.setBackgroundResource(R.drawable.selected_sex);
            woman.setTextColor(getResources().getColor(R.color.colorWhite));
            sex = 0;
        }

        tvTitle.setText(getResources().getText(R.string.updata_address));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_add_address);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        initWidget();
        getIntentExtra();
    }

    private void initWidget() {
        tvTitle.setText(getResources().getText(R.string.add_address));
        llReturn.setOnClickListener(this);
        man.setOnClickListener(this);
        woman.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        tvAddress1.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
            case R.id.man:
                clear();
                man.setBackgroundResource(R.drawable.selected_sex);
                man.setTextColor(getResources().getColor(R.color.colorWhite));
                sex = 1;
                break;
            case R.id.woman:
                clear();
                woman.setBackgroundResource(R.drawable.selected_sex);
                woman.setTextColor(getResources().getColor(R.color.colorWhite));
                sex = 0;
                break;
            case R.id.et_address1:
                startActivityForResult(new Intent(AddAddressActivity.this, AllLocationActivity.class) , REQUEST_CODE);
                break;
            case R.id.tv_ok:

                userName = etName.getText().toString().trim();
                phone = etPhoneNumber.getText().toString().trim();
                location = tvAddress1.getText().toString().trim();
                address = address2.getText().toString().trim();

                if (TextUtils.isEmpty(userName) || sex == -1 || TextUtils.isEmpty(phone)) {
                    showMessage("请完整填写信息");
                    return;
                }
                if(TextUtils.isEmpty(location) && TextUtils.isEmpty(address)){
                    showMessage("请填写地址信息");
                    return;
                }
                tvOk.setEnabled(false);
                if (tag == 1) {
                    //修改地址
                    upDataAddress(userName, sex, phone, location, address);
                } else {
                    //添加收货地址
                    addAddress(userName, sex, phone, location, address);
                }

                break;

        }
    }


    private void clear() {

        man.setBackgroundResource(R.drawable.bg_sex);
        woman.setBackgroundResource(R.drawable.bg_sex);
        man.setTextColor(getResources().getColor(R.color.colorGrayText));
        woman.setTextColor(getResources().getColor(R.color.colorGrayText));
    }


    //添加收货地址
    private void addAddress(String userName, int sex, String phone, String location, String address) {

        RequestParams params = AsynClient.getRequestParams();
        params.put("userName", userName);
        params.put("sex", sex);
        params.put("phone", Long.parseLong(phone));
        params.put("location", location);
        params.put("address", address);

        AsynClient.post(MyHttpConfing.addAddress, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(MyHttpConfing.tag, rawJsonResponse);
                CommonMsg commonMsg = UiFormat.getCommonMsg(rawJsonResponse);
                showMessage(commonMsg.getMessage());
                if (commonMsg.getCode() == 100) {
                    setResult(RESULT_CODE);
                    finish();
                }
                else {
                    tvOk.setEnabled(true);
                }

            }
        });

    }


    //更新地址
    private void upDataAddress(String userName, int sex, String phone, String location, String address) {

        RequestParams params = AsynClient.getRequestParams();
        params.put("addressId", addressId);
        params.put("userName", userName);
        params.put("sex", sex);
        params.put("phone", Long.parseLong(phone));
        params.put("location", location);
        params.put("address", address);

        AsynClient.post(MyHttpConfing.upDataAddress, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                CommonMsg commonMsg = UiFormat.getCommonMsg(rawJsonResponse);
                showMessage(commonMsg.getMessage());
                if (commonMsg.getCode() == 100) {
                    setResult(RESULT_CODE);
                    finish();
                }
                else {
                    tvOk.setEnabled(true);
                }

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == 101){
            String address = data.getStringExtra("address");
            tvAddress1.setText(address);
        }
    }
}
