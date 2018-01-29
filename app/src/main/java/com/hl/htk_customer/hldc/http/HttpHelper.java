package com.hl.htk_customer.hldc.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.hl.htk_customer.hldc.utils.ContactValues;
import com.hl.htk_customer.hldc.utils.Logger;
import com.hl.htk_customer.hldc.utils.PreferencesUtils;
import com.hl.htk_customer.hldc.utils.ToolUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;


/**
 * Created by asus on 2017/11/1.
 */

public class HttpHelper {

    //activity 销毁 取消请求
    private static AsyncHttpClient mClient;
    private static HttpHelper httpHelper;
    public static HttpHelper getInstance(){
        if(httpHelper == null){
            synchronized (HttpHelper.class){
                if(httpHelper == null){
                    httpHelper = new HttpHelper();
                }
            }
        }
        return httpHelper;
    }

    private AsyncHttpClient getClient() {
        if (mClient == null){
            mClient = new AsyncHttpClient();
        }
        return mClient;
    }

    public static void cancleRequests(Context context) {
        if (mClient != null && context != null) {
            mClient.cancelRequests(context, true);
        }
    }

    public void addDefaultParams(Context context, RequestParams params) {
        String token = null;
        if (TextUtils.isEmpty(ToolUtils.getToken()) || ToolUtils.getToken().equals("null")) {
            token = PreferencesUtils.getString(context, ContactValues.KEY_TOKEN);
        } else {
            token = ToolUtils.getToken();
        }
        params.put("token", token);
    }

    private void requestForGet(Context context, String url, RequestParams params, TextHttpResponseHandler handler) {
        AsyncHttpClient client = getClient();
        addDefaultParams(context, params);
        client.get(context, url, params, handler);
    }

    private void requestForPost(Context context, String url, RequestParams params, JsonHandler handler) {
        AsyncHttpClient client = getClient();
        addDefaultParams(context, params);
        Logger.gLog().e("[" + params.toString() + "]");
        client.post(context, url, params, handler);
    }

    private void requestForPost(String url, RequestParams params, JsonHandler handler) {
        requestForPost(null, url, params, handler);
    }

    private void requestForPostNoToken(Context context, String url, RequestParams params, JsonHandler handler){
        AsyncHttpClient client = getClient();
        Log.d("HttpHelper","[" + params.toString() + "]");
        client.post(context, url, params, handler);
    }

    /**
     * 一、获取分类 --
     */
    public void getCategoryList(Context context,int shopId,JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
//        params.put("qrKey", "udgCpUD3O1EpSQwfOweBHA==");
        params.put("shopId", shopId);
        requestForPostNoToken(context,ContactValues.GETCATEGORYLIST,params,jsonHandler);
    }

    /**
     * 二、根据分类ID获取分类下商品列表接口-- int categoryId（分类id）, int pageNum(分页参数,默认页数为1)
     */
    public void getGoodsListById(Context context,int categoryId,int pageNum,JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
        params.put("categoryId", categoryId+"");
        params.put("pageNum", pageNum+"");
//        params.put("token", "db5af7ca-e946-483a-8acd-d3b0678a4c8f");
        requestForPost(context,ContactValues.GOODSLIST,params,jsonHandler);
    }

    /**
     * 三、确认按钮接口--
     * double   orderAmount订单金额,int  shopId  店铺id,
     * String   jsonProductList  　用户选择要下单的产品集合json字符串
     * {int productId(产品id), String productName(产品名称)
     * int quantity(数量)，　double price(价格)
     */
    public void comfirmBtn(Context context,double orderAmount, int shopId,String jsonProductList,JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
        params.put("orderAmount", orderAmount+"");
        params.put("jsonProductList", jsonProductList+"");
        params.put("shopId", shopId+"");
        requestForPostNoToken(context,ContactValues.COMFIRM_BTN,params,jsonHandler);
    }

    /**
     * 四、商品下评论列表接口
     * int shopId(商铺id),  int productId(产品id),  int pageNum(分页参数)
     */
    public void getCommitsListById(Context context,int shopId,int productId, int pageNum,JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
        params.put("shopId", shopId+"");
        params.put("productId", productId+"");
        params.put("pageNum", pageNum+"");
        requestForPostNoToken(context,ContactValues.COMMITS_LIST,params,jsonHandler);
    }

    /**
     * 五、订单详情页面接口
     * String orderNumber
     */
    public void getOrderDetail(Context context,String orderNumber,JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
        params.put("orderNumber", orderNumber+"");
        requestForPostNoToken(context,ContactValues.ORDERPAGE_DETAIL,params,jsonHandler);
    }

    /**
     * 六、已点商品界面接口
     * String orderNumber
     */
    public void getOrderedGoodsPage(Context context,String orderNumber,JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
        params.put("orderNumber", orderNumber+"");
        requestForPostNoToken(context,ContactValues.ORDERED_GOODSLIST,params,jsonHandler);
    }

    /**
     * 七、下单按钮接口
     * String orderNumber，String jsonProductList
     */
    public void commitOrderBtn(Context context,String orderNumber,String jsonProductList,JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
        params.put("orderNumber", orderNumber+"");
        params.put("jsonProductList", jsonProductList+"");
        requestForPostNoToken(context,ContactValues.ORDER_BTN,params,jsonHandler);
    }

    /**
     * 八、确认下单接口
     * String remark(备注)，double discountAmount(优惠金额)，
     * String seatName(座位号名字)，double orderAmount(订单金额)，
     * String orderNumber(订单号)，int discountCouponId(优惠券id)
     */
    public void commitOrderBtn(Context context,String remark,double discountAmount,String seatName,
                               double orderAmount,String orderNumber,int discountCouponId,JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
        params.put("remark", remark+"");
        params.put("discountAmount", discountAmount+"");
        params.put("seatName", seatName+"");
        params.put("orderAmount", orderAmount+"");
        params.put("orderNumber", orderNumber+"");
        params.put("discountCouponId", discountCouponId+"");
        requestForPostNoToken(context,ContactValues.COMFIRM_ORDER,params,jsonHandler);
    }

    /**
     * 九、获取当前可用优惠券接口
     * String token
     */
    public void getAvailbleCoupon(Context context,JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
        requestForPost(context,ContactValues.GETAVAILBLE_COUPON,params,jsonHandler);
    }

    /**
     * 十、获取自助点餐座位列表
     * int shopId  (店铺id)
     */
    public void getSeatPosition(Context context,int shopId, JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
        params.put("shopId", shopId+"");
        requestForPostNoToken(context,ContactValues.GETPOSITION,params,jsonHandler);
    }

    /**
     * 十一、催单接口
     * String orderNumber
     */
    public void cuiDan(Context context,String orderNumber,JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
        params.put("orderNumber", orderNumber+"");
        requestForPostNoToken(context,ContactValues.CUIDAN,params,jsonHandler);
    }

    /**
     * 十二、调单接口
     * String orderNumber，String jsonProductList
     */
    public void tiaoDan(Context context,String orderNumber,String jsonProductList,JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
        params.put("orderNumber", orderNumber+"");
        params.put("jsonProductList", jsonProductList+"");
        requestForPostNoToken(context,ContactValues.TIAO_DAN,params,jsonHandler);
    }

    /**
     * 十三、我的
     * String token
     */
    public void getMineInfo(Context context,JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
//        params.put("token","db5af7ca-e946-483a-8acd-d3b0678a4c8f");
        requestForPost(context,ContactValues.MINE,params,jsonHandler);
    }

    /**
     * 十四、右上角搜索接口
     * String searchKey
     */
    public void searchBtn(Context context,String searchKey,JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
        params.put("searchKey", searchKey+"");
        requestForPostNoToken(context,ContactValues.RIGHT_SEARCH,params,jsonHandler);
    }

    /**
     * 十五、确认调单接口
     * String orderNumber，String jsonProductList
     */
    public void comfirmTiaoDan(Context context,String orderNumber,String jsonProductList,JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
        params.put("orderNumber", orderNumber+"");
        params.put("jsonProductList", jsonProductList+"");
        requestForPostNoToken(context,ContactValues.COMFIRM_TIAODAN,params,jsonHandler);
    }

    /**
     * 十六、 确认调单页面商品列表
     * String orderNumber
     */
    public void comfirmTiaoDanGoodsList(Context context,String orderNumber,JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
        params.put("orderNumber", orderNumber+"");
        requestForPostNoToken(context,ContactValues.COMFIRM_TIAODAN_GOODSLIST,params,jsonHandler);
    }

    /**
     * 十七、已点商品列表
     * String orderNumber
     */
    public void getOrderedGoodsList(Context context,String orderNumber,JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
        params.put("orderNumber", orderNumber+"");
        requestForPostNoToken(context,ContactValues.ORDEREDGOODSLIST,params,jsonHandler);
    }

    /**
     * 十八、 商品详情
     * int productId  (商品id)
     */
    public void getGoodDetail(Context context,int productId,JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
        params.put("productId", productId+"");
        requestForPostNoToken(context,ContactValues.GOODS_DETAIL,params,jsonHandler);
    }

    /**
     * 十九、收藏和取消收藏接口
     * int productId, String token,  int state(0 取收， 1收藏)
     */
    public void setIsCollect(Context context, int productId, int state, JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
        params.put("productId", productId+"");
//        params.put("token", "db5af7ca-e946-483a-8acd-d3b0678a4c8f");
        params.put("state", state+"");
        requestForPost(context,ContactValues.SET_ISCOLLECT,params,jsonHandler);
    }

    /**
     * 二十一、根据产品id拿套餐内产品详情
     * int productId  (商品id)
     */
    public void getTcList(Context context,int productId,JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
        params.put("id", productId+"");
        requestForPostNoToken(context,ContactValues.GET_TCLIST,params,jsonHandler);
    }

    /**
     * 三、确认按钮接口--
     * double   orderAmount订单金额,int  shopId  店铺id,
     * String   jsonProductList  　用户选择要下单的产品集合json字符串
     * {int productId(产品id), String productName(产品名称)
     * int quantity(数量)，　double price(价格)
     */
    public void quickOrdered(Context context,double orderAmount, int shopId,String jsonProductList,JsonHandler<String> jsonHandler){
        RequestParams params = new RequestParams();
        params.put("orderAmount", orderAmount+"");
        params.put("jsonProductList", jsonProductList+"");
        params.put("shopId", shopId+"");
        requestForPostNoToken(context,ContactValues.QUICK_ORDERED,params,jsonHandler);
    }
}
