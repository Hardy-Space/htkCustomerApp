package com.hl.htk_customer.hldc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hl.htk_customer.R;
import com.hl.htk_customer.hldc.bean.CommentBean;

import java.util.List;


/**
 * Created by asus on 2017/11/7.
 */

public class CommentAdapter extends BaseAdapter {

    private Context mContext;
    private List<CommentBean> mList;
    public CommentAdapter(Context context,List<CommentBean> list){
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder mHolder = null;
        if(mHolder == null){
            mHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.comment_item_layout, null);
            mHolder.imgA = view.findViewById(R.id.xingxinga);
            mHolder.imgB = view.findViewById(R.id.xingxinga);
            mHolder.imgC = view.findViewById(R.id.xingxinga);
            mHolder.imgD = view.findViewById(R.id.xingxinga);
            mHolder.imgE = view.findViewById(R.id.xingxinga);
            mHolder.tvUserName = view.findViewById(R.id.tv_username);
            mHolder.tvDate = view.findViewById(R.id.tv_date);
            mHolder.tvComment = view.findViewById(R.id.tv_commentcontent);
            view.setTag(mHolder);
        }else{
            mHolder = (ViewHolder)view.getTag();
        }
        if(mList.get(i).getCommentStar() == 0){
            mHolder.imgA.setBackgroundResource(R.drawable.huixing);
            mHolder.imgB.setBackgroundResource(R.drawable.huixing);
            mHolder.imgC.setBackgroundResource(R.drawable.huixing);
            mHolder.imgD.setBackgroundResource(R.drawable.huixing);
            mHolder.imgE.setBackgroundResource(R.drawable.huixing);
        }else if(mList.get(i).getCommentStar() == 1){
            mHolder.imgA.setBackgroundResource(R.drawable.hongxing);
            mHolder.imgB.setBackgroundResource(R.drawable.huixing);
            mHolder.imgC.setBackgroundResource(R.drawable.huixing);
            mHolder.imgD.setBackgroundResource(R.drawable.huixing);
            mHolder.imgE.setBackgroundResource(R.drawable.huixing);
        }else if(mList.get(i).getCommentStar() == 2){
            mHolder.imgA.setBackgroundResource(R.drawable.hongxing);
            mHolder.imgB.setBackgroundResource(R.drawable.hongxing);
            mHolder.imgC.setBackgroundResource(R.drawable.huixing);
            mHolder.imgD.setBackgroundResource(R.drawable.huixing);
            mHolder.imgE.setBackgroundResource(R.drawable.huixing);
        }else if(mList.get(i).getCommentStar() == 3){
            mHolder.imgA.setBackgroundResource(R.drawable.hongxing);
            mHolder.imgB.setBackgroundResource(R.drawable.hongxing);
            mHolder.imgC.setBackgroundResource(R.drawable.hongxing);
            mHolder.imgD.setBackgroundResource(R.drawable.huixing);
            mHolder.imgE.setBackgroundResource(R.drawable.huixing);
        }else if(mList.get(i).getCommentStar() == 4){
            mHolder.imgA.setBackgroundResource(R.drawable.hongxing);
            mHolder.imgB.setBackgroundResource(R.drawable.hongxing);
            mHolder.imgC.setBackgroundResource(R.drawable.hongxing);
            mHolder.imgD.setBackgroundResource(R.drawable.hongxing);
            mHolder.imgE.setBackgroundResource(R.drawable.huixing);
        }else if(mList.get(i).getCommentStar() == 5){
            mHolder.imgA.setBackgroundResource(R.drawable.hongxing);
            mHolder.imgB.setBackgroundResource(R.drawable.hongxing);
            mHolder.imgC.setBackgroundResource(R.drawable.hongxing);
            mHolder.imgD.setBackgroundResource(R.drawable.hongxing);
            mHolder.imgE.setBackgroundResource(R.drawable.hongxing);
        }
        mHolder.tvUserName.setText(mList.get(i).getAccountUserName()+"");
        mHolder.tvDate.setText(mList.get(i).getCommentTime()+"");
        mHolder.tvComment.setText(mList.get(i).getCommentContent()+"");
        return view;
    }

    static class ViewHolder{
        ImageView imgA,imgB,imgC,imgD,imgE;
        TextView tvUserName,tvDate,tvComment;
    }
}
