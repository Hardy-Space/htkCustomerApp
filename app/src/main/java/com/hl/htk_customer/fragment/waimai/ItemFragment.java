package com.hl.htk_customer.fragment.waimai;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.activity.ConfirmOrderActivity;
import com.hl.htk_customer.adapter.wm.ShopAdapter;
import com.hl.htk_customer.adapter.wm.TestSectionedAdapter;
import com.hl.htk_customer.assistant.ShopToDetailListener;
import com.hl.htk_customer.assistant.onCallBackListener;
import com.hl.htk_customer.base.BaseFragment;
import com.hl.htk_customer.entity.ShopGoodsEntity;
import com.hl.htk_customer.model.ProductType;
import com.hl.htk_customer.model.ShopProduct;
import com.hl.htk_customer.model.UserInfoManager;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.DoubleUtil;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.hl.htk_customer.widget.PinnedHeaderListView;
import com.loopj.android.http.RequestParams;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/22.
 * <p>
 * 菜单
 */

public class ItemFragment extends BaseFragment implements View.OnClickListener, onCallBackListener, ShopToDetailListener {

    private boolean isPrepared = false;
    private boolean isFirst = true;
    private View view = null;

    private boolean isScroll = true;
    private ListView mainlist;
    private PinnedHeaderListView morelist;
    private TestSectionedAdapter sectionedAdapter;
    /**
     * 保存购物车对象到List
     * TODO:考虑保存购物车缓存
     */
    private List<ShopProduct> productList;
    /**
     * 购物车价格
     */
    private TextView shoppingPrise;
    /**
     * 购物车件数
     */
    private TextView shoppingNum;
    /**
     * 去结算
     */
    private TextView settlement;
    /**
     * 购物车View
     */
    private FrameLayout cardLayout;

    private LinearLayout cardShopLayout;
    /**
     * 背景View
     */
    private View bg_layout;
    /**
     * 购物车Logo
     */
    private ImageView shopping_cart;
    // 动画时间
    private int AnimationDuration = 500;
    // 正在执行的动画数量
    private int number = 0;
    // 是否完成清理
    private boolean isClean = false;
    private FrameLayout animation_viewGroup;

    private TextView defaultText;

    private List<String> strings;

    //父布局
    private RelativeLayout parentLayout;

    private TextView noData;

    /**
     * 分类列表
     */
    private List<ProductType> productCategorizes;

    private List<ShopProduct> shopProductsAll;

    private ListView shoppingListView;

    private ShopAdapter shopAdapter;

    /*
    * 店铺id
    * */
    private int shopId = -1;
    /*
    * 价格
    * */
    private double sum = 0.0;

    private Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    // 用来清除动画后留下的垃圾
                    try {
                        animation_viewGroup.removeAllViews();
                    } catch (Exception e) {

                    }
                    isClean = false;

                    break;
                default:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        shopId = getArguments().getInt("shopId");
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_item, null);
            isPrepared = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lazyInitData();
                }
            }, 200);
        }
        return view;
    }

    @Override
    public void lazyInitData() {
        if (isPrepared && isFirst && isVisible) {
            isFirst = false;
            initView();
        }
    }

    private void getDatas(ShopGoodsEntity shopGoodsEntity) {
        getData(shopGoodsEntity);
        initData();
    }

    public List<ProductType> getData(ShopGoodsEntity shopGoodsEntity) {
        productList = new ArrayList<>();
        strings = new ArrayList<>();

        sum = getActivity().getIntent().getDoubleExtra("price", 0.0);
        if (sum > 0) {
            productList = getActivity().getIntent().getParcelableArrayListExtra("productList");
            setPrise();
        }

        int size = shopGoodsEntity.getData().size();
        productCategorizes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ShopGoodsEntity.DataBean dataBean = shopGoodsEntity.getData().get(i);
            ProductType productCategorize = new ProductType();
            productCategorize.setType(dataBean.getCategoryName());
            shopProductsAll = new ArrayList<>();
            List<ShopGoodsEntity.DataBean.TakeoutProductListBean> takeoutProductList = dataBean.getTakeoutProductList();

            if (takeoutProductList == null) continue;

            for (int j = 0; j < takeoutProductList.size(); j++) {

                ShopProduct product = new ShopProduct();
                product.setId(takeoutProductList.get(j).getId());
                product.setGoods(takeoutProductList.get(j).getProductName());
                product.setPrice(takeoutProductList.get(j).getPrice() + "");
                product.setPicture(takeoutProductList.get(j).getImgUrl());
                product.setInventory(takeoutProductList.get(j).getInventory());

                for (int z = 0; z < productList.size(); z++) {
                    if (productList.get(z).getGoods().equals(takeoutProductList.get(j).getProductName())) {
                        product.setNumber(productList.get(z).getNumber());
                    }
                }

                shopProductsAll.add(product);
            }
            productCategorize.setProduct(shopProductsAll);
            productCategorizes.add(productCategorize);
        }
        return productCategorizes;

    }

    private void initView() {
        animation_viewGroup = createAnimLayout();
        noData = (TextView) getView().findViewById(R.id.noData);
        parentLayout = (RelativeLayout) getView().findViewById(R.id.parentLayout);
        shoppingPrise = (TextView) getView().findViewById(R.id.shoppingPrise);
        shoppingNum = (TextView) getView().findViewById(R.id.shoppingNum);
        settlement = (TextView) getView().findViewById(R.id.settlement);
        mainlist = (ListView) getView().findViewById(R.id.classify_mainlist);
        morelist = (PinnedHeaderListView) getView().findViewById(R.id.classify_morelist);
        shopping_cart = (ImageView) getView().findViewById(R.id.shopping_cart);
        defaultText = (TextView) getView().findViewById(R.id.defaultText);
        shoppingListView = (ListView) getView().findViewById(R.id.shopproductListView);
        cardLayout = (FrameLayout) getView().findViewById(R.id.cardLayout);
        cardShopLayout = (LinearLayout) getView().findViewById(R.id.cardShopLayout);
        bg_layout = getView().findViewById(R.id.bg_layout);
        getGoods();
    }


    private void getGoods() {

        RequestParams params = AsynClient.getRequestParams();
        params.put("shopId", shopId);
        AsynClient.post(MyHttpConfing.shopGoods, getActivity(), params, new GsonHttpResponseHandler() {
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
                Log.i(TAG , rawJsonResponse);
                Gson gson = new Gson();
                ShopGoodsEntity shopGoodsEntity = gson.fromJson(rawJsonResponse, ShopGoodsEntity.class);
                if (shopGoodsEntity.getCode() == 100) {
                    if (shopGoodsEntity.getData() == null || shopGoodsEntity.getData().size() == 0)
                        showMessage("店铺目前没有商品");
                    else {
                        getDatas(shopGoodsEntity);
                    }
                }
            }
        });

    }


    public void initData() {
        sectionedAdapter = new TestSectionedAdapter(getActivity(), productCategorizes);
        sectionedAdapter.SetOnSetHolderClickListener(new TestSectionedAdapter.HolderClickListener() {

            @Override
            public void onHolderClick(Drawable drawable, int[] start_location) {
                doAnim(drawable, start_location);
            }

        });

        for (ProductType type : productCategorizes) {
            strings.add(type.getType());
        }
        morelist.setAdapter(sectionedAdapter);

        sectionedAdapter.setCallBackListener(this);
        mainlist.setAdapter(new ArrayAdapter<String>(getActivity(),
                R.layout.categorize_item, strings));

        shopAdapter = new ShopAdapter(getActivity(), productList);
        shoppingListView.setAdapter(shopAdapter);
        shopAdapter.setShopToDetailListener(this);

        mainlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                                    int position, long arg3) {
                isScroll = false;

                for (int i = 0; i < mainlist.getChildCount(); i++) {
                    if (i == position) {
                        mainlist.getChildAt(i).setBackgroundColor(
                                Color.rgb(255, 255, 255));
                    } else {
                        mainlist.getChildAt(i).setBackgroundColor(
                                Color.TRANSPARENT);
                    }
                }
                int rightSection = 0;
                for (int i = 0; i < position; i++) {
                    rightSection += sectionedAdapter.getCountForSection(i) + 1;
                }
                morelist.setSelection(rightSection);
            }

        });

        morelist.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (isScroll) {
                    for (int i = 0; i < mainlist.getChildCount(); i++) {

                        if (i == sectionedAdapter
                                .getSectionForPosition(firstVisibleItem)) {
                            mainlist.getChildAt(i).setBackgroundColor(
                                    Color.rgb(255, 255, 255));
                        } else {
                            mainlist.getChildAt(i).setBackgroundColor(
                                    Color.TRANSPARENT);

                        }
                    }

                } else {
                    isScroll = true;
                }
            }
        });

        // TODO: 2017/10/20 点击列表条目显示具体商品信息

        bg_layout.setOnClickListener(this);
        settlement.setOnClickListener(this);
        shopping_cart.setOnClickListener(this);
    }

    private boolean contains(ShopProduct product) {
        boolean result = false;

        if (productList.size() == 0) return false;

        for (int i = 0; i < productList.size(); i++) {
            //根据商品名判断会出现商品名称相同的情况，故应用id做判断
//            if (productList.get(i).getGoods().equals(product.getGoods())) {
//                result = true;
//                break;
//            }
            if (productList.get(i).getId() == product.getId()) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public void updateProduct(ShopProduct product, String type) {
        if (type.equals("1")) {
            if (!contains(product)) {
                productList.add(product);
            } else {
                for (ShopProduct shopProduct : productList) {
                    if (product.getId() == shopProduct.getId()) {
                        shopProduct.setNumber(product.getNumber());
                    }
                }
            }
        } else if (type.equals("2")) {
            if (contains(product)) {
                if (product.getNumber() == 0) {
                    int position = -1;
                    for (int i = 0; i < productList.size(); i++) {
                        if (productList.get(i).getId() == product.getId()) {
                            position = i;
                            break;
                        }
                    }
                    if (position != -1) {
                        productList.remove(position);
                    }
                } else {
                    for (ShopProduct shopProduct : productList) {
                        if (product.getId() == shopProduct.getId()) {
                            shopProduct.setNumber(product.getNumber());
                        }
                    }
                }
            }
        }
        shopAdapter.notifyDataSetChanged();
        setPrise();
    }

    @Override
    public void onUpdateDetailList(ShopProduct product, String type) {
        if (type.equals("1")) {
            for (int i = 0; i < productCategorizes.size(); i++) {
                shopProductsAll = productCategorizes.get(i).getProduct();
                for (ShopProduct shopProduct : shopProductsAll) {
                    if (product.getGoods().equals(shopProduct.getGoods())) {
                        shopProduct.setNumber(product.getNumber());
                    }
                }
            }
        } else if (type.equals("2")) {
            for (int i = 0; i < productCategorizes.size(); i++) {
                shopProductsAll = productCategorizes.get(i).getProduct();
                for (ShopProduct shopProduct : shopProductsAll) {
                    if (product.getGoods().equals(shopProduct.getGoods())) {
                        shopProduct.setNumber(product.getNumber());
                    }
                }
            }
        }
        sectionedAdapter.notifyDataSetChanged();
        setPrise();
    }

    @Override
    public void onRemovePriduct(ShopProduct product) {
        for (int i = 0; i < productCategorizes.size(); i++) {
            shopProductsAll = productCategorizes.get(i).getProduct();
            for (ShopProduct shopProduct : shopProductsAll) {
                if (product.getGoods().equals(shopProduct.getGoods())) {
                    productList.remove(product);
                    shopAdapter.notifyDataSetChanged();
                    shopProduct.setNumber(shopProduct.getNumber());
                }
            }
        }
        sectionedAdapter.notifyDataSetChanged();
        shopAdapter.notifyDataSetChanged();
        setPrise();
    }

    /**
     * 更新购物车价格
     */
    public void setPrise() {
        sum = 0;
        int shopNum = 0;
        for (ShopProduct pro : productList) {
            sum = DoubleUtil.sum(sum, DoubleUtil.mul((double) pro.getNumber(), Double.parseDouble(pro.getPrice())));
            shopNum = shopNum + pro.getNumber();
        }
        if (shopNum > 0) {
            shoppingNum.setVisibility(View.VISIBLE);
        } else {
            shoppingNum.setVisibility(View.GONE);
        }
        if (sum > 0) {
            shoppingPrise.setVisibility(View.VISIBLE);
        } else {
            shoppingPrise.setVisibility(View.GONE);
        }
        shoppingPrise.setText("¥" + " " + (new DecimalFormat("0.00")).format(sum));
        shoppingNum.setText(String.valueOf(shopNum));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shopping_cart:
                if (productList.isEmpty() || productList == null) {
                    defaultText.setVisibility(View.VISIBLE);
                } else {
                    defaultText.setVisibility(View.GONE);
                }
                if (cardLayout.getVisibility() == View.GONE) {
                    cardLayout.setVisibility(View.VISIBLE);

                    // 加载动画
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.push_bottom_in);
                    // 动画开始
                    cardShopLayout.setVisibility(View.VISIBLE);
                    cardShopLayout.startAnimation(animation);
                    bg_layout.setVisibility(View.VISIBLE);
                } else {
                    cardLayout.setVisibility(View.GONE);
                    bg_layout.setVisibility(View.GONE);
                    cardShopLayout.setVisibility(View.GONE);
                }
                break;

            case R.id.settlement:
                if (productList == null) return;

                if (productList.size() == 0) return;

                if (!new UserInfoManager(getContext()).getISLOGIN()) {
                    showMessage("请先登录");
                    return;
                }

                Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("productList", (ArrayList<? extends Parcelable>) productList);
                bundle.putInt("shopId", shopId);
                bundle.putDouble("price", sum);
                double deliveryFee = getActivity().getIntent().getDoubleExtra("deliveryFee", 0);
                bundle.putDouble("deliveryFee" , deliveryFee);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.bg_layout:
                cardLayout.setVisibility(View.GONE);
                bg_layout.setVisibility(View.GONE);
                cardShopLayout.setVisibility(View.GONE);
                break;
        }
    }

    /**
     *  创建动画层
     */
    private FrameLayout createAnimLayout() {
        ViewGroup rootView = (ViewGroup) getActivity().getWindow().getDecorView();
        FrameLayout animLayout = new FrameLayout(getActivity());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;

    }

    private void doAnim(Drawable drawable, int[] start_location) {
        if (!isClean) {
            setAnim(drawable, start_location);
        } else {
            try {
                animation_viewGroup.removeAllViews();
                isClean = false;
                setAnim(drawable, start_location);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isClean = true;
            }
        }
    }

    /**
     * 动画效果设置
     *
     * @param drawable       将要加入购物车的商品
     * @param start_location 起始位置
     */
    @SuppressLint("NewApi")
    private void setAnim(Drawable drawable, int[] start_location) {
        Animation mScaleAnimation = new ScaleAnimation(1.2f, 0.6f, 1.2f, 0.6f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mScaleAnimation.setFillAfter(true);

        final ImageView iview = new ImageView(getActivity());
        iview.setImageDrawable(drawable);
        final View view = addViewToAnimLayout(animation_viewGroup, iview,
                start_location);


        view.setAlpha(0.6f);

        int[] end_location = new int[2];
        settlement.getLocationInWindow(end_location);

        // 计算位移
        int endX = 0 - start_location[0] + 40;// 动画位移的X坐标
        int endY = end_location[1] - start_location[1];// 动画位移的y坐标
        TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
                0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);


        Animation mRotateAnimation = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.3f, Animation.RELATIVE_TO_SELF,
                0.3f);
        mRotateAnimation.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(mRotateAnimation);
        set.addAnimation(mScaleAnimation);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(500);// 动画的执行时间
        view.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                number++;
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                number--;
                if (number == 0) {
                    isClean = true;
                    myHandler.sendEmptyMessage(0);
                }

                ObjectAnimator.ofFloat(shopping_cart, "translationY", 0, 4, -2, 0).setDuration(400).start();
                ObjectAnimator.ofFloat(shoppingNum, "translationY", 0, 4, -2, 0).setDuration(400).start();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });

    }

    /**
     * @param vg       动画运行的层 这里是frameLayout
     * @param view     要运行动画的View
     * @param location 动画的起始位置
     * @return
     * @deprecated 将要执行动画的view 添加到动画层
     */
    private View addViewToAnimLayout(ViewGroup vg, View view, int[] location) {
        int x = location[0];
        int y = location[1];
        vg.addView(view);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setPadding(5, 5, 5, 5);
        view.setLayoutParams(lp);

        return view;
    }

    /**
     * 内存过低时及时处理动画产生的未处理冗余
     */
    @Override
    public void onLowMemory() {
        isClean = true;
        try {
            animation_viewGroup.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
        isClean = false;
        super.onLowMemory();
    }

}
