package com.hl.htk_customer.widget;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.hl.htk_customer.R;
import com.hl.htk_customer.utils.MyApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 、
 * 公司：百迅
 * 创建者： 龙威陶
 * 时间： 2016/5/4.
 * 描述：${TEXT} implements  MultiImageSelectorFragment.Callback
 */
public class XinDeImageSelectActivity extends FragmentActivity  {

    /**
     * 最大图片选择次数，int类型，默认9
     */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    /**
     * 图片选择模式，默认多选
     */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    /**
     * 是否显示相机，默认显示
     */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    /**
     * 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合
     */
    public static final String EXTRA_RESULT = "select_result";
    /**
     * 默认选择集
     */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";

    /**
     * 单选
     */
    public static final int MODE_SINGLE = 0;
    /**
     * 多选
     */
    public static final int MODE_MULTI = 1;
    @Bind(R.id.view1)
    View view1;
    @Bind(R.id.ivFunction)
    ImageView ivFunction;
    @Bind(R.id.tvFunction)
    TextView tvFunction;
    @Bind(R.id.rl_zpxz)
    RelativeLayout rlZpxz;
    @Bind(R.id.image_grid_zpxz)
    FrameLayout imageGridZpxz;
    @Bind(R.id.tvselectNum_zpxz)
    TextView tvselectNumZpxz;
    @Bind(R.id.tvZhengMian_zpxz)
    TextView tvZhengMianZpxz;
    @Bind(R.id.tvCeMian_zpxz)
    TextView tvCeMianZpxz;
    @Bind(R.id.tvBeiMian_zpxz)
    TextView tvBeiMianZpxz;
    @Bind(R.id.tvnext_zpxz)
    TextView tvnextZpxz;
    @Bind(R.id.ll_zp)
    LinearLayout llZp;


    private Map<Integer, String> map = new HashMap<>();


    private ArrayList<String> resultList = new ArrayList<>();

    private int mDefaultCount;
    private int key;
    private int num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

        setContentView(R.layout.ui_staff_zuopinxuanze);
        ButterKnife.bind(this);
        llZp.setVisibility(View.GONE);
        tvFunction.setText("完成");

        Intent intent = getIntent();
        Log.v("TAG", "num----" + intent.getIntExtra("key", 1));
        key = intent.getIntExtra("key", 6);


        Bundle bundle = new Bundle();
//        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, key);
//        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, 1);
//        bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, true);       //false  不显示 照相功能
//        bundle.putStringArrayList(MultiImageSelectorFragment.EXTRA_DEFAULT_SELECTED_LIST, resultList);

//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.image_grid_zpxz, Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle))
//                .commit();

        // 完成按钮

        tvFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(resultList != null && resultList.size() >0){
                    // 返回已选择的图片数据
                    Intent data = new Intent();
                    data.putStringArrayListExtra(EXTRA_RESULT, resultList);


                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });


        ivFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        MyApplication.settopbar(this, view1);


    }

//    private void updateDoneText(){
//        tvFunction.setText(String.format("%s(%d/"+key+")",
//                getString(me.nereo.multi_image_selector.R.string.action_done), resultList.size(), mDefaultCount));
//    }

//    @Override
//    public void onSingleImageSelected(String path) {
//        Intent data = new Intent();
//        resultList.add(path);
//        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
//        setResult(RESULT_OK, data);
//        finish();
//    }

//    @Override
//    public void onImageSelected(String path, int position) {
//        if(!resultList.contains(path)) {
//            resultList.add(path);
//        }
//        // 有图片之后，改变按钮状态
//        if(resultList.size() > 0){
//            updateDoneText();
//            if(!tvFunction.isEnabled()){
//                tvFunction.setEnabled(true);
//            }
//        }
//    }
//
//    @Override
//    public void onImageUnselected(String path, int position) {
//        if(resultList.contains(path)){
//            resultList.remove(path);
//        }
//        updateDoneText();
//        // 当为选择图片时候的状态
//        if(resultList.size() == 0){
//            tvFunction.setText(me.nereo.multi_image_selector.R.string.action_done);
//            tvFunction.setEnabled(false);
//        }
//    }
//
//    @Override
//    public void onCameraShot(File imageFile) {
//        if(imageFile != null) {
//
//            // notify system
//            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile)));
//
//            Intent data = new Intent();
//            resultList.add(imageFile.getAbsolutePath());
//            data.putStringArrayListExtra(EXTRA_RESULT, resultList);
//            setResult(RESULT_OK, data);
//            finish();
//        }
//    }
}
