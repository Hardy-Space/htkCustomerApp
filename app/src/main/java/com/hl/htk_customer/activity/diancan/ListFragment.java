package com.hl.htk_customer.activity.diancan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hl.htk_customer.R;
import com.hl.htk_customer.adapter.DianCanListAdapter;
import com.hl.htk_customer.base.BaseFragment;
import com.hl.htk_customer.entity.DianCanEntity;
import com.hl.htk_customer.entity.DianCanFenLeiListEntity;
import com.hl.htk_customer.utils.AsynClient;
import com.hl.htk_customer.utils.GsonHttpResponseHandler;
import com.hl.htk_customer.utils.MyApplication;
import com.hl.htk_customer.utils.MyHttpConfing;
import com.hl.htk_customer.utils.UiFormat;
import com.loopj.android.http.RequestParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/20.
 */

public class ListFragment extends BaseFragment implements AdapterView.OnItemClickListener {


    private boolean isPrepared = false;
    private boolean isFirst = true;
    private View view = null;

    @Bind(R.id.listView)
    ListView listView;

    DianCanListAdapter dianCanListAdapter;

    private int page = 1;
    private DianCanFenLeiListEntity dianCanFenLeiListEntity;
    private int categoryId = -1;

    public static ListFragment newInstance(int categoryId) {

        Bundle args = new Bundle();
        args.putInt("categoryId", categoryId);
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_diancan_list, null);
            isPrepared = true;
            categoryId = getArguments().getInt("categoryId");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lazyInitData();
                }
            }, 200);
        }

        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void lazyInitData() {

        if (isVisible && isFirst && isPrepared) {
            EventBus.getDefault().register(this);
            isFirst = false;
            initWidget();
            getData(page);
        }
    }


    private void initWidget() {

        dianCanListAdapter = new DianCanListAdapter(getActivity());
        listView.setAdapter(dianCanListAdapter);
        listView.setOnItemClickListener(this);

   /*     listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getData(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {


                if (dianCanFenLeiListEntity != null && dianCanFenLeiListEntity.getData() != null && dianCanFenLeiListEntity.getData().size() < 8) {
                    Message message = new Message();
                    message.what = 1000;
                    handler.sendMessage(message);
                } else {
                    page++;
                    getData(page);
                }

            }
        });
*/

    }


    private void getData(final int page) {

        RequestParams params = AsynClient.getRequestParams();
        params.put("categoryId", categoryId);
        //  params.put("pageNumber", page);
        AsynClient.post(MyHttpConfing.diancanFenleiList, getActivity(), params, new GsonHttpResponseHandler() {
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
                Log.i(MyHttpConfing.tag, rawJsonResponse);
                Gson gson = new Gson();
                dianCanFenLeiListEntity = gson.fromJson(rawJsonResponse, DianCanFenLeiListEntity.class);

                if (dianCanFenLeiListEntity.getCode() == 100) {
                    if (dianCanFenLeiListEntity.getData() == null) return;

                 /*   if (page == 1) {
                        dianCanListAdapter.setData(dianCanFenLeiListEntity.getData());
                    } else {
                        dianCanListAdapter.addData(dianCanFenLeiListEntity.getData());
                    }*/

                    dianCanListAdapter.setData(dianCanFenLeiListEntity.getData());

                }

                // complete();
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }


   /* Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1000) {
                complete();
            }

        }
    };


    private void complete() {
        if (listView != null) {
            listView.onRefreshComplete();
        }

    }*/


    @Subscribe
    public void onEventMainThread(DianCanEntity event) {


        if (!event.isChanged()) return;

        for (int i = 0; i < dianCanFenLeiListEntity.getData().size(); i++) {

            String productName1 = dianCanFenLeiListEntity.getData().get(i).getProductName();
            dianCanFenLeiListEntity.getData().get(i).setChooseNum(0);

            for (int j = 0; j < MyApplication.diancanList.size(); j++) {

                String productName = MyApplication.diancanList.get(j).getProductName();

                if (productName1.equals(productName)) {

                    dianCanFenLeiListEntity.getData().get(i).setChooseNum(MyApplication.diancanList.get(j).getChooseNum());
                    break;
                }


            }

        }

        dianCanListAdapter.notifyDataSetChanged();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //  startActivity(new Intent(getActivity(), CaiPinDetailActivity.class));
        Intent intent = new Intent(getActivity(), CaiPinDetailActivity.class);
        intent.putExtra("id", dianCanFenLeiListEntity.getData().get(position).getId());
        intent.putExtra("num",dianCanFenLeiListEntity.getData().get(position).getChooseNum());
        startActivity(intent);
    }
}
