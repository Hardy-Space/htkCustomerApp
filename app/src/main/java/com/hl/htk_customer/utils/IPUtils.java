package com.hl.htk_customer.utils;

import android.util.Log;

import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by Administrator on 2017/10/20.
 * 获取手机端IP
 */

public class IPUtils {

    /**
     * 获取自己手机的ipv4地址
     * @return
     */
    public static String getIp() {
        try {

            for (Enumeration<NetworkInterface> en = NetworkInterface

                    .getNetworkInterfaces(); en.hasMoreElements();) {

                NetworkInterface intf = en.nextElement();

                for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr

                        .hasMoreElements();) {

                    InetAddress inetAddress = ipAddr.nextElement();
                    // ipv4地址
                    if (!inetAddress.isLoopbackAddress()
                            && InetAddressUtils.isIPv4Address(inetAddress
                            .getHostAddress())) {

                        Log.i("TAG", "ipv4=" + inetAddress.getHostAddress());
                        return inetAddress.getHostAddress();

                    }

                }

            }

        } catch (Exception ex) {

        }

        return "192.168.1.0";

    }

}
