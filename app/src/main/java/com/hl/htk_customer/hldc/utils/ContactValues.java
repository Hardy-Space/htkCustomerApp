package com.hl.htk_customer.hldc.utils;

/**
 * Created by asus on 2017/11/1.
 */

public class ContactValues {
    /**
     * 请求成功代码
     */
    public static final int REQUEST_SUCCESS = 100;

    public static final int REQUEST_SUCCESS_PHP = 1001;
    /**
     * token 失效
     */
    public static final int REQUEST_TOKEN_TIMEOUT = 1004;
    /**
     * 用户在另一台设备登录
     */
    public static final int REQUEST_LOGINED_ANOTHER_DEVICE = 504;
    /**
     * token 失效
     */
    public static final int REQUEST_REQEAT_ORDER = 909;

    public static final String KEY_TOKEN = "preferences_token";


//    public static final String ROOT_IP = "http://120.27.5.36:8080/htkApp/API/buffetFoodAPI/";  //远程服务器地址

    public static final String ROOT_IP = "http://192.168.0.13:8080/htkApp/API/buffetFoodAPI/";  //本地服务器地址(青岛办马鹏昊)

//    public static final String ROOT_IP = "http://192.168.0.7:8080/htkApp/API/buffetFoodAPI/";   //田晋奇电脑服务器


//    public static final String ROOT_IP = "http://120.27.5.36:8888/htkApp/API/buffetFoodAPI/";  //远程服务器测试版本地址



//    public static final String ROOT_IP = "http://1704aa0586.51mypc.cn:32351/htkApp/API/buffetFoodAPI/";
    public static final String GETCATEGORYLIST = ROOT_IP + "getCategoryList"; // 获取分类列表
    public static final String GOODSLIST = ROOT_IP + "getGoodsListByCategoryId"; // 二、根据分类ID获取分类下商品列表接口
    public static final String COMFIRM_BTN = ROOT_IP + "insertInitialOrder" ;  // 确认按钮接口
    public static final String COMMITS_LIST = ROOT_IP + "getGoodsCommentListByProductId" ;  // 商品下评论列表接口
    public static final String ORDERPAGE_DETAIL = ROOT_IP + "getOrderDetailsByOrderNumber" ;  // 订单详情页接口
    public static final String ORDERED_GOODSLIST = ROOT_IP + "enterOrderRequest" ;  // 已点商品界面接口
    public static final String ORDER_BTN = ROOT_IP + "enterOrderButton" ;  // 下单按钮接口
    public static final String COMFIRM_ORDER = ROOT_IP + "confirmOrderButton" ;  // 确认下单接口
    public static final String GETAVAILBLE_COUPON = ROOT_IP + "getBuffetFoodCouponList" ;//获取当前可用优惠券列表
    public static final String GETPOSITION = ROOT_IP + "getShopSeatInfoById" ; // 获取自助点餐座位接口
    public static final String CUIDAN = ROOT_IP + "reminderInterface" ; // 催单接口
    public static final String TIAO_DAN = ROOT_IP + "adjustOrder" ; // 调单接口
    public static final String MINE = ROOT_IP + "getAccountMes" ; // 我的接口
    public static final String RIGHT_SEARCH = ROOT_IP + "searchProductByKey" ; // 右上角搜索接口
    public static final String COMFIRM_TIAODAN = ROOT_IP + "enterAdjustOrder" ; // 确认调单接口
    public static final String COMFIRM_TIAODAN_GOODSLIST = ROOT_IP + "getLastModifiedAdjustProductList" ; // 确认调单页面商品列表
    public static final String ORDEREDGOODSLIST = ROOT_IP + "getLastModifiedProductList" ; // 已点商品列表
    public static final String GOODS_DETAIL = ROOT_IP + "getProductDetailById"; // 商品详情
    public static final String SET_ISCOLLECT = ROOT_IP + "addToWishListById" ; //收藏和取消收藏
    public static final String GET_TCLIST = ROOT_IP + "getPackageDetailById"; //二十一、根据产品id拿套餐内产品详情
    public static final String QUICK_ORDERED = ROOT_IP + "quickOrder"; //二十一、根据产品id拿套餐内产品详情

    public static final String CHECK_IF_ALREADY_EXIST_ORDER = ROOT_IP + "checkIfAlreadyExistOrder"; //自主点餐是否有未完成订单











}
