package com.hl.htk_customer.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hl.htk_customer.R;

/**
 * Created by Administrator on 2017/9/21.
 *
 */

public class ImageLoadManager {

    private ImageLoadManager() {
    }

    public static ImageLoadManager getInstance(){
        return ImageLoadHolder.INSTANCE;
    }

    private static class  ImageLoadHolder{
        private static final ImageLoadManager INSTANCE = new ImageLoadManager();
    }

    public void setImage( String url , ImageView iv){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_launcher);
        requestOptions.error(R.drawable.ic_launcher);
        Glide.with(MyApplication.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(url)
                .into(iv);
    }
}
