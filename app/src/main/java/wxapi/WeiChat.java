package wxapi;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.hl.htk_customer.R;
import com.hl.htk_customer.entity.WxEntity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2017/9/25.
 */

public class WeiChat {

    private static final String TAG = "WeChat";

    private Context context;
    private IWXAPI api;
    private WxEntity entity;
    private WxEntity.DataBean object;

    private String mark;
    private String extData;

    public WeiChat(Context context){
        this.context = context;
        create();
    }

    public void create(){
        api = WXAPIFactory.createWXAPI(context, context.getResources().getString(R.string.key_wx_appid));
    }

    public void pay(String weiChar , String mark){
        String result = weiChar.replaceAll("packages" , "packageX");
        Log.i(TAG, "pay: " + weiChar);
        entity = new Gson().fromJson(result, WxEntity.class);

        this.mark = mark;


        if (entity.getCode() == 100){
            object = entity.getData();
            extData = new Gson().toJson(new WxEntity.ExtData(mark , object.getOrderNumber() , object.getOrderBody() , object.getOrderId()));

            sendPayRequest();
        }

    }

    /**调用微信支付*/
    public void sendPayRequest() {
        PayReq req = new PayReq();

        req.appId = object.getAppId();
        req.partnerId = object.getMchId();
        req.prepayId = object.getPrePayId();
        req.nonceStr = object.getNonceStr();
        req.timeStamp = object.getTimestamp()+"";
        req.packageValue = "Sign=WXPay";
        req.sign = object.getSign();

        req.extData = this.extData;//额外信息用于分辨支付类型（外卖，团购，自助点餐）

        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        //3.调用微信支付sdk支付方法
//        Log.i(TAG, "sendPayRequest: "+"签名-- "+ req.sign +"--商户id--" + req.partnerId + "--时间--" + req.timeStamp + "--字符串--" + req.nonceStr + "-appid-"
//                + req.appId + "--packge--"  + req.packageValue + "--预--" + req.prepayId);
        api.sendReq(req);
    }

}
