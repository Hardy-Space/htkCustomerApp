package com.hl.htk_customer.utils;

/**
 * Created by Administrator on 2017/6/15.
 *
 */

public class MyHttpConfing {

    public static final String tag = "TAG-->";

//    private static final String baseUrl = "http://192.168.0.7:8080/htkApp/API/";//内网
    public static final String baseUrl = "http://120.27.5.36:8080/htkApp/API/";//外网
//    public static final String baseUrl = "http://192.168.0.3:8080/htkApp/API/";//青岛办内网
//    private static final String baseUrl = "http://1704aa0586.51mypc.cn:32351/htkApp/API/";//后台测试使用
//      private static final String baseUrl = "http://192.168.1.120:8080/htkApp/API/";

    public static final String remember = baseUrl + "appMember/index?token=";
    public static final String rememberLoginOut = baseUrl + "appMember/index?";

    private static final String allUrl = baseUrl + "AccountMessage";
    private static final String allUrl1 = baseUrl + "shopDataAPI";

    /**
     * 会员尊享平台根url
     */
    private static final String memberBase = baseUrl + "appMemberAPI";

    //支付模块
    private static final String payUrl = baseUrl + "paymentInterfaceAPI";

    private static final String upData = baseUrl + "appAPI";

    //点餐模块
    private static final String diancanUrl = baseUrl + "buffetFoodAPI";

    public static final String erweima = diancanUrl + "/confirmCollection";
    public static final String getshopId = baseUrl+"appAPI/QRCodeDecoding";

    public static final String xiadan = payUrl + "/enterBuffetFoodSuccessfullyTransferred";

    public static final String login = allUrl + "/appAccountLoginByUserName"; //登录

    public static final String getAuth = allUrl + "/sendSms/";      //发送验证码

    public static final String register = allUrl + "/registerByPhone"; //注册

    public static final String userInfo = allUrl + "/getAppAccountData";  //获取用户信息

    public static final String changeHeadUrl = allUrl + "/changeAvaImg";  //修改头像

    public static final String itemList = allUrl + "/product/listAPI";     //菜单项目列表

    public static final String itemListById = allUrl + "/product/getProductListByShopIdAPI";   //通过商铺ID获得菜品列表

    public static final String changePass = allUrl + "/changeAppAccountPassword";     //修改密码

    public static final String tuangouItemList = allUrl + "/order/getGroupBuyOrderListByAccountIdAPI";  //团购订单列表

    public static final String fenleiItemById = allUrl + "/shop/ShopById";  //通过商品ID获取分类

    public static final String homeAddress = allUrl + "/getShippingAddressList"; //收货地址列表

    public static final String changeNickName = allUrl + "/changeNickName";  //修改昵称

    public static final String addAddress = allUrl + "/addAccountShippingAddress"; //添加收货地址

    public static final String upDataAddress = allUrl + "/changeAccountShippingAddress";//更新收货地址
    public static final String collection = allUrl + "/collectionStore";//
    public static final String myCollection = allUrl + "/getCollectionListByToken";


    public static final String productUrl = allUrl1 + "/category"; //产品分类
    public static final String searchShop = allUrl1 + "/getShopByCondition";  //搜索商铺
    public static final String bestShop = allUrl1 + "/getBestShop";   //推荐商家
    public static final String shopInfo = allUrl1 + "/getShopShowInfoById"; //店铺信息
    public static final String shopGoods = allUrl1 + "/getGoodsListByShopId"; //店铺菜单
    public static final String shopEvaluate = allUrl1 + "/getShopUserReviews";//评价
    public static final String shopListById = allUrl1 + "/getFocusShopListByCategoryId"; //根据分类获取店铺列表

    //获取二级分类

    public static final String shopStyle = allUrl1 + "/getChildCategoryListByCIdAndMark";

    /**
     * 支付接口，生成订单，返回订单号
     */
    public static final String pay = payUrl + "/callUpPaymentInterface";

    public static final String orderDetail = allUrl1 + "/getOrderRecordDetailById"; //外卖订单详情

    public static final String WmOrderList = allUrl1 + "/getOrderRecordList";  //外卖订单列表

    public static final String getOrderList = allUrl1 + "/getOrderRecordList";  //外卖订单列表

    public static final String getWmGuanggao = allUrl1 + "/getTakeoutAdList";  //外卖广告

    public static final String evaluateDetail = allUrl1 + "/viewReviewDetailsById"; //评价详情

    public static final String TgShopGuangGao = allUrl1 + "/getGroupBuyAdListById";  //团购店铺广告

    public static final String TgGuangGao = allUrl1 + "/getRandomlyAdList";   // 团购广告

    public static final String evaluate = allUrl1 + "/commentOrder"; //评价

    public static final String deleteOrder = allUrl1 + "/deleteOrderByOrderNumber"; //删除订单

    public static final String cancelOrder = payUrl + "/callUpRefundInterface"; //取消订单

    public static final String reminder = allUrl1 + "/callReminderInterface"; //催单

    public static final String shouhuo = allUrl + "/enterReceipt";//确认收货

    public static final String TuanGouShopInfo = allUrl1 + "/getShopShowInfoById";   //团购店铺信息

    public static final String TuanGouTaoCanDetail = allUrl1 + "/getPackageDetailsById"; //套餐信息

    public static final String taocanEvaluate = allUrl1 + "/getCommentsUnderThePackage"; //套餐评论

    public static final String tgTaoOrderDetail = allUrl1 + "/getGroupBuyOrderDetailById";

    public static final String deleteAddress = allUrl + "/deleteAccountShippingAddressById";

    public static final String upDataUrl = upData + "/checkAppUpdate";

    public static final String message = upData + "/getNoticeCenterByToken";

    public static final String findPass = allUrl + "/forgetPasswordBySMS";

    public static final String UmLogin = allUrl + "/loginByWeChat";

    public static final String QqLogin = allUrl + "/loginByQq";

    public static final String bindPhoneNumber = allUrl + "/weChatLoginCallUpInterface";

    /**
     * 微信，QQ解除绑定
     */
    public static final String unBind = allUrl + "/unbindThirdPartyAccount";

    public static final String loginByAuth = allUrl + "/appAccountLoginByCode";

    public static final String bindQq = allUrl + "/qqLoginCallUpInterface";

    public static final String diancanFenlei = diancanUrl + "/getCategoryList";

    public static final String diancanFenleiList = diancanUrl + "/getGoodsListByCategoryId";

    public static final String shopSeat = diancanUrl + "/getShopSeatInfoById";

    public static final String detail = diancanUrl + "/getProductDetailById";

    public static final String zan = diancanUrl + "/likeProduct";

    public static final String wx = payUrl + "/callUpWeChatPayInterface";

    public static final String zzConfig = payUrl + "/aliPayPaidBuffetFoodSuccessfullyTransferred";

    public static final String diancanRecord = diancanUrl + "/getBuffetFoodOrderList";

    public static final String diancanDetail = diancanUrl + "/getOrderDetailList";

    public static final String diancanAl = payUrl + "/paymentInterfaceByAliPay";

    public static final String diancanWx = payUrl + "/paymentInterfaceByWeChat";

    public static final String diancanSuccess = diancanUrl + "/paymentSuccess";

    public  static  final String  cuidan  = diancanUrl + "/reminderFormToMerchant";

    public  static  final  String  tuicai = diancanUrl + "/confirmWithdrawalRequest";

    /**
     * 会员尊享平台首页数据
     * int shopId,  int pageNum
     */
    public  static  final  String  getMemberHomeListData = memberBase + "/getMemberHomeListData";

    /**
     * 会员尊享平台我的数据
     * int shopId,  int token
     */
    public  static  final  String  getMemberAccountMes = memberBase + "/getMemberAccountMes";

    /**
     * 会员尊享平台优惠券数据
     * int shopId,  int token
     */
    public  static  final  String  getAccountCouponList = memberBase + "/getAccountCouponList";

    /**
     * 会员尊享平台获取店铺信息
     */
    public  static  final  String  getShopIntroduce = memberBase + "/getShopIntroduce";

    /**
     * 会员尊享平台预定座位
     */
    public  static  final  String  addReserveRequest = memberBase + "/addReserveRequest";

    /**
     * 会员尊享平台我的预约
     */
    public  static  final  String  getAccountReserve = memberBase + "/getAccountReserve";

    /**
     * 会员尊享平台积分兑换
     */
    public  static  final  String  getIntegralBuyData = memberBase + "/getIntegralBuyData";

    /**
     * 会员尊享平台积分记录
     */
    public  static  final  String  getAccountIntegralRecord = memberBase + "/getAccountIntegralRecord";

    /**
     * 会员尊享平台，下载关注二维码
     */
    public  static  final  String  getQrImgData = memberBase + "/getQrImgData";

    /**
     * 会员尊享平台，加入会员
     */
    public  static  final  String  addMember = memberBase + "/addMember";

    /**
     * 会员尊享平台，积分兑换，兑换功能
     */
    public  static  final  String  redeemOperation = memberBase + "/redeemOperation";

    /**
     * 会员尊享平台，获取交易记录
     */
    public  static  final  String  getAccountTradingRecord = memberBase + "/getAccountTradingRecord";

    /**
     * 会员尊享平台，建议评价
     */
    public  static  final  String  accountSuggestRequest = memberBase + "/accountSuggestRequest";

    /**
     * 获取店铺头像
     */
    public  static  final  String  getShopImgUrl = allUrl1 + "/getShopImgUrl";

    /**
     * 预定座位详情
     */
    public  static  final  String  getSeatOrderDetail = allUrl1 + "/getSeatOrderDetail";
    /**
     * 团购店铺相册
     */
    public  static  final  String  getTuanGouShopPhoto = allUrl1 + "/getShopAlbumList";

}



