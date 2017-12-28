package com.hl.htk_customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.HomeAddressAdapter;
import com.hl.htk_customer.base.BaseActivity;
import com.hl.htk_customer.entity.AddressListEntity;
import com.hl.htk_customer.model.CommonMsg;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;
import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20.
 * 收货地址
 */

public class HomeAddressActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_return)
    LinearLayout llReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.listView_address)
    SlideAndDragListView listViewAddress;
    @Bind(R.id.rl_addAddress)
    LinearLayout rlAddAddress;
    @Bind(R.id.empty_view)
    ImageView emptyView;

    private final int REQUEST_CODE = 2058;
    private final int REQUEST_CODE_ADD = 2059;

    private HomeAddressAdapter homeAddressAdapter;

    private int tag = 1;
    private int ChooseTag = -1;
    Menu mMenu;
    private AddressListEntity addressListEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_home_address);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initMenu();
        initWidget();
        setClick();
    }


    public void initMenu() {
        mMenu = new Menu(true, true);
        mMenu.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn_width_img))
                .setBackground(getResources().getDrawable(R.color.colorRedText))
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setText("删除")
                .setTextColor(getResources().getColor(R.color.colorWhite))
                .build());
    }

    private void initWidget() {

        tag = getIntent().getIntExtra("TAG", 1);
        ChooseTag = getIntent().getIntExtra("ChooseTag", -1);
        listViewAddress.setMenu(mMenu);
        tvTitle.setText(getResources().getText(R.string.home_address));
        llReturn.setOnClickListener(this);
        rlAddAddress.setOnClickListener(this);

        homeAddressAdapter = new HomeAddressAdapter(this, 1);
        listViewAddress.setAdapter(homeAddressAdapter);

        getAddressList();

        listViewAddress.setOnListItemClickListener(new SlideAndDragListView.OnListItemClickListener() {
            @Override
            public void onListItemClick(View view, int i) {
                AddressListEntity.DataBean item = (AddressListEntity.DataBean) homeAddressAdapter.getItem(i);

                if (ChooseTag == 1) {
                    app.getDefaultAddress().setAddressId(item.getAddressId());
                    app.getDefaultAddress().setUserName(item.getUserName());
                    app.getDefaultAddress().setPhoneNumber(item.getPhone());
                    app.getDefaultAddress().setLocation(item.getLocation());
                    app.getDefaultAddress().setAddress(item.getAddress());
                    app.getDefaultAddress().setSex(item.getSex());
                    finish();
                }
            }
        });
    }


    private void setClick() {

        homeAddressAdapter.setChangeListener(new HomeAddressAdapter.ChangeListener() {
            @Override
            public void changeClick(int position) {
                Intent intent = new Intent(HomeAddressActivity.this, AddAddressActivity.class);
                AddressListEntity.DataBean item = (AddressListEntity.DataBean) homeAddressAdapter.getItem(position);
                intent.putExtra("info", item);
                intent.putExtra("tag", 1);
                startActivityForResult(intent , REQUEST_CODE);
            }
        });

        listViewAddress.setOnMenuItemClickListener(new SlideAndDragListView.OnMenuItemClickListener() {     //左划优化拉出来的 菜单的点击事件
            @Override
            public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
                switch (direction) {
                    case MenuItem.DIRECTION_RIGHT:
                        switch (buttonPosition) {
                            case 0:
                                AddressListEntity.DataBean item = (AddressListEntity.DataBean) homeAddressAdapter.getItem(itemPosition);
                                int addressId = item.getAddressId();
                                delete(addressId, itemPosition);
                                return Menu.ITEM_DELETE_FROM_BOTTOM_TO_TOP;
                        }
                        break;
                }
                return 0;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE == requestCode && resultCode == 204){
            getAddressList();
        }else if (REQUEST_CODE_ADD == requestCode && resultCode == 204){
            getAddressList();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
            case R.id.rl_addAddress:

                if (tag == 1) {
                    startActivityForResult(new Intent(HomeAddressActivity.this, AddAddressActivity.class) , REQUEST_CODE_ADD);
                } else {
                    finish();
                }

                break;
        }
    }

    //获取地址列表
    private void getAddressList() {
        showLoadingDialog();
        RequestParams params = AsynClient.getRequestParams();
        AsynClient.post(MyHttpConfing.homeAddress, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                hideLoadingDialog();
                UiFormat.tryRequest(rawJsonData);
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {

                Log.i(MyHttpConfing.tag, rawJsonResponse);
                Gson gson = new Gson();
                addressListEntity = gson.fromJson(rawJsonResponse, AddressListEntity.class);
                if (addressListEntity.getCode() == 100) {
                    if (addressListEntity.getData() == null || addressListEntity.getData().size() == 0){
                        listViewAddress.setEmptyView(emptyView);
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        homeAddressAdapter.setData(addressListEntity.getData());
                        emptyView.setVisibility(View.GONE);
                    }
                }

                hideLoadingDialog();
            }
        });
    }

    private void delete(int id, final int position) {
        showChangeDialog("正在删除");
        RequestParams params = AsynClient.getRequestParams();
        params.put("addressId", id);
        AsynClient.post(MyHttpConfing.deleteAddress, this, params, new GsonHttpResponseHandler() {
            @Override
            protected Object parseResponse(String rawJsonData) throws Throwable {
                return null;
            }

            @Override
            public void onFailure(int statusCode, String rawJsonData, Object errorResponse) {
                UiFormat.tryRequest(rawJsonData);
                hideChangeDialog();
            }

            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, Object response) {
                Log.i(MyHttpConfing.tag, rawJsonResponse);

                Gson gson = new Gson();
                CommonMsg commonMsg = gson.fromJson(rawJsonResponse, CommonMsg.class);
                showMessage(commonMsg.getMessage());
                if (commonMsg.getCode() == 100) {
                    homeAddressAdapter.getData().remove(position);
                    homeAddressAdapter.notifyDataSetChanged();
                }

                hideChangeDialog();
            }
        });

    }

}
